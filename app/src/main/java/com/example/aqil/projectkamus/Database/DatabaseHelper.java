package com.example.aqil.projectkamus.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.aqil.projectkamus.Database.DatabaseContract.DictionaryColuumns.TITLE;
import static com.example.aqil.projectkamus.Database.DatabaseContract.DictionaryColuumns.TRANSLATION;
import static com.example.aqil.projectkamus.Database.DatabaseContract.DictionaryColuumns._ID;
import static com.example.aqil.projectkamus.Database.DatabaseContract.TABLE_NAME_EI;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbDictionary";
    private static final int DATABASE_VERSION = 1;
    public static String CREATE_TABLE_IE = "create table " + DatabaseContract.TABLE_NAME_IE +
            " (" + _ID + " integer primary key autoincrement, " +
            TITLE + " text not null, " +
            TRANSLATION + " text not null);";
    public static String CREATE_TABLE_EI = "create table " + DatabaseContract.TABLE_NAME_EI +
            " (" + _ID + " integer primary key autoincrement, " +
            TITLE + " text not null, " +
            TRANSLATION + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_IE);
        db.execSQL(CREATE_TABLE_EI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_NAME_EI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_EI);
        onCreate(db);
    }
}
