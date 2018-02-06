package com.example.cfung.project_1_popular_movie.data;

import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by cfung on 1/14/18.
 */

public class MovieContract {


        public static final String AUTHORITY = "com.example.cfung.project_1_popular_movie";
        // public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY );

        public static  final String PATH_MOVIE = "movie"; // TODO:  is this correct?

        public static final class MovieEntry implements BaseColumns {

            public static final String TABLE_NAME = "movie";
            // public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();
            public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();
            //Log.v("popularmovie", "what is content uri: ");
            //BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE) .build();

            //public static Uri buildMovieUri(long id){
                //return ContentUris.withAppendedId(CONTENT_URI, id);
            //}

            public static final String COLUMN_MOVIE_NAME = "name";
            public static final String COLUMN_MOVIE_POPULARITY = "popularity";
            public static final String COLUMN_MOVIE_POSTER_PATH = "poster_path";
            public static final String COLUMN_MOVIE_OVERVIEW = "overview";
            public static final String COLUMN_MOVIE_VOTE_AVG = "vote_average";
            public static final String COLUMN_MOVIE_RELEASE_DATE = "release_date";
            public static final String COLUMN_MOVIE_ID = "id";
            public static final String COLUMN_MOVIE_REVIEWS = "reviews";
            public static final String COLUMN_MOVIE_TRAILER = "trailer";

        }
}
