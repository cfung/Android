package com.example.cfung.project_1_popular_movie;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by cfung on 10/4/17.
 */

@Entity(tableName = "movie")
public class MovieModel implements Parcelable{

    @PrimaryKey
    //@ColumnInfo(name = "moiveid")
    //private String mId;
    @NonNull
    @ColumnInfo(name = "movieName")
    private String movieName;
    @ColumnInfo(name = "popularity")
    private String popularity;
    @ColumnInfo(name = "moiveLink")
    private String movieLink;
    @ColumnInfo(name = "overview")
    private String overview;
    @ColumnInfo(name = "vote_average")
    private String vote_average;
    @ColumnInfo(name = "release_date")
    private String release_date;
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "reviews")
    private ArrayList<String> reviews;
    @ColumnInfo(name = "trailer")
    private String trailer;
    //private int image; // drawable reference id

    //@Ignore
    public MovieModel(String movieName, String popularity, String movieLink, String overview, String vote_average, String release_date, String id, ArrayList<String> reviews, String trailer)
    {
        this.movieName = movieName;
        this.popularity = popularity;
        this.movieLink = movieLink;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.id = id;
        this.reviews = reviews;
        this.trailer = trailer;
    }
    /*
    public MovieModel(String id, String mName, String poplularity, String mLink, String mOverview, String mVote_average, String mRelease_date, String mID, ArrayList<String> mReviews, String mTrailer)
    {
        this.mId = id;
        this.movieName = mName;
        this.popularity = poplularity;
        this.movieLink = mLink;
        this.overview = mOverview;
        this.vote_average = mVote_average;
        this.release_date = mRelease_date;
        this.id = mID;
        this.reviews = mReviews;
        this.trailer = mTrailer;
    }*/

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
    public String getId(){return this.id;}
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
