package com.example.cfung.project_1_popular_movie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;

import com.example.cfung.project_1_popular_movie.data.AppDatabase;

/**
 * Created by cfung on 11/26/18.
 */

public class AddMovieViewModel extends ViewModel {

    private LiveData<MovieModel> movie;

    public AddMovieViewModel(AppDatabase database) {
        movie = database.movieDao().getFavoriteMoviesFromDB();
    }

    public LiveData<MovieModel> getMovie() { return movie; }
}
