package com.example.android.cdocs.data.local;

public interface IDatabase {

    interface IContentTable {
        String TABLE_NAME = "documents";
        String ID = "id";
        String DOCUMENT_TITLE = "document_title";
        String TYPE = "type";

        // Raw SQL statement for creating a table
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER PRIMARY KEY, "
                + DOCUMENT_TITLE + " TEXT, "
                + TYPE + " TEXT" + ")";

        // Raw SQL statement for delete table is it exists in database
        String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
