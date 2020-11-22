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

// This class displays the view of the game "Find the pictures"
// The user must choose from four images the one that corresponds to the word.

public class FindThePictureFragment extends Fragment {

    private LanguageEnum bundleLanguage;            // Language to learn, chosen in the menu
    private FindThePictureViewModel viewModel;      // ViewModel
    private TextView wordText;                      // Word to find
    private ImageView imageViewTopLeft;             // Image top left
    private ImageView imageViewTopRight;            // Image top right
    private ImageView imageViewBottomLeft;          // Image bottom left
    private ImageView imageViewBottomRight;         // Image bottom right

    // instanciate this fragment
    public static FindThePictureFragment newInstance(LanguageEnum bundleLanguage) {
        FindThePictureFragment findThePictureFragment = new FindThePictureFragment();

        Bundle args = new Bundle();
        args.putSerializable("language", bundleLanguage); // Gets langage from the menu
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
        viewModel.getData(bundleLanguage);  // Gets the list of draws and add the first draw in viewModel.currentModel

        // Displays the current draw (viewModel.currentModel):
        viewModel.currentModel.observe(getViewLifecycleOwner(), model ->
                {
                    wordText.setText(model.getWord());
                    displayPicture(model.getTopLeftPicture(), imageViewTopLeft);
                    displayPicture(model.getTopRightPicture(), imageViewTopRight);
                    displayPicture(model.getBottomLeftPicture(), imageViewBottomLeft);
                    displayPicture(model.getBottomRightPicture(), imageViewBottomRight);
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
        imageClickListener(imageViewTopLeft, 0);
        imageClickListener(imageViewTopRight, 1);
        imageClickListener(imageViewBottomLeft, 2);
        imageClickListener(imageViewBottomRight, 3);

        // When all draws have been played: call gameOver:
        viewModel.isGameOver.observe(getViewLifecycleOwner(), gameOver ->
                {
                    if (gameOver) {
                        gameOver();
                    }
                }
        );

        // When border picture color must change: call displayBorderPicture:
        viewModel.borderPictureColor.observe(getViewLifecycleOwner(), borderPictureColor ->
                {
                    displayBorderPicture(borderPictureColor);
                }
        );

    }

    // When the game is over, gets the score and replace this fragment by ResultGameFragment
    private void gameOver() {

        int score = viewModel.score.getValue();
        Fragment resultGameFragment = ResultGameFragment.newInstance(score);
        Utils.replaceFragmentInGameActivity(getActivity(), resultGameFragment);
    }

    // When an image is clicked, call viewModel.userChoosePictureAtIndex and send him the image position (index)
    private void imageClickListener(ImageView imageView, int index) {
        imageView.setOnClickListener(view -> viewModel.userChoosePictureAtIndex(index, getContext()));
    }

    // Displays border picture when user clicks on it.
    // BorderColorModel contains colors and words positions
    private void displayBorderPicture(BorderColorModel borderPictureColor) {

        String colorBorder = "";

        if (borderPictureColor.getBorderColor() == BorderColorEnum.GREEN) {
            colorBorder = "#0AEA14";                                            // GREED for correct answer
        }
        if (borderPictureColor.getBorderColor() == BorderColorEnum.RED) {
            colorBorder = "#E53935";                                            // RED for wrong answer
        }
        if (borderPictureColor.getBorderColor() == BorderColorEnum.NONE) {
            colorBorder = "#00000000";                                          // By default: color of the background for invisible border
        }

        ImageView imageView = null;
        if (borderPictureColor.getPositionPicture() == 0) {
            imageView = imageViewTopLeft;                                       // imageView is one at the top left
        }
        if (borderPictureColor.getPositionPicture() == 1) {
            imageView = imageViewTopRight;                                      // ImageView is one at the top right
        }
        if (borderPictureColor.getPositionPicture() == 2) {                     // ImageView is one at the bottom left
            imageView = imageViewBottomLeft;
        }
        if (borderPictureColor.getPositionPicture() == 3) {                     // ImageView is one at the bottom right
            imageView = imageViewBottomRight;
        }

        imageView.setBackgroundColor(Color.parseColor(colorBorder));            // Displays border picture for the imageView and color attributed
    }

    // Displays picture (loading by url) in imageView attributed, using Glide library.
    private void displayPicture(String url, ImageView imageView) {

        Glide.with(this)
                .load(url) // image url
                .override(500, 500) // resizing
                .centerCrop()
                .into(imageView);  // imageview object
    }
}
