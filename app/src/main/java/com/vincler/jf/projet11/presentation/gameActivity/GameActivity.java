package com.vincler.jf.projet11.presentation.gameActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.vincler.jf.projet11.R;
import com.vincler.jf.projet11.presentation.findThePicture.FindThePictureFragment;
import com.vincler.jf.projet11.presentation.findTheWord.FindTheWordFragment;
import com.vincler.jf.projet11.presentation.writetheword.WriteTheWordFragment;
import com.vincler.jf.projet11.utils.Utils;

public class GameActivity extends AppCompatActivity {
    // Langage and gameId chosen by the user
    private GameActivityDependency gameActivityDependency;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        Bundle extras = getIntent().getExtras();
        if (extras != null && savedInstanceState==null) {
            gameActivityDependency = (GameActivityDependency) getIntent().getSerializableExtra("values");

            switch (gameActivityDependency.getGameId()) {
                case 1:
                    // Game "find the picture"
                    callFragmentGame1();
                    break;
                case 2:
                    // Game "find the word"
                    callFragmentGame2();
                    break;
                case 3:
                    // Game "write the word with first letter"
                case 4:
                    // Game "write the word without first letter"
                    // The same fragment for games 3 and 4
                    callFragmentGame3_or_4();
            }
        }
    }

    // Game "find the picture"
    private void callFragmentGame1() {
        Fragment findThePictureFragment = FindThePictureFragment.newInstance(gameActivityDependency.getLanguage());
        Utils.addFragmentInGameActivity(this, findThePictureFragment);
    }

    // Game "find the word"
    private void callFragmentGame2() {
        Fragment findTheWordFragment = FindTheWordFragment.newInstance(gameActivityDependency.getLanguage());
        Utils.addFragmentInGameActivity(this, findTheWordFragment);
    }

    // Game "write the word with first letter" or "write the word without first letter"
    // Same fragment for the two games
    private void callFragmentGame3_or_4() {
        Fragment writeTheWordFragment = WriteTheWordFragment.newInstance(gameActivityDependency);
        Utils.addFragmentInGameActivity(this, writeTheWordFragment);
    }
}