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

// Prepares and manages the data for WriteTheWordFragment
public class WriteTheWordViewModel extends ViewModel {

    // List of the draws (pictures and words)
    ArrayList<WriteTheWordModel> writeTheWordList = new ArrayList<>();

    // Current draw (the picture and the word)
    public MutableLiveData<WriteTheWordModel> currentModel = new MutableLiveData<>();

    // Counter of the draws
    MutableLiveData<Integer> draw = new MutableLiveData<>();

    MutableLiveData<Boolean> isGameOver = new MutableLiveData<>();
    MutableLiveData<Boolean> isErrorLoading = new MutableLiveData<>();
    MutableLiveData<Integer> score = new MutableLiveData<>();
    MutableLiveData<ColorEnum> borderWordColor = new MutableLiveData<>();
    MutableLiveData<Boolean> isIncorrectAnswer = new MutableLiveData<>();

    // Gets the list of draws in writeTheWordList,
    // initializes score, draw and isGameOver,
    // and gets the first draw in currentModel
    public void getData(LanguageEnum language, Context context) {
        if (writeTheWordList.isEmpty()) {
            // data initialization
            draw.postValue(0);
            isGameOver.postValue(false);
            score.postValue(0);

            getWriteTheWordList(language, context);
        }
    }

    private void getWriteTheWordList(LanguageEnum language, Context context) {
        // gets the list of draw from the repository, filtered by the chosen language
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

    // when user validate his word,
    // checks if it's a correct answer,
    // display the color word appropriate during some delay,
    // and go to the new draw
    public void userValidateWord(String textValidate, Context context) {

        //check if the word written is correct
        if (isWordCorrect(textValidate)) {
            int newScore = score.getValue() + 1;
            score.postValue(newScore);
            changeWordColor(ColorEnum.GREEN);
        } else {
            changeWordColor(ColorEnum.RED);
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

    // gets the first letter of the word in EditText
    public String getFirstLetter() {
        if (currentModel.getValue().getWord() != null) {
            return currentModel.getValue().getWord().substring(0, 1);
        } else return "";
    }

    // changes word color
    // wordColor  = the color for the validated word
    private void changeWordColor(ColorEnum wordColor) {

        borderWordColor.postValue(wordColor);
    }

    // check if the word written by the user is correct
    private boolean isWordCorrect(String wordValidate) {
        boolean iscorrect = false;
        String correctWord = currentModel.getValue().getWord();
        if (wordValidate.toUpperCase().equals(correctWord.toUpperCase())) {
            iscorrect = true;
        }
        return iscorrect;
    }

    // goes to the next draw
    // Increase the counter of draws by 1,
    // changes color word in default color (grey),
    // and increase the counter of draw by 1.
    // If it's the last draw, go to the Result.
    private void goToTheNextDraw(Context context) {

        changeWordColor(ColorEnum.NONE);
        int newDraw = draw.getValue() + 1;
        int numberOfDraw = Utils.getPrefs(context, "drawsPerGame", 7);
        if (newDraw < numberOfDraw) {
            currentModel.postValue(writeTheWordList.get(newDraw));
            draw.postValue(newDraw);
        } else isGameOver.postValue(true);
    }
}
