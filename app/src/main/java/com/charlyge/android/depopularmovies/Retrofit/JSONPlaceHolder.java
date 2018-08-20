package com.charlyge.android.depopularmovies.Retrofit;

import com.charlyge.android.depopularmovies.model.RootMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 *
 * Created by DELL PC on 8/4/2018.
 */

public interface JSONPlaceHolder {

    /**
     *
     *
     * @param sort Sorting criteria (popular or top_rated)
     * @param apiKey     MovieDB API key
     * @return MoviesList which contains a list of Movie objects.
     */

    @GET("/3/movie/{sorting}")
    Call<RootMovies> movielist(@Path("sorting") String sort, @Query("api_key") String apiKey );
}
