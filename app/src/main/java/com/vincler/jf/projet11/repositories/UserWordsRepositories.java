package com.vincler.jf.projet11.repositories;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vincler.jf.projet11.models.UserWordsModel;

import java.util.ArrayList;
import java.util.List;

public class UserWordsRepositories {

    public static CollectionReference getCollection(String collection_name) {
        return FirebaseFirestore.getInstance().collection(collection_name);
    }

    public static void getWordsUser(Result<ArrayList<UserWordsModel>> result) {
        String firebaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        getCollection("users_words")
                .whereEqualTo("user_id", firebaseUser)
                .get()
                .addOnFailureListener(e -> result.onError())
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    ArrayList<UserWordsModel> userWordsModelArrayList = new ArrayList<>();
                    List<DocumentSnapshot> wordsUsedDocuments =
                            queryDocumentSnapshots.getDocuments();

                    for (int i = 1; i < wordsUsedDocuments.size(); i++) {
                        userWordsModelArrayList.add(new UserWordsModel(
                                (String) wordsUsedDocuments.get(i).get("user_id"),
                                (String) wordsUsedDocuments.get(i).get("word_id"),
                                Integer.parseInt(wordsUsedDocuments.get(i).get("game_id").toString())));
                    }


                    if (result != null) {
                        result.onResult(userWordsModelArrayList);
                    }
                })
                .

                        addOnFailureListener(e ->

                        {
                            e.printStackTrace();
                            result.onError();
                        });
    }
}
