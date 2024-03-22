package com.roland.urlblocker.database;

public class UrlDatabaseHelper {
    private static final String DATABASE_NAME = "url_database_v01.db";
    public static final String TABLE_URL = "BlockedUrls";

    public static final String TABLE_CREATE_URL = " CREATE TABLE IF NOT EXISTS "+ TABLE_URL + " ("+
            "Id TEXT NOT NULL PRIMARY KEY,"+
            "Name TEXT,"+
            "Date INTEGER NOT NULL);";
}
