package com.charlyge.android.depopularmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.charlyge.android.depopularmovies.Database.AppDatabase;
import com.charlyge.android.depopularmovies.model.Movies;

import java.util.List;

public class DatabaseViewModel extends AndroidViewModel {

    private LiveData<List<Movies>> movies;


    public DatabaseViewModel(@NonNull Application application) {
        super(application);
      AppDatabase appDatabase = AppDatabase.getAppDatabaseInstance(this.getApplication());
       movies = appDatabase.movieDao().loadAllMovies();

    }

    public LiveData<List<Movies>> getMovies() {
        return movies;
    }
}
