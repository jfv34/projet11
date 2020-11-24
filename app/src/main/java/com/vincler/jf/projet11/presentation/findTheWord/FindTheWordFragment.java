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
import com.vincler.jf.projet11.models.ColorEnum;
import com.vincler.jf.projet11.models.BorderColorModel;
import com.vincler.jf.projet11.models.LanguageEnum;
import com.vincler.jf.projet11.presentation.resultGame.ResultGameFragment;
import com.vincler.jf.projet11.utils.Utils;

// This class displays the view of the game "Find the word"
// The user must choose from four words the one that corresponds to the picture.
public class FindTheWordFragment extends Fragment {

    private LanguageEnum bundleLanguage;        // Language to learn, chosen in the menu
    private FindTheWordViewModel viewModel;     // ViewModel
    private ImageView pictureImageView;         // The picture
    private TextView wordTopLeft;               // Word top left
    private TextView wordTopRight;              // Word top right
    private TextView wordBottomLeft;            // Word bottom left
    private TextView wordBottomRight;           // Word bottom right

    // instantiate this fragment
    public static FindTheWordFragment newInstance(LanguageEnum bundleLanguage) {
        FindTheWordFragment findTheWordFragment = new FindTheWordFragment();

        Bundle args = new Bundle();
        args.putSerializable("language", bundleLanguage); // Gets langage from the menu
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
        viewModel.getData(bundleLanguage);  // Gets the list of draws and add the first draw in viewModel.currentModel

        // Displays the current draw (viewModel.currentModel):
        viewModel.currentModel.observe(getViewLifecycleOwner(), model ->
                {
                    displayPicture(model.getPicture(),pictureImageView);
                    wordTopLeft.setText(model.getTopLeftWord());
                    wordTopRight.setText(model.getTopRightWord());
                    wordBottomLeft.setText(model.getBottomLeftWord());
                    wordBottomRight.setText(model.getBottomRightWord());

                }
        );

        // Displays a error message in toast when no data is loading:
        viewModel.isErrorLoading.observe(getViewLifecycleOwner(), errorLoading ->
        {
            if (errorLoading) {
                Utils.toastErrorLoading(getContext());
                viewModel.isErrorLoading.postValue(false);
            }
        });

        // When user clicks an image: call imageClickListener:
        textClickListener(wordTopLeft, 0);
        textClickListener(wordTopRight, 1);
        textClickListener(wordBottomLeft, 2);
        textClickListener(wordBottomRight, 3);

        // When all draws have been played: call gameOver:
        viewModel.isGameOver.observe(getViewLifecycleOwner(), gameOver ->
                {
                    if (gameOver) {
                        gameOver();
                    }
                }
        );

        // When border picture color must change: call displayBorderWord:
        viewModel.borderWordColor.observe(getViewLifecycleOwner(), this::displayBorderWord
        );

    }

    // When the game is over, gets the score and replace this fragment by ResultGameFragment
    private void gameOver() {
        int score = viewModel.score.getValue();
        Fragment resultGameFragment = ResultGameFragment.newInstance(score);
        Utils.replaceFragmentInGameActivity(getActivity(), resultGameFragment);
    }

    // When a word is clicked, call viewModel.userChooseWordAtIndex and send him the word position (index)
    private void textClickListener(TextView textView, int index) {
        textView.setOnClickListener(view -> viewModel.userChooseWordAtIndex(index, getContext()));
    }

    // Displays word color when user clicks on it.
    // BorderColorModel contains colors and words positions
    private void displayBorderWord(BorderColorModel borderTextColor) {

        String colorBorder = "#80000000";

        if (borderTextColor.getBorderColor() == ColorEnum.GREEN) {    // GREED for correct answer
            colorBorder = "#00FF00";
        }
        if (borderTextColor.getBorderColor() == ColorEnum.RED) {      // RED for wrong answer
            colorBorder = "#FF0000";
        }
        if (borderTextColor.getBorderColor() == ColorEnum.NONE) {     // NONE for color of the word by default (grey)
            colorBorder = "#80000000";
        }

        TextView textView = null;
        if (borderTextColor.getPosition() == 0) {
            textView = wordTopLeft;                                         // Textview is one at the top left
        }
        if (borderTextColor.getPosition() == 1) {
            textView = wordTopRight;                                        // TextView is one at the top right
        }
        if (borderTextColor.getPosition() == 2) {
            textView = wordBottomLeft;                                      // TextView is one at the bottom left
        }
        if (borderTextColor.getPosition() == 3) {
            textView = wordBottomRight;                                     // TextView is one at the bottom right
        }

        textView.setTextColor(Color.parseColor(colorBorder));                // Coloring word of the textView attributed
    }

    // Displays picture (loading by url) using Glide library.
    private void displayPicture(String url, ImageView imageView) {

        Glide.with(this)
                .load(url) // image url
                .override(500, 500) // resizing
                .centerCrop()
                .into(imageView);  // imageview object
    }
}
