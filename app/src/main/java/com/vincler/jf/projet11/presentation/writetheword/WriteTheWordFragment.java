package com.vincler.jf.projet11.presentation.writetheword;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.vincler.jf.projet11.R;
import com.vincler.jf.projet11.models.BorderColorEnum;
import com.vincler.jf.projet11.presentation.gameActivity.GameActivityDependency;
import com.vincler.jf.projet11.presentation.resultGame.ResultGameFragment;
import com.vincler.jf.projet11.utils.Utils;

public class WriteTheWordFragment extends Fragment {

    private GameActivityDependency bundleGameActivityDependency;
    private WriteTheWordViewModel viewModel;
    private ImageView pictureImageView;
    private EditText wordET;
    private TextView correctWordTV;
    private TextView firstLetterTV;
    private ExtendedFloatingActionButton validateFab;

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
        wordET = root.findViewById(R.id.fragment_writetheword_textInputEditText);
        correctWordTV = root.findViewById(R.id.fragment_writetheword_correctWord_tv);
        firstLetterTV = root.findViewById(R.id.fragment_writetheword_firstLetter_TV);
        validateFab = root.findViewById(R.id.fragment_writetheword_validate_fab);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(WriteTheWordViewModel.class);
        viewModel.getData(bundleGameActivityDependency.getLanguage());
        if (bundleGameActivityDependency.getGameId() == 3) {
            wordET.setGravity(Gravity.START);
        } else wordET.setGravity(Gravity.CENTER_HORIZONTAL);

        viewModel.currentModel.observe(getViewLifecycleOwner(), model ->
                {
                    displayPicture(model.getPicture(), pictureImageView);
                    correctWordTV.setText("");
                    wordET.getText().clear();
                    if (bundleGameActivityDependency.getGameId() == 3) {
                        displayFistLetter();
                    } else {
                        wordET.setHint("Write the word");
                    }
                }
        );

        viewModel.isGameOver.observe(getViewLifecycleOwner(), gameOver ->
                {
                    if (gameOver) {
                        gameOver();
                    }
                }
        );

        validateFab.setOnClickListener(view1 -> {
            viewModel.userValidateWord(wordET.getText().toString(), bundleGameActivityDependency.getGameId());
        });

        viewModel.borderWordColor.observe(getViewLifecycleOwner(), this::displayBorderWord
        );
        viewModel.isIncorrectAnswer.observe(getViewLifecycleOwner(), isIncorrectAnswer ->
                {
                    if (isIncorrectAnswer) {
                        displayCorrectWord();
                    }
                }
        );
    }

    private void displayFistLetter() {
        if(viewModel.currentModel.getValue()!=null){

        firstLetterTV.setText(viewModel.currentModel.getValue().getWord().toUpperCase().substring(0,1));}
    }

    private void displayCorrectWord() {
        correctWordTV.setText(viewModel.currentModel.getValue().getWord().toUpperCase());
    }

    private void displayBorderWord(BorderColorEnum borderWordColor) {
        String colorBorder = "";

        if (borderWordColor == BorderColorEnum.GREEN) {
            colorBorder = "#0AEA14";
        }
        if (borderWordColor == BorderColorEnum.RED) {
            colorBorder = "#E53935";
        }
        if (borderWordColor == BorderColorEnum.TRANSPARENT) {
            colorBorder = "#00000000";
        }

        wordET.setBackgroundColor(Color.parseColor(colorBorder));
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

