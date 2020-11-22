package com.vincler.jf.projet11.models;

public class BorderColorModel {
    private ColorEnum borderColorList;
    private int position;

    public BorderColorModel(ColorEnum borderColorList, int position) {
        this.borderColorList = borderColorList;
        this.position = position;
    }

    public ColorEnum getBorderColor() {
        return borderColorList;
    }

    public int getPositionPicture() {
        return position;
    }

}
