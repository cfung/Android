package com.example.cfung.project_1_popular_movie.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import com.example.cfung.project_1_popular_movie.MovieModel;

/**
 * Created by cfung on 11/20/18.
 */

@Database(entities = {MovieModel.class}, version = 1, exportSchema = false)
@TypeConverters({ReviewsTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase{

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "movielist.db";
    private static AppDatabase sInstance;

    public static AppDatabase getsInstance(Context context) {

        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the databae instance");
        return sInstance;

    }

    public static void destroyInstance() {
        sInstance = null;
    }

    public abstract MovieDao movieDao();
}
