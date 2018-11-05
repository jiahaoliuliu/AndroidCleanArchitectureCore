package com.jiahaoliuliu.datalayer.moviesrepository;

import android.content.Context;

import com.jiahaoliuliu.domain.IMovie;
import com.jiahaoliuliu.networklayer.ITMDBService;
import com.jiahaoliuliu.networklayer.NetworkModule;
import com.jiahaoliuliu.storagelayer.MoviesDatabase;
import com.jiahaoliuliu.storagelayer.MoviesDatabaseModule;

import java.util.List;

import io.reactivex.Single;

public class MoviesRepository implements IMoviesRepository {

    private Context context;
    // TODO: Inject this
    private NetworkModule networkModule;
    private ITMDBService tmdbService;
    // TODO: Inject this
    private MoviesDatabaseModule moviesDatabaseModule;
    private MoviesDatabase moviesDatabase;

    public MoviesRepository(Context context) {
        this.context = context;
        this.networkModule = new NetworkModule();
        this.tmdbService = networkModule.provideITmdbService();
        this.moviesDatabaseModule = new MoviesDatabaseModule();
        this.moviesDatabase = moviesDatabaseModule.provideMoviesDatabase(context);
    }

    @Override
    public Single<List<? extends IMovie>> retrieveMoviesList() {
        // TODO: Check if the network is available. If not, use the cache or memory
        return tmdbService.getMoviesList()
                .map(moviesList -> moviesList.getMoviesList());
    }
}
