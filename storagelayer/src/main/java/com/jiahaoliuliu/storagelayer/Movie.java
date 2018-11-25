package com.jiahaoliuliu.storagelayer;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.jiahaoliuliu.domain.IMovie;

import java.util.Date;

@Entity(tableName = "movie_table")
public class Movie implements IMovie {

    @PrimaryKey
    @NonNull
    private String id;

    private String title;

    private String description;

    private String imageUrl;

    private long timeStamp;

    public Movie(String id, String title, String description, String imageUrl, long timeStamp) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.timeStamp = timeStamp;
    }

    public Movie(IMovie anotherMovie) {
        this(anotherMovie.getId(), anotherMovie.getTitle(), anotherMovie.getDescription(),
                anotherMovie.getImageUrl(), new Date().getTime());
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String getAbsoluteImageUrl() {
        return ImagesBaseUrl + imageUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (timeStamp != movie.timeStamp) return false;
        if (!id.equals(movie.id)) return false;
        if (title != null ? !title.equals(movie.title) : movie.title != null) return false;
        if (description != null ? !description.equals(movie.description) : movie.description != null)
            return false;
        return imageUrl != null ? imageUrl.equals(movie.imageUrl) : movie.imageUrl == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (int) (timeStamp ^ (timeStamp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
