package com.charlyge.android.depopularmovies.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DELL PC on 8/4/2018.
 */

public class NetworkService {

    private final Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private final JSONPlaceHolder jsonPlaceHolder = retrofit.create(JSONPlaceHolder.class);

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public JSONPlaceHolder getJsonPlaceHolder() {
        return jsonPlaceHolder;
    }

    private NetworkService() {

    }
    private static final NetworkService ourInstance = new NetworkService();

    public static NetworkService getInstance () {
        return ourInstance;
    }
}
