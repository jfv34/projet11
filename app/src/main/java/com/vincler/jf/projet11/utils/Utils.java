package com.vincler.jf.projet11.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.vincler.jf.projet11.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Utils {
    public static void addFragmentInMenuActivity(FragmentActivity activity, Fragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.activity_main_constraintLayout, fragment).commit();
    }
    public static void addFragmentInGameActivity(FragmentActivity activity, Fragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.activity_game_constraintLayout, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }


    public static void replaceFragmentInMenuActivity(FragmentActivity activity, Fragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_constraintLayout, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }
    public static void replaceFragmentInGameActivity(FragmentActivity activity, Fragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_game_constraintLayout, fragment).addToBackStack(fragment.getClass().getName()).commit();
    }

    public static List<Integer> getListRandom(int maximalNumber) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < maximalNumber; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        return list;
    }

    public static String random(int size) {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        char tempChar;
        for (int i = 0; i < size; i++) {
            tempChar = (char) (generator.nextInt(26) + 65);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }


    public static void toastErrorLoading(Context context) {
        int message = R.string.data_loading_error;
        toast(context, message);
    }

    public static void toast(Context context, int message) {
        Toast toast = Toast.makeText(context, context.getString(message), Toast.LENGTH_LONG);
        toast.show();
    }
}