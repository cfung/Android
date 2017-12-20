package com.example.cfung.project_1_popular_movie;

/**
 * Created by cfung on 10/4/17.
 */

public class MovieModel {

    private String movieName;
    private String popularity;
    private String movieLink;
    //private int image; // drawable reference id

    public MovieModel(String mName, String poplularity, String mLink)
    {
        this.movieName = mName;
        this.popularity = poplularity;
        this.movieLink = mLink;
    }

    public String getMovieName(){ return this.movieName;}
    public String getPopularity(){ return this.popularity;}
    public String getMovieLink(){return this.movieLink;}

}
