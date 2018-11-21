package com.jiahaoliuliu.androidcleanarchitecturecore.movieslist;

import com.jiahaoliuliu.datalayer.moviesrepository.IMoviesRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviesListModule {

    @Provides
    @Singleton
    MoviesListContract.Presenter providePresenter(MoviesListContract.Model model) {
        return new MoviesListPresenter(model);
    }

    @Provides
    @Singleton
    MoviesListContract.Model provideModel(IMoviesRepository moviesRepository) {
        return new MoviesListModel(moviesRepository);
    }
}
