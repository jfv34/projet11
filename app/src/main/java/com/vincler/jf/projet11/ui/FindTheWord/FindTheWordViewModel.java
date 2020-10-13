package com.vincler.jf.projet11.ui.FindTheWord;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vincler.jf.projet11.api.GetData;

import java.util.List;
import java.util.Random;

public class FindTheWordViewModel extends ViewModel {

    int NUMBER_OF_WORDS = 11;

    public void getRandomWord() {

        String LANGAGE = "1";  // langage English for testing
        Random random = new Random();
        int alea = random.nextInt(NUMBER_OF_WORDS);
        Task<QuerySnapshot> data = GetData.getWords(LANGAGE);
        data.addOnCompleteListener(task -> {
            if (task.getResult() != null) {
                String word = task.getResult().getDocuments().get(alea).get("word").toString();
                String pictureId = task.getResult().getDocuments().get(alea).get("picture_id").toString();
                getPicture(pictureId, alea, word);

            }
        });
    }

    private void getPicture(String pictureId, int aleaCorrectPicture, String word) {
        Task<DocumentSnapshot> picture = GetData.getPicture(pictureId);
        picture.addOnCompleteListener(task ->
        {
            if (task.getResult() != null) {
                String correctPictureUrl =
                        task.getResult()
                                .get("url")
                                .toString();
                getFakePicture(correctPictureUrl, aleaCorrectPicture, word);
            }
        });
    }

    private void getFakePicture(String correctPictureUrl, int aleaCorrectPicture, String word) {
        Random random = new Random();
        int alea1;
        int alea2;
        int alea3;
        do {
            alea1 = random.nextInt(NUMBER_OF_WORDS);
        } while (alea1 == aleaCorrectPicture);
        do {
            alea2 = random.nextInt(NUMBER_OF_WORDS);
        } while ((alea2 == alea1) || (alea2 == aleaCorrectPicture));
        do {
            alea3 = random.nextInt(NUMBER_OF_WORDS);
        } while ((alea3 == alea2) || (alea3 == alea1) || (alea3 == aleaCorrectPicture));


        Task<QuerySnapshot> pictures = GetData.getPictures();
        int finalAlea1 = alea1;
        int finalAlea2 = alea2;
        int finalAlea3 = alea3;
        pictures.addOnCompleteListener(task ->
        {
            if (task.getResult() != null) {
                List<DocumentSnapshot> documents =
                        task.getResult().getDocuments();
                String fakePictureUrl1 = documents.get(finalAlea1).get("url").toString();
                String fakePictureUrl2 = documents.get(finalAlea2).get("url").toString();
                String fakePictureUrl3 = documents.get(finalAlea3).get("url").toString();

                Log.i("tag_fakePictureUrl1", fakePictureUrl1);
                Log.i("tag_fakePictureUrl2", fakePictureUrl2);
                Log.i("tag_fakePictureUrl3", fakePictureUrl3);
                Log.i("tag_correctPictureUrl", correctPictureUrl);
                Log.i("tag_word", word);
                Log.i("tag_alea1", String.valueOf(finalAlea1));
                Log.i("tag_alea2", String.valueOf(finalAlea2));
                Log.i("tag_alea3", String.valueOf(finalAlea3));
                Log.i("tag_aleaCorrectPicture", String.valueOf(aleaCorrectPicture));
            }
        });
    }
};
