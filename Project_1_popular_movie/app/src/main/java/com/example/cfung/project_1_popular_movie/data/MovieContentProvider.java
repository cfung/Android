package com.example.cfung.project_1_popular_movie.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Movie;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.cfung.project_1_popular_movie.data.MovieContract.MovieEntry.TABLE_NAME;

/**
 * Created by cfung on 1/25/18.
 */

public class MovieContentProvider extends ContentProvider {

    private MovieDBHelper movieDBHelper;

    // final integer constants for MovieModel (name, popularity, link, overview, vote_average, release_date, id, reviews, trailer)
    public static final int MOVIES = 100;
    public static final int MOVIE_WITH_ID = 101;

    // declare static variable for Uri matcher
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    /**
     Method to initalize a new matcher object with NO_MATCH
     then use .addURI to add matchers
     **/
    public static UriMatcher buildUriMatcher(){

        //Initialize with NO_MATCH
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIE, MOVIES);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIE, MOVIE_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {

        Context context = getContext();
        movieDBHelper = new MovieDBHelper(getContext());
        return true;
    }


    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Get access to DB for read access
        final SQLiteDatabase db = movieDBHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        switch (match){

            case MOVIES:
                retCursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // set a notification URI on the Cursor and return that Cursor
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        //Get access to the task database for write access
        final SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match){

            case MOVIES:

                long id = db.insert(TABLE_NAME,null, contentValues);
                if (id > 0){
                    returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;


            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        //Get access to the task database for write access
        final SQLiteDatabase db = movieDBHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        int taskDeleted = 0;

        switch (match){

            case MOVIE_WITH_ID:

                String id = uri.getPathSegments().get(1);
                String mSelection = "_id=?";
                String [] mSelectionArgs = new String[]{id};

                taskDeleted = db.delete(TABLE_NAME,
                        mSelection,
                        mSelectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (taskDeleted != 0) {

            getContext().getContentResolver().notifyChange(uri, null);
        }
        return taskDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
