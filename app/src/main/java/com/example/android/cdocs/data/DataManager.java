package com.example.android.cdocs.data;

import android.content.Context;

public class DataManager {
    private static DataManager sDataManager;
    private static PreferenceHelper sPreferenceHelper;

    public static DataManager getInstanace(Context context) {
        if (sDataManager == null) {
            sDataManager = new DataManager();
            sPreferenceHelper = PreferenceHelper.getInstance(context);
        }
        return sDataManager;
    }

    // Write data to SharedPreference
    public void writeDataToPreference(String key, String value) {
        sPreferenceHelper.writeString(key, value);
    }

    // Read data from SharedPreference
    public String readDataFromPreference(String key) {
        return sPreferenceHelper.readString(key);
    }
}