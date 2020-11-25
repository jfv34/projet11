package com.vincler.jf.projet11.presentation.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;

import com.vincler.jf.projet11.R;
import com.vincler.jf.projet11.models.Constants;

public class SettingsFragment extends PreferenceFragmentCompat {
    SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        Context context = getActivity();
        sharedPreferences = context.getSharedPreferences(
                Constants.SHAREDPREFERENCES_SETTINGS, Context.MODE_PRIVATE);

        drawPerGamePreference();
        delayPreference();
    }

    // prefences for the delay between two draw
    private void delayPreference() {
        SeekBarPreference delayPreference = getPreferenceScreen().findPreference("delay");
        if (delayPreference != null) {
            delayPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                        sharedPreferences.edit().putInt("delay", (int) newValue).apply();
                        return true;
                    }
            );
        }
    }

    // prefences for the number of draws by game
    private void drawPerGamePreference() {
        SeekBarPreference drawPerGamePreference = getPreferenceScreen().findPreference("drawsPerGame");
        if (drawPerGamePreference != null) {
            drawPerGamePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                        sharedPreferences.edit().putInt("drawsPerGame", (int) newValue).apply();
                        return true;
                    }
            );
        }
    }
}