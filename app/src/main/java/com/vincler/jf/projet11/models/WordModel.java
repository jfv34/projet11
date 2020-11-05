package com.vincler.jf.projet11.models;

public class WordModel {
    private String word;
    private LanguageEnum language;
    private String picture_id;

    public WordModel(String word, LanguageEnum language, String picture_id) {
        this.word = word;
        this.language = language;
        this.picture_id = picture_id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public LanguageEnum getLanguage() {
        return language;
    }

    public void setLanguage(LanguageEnum language) {
        this.language = language;
    }

    public String getPicture_id() {
        return picture_id;
    }

    public void setPicture_id(String picture_id) {
        this.picture_id = picture_id;
    }
}

