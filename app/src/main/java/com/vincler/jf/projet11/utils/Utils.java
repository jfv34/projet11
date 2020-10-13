package com.vincler.jf.projet11.utils;

import android.content.res.Configuration;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.vincler.jf.projet11.R;

import java.util.Random;

public class Utils {
    public static void addFragment(FragmentActivity activity, Fragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.activity_main_constraintLayout, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }
}
