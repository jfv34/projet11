package com.vincler.jf.projet11.ui.FindTheWord;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.vincler.jf.projet11.R;
import com.vincler.jf.projet11.api.GetData;

public class FindTheWordFragment extends Fragment {
    public static FindTheWordFragment newInstance() {
        return new FindTheWordFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_findtheword, container, false);
        return root;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FindTheWordViewModel viewModel = new ViewModelProvider(this).get(FindTheWordViewModel.class);

        viewModel.getRandomWord();




    }
}
