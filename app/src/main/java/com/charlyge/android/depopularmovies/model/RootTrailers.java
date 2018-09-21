package com.charlyge.android.depopularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RootTrailers {
    @SerializedName("results")
   private ArrayList<Trailers> trailersArrayList= null;

    public void setTrailersArrayList(ArrayList<Trailers> trailersArrayList) {
        this.trailersArrayList = trailersArrayList;
    }

    public ArrayList<Trailers> getTrailersArrayList() {
        return trailersArrayList;
    }
}
