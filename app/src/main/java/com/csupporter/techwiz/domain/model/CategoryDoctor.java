package com.csupporter.techwiz.domain.model;

public class CategoryDoctor {
    private int imageResource;
    private String nameType;

    public CategoryDoctor(int imageResource, String nameType) {
        this.imageResource = imageResource;
        this.nameType = nameType;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }
}
