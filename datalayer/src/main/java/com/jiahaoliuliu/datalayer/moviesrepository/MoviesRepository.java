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
import java.lang.Math;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MoviesRepository implements IMoviesRepository {

    private static final String TAG = "MoviesRepository";

    private Context context;
    // TODO: Inject this
    private NetworkModule networkModule;
    private ITMDBService tmdbService;
    // TODO: Inject this
    private MoviesDatabaseModule moviesDatabaseModule;
    private MoviesDatabase moviesDatabase;
    // Temporal memory for the movies list
    private List<? extends IMovie> moviesList = new ArrayList<>();

    public MoviesRepository(Context context) {
        this.context = context;
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

        return Single.concat(backendSource, storageSource,cacheSource)
                .filter(source -> {
                    return !source.isEmpty();
                })
                .first(moviesList);
    }

    private Observable<? extends List<? extends IMovie>> retrieveMoviesListFromBackend() {
        return tmdbService.getMoviesList()
             .map(moviesListBackend -> moviesListBackend.getMoviesList())
             .doOnSuccess(moviesList -> {
                // Updates the internal cache
                saveMoviesListToCache(moviesList);
                // Save the content into the database
                for (IMovie movie: moviesList) {
                    Log.v(TAG, "Trying to save " + movie + " into the database");
                    moviesDatabase.movieDao().upsert(new Movie(movie));
             }})
            .onErrorResumeNext(Observable.empty().single(moviesList));

        Single<Integer> numbers = Single.just(() -> 1, (state, emitter) -> {
            emitter.onNext(state);

            return state + 1;
        });

        numbers.scan((number1, number2) -> Math.abs(number1))
                .onErrorResumeNext(Observable.empty())
                .subscribe(
                        System.out::println,
                        error -> System.err.println("onError should not be printed!"));
    }

    private Single<? extends List<? extends IMovie>> retrieveMoviesListFromCache() {
        return Single.just(moviesList);
    }

    private Single<? extends List<? extends IMovie>> retrieveMoviesListFromStorage() {
        return moviesDatabase.movieDao().getAllMovies()
                .doOnSuccess(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        saveMoviesListToCache(movies);
                    }
                });
//                .doOnSuccess(this::saveMoviesListToCache);
    }
    private void saveMoviesListToCache(List<? extends IMovie> newMovieList) {
        this.moviesList = newMovieList;
    }
}
