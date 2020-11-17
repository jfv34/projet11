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




}}