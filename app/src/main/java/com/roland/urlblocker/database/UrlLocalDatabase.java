package com.roland.urlblocker.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.roland.urlblocker.helpers.DateTypeConverter;
import com.roland.urlblocker.models.UrlModel;


@Database(entities = {UrlModel.class}, version = 2, exportSchema = false)
@TypeConverters(DateTypeConverter.class)
public abstract class UrlLocalDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "url_database_v01.db";
    public abstract UrlDatabaseDao UrlDatabaseDao();
    private static UrlLocalDatabase urlLocalDatabase;

   public static UrlLocalDatabase getInstance(final Context context){
       if(urlLocalDatabase == null){
           synchronized (UrlLocalDatabase.class){
               if(urlLocalDatabase == null){
                   urlLocalDatabase = Room.databaseBuilder(context, UrlLocalDatabase.class,
                           DATABASE_NAME)
                           .allowMainThreadQueries()
                           .addMigrations(MIGRATION_1_2)
                           .build();
               }
           }
       }

       return urlLocalDatabase;
   }

   private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
       @Override
       public void migrate(@NonNull SupportSQLiteDatabase supportSQLiteDatabase) {
            supportSQLiteDatabase.execSQL( UrlDatabaseHelper.TABLE_CREATE_URL);
       }
   };
}
