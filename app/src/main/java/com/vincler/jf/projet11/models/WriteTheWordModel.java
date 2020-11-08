package com.vincler.jf.projet11.models;

public class WriteTheWordModel {
    private String picture;
    private String word;

    public WriteTheWordModel(String picture, String word) {
        this.picture = picture;
        this.word = word;
    }

    public String getPicture() {
        return picture;
    }

    public String getWord() {
        return word;
    }
}