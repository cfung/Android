package com.example.cfung.project_1_popular_movie;

/**
 * Created by cfung on 10/4/17.
 */

public class MovieModel {

    private String movieName;
    private String movieLink;
    //private int image; // drawable reference id

    public MovieModel(String mName, String mLink)
    {
        this.movieName = mName;
        this.movieLink = mLink;
    }

    public String getMovieName(){ return this.movieName;}
    public String getMovieLink(){return this.movieLink;}

}
