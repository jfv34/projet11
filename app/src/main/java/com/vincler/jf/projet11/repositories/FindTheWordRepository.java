package com.vincler.jf.projet11.repositories;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vincler.jf.projet11.models.FindTheWordModel;
import com.vincler.jf.projet11.models.LanguageEnum;
import com.vincler.jf.projet11.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FindTheWordRepository {

    public static CollectionReference getCollection(String collection_name) {
        return FirebaseFirestore.getInstance().collection(collection_name);
    }

    public static void getFindTheWordList(Result<List<FindTheWordModel>> result, LanguageEnum language) {
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

                                        ArrayList<FindTheWordModel> findTheWordModelArrayList = new ArrayList<>();
                                        List<Integer> randomList = Utils.getListRandom(wordsDocuments.size());
                                        for (int i = 0; i < wordsDocuments.size(); i++) {

                                            String correctWord = wordsDocuments.get(randomList.get(i)).get("word").toString();
                                            String pictureId = wordsDocuments.get(randomList.get(i)).get("picture_id").toString();

                                            String picture = "";
                                            for (int j = 0; j < picturesDocuments.size(); j++) {
                                                String picture_id = picturesDocuments.get(j).getId();
                                                if (picture_id.equals(pictureId)) {
                                                    picture = picturesDocuments.get(j).get("url").toString();
                                                }
                                            }
                                            List<Integer> random4wordsList;
                                            do {
                                                random4wordsList = Utils.getListRandom(wordsDocuments.size());
                                            }
                                            while ((wordsDocuments.get(random4wordsList.get(0)).get("word").equals(correctWord))
                                                    || (wordsDocuments.get(random4wordsList.get(1)).get("word").equals(correctWord))
                                                    || (wordsDocuments.get(random4wordsList.get(2)).get("word").equals(correctWord))
                                                    || (wordsDocuments.get(random4wordsList.get(3)).get("word").equals(correctWord)));


                                            ArrayList<String> words = new ArrayList<>();
                                            words.add(wordsDocuments.get(random4wordsList.get(0)).get("word").toString());
                                            words.add(wordsDocuments.get(random4wordsList.get(1)).get("word").toString());
                                            words.add(wordsDocuments.get(random4wordsList.get(2)).get("word").toString());
                                            words.add(wordsDocuments.get(random4wordsList.get(3)).get("word").toString());

                                            Random random = new Random();
                                            int correctPositionWord = random.nextInt(4);
                                            words.set(correctPositionWord, correctWord);

                                            FindTheWordModel findTheWordModel = new FindTheWordModel(
                                                    picture,
                                                    correctPositionWord,
                                                    words.get(0),
                                                    words.get(1),
                                                    words.get(2),
                                                    words.get(3)
                                            );

                                            findTheWordModelArrayList.add(findTheWordModel);
                                        }
                                        if (result != null) {
                                            result.onResult(findTheWordModelArrayList);
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