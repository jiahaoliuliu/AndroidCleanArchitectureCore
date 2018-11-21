package com.jiahaoliuliu.datalayer.moviesrepository;

import com.jiahaoliuliu.networklayer.ITMDBService;
import com.jiahaoliuliu.storagelayer.MoviesDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviesRepositoryModule {

    @Provides
    @Singleton
    IMoviesRepository providesMoviesRepository(ITMDBService tmdbService, MoviesDatabase moviesDatabase) {
        return new MoviesRepository(tmdbService, moviesDatabase);
    }
}
