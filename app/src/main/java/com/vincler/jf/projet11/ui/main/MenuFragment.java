package com.vincler.jf.projet11.ui.main;

import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vincler.jf.projet11.R;
import com.vincler.jf.projet11.models.GameActivityDependency;
import com.vincler.jf.projet11.ui.GameActivity;
import com.vincler.jf.projet11.ui.findTheWord.FindTheWordFragment;
import com.vincler.jf.projet11.utils.Utils;

public class MenuFragment extends Fragment {

    private String language = "";
    private ColorMatrixColorFilter filterBlackAndWhite;
    private ColorMatrixColorFilter filterColor;
    private ImageView frenchFlagImageView;
    private ImageView kingdomFlagImageView;
    private ImageView spainFlagImageView;
    private ImageView game1ImageView;
    private TextView game1TextView;
    private ImageView game2ImageView;
    private TextView game2TextView;

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        views(view);
        setOnClickListeners();
        flagsColors();
    }

    private void views(View view) {
        game1ImageView = view.findViewById(R.id.fragment_menu_game1_iv);
        game1TextView = view.findViewById(R.id.fragment_menu_text1_tv);
        game2ImageView = view.findViewById(R.id.fragment_menu_game2_iv);
        game2TextView = view.findViewById(R.id.fragment_menu_text2_tv);
        frenchFlagImageView = view.findViewById(R.id.fragment_menu_flag_french);
        kingdomFlagImageView = view.findViewById(R.id.fragment_menu_flag_kingdom);
        spainFlagImageView = view.findViewById(R.id.fragment_menu_flag_spain);
    }

    private void setOnClickListeners() {
        game1ImageView.setOnClickListener(view1 -> callGameActivity(1));
        game1TextView.setOnClickListener(view1 -> callGameActivity(1));

        game2ImageView.setOnClickListener(view1 -> callGameActivity(2));
        game2TextView.setOnClickListener(view1 -> callGameActivity(2));

        frenchFlagImageView.setOnClickListener(view -> {
            changeColorFlag(frenchFlagImageView);
            language = "0";
        });
        kingdomFlagImageView.setOnClickListener(view -> {
            changeColorFlag(kingdomFlagImageView);
            language = "1";
        });
        spainFlagImageView.setOnClickListener(view -> {
            changeColorFlag(spainFlagImageView);
            language = "2";
        });
    }

    private void changeColorFlag(ImageView imageViewClicked) {

        if (imageViewClicked.getColorFilter() == filterBlackAndWhite) {
            frenchFlagImageView.setColorFilter(filterBlackAndWhite);
            kingdomFlagImageView.setColorFilter(filterBlackAndWhite);
            spainFlagImageView.setColorFilter(filterBlackAndWhite);
            imageViewClicked.setColorFilter(filterColor);
        }
    }

    private void flagsColors() {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        filterBlackAndWhite = new ColorMatrixColorFilter(matrix);
        matrix.setSaturation(1);
        filterColor = new ColorMatrixColorFilter(matrix);
        frenchFlagImageView.setColorFilter(filterBlackAndWhite);
        kingdomFlagImageView.setColorFilter(filterColor);
        spainFlagImageView.setColorFilter(filterBlackAndWhite);
    }


/*    private void callFragmentGame1() {
        Fragment findThePictureFragment = FindThePictureFragment.newInstance(language);
        Utils.replaceFragmentInMainActivity(getActivity(), findThePictureFragment);
    }*/

    private void callFragmentGame2() {
        Fragment findTheWordFragment = FindTheWordFragment.newInstance(language);
        Utils.replaceFragmentInMainActivity(getActivity(), findTheWordFragment);
    }

    private void callGameActivity(int gameId) {
        GameActivityDependency gameActivityDependency
                = new GameActivityDependency(gameId, language);
        Intent intent = new Intent(getActivity(), GameActivity.class);
        intent.putExtra("values", gameActivityDependency);
        startActivity(intent);
    }
}