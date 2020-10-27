package com.vincler.jf.projet11.ui.findThePicture;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vincler.jf.projet11.models.BorderColorList;
import com.vincler.jf.projet11.models.BorderColorModel;
import com.vincler.jf.projet11.models.Constants;
import com.vincler.jf.projet11.models.FindThePictureModel;
import com.vincler.jf.projet11.repositories.FindThePictureRepository;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class FindThePictureViewModel extends ViewModel {

    ArrayList<FindThePictureModel> findThePictureList = new ArrayList<>();
    MutableLiveData<FindThePictureModel> currentModel = new MutableLiveData<>();
    MutableLiveData<Integer> draw = new MutableLiveData<>();
    MutableLiveData<Boolean> isGameOver = new MutableLiveData<>();
    MutableLiveData<Integer> score = new MutableLiveData<>();
    MutableLiveData<BorderColorModel> borderPictureColor = new MutableLiveData<>();

    public void getData(String language) {
        FindThePictureRepository.getFindThePictureList(result -> {
            findThePictureList.clear();
            findThePictureList.addAll(result);
            currentModel.postValue(result.get(0));
            draw.postValue(0);
            isGameOver.postValue(false);
            score.postValue(0);
        }, language);
    }

    public void userChoosePictureAtIndex(int index) {

        boolean iscorrectPosition = currentModel.getValue().getCorrectPicturePosition() == index;

        if (iscorrectPosition) {
            int newScore = score.getValue() + 1;
            score.postValue(newScore);
            changeBorderPictureColor(BorderColorList.GREEN, index);
        }
        if (!iscorrectPosition) {
            changeBorderPictureColor(BorderColorList.RED, index);
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                goToTheNextDraw(index);
            }
        }, 800);
    }

    private void changeBorderPictureColor(BorderColorList borderColorList, int index) {

        BorderColorModel newBorderPictureColor = new BorderColorModel(borderColorList, index);
        borderPictureColor.postValue(newBorderPictureColor);
    }

    private void goToTheNextDraw(int index) {

        changeBorderPictureColor(BorderColorList.TRANSPARENT, index);
        int newDraw = draw.getValue() + 1;
        if (newDraw < Constants.NUMBER_OF_DRAWS) {
            currentModel.postValue(findThePictureList.get(newDraw));
            draw.postValue(newDraw);
        } else isGameOver.postValue(true);
    }
}