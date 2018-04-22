package com.example.android.cdocs.application;

import android.app.Application;

import com.twitter.sdk.android.core.Twitter;

public class CdocApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Twitter.initialize(this);
    }
}
