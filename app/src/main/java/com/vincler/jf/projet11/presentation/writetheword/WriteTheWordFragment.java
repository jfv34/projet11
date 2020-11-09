package com.vincler.jf.projet11.presentation.writetheword;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.vincler.jf.projet11.R;
import com.vincler.jf.projet11.presentation.findThePicture.FindThePictureViewModel;
import com.vincler.jf.projet11.presentation.gameActivity.GameActivityDependency;
import com.vincler.jf.projet11.presentation.resultGame.ResultGameFragment;
import com.vincler.jf.projet11.utils.Utils;

public class WriteTheWordFragment extends Fragment {

    private GameActivityDependency bundleGameActivityDependency;
    private WriteTheWordViewModel viewModel;
    private ImageView pictureImageView;

    public static WriteTheWordFragment newInstance(GameActivityDependency bundleGameActivityDependency) {
        WriteTheWordFragment writeTheWordFragment = new WriteTheWordFragment();

        Bundle args = new Bundle();
        args.putSerializable("value", bundleGameActivityDependency);
        writeTheWordFragment.setArguments(args);
        return writeTheWordFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_writetheword, container, false);
        bundleGameActivityDependency = (GameActivityDependency) getArguments().getSerializable("value");

        pictureImageView = root.findViewById(R.id.fragment_writetheword_imageView);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(WriteTheWordViewModel.class);
        viewModel.getData(bundleGameActivityDependency.getLanguage());

        viewModel.currentModel.observe(getViewLifecycleOwner(),model ->
                {
                    displayPicture(model.getPicture(),pictureImageView);
                    ;
                }
                );

        viewModel.isGameOver.observe(getViewLifecycleOwner(), gameOver ->
                {
                    if (gameOver) {
                        gameOver();
                    }
                }
        );
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

    private void gameOver() {

        int score = viewModel.score.getValue();
        Fragment resultGameFragment = ResultGameFragment.newInstance(score);
        Utils.replaceFragmentInGameActivity(getActivity(), resultGameFragment);
    }
}

