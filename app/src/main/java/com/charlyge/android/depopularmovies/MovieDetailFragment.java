package com.charlyge.android.depopularmovies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.charlyge.android.depopularmovies.Database.AppDatabase;
import com.charlyge.android.depopularmovies.model.Movies;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DELL PC on 8/15/2018.
 */

public class MovieDetailFragment extends Fragment {
    private String overview;
    private String title;
    private String release_date;
    private String user_rating;
    private String poster_path;
    private String idMovies;
    private String db_poster_path;
    private AppDatabase appDatabase;
    public void setOverview(String overview) {
        this.overview = overview;
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
       TextView overviewTv = root.findViewById(R.id.overview);



        TextView titleTv = root.findViewById(R.id.detail_title);
        final ImageView addToFav = (ImageView)root.findViewById(R.id.add_to_fav);
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
        //Loads movie by Id



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
                            Movies movieEntry = new Movies(idMovies,user_rating,db_poster_path,title,overview,release_date);
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


        return root;
    }
}
