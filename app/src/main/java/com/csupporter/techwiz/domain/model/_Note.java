package com.csupporter.techwiz.domain.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class _Note implements Serializable {

    private String id;
    private String title;
    private String content;
    private String url;

    public _Note() {
    }

    public _Note(String id, String title, String content, String url) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @NonNull
    @Override
    public String toString() {
        return "id=" + id + "title=" + title + ", content=" + content + ", url=" + url;
    }
}
