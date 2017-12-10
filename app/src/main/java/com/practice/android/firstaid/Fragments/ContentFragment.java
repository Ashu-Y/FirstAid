package com.practice.android.firstaid.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.android.firstaid.Adapters.DescAdapter;
import com.practice.android.firstaid.Models.DescModel;
import com.practice.android.firstaid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment {


    public static final String ARG_PAGE = "page";
    public static final String TITLE = "Title";
    public RecyclerView desc_recycler;
    //    public ContentAdapter adapter;
//    public List<Content_Row_Item> content;
    public List<String> desc;
    public DescAdapter descAdapter;
    TextView titleTv;
    ImageView stepImage;

    private int mPageNumber;
    private String mTitle, stepTitle;
    private DatabaseReference mDatabase;

    public ContentFragment() {
        // Required empty public constructor
    }


    public static ContentFragment newInstance(int pageNumber, String title) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        mTitle = getArguments().getString(TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        titleTv = view.findViewById(R.id.title);
        stepImage = view.findViewById(R.id.stepImage);

        desc = new ArrayList<>();

//        getDescList();

        desc_recycler = view.findViewById(R.id.desc_recycler);

        RecyclerView.LayoutManager recyclerLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        desc_recycler.setLayoutManager(recyclerLayout);


//        descAdapter = new DescAdapter(desc);
//        desc_recycler.setAdapter(descAdapter);

//TODO edit child in this one line
        mDatabase = FirebaseDatabase.getInstance().getReference("FirstAidContent").child(mTitle);
        mDatabase.keepSynced(true);

//        getDetails();

        mDatabase.child("" + (mPageNumber + 1)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DescModel descModel = dataSnapshot.getValue(DescModel.class);

                setDesc(descModel);

//                descAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

//    public void getDescList() {
//
//
////        mDatabase = FirebaseDatabase.getInstance().getReference("FirstAidContent//" + mTitle);
//
//        desc.add("\u2022 \t Wash the bitten area well to remove any remaining venom from the skin.");
//        desc.add("\u2022 \t Keep the patient still to reduce the toxic effects of the venom.");
//        desc.add("\u2022 \t Apply a wrapped ice pack for up to 10 minutes at a time, or a cold compress that has been soaked in water to which a few ice cubes have been added.");
//
//    }

    public void setDesc(DescModel descModel) {

        Glide.with(getActivity()).load(descModel.getImageUrl()).fitCenter().skipMemoryCache(false).placeholder(R.drawable.placeholder).into(stepImage);

        stepTitle = descModel.getTitle();
        titleTv.setText((mPageNumber + 1) + ". " + stepTitle);

        desc.clear();
        desc = descModel.getDesc();
        descAdapter = new DescAdapter(desc);

        desc_recycler.setAdapter(descAdapter);

        descAdapter.notifyDataSetChanged();
    }

//    public void getDetails() {
//        mDatabase.child("Animal Bite").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
//
//                ch(userInfo);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }


}
