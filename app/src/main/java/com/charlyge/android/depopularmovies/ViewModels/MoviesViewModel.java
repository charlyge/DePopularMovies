package com.charlyge.android.depopularmovies.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.charlyge.android.depopularmovies.Data.Constants;
import com.charlyge.android.depopularmovies.R;
import com.charlyge.android.depopularmovies.Retrofit.NetworkService;
import com.charlyge.android.depopularmovies.model.Movies;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {

private LiveData<List<Movies>> listLiveData;
    public MoviesViewModel(@NonNull Application application) {
        super(application);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication());

         listLiveData = NetworkService.getOurInstance().getMovieList(sharedPreferences.getString(getApplication().getString(R.string.listkey),
                 getApplication().getString(R.string.sort_default)),Constants.API_KEY);
    }

    public LiveData<List<Movies>> getListLiveData() {
        return listLiveData;
    }




}
