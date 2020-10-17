package com.vincler.jf.projet11.ui.findTheWord;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.vincler.jf.projet11.R;

public class FindTheWordFragment extends Fragment {
    TextView wordText;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;

    public static FindTheWordFragment newInstance() {
        return new FindTheWordFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_findtheword, container, false);
        wordText = root.findViewById(R.id.fragment_findtheword_word_text);
        imageView1 = root.findViewById(R.id.fragment_findtheword_imageView_1);
        imageView2 = root.findViewById(R.id.fragment_findtheword_imageView_2);
        imageView3 = root.findViewById(R.id.fragment_findtheword_imageView_3);
        imageView4 = root.findViewById(R.id.fragment_findtheword_imageView_4);
        return root;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FindTheWordViewModel viewModel = new ViewModelProvider(this).get(FindTheWordViewModel.class);

        viewModel.getData();
        viewModel.word_liveData.observe(getViewLifecycleOwner(), englishWord ->{
            wordText.setText(englishWord);
        });
        viewModel.topLeftPictureLiveData.observe(getViewLifecycleOwner(), correctPictureUrl -> {
            Log.i("tag_correctPictureUrl", correctPictureUrl);
            displayPicture(correctPictureUrl, imageView1);

        });
        viewModel.topRightPictureLiveData.observe(getViewLifecycleOwner(), fakePictureUrl1 ->{
            Log.i("tag_fakePicture_Url1", fakePictureUrl1);
            displayPicture(fakePictureUrl1, imageView2);
        });

        viewModel.bottomLeftPictureLiveData.observe(getViewLifecycleOwner(), fakePictureUrl2 ->{
            Log.i("tag_fakePicture_Url2", fakePictureUrl2);
            displayPicture(fakePictureUrl2, imageView3);
        });
        viewModel.bottomRightPictureLiveData.observe(getViewLifecycleOwner(), fakePictureUrl3 ->{
            Log.i("tag_fakePicture_Url3", fakePictureUrl3);
            displayPicture(fakePictureUrl3, imageView4);
        });
    }

    private void displayPicture(String url, ImageView imageView) {
        Glide.with(this)
                .load(url) // image url
                //.placeholder(R.drawable.placeholder) // any placeholder to load at start
                //.error(R.drawable.imagenotfound)  // any image in case of error
                .override(100, 100) // resizing
                .centerCrop()
                .into(imageView);  // imageview object
    }
}
