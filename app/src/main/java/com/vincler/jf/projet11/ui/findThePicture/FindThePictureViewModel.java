package com.vincler.jf.projet11.ui.findThePicture;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vincler.jf.projet11.models.FindThePictureModel;
import com.vincler.jf.projet11.models.FindThePictureResultModel;
import com.vincler.jf.projet11.repositories.PicturesRepository;

import java.util.ArrayList;

public class FindThePictureViewModel extends ViewModel {

    ArrayList<FindThePictureModel> findThePictureList = new ArrayList<>();
    MutableLiveData<FindThePictureModel> currentModel = new MutableLiveData<>();
    MutableLiveData<FindThePictureResultModel> result = new MutableLiveData<>();

    public void getData() {

        PicturesRepository.getPictures(result -> {
            findThePictureList.clear();
            findThePictureList.addAll(result);
            currentModel.postValue(result.get(0));
        });
    }

    public void userChoosePictureAtIndex(int index) {
        FindThePictureModel findThePicture = findThePictureList.get(index);
        boolean iscorrectPosition = currentModel.getValue().getCorrectPicturePosition() == index;
        if (iscorrectPosition) {
            result.postValue(FindThePictureResultModel.WON);
        } else {
            result.postValue(FindThePictureResultModel.LOST);
        }
    }
}