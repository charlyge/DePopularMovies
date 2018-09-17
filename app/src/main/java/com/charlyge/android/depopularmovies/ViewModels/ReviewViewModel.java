package com.charlyge.android.depopularmovies.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.charlyge.android.depopularmovies.Retrofit.NetworkService;
import com.charlyge.android.depopularmovies.model.Review;

import java.util.List;


public class ReviewViewModel extends ViewModel {
 private LiveData<List<Review>> listLiveData;

    public ReviewViewModel(NetworkService networkService, String id, String apiKey) {
        listLiveData = networkService.getReviews(id,apiKey);
    }

    public LiveData<List<Review>> getListLiveData() {
        return listLiveData;
    }

}
