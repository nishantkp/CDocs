package com.example.android.cdocs.ui.model;

public class Docs {
    private final String title;
    private final String type;
    private final String url;

    public Docs(String title, String type, String url) {
        this.title = title;
        this.type = type;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }
}
