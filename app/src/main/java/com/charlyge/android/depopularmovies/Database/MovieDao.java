package com.charlyge.android.depopularmovies.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.charlyge.android.depopularmovies.model.Movies;

import java.util.List;

/**
 * Created by DELL PC on 8/31/2018.
 */

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    LiveData<List<Movies>> loadAllMovies();

    @Insert
    void insertMovies(Movies movieEntry);

    @Delete
    void deleteMovies(Movies movieEntry);

    @Query("SELECT * FROM movies WHERE idMovies =:id")
    Movies loadMoviesById(String id);

}
