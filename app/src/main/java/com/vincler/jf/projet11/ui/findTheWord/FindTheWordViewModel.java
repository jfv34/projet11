package com.vincler.jf.projet11.ui.findTheWord;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vincler.jf.projet11.api.GetData;

import java.util.List;
import java.util.Random;

public class FindTheWordViewModel extends ViewModel {

    int NUMBER_OF_WORDS = 10;
    MutableLiveData<String> englishWord_liveData = new MutableLiveData<>();
    MutableLiveData<String> topLeftPictureLiveData = new MutableLiveData<>();
    MutableLiveData<String> topRightPictureLiveData = new MutableLiveData<>();
    MutableLiveData<String> bottomLeftPictureLiveData = new MutableLiveData<>();
    MutableLiveData<String> bottomRightPictureLiveData = new MutableLiveData<>();

    public void getRandomWord() {

        String LANGAGE = "1";  // langage English for testing
        int random = new Random().nextInt(NUMBER_OF_WORDS);
        Task<QuerySnapshot> data = GetData.getWords(LANGAGE);
        data.addOnCompleteListener(task -> {
            if (task.getResult() != null) {
                String word = task.getResult().getDocuments().get(random).get("word").toString();
                englishWord_liveData.postValue(word);
                String pictureId = task.getResult().getDocuments().get(random).get("picture_id").toString();
                getPicture(pictureId, random);
            }
        });
    }

    private void getPicture(String pictureId, int randomCorrectPicture) {
        Task<DocumentSnapshot> picture = GetData.getPicture(pictureId);
        picture.addOnCompleteListener(task ->
        {
            if (task.getResult() != null) {
                String correctPictureUrl =
                        task.getResult()
                                .get("url")
                                .toString();
                topLeftPictureLiveData.postValue(correctPictureUrl);
                getFakePicture(randomCorrectPicture);
            }
        });
    }

    private void getFakePicture(int randomCorrectPicture) {
        Random random = new Random();
        int random1=0;
        int random2=0;
        int random3=0;
        
       while (random1 == randomCorrectPicture) {
           random1 = random.nextInt(NUMBER_OF_WORDS);
       } ;
        do {
            random2 = random.nextInt(NUMBER_OF_WORDS);
        } while ((random2 == random1) || (random2 == randomCorrectPicture));
        do {
            random3 = random.nextInt(NUMBER_OF_WORDS);
        } while ((random3 == random2) || (random3 == random1) || (random3 == randomCorrectPicture));

        Task<QuerySnapshot> pictures = GetData.getPictures();
        int finalAlea1 = random1;
        int finalAlea2 = random2;
        int finalAlea3 = random3;
        pictures.addOnCompleteListener(task ->
        {
            if (task.getResult() != null) {
                List<DocumentSnapshot> documents =
                        task.getResult().getDocuments();
                String fakePictureUrl1 = documents.get(finalAlea1).get("url").toString();
                String fakePictureUrl2 = documents.get(finalAlea2).get("url").toString();
                String fakePictureUrl3 = documents.get(finalAlea3).get("url").toString();
                topRightPictureLiveData.postValue(fakePictureUrl1);
                bottomLeftPictureLiveData.postValue(fakePictureUrl2);
                bottomRightPictureLiveData.postValue(fakePictureUrl3);
                Log.i("tag_alea_correctPicture", String.valueOf(randomCorrectPicture));
                Log.i("tag_alea1", String.valueOf(finalAlea1));
                Log.i("tag_alea2", String.valueOf(finalAlea2));
                Log.i("tag_alea3", String.valueOf(finalAlea3));
            }
        });
    }
};
