package com.vincler.jf.projet11.models;

public class ColorModel {
    private ColorEnum colorEnum;
    private int position;

    public ColorModel(ColorEnum colorEnum, int position) {
        this.colorEnum = colorEnum;
        this.position = position;
    }

    public ColorEnum getColorEnum() {
        return colorEnum;
    }

    public int getPosition() {
        return position;
    }

}
