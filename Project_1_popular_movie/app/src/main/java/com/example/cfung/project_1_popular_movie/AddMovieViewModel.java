package com.example.cfung.project_1_popular_movie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;

import com.example.cfung.project_1_popular_movie.data.AppDatabase;

import java.util.List;

/**
 * Created by cfung on 11/26/18.
 */

public class AddMovieViewModel extends ViewModel {

    private LiveData<List<MovieModel>> movies;

    public AddMovieViewModel(AppDatabase database) {
        movies = database.movieDao().getFavoriteMoviesFromDB();
    }

    public LiveData<List<MovieModel>> getMovies() { return movies; }

    public void deleteMovie(AppDatabase database, String movieName){
        database.movieDao().DeleteMovie(movieName);

    }


}
