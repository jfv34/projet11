package com.vincler.jf.projet11.ui.FindTheWord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.vincler.jf.projet11.R;

public class FindTheWordFragment extends Fragment {
    public static FindTheWordFragment newInstance() {
        return new FindTheWordFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_findtheword, container, false);
        return root;
    }
}
