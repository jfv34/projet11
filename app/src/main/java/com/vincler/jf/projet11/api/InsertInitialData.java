package com.vincler.jf.projet11.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vincler.jf.projet11.models.LanguageEnum;
import com.vincler.jf.projet11.models.PictureModel;
import com.vincler.jf.projet11.models.WordModel;
import com.vincler.jf.projet11.utils.Utils;

import java.util.ArrayList;

public class InsertInitialData {

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getCollection(String collection_name) {
        return FirebaseFirestore.getInstance().collection(collection_name);
    }
    // --- CREATE ---

    public static void createInitialData() {
        ArrayList<String> wordsList;
        wordsList = getWordsList();
        int t = 0;

        while (t + 3 <= wordsList.size()) {
            String pictureId = Utils.random(16);
            WordModel french_word = new WordModel(wordsList.get(t), LanguageEnum.FRENCH, pictureId);
            WordModel english_word = new WordModel(wordsList.get(t + 1), LanguageEnum.ENGLISH, pictureId);
            WordModel spanish_word = new WordModel(wordsList.get(t + 2), LanguageEnum.SPAIN, pictureId);
            PictureModel picture_ = new PictureModel(wordsList.get(t + 3));

            insertWord(french_word);
            insertWord(english_word);
            insertWord(spanish_word);
            insertPicture(picture_, pictureId);

            t = t + 4;
        }
    }

    private static Task<Void> insertPicture(PictureModel picture_, String pictureId) {
        return InsertInitialData.getCollection("pictures")
                .document(pictureId)
                .set(picture_);
    }

    public static Task<Void> insertWord(WordModel word) {

        return InsertInitialData.getCollection("words")
                .document(Utils.random(16))
                .set(word);
    }

    private static ArrayList getWordsList() {
        ArrayList<String> wordsList = new ArrayList<>();
        wordsList.add("boîte");// french
        wordsList.add("box");// english
        wordsList.add("caja");// spanish
        wordsList.add("https://live.staticflickr.com/7013/6439082447_e373274dd9_w_d.jpg");// picture

        wordsList.add("cartes");// french
        wordsList.add("cards");// english
        wordsList.add("naipes");// spanish
        wordsList.add("https://live.staticflickr.com/3817/13807142985_7ab44754fc_q_d.jpg");// picture

        wordsList.add("chambre");// french
        wordsList.add("bedroom");// english
        wordsList.add("habitación");// spanish
        wordsList.add("https://live.staticflickr.com/83/269816753_c8e39c3a1b_q_d.jpg");// picture

        wordsList.add("classe");// french
        wordsList.add("class");// english
        wordsList.add("clase");// spanish
        wordsList.add("https://live.staticflickr.com/16/22866190_f91cf2b273_q_d.jpg");// picture

        wordsList.add("cuisine");// french
        wordsList.add("kitchen");// english
        wordsList.add("cocido");// spanish
        wordsList.add("https://live.staticflickr.com/4037/4158892859_9690b34225_q_d.jpg");// picture

        wordsList.add("famille");// french
        wordsList.add("family");// english
        wordsList.add("familia");// spanish
        wordsList.add("https://live.staticflickr.com/2709/4185571886_9b3ee3a481_q_d.jpg");// picture

        wordsList.add("fenêtre");// french
        wordsList.add("window");// english
        wordsList.add("ventana");// spanish
        wordsList.add("https://live.staticflickr.com/8213/29123237311_eb3be6caae_q_d.jpg");// picture

        wordsList.add("fleur");// french
        wordsList.add("flower");// english
        wordsList.add("flor");// spanish
        wordsList.add("https://live.staticflickr.com/3927/32975142044_b9f2ea5f59_q_d.jpg");// picture

        wordsList.add("main");// french
        wordsList.add("hand");// english
        wordsList.add("mano");// spanish
        wordsList.add("https://live.staticflickr.com/206/481987330_2a161eb641_q_d.jpg");// picture

        wordsList.add("maison");// french
        wordsList.add("house");// english
        wordsList.add("Casa");// spanish
        wordsList.add("https://live.staticflickr.com/2422/3798463572_cc9b914af4_q_d.jpg");// picture

        wordsList.add("fête");// french
        wordsList.add("party");// english
        wordsList.add("fiesta");// spanish
        wordsList.add("https://live.staticflickr.com/2864/33969591002_9e4a38c2e4_q_d.jpg");// picture

        wordsList.add("lettre");// french
        wordsList.add("letter");// english
        wordsList.add("letra");// spanish
        wordsList.add("https://live.staticflickr.com/6191/6152637301_ed82292dbd_q_d.jpg");// picture

        wordsList.add("nuit");// french
        wordsList.add("night");// english
        wordsList.add("noche");// spanish
        wordsList.add("https://live.staticflickr.com/4843/33143291508_76a5f8e0fd_q_d.jpg");// picture

        wordsList.add("musique");// french
        wordsList.add("music");// english
        wordsList.add("música");// spanish
        wordsList.add("https://live.staticflickr.com/159/390534244_3db9138593_q_d.jpg");// picture

        return wordsList;
    }
}