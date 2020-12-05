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

    // List of the draws (pictures, words and the correct positions of the words)
    ArrayList<FindTheWordModel> findTheWordList = new ArrayList<>();

    // Current draw (picture, the four words and the correct position of the word)
    MutableLiveData<FindTheWordModel> currentModel = new MutableLiveData<>();

    // Counter of the draws
    MutableLiveData<Integer> draw = new MutableLiveData<>();

    MutableLiveData<Boolean> isGameOver = new MutableLiveData<>();
    MutableLiveData<Boolean> isErrorLoading = new MutableLiveData<>();
    MutableLiveData<Integer> score = new MutableLiveData<>();
    public MutableLiveData<ColorModel> wordColor = new MutableLiveData<>();

    // Gets the list of draws in findTheWordList,
    // initializes score, draw and isGameOver,
    // and gets the first draw in currentModel
    public void getData(LanguageEnum language) {
        if (findTheWordList.isEmpty()) {
            // data initialization
            draw.postValue(0);
            isGameOver.postValue(false);
            score.postValue(0);

            // gets the list of draw from the repository, filtered by the chosen language
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
    }

    // when user choose a word,
    // checks if it's a correct answer,
    // display the color word appropriate during some delay,
    // and go to the new draw
    // index = the word position clicked by the user
    public void userChooseWordAtIndex(int index, Context context) {

        boolean iscorrectPosition = currentModel.getValue().getCorrectWordPosition() == index;

        if (iscorrectPosition) {
            int newScore = score.getValue() + 1;
            score.postValue(newScore);
            changeWordColor(ColorEnum.GREEN, index);
        }
        if (!iscorrectPosition) {
            changeWordColor(ColorEnum.RED, index);
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                goToTheNextDraw(index, context);
            }
        }, Utils.getPrefs(context,"delay",1500));
    }

    // changes word color
    // index = the word position to change
    // chosenWordColor  = the color for the chosen word
    public void changeWordColor(ColorEnum chosenWordColor, int index) {

        ColorModel newChosenWordColor = new ColorModel(chosenWordColor, index, null, 0);
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