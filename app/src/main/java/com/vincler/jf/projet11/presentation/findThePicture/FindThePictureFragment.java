package com.vincler.jf.projet11.presentation.findThePicture;

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

public class FindThePictureFragment extends Fragment {

    private LanguageEnum bundleLanguage;
    private FindThePictureViewModel viewModel;
    private TextView wordText;
    private ImageView imageViewTopLeft;
    private ImageView imageViewTopRight;
    private ImageView imageViewBottomLeft;
    private ImageView imageViewBottomRight;

    public static FindThePictureFragment newInstance(LanguageEnum bundleLanguage) {
        FindThePictureFragment findThePictureFragment = new FindThePictureFragment();

        Bundle args = new Bundle();
        args.putSerializable("language", bundleLanguage);
        findThePictureFragment.setArguments(args);
        return findThePictureFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_findthepicture, container, false);
        bundleLanguage = (LanguageEnum) getArguments().getSerializable("language");
        wordText = root.findViewById(R.id.fragment_findthepicture_word_text);
        imageViewTopLeft = root.findViewById(R.id.fragment_findthepicture_imageView_top_left);
        imageViewTopRight = root.findViewById(R.id.fragment_findthepicture_imageView_top_right);
        imageViewBottomLeft = root.findViewById(R.id.fragment_findthepicture_imageView_bottom_left);
        imageViewBottomRight = root.findViewById(R.id.fragment_findthepicture_imageView_bottom_right);
        return root;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(FindThePictureViewModel.class);
        viewModel.getData(bundleLanguage);

        viewModel.currentModel.observe(getViewLifecycleOwner(), model ->
                {
                    wordText.setText(model.getWord());
                    displayPicture(model.getTopLeftPicture(), imageViewTopLeft);
                    displayPicture(model.getTopRightPicture(), imageViewTopRight);
                    displayPicture(model.getBottomLeftPicture(), imageViewBottomLeft);
                    displayPicture(model.getBottomRightPicture(), imageViewBottomRight);
                }
        );

        viewModel.isErrorLoading.observe(getViewLifecycleOwner(), errorLoading ->
        {
            if (errorLoading) {
                Utils.toastErrorLoading(getContext());
                viewModel.isErrorLoading.postValue(false);
            }
        });

        imageClickListener(imageViewTopLeft, 0);
        imageClickListener(imageViewTopRight, 1);
        imageClickListener(imageViewBottomLeft, 2);
        imageClickListener(imageViewBottomRight, 3);

        viewModel.isGameOver.observe(getViewLifecycleOwner(), gameOver ->
                {
                    if (gameOver) {
                        gameOver();
                    }
                }
        );

        viewModel.borderPictureColor.observe(getViewLifecycleOwner(), borderPictureColor ->
                {
                    displayBorderPicture(borderPictureColor);
                }
        );

    }

    private void gameOver() {

        int score = viewModel.score.getValue();
        Fragment resultGameFragment = ResultGameFragment.newInstance(score);
        Utils.replaceFragmentInGameActivity(getActivity(), resultGameFragment);
    }

    private void imageClickListener(ImageView imageView, int index) {
        imageView.setOnClickListener(view -> viewModel.userChoosePictureAtIndex(index));
    }

    private void displayBorderPicture(BorderColorModel borderPictureColor) {

        String colorBorder = "";

        if (borderPictureColor.getBorderColor() == BorderColorEnum.GREEN) {
            colorBorder = "#0AEA14";
        }
        if (borderPictureColor.getBorderColor() == BorderColorEnum.RED) {
            colorBorder = "#E53935";
        }
        if (borderPictureColor.getBorderColor() == BorderColorEnum.TRANSPARENT) {
            colorBorder = "#00000000";
        }

        ImageView imageView = null;
        if (borderPictureColor.getPositionWord() == 0) {
            imageView = imageViewTopLeft;
        }
        if (borderPictureColor.getPositionWord() == 1) {
            imageView = imageViewTopRight;
        }
        if (borderPictureColor.getPositionWord() == 2) {
            imageView = imageViewBottomLeft;
        }
        if (borderPictureColor.getPositionWord() == 3) {
            imageView = imageViewBottomRight;
        }

        imageView.setBackgroundColor(Color.parseColor(colorBorder));
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
