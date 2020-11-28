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
import com.vincler.jf.projet11.models.ColorEnum;
import com.vincler.jf.projet11.models.ColorModel;
import com.vincler.jf.projet11.models.LanguageEnum;
import com.vincler.jf.projet11.presentation.resultGame.ResultGameFragment;
import com.vincler.jf.projet11.utils.Utils;

// This class displays the view of the game "Find the pictures"
// The user must choose from four images the one that corresponds to the word.

public class FindThePictureFragment extends Fragment {

    private LanguageEnum bundleLanguage;
    private FindThePictureViewModel viewModel;
    private TextView wordText;
    private ImageView imageViewTopLeft;
    private ImageView imageViewTopRight;
    private ImageView imageViewBottomLeft;
    private ImageView imageViewBottomRight;

    // instantiate this fragment
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
        // Gets the list of draws and add the first draw in viewModel.currentModel
        viewModel.getData(bundleLanguage);

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
    private void displayBorderPicture(ColorModel borderPictureColor) {

        String colorBorder = "";

        if (borderPictureColor.getColorEnum() == ColorEnum.GREEN) {
            colorBorder = "#0AEA14";
        }
        if (borderPictureColor.getColorEnum() == ColorEnum.RED) {
            colorBorder = "#E53935";
        }
        if (borderPictureColor.getColorEnum() == ColorEnum.NONE) {
            colorBorder = "#00000000";
        }

        ImageView imageView = null;
        if (borderPictureColor.getPosition() == 0) {
            imageView = imageViewTopLeft;
        }
        if (borderPictureColor.getPosition() == 1) {
            imageView = imageViewTopRight;
        }
        if (borderPictureColor.getPosition() == 2) {
            imageView = imageViewBottomLeft;
        }
        if (borderPictureColor.getPosition() == 3) {
            imageView = imageViewBottomRight;
        }

        imageView.setBackgroundColor(Color.parseColor(colorBorder));
    }

    // Displays picture (loading by url) in imageView attributed, using Glide library.
    private void displayPicture(String url, ImageView imageView) {

        Glide.with(this)
                .load(url)
                .override(500, 500)
                .centerCrop()
                .into(imageView);
    }
}
