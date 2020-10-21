package com.vincler.jf.projet11.repositories;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vincler.jf.projet11.models.FindThePictureModel;
import com.vincler.jf.projet11.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FindThePictureRepository {

    public static CollectionReference getCollection(String collection_name) {
        return FirebaseFirestore.getInstance().collection(collection_name);
    }

    public static void getFindThePictureList(Result<List<FindThePictureModel>> result) {
        String LANGAGE = "1";  // langage English for testing
        getCollection("pictures")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    List<DocumentSnapshot> picturesDocuments =
                            queryDocumentSnapshots.getDocuments();

                    getCollection("words")
                            .whereEqualTo("langage_id", LANGAGE)
                            .get()
                            .addOnSuccessListener(queryDocumentSnapshots2 -> {
                                        List<DocumentSnapshot> wordsDocuments = queryDocumentSnapshots2.getDocuments();

                                        ArrayList<FindThePictureModel> findThePictureModelArrayList = new ArrayList<>();

                                        for (int i = 0; i < wordsDocuments.size(); i++) {

                                            List<Integer> randomList = Utils.getListRandom(wordsDocuments.size());

                                            Random random = new Random();
                                            int correctPositionPicture = random.nextInt(4);
                                            String correctPictureId = picturesDocuments.get(randomList.get(correctPositionPicture)).getId();
                                            String word = "";
                                            for (int j = 0; j < wordsDocuments.size(); j++) {
                                                String picture_id = wordsDocuments.get(j).get("picture_id").toString();
                                                if (picture_id.equals(correctPictureId)) {
                                                    word = wordsDocuments.get(j).get("word").toString();
                                                }
                                            }

                                            FindThePictureModel findThePictureModel = new FindThePictureModel(
                                                    word,
                                                    correctPositionPicture,
                                                    picturesDocuments.get(randomList.get(0)).get("url").toString(),
                                                    picturesDocuments.get(randomList.get(1)).get("url").toString(),
                                                    picturesDocuments.get(randomList.get(2)).get("url").toString(),
                                                    picturesDocuments.get(randomList.get(3)).get("url").toString()
                                                    );

                                            findThePictureModelArrayList.add(findThePictureModel);
                                        }
                                        result.onResult(findThePictureModelArrayList);
                                    }
                            );
                });
    }
}