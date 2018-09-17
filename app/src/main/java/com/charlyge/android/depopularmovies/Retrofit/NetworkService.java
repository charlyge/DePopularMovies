package com.charlyge.android.depopularmovies.Retrofit;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.view.View;

import com.charlyge.android.depopularmovies.model.Movies;
import com.charlyge.android.depopularmovies.model.Review;
import com.charlyge.android.depopularmovies.model.RootMovies;
import com.charlyge.android.depopularmovies.model.RootReview;
import com.charlyge.android.depopularmovies.model.RootTrailers;
import com.charlyge.android.depopularmovies.model.Trailers;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DELL PC on 8/4/2018.
 */

public class NetworkService {



    private JSONPlaceHolder jsonPlaceHolder;
    private static NetworkService networkService;

    public NetworkService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolder = retrofit.create(JSONPlaceHolder.class);
    }

   public synchronized static NetworkService getOurInstance(){
        if(networkService==null){
           networkService = new NetworkService();

        }
       return networkService;
    }
    public LiveData<List<Movies>> getMovieList(String sort,String api_key){
        final MutableLiveData<List<Movies>> mutableLiveData = new MutableLiveData<>();
        jsonPlaceHolder.movielist(sort,api_key).enqueue(new Callback<RootMovies>() {
            @Override
            public void onResponse(Call<RootMovies> call, Response<RootMovies> response) {
                mutableLiveData.setValue(response.body().getMoviesArrayList());
            }

            @Override
            public void onFailure(Call<RootMovies> call, Throwable t) {
              mutableLiveData.setValue(null);
            }
        });
   return  mutableLiveData;
    }

    public LiveData<List<Review>> getReviews(String id,String api_key){
        final MutableLiveData<List<Review>> mutableLiveData = new MutableLiveData<>();
        jsonPlaceHolder.reviewlist(id,api_key).enqueue(new Callback<RootReview>() {
            @Override
            public void onResponse(Call<RootReview> call, Response<RootReview> response) {
                mutableLiveData.setValue(response.body().getReviewArrayList());
            }

            @Override
            public void onFailure(Call<RootReview> call, Throwable t) {
            mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }

    public LiveData<List<Trailers>> getTrailers(String id,String api_key){
        final MutableLiveData<List<Trailers>> mutableLiveData = new MutableLiveData<>();
        jsonPlaceHolder.trailerlist(id,api_key).enqueue(new Callback<RootTrailers>() {
            @Override
            public void onResponse(Call<RootTrailers> call, Response<RootTrailers> response) {
                mutableLiveData.setValue(response.body().getTrailersArrayList());
            }

            @Override
            public void onFailure(Call<RootTrailers> call, Throwable t) {
            mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }
}