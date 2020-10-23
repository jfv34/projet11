package com.vincler.jf.projet11.models;

public class FindThePictureModel {
    private String word;
    private int correctPicturePosition;
    private String topLeftPicture;
    private String topRightPicture;
    private String bottomLeftPicture;
    private String bottomRightPicture;

    public FindThePictureModel(String word, int correctPositionPicture,
                               String topLeftPicture, String topRightPicture, String bottomLeftPicture, String bottomRightPicture) {
        this.word = word;
        this.correctPicturePosition = correctPositionPicture;
        this.topLeftPicture = topLeftPicture;
        this.topRightPicture = topRightPicture;
        this.bottomLeftPicture = bottomLeftPicture;
        this.bottomRightPicture = bottomRightPicture;
    }

    public String getWord() {
        return word;
    }

    public int getCorrectPicturePosition() {
        return correctPicturePosition;
    }

    public String getTopLeftPicture() {
        return topLeftPicture;
    }

    public String getTopRightPicture() {
        return topRightPicture;
    }

    public String getBottomLeftPicture() {
        return bottomLeftPicture;
    }

    public String getBottomRightPicture() {
        return bottomRightPicture;
    }
    }