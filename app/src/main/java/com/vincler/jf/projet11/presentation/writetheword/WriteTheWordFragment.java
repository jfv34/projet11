package com.vincler.jf.projet11.presentation.writetheword;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
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
import com.vincler.jf.projet11.models.ColorEnum;
import com.vincler.jf.projet11.presentation.gameActivity.GameActivityDependency;
import com.vincler.jf.projet11.presentation.resultGame.ResultGameFragment;
import com.vincler.jf.projet11.utils.Utils;

// This class displays the view of the games
// "Write the word with the first letter"
// and "Write the word without the first letter"
// The user must write the words that corresponds to the picture.

public class WriteTheWordFragment extends Fragment {

    private GameActivityDependency bundleGameActivityDependency;  // Game and Language to learn, chosen in the menu
    private WriteTheWordViewModel viewModel;                      // ViewModel
    private ImageView pictureImageView;                           // The picture
    private EditText wordET;                                      // To write the word find
    private TextView correctWordTV;                               // To display the correct answer
    private ExtendedFloatingActionButton validateFab;             // User validates his choice

    // instantiate this fragment
    public static WriteTheWordFragment newInstance(GameActivityDependency bundleGameActivityDependency) {
        WriteTheWordFragment writeTheWordFragment = new WriteTheWordFragment();

        Bundle args = new Bundle();
        args.putSerializable("value", bundleGameActivityDependency);  // Gets game and langage from the menu
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
        validateFab = root.findViewById(R.id.fragment_writetheword_validate_fab);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(WriteTheWordViewModel.class);
        viewModel.getData(bundleGameActivityDependency.getLanguage(), getContext());
        if (bundleGameActivityDependency.getGameId() == 3) { // gravity start for word with first letter
            wordET.setGravity(Gravity.START);
        } else
            wordET.setGravity(Gravity.CENTER_HORIZONTAL); // gravity center for word without first letter
        displayBackgroundWord(ColorEnum.NONE);

        // Displays the current draw (viewModel.currentModel):
        viewModel.currentModel.observe(getViewLifecycleOwner(), model ->
                {
                    displayPicture(model.getPicture(), pictureImageView); // displays the picture
                    correctWordTV.setText("");                  // clear the correctWord TextView
                    editTextinAllCaps();                        // EditText in all caps
                    wordET.getText().clear();                   // clear the EditText
                    if (bundleGameActivityDependency.getGameId() == 3) {
                        if (wordET.getText().length() == 0) {
                            displayFistLetter();                // displays first letter for the game 3
                        }

                    } else {
                        wordET.setHint("Write the word");       // displays hint for the game 4
                    }
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

        // When all draws have been played: call gameOver:
        viewModel.isGameOver.observe(getViewLifecycleOwner(), gameOver ->
                {
                    if (gameOver) {
                        gameOver();
                    }
                }
        );

        // When game is the 3, add textChangedListener to always keep displayed the first letter
        if (bundleGameActivityDependency.getGameId() == 3) {
            addTextChangedListener();
        }

        // When user clicks on floating button to validate his word, call viewModel.userValidateWord
        validateFab.setOnClickListener(view1 -> {
            viewModel.userValidateWord(wordET.getText().toString(), bundleGameActivityDependency.getGameId(), getContext());
        });

        // When border picture color must change: call displayBorderWord
        viewModel.borderWordColor.observe(getViewLifecycleOwner(), this::displayBackgroundWord
        );

        // When the answer is wrong, displays the correct word
        viewModel.isIncorrectAnswer.observe(getViewLifecycleOwner(), isIncorrectAnswer ->
                {
                    if (isIncorrectAnswer) {
                        displayCorrectWord();
                    }
                }
        );
    }

    // textChangedListener to always keep displayed the first letter in the game 3
    private void addTextChangedListener() {
        wordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String firstLetter = viewModel.getFirstLetter();
                if (!firstLetter.equals("")) {
                    // if there are the first letter in viewModel...
                    if (wordET.length() > 0) {
                        // if first letter in EditText if wrong, displays the correct first letter
                        String firstLetterInEditText = wordET.getText().toString().substring(0, 1);
                        if (!firstLetterInEditText.equals(firstLetter.toUpperCase())) {
                            displayFistLetter();
                        }
                    } else {
                        // if EditText is empty, displays the first letter
                        displayFistLetter();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    // Displays the user word in all caps
    private void editTextinAllCaps() {
        wordET.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }

    // Displays the first letter of the word
    private void displayFistLetter() {
        String firstLetterInUpperCase = viewModel.getFirstLetter().toUpperCase();
        wordET.setText(firstLetterInUpperCase);
        wordET.setSelection(wordET.getText().length());

    }

    // Displays the correct word
    private void displayCorrectWord() {
        correctWordTV.setText(viewModel.currentModel.getValue().getWord().toUpperCase());
    }

    // Displays the word background when user validate his word
    private void displayBackgroundWord(ColorEnum borderWordColor) {
        String color = "";

        if (borderWordColor == ColorEnum.GREEN) {
            color = "#0AEA14";                          // Background GREEN for a correct word
        }
        if (borderWordColor == ColorEnum.RED) {
            color = "#E53935";                          // Background RED for a wrong word
        }
        if (borderWordColor == ColorEnum.NONE) {
            color = "#E1CDCDCD";                        // Background NONE (grey) before the choice
        }

        wordET.setBackgroundColor(Color.parseColor(color));  // Display background word
    }

    // Displays picture (loading by url) in imageView attributed, using Glide library.
    private void displayPicture(String url, ImageView imageView) {

        Glide.with(this)
                .load(url) // image url
                .override(500, 500) // resizing
                .centerCrop()
                .into(imageView);  // imageview object
    }

    // When the game is over, gets the score and replace this fragment by ResultGameFragment
    private void gameOver() {

        int score = viewModel.score.getValue();
        Fragment resultGameFragment = ResultGameFragment.newInstance(score);
        Utils.replaceFragmentInGameActivity(getActivity(), resultGameFragment);
    }
}

