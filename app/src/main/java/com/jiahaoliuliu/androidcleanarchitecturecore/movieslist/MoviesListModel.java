package com.jiahaoliuliu.androidcleanarchitecturecore.movieslist;

import com.jiahaoliuliu.androidcleanarchitecturecore.MainApplication;
import com.jiahaoliuliu.datalayer.moviesrepository.IMoviesRepository;
import com.jiahaoliuliu.domain.IMovie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class MoviesListModel implements MoviesListContract.Model {

    private final IMoviesRepository moviesRepository;

    @Inject
    public MoviesListModel(IMoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @Override
    public Single<? extends List<? extends IMovie>> retrieveMoviesList() {
        return moviesRepository.retrieveMoviesList();
    }
}
