package com.vincler.jf.projet11.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vincler.jf.projet11.R;
import com.vincler.jf.projet11.ui.findThePicture.FindThePictureFragment;
import com.vincler.jf.projet11.utils.Utils;

public class MenuFragment extends Fragment {

    private ImageView game1ImageView;

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

        game1ImageView = view.findViewById(R.id.fragment_menu_game1_iv);
        game1ImageView.setOnClickListener(view1 -> callFragmentGame1());
    }

    private void callFragmentGame1() {
        Fragment findThePictureFragment = FindThePictureFragment.newInstance();
        Utils.replaceFragmentInMainActivity(getActivity(), findThePictureFragment);
    }
}