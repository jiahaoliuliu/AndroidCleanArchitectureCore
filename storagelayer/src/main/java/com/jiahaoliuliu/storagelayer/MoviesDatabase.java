package com.jiahaoliuliu.storagelayer;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Movie.class}, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "movies_database";

    public abstract MovieDao movieDao();
}
