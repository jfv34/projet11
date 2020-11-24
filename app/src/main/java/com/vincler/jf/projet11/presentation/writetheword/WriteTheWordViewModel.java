package com.vincler.jf.projet11.presentation.writetheword;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vincler.jf.projet11.models.ColorEnum;
import com.vincler.jf.projet11.models.LanguageEnum;
import com.vincler.jf.projet11.models.WriteTheWordModel;
import com.vincler.jf.projet11.repositories.Result;
import com.vincler.jf.projet11.repositories.WriteTheWordRepository;
import com.vincler.jf.projet11.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WriteTheWordViewModel extends ViewModel {

    ArrayList<WriteTheWordModel> writeTheWordList = new ArrayList<>();
    public MutableLiveData<WriteTheWordModel> currentModel = new MutableLiveData<>();
    MutableLiveData<Integer> draw = new MutableLiveData<>();
    MutableLiveData<Boolean> isGameOver = new MutableLiveData<>();
    MutableLiveData<Boolean> isErrorLoading = new MutableLiveData<>();
    MutableLiveData<Integer> score = new MutableLiveData<>();
    MutableLiveData<ColorEnum> borderWordColor = new MutableLiveData<>();
    MutableLiveData<Boolean> isIncorrectAnswer = new MutableLiveData<>();

    public void getData(LanguageEnum language, Context context) {
        if(writeTheWordList.isEmpty()) {
            draw.postValue(0);
            isGameOver.postValue(false);
            score.postValue(0);

            getWriteTheWordList(language, context);
        }}

    private void getWriteTheWordList(LanguageEnum language, Context context) {
        WriteTheWordRepository.getWriteTheWordList(
                new Result<List<WriteTheWordModel>>() {
                    @Override
                    public void onResult(List<WriteTheWordModel> result) {
                        if (result != null && result.size() != 0 && result.get(0) != null) {
                            writeTheWordList.clear();
                            writeTheWordList.addAll(result);
                            currentModel.postValue(result.get(0));
                            int i = 0;
                        } else {
                            isErrorLoading.postValue(true);
                        }
                    }

                    @Override
                    public void onError() {
                        isErrorLoading.postValue(true);
                    }
                }, language, context);
    }

    public void userValidateWord(String textValidate, Context context) {

        if (isWordCorrect(textValidate)) {
            int newScore = score.getValue() + 1;
            score.postValue(newScore);
            changeBorderWordColor(ColorEnum.GREEN);
        } else {
            changeBorderWordColor(ColorEnum.RED);
            isIncorrectAnswer.postValue(true);
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                isIncorrectAnswer.postValue(false);
                goToTheNextDraw(context);
            }
        }, Utils.getPrefs(context, "delay", 1500));
    }

    public String getFirstLetter() {
        if (currentModel.getValue().getWord() != null) {
            return currentModel.getValue().getWord().substring(0, 1);
        } else return "";
    }

    private void changeBorderWordColor(ColorEnum borderColorEnum) {

        borderWordColor.postValue(borderColorEnum);
    }

    private boolean isWordCorrect(String wordValidate) {
        boolean iscorrect = false;
        String correctWord = currentModel.getValue().getWord();
        if (wordValidate.toUpperCase().equals(correctWord.toUpperCase())) {
            iscorrect = true;
        }
        return iscorrect;
    }

    private void goToTheNextDraw(Context context) {

        changeBorderWordColor(ColorEnum.NONE);
        int newDraw = draw.getValue() + 1;
        int numberOfDraw = Utils.getPrefs(context, "drawsPerGame", 7);
        if (newDraw < numberOfDraw) {
            currentModel.postValue(writeTheWordList.get(newDraw));
            draw.postValue(newDraw);
        } else isGameOver.postValue(true);
    }
}
