package com.vincler.jf.projet11.presentation.writetheword;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.vincler.jf.projet11.R;
import com.vincler.jf.projet11.presentation.findThePicture.FindThePictureViewModel;
import com.vincler.jf.projet11.presentation.gameActivity.GameActivityDependency;
import com.vincler.jf.projet11.presentation.resultGame.ResultGameFragment;
import com.vincler.jf.projet11.utils.Utils;

public class WriteTheWordFragment extends Fragment {

    private GameActivityDependency bundleGameActivityDependency;
    private WriteTheWordViewModel viewModel;

    public static WriteTheWordFragment newInstance(GameActivityDependency bundleGameActivityDependency) {
        WriteTheWordFragment writeTheWordFragment = new WriteTheWordFragment();

        Bundle args = new Bundle();
        args.putSerializable("value", bundleGameActivityDependency);
        writeTheWordFragment.setArguments(args);
        return writeTheWordFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_findthepicture, container, false);
        bundleGameActivityDependency = (GameActivityDependency) getArguments().getSerializable("value");
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(WriteTheWordViewModel.class);
        viewModel.getData(bundleGameActivityDependency.getLanguage());

        viewModel.isGameOver.observe(getViewLifecycleOwner(), gameOver ->
                {
                    if (gameOver) {
                        gameOver();
                    }
                }
        );
    }

    private void gameOver() {

        int score = viewModel.score.getValue();
        Fragment resultGameFragment = ResultGameFragment.newInstance(score);
        Utils.replaceFragmentInGameActivity(getActivity(), resultGameFragment);
    }
}

