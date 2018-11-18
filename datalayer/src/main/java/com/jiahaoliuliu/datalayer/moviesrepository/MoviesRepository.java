package com.jiahaoliuliu.datalayer.moviesrepository;

import android.content.Context;
import android.util.Log;

import com.jiahaoliuliu.domain.IMovie;
import com.jiahaoliuliu.networklayer.ITMDBService;
import com.jiahaoliuliu.networklayer.NetworkModule;
import com.jiahaoliuliu.storagelayer.Movie;
import com.jiahaoliuliu.storagelayer.MoviesDatabase;
import com.jiahaoliuliu.storagelayer.MoviesDatabaseModule;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class MoviesRepository implements IMoviesRepository {

    private static final String TAG = "MoviesRepository";

    // TODO: Inject this
    private NetworkModule networkModule;
    private ITMDBService tmdbService;
    // TODO: Inject this
    private MoviesDatabaseModule moviesDatabaseModule;
    private MoviesDatabase moviesDatabase;
    // Temporal memory for the movies list
    private List<? extends IMovie> memoryCache = new ArrayList<>();

    public MoviesRepository(Context context) {
        this.networkModule = new NetworkModule();
        this.tmdbService = networkModule.provideITmdbService();
        this.moviesDatabaseModule = new MoviesDatabaseModule();
        this.moviesDatabase = moviesDatabaseModule.provideMoviesDatabase(context);
    }

    @Override
    public Single<? extends List<? extends IMovie>> retrieveMoviesList() {
        // List of Priorities
        Single<? extends List<? extends IMovie>> backendSource = retrieveMoviesListFromBackend();
        Single<? extends List<? extends IMovie>> storageSource = retrieveMoviesListFromStorage();
        Single<? extends List<? extends IMovie>> cacheSource = retrieveMoviesListFromCache();

        return Single.concat(backendSource, storageSource, cacheSource)
                .filter(source -> !source.isEmpty())
                .first(memoryCache);
    }

    private Single<? extends List<? extends IMovie>> retrieveMoviesListFromBackend() {
        return tmdbService.getMoviesList()
             .map(moviesListBackend -> moviesListBackend.getMoviesList())
             .doOnSuccess(moviesList -> {
                // Updates the internal cache
                saveMoviesListToCache(moviesList);
                // Save the content into the database
                for (IMovie movie: moviesList) {
                    Log.v(TAG, "Trying to save " + movie + " into the database");
                    moviesDatabase.movieDao().upsert(new Movie(movie));
                }
             }).onErrorResumeNext(throwable -> {
                 Log.e(TAG, "Error retrieving data from backend", throwable);
                 return Single.just(new ArrayList<>());
             });
    }

    private Single<? extends List<? extends IMovie>> retrieveMoviesListFromCache() {
        return Single.just(memoryCache);
    }

    private Single<? extends List<? extends IMovie>> retrieveMoviesListFromStorage() {
        return moviesDatabase.movieDao().getAllMovies()
                .doOnSuccess(moviesList -> saveMoviesListToCache(moviesList))
                .onErrorResumeNext(throwable -> {
                   Log.e(TAG, "Error retrieving data from the database ", throwable);
                   return Single.just(new ArrayList<>());
                });
    }

    private void saveMoviesListToCache(List<? extends IMovie> newMovieList) {
        this.memoryCache = newMovieList;
    }
}
