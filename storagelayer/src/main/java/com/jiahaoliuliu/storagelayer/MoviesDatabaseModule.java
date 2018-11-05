package com.jiahaoliuliu.storagelayer;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviesDatabaseModule {

    private MoviesDatabase moviesDatabase;

    // TODO: Add it into the module
    @Provides
    @Singleton
    public MoviesDatabase provideMoviesDatabase(Context context) {
        if (moviesDatabase == null) {
            moviesDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    MoviesDatabase.class, MoviesDatabase.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return moviesDatabase;
    }
}
