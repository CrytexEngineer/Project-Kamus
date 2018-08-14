package com.example.aqil.projectkamus.Database;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_NAME_IE = "indonesia_english";
    static String TABLE_NAME_EI = "englishindonesiaFragment";

    static final class DictionaryColuumns implements BaseColumns {
        static String TITLE = "title";
        static String TRANSLATION = "translation";
    }
}
