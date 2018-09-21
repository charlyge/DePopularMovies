package com.charlyge.android.depopularmovies;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.charlyge.android.depopularmovies.Adapter.ReviewAdapter;
import com.charlyge.android.depopularmovies.Adapter.TrailerAdapter;
import com.charlyge.android.depopularmovies.Data.Constants;
import com.charlyge.android.depopularmovies.Database.AppDatabase;
import com.charlyge.android.depopularmovies.Retrofit.NetworkService;
import com.charlyge.android.depopularmovies.model.Movies;
import com.charlyge.android.depopularmovies.model.Review;
import com.charlyge.android.depopularmovies.model.Trailers;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DELL PC on 8/15/2018.
 */

public class MovieDetailFragment extends Fragment implements TrailerAdapter.TrailerItemClckListener {
    private String overview;
    private String title;
    private String release_date;
    private String user_rating;
    private String poster_path;
    private String idMovies;
    private String db_poster_path;
    private AppDatabase appDatabase;
    private String db_backDropImage;
    private String backDropImage;
    public void setDbBackdrop_Image(String db_backDropImage) {
        this.db_backDropImage = db_backDropImage;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setBackdrop_Image(String backDropImage) {
        this.backDropImage = backDropImage;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setDbPoster_path(String db_poster_path) {
        this.db_poster_path = db_poster_path;
    }

    public void setidMovies(String idMovies) {
        this.idMovies = idMovies;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser_rating(String user_rating) {
        this.user_rating = user_rating;
    }



    public MovieDetailFragment(){
        }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     View root = inflater.inflate(R.layout.fragment_movie_details,container,false);
      appDatabase = AppDatabase.getAppDatabaseInstance(getActivity());
      NetworkService networkService = NetworkService.getOurInstance();

       TextView overviewTv = root.findViewById(R.id.overview);



        TextView titleTv = root.findViewById(R.id.detail_title);
        final ImageView addToFav = root.findViewById(R.id.add_to_fav);
        AppExecutor.getInstance().getDiskIo().execute(new Runnable() {
            @Override
            public void run() {
                Movies moviesId = appDatabase.movieDao().loadMoviesById(idMovies);
                //sets background color to pink is if id exist in db
                if(moviesId!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addToFav.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        }
                    });

                }
            }
        });
        //Setup RecyclerView for Reviews







        TextView  release_date_Tv = root.findViewById(R.id.release_date_details);
        ImageView poster_path_Iv = root.findViewById(R.id.poster_path_details);
        TextView user_rating_Tv = root.findViewById(R.id.user_rating);

        addToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppExecutor.getInstance().getDiskIo().execute(new Runnable() {
                    @Override
                    public void run() {
                        Movies moviesId = appDatabase.movieDao().loadMoviesById(idMovies);

                        if(moviesId!=null){
                            appDatabase.movieDao().deleteMovies(moviesId);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(),"Movie removed from favourite List",Toast.LENGTH_LONG).show();
                                    addToFav.setBackgroundColor(getResources().getColor(R.color.white));
                                }
                            });


                        }

                        else {
                            Movies movieEntry = new Movies(idMovies,user_rating,db_poster_path,title,overview,release_date,db_backDropImage);
                            appDatabase.movieDao().insertMovies(movieEntry);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    addToFav.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                    Toast.makeText(getActivity(),"Movie Added to Favourite",Toast.LENGTH_LONG).show();
                                }
                            });


                        }

                    }
                });

            }
        });
        overviewTv.setText(overview);
        titleTv.setText(title);
        release_date_Tv.setText(release_date);
        user_rating_Tv.setText(user_rating);
        Picasso.get().load(poster_path).placeholder(R.drawable.ic_image_black_24dp).into(poster_path_Iv);


        //Setup Review RecyclerView Network Connection for Reviews
        final RecyclerView reviewRv = root.findViewById(R.id.recycler_view_reviews);
        LinearLayoutManager linearLayoutManagerRv = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        reviewRv.setLayoutManager(linearLayoutManagerRv);
        reviewRv.setHasFixedSize(true);
        ((DefaultItemAnimator) reviewRv.getItemAnimator()).setSupportsChangeAnimations(false);

       networkService.getReviews(idMovies,Constants.API_KEY).observe(getActivity(), new Observer<List<Review>>() {
           @Override
           public void onChanged(@Nullable List<Review> reviews) {
               Log.i("Review","Started Review call");
               final ReviewAdapter reviewAdapter = new ReviewAdapter(reviews);
               reviewRv.setAdapter(reviewAdapter);
           }
       });



        //NetworkConnection for Trailers
        //Setup RecyclerView For Trailer
        RecyclerView trailerRv = root.findViewById(R.id.recycler_view_trailer);

        LinearLayoutManager linearLayoutManagerTr = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        trailerRv.setLayoutManager(linearLayoutManagerTr);
        trailerRv.setHasFixedSize(true);
        ((DefaultItemAnimator) trailerRv.getItemAnimator()).setSupportsChangeAnimations(false);
        final TrailerAdapter trailerAdapter = new TrailerAdapter(this);
        trailerRv.setAdapter(trailerAdapter);
        networkService.getTrailers(idMovies,Constants.API_KEY).observe(getActivity(), new Observer<List<Trailers>>() {
            @Override
            public void onChanged(@Nullable List<Trailers> trailers) {
                Log.i("Trailer","Started Trailer call");
                trailerAdapter.setTrailersList(trailers);
            }
        });



        return root;
    }

    @Override
    public void WatchTrailer(String VideoKey) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + VideoKey));
        startActivity(intent);
    }



}
