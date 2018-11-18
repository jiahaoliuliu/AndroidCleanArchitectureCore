package com.jiahaoliuliu.domain;

public interface IMovie {

    String ImagesBaseUrl = "https://image.tmdb.org/t/p/w300";

    String getId();

    String getTitle();

    String getDescription();

    String getImageUrl();

    /**
     * Get the absolute url of the image
     * @return
     *  The absolute url of the image
     */
    String getAbsoluteImageUrl();

}
