package com.jiahaoliuliu.androidcleanarchitecturecore.movieslist;

import android.util.Log;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MoviesListPresenter implements MoviesListContract.Presenter {

    private static final String TAG = "MoviesListPresenter";

    private final MoviesListContract.Model model;

    private MoviesListContract.View view;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public MoviesListPresenter(MoviesListContract.Model model) {
        this.model = model;
    }

    @Override
    public void setView(MoviesListContract.View view) {
        this.view = view;
    }

    @Override
    public void retrieveMoviesList() {
        compositeDisposable.add(model.retrieveMoviesList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                moviesList -> view.showMoviesList(moviesList),
                throwable -> {
                    // TODO: Show the error on the screen
                    Log.e(TAG, "Error getting the list of the movies from backend ", throwable);
                }));
    }

    @Override
    public void dispose() {
        compositeDisposable.dispose();
    }
}
