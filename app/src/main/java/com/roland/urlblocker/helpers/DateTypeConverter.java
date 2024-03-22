package com.roland.urlblocker.helpers;

import androidx.room.TypeConverter;

import java.util.Date;
import java.util.UUID;

public class DateTypeConverter {
    @TypeConverter
    public static Date LongtoDate(Long value) {
        return value == null ? null : new Date(value);
    }
    @TypeConverter
    public static Long DatetoLong(Date value) {
        return value == null ? null : value.getTime();
    }
    @TypeConverter
    public static UUID StringtoUUID(String value) {
        return value.isEmpty() ? UUID.randomUUID() : UUID.fromString(value);
    }
    @TypeConverter
    public static String UUIDtoString(UUID value) {
        return value == null ? "" : value.toString();
    }

}
