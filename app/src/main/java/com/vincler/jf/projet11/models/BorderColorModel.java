package com.vincler.jf.projet11.models;

public class BorderColorModel {
    private BorderColorList borderColorList;
    private int position;

    public BorderColorModel(BorderColorList borderColorList, int position) {
        this.borderColorList = borderColorList;
        this.position = position;
    }

    public BorderColorList getBorderColor() {
        return borderColorList;
    }

    public int getPositionWord() {
        return position;
    }

}
