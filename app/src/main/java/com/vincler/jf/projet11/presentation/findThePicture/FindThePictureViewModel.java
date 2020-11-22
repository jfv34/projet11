package com.vincler.jf.projet11.presentation.findThePicture;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vincler.jf.projet11.models.ColorEnum;
import com.vincler.jf.projet11.models.BorderColorModel;
import com.vincler.jf.projet11.models.FindThePictureModel;
import com.vincler.jf.projet11.models.LanguageEnum;
import com.vincler.jf.projet11.repositories.FindThePictureRepository;
import com.vincler.jf.projet11.repositories.Result;
import com.vincler.jf.projet11.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FindThePictureViewModel extends ViewModel {

    ArrayList<FindThePictureModel> findThePictureList = new ArrayList<>();
    MutableLiveData<FindThePictureModel> currentModel = new MutableLiveData<>();
    MutableLiveData<Integer> draw = new MutableLiveData<>();
    MutableLiveData<Boolean> isGameOver = new MutableLiveData<>();
    MutableLiveData<Boolean> isErrorLoading = new MutableLiveData<>();
    MutableLiveData<Integer> score = new MutableLiveData<>();
    MutableLiveData<BorderColorModel> borderPictureColor = new MutableLiveData<>();

    public void getData(LanguageEnum language) {
       if(findThePictureList.isEmpty()){draw.postValue(0);
        isGameOver.postValue(false);
        score.postValue(0);

        FindThePictureRepository.getFindThePictureList(
                new Result<List<FindThePictureModel>>() {
                    @Override
                    public void onResult(List<FindThePictureModel> result) {
                        if (result != null && result.size() != 0 && result.get(0) != null) {
                            findThePictureList.clear();
                            findThePictureList.addAll(result);
                            currentModel.postValue(result.get(0));
                        }
                        else {
                            isErrorLoading.postValue(true);
                            ;}
                    }

                    @Override
                    public void onError() {
                        isErrorLoading.postValue(true);
                    }
                }, language);
    }}

    public void userChoosePictureAtIndex(int index,Context context) {

        boolean iscorrectPosition = currentModel.getValue().getCorrectPicturePosition() == index;

        if (iscorrectPosition) {
            int newScore = score.getValue() + 1;
            score.postValue(newScore);
            changeBorderPictureColor(ColorEnum.GREEN, index);
        }
        if (!iscorrectPosition) {
            changeBorderPictureColor(ColorEnum.RED, index);
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                goToTheNextDraw(index, context);
            }
        }, Utils.getPrefs(context, "delay",1500));
    }

    private void changeBorderPictureColor(ColorEnum borderColorList, int index) {

        BorderColorModel newBorderPictureColor = new BorderColorModel(borderColorList, index);
        borderPictureColor.postValue(newBorderPictureColor);
    }

    private void goToTheNextDraw(int index, Context context) {

        changeBorderPictureColor(ColorEnum.NONE, index);
        int newDraw = draw.getValue() + 1;
        int numberOfDraw = Utils.getPrefs(context, "drawsPerGame",7);
        if (newDraw < numberOfDraw) {
            currentModel.postValue(findThePictureList.get(newDraw));
            draw.postValue(newDraw);
        } else isGameOver.postValue(true);
    }
}