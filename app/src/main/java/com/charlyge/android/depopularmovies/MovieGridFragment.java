package com.charlyge.android.depopularmovies;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.charlyge.android.depopularmovies.Adapter.MovieAdapter;
import com.charlyge.android.depopularmovies.Database.AppDatabase;
import com.charlyge.android.depopularmovies.ViewModels.DatabaseViewModel;
import com.charlyge.android.depopularmovies.ViewModels.MoviesViewModel;
import com.charlyge.android.depopularmovies.model.Movies;

import java.util.ArrayList;
import java.util.List;

import static com.charlyge.android.depopularmovies.Data.Constants.EXTRA_BACKDROP_IMAGE;
import static com.charlyge.android.depopularmovies.Data.Constants.EXTRA_DBBACKDROP_IMAGE;
import static com.charlyge.android.depopularmovies.Data.Constants.EXTRA_DBPOSTER_PATH;
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
    AppDatabase appDatabase;
    private TextView errorTextView;
    private ProgressBar progressBar;
    private int screenRotateOnFavselected=0;
    private boolean PREFERENCE_UPDATED = false;
    SharedPreferences sharedPreferences;
    private List<Movies> moviesList;
    DatabaseViewModel databaseViewModel;
    MoviesViewModel moviesViewModel;

    private static final String SCREEN_ROTATE_KEY = "Screenkey";

    public MovieGridFragment(){}


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.settings, menu);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SCREEN_ROTATE_KEY,screenRotateOnFavselected);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        }
        if(id==R.id.action_fav){
            screenRotateOnFavselected=1;
           databaseViewModel = ViewModelProviders.of(getActivity()).get(DatabaseViewModel.class);
          //LiveData<List<Movies>> moviesList = databaseViewModel.getMovies();
                databaseViewModel.getMovies().observe(getActivity(), new Observer<List<Movies>>() {
                     @Override
                     public void onChanged(@Nullable List<Movies> movies) {
                         movieAdapter.setMovieData(movies);
                         movieAdapter.notifyDataSetChanged();
                     }
                 });


        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_grid,container,false);
        setHasOptionsMenu(true);
        setUpSharedPreference();
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        if (rootView.findViewById(R.id.Details_activity) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }
        if(savedInstanceState!=null){

            screenRotateOnFavselected = savedInstanceState.getInt(SCREEN_ROTATE_KEY);
        }
        recyclerView = rootView.findViewById(R.id.recycler_view);
        errorTextView = rootView.findViewById(R.id.error_view);
        progressBar = rootView.findViewById(R.id.loading_indicator);
        moviesList = new ArrayList<>();
        appDatabase = AppDatabase.getAppDatabaseInstance(getActivity());
        movieAdapter = new MovieAdapter(getActivity(), moviesList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        // recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);
        if(screenRotateOnFavselected==0){
            makeNetworkConnection();
        }
        else {
            databaseViewModel = ViewModelProviders.of(getActivity()).get(DatabaseViewModel.class);
            //LiveData<List<Movies>> moviesList = databaseViewModel.getMovies();
            databaseViewModel.getMovies().observe(getActivity(), new Observer<List<Movies>>() {
                @Override
                public void onChanged(@Nullable List<Movies> movies) {
                    movieAdapter.setMovieData(movies);
                    movieAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            });

        }


        return rootView;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        PREFERENCE_UPDATED = true;
    }



    private void makeNetworkConnection() {

            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {



                moviesViewModel = ViewModelProviders.of(getActivity()).get(MoviesViewModel.class);
               moviesViewModel.getListLiveData().observe(getActivity(), new Observer<List<Movies>>() {
                   @Override
                   public void onChanged(@Nullable List<Movies> movies) {
                       movieAdapter.setMovieData(movies);
                       movieAdapter.notifyDataSetChanged();
                       progressBar.setVisibility(View.GONE);
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
        Log.i("main", "onStart: preferences were updated" + PREFERENCE_UPDATED);
        if (PREFERENCE_UPDATED) {
            Log.d("main", "onStart: preferences were updated");

            Intent intent = getActivity().getIntent();

            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            getActivity().finish();
            getActivity().overridePendingTransition(0, 0);

            startActivity(intent);
            getActivity().overridePendingTransition(0, 0);
            PREFERENCE_UPDATED = false;
            screenRotateOnFavselected=0;
        }
    }

    @Override
    public void onItemClicked(String id, String title, String release_date, String overview, String vote_average, String poster_path, String db_poster_path, String backDropImage, String db_backDropImage) {
        if (mTwoPane) {
            MovieDetailFragment movieDetailFragment = new MovieDetailFragment();

            movieDetailFragment.setOverview(overview);
            movieDetailFragment.setPoster_path(poster_path);
            movieDetailFragment.setTitle(title);
            movieDetailFragment.setRelease_date(release_date);
            movieDetailFragment.setUser_rating(vote_average);
            movieDetailFragment.setidMovies(id);
            movieDetailFragment.setDbPoster_path(db_poster_path);
            movieDetailFragment.setDbBackdrop_Image(db_backDropImage);
            movieDetailFragment.setBackdrop_Image(backDropImage);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.movie_details_container,movieDetailFragment).commit();

        } else {
            Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
            intent.putExtra(EXTRA_IDD, id);
            intent.putExtra(EXTRA_OVERVIEW, overview);
            intent.putExtra(EXTRA_VOTE_AVERAGE, vote_average);
            intent.putExtra(EXTRA_POSTER_PATH, poster_path);
            intent.putExtra(EXTRA_RELEASE_DATE, release_date);
            intent.putExtra(EXTRA_TITLEE, title);
            intent.putExtra(EXTRA_DBPOSTER_PATH,db_poster_path);
            intent.putExtra(EXTRA_DBBACKDROP_IMAGE,db_backDropImage);
            intent.putExtra(EXTRA_BACKDROP_IMAGE,backDropImage);
            startActivity(intent);
        }
    }
}
