package com.vincler.jf.projet11.presentation.resultGame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.vincler.jf.projet11.R;

public class ShareScoreFragment extends Fragment {

    ExtendedFloatingActionButton returnToMenuFab;

    // instantiate this fragment
    public static ShareScoreFragment newInstance() {
        return new ShareScoreFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_share_score, container, false);
        returnToMenuFab = root.findViewById(R.id.fragment_share_score_return_menu_fab);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        returnToMenuFab.setOnClickListener(view1 -> {
            getActivity().finish();
        });
    }
}
