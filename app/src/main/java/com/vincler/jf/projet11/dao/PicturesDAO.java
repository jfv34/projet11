package com.vincler.jf.projet11.dao;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;
import com.vincler.jf.projet11.repositories.PicturesRepository;

public class PicturesDAO {
    PicturesRepository picturesRepository = new PicturesRepository();

    public Task<QuerySnapshot> getPictures() {
        return picturesRepository.getPictures();
    }




}
