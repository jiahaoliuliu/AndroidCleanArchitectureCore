package com.jiahaoliuliu.androidcleanarchitecturecore.movieslist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.jiahaoliuliu.androidcleanarchitecturecore.MainApplication;
import com.jiahaoliuliu.androidcleanarchitecturecore.R;
import com.jiahaoliuliu.domain.IMovie;

import java.util.List;

import javax.inject.Inject;

public class MoviesListActivity extends AppCompatActivity implements MoviesListContract.View {

    private static final String TAG = "MoviesListActivity";

    @Inject
    protected MoviesListContract.Presenter presenter;

    private RecyclerView recyclerView;
    private MoviesListAdapter moviesListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);

        MainApplication.getMainComponent().inject(this);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        moviesListAdapter = new MoviesListAdapter();
        recyclerView.setAdapter(moviesListAdapter);

        presenter.setView(this);
        presenter.retrieveMoviesList();
    }

    @Override
    public void showMoviesList(List<? extends IMovie> moviesList) {
        Log.v(TAG, "List of movies retrieved " + moviesList.toString());
        moviesListAdapter.setMoviesList(moviesList);
    }

    @Override
    protected void onDestroy() {
        presenter.dispose();
        super.onDestroy();
    }
}
