package com.example.android.cdocs.utils;

public interface IConstants {
    // Login activity keys
    interface Login {
        String KEY_USER_NAME = "KEY_USER_NAME";
        String KEY_TOKEN = "KEY_TOKEN";
    }

    // Keys for SharedPreferences
    interface Preference {
        String KEY_USER_NAME_PREF = "KEY_USER_NAME_PREF";
        String KEY_TOKEN_PREF = "KEY_TOKEN_PREF";
    }

    // Keys for Firebase message data
    interface Fcm {
        String KEY_FCM_MSG_TITLE = "TITLE";
        String KEY_FCM_MSG_TYPE = "TYPE";
        String KEY_FCM_MSG_URL = "URL";
        String ACTION_FCM_NOTIFICATION = "ACTION_FCM_NOTIFICATION";
    }

    // Keys for Pdf activity
    interface Pdf {
        String KEY_PDF_DOCS = "KEY_PDF_DOCS";
    }
}
