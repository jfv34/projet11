package com.vincler.jf.projet11.models;

public class BorderColorModel {
    private BorderColorList borderColorList;
    private int positionPicture;

    public BorderColorModel(BorderColorList borderColorList, int positionPicture) {
        this.borderColorList = borderColorList;
        this.positionPicture = positionPicture;
    }

    public BorderColorList getBorderColor() {
        return borderColorList;
    }

    public int getPositionPicture() {
        return positionPicture;
    }

}
