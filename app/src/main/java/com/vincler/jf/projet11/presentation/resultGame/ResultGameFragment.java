package com.vincler.jf.projet11.presentation.resultGame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.vincler.jf.projet11.R;
import com.vincler.jf.projet11.utils.Utils;

// This fragment display the score after the end of the game
public class ResultGameFragment extends Fragment {

    TextView textview1;
    TextView textview2;
    NumberProgressBar progressBar;
    ExtendedFloatingActionButton returnToMenuFab;
    private int bundleScore;

    // instantiate this fragment
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
        textview1 = root.findViewById(R.id.fragment_result_game_1_textview);
        textview2 = root.findViewById(R.id.fragment_result_game_2_textview);
        progressBar = root.findViewById(R.id.fragment_result_game_progressbar);
        returnToMenuFab = root.findViewById(R.id.fragment_result_game_return_menu_fab);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textview1.setText(R.string.yourfinalscoreis);
        textview2.setText(new StringBuilder()
                .append(bundleScore)
                .append(" / ")
                .append(Utils.getPrefs(getContext(),"drawsPerGame",7))
                .toString());
        progressBar.setProgressTextSize(60);
        progressBar.setUnreachedBarHeight(10);
        progressBar.setReachedBarHeight(15);
        progressBar.setMax(Utils.getPrefs(getContext(),"drawsPerGame",7));
        progressBar.setProgress(bundleScore);

        returnToMenuFab.setOnClickListener(view1 -> {
            getActivity().finish();
        });
    }
}
