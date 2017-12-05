package com.example.cfung.project_1_popular_movie;

/**
 * Created by cfung on 10/4/17.
 */

public class MovieModel {

    String movieName;
    String movieLink;
    int image; // drawable reference id

    public MovieModel(String mName, int mImage)
    {
        this.movieName = mName;
        this.image = mImage;
    }

    public MovieModel(String mName, String mLink)
    {
        this.movieName = mName;
        this.movieLink = mLink;
    }

    public String getMovieLink(){
        return this.movieLink;
    }
}
