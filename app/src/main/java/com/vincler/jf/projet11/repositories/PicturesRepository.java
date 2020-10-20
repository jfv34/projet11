package com.vincler.jf.projet11.repositories;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class PicturesRepository {

    public static CollectionReference getCollection(String collection_name) {
        return FirebaseFirestore.getInstance().collection(collection_name);
    }

    public static Task<QuerySnapshot> getPictures() {
        return getCollection("pictures")
                .get();
    }
}