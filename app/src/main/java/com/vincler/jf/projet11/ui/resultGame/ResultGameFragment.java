package com.vincler.jf.projet11.ui.resultGame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vincler.jf.projet11.R;

public class ResultGameFragment extends Fragment {

    TextView textview;
    private int bundleScore;

    public static ResultGameFragment newInstance(int bundleScore) {
        ResultGameFragment resultGameFragment = new ResultGameFragment();
        Bundle args = new Bundle();
        args.putInt("score", bundleScore);
        resultGameFragment.setArguments(args);

        return resultGameFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundleScore = getArguments().getInt("score", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_result_game, container, false);
        textview = root.findViewById(R.id.fragment_result_game_textview);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textview.setText("Votre score final est de " + bundleScore);

    }
}
