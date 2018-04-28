package com.example.android.cdocs.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Docs implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.type);
        dest.writeString(this.url);
    }

    protected Docs(Parcel in) {
        this.title = in.readString();
        this.type = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Docs> CREATOR = new Parcelable.Creator<Docs>() {
        @Override
        public Docs createFromParcel(Parcel source) {
            return new Docs(source);
        }

        @Override
        public Docs[] newArray(int size) {
            return new Docs[size];
        }
    };
}
