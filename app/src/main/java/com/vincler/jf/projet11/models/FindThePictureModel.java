package com.vincler.jf.projet11.models;

public class FindThePictureModel {
    private String word;
    private int correctPicturePosition;
    private String topLeftPicture;
    private String topRightPicture;
    private String bottomLeftPicture;
    private String bottomRightPicture;
    private Result result;

    public FindThePictureModel(String word, int correctPositionPicture,
                               String topLeftPicture, String topRightPicture, String bottomLeftPicture, String bottomRightPicture, Result result) {
        this.word = word;
        this.correctPicturePosition = correctPositionPicture;
        this.topLeftPicture = topLeftPicture;
        this.topRightPicture = topRightPicture;
        this.bottomLeftPicture = bottomLeftPicture;
        this.bottomRightPicture = bottomRightPicture;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCorrectPicturePosition() {
        return correctPicturePosition;
    }

    public void setCorrectPicturePosition(int correctPicturePosition) {
        this.correctPicturePosition = correctPicturePosition;
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
    }