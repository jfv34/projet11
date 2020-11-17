package com.vincler.jf.projet11.presentation.findTheWord;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vincler.jf.projet11.models.BorderColorEnum;
import com.vincler.jf.projet11.models.BorderColorModel;
import com.vincler.jf.projet11.models.FindTheWordModel;
import com.vincler.jf.projet11.models.LanguageEnum;
import com.vincler.jf.projet11.repositories.FindTheWordRepository;
import com.vincler.jf.projet11.repositories.Result;
import com.vincler.jf.projet11.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FindTheWordViewModel extends ViewModel {

    ArrayList<FindTheWordModel> findTheWordList = new ArrayList<>();
    MutableLiveData<FindTheWordModel> currentModel = new MutableLiveData<>();
    MutableLiveData<Integer> draw = new MutableLiveData<>();
    MutableLiveData<Boolean> isGameOver = new MutableLiveData<>();
    MutableLiveData<Boolean> isErrorLoading = new MutableLiveData<>();
    MutableLiveData<Integer> score = new MutableLiveData<>();
    MutableLiveData<BorderColorModel> borderWordColor = new MutableLiveData<>();

    public void getData(LanguageEnum language) {
        draw.postValue(0);
        isGameOver.postValue(false);
        score.postValue(0);

        FindTheWordRepository.getFindTheWordList(
                new Result<List<FindTheWordModel>>() {
                    @Override
                    public void onResult(List<FindTheWordModel> result) {
                        if (result != null && result.size() != 0 && result.get(0) != null) {
                            findTheWordList.clear();
                            findTheWordList.addAll(result);
                            currentModel.postValue(result.get(0));
                        } else {
                            isErrorLoading.postValue(true);
                        }
                    }

                    @Override
                    public void onError() {
                        isErrorLoading.postValue(true);

                    }
                }, language);
    }

    public void userChooseWordAtIndex(int index, Context context) {

        boolean iscorrectPosition = currentModel.getValue().getCorrectWordPosition() == index;

        if (iscorrectPosition) {
            int newScore = score.getValue() + 1;
            score.postValue(newScore);
            changeBorderWordColor(BorderColorEnum.GREEN, index);
        }
        if (!iscorrectPosition) {
            changeBorderWordColor(BorderColorEnum.RED, index);
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                goToTheNextDraw(index, context);
            }
        }, Utils.getPrefs(context,"delay",1500));
    }

    private void changeBorderWordColor(BorderColorEnum borderColorList, int index) {

        BorderColorModel newBorderWordColor = new BorderColorModel(borderColorList, index);
        borderWordColor.postValue(newBorderWordColor);
    }

    private void goToTheNextDraw(int index, Context context) {

        changeBorderWordColor(BorderColorEnum.NONE, index);
        int newDraw = draw.getValue() + 1;
        int numberOfDraw = Utils.getPrefs(context,"drawsPerGame",7);
        if (newDraw < numberOfDraw) {
            currentModel.postValue(findTheWordList.get(newDraw));
            draw.postValue(newDraw);
        } else isGameOver.postValue(true);
    }
}