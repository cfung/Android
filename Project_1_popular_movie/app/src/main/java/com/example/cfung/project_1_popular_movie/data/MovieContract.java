package com.example.cfung.project_1_popular_movie.data;

import android.provider.BaseColumns;

/**
 * Created by cfung on 1/14/18.
 */

public class MovieContract {

    public static final class MovieEntry implements BaseColumns{
        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_FAVORITE_MOVIE = "favorite";
    }
}
