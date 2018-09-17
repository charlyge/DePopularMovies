package com.charlyge.android.depopularmovies.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.charlyge.android.depopularmovies.R;
import com.charlyge.android.depopularmovies.model.Trailers;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
     List<Trailers> trailersList;
     public  TrailerItemClckListener trailerItemClckListener;

     public TrailerAdapter(TrailerItemClckListener trailerItemClckListener){
         this.trailerItemClckListener =trailerItemClckListener;

     }

     public interface TrailerItemClckListener{
       void WatchTrailer(String VideoKey);

     }

    public void setTrailersList(List<Trailers> trailersList) {
        this.trailersList = trailersList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.trailer_list_item,parent,false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
     Trailers trailers = trailersList.get(position);
     holder.trailerTitle.setText(trailers.getName());

    }


    @Override
    public int getItemCount() {
        if(trailersList==null){
            return 0;
        }
        else {
            return trailersList.size();
        }

    }


    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
      ImageView imageView;
      TextView trailerTitle;
        public TrailerViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView)itemView.findViewById(R.id.TrailerView);
            trailerTitle= (TextView)itemView.findViewById(R.id.TrailerTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
         Trailers trailers = trailersList.get(getAdapterPosition());
         String VideoKey = trailers.getKey();
         trailerItemClckListener.WatchTrailer(VideoKey);
        }
    }
}
