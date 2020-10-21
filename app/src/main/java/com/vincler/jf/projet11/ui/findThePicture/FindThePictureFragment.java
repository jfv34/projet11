package com.vincler.jf.projet11.ui.findThePicture;

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

public class FindThePictureFragment extends Fragment {
    FindThePictureViewModel viewModel;
    TextView wordText;
    ImageView imageViewTopLeft;
    ImageView imageViewTopRight;
    ImageView imageViewBottomLeft;
    ImageView imageViewBottomRight;

    public static FindThePictureFragment newInstance() {
        return new FindThePictureFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_findthepicture, container, false);
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

        viewModel.getData();

        viewModel.currentModel.observe(getViewLifecycleOwner(), model->
                {
                    wordText.setText(model.getWord());
                    displayPicture(model.getTopLeftPicture(), imageViewTopLeft);
                    displayPicture(model.getTopRightPicture(), imageViewTopRight);
                    displayPicture(model.getBottomLeftPicture(), imageViewBottomLeft);
                    displayPicture(model.getBottomRightPicture(), imageViewBottomRight);
                }
        );

        imageClickListener(imageViewTopLeft, 0);
        imageClickListener(imageViewTopRight, 1);
        imageClickListener(imageViewBottomLeft, 2);
        imageClickListener(imageViewBottomRight, 3);

        viewModel.isGameOver.observe(getViewLifecycleOwner(), gameOver ->
                {
                    if (gameOver) {
                        gameOver();
                    }
                    ;
                }
        );

    }

    private void gameOver() {
        // todo...

    }

    private void imageClickListener(ImageView imageView, int draw) {
        imageView.setOnClickListener(view -> viewModel.userChoosePictureAtIndex(draw));
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
