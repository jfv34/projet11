package com.vincler.jf.projet11.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vincler.jf.projet11.models.Picture_;
import com.vincler.jf.projet11.models.Word;
import com.vincler.jf.projet11.utils.GetStringUtils;

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
            String pictureId = GetStringUtils.random(16);
            Word french_word = new Word(wordsList.get(t), "0", pictureId);
            Word english_word = new Word(wordsList.get(t + 1), "1", pictureId);
            Word spanish_word = new Word(wordsList.get(t + 2), "2", pictureId);
            Picture_ picture_ = new Picture_(wordsList.get(t + 3));

            insertWord(french_word);
            insertWord(english_word);
            insertWord(spanish_word);
            insertPicture(picture_, pictureId);

            t = t + 4;
        }
    }

    private static Task<Void> insertPicture(Picture_ picture_, String pictureId) {
        return InsertInitialData.getCollection("pictures")
                .document(pictureId)
                .set(picture_);
    }

    public static Task<Void> insertWord(Word word) {

        return InsertInitialData.getCollection("words")
                .document(GetStringUtils.random(16))
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
        wordsList.add("https://www.flickr.com/photos/nettlesphotography/4733764265/sizes/q/");// picture

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
        wordsList.add("https://www.flickr.com/photos/cheishichiyo/4675450200/sizes/q/");// picture

        wordsList.add("main");// french
        wordsList.add("hand");// english
        wordsList.add("mano");// spanish
        wordsList.add("https://live.staticflickr.com/206/481987330_2a161eb641_q_d.jpg");// picture

        wordsList.add("maison");// french
        wordsList.add("house");// english
        wordsList.add("Casa");// spanish
        wordsList.add("https://www.flickr.com/photos/parismadrid/6883150650/sizes/q/");// picture

        return wordsList;
    }
}