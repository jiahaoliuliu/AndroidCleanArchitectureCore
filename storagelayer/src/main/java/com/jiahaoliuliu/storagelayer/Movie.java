package com.jiahaoliuliu.storagelayer;

import android.arch.persistence.room.Entity;

import com.jiahaoliuliu.domain.IMovie;

@Entity
public class Movie implements IMovie {

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getImageUrl() {
        return null;
    }
}
