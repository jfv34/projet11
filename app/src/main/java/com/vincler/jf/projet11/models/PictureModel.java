package com.vincler.jf.projet11.models;

public class PictureModel {
    private String url;

    public PictureModel(String url) {
        this.url = url;
    }

    // Requested by firebase
    public String getUrl() {   // requested by firebase
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}