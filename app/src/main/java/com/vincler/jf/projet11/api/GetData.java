package com.vincler.jf.projet11.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class GetData {

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getCollection(String collection_name) {
        return FirebaseFirestore.getInstance().collection(collection_name);
    }

    public static Task<QuerySnapshot> getPictures() {
        return getCollection("pictures")
                .get();
    }

    public static Task<DocumentSnapshot> getPicture(String pictureId) {
        return getCollection("pictures")
                .document(pictureId)
                .get();
    }

    public static Task<QuerySnapshot> getWords(String langage_id) {
        return getCollection("words")
                .whereEqualTo("langage_id",langage_id)
                .get();
    }
}