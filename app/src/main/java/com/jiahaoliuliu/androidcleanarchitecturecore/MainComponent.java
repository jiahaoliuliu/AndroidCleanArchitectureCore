package com.jiahaoliuliu.androidcleanarchitecturecore;

import com.jiahaoliuliu.androidcleanarchitecturecore.movieslist.MoviesListActivity;
import com.jiahaoliuliu.androidcleanarchitecturecore.movieslist.MoviesListModule;
import com.jiahaoliuliu.datalayer.moviesrepository.MoviesRepositoryModule;
import com.jiahaoliuliu.networklayer.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        ApplicationModule.class, MoviesListModule.class,
        MoviesRepositoryModule.class,
        // TODO: Add repository as subcomponent so the app module does
        // not depends on the network module
        NetworkModule.class
})
@Singleton
public interface MainComponent {

    void inject(MoviesListActivity moviesListActivity);
}
