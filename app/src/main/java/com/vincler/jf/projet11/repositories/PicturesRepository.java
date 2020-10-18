package com.vincler.jf.projet11.repositories;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class PicturesRepository {

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getCollection(String collection_name) {
        return FirebaseFirestore.getInstance().collection(collection_name);
    }

    public static Task<QuerySnapshot> getPictures() {

        getCollection("pictures")
                .get()
                .addOnCompleteListener(task ->
                {
                    if (task.getResult() != null) {
                        List<DocumentSnapshot> documents =
                                task.getResult().getDocuments();
                    }
                });
        return null;
    }
}
