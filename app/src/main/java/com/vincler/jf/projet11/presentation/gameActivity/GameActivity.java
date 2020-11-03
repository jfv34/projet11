package com.vincler.jf.projet11.presentation.gameActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.vincler.jf.projet11.R;
import com.vincler.jf.projet11.presentation.findThePicture.FindThePictureFragment;
import com.vincler.jf.projet11.presentation.findTheWord.FindTheWordFragment;
import com.vincler.jf.projet11.utils.Utils;

public class GameActivity extends AppCompatActivity {
    private GameActivityDependency gameActivityDependency;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            gameActivityDependency = (GameActivityDependency) getIntent().getSerializableExtra("values");

            switch (gameActivityDependency.getGameId()) {
                case 1:
                    callFragmentGame1();
                    break;
                case 2:
                    callFragmentGame2();
                    ;
            }
        }
    }

    private void callFragmentGame1() {
        Fragment findThePictureFragment = FindThePictureFragment.newInstance(gameActivityDependency.getLanguage());
        Utils.addFragmentInGameActivity(this, findThePictureFragment);
    }

    private void callFragmentGame2() {
        Fragment findTheWordFragment = FindTheWordFragment.newInstance(gameActivityDependency.getLanguage());
        Utils.addFragmentInGameActivity(this, findTheWordFragment);
    }

}