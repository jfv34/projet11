package com.vincler.jf.projet11.repositories;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vincler.jf.projet11.models.FindThePictureModel;
import com.vincler.jf.projet11.models.LanguageEnum;
import com.vincler.jf.projet11.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FindThePictureRepository {

    public static CollectionReference getCollection(String collection_name) {
        return FirebaseFirestore.getInstance().collection(collection_name);
    }

    public static void getFindThePictureList(Result<List<FindThePictureModel>> result, LanguageEnum language) {

        getCollection("words")
                .whereEqualTo("language", language)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> wordsDocuments =
                            queryDocumentSnapshots.getDocuments();

                    getCollection("pictures")

                            .get()
                            .addOnSuccessListener(queryDocumentSnapshots2 -> {
                                List<DocumentSnapshot> picturesDocuments = queryDocumentSnapshots2.getDocuments();

                                ArrayList<FindThePictureModel> findThePictureModelArrayList = new ArrayList<>();
                                // Generate randomList for draws in random order
                                List<Integer> randomList = Utils.getListRandom(wordsDocuments.size());

                                // Gets draw for each words (filtered by language)
                                for (int i = 0; i < wordsDocuments.size(); i++) {

                                    String word = wordsDocuments.get(randomList.get(i)).get("word").toString();
                                    String correctPictureId = wordsDocuments.get(randomList.get(i)).get("picture_id").toString();

                                    String correctPicture = "";

                                    // Get the picture of the word
                                    for (int j = 0; j < picturesDocuments.size(); j++) {
                                        String picture_id = picturesDocuments.get(j).getId();
                                        if (picture_id.equals(correctPictureId)) {
                                            correctPicture = picturesDocuments.get(j).get("url").toString();
                                        }
                                    }

                                    // Get four pictures others than the correct picture
                                    List<Integer> random4picturesList;
                                    do {
                                        random4picturesList = Utils.getListRandom(picturesDocuments.size());
                                    }
                                    while ((picturesDocuments.get(random4picturesList.get(0)).getId().equals(correctPictureId))
                                            || (picturesDocuments.get(random4picturesList.get(1)).getId().equals(correctPictureId))
                                            || (picturesDocuments.get(random4picturesList.get(2)).getId().equals(correctPictureId))
                                            || (picturesDocuments.get(random4picturesList.get(3)).getId().equals(correctPictureId)));

                                    // Get the URL of each of this four pictures
                                    ArrayList<String> pictures = new ArrayList<>();
                                    pictures.add(picturesDocuments.get(random4picturesList.get(0)).get("url").toString());
                                    pictures.add(picturesDocuments.get(random4picturesList.get(1)).get("url").toString());
                                    pictures.add(picturesDocuments.get(random4picturesList.get(2)).get("url").toString());
                                    pictures.add(picturesDocuments.get(random4picturesList.get(3)).get("url").toString());
                                    Random random = new Random();

                                    int correctPositionPicture = random.nextInt(4);
                                    //In this correct position, replace wrong picture by the correct picture
                                    pictures.set(correctPositionPicture, correctPicture);

                                    // Create a draw
                                    FindThePictureModel findThePictureModel = new FindThePictureModel(
                                            word,
                                            correctPositionPicture,
                                            pictures.get(0),
                                            pictures.get(1),
                                            pictures.get(2),
                                            pictures.get(3)
                                    );

                                    // Add this draw in the list of the draws
                                    findThePictureModelArrayList.add(findThePictureModel);
                                }
                                        if (result != null) {
                                            result.onResult(findThePictureModelArrayList);
                                        } else {
                                            result.onError();
                                        }
                                    }
                            )
                            .addOnFailureListener(e -> {
                                result.onError();
                            });
                });
    }
}