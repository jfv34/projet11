package com.vincler.jf.projet11.ui.findThePicture;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vincler.jf.projet11.models.Constants;
import com.vincler.jf.projet11.models.FindThePictureModel;
import com.vincler.jf.projet11.models.FindThePictureResultModel;
import com.vincler.jf.projet11.repositories.FindThePictureRepository;

import java.util.ArrayList;

public class FindThePictureViewModel extends ViewModel {

    ArrayList<FindThePictureModel> findThePictureList = new ArrayList<>();
    MutableLiveData<FindThePictureModel> currentModel = new MutableLiveData<>();
    MutableLiveData<FindThePictureResultModel> result = new MutableLiveData<>();
    MutableLiveData<Integer> draw = new MutableLiveData<>();
    MutableLiveData<Boolean> isGameOver = new MutableLiveData<>();
    MutableLiveData<Integer> score = new MutableLiveData<>();

    public void getData() {

        FindThePictureRepository.getFindThePictureList(result -> {
            findThePictureList.clear();
            findThePictureList.addAll(result);
            currentModel.postValue(result.get(0));
            draw.postValue(0);
            isGameOver.postValue(false);
            score.postValue(0);
        });
    }

    public void userChoosePictureAtIndex(int index) {
        FindThePictureModel findThePicture = findThePictureList.get(index);
        boolean iscorrectPosition = currentModel.getValue().getCorrectPicturePosition() == index;
        if (iscorrectPosition) {
            result.postValue(FindThePictureResultModel.WIN);
            int newScore = score.getValue() + 1;
            score.postValue(newScore);
            Log.i("tag_result", "win");
        } else {
            result.postValue(FindThePictureResultModel.LOST);
            Log.i("tag_result", "lost");
        }

        int newDraw = draw.getValue() + 1;
        if (newDraw < Constants.NUMBER_OF_DRAWS) {
            currentModel.postValue(findThePictureList.get(newDraw));
            draw.postValue(newDraw);
        }
        else isGameOver.postValue(true);
    }
}