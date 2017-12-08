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
import com.practice.android.firstaid.Activities.Matter;
import com.practice.android.firstaid.Fragments.SpiderContentFragment;
import com.practice.android.firstaid.Interfaces.HideFirstAidToolbar;
import com.practice.android.firstaid.Models.FaSubCategory;
import com.practice.android.firstaid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by parven on 16-10-2017.
 */

public class FaSubCategoryRecyclerAdapter extends RecyclerView.Adapter<FaSubCategoryRecyclerAdapter.MyViewHolder> {

    static int x = 0;
    protected ProgressDialog pDialog;
    ArrayList<FaSubCategory> subCategoriesList;
    Context context;
    private List<Matter> matterlist = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;
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

    private void prepareMovieData() {

        matterlist.clear();

        Matter movie = new Matter("Apply cold treatment", "Wash the bitten area well to remove any remaining venom from the skin.\n"
                + "Keep the patient still to reduce the toxic effects of the venom.\n"
                + "Apply a wrapped ice pack for up to 10 minutes at a time, or a cold \n" +
                "compress that has been soaked in water to which a few ice cubes have been \n" +
                "added.");
        matterlist.add(movie);

        movie = new Matter("Raise a bitten limb", "If the bite is on a limb, raise it to limit swelling.\n"
                + "If an arm or hand is involved, apply an elevation sling to provide comfort \n" +
                "and support.");
        matterlist.add(movie);

//        movie = new Matter("Star Wars: Episode VII - The Force Awakens", "Action");
//        matterlist.add(movie);
//
//        movie = new Matter("Shaun the Sheep", "Animation");
//        matterlist.add(movie);
//
//        movie = new Matter("The Martian", "Science Fiction & Fantasy");
//        matterlist.add(movie);
//
//        movie = new Matter("Mission: Impossible Rogue Nation", "Action");
//        matterlist.add(movie);
//
//        movie = new Matter("Up", "Animation");
//        matterlist.add(movie);
//
//        movie = new Matter("Star Trek", "Science Fiction");
//        matterlist.add(movie);
//
//        movie = new Matter("The LEGO Matter()", "Animation");
//        matterlist.add(movie);


        mAdapter.notifyDataSetChanged();
    }

    public void callFrag(String t) {
        ((MainActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, SpiderContentFragment.newInstance(t, x), "Spider content").commit();

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
