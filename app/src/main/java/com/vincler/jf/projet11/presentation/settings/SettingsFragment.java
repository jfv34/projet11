package com.vincler.jf.projet11.presentation.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;

import com.vincler.jf.projet11.R;
import com.vincler.jf.projet11.models.Constants;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                Constants.SHAREDPREFERENCES_SETTINGS, Context.MODE_PRIVATE);
        SeekBarPreference seekBarPreference = getPreferenceScreen().findPreference("drawsPerGame");
        seekBarPreference.setMin(4);
        seekBarPreference.setDefaultValue(7);
        if (seekBarPreference != null) {
            seekBarPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                        sharedPref.edit().putInt("drawsPerGame", (int) newValue).apply();
                        return true;
                    }
            );
        }
    }
}