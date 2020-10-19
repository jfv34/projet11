package com.vincler.jf.projet11.repositories;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class WordsRepository {
    public static CollectionReference getCollection(String collection_name) {
        return FirebaseFirestore.getInstance().collection(collection_name);
    }

    public static Task<QuerySnapshot> getWordByPicture(String langage_id, String picture_id) {
        return getCollection("words")
                .whereEqualTo("langage_id", langage_id)
                .whereEqualTo("picture_id", picture_id)
                .get();
    }
}
