package com.practice.android.firstaid.Adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.practice.android.firstaid.Models.BloodRequestDetail;
import com.practice.android.firstaid.R;

import java.util.ArrayList;

/**
 * Created by Ashutosh on 9/24/2017.
 */

public class RbrRecyclerAdapter extends RecyclerView.Adapter<RbrRecyclerAdapter.MyViewHolder> {

    String UserID;
    Toast toast;
    int vis = 0;

    ArrayList<BloodRequestDetail> mPosts;
    Context mContext;
    private DatabaseReference mDatabase;

    public RbrRecyclerAdapter(ArrayList<BloodRequestDetail> posts) {
        mPosts = posts;
    }

    public RbrRecyclerAdapter(ArrayList<BloodRequestDetail> posts, Context context) {
        mPosts = posts;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View row_itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.check, parent, false);

        return new MyViewHolder(row_itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final BloodRequestDetail post = mPosts.get(position);
        holder.setData(post, position);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            UserID = user.getUid();
            final String curremail = user.getEmail();
            Log.d("FirstSignInSupport", curremail);
        }

//        holder.row_itemCard.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
////                mDatabase = FirebaseDatabase.getInstance().getReference("BloodRequest");
////
////                mDatabase.addValueEventListener(new ValueEventListener() {
////                    @Override
////                    public void onDataChange(DataSnapshot dataSnapshot) {
////
////                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
////
////                        if (user != null) {
////
////                            UserID = user.getUid();
////                            final String curremail = user.getEmail();
////                            Log.d("FirstSignInSupport", curremail);
////                        }
////
////                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
////
////                            BloodRequestDetail userInfo = postSnapshot.getValue(BloodRequestDetail.class);
////
////                            if (userInfo.getKey().equals(mPosts.get(position).getKey())) {
////
////                                if (userInfo.getStatus().equals("Pending")) {
////
////                                    if (!userInfo.getUserID().equals(UserID)) {
////                                        vis = 1;
////                                    }
////                                }
////                            }
////                        }
////
////                    }
////
////                    @Override
////                    public void onCancelled(DatabaseError databaseError) {
////
////                    }
////                });
//
////                if(!mPosts.get(position).getUserID().equals(UserID)){
////                    if (mPosts.get(position).getStatus().equals("Pending")){
////                        vis = 1;
////                    }
////                }
////
////                if (vis == 1) {
////                    if (holder.ll.getVisibility() == View.GONE) {
////                        holder.ll.setVisibility(View.VISIBLE);
////                    } else {
////                        holder.ll.setVisibility(View.GONE);
////                    }
////                    vis = 0;
////                }
//
//
//
//            }
//        });

        if (!mPosts.get(position).getPhone().isEmpty() || (mPosts.get(position).getPhone() == null)) {
            holder.ll.setVisibility(View.VISIBLE);
        }

        holder.row_itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (toast == null) {

                    toast = Toast.makeText(mContext, "Long press on the card to copy phone number", Toast.LENGTH_SHORT);
                    //toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                } else {
                    toast.cancel();
                }

            }

        });

        holder.row_itemCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager clipBoard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Number", post.getPhone());
                clipBoard.setPrimaryClip(clip);

                Toast.makeText(mContext, "Phone Number Copied", Toast.LENGTH_SHORT).show();
                return true;
            }
        });


//        holder.acceptTV.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                mDatabase = FirebaseDatabase.getInstance().getReference("BloodRequest");
//
//                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//
//                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
//                            String ck = post.getKey();
//                            BloodRequestDetail bloodRequestDetail = snapshot.getValue(BloodRequestDetail.class);
//
//
//
//                            if (bloodRequestDetail.getKey().equals(ck)) {
//
//                                mDatabase.child(ck).child("Status").setValue("Accepted");
//                                mDatabase.child(ck).child("AcceptorID").setValue(UserID);
//
//                            }
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//
//            }
//        });

//        holder.rejectTV.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });


    }


    public void changeListData(ArrayList<BloodRequestDetail> data) {
        if (data != null) {
            mPosts = data;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV, statusTV, dateTV, timeTV, fa_bg, acceptTV, rejectTV, phoneTv;
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
            phoneTv = itemView.findViewById(R.id.phone);
//            acceptTV = itemView.findViewById(R.id.accept);
//            rejectTV = itemView.findViewById(R.id.reject);
        }

        public void setData(BloodRequestDetail post, int position) {
            nameTV.setText(post.getName());
//            statusTV.setText(post.getStatus());
            dateTV.setText(post.getDate());
            timeTV.setText(post.getTime());
            fa_bg.setText(post.getBloodGroup());
            phoneTv.setText("Call: " + post.getPhone());
        }

    }
}
