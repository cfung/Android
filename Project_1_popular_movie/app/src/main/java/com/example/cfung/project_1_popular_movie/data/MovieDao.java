package com.example.cfung.project_1_popular_movie.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.example.cfung.project_1_popular_movie.MovieModel;

import java.util.ArrayList;

/**
 * Created by cfung on 11/20/18.
 */

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertMovie(MovieModel movieModel);

    @Query("SELECT * FROM " + MovieContract.MovieEntry.TABLE_NAME)
    ArrayList<MovieModel> getFavoriteMoviesFromDB();

    //@Update


    @Delete
    void DeleteMovie(MovieModel movieModel);

}
