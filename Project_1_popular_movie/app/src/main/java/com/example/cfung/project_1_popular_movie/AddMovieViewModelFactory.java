package com.example.cfung.project_1_popular_movie;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.cfung.project_1_popular_movie.data.AppDatabase;

/**
 * Created by cfung on 11/26/18.
 */

public class AddMovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private Application mApplication;

    public AddMovieViewModelFactory(AppDatabase appDatabase, Application application) {
        //super(database);
        mApplication = application;
        mDb = appDatabase;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        return (T) new AddMovieViewModel(mDb, mApplication);
    }
}
