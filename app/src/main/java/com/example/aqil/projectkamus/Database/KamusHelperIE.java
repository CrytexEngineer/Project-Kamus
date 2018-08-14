package com.example.aqil.projectkamus.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.aqil.projectkamus.Model.KamusItem;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.aqil.projectkamus.Database.DatabaseContract.DictionaryColuumns.TITLE;
import static com.example.aqil.projectkamus.Database.DatabaseContract.DictionaryColuumns.TRANSLATION;

public class KamusHelperIE {
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public KamusHelperIE(Context context) {
        this.context = context;


    }

    public KamusHelperIE open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getReadableDatabase();
        return this;
    }


    public void close() {
        databaseHelper.close();
    }


    public ArrayList<KamusItem> getAllData( ) {
        String result = null;
        Cursor cursor;

        cursor = database.query(DatabaseContract.TABLE_NAME_IE, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<KamusItem> arrayList = new ArrayList<>();
        KamusItem kamusItem;
        if (cursor.getCount() > 0) {
            do {
                kamusItem = new KamusItem();
                kamusItem.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusItem.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                kamusItem.setTranslation(cursor.getString(cursor.getColumnIndexOrThrow(TRANSLATION)));
                arrayList.add(kamusItem);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());

        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<KamusItem> getDataByName(String title) {
        String result = null;
        Cursor cursor = database.query(DatabaseContract.TABLE_NAME_IE,null,TITLE+" LIKE ?",new String[]{title
        },null,null,_ID + " ASC",null);
        cursor.moveToFirst();
        ArrayList<KamusItem> arrayList = new ArrayList<>();
        KamusItem kamusItem;
        if (cursor.getCount() > 0) {
            do {
                kamusItem = new KamusItem();
                kamusItem.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusItem.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                kamusItem.setTranslation(cursor.getString(cursor.getColumnIndexOrThrow(TRANSLATION)));
                arrayList.add(kamusItem);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());

        }
        cursor.close();
        return arrayList;
    }

    public void beginTransaction() {

 database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }

    public void insertTransaction(KamusItem kamusItem) {
        Log.d("TAG", "insertTransaction: " + kamusItem.getTitle());
        String sql = "INSERT INTO " + DatabaseContract.TABLE_NAME_IE + " (" + TITLE + ", " + TRANSLATION
                + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, kamusItem.getTitle());
        stmt.bindString(2, kamusItem.getTranslation());
        stmt.execute();
        stmt.clearBindings();


    }
}
