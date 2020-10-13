package com.vincler.jf.projet11.models;

public class Word {
    private String word;
    private String langage_id;
    private String picture_id;

    public Word(String word, String langage_id, String picture_id) {
        this.word = word;
        this.langage_id = langage_id;
        this.picture_id = picture_id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getLangage_id() {
        return langage_id;
    }

    public void setLangage_id(String langage_id) {
        this.langage_id = langage_id;
    }

    public String getPicture_id() {
        return picture_id;
    }

    public void setPicture_id(String picture_id) {
        this.picture_id = picture_id;
    }
}

