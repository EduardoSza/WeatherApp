package com.example.findinglogs.model.database;

import android.net.Uri;
import android.provider.BaseColumns;

public final class WeatherContract {

    private WeatherContract() {}

    public static final String CONTENT_AUTHORITY = "com.example.findinglogs.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_HISTORY = "weather_history";

    public static class WeatherEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_HISTORY);

        public static final String TABLE_NAME = "weather_history";
        public static final String COLUMN_CITY_NAME = "city";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}
