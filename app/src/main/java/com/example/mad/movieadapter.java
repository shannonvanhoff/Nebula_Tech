package com.example.mad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

 public class movieadapter extends RecyclerView.Adapter<movieadapter.reviewholder >{
   Context context;
   ArrayList<userreviews> reviewArrayList;

    public movieadapter(Context context, ArrayList<userreviews> reviewArrayList) {
        this.context = context;
        this.reviewArrayList = reviewArrayList;
    }

    @NonNull
    @Override
    public movieadapter.reviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview,parent,false);

        return new reviewholder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull movieadapter.reviewholder holder, int position) {
       userreviews User = reviewArrayList.get(position);
       holder.review.setText(User.getReview());
    }

   /* @Override
    public void onBindViewHolder(@NonNull movieadapter holder, int position) {

    }*/

    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }
    public static class reviewholder extends RecyclerView.ViewHolder{
        TextView review;

        public reviewholder(@NonNull View itemView) {
            super(itemView);
            review = itemView.findViewById(R.id.reviewid);
        }
    }
}
