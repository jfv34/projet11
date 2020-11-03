package com.vincler.jf.projet11.presentation.findThePicture;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vincler.jf.projet11.models.BorderColorList;
import com.vincler.jf.projet11.models.BorderColorModel;
import com.vincler.jf.projet11.models.Constants;
import com.vincler.jf.projet11.models.FindThePictureModel;
import com.vincler.jf.projet11.repositories.FindThePictureRepository;
import com.vincler.jf.projet11.repositories.Result;

import java.util.ArrayList;
import java.util.List;
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

        FindThePictureRepository.getFindThePictureList(
                new Result<List<FindThePictureModel>>() {
                    @Override
                    public void onResult(List<FindThePictureModel> result) {
                        if (result != null && result.size() != 0 && result.get(0) != null) {
                            findThePictureList.clear();
                            findThePictureList.addAll(result);
                            currentModel.postValue(result.get(0));
                            draw.postValue(0);
                            isGameOver.postValue(false);
                            score.postValue(0);
                        }
                        else {// todo
                            ;}
                    }

                    @Override
                    public void onError() {
                        Log.i("error","error findthepicture");
                    }
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
        }, Constants.DELAY_BETWEEN_DRAWS);
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