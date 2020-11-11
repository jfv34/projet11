package com.vincler.jf.projet11.models;

public class UserWordsModel {
    private String user_id;
    private String word_id;
    private int game_id;

    public UserWordsModel(String user_id, String word_id, int game_id) {
        this.user_id = user_id;
        this.word_id = word_id;
        this.game_id = game_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getWord_id() {
        return word_id;
    }

    public void setWord_id(String word_id) {
        this.word_id = word_id;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }
}