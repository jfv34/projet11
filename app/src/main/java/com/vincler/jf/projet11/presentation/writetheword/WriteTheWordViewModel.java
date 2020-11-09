package com.vincler.jf.projet11.presentation.writetheword;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vincler.jf.projet11.models.Constants;
import com.vincler.jf.projet11.models.LanguageEnum;
import com.vincler.jf.projet11.models.WriteTheWordModel;
import com.vincler.jf.projet11.repositories.Result;
import com.vincler.jf.projet11.repositories.WriteTheWordRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WriteTheWordViewModel extends ViewModel {

    ArrayList<WriteTheWordModel> writeTheWordList = new ArrayList<>();
    MutableLiveData<WriteTheWordModel> currentModel = new MutableLiveData<>();
    MutableLiveData<Integer> draw = new MutableLiveData<>();
    MutableLiveData<Boolean> isGameOver = new MutableLiveData<>();
    MutableLiveData<Boolean> isErrorLoading = new MutableLiveData<>();
    MutableLiveData<Integer> score = new MutableLiveData<>();

    public void getData(LanguageEnum language) {
        draw.postValue(0);
        isGameOver.postValue(false);
        score.postValue(0);

        WriteTheWordRepository.getWriteTheWordList(
                new Result<List<WriteTheWordModel>>() {
                    @Override
                    public void onResult(List<WriteTheWordModel> result) {
                        if (result != null && result.size() != 0 && result.get(0) != null) {
                            writeTheWordList.clear();
                            writeTheWordList.addAll(result);
                            currentModel.postValue(result.get(0));
                            int i =0;
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

    public void userValidateWord(int index) {

        boolean iscorrect = false;
        if (iscorrect) {
            int newScore = score.getValue() + 1;
            score.postValue(newScore);
        }
        if (!iscorrect) {
            //todo
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                goToTheNextDraw(index);
            }
        }, Constants.DELAY_BETWEEN_DRAWS);
    }

    private void goToTheNextDraw(int index) {

        int newDraw = draw.getValue() + 1;
        if (newDraw < Constants.NUMBER_OF_DRAWS) {
            // currentModel.postValue(writeTheWordList.get(newDraw));
            draw.postValue(newDraw);
        } else isGameOver.postValue(true);
    }
}
