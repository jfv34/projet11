package com.vincler.jf.projet11.repositories;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vincler.jf.projet11.models.FindTheWordModel;
import com.vincler.jf.projet11.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FindTheWordRepository {

    public static CollectionReference getCollection(String collection_name) {
        return FirebaseFirestore.getInstance().collection(collection_name);
    }

    public static void getFindTheWordList(Result<List<FindTheWordModel>> result, String language) {
        getCollection("words")
                .whereEqualTo("langage_id", language)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                            List<DocumentSnapshot> wordsDocuments =
                                    queryDocumentSnapshots.getDocuments();

                            getCollection("pictures")
                                    .get()
                                    .addOnFailureListener(e -> {
                                        result.onError();
                                    })
                                    .addOnSuccessListener(queryDocumentSnapshots1 ->
                                            {
                                                List<DocumentSnapshot> picturesDocuments =
                                                        queryDocumentSnapshots1.getDocuments();

                                                ArrayList<FindTheWordModel> findTheWordModelArrayList = new ArrayList<>();
                                                for (int i = 0; i < wordsDocuments.size(); i++) {

                                                    List<Integer> randomList = Utils.getListRandom(wordsDocuments.size());

                                                    Random random = new Random();
                                                    int correctPositionWord = random.nextInt(4);
                                                    String correctWordId = wordsDocuments.get(randomList.get(correctPositionWord)).getId();
                                                    String picture = "";
                                                    for (int j = 0; j < picturesDocuments.size(); j++) {
                                                        String picture_id = picturesDocuments.get(j).getId();
                                                        if (picture_id.equals(correctWordId)) {
                                                            picture = picturesDocuments.get(j).get("url").toString();
                                                        }
                                                    }

                                                    FindTheWordModel findTheWordModel = new FindTheWordModel(
                                                            picture,
                                                            correctPositionWord,
                                                            wordsDocuments.get(randomList.get(0)).get("word").toString(),
                                                            wordsDocuments.get(randomList.get(1)).get("word").toString(),
                                                            wordsDocuments.get(randomList.get(2)).get("word").toString(),
                                                            wordsDocuments.get(randomList.get(3)).get("word").toString()
                                                    );

                                                    findTheWordModelArrayList.add(findTheWordModel);
                                                }
                                                if (result != null) {
                                                    result.onResult(findTheWordModelArrayList);
                                                }
                                            }
                                    )
                                    .addOnFailureListener(e -> {
                                        e.printStackTrace();
                                        result.onError();
                                    });
                        }
                );
    }
}
