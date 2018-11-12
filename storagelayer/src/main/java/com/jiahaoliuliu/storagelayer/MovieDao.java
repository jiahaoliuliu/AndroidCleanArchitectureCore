package com.jiahaoliuliu.storagelayer;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public abstract class MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    protected abstract void insert(Movie movie);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    protected abstract void update(Movie movie);

    public void upsert(Movie movie) {
        insert(movie);
        update(movie);
    }

    public void upsert(List<Movie> moviesList) {
        for (Movie movie: moviesList) {
            upsert(movie);
        }
    }

    @Delete
    public abstract void delete(Movie movie);

    @Query("Delete from movie_table")
    public abstract void deleteAllMovies();

    @Query("Select * from movie_table order by id desc")
    public abstract LiveData<List<Movie>> getAllMovies();
}
