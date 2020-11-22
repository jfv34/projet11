package com.vincler.jf.projet11.models;

public class BorderColorModel {
    private BorderColorEnum borderColorList;
    private int position;

    public BorderColorModel(BorderColorEnum borderColorList, int position) {
        this.borderColorList = borderColorList;
        this.position = position;
    }

    public BorderColorEnum getBorderColor() {
        return borderColorList;
    }

    public int getPositionPicture() {
        return position;
    }

}
