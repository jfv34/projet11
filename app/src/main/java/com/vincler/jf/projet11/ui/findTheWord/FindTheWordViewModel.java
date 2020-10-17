package com.vincler.jf.projet11.ui.findTheWord;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vincler.jf.projet11.api.GetData;
import com.vincler.jf.projet11.utils.Utils;

import java.util.List;

public class FindTheWordViewModel extends ViewModel {

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
        getWord();
        getPictures();
    }

    private void getWord() {
        Task<QuerySnapshot> data = GetData.getWords(LANGAGE);
        data.addOnCompleteListener(task -> {
            if (task.getResult() != null) {
                String word = task.getResult().getDocuments().get(randomList.get(0)).get("word").toString();
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
                String correctPicture = documents.get(randomList.get(0)).get("url").toString();
                String fakePictureUrl1 = documents.get(randomList.get(1)).get("url").toString();
                String fakePictureUrl2 = documents.get(randomList.get(2)).get("url").toString();
                String fakePictureUrl3 = documents.get(randomList.get(3)).get("url").toString();
                topRightPictureLiveData.postValue(fakePictureUrl1);
                bottomLeftPictureLiveData.postValue(fakePictureUrl2);
                bottomRightPictureLiveData.postValue(fakePictureUrl3);
                topLeftPictureLiveData.postValue(correctPicture);
            }
        });
    }
};
