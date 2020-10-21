package com.vincler.jf.projet11.utils;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.vincler.jf.projet11.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {
    public static void addFragment(FragmentActivity activity, Fragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.activity_main_constraintLayout, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }

    public static void replaceFragment(FragmentActivity activity, Fragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_constraintLayout, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }

    public static List<Integer> getListRandom(int maximalNumber) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < maximalNumber; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
return list;
    }
}
