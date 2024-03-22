package com.roland.urlblocker.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.roland.urlblocker.models.UrlModel;

import java.util.List;

@Dao
public interface UrlDatabaseDao {
    @Insert
    void insertUrl(UrlModel urlModel);
    @Update
    void updateUrl(UrlModel urlModel);
    @Delete
    void deleteUrl(UrlModel urlModel);
    @Query("SELECT * FROM BlockedUrls")
    List<UrlModel>getBlockedList();
}
