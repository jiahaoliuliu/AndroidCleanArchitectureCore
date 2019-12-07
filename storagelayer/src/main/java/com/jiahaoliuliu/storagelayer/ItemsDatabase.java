package com.jiahaoliuliu.storagelayer;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ItemImpl.class}, version = 1, exportSchema = false)
public abstract class ItemsDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "items_database";

    public abstract ItemDao itemDao();
}
