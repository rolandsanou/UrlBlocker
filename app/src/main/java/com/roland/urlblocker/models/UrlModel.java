package com.roland.urlblocker.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.roland.urlblocker.database.UrlDatabaseHelper;

import java.util.UUID;

@Entity(tableName = UrlDatabaseHelper.TABLE_URL)
public class UrlModel {
    @PrimaryKey
    @ColumnInfo(name = "Id")
    @SerializedName("Id")
    @NonNull
    private UUID id;
    @ColumnInfo(name = "Name")
    @SerializedName("Name")
    private String name;
    @ColumnInfo(name = "Date")
    @SerializedName("Date")
    private long date;
    public UUID getId(){return id;}
    public String getName(){return name;}
    public long getDate(){return date;}
    public void setId(UUID id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setDate(long date){this.date = date;}
}
