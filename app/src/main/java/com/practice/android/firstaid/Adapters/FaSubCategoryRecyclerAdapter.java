package com.practice.android.firstaid.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.android.firstaid.Activities.MainActivity;
import com.practice.android.firstaid.Fragments.ContentPageFragment;
import com.practice.android.firstaid.Interfaces.HideFirstAidToolbar;
import com.practice.android.firstaid.Models.FaSubCategory;
import com.practice.android.firstaid.R;

import java.util.ArrayList;

/**
 * Created by parven on 16-10-2017.
 */

public class FaSubCategoryRecyclerAdapter extends RecyclerView.Adapter<FaSubCategoryRecyclerAdapter.MyViewHolder> {

    static int x = 0;
    protected ProgressDialog pDialog;
    ArrayList<FaSubCategory> subCategoriesList;
    Context context;
    private DatabaseReference mDatabase;

    public FaSubCategoryRecyclerAdapter() {
    }

    public FaSubCategoryRecyclerAdapter(ArrayList<FaSubCategory> subCategoriesList, Context context) {
        this.subCategoriesList = subCategoriesList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_single, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final FaSubCategory subCategory = subCategoriesList.get(position);


        Glide.with(context).load(subCategory.getIconUrl()).fitCenter().skipMemoryCache(false).into(holder.subCategoryImg);
        holder.subCategoryName.setText(subCategory.getName());

//        holder.subCategoryCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (holder.content.getVisibility() == View.GONE) {
//                    holder.content.setVisibility(View.VISIBLE);
//                    mAdapter = new RecyclerAdapter(matterlist);
//                    prepareMovieData();
//                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
//                    holder.content.setLayoutManager(mLayoutManager);
//                    holder.content.setItemAnimator(new DefaultItemAnimator());
//
//                    RecyclerView.ItemDecoration itemDecoration = new
//                            DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
//                    holder.content.addItemDecoration(itemDecoration);
//                    holder.content.setAdapter(mAdapter);
//
//                } else {
//                    holder.content.setVisibility(View.GONE);
//                }
//                holder.content
//
//
//
//
//            }
//        });

        holder.subCategoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pDialog = new ProgressDialog(context);
                pDialog.setMessage("Please wait...");
                pDialog.show();

                if (context instanceof HideFirstAidToolbar) {
                    ((HideFirstAidToolbar) context).hideToolbar(subCategory.getName());
                }
                mDatabase = FirebaseDatabase.getInstance().getReference("FirstAidContent");
                mDatabase.child(subCategory.getName()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        if (dataSnapshot.exists()) {
                            x = (int) dataSnapshot.getChildrenCount();
                            pDialog.dismiss();
                            callFrag(subCategory.getName());
                        } else {
                            pDialog.dismiss();
                            Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




//                context.startActivity(new Intent(context, AidInfoActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return subCategoriesList.size();
    }



    public void callFrag(String t) {
        ((MainActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, ContentPageFragment.newInstance(t, x), "Spider content").commit();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView subCategoryName;
        ImageView subCategoryImg;
        CardView subCategoryCard;
//        RecyclerView content;

        public MyViewHolder(View view) {

            super(view);
            subCategoryName = view.findViewById(R.id.fa_sub_tv);
            subCategoryImg = view.findViewById(R.id.fa_sub_img);
            subCategoryCard = view.findViewById(R.id.subCategoryCard);
//            content = view.findViewById(R.id.subCategoryContent);

        }
    }

}
