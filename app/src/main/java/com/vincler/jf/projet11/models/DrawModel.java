package com.vincler.jf.projet11.models;

public class DrawModel {
    private String word;
    private String picture;
    private int correctPositionPicture;
    private int correctPositionWord;
    private String topLeftPicture;
    private String topRightPicture;
    private String bottomLeftPicture;
    private String bottomRightPicture;
    private String topLeftWord;
    private String topRightWord;
    private String bottomLeftWord;
    private String bottomRightWord;

    public DrawModel(String word, String picture, int correctPositionPicture, int correctPositionWord,
                     String topLeftPicture, String topRightPicture, String bottomLeftPicture, String bottomRightPicture,
                     String topLeftWord, String topRightWord, String bottomLeftWord, String bottomRightWord) {
        this.word = word;
        this.picture = picture;
        this.correctPositionPicture = correctPositionPicture;
        this.correctPositionWord = correctPositionWord;
        this.topLeftPicture = topLeftPicture;
        this.topRightPicture = topRightPicture;
        this.bottomLeftPicture = bottomLeftPicture;
        this.bottomRightPicture = bottomRightPicture;
        this.topLeftWord = topLeftWord;
        this.topRightWord = topRightWord;
        this.bottomLeftWord = bottomLeftWord;
        this.bottomRightWord = bottomRightWord;




    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getCorrectPositionPicture() {
        return correctPositionPicture;
    }

    public void setCorrectPositionPicture(int correctPositionPicture) {
        this.correctPositionPicture = correctPositionPicture;
    }

    public int getCorrectPositionWord() {
        return correctPositionWord;
    }

    public void setCorrectPositionWord(int correctPositionWord) {
        this.correctPositionWord = correctPositionWord;
    }

    public String getTopLeftPicture() {
        return topLeftPicture;
    }

    public void setTopLeftPicture(String topLeftPicture) {
        this.topLeftPicture = topLeftPicture;
    }

    public String getTopRightPicture() {
        return topRightPicture;
    }

    public void setTopRightPicture(String topRightPicture) {
        this.topRightPicture = topRightPicture;
    }

    public String getBottomLeftPicture() {
        return bottomLeftPicture;
    }

    public void setBottomLeftPicture(String bottomLeftPicture) {
        this.bottomLeftPicture = bottomLeftPicture;
    }

    public String getBottomRightPicture() {
        return bottomRightPicture;
    }

    public void setBottomRightPicture(String bottomRightPicture) {
        this.bottomRightPicture = bottomRightPicture;
    }

    public String getTopLeftWord() {
        return topLeftWord;
    }

    public void setTopLeftWord(String topLeftWord) {
        this.topLeftWord = topLeftWord;
    }

    public String getTopRightWord() {
        return topRightWord;
    }

    public void setTopRightWord(String topRightWord) {
        this.topRightWord = topRightWord;
    }

    public String getBottomLeftWord() {
        return bottomLeftWord;
    }

    public void setBottomLeftWord(String bottomLeftWord) {
        this.bottomLeftWord = bottomLeftWord;
    }

    public String getBottomRightWord() {
        return bottomRightWord;
    }

    public void setBottomRightWord(String bottomRightWord) {
        this.bottomRightWord = bottomRightWord;
    }
}
