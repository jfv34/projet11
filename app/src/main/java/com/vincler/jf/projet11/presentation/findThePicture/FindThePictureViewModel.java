package com.vincler.jf.projet11.presentation.findThePicture;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vincler.jf.projet11.models.BorderColorModel;
import com.vincler.jf.projet11.models.ColorEnum;
import com.vincler.jf.projet11.models.FindThePictureModel;
import com.vincler.jf.projet11.models.LanguageEnum;
import com.vincler.jf.projet11.repositories.FindThePictureRepository;
import com.vincler.jf.projet11.repositories.Result;
import com.vincler.jf.projet11.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

// Prepares and manages the data for FindThePictureFragment
public class FindThePictureViewModel extends ViewModel {

    ArrayList<FindThePictureModel> findThePictureList = new ArrayList<>();          //
    MutableLiveData<FindThePictureModel> currentModel = new MutableLiveData<>();    // current draw (word, the four pictures, the correct position of the picture
    MutableLiveData<Integer> draw = new MutableLiveData<>();                        // counter of the draws
    MutableLiveData<Boolean> isGameOver = new MutableLiveData<>();                  // true if game is over
    MutableLiveData<Boolean> isErrorLoading = new MutableLiveData<>();              // true if the data has not loaded correctly
    MutableLiveData<Integer> score = new MutableLiveData<>();                       // counter of the correct answer by the user
    MutableLiveData<BorderColorModel> borderPictureColor = new MutableLiveData<>(); // picture border color

    // Gets the list of draws in findThePictureList,
    // initializes score, draw and isGameOver,
    // and gets the first draw in currentModel
    public void getData(LanguageEnum language) {
        if (findThePictureList.isEmpty()) {
            draw.postValue(0);  // initializes counter of draws
            isGameOver.postValue(false);                        // initializes isGameOver
            score.postValue(0);                                 // initializes the score

            FindThePictureRepository.getFindThePictureList(    // gets the list of draw from the repository, filtered by the chosen language
                    new Result<List<FindThePictureModel>>() {
                        @Override
                        public void onResult(List<FindThePictureModel> result) {
                            if (result != null && result.size() != 0 && result.get(0) != null) {
                                findThePictureList.clear();
                                findThePictureList.addAll(result);      // puts the list of draws in findThePictureList
                                currentModel.postValue(result.get(0));  // puts the first draw in currentModel
                            } else {
                                isErrorLoading.postValue(true);
                                ;
                            }
                        }

                        @Override
                        public void onError() {
                            isErrorLoading.postValue(true);
                        }
                    }, language);
        }
    }

    // when user choose a picture,
    // checks if it's a correct answer,
    // display the border color appropriate during some delay,
    // and go to the new draw
    // index = the picture's position clicked by the user
    public void userChoosePictureAtIndex(int index, Context context) {

        //check if the picture's position clicked is correct
        boolean iscorrectPosition = currentModel.getValue().getCorrectPicturePosition() == index;

        if (iscorrectPosition) {
            int newScore = score.getValue() + 1;
            score.postValue(newScore);                          // if result is correct, score increases by 1
            changeBorderPictureColor(ColorEnum.GREEN, index);   // and border picture displays in GREEN
        }
        if (!iscorrectPosition) {
            changeBorderPictureColor(ColorEnum.RED, index);     // if result is incorrect, border picture displays in RED
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {                             // A delay after displaying of the border color
                goToTheNextDraw(index, context);                // After the delay, goes to the next draw
            }
        }, Utils.getPrefs(context, "delay", 1500));
    }

    // changes border picture color
    // index = the image's position to change
    // borderColor  = the border color to displays
    private void changeBorderPictureColor(ColorEnum borderColor, int index) {

        BorderColorModel newBorderPictureColor = new BorderColorModel(borderColor, index);
        borderPictureColor.postValue(newBorderPictureColor);
    }

    // goes to the next draw
    // Increase the counter of draws by 1,
    // changes color picture border in invible,
    // and increase the counter of draw by 1.
    // If it's the last draw, go to the Result.
    private void goToTheNextDraw(int index, Context context) {

        changeBorderPictureColor(ColorEnum.NONE, index);
        int newDraw = draw.getValue() + 1;
        int numberOfDraw = Utils.getPrefs(context, "drawsPerGame", 7);
        if (newDraw < numberOfDraw) {
            currentModel.postValue(findThePictureList.get(newDraw));
            draw.postValue(newDraw);
        } else isGameOver.postValue(true);
    }
}