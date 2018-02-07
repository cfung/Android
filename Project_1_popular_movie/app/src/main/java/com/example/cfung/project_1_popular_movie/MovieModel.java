package com.example.cfung.project_1_popular_movie;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by cfung on 10/4/17.
 */

public class MovieModel implements Parcelable{

    private String movieName;
    private String popularity;
    private String movieLink;
    private String overview;
    private String vote_average;
    private String release_date;
    private String id;
    private ArrayList<String> reviews;
    private String trailer;
    //private int image; // drawable reference id

    public MovieModel(String mName, String poplularity, String mLink, String mOverview, String mVote_average, String mRelease_date, String mID, ArrayList<String> mReviews, String mTrailer)
    {
        this.movieName = mName;
        this.popularity = poplularity;
        this.movieLink = mLink;
        this.overview = mOverview;
        this.vote_average = mVote_average;
        this.release_date = mRelease_date;
        this.id = mID;
        this.reviews = mReviews;
        this.trailer = mTrailer;
    }

    protected MovieModel(Parcel in) {
        movieName = in.readString();
        popularity = in.readString();
        movieLink = in.readString();
        overview = in.readString();
        vote_average = in.readString();
        release_date = in.readString();
        id = in.readString();
        reviews = in.createStringArrayList();
        trailer = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public String getMovieName(){ return this.movieName;}
    public String getPopularity(){ return this.popularity;}
    public String getMovieLink(){return this.movieLink;}
    public String getOverview(){return this.overview;}
    public String getVote_average(){return this.vote_average;}
    public String getRelease_date(){return this.release_date;}
    public String getMovieID(){return this.id;}
    public ArrayList<String> getMovieReviews(){return this.reviews;}
    public String getTrailer(){return this.trailer;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieName);
        parcel.writeString(popularity);
        parcel.writeString(movieLink);
        parcel.writeString(overview);
        parcel.writeString(vote_average);
        parcel.writeString(release_date);
        parcel.writeString(id);
        parcel.writeStringList(reviews);
        parcel.writeString(trailer);
    }
}
