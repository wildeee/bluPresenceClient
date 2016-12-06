package br.com.wilderossi.blupresenceclient.transaction.database;

import android.database.sqlite.SQLiteDatabase;

public interface TableCreator {

    void onCreate(SQLiteDatabase db);
    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

}