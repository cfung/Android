package com.example.cfung.project_1_popular_movie.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Movie;
import android.util.Log;

import com.example.cfung.project_1_popular_movie.MovieModel;

import java.util.ArrayList;

/**
 * Created by cfung on 1/14/18.
 */

public class MovieDBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 1;
    public String databasePath = "";

    private static final String TAG = "MyActivity";

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        databasePath = context.getDatabasePath(MovieDBHelper.DATABASE_NAME).toString();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.v(TAG, "SQLiteHelper onCreate...");

        final String SQL_CREATE_MOVIE_FAVORITE_TABLE = "CREATE TABLE " +
                MovieContract.MovieEntry.TABLE_NAME + " (" +
                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieContract.MovieEntry.COLUMN_MOVIE_NAME + " BOOLEAN NOT NULL" +
                MovieContract.MovieEntry.COLUMN_MOVIE_POPULARITY + " BOOLEAN NOT NULL" +
                MovieContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH + " BOOLEAN NOT NULL" +
                MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW + " BOOLEAN NOT NULL" +
                MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_AVG + " BOOLEAN NOT NULL" +
                MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE + " BOOLEAN NOT NULL" +
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " BOOLEAN NOT NULL" +
                MovieContract.MovieEntry.COLUMN_MOVIE_REVIEWS + " BOOLEAN NOT NULL" +
                MovieContract.MovieEntry.COLUMN_MOVIE_TRAILER + " BOOLEAN NOT NULL" +

                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_FAVORITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public ArrayList<MovieModel> getFavoriteMoviesFromDB() {

        final String SQL_QUERY_MOVIE_FAVORITE_TABLE = "SELECT * FROM " + MovieContract.MovieEntry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL_QUERY_MOVIE_FAVORITE_TABLE, null);
        ArrayList<MovieModel> movieList = new ArrayList<MovieModel>();
        ArrayList<String> reviewList = new ArrayList<String>();

        //Cursor cursor = dbHelper.getFavoriteMoviesFromDB();
        if (cursor.moveToFirst()){
            do{
                // MovieModel movie = new MovieModel(title, popularity,
                // poster_path, overview, vote_average, release_date, id, mReview, trailer );
                String movieTitle = cursor.getString(cursor.getColumnIndex("title"));
                String moviePopularity= cursor.getString(cursor.getColumnIndex("popularity"));
                String moviePosterPath= cursor.getString(cursor.getColumnIndex("path"));
                String movieOvervuew= cursor.getString(cursor.getColumnIndex("overview"));
                String movieVote= cursor.getString(cursor.getColumnIndex("vote"));
                String movieReleaseDate= cursor.getString(cursor.getColumnIndex("date"));
                String movieID= cursor.getString(cursor.getColumnIndex("id"));
                reviewList.add(cursor.getString(cursor.getColumnIndex("review")));
                String trailer = cursor.getString(cursor.getColumnIndex("trailer"));

                MovieModel movie = new MovieModel(movieTitle, moviePopularity, moviePosterPath, movieOvervuew, movieVote, movieReleaseDate, movieID, reviewList, trailer );
                movieList.add(movie);
                //Log.v(TAG, "movie from cursor..."+data);
            } while(cursor.moveToNext());
        }
        cursor.close();

        return movieList;


    }
}
