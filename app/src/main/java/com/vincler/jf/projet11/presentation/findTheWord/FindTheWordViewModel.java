package com.vincler.jf.projet11.presentation.findTheWord;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vincler.jf.projet11.models.ColorEnum;
import com.vincler.jf.projet11.models.ColorModel;
import com.vincler.jf.projet11.models.FindTheWordModel;
import com.vincler.jf.projet11.models.LanguageEnum;
import com.vincler.jf.projet11.repositories.FindTheWordRepository;
import com.vincler.jf.projet11.repositories.Result;
import com.vincler.jf.projet11.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

// Prepares and manages the data for FindTheWordFragment
public class FindTheWordViewModel extends ViewModel {

    ArrayList<FindTheWordModel> findTheWordList = new ArrayList<>();                    // list of the draws (pictures, words and the correct positions of the words)
    MutableLiveData<FindTheWordModel> currentModel = new MutableLiveData<>();           // current draw (picture, the four words and the correct position of the word)
    MutableLiveData<Integer> draw = new MutableLiveData<>();                            // counter of the draws
    MutableLiveData<Boolean> isGameOver = new MutableLiveData<>();                      // true if game is over
    MutableLiveData<Boolean> isErrorLoading = new MutableLiveData<>();                  // true if the data has not loaded correctly
    MutableLiveData<Integer> score = new MutableLiveData<>();                           // counter of the correct answer by the user
    public MutableLiveData<ColorModel> wordColor = new MutableLiveData<>();             // color for chosen word

    // Gets the list of draws in findTheWordList,
    // initializes score, draw and isGameOver,
    // and gets the first draw in currentModel
    public void getData(LanguageEnum language) {
        if (findTheWordList.isEmpty()) {    // initializes counter of draws
            draw.postValue(0);              // initializes isGameOver
            isGameOver.postValue(false);    // initializes the score
            score.postValue(0);

            FindTheWordRepository.getFindTheWordList(   // gets the list of draw from the repository, filtered by the chosen language
                    new Result<List<FindTheWordModel>>() {
                        @Override
                        public void onResult(List<FindTheWordModel> result) {
                            if (result != null && result.size() != 0 && result.get(0) != null) {
                                findTheWordList.clear();
                                findTheWordList.addAll(result);         // puts the list of draws in findTheWordList
                                currentModel.postValue(result.get(0));  // puts the first draw in currentModel
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
    }

    // when user choose a word,
    // checks if it's a correct answer,
    // display the color word appropriate during some delay,
    // and go to the new draw
    // index = the word position clicked by the user
    public void userChooseWordAtIndex(int index, Context context) {

        //check if the word position clicked is correct
        boolean iscorrectPosition = currentModel.getValue().getCorrectWordPosition() == index;

        if (iscorrectPosition) {
            int newScore = score.getValue() + 1;
            score.postValue(newScore);                // if result is correct, score increases by 1
            changeWordColor(ColorEnum.GREEN, index);  // and word color displays in GREEN
        }
        if (!iscorrectPosition) {
            changeWordColor(ColorEnum.RED, index);    // if result is incorrect, word color displays in RED
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {                   // A delay after displaying of the word color
                goToTheNextDraw(index, context);      // After the delay, goes to the next draw
            }
        }, Utils.getPrefs(context,"delay",1500));
    }

    // changes word color
    // index = the word position to change
    // chosenWordColor  = the color for the chosen word
    public void changeWordColor(ColorEnum chosenWordColor, int index) {

        ColorModel newChosenWordColor = new ColorModel(chosenWordColor, index);
        wordColor.postValue(newChosenWordColor);
    }

    // goes to the next draw
    // Increase the counter of draws by 1,
    // changes color word in default color (grey),
    // and increase the counter of draw by 1.
    // If it's the last draw, go to the Result.
    private void goToTheNextDraw(int index, Context context) {

        changeWordColor(ColorEnum.NONE, index);
        int newDraw = draw.getValue() + 1;
        int numberOfDraw = Utils.getPrefs(context,"drawsPerGame",7);
        if (newDraw < numberOfDraw) {
            currentModel.postValue(findTheWordList.get(newDraw));
            draw.postValue(newDraw);
        } else isGameOver.postValue(true);
    }
}