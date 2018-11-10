package com.jiahaoliuliu.datalayer.moviesrepository;

import android.content.Context;
import android.util.Log;

import com.jiahaoliuliu.domain.IMovie;
import com.jiahaoliuliu.networklayer.ITMDBService;
import com.jiahaoliuliu.networklayer.NetworkModule;
import com.jiahaoliuliu.storagelayer.Movie;
import com.jiahaoliuliu.storagelayer.MoviesDatabase;
import com.jiahaoliuliu.storagelayer.MoviesDatabaseModule;

import java.util.List;

import io.reactivex.Single;

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
    private List<? extends IMovie> moviesList;

    public MoviesRepository(Context context) {
        this.context = context;
        this.networkModule = new NetworkModule();
        this.tmdbService = networkModule.provideITmdbService();
        this.moviesDatabaseModule = new MoviesDatabaseModule();
        this.moviesDatabase = moviesDatabaseModule.provideMoviesDatabase(context);
    }

    @Override
    public Single<List<? extends IMovie>> retrieveMoviesList() {
        Single<List<? extends IMovie>> backendSource;
        Single<List<? extends IMovie>> storageLayerSource;
        Single<List<? extends IMovie>> cacheSource;

        // TODO: Check if the network is available. If not, use the cache or memory
        return retrieveMoviesListFromBackend();
    }

    public Single<List<? extends IMovie>> retrieveMoviesListFromBackend() {
        return tmdbService.getMoviesList()
            .map(moviesListBackend -> {
                // Updates the internal cache
                moviesList = moviesListBackend.getMoviesList();
                // Save the content into the database
                for (IMovie movie: moviesList) {
                    Log.v(TAG, "Trying to save " + movie + " into the database");
                    moviesDatabase.movieDao().insert(new Movie(movie));
                }

                //return the movies list
                return moviesList;
            });
    }
}
