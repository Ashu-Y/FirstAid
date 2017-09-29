package com.practice.android.firstaid.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.practice.android.firstaid.Models.Post;
import com.practice.android.firstaid.R;

import java.util.ArrayList;

/**
 * Created by Ashutosh on 9/24/2017.
 */

public class RbrRecyclerAdapter extends RecyclerView.Adapter<RbrRecyclerAdapter.MyViewHolder> {

    ArrayList<Post> mPosts;
    Context mContext;

    public RbrRecyclerAdapter(ArrayList<Post> posts) {
        mPosts = posts;
    }

    public RbrRecyclerAdapter(ArrayList<Post> posts, Context context) {
        mPosts = posts;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View row_itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.check, parent, false);

        return new MyViewHolder(row_itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Post post = mPosts.get(position);
        holder.setData(post, position);

        holder.row_itemCard.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nameTV, statusTV, dateTV, timeTV;
        CardView row_itemCard;

        public MyViewHolder(View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.name);
            statusTV = itemView.findViewById(R.id.status);
            dateTV = itemView.findViewById(R.id.date);
            timeTV = itemView.findViewById(R.id.time);
            row_itemCard = itemView.findViewById(R.id.row_itemCard);
        }

        public void setData(Post post, int position){
            nameTV.setText(post.getName());
            statusTV.setText(post.getStatus());
            dateTV.setText(post.getDate());
            timeTV.setText(post.getTime());
        }
    }
}
