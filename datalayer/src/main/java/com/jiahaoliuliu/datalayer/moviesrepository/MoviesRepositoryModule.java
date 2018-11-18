package com.jiahaoliuliu.datalayer.moviesrepository;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviesRepositoryModule {

    @Provides
    @Singleton
    IMoviesRepository providesMoviesRepository(Context context) {
        return new MoviesRepository(context);
    }
}
