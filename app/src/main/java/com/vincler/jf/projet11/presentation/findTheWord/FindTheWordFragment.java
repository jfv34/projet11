package com.vincler.jf.projet11.presentation.findTheWord;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.vincler.jf.projet11.R;
import com.vincler.jf.projet11.models.BorderColorEnum;
import com.vincler.jf.projet11.models.BorderColorModel;
import com.vincler.jf.projet11.models.LanguageEnum;
import com.vincler.jf.projet11.presentation.resultGame.ResultGameFragment;
import com.vincler.jf.projet11.utils.Utils;

public class FindTheWordFragment extends Fragment {

    private LanguageEnum bundleLanguage;
    private FindTheWordViewModel viewModel;
    private ImageView pictureImageView;
    private TextView wordTopLeft;
    private TextView wordTopRight;
    private TextView wordBottomLeft;
    private TextView wordBottomRight;

    public static FindTheWordFragment newInstance(LanguageEnum bundleLanguage) {
        FindTheWordFragment findTheWordFragment = new FindTheWordFragment();

        Bundle args = new Bundle();
        args.putSerializable("language", bundleLanguage);
        findTheWordFragment.setArguments(args);
        return findTheWordFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_findtheword, container, false);
        bundleLanguage = (LanguageEnum) getArguments().getSerializable("language");
        pictureImageView = root.findViewById(R.id.fragment_findtheword_imageView);
        wordTopLeft = root.findViewById(R.id.fragment_findtheword_textView_top_left);
        wordTopRight = root.findViewById(R.id.fragment_findtheword_textView_top_right);
        wordBottomLeft = root.findViewById(R.id.fragment_findtheword_textView_bottom_left);
        wordBottomRight = root.findViewById(R.id.fragment_findtheword_textView_bottom_right);
        return root;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(FindTheWordViewModel.class);
        viewModel.getData(bundleLanguage);

        viewModel.currentModel.observe(getViewLifecycleOwner(), model ->
                {
                    displayPicture(model.getPicture(),pictureImageView);
                    wordTopLeft.setText(model.getTopLeftWord());
                    wordTopRight.setText(model.getTopRightWord());
                    wordBottomLeft.setText(model.getBottomLeftWord());
                    wordBottomRight.setText(model.getBottomRightWord());

                }
        );

        textClickListener(wordTopLeft, 0);
        textClickListener(wordTopRight, 1);
        textClickListener(wordBottomLeft, 2);
        textClickListener(wordBottomRight, 3);

        viewModel.isGameOver.observe(getViewLifecycleOwner(), gameOver ->
                {
                    if (gameOver) {
                        gameOver();
                    }
                }
        );

        viewModel.borderWordColor.observe(getViewLifecycleOwner(), borderWordColor ->
                {
                    displayBorderWord(borderWordColor);
                }
        );

    }

    private void gameOver() {
        int score = viewModel.score.getValue();
        Fragment resultGameFragment = ResultGameFragment.newInstance(score);
        Utils.replaceFragmentInMenuActivity(getActivity(), resultGameFragment);
    }

    private void textClickListener(TextView textView, int index) {
        textView.setOnClickListener(view -> viewModel.userChooseWordAtIndex(index));
    }

    private void displayBorderWord(BorderColorModel borderTextColor) {

        String colorBorder = "";

        if (borderTextColor.getBorderColor() == BorderColorEnum.GREEN) {
            colorBorder = "#0AEA14";
        }
        if (borderTextColor.getBorderColor() == BorderColorEnum.RED) {
            colorBorder = "#E53935";
        }
        if (borderTextColor.getBorderColor() == BorderColorEnum.TRANSPARENT) {
            colorBorder = "#00000000";
        }

        TextView textView = null;
        if (borderTextColor.getPositionWord() == 0) {
            textView =  wordTopLeft;
        }
        if (borderTextColor.getPositionWord() == 1) {
            textView = wordTopRight;
        }
        if (borderTextColor.getPositionWord() == 2) {
            textView = wordBottomLeft;
        }
        if (borderTextColor.getPositionWord() == 3) {
           textView = wordBottomRight;
        }

        textView.setBackgroundColor(Color.parseColor(colorBorder));
    }

    private void displayPicture(String url, ImageView imageView) {

        Glide.with(this)
                .load(url) // image url
                //.placeholder(R.drawable.placeholder) // any placeholder to load at start
                //.error(R.drawable.imagenotfound)  // any image in case of error
                .override(100, 100) // resizing
                .centerCrop()
                .into(imageView);  // imageview object
    }
}
