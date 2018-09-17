package com.charlyge.android.depopularmovies.ViewModels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.charlyge.android.depopularmovies.Retrofit.NetworkService;

public class TrailerViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private NetworkService networkService;
    private String id;
    private String apiKey;


    public TrailerViewModelFactory(NetworkService networkService,String id,String apiKey){
        this.apiKey=apiKey;
        this.id=id;
        this.networkService =networkService;


    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new TrailerViewModel(networkService,id,apiKey);
    }
}
