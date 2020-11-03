package com.vincler.jf.projet11.models;

public class FindTheWordModel {
    private String picture;
    private int correctWordPosition;
    private String topLeftWord;
    private String topRightWord;
    private String bottomLeftWord;
    private String bottomRightWord;

    public FindTheWordModel(String picture, int correctWordPosition,
                            String topLeftWord, String topRightWord, String bottomLeftWord, String bottomRightWord) {
        this.picture = picture;
        this.correctWordPosition = correctWordPosition;
        this.topLeftWord = topLeftWord;
        this.topRightWord = topRightWord;
        this.bottomLeftWord = bottomLeftWord;
        this.bottomRightWord = bottomRightWord;
    }

    public String getPicture() {
        return picture;
    }

    public int getCorrectWordPosition() {
        return correctWordPosition;
    }

    public String getTopLeftWord() {
        return topLeftWord;
    }

    public String getTopRightWord() {
        return topRightWord;
    }

    public String getBottomLeftWord() {
        return bottomLeftWord;
    }

    public String getBottomRightWord() {
        return bottomRightWord;
    }
}