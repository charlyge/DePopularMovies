package com.charlyge.android.depopularmovies.ViewModels;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;


import com.charlyge.android.depopularmovies.Retrofit.NetworkService;
import com.charlyge.android.depopularmovies.model.Trailers;

import java.util.List;

public class TrailerViewModel extends ViewModel {
    LiveData<List<Trailers>> trailersList;


    public TrailerViewModel(NetworkService networkService, String id, String apiKey) {
      trailersList = networkService.getTrailers(id,apiKey);
    }

    public LiveData<List<Trailers>> getTrailersList() {
        return trailersList;
    }
}
