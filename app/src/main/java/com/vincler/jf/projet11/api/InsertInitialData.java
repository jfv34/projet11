package com.vincler.jf.projet11.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
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
        ArrayList<String> wordsList = new ArrayList<>();
        wordsList = getWordsList();
        int t = 0;

        while (t < wordsList.size()) {
            Word french_word = new Word(wordsList.get(t), "0", wordsList.get(t + 3));
            Word english_word = new Word(wordsList.get(t + 1), "1", wordsList.get(t + 3));
            Word spanish_word = new Word(wordsList.get(t + 2), "2", wordsList.get(t + 3));
            

            insertData(french_word);
            insertData(english_word);
            insertData(spanish_word);

            t = t + 4;
        }
    }

    public static Task<Void> insertData(Word word) {

        return InsertInitialData.getCollection("words")
                .document(GetStringUtils.random(16))
                .set(word);
    }

    private static ArrayList getWordsList() {
        ArrayList<String> wordsList = new ArrayList<>();
        wordsList.add("ljlkjlj");// french
        wordsList.add("ljlkjlj");// english
        wordsList.add("ljlkjlj");// spanish
        wordsList.add("ljlkjlj");// picture

        wordsList.add("ljlkjlj");// french
        wordsList.add("ljlkjlj");// english
        wordsList.add("ljlkjlj");// spanish
        wordsList.add("ljlkjlj");// picture

        wordsList.add("ljlkjlj");// french
        wordsList.add("ljlkjlj");// english
        wordsList.add("ljlkjlj");// spanish
        wordsList.add("ljlkjlj");// picture

        wordsList.add("ljlkjlj");// french
        wordsList.add("ljlkjlj");// english
        wordsList.add("ljlkjlj");// spanish
        wordsList.add("ljlkjlj");// picture

        wordsList.add("ljlkjlj");// french
        wordsList.add("ljlkjlj");// english
        wordsList.add("ljlkjlj");// spanish
        wordsList.add("ljlkjlj");// picture

        wordsList.add("ljlkjlj");// french
        wordsList.add("ljlkjlj");// english
        wordsList.add("ljlkjlj");// spanish
        wordsList.add("ljlkjlj");// picture

        wordsList.add("ljlkjlj");// french
        wordsList.add("ljlkjlj");// english
        wordsList.add("ljlkjlj");// spanish
        wordsList.add("ljlkjlj");// picture

        wordsList.add("ljlkjlj");// french
        wordsList.add("ljlkjlj");// english
        wordsList.add("ljlkjlj");// spanish
        wordsList.add("ljlkjlj");// picture

        wordsList.add("ljlkjlj");// french
        wordsList.add("ljlkjlj");// english
        wordsList.add("ljlkjlj");// spanish
        wordsList.add("ljlkjlj");// picture

        wordsList.add("ljlkjlj");// french
        wordsList.add("ljlkjlj");// english
        wordsList.add("ljlkjlj");// spanish
        wordsList.add("ljlkjlj");// picture

return wordsList;
    }



}
