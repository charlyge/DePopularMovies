package com.charlyge.android.depopularmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.charlyge.android.depopularmovies.MovieDetailActivity;
import com.charlyge.android.depopularmovies.R;
import com.charlyge.android.depopularmovies.model.Movies;
import com.charlyge.android.depopularmovies.model.RootMovies;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by DELL PC on 7/28/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    private Context mContext;
    public List<Movies> moviesList;
    private itemClickListener newItemClickListener;

    public MovieAdapter(Context mContext, List<Movies> moviesList,itemClickListener newItemClickListener) {
        this.mContext = mContext;
        this.moviesList = moviesList;
        this.newItemClickListener = newItemClickListener;
    }

    public interface itemClickListener{
        void onItemClicked(String id, String title, String release_date,String overview,String vote_average,String poster_path,String db_poster_path,String backDropImage, String db_backDropImage);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.movie_list_item,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
      Movies movies = moviesList.get(position);
      final String title = movies.getOriginal_title();
      final String release_date = movies.getRelease_date();
      final String poster_path = "http://image.tmdb.org/t/p/w342/"+movies.getPoster_path();
      holder.textView_title.setText(title);
      holder.textView_release_date.setText(release_date);


      Log.i("AlbumAdapter",poster_path);
        Picasso.get().load(poster_path).placeholder(R.drawable.ic_image_black_24dp).into(holder.imageView_thumb);
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopMenu(holder.overflow);
            }
        });

    }

    private void showPopMenu(View view) {
        PopupMenu popupMenu  = new PopupMenu(mContext,view);
        popupMenu.getMenuInflater().inflate(R.menu.popmenu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popupMenu.show();
    }


    @Override
    public int getItemCount() {
        if (moviesList ==null){
            return 0;
        }
        else {
            return moviesList.size();
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
     ImageView imageView_thumb,overflow;
     TextView textView_title;
     TextView textView_release_date;


        public MyViewHolder(View itemView) {
            super(itemView);
            imageView_thumb = itemView.findViewById(R.id.thumbnail);
            textView_release_date = itemView.findViewById(R.id.release_date);
            overflow = itemView.findViewById(R.id.overflow);
            textView_title = itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
           Movies movies = moviesList.get(getAdapterPosition());
            final String id = movies.getIdMovies();
            final String title = movies.getOriginal_title();
            final String release_date = movies.getRelease_date();
            final String overView = movies.getOverview();
            final String vote_Average = movies.getVote_average();
            //poster path to insert in room Database
            final String db_Poster_Path = movies.getPoster_path();
            final String db_backDropImage = movies.getBackdrop_path();
            final String backDropImage =  "http://image.tmdb.org/t/p/w342/" + movies.getBackdrop_path();
            final String poster_path = "http://image.tmdb.org/t/p/w342/"+movies.getPoster_path();
           newItemClickListener.onItemClicked(id,title,release_date,overView,vote_Average,poster_path,db_Poster_Path,backDropImage,db_backDropImage);

        }
    }

    private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        public MyMenuItemClickListener(){

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            int id = menuItem.getItemId();

            if(id==R.id.action_play_next){
                Toast.makeText(mContext,"Play Next",Toast.LENGTH_LONG).show();

            }
            return true;
        }
    }

    public void setMovieData(List<Movies> moviesList){
      this.moviesList = moviesList;

    }

}
