package com.example.android.cdocs.ui.model;

public class Docs {
    private final String title;
    private final String type;

    public Docs(String title, String type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }
}
