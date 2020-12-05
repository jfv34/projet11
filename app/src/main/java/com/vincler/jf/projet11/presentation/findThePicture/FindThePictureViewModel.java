package com.vincler.jf.projet11.presentation.findThePicture;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vincler.jf.projet11.models.ColorEnum;
import com.vincler.jf.projet11.models.ColorModel;
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
    // List of the draws (words, pictures and correct position)
    ArrayList<FindThePictureModel> findThePictureList = new ArrayList<>();

    // Current draw (word, the four pictures, the correct position of the picture)
    MutableLiveData<FindThePictureModel> currentModel = new MutableLiveData<>();

    // Counter of the draws
    MutableLiveData<Integer> draw = new MutableLiveData<>();

    MutableLiveData<Boolean> isGameOver = new MutableLiveData<>();
    MutableLiveData<Boolean> isErrorLoading = new MutableLiveData<>();
    MutableLiveData<Integer> score = new MutableLiveData<>();
    public MutableLiveData<ColorModel> borderPictureColor = new MutableLiveData<>();

    /* Gets the list of draws in findThePictureList,
    initializes score, draw and isGameOver,
    /and gets the first draw in currentModel */
    public void getData(LanguageEnum language) {
        if (findThePictureList.isEmpty()) {
            // data initialization
            draw.postValue(0);
            isGameOver.postValue(false);
            score.postValue(0);

            // gets the list of draw from the repository, filtered by the chosen language
            FindThePictureRepository.getFindThePictureList(
                    new Result<List<FindThePictureModel>>() {
                        @Override
                        public void onResult(List<FindThePictureModel> result) {
                            if (result != null && result.size() != 0 && result.get(0) != null) {
                                findThePictureList.clear();
                                findThePictureList.addAll(result);
                                currentModel.postValue(result.get(0));
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
    // display the borders colors appropriates during some delay,
    // and go to the new draw
    // index = the picture position clicked by the user
    public void userChoosePictureAtIndex(int index, Context context) {

        boolean iscorrectPosition = currentModel.getValue().getCorrectPicturePosition() == index;

        if (iscorrectPosition) {
            int newScore = score.getValue() + 1;
            score.postValue(newScore);
            changeBorderPictureColor(ColorEnum.GREEN, index, null, 0);
        }
        if (!iscorrectPosition) {
            changeBorderPictureColor(ColorEnum.RED, index, ColorEnum.GREEN, currentModel.getValue().getCorrectPicturePosition());
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                goToTheNextDraw(index, context);
            }
        }, Utils.getPrefs(context, "delay", 1500));
    }

    // changes borders pictures colors
    // index = the image position to change
    // borderColor  = the border color to displays
    // index2 = the correct image position to change if the answer is incorrect
    // borderColor2  = the border color to displays if the answer is incorrect

    public void changeBorderPictureColor(ColorEnum borderColor, int index, ColorEnum borderColor2, int index2) {

        ColorModel newBorderPictureColor = new ColorModel(borderColor, index, borderColor2, index2);
        borderPictureColor.postValue(newBorderPictureColor);
    }

    // goes to the next draw
    // Increase the counter of draws by 1,
    // changes color picture border in invible,
    // and increase the counter of draw by 1.
    // If it's the last draw, go to the Result.
    public void goToTheNextDraw(int index, Context context) {

        changeBorderPictureColor(ColorEnum.NONE, index, ColorEnum.NONE, currentModel.getValue().getCorrectPicturePosition());
        int newDraw = draw.getValue() + 1;
        int numberOfDraw = Utils.getPrefs(context, "drawsPerGame", 7);
        if (newDraw < numberOfDraw) {
            currentModel.postValue(findThePictureList.get(newDraw));
            draw.postValue(newDraw);
        } else isGameOver.postValue(true);
    }
}