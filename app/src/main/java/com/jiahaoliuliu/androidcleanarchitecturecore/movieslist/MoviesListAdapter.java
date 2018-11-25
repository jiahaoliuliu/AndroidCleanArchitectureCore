package com.jiahaoliuliu.androidcleanarchitecturecore.movieslist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiahaoliuliu.androidcleanarchitecturecore.R;
import com.jiahaoliuliu.androidcleanarchitecturecore.databinding.MovieItemBinding;
import com.jiahaoliuliu.domain.IMovie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MovieHolder>{

    private List<? extends IMovie> moviesList;
    private Picasso picasso;

    public MoviesListAdapter() {
        this.moviesList = new ArrayList<>();
        this.picasso = Picasso.get();
    }

    public void setMoviesList(List<? extends IMovie> moviesList) {
        this.moviesList = moviesList;
        notifyDataSetChanged();
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());

        MovieItemBinding movieItemBinding =
                MovieItemBinding.inflate(layoutInflater, parent, false);
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.movie_item, parent, false);
        return new MovieHolder(movieItemBinding);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        IMovie movie = moviesList.get(position);
        holder.bind(movie);
//        holder.setMovie(movie);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {

        private MovieItemBinding movieItemBinding;
        private ImageView image;

        public MovieHolder(MovieItemBinding movieItemBinding) {
            super(movieItemBinding.getRoot());
            this.movieItemBinding = movieItemBinding;
            this.image = movieItemBinding.movieImage;
        }

        public void bind(IMovie movie) {
            picasso.load(movie.getAbsoluteImageUrl()).into(image);
            movieItemBinding.setMovie(movie);
            movieItemBinding.executePendingBindings();
        }
    }
}
