package com.charlyge.android.depopularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DELL PC on 7/28/2018.
 */

public class Movies implements Parcelable{

    private String id;
    private String vote_average;
    private String poster_path;
    private String original_title;
    private String overview;
    private String release_date;


    public Movies(String id, String vote_average, String poster_path, String original_title, String overview, String release_date) {
        this.id = id;
        this.original_title = original_title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.vote_average = vote_average;
        this.release_date = release_date;
    }

    public Movies(String poster_path, String original_title,String release_date) {
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.release_date = release_date;
    }

    protected Movies(Parcel in) {
        id = in.readString();
        vote_average = in.readString();
        poster_path = in.readString();
        original_title = in.readString();
        overview = in.readString();
        release_date = in.readString();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    public String getOriginal_title() {
        return original_title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOverview() {
        return overview;
    }

    public String getId() {
        return id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getVote_average() {
        return vote_average;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {


        parcel.writeString(id);
        parcel.writeString(vote_average);
        parcel.writeString(poster_path);
        parcel.writeString(original_title);
        parcel.writeString(overview);
        parcel.writeString(release_date);
    }
}
