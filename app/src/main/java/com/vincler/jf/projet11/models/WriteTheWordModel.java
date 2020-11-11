package com.vincler.jf.projet11.models;

public class WriteTheWordModel {
    private String picture;
    private String word;
    private String wordId;

    public WriteTheWordModel(String picture, String word, String wordId) {
        this.picture = picture;
        this.word = word;
        this.wordId = wordId;
    }

    public String getPicture() {
        return picture;
    }

    public String getWord() {
        return word;
    }

    public String getWordId() {
        return wordId;
    }
}