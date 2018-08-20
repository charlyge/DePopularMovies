package com.charlyge.android.depopularmovies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.charlyge.android.depopularmovies.R;
import com.squareup.picasso.Picasso;

/**
 * Created by DELL PC on 8/15/2018.
 */

public class MovieDetailFragment extends Fragment {
    private String overview;
    private String title;
    private String release_date;
    private String user_rating;
    private String poster_path;

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
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
       TextView overviewTv = root.findViewById(R.id.overview);
        TextView titleTv = root.findViewById(R.id.detail_title);
        TextView  release_date_Tv = root.findViewById(R.id.release_date_details);
        ImageView poster_path_Iv = root.findViewById(R.id.poster_path_details);
        TextView user_rating_Tv = root.findViewById(R.id.user_rating);

        overviewTv.setText(overview);
        titleTv.setText(title);
        release_date_Tv.setText(release_date);
        user_rating_Tv.setText(user_rating);
        Picasso.get().load(poster_path).placeholder(R.drawable.ic_image_black_24dp).into(poster_path_Iv);


        return root;
    }
}
