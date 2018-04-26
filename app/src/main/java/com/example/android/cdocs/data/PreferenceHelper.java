package com.example.android.cdocs.data;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {
    private static PreferenceHelper sPreferenceHelper;
    private static SharedPreferences sSharedPreferences;
    private static SharedPreferences.Editor sEditor;

    // Creates a singleton PreferenceHelper object
    public static PreferenceHelper getInstance(Context context) {
        if (sPreferenceHelper == null) {
            sPreferenceHelper = new PreferenceHelper();
            sSharedPreferences = context.getSharedPreferences("LOGIN_PREFERENCE", Context.MODE_PRIVATE);
            sEditor = sSharedPreferences.edit();
        }
        return sPreferenceHelper;
    }

    // Write data into SharedPreferences
    public void writeString(String key, String value) {
        sEditor.putString(key, value).commit();
    }

    // Get the data from SharedPreferences with specific key, if there is no data to be found
    // then return null
    public String readString(String key) {
        return sSharedPreferences.getString(key, null);
    }

    // Clear all the saved preferences when user clicks logout button
    public void clearPreferences() {
        sEditor.clear().commit();
    }
}