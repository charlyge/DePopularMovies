package com.charlyge.android.depopularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by DELL PC on 8/4/2018.
 */

public class RootMovies {
    @SerializedName("results") @Expose
    ArrayList<Movies> moviesArrayList = null;

    public void setMoviesArrayList(ArrayList<Movies> moviesArrayList) {
        this.moviesArrayList = moviesArrayList;
    }

    public  ArrayList<Movies> getMoviesArrayList() {
        return moviesArrayList;
    }
}
