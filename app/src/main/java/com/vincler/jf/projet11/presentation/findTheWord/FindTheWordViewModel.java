package com.vincler.jf.projet11.presentation.findTheWord;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vincler.jf.projet11.models.BorderColorList;
import com.vincler.jf.projet11.models.BorderColorModel;
import com.vincler.jf.projet11.models.Constants;
import com.vincler.jf.projet11.models.FindTheWordModel;
import com.vincler.jf.projet11.repositories.FindTheWordRepository;
import com.vincler.jf.projet11.repositories.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FindTheWordViewModel extends ViewModel {

    ArrayList<FindTheWordModel> findTheWordList = new ArrayList<>();
    MutableLiveData<FindTheWordModel> currentModel = new MutableLiveData<>();
    MutableLiveData<Integer> draw = new MutableLiveData<>();
    MutableLiveData<Boolean> isGameOver = new MutableLiveData<>();
    MutableLiveData<Integer> score = new MutableLiveData<>();
    MutableLiveData<BorderColorModel> borderWordColor = new MutableLiveData<>();

    public void getData(String language) {

        FindTheWordRepository.getFindTheWordList(
                new Result<List<FindTheWordModel>>() {
                    @Override
                    public void onResult(List<FindTheWordModel> result) {
                        if (result != null && result.size() != 0 && result.get(0) != null) {
                            findTheWordList.clear();
                            findTheWordList.addAll(result);
                            currentModel.postValue(result.get(0));
                            draw.postValue(0);
                            isGameOver.postValue(false);
                            score.postValue(0);
                        }
                    }

                    @Override
                    public void onError() {
                        Log.i("Error", "Error loading");

                    }
                }, language);
    }

    public void userChooseWordAtIndex(int index) {

        boolean iscorrectPosition = currentModel.getValue().getCorrectWordPosition() == index;

        if (iscorrectPosition) {
            int newScore = score.getValue() + 1;
            score.postValue(newScore);
            changeBorderWordColor(BorderColorList.GREEN, index);
        }
        if (!iscorrectPosition) {
            changeBorderWordColor(BorderColorList.RED, index);
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                goToTheNextDraw(index);
            }
        }, Constants.DELAY_BETWEEN_DRAWS);
    }

    private void changeBorderWordColor(BorderColorList borderColorList, int index) {

        BorderColorModel newBorderWordColor = new BorderColorModel(borderColorList, index);
        borderWordColor.postValue(newBorderWordColor);
    }

    private void goToTheNextDraw(int index) {

        changeBorderWordColor(BorderColorList.TRANSPARENT, index);
        int newDraw = draw.getValue() + 1;
        if (newDraw < Constants.NUMBER_OF_DRAWS) {
            currentModel.postValue(findTheWordList.get(newDraw));
            draw.postValue(newDraw);
        } else isGameOver.postValue(true);
    }
}