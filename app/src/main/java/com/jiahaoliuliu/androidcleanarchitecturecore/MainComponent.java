package com.jiahaoliuliu.androidcleanarchitecturecore;

import com.jiahaoliuliu.androidcleanarchitecturecore.movieslist.MoviesListActivity;
import com.jiahaoliuliu.androidcleanarchitecturecore.movieslist.MoviesListModule;
import com.jiahaoliuliu.datalayer.moviesrepository.MoviesRepositoryModule;
import com.jiahaoliuliu.networklayer.NetworkModule;
import com.jiahaoliuliu.storagelayer.MoviesDatabaseModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        ApplicationModule.class, MoviesListModule.class,
        MoviesRepositoryModule.class,
        NetworkModule.class, MoviesDatabaseModule.class
})
@Singleton
public interface MainComponent {
    void inject(MoviesListActivity moviesListActivity);
}
