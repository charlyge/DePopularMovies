package com.charlyge.android.depopularmovies;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import static com.charlyge.android.depopularmovies.Data.Constants.EXTRA_BACKDROP_IMAGE;
import static com.charlyge.android.depopularmovies.Data.Constants.EXTRA_DBBACKDROP_IMAGE;
import static com.charlyge.android.depopularmovies.Data.Constants.EXTRA_DBPOSTER_PATH;
import static com.charlyge.android.depopularmovies.Data.Constants.EXTRA_IDD;
import static com.charlyge.android.depopularmovies.Data.Constants.EXTRA_OVERVIEW;
import static com.charlyge.android.depopularmovies.Data.Constants.EXTRA_POSTER_PATH;
import static com.charlyge.android.depopularmovies.Data.Constants.EXTRA_RELEASE_DATE;
import static com.charlyge.android.depopularmovies.Data.Constants.EXTRA_TITLEE;
import static com.charlyge.android.depopularmovies.Data.Constants.EXTRA_VOTE_AVERAGE;


public class MovieDetailActivity extends AppCompatActivity {
    private MovieDetailFragment movieDetailFragment;
    private final String FRAGMENT_TAG = "myfragmenttag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(savedInstanceState!=null){
      movieDetailFragment =(MovieDetailFragment)getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        }
        else if(movieDetailFragment==null){
            movieDetailFragment = new MovieDetailFragment();
        }


        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_IDD)){

            String id = intent.getStringExtra(EXTRA_IDD);
            movieDetailFragment.setidMovies(id);
        }
        if(intent.hasExtra(EXTRA_OVERVIEW)){
            String overview = intent.getStringExtra(EXTRA_OVERVIEW);
            movieDetailFragment.setOverview(overview);


        }
        if(intent.hasExtra(EXTRA_POSTER_PATH)){
            String poster_path = intent.getStringExtra(EXTRA_POSTER_PATH);
            movieDetailFragment.setPoster_path(poster_path);

        }

        if(intent.hasExtra(EXTRA_DBPOSTER_PATH)){
            String poster_path = intent.getStringExtra(EXTRA_DBPOSTER_PATH);
            movieDetailFragment.setDbPoster_path(poster_path);

        }

        if(intent.hasExtra(EXTRA_BACKDROP_IMAGE)){
            String backDropImage = intent.getStringExtra(EXTRA_BACKDROP_IMAGE);
            movieDetailFragment.setBackdrop_Image(backDropImage);
            ImageView backDropImageView = findViewById(R.id.backDropImage);
            Picasso.get().load(backDropImage).into(backDropImageView);

        }

        if(intent.hasExtra(EXTRA_DBBACKDROP_IMAGE)){
            String dbBackDropImage = intent.getStringExtra(EXTRA_DBBACKDROP_IMAGE);
            movieDetailFragment.setDbBackdrop_Image(dbBackDropImage);

        }


        if(intent.hasExtra(EXTRA_TITLEE)){
            String title = intent.getStringExtra(EXTRA_TITLEE);
            movieDetailFragment.setTitle(title);
            CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
            collapsingToolbarLayout.setTitle(title);

        }
        if(intent.hasExtra(EXTRA_RELEASE_DATE)){
            String release_date = intent.getStringExtra(EXTRA_RELEASE_DATE);
            movieDetailFragment.setRelease_date(release_date);

        }
        if(intent.hasExtra(EXTRA_VOTE_AVERAGE)){
            String user_rating = intent.getStringExtra(EXTRA_VOTE_AVERAGE);
            movieDetailFragment.setUser_rating(user_rating);

        }
        if(!movieDetailFragment.isInLayout()){
            getSupportFragmentManager().beginTransaction().replace(R.id.movie_details_containerr,movieDetailFragment,FRAGMENT_TAG).
                    commit();
        }

    }
}
