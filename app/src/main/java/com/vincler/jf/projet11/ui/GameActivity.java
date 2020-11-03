package com.vincler.jf.projet11.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.vincler.jf.projet11.models.GameActivityDependency;

public class GameActivity extends Activity {
    private GameActivityDependency gameActivityDependency;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            gameActivityDependency = (GameActivityDependency) getIntent().getSerializableExtra("values");
        }
        Log.i("result1",gameActivityDependency.getLanguage());
        Log.i("result2", String.valueOf(gameActivityDependency.getGameId()));
    }
}