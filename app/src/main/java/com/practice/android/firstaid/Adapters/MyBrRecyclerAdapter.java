package com.practice.android.firstaid.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.android.firstaid.Models.BloodRequestDetail;
import com.practice.android.firstaid.R;

import java.util.ArrayList;

/**
 * Created by Ashutosh on 9/24/2017.
 */

public class MyBrRecyclerAdapter extends RecyclerView.Adapter<MyBrRecyclerAdapter.MyViewHolder> {

    public static int flag = 0;
    ArrayList<BloodRequestDetail> mPosts;
    Context mContext;
    private DatabaseReference mDatabase;

    public MyBrRecyclerAdapter(ArrayList<BloodRequestDetail> posts) {
        mPosts = posts;
    }

    public MyBrRecyclerAdapter(ArrayList<BloodRequestDetail> posts, Context context) {
        mPosts = posts;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View row_itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_br_row_item, parent, false);

        return new MyViewHolder(row_itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        BloodRequestDetail post = mPosts.get(position);
        holder.setData(post, position);

//        holder.row_itemCard.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (holder.ll.getVisibility() == View.GONE) {
//                    holder.ll.setVisibility(View.VISIBLE);
//                }else {
//                    holder.ll.setVisibility(View.GONE);
//                }
//
//            }
//        });

        holder.del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase = FirebaseDatabase.getInstance().getReference("BloodRequest");

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            String ck = postSnapshot.getKey();

                            if(mPosts.get(position).getKey().equals(ck)){
                                postSnapshot.getRef().removeValue();
                                flag = 1;
                            }
                        }

                        if(flag == 1){
                            mPosts.remove(position);
                            notifyItemRemoved(position);
                            flag = 0;
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV, statusTV, dateTV, timeTV, fa_bg, acceptTV, rejectTV, del;
        CardView row_itemCard;
        LinearLayout ll;

        public MyViewHolder(View itemView) {
            super(itemView);

            nameTV = itemView.findViewById(R.id.name);
//            statusTV = itemView.findViewById(R.id.status);
            dateTV = itemView.findViewById(R.id.date);
            timeTV = itemView.findViewById(R.id.time);
            fa_bg = itemView.findViewById(R.id.fa_bg);
            row_itemCard = itemView.findViewById(R.id.row_itemCard);
            ll = itemView.findViewById(R.id.select_status);
//            acceptTV = itemView.findViewById(R.id.accept);
//            rejectTV = itemView.findViewById(R.id.reject);
            del = itemView.findViewById(R.id.del);
        }

        public void setData(BloodRequestDetail post, int position) {
            nameTV.setText(post.getName());
//            statusTV.setText(post.getStatus());
            dateTV.setText(post.getDate());
            timeTV.setText(post.getTime());
            fa_bg.setText(post.getBloodGroup());
        }
    }
}
