package com.charlyge.android.depopularmovies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL PC on 7/28/2018.
 */
@Entity(tableName = "movies")
public class Movies{


    @PrimaryKey(autoGenerate =true)
    private int idDatabase;
    @SerializedName("id")
    private String idMovies;
    private String vote_average;
    private String poster_path;
    private String original_title;
    private String overview;
    private String release_date;
    private String backdrop_path;

    @Ignore
    public Movies(){


    }

    @Ignore
    public Movies(String idMovies,String vote_average,String poster_path,String original_title,String overview,String release_date,String backdrop_path){
        this.original_title=original_title;
        this.poster_path=poster_path;
        this.overview=overview;
        this.idMovies=idMovies;
        this.release_date=release_date;
        this.vote_average=vote_average;
        this.backdrop_path = backdrop_path;

    }

    public Movies(int idDatabase,String idMovies,String vote_average,String poster_path,String original_title,String overview,String release_date,String backdrop_path){
        this.original_title=original_title;
        this.poster_path=poster_path;
        this.overview=overview;
        this.release_date=release_date;
        this.vote_average=vote_average;
        this.idDatabase=idDatabase;
        this.idMovies = idMovies;
        this.backdrop_path=backdrop_path;

    }


    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOverview() {
        return overview;
    }

    public String getIdMovies() {
            return idMovies;

    }


    public String getPoster_path() {
        return poster_path;
    }

    public String getVote_average() {
        return vote_average;
    }


    public int getIdDatabase() {
        return idDatabase;
    }

    public void setIdDatabase(int idDatabase) {
        this.idDatabase = idDatabase;
    }
}
