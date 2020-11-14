package com.vincler.jf.projet11.presentation.menu;

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
import androidx.lifecycle.ViewModelProvider;

import com.vincler.jf.projet11.R;
import com.vincler.jf.projet11.models.LanguageEnum;
import com.vincler.jf.projet11.presentation.gameActivity.GameActivity;
import com.vincler.jf.projet11.presentation.gameActivity.GameActivityDependency;

public class MenuFragment extends Fragment {

    private MenuFragmentViewModel viewModel;
    private ImageView frenchFlagImageView;
    private ImageView englishFlagImageView;
    private ImageView spainFlagImageView;
    private ImageView game1ImageView;
    private TextView game1TextView;
    private ImageView game2ImageView;
    private TextView game2TextView;
    private ImageView game3ImageView;
    private TextView game3TextView;
    private ImageView game4ImageView;
    private TextView game4TextView;

    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MenuFragmentViewModel.class);
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
        gamesSetOnClickListeners();
    }

    private void views(View view) {
        game1ImageView = view.findViewById(R.id.fragment_menu_game1_iv);
        game1TextView = view.findViewById(R.id.fragment_menu_text1_tv);
        game2ImageView = view.findViewById(R.id.fragment_menu_game2_iv);
        game2TextView = view.findViewById(R.id.fragment_menu_text2_tv);
        game3ImageView = view.findViewById(R.id.fragment_menu_game3_iv);
        game3TextView = view.findViewById(R.id.fragment_menu_text3_tv);
        game4ImageView = view.findViewById(R.id.fragment_menu_game4_iv);
        game4TextView = view.findViewById(R.id.fragment_menu_text4_tv);
        frenchFlagImageView = view.findViewById(R.id.fragment_menu_flag_french);
        englishFlagImageView = view.findViewById(R.id.fragment_menu_flag_kingdom);
        spainFlagImageView = view.findViewById(R.id.fragment_menu_flag_spain);
    }

    private void gamesSetOnClickListeners() {
        game1ImageView.setOnClickListener(v -> callGameActivity(1));
        game1TextView.setOnClickListener(v -> callGameActivity(1));
        game2ImageView.setOnClickListener(v -> callGameActivity(2));
        game2TextView.setOnClickListener(v -> callGameActivity(2));
        game3ImageView.setOnClickListener(v -> callGameActivity(3));
        game3TextView.setOnClickListener(v -> callGameActivity(3));
        game4ImageView.setOnClickListener(v -> callGameActivity(4));
        game4TextView.setOnClickListener(v -> callGameActivity(4));

        frenchFlagImageView.setOnClickListener(v -> viewModel.changeLanguage(LanguageEnum.FRENCH));
        englishFlagImageView.setOnClickListener(v -> viewModel.changeLanguage(LanguageEnum.ENGLISH));
        spainFlagImageView.setOnClickListener(v -> viewModel.changeLanguage(LanguageEnum.SPANISH));

        viewModel.isFrench.observe(getViewLifecycleOwner(), isFrench ->
        {
            if (isFrench) {
                changeColorFlag(frenchFlagImageView);
            }
        });
        viewModel.isEnglish.observe(getViewLifecycleOwner(), isEnglish ->
        {
            if (isEnglish) {
                changeColorFlag(englishFlagImageView);
            }
        });
        viewModel.isSpanish.observe(getViewLifecycleOwner(), isSpain ->
        {
            if (isSpain) {
                changeColorFlag(spainFlagImageView);
            }
        });
    }

    private void changeColorFlag(ImageView imageViewClicked) {

        frenchFlagImageView.setColorFilter(flagsColors(0));
        englishFlagImageView.setColorFilter(flagsColors(0));
        spainFlagImageView.setColorFilter(flagsColors(0));
        imageViewClicked.setColorFilter(flagsColors(1));

    }

    private ColorMatrixColorFilter flagsColors(int color) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(color);
        return new ColorMatrixColorFilter(matrix);
    }

    private void callGameActivity(int gameId) {
        GameActivityDependency gameActivityDependency
                = new GameActivityDependency(gameId, viewModel.language.getValue());
        Intent intent = new Intent(getActivity(), GameActivity.class);
        intent.putExtra("values", gameActivityDependency);
        startActivity(intent);
    }
}