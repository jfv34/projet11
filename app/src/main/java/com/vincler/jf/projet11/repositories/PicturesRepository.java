package com.vincler.jf.projet11.repositories;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.vincler.jf.projet11.models.FindThePictureModel;
import com.vincler.jf.projet11.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PicturesRepository {

    public static CollectionReference getCollection(String collection_name) {
        return FirebaseFirestore.getInstance().collection(collection_name);
    }

    public static void getPictures(Result<List<FindThePictureModel>> result) {
        getCollection("pictures")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    result.onResult(new ArrayList<>());
                });
    }

   /* List<DocumentSnapshot> documents =
            task.getResult().getDocuments();
    topLeftPicture = documents.get(randomList.get(0)).get("url").toString();
    topRightPicture = documents.get(randomList.get(1)).get("url").toString();
    bottomLeftPicture = documents.get(randomList.get(2)).get("url").toString();
    bottomRightPicture = documents.get(randomList.get(3)).get("url").toString();

    Random random = new Random();
    int correctPicture = random.nextInt(4);
    String correctPictureId = documents.get(randomList.get(correctPicture)).getId();

    findThePictureModel = new FindThePictureModel("",correctPicture,
                                                  topLeftPicture, topRightPicture, bottomLeftPicture, bottomRightPicture, com.vincler.jf.projet11.models.FindThePictureResultModel.NOT_YET_PLAYING);
    ;
                findThePictureModelList.add(draw, findThePictureModel);
    getWord(correctPictureId, draw);*/


   /* private void getWord(String correctPictureId, int draw) {
        Task<QuerySnapshot> data = WordsRepository.getWordByPicture(LANGAGE, correctPictureId);
        data.addOnCompleteListener(task -> {

            String word = task.getResult().getDocuments().get(0).get("word").toString();
            findThePictureModel.setWord(word);
            findThePictureModel.setResult(com.vincler.jf.projet11.models.FindThePictureResultModel.NOT_YET_PLAYING);
            findThePictureModelList.set(draw, findThePictureModel);
            if (draw + 1 == NUMBER_OF_DRAW_FOR_THE_GAME) {
                findThePictureListLiveData.postValue(findThePictureModelList);
            }
        });
    }*/
/*   public void getData() {
       randomList = Utils.getListRandom(5, NUMBER_OF_WORDS);
       for (int i = 0; i < NUMBER_OF_DRAW_FOR_THE_GAME; i++) {
           getPictures(i);
       }
   }*/
/*   String LANGAGE = "1";  // langage English for testing
    int NUMBER_OF_WORDS = 10;
    int NUMBER_OF_DRAW_FOR_THE_GAME = 5;
    List<Integer> randomList;*/

}

