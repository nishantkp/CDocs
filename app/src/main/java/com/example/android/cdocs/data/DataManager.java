package com.example.android.cdocs.data;

import android.content.Context;

import com.example.android.cdocs.data.local.DatabaseHelper;
import com.example.android.cdocs.ui.model.Docs;

import java.util.List;

public class DataManager {
    private static DataManager sDataManager;
    private static PreferenceHelper sPreferenceHelper;
    private static DatabaseHelper sDatabaseHelper;

    public static DataManager getInstance(Context context) {
        if (sDataManager == null) {
            sDataManager = new DataManager();
            sPreferenceHelper = PreferenceHelper.getInstance(context);
            sDatabaseHelper = DatabaseHelper.getInstance(context);
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

    // Insert data into database table
    public void insertDataToDatabase(List<Docs> docsList) {
        sDatabaseHelper.insertDocuments(docsList);
    }

    // Insert single item into database
    public void insertSingleItemToDatabase(Docs docs) {
        sDatabaseHelper.insertSingleItem(docs);
    }

    // Read data from database
    public List<Docs> readDataFromDatabase() {
        return sDatabaseHelper.readDocumentList();
    }

    // Delete all the data from database table
    public void deleteDataFromDatabase() {
        sDatabaseHelper.deleteAll();
    }

    // Clear preferences when user clicks on logout
    public void logout() {
        sPreferenceHelper.clearPreferences();
    }
}