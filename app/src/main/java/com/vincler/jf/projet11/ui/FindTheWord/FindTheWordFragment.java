package com.vincler.jf.projet11.ui.FindTheWord;

import android.os.Bundle;
import android.util.Log;
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
        viewModel.englishWord_liveData.observe(getViewLifecycleOwner(),englishWord ->{
            Log.i("tag_word", englishWord);
        });
        viewModel.correctPictureUrl_liveData.observe(getViewLifecycleOwner(),correctPictureUrl ->{
            Log.i("tag_correctPictureUrl", correctPictureUrl);
        });
        viewModel.fakePictureUrl1_liveData.observe(getViewLifecycleOwner(),fakePictureUrl1 ->{
            Log.i("tag_fakePicture_Url1", fakePictureUrl1);
        });

        viewModel.fakePictureUrl2_liveData.observe(getViewLifecycleOwner(),fakePictureUrl2 ->{
            Log.i("tag_fakePicture_Url2", fakePictureUrl2);
        });
        viewModel.fakePictureUrl3_liveData.observe(getViewLifecycleOwner(),fakePictureUrl3 ->{
            Log.i("tag_fakePicture_Url3", fakePictureUrl3);
        });







    }
}
