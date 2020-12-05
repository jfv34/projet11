package com.vincler.jf.projet11.models;

public class ColorModel {
    private ColorEnum colorEnum;
    private int position;
    private ColorEnum colorEnum2;
    private int position2;

    public ColorModel(ColorEnum colorEnum, int position, ColorEnum colorEnum2, int position2) {
        this.colorEnum = colorEnum;
        this.position = position;
        this.colorEnum2 = colorEnum2;
        this.position2 = position2;

    }

    public ColorEnum getColorEnum() {
        return colorEnum;
    }

    public int getPosition() {
        return position;
    }

    public ColorEnum getColorEnum2() {
        return colorEnum2;
    }

    public int getPosition2() {
        return position2;
    }

}
