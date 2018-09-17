package com.charlyge.android.depopularmovies.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.charlyge.android.depopularmovies.R;
import com.charlyge.android.depopularmovies.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder>{

    List<Review> reviewArrayList;

       public ReviewAdapter( List<Review> reviewArrayList){
   this.reviewArrayList = reviewArrayList;
       }
    @NonNull
    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.review_list_item,parent,false);
        return new ReviewAdapterViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull ReviewAdapterViewHolder holder, int position) {
    Review review= reviewArrayList.get(position);
    holder.authorTv.setText(review.getAuthor());
    holder.reviewContentTv.setText(review.getContent());
    }


    @Override
    public int getItemCount() {
           if(reviewArrayList==null){
               return 0;
           }else {
               return reviewArrayList.size();
           }

    }

    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView reviewContentTv;
        TextView authorTv;

        public ReviewAdapterViewHolder(View itemView) {
            super(itemView);
            reviewContentTv = (TextView)itemView.findViewById(R.id.reviewContent_tv);
            authorTv = (TextView)itemView.findViewById(R.id.reviewAuthor_tv);
        }
    }

}
