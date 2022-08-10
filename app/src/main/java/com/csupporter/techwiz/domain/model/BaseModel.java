package com.csupporter.techwiz.domain.model;

import com.google.firebase.firestore.Exclude;

abstract class BaseModel {

    private String id;

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
