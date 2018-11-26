package com.example.cfung.project_1_popular_movie;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.cfung.project_1_popular_movie.data.AppDatabase;

/**
 * Created by cfung on 11/26/18.
 */

public class AddMovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;

    public AddMovieViewModelFactory(AppDatabase database) {
        mDb = database;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        return (T) new AddMovieViewModel(mDb);
    }
}
