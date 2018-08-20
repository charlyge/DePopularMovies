package com.charlyge.android.depopularmovies;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.charlyge.android.depopularmovies.Adapter.MovieAdapter;
import com.charlyge.android.depopularmovies.Data.Constants;
import com.charlyge.android.depopularmovies.Retrofit.NetworkService;
import com.charlyge.android.depopularmovies.model.Movies;
import com.charlyge.android.depopularmovies.model.RootMovies;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.charlyge.android.depopularmovies.Data.Constants.EXTRA_IDD;
import static com.charlyge.android.depopularmovies.Data.Constants.EXTRA_OVERVIEW;
import static com.charlyge.android.depopularmovies.Data.Constants.EXTRA_POSTER_PATH;
import static com.charlyge.android.depopularmovies.Data.Constants.EXTRA_RELEASE_DATE;
import static com.charlyge.android.depopularmovies.Data.Constants.EXTRA_TITLEE;
import static com.charlyge.android.depopularmovies.Data.Constants.EXTRA_VOTE_AVERAGE;

/**
 * Created by DELL PC on 8/17/2018.
 */

public class MovieGridFragment extends Fragment implements
        SharedPreferences.OnSharedPreferenceChangeListener, MovieAdapter.itemClickListener {
    boolean mTwoPane;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private TextView errorTextView;
    private ProgressBar progressBar;
    private boolean PREFERENCE_UPDATED = false;
    SharedPreferences sharedPreferences;
    private static String MOVIE_ADAPTER_LIST = "movie Adapter List";
    private ArrayList<Movies> moviesList = new RootMovies().getMoviesArrayList();
    public MovieGridFragment(){}



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOVIE_ADAPTER_LIST,moviesList);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            moviesList = savedInstanceState.getParcelableArrayList(MOVIE_ADAPTER_LIST);

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_grid,container,false);
        if (rootView.findViewById(R.id.Details_activity) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }
        recyclerView = rootView.findViewById(R.id.recycler_view);
        errorTextView = rootView.findViewById(R.id.error_view);
        progressBar = rootView.findViewById(R.id.loading_indicator);
        moviesList = new ArrayList<>();
        movieAdapter = new MovieAdapter(getActivity(), moviesList, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        // recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);
            makeNetworkConnection();

        return rootView;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        PREFERENCE_UPDATED = true;
    }

    @Override
    public void onItemClicked(String id, String title, String release_date, String overview, String vote_average, String poster_path) {
        if (mTwoPane) {
            MovieDetailFragment movieDetailFragment = new MovieDetailFragment();

                movieDetailFragment.setOverview(overview);
                movieDetailFragment.setPoster_path(poster_path);
                movieDetailFragment.setTitle(title);
                movieDetailFragment.setRelease_date(release_date);
                movieDetailFragment.setUser_rating(vote_average);

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.movie_details_container,movieDetailFragment).commit();

        } else {
            Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
            intent.putExtra(EXTRA_IDD, id);
            intent.putExtra(EXTRA_OVERVIEW, overview);
            intent.putExtra(EXTRA_VOTE_AVERAGE, vote_average);
            intent.putExtra(EXTRA_POSTER_PATH, poster_path);
            intent.putExtra(EXTRA_RELEASE_DATE, release_date);
            intent.putExtra(EXTRA_TITLEE, title);
            startActivity(intent);
        }
    }

    private void makeNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            String sort = setUpSharedPreference();
            sharedPreferences.registerOnSharedPreferenceChangeListener(this);
            String api_key = Constants.API_KEY;
            NetworkService.getInstance().getJsonPlaceHolder().movielist(sort, api_key).enqueue(new Callback<RootMovies>() {
                @Override
                public void onResponse(Call<RootMovies> call, Response<RootMovies> response) {
                    if (response.body() != null) {
                        Log.i("ResponseBody", response.body().toString());
                        moviesList = new RootMovies().getMoviesArrayList();
                        moviesList = response.body().getMoviesArrayList();
                        movieAdapter.setMovieData(moviesList);
                        movieAdapter.notifyDataSetChanged();
                        Log.i("MAINACTIVITY", "sucess" + response.body());
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<RootMovies> call, Throwable t) {
                    Log.i("MAINACTIVITY", "MainActvityfail " + t.getMessage());
                }
            });


        } else {
            errorTextView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

    }

    @NonNull
    private String setUpSharedPreference() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //sort oder from users selection
        return sharedPreferences.getString(getString(R.string.listkey), getString(R.string.sort_default));
    }

    /**
     * Called when the fragment is no longer in use.  This is called
     * after {@link #onStop()} and before {@link #onDetach()}.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getActivity()).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (PREFERENCE_UPDATED) {
            Log.d("main", "onStart: preferences were updated");
            getActivity().recreate();
            PREFERENCE_UPDATED = false;
        }
    }
}
