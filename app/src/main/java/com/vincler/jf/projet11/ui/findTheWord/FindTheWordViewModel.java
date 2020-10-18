package com.vincler.jf.projet11.ui.findTheWord;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vincler.jf.projet11.api.GetData;
import com.vincler.jf.projet11.dao.PicturesDAO;
import com.vincler.jf.projet11.utils.Utils;

import java.util.List;
import java.util.Random;

public class FindTheWordViewModel extends ViewModel {

    PicturesDAO picturesDAO = new PicturesDAO();
    String LANGAGE = "1";  // langage English for testing
    int NUMBER_OF_WORDS = 10;
    List<Integer> randomList;
    MutableLiveData<String> word_liveData = new MutableLiveData<>();
    MutableLiveData<String> topLeftPictureLiveData = new MutableLiveData<>();
    MutableLiveData<String> topRightPictureLiveData = new MutableLiveData<>();
    MutableLiveData<String> bottomLeftPictureLiveData = new MutableLiveData<>();
    MutableLiveData<String> bottomRightPictureLiveData = new MutableLiveData<>();

    public void getData() {

        randomList = Utils.getListRandom(5, NUMBER_OF_WORDS);
        getPictures();
    }

    private void getWord(String correctPictureId) {
        Task<QuerySnapshot> data = GetData.getWordByPicture(LANGAGE, correctPictureId);
        data.addOnCompleteListener(task -> {
            if (task.getResult() != null) {
                String word = task.getResult().getDocuments().get(0).get("word").toString();
                word_liveData.postValue(word);
            }
        });
    }

    private void getPictures() {

        Task<QuerySnapshot> pictures = GetData.getPictures();

        pictures.addOnCompleteListener(task ->
        {
            if (task.getResult() != null) {
                List<DocumentSnapshot> documents =
                        task.getResult().getDocuments();
                String topRightPicture = documents.get(randomList.get(0)).get("url").toString();
                String topLeftPicture = documents.get(randomList.get(1)).get("url").toString();
                String bottomLeftPicture = documents.get(randomList.get(2)).get("url").toString();
                String bottomRightPicture = documents.get(randomList.get(3)).get("url").toString();
                topRightPictureLiveData.postValue(topRightPicture);
                topLeftPictureLiveData.postValue(topLeftPicture);
                bottomLeftPictureLiveData.postValue(bottomLeftPicture);
                bottomRightPictureLiveData.postValue(bottomRightPicture);
                Random random = new Random();
                int correctPicture = random.nextInt(4);
                String correctPictureId = documents.get(randomList.get(correctPicture)).getId();
                getWord(correctPictureId);
            }
        });
    }
};
