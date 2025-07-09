package com.example.findinglogs.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "weather.db";
    private static final int DATABASE_VERSION = 1;

    public WeatherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_HISTORY_TABLE =
                "CREATE TABLE " + WeatherContract.WeatherEntry.TABLE_NAME + " (" +
                        WeatherContract.WeatherEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        WeatherContract.WeatherEntry.COLUMN_CITY_NAME + " TEXT NOT NULL, " +
                        WeatherContract.WeatherEntry.COLUMN_TIMESTAMP + " INTEGER" +
                        ");";

        db.execSQL(SQL_CREATE_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Desnecessário neste exemplo
    }
}
