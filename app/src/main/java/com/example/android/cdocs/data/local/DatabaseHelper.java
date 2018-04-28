package com.example.android.cdocs.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.android.cdocs.ui.model.Docs;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    private static DatabaseHelper sDatabaseHelper;
    private static SQLiteDatabase sDatabase;

    public static DatabaseHelper getInstance(Context context) {
        if (sDatabaseHelper == null) {
            sDatabaseHelper = new DatabaseHelper();
            sDatabase = new DbOpenHelper(context).getWritableDatabase();
        }
        return sDatabaseHelper;
    }

    /**
     * This method is called to insert list of documents into the database
     *
     * @param docsList list of documents
     */
    public void insertDocuments(List<Docs> docsList) {
        for (Docs docs : docsList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(IDatabase.IContentTable.DOCUMENT_TITLE, docs.getTitle());
            contentValues.put(IDatabase.IContentTable.TYPE, docs.getType());
            contentValues.put(IDatabase.IContentTable.URL, docs.getUrl());
            sDatabase.insert(IDatabase.IContentTable.TABLE_NAME, null, contentValues);
        }
    }

    /**
     * This method is called to insert single item to database
     *
     * @param docs Docs object
     */
    public void inserSIngleItem(Docs docs) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(IDatabase.IContentTable.DOCUMENT_TITLE, docs.getTitle());
        contentValues.put(IDatabase.IContentTable.TYPE, docs.getType());
        contentValues.put(IDatabase.IContentTable.URL, docs.getUrl());
        sDatabase.insert(IDatabase.IContentTable.TABLE_NAME, null, contentValues);
    }

    /**
     * This method is called to get every single data from database table
     *
     * @return data inform of List<Docs> format
     */
    public List<Docs> readDocumentList() {
        List<Docs> docsList = new ArrayList<>();
        String[] projection =
                {
                        IDatabase.IContentTable.DOCUMENT_TITLE,
                        IDatabase.IContentTable.TYPE,
                        IDatabase.IContentTable.URL
                };

        Cursor cursor = sDatabase.query(
                IDatabase.IContentTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            String documentTitle = cursor.getString(cursor.getColumnIndex(IDatabase.IContentTable.DOCUMENT_TITLE));
            String type = cursor.getString(cursor.getColumnIndex(IDatabase.IContentTable.TYPE));
            String url = cursor.getString(cursor.getColumnIndex(IDatabase.IContentTable.URL));
            // Add new Docs object to list
            docsList.add(new Docs(documentTitle, type, url));
        }
        // Close the cursor after it's usage
        cursor.close();
        return docsList;
    }

    /**
     * Delete every single entries form table
     */
    public void deleteAll() {
        sDatabase.delete(IDatabase.IContentTable.TABLE_NAME, null, null);
    }
}
