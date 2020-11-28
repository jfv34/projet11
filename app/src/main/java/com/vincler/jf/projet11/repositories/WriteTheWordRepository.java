package com.vincler.jf.projet11.repositories;

import android.content.Context;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vincler.jf.projet11.models.LanguageEnum;
import com.vincler.jf.projet11.models.WriteTheWordModel;
import com.vincler.jf.projet11.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class WriteTheWordRepository {

    public static CollectionReference getCollection(String collection_name) {
        return FirebaseFirestore.getInstance().collection(collection_name);
    }

    public static void getWriteTheWordList(Result<List<WriteTheWordModel>> result, LanguageEnum language, Context context) {
        getCollection("words")
                .whereEqualTo("language", language)
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
                                                ArrayList<WriteTheWordModel> writeTheWordModelList = new ArrayList<>();
                                                // Generate randomList for draws in random order
                                                List<Integer> randomList = Utils.getListRandom(wordsDocuments.size());

                                                // Gets draw for each words (filtered by language)
                                                for (int i = 0; i < Utils.getPrefs(context, "drawsPerGame", 7); i++) {

                                                    // Get the picture of this word
                                                    String pictureId = wordsDocuments.get(randomList.get(i)).get("picture_id").toString();
                                                    String picture = "";

                                                    // Get the url of this picture
                                                    for (int j = 0; j < picturesDocuments.size(); j++) {
                                                        String id = picturesDocuments.get(j).getId();
                                                        if (id.equals(pictureId)) {
                                                            picture = picturesDocuments.get(j).get("url").toString();
                                                        }
                                                    }

                                                    // Create a draw
                                                    WriteTheWordModel writeTheWordModel = new WriteTheWordModel(
                                                            picture,
                                                            wordsDocuments.get(randomList.get(i)).get("word").toString(),
                                                            wordsDocuments.get(randomList.get(i)).getId()
                                                    );

                                                    // Add this draw in the list of the draws
                                                    writeTheWordModelList.add(writeTheWordModel);
                                                }
                                                if (result != null) {
                                                    result.onResult(writeTheWordModelList);
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