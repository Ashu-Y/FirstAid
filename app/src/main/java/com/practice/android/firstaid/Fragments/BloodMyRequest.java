package com.practice.android.firstaid.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.android.firstaid.Adapters.MyBrRecyclerAdapter;
import com.practice.android.firstaid.Models.BloodRequestDetail;
import com.practice.android.firstaid.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BloodMyRequest extends Fragment {

    String UserID;
    RecyclerView mRecyclerView;
    MyBrRecyclerAdapter mRecyclerAdapter;
    ArrayList<BloodRequestDetail> check;
    ImageView placeHolderImg;
    TextView placeHolderTv;
    private DatabaseReference mDatabase;

    public BloodMyRequest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            UserID = user.getUid();
            final String curremail = user.getEmail();
            Log.d("FirstSignInSupport", curremail);
        }

        mDatabase = FirebaseDatabase.getInstance().getReference("BloodRequest");
        mDatabase.keepSynced(true);


        View v = inflater.inflate(R.layout.fragment_blood_my_request, container, false);

        placeHolderImg = v.findViewById(R.id.noReqImg);
        placeHolderTv = v.findViewById(R.id.noReqTV);

        FloatingActionButton here = v.findViewById(R.id.fab_open_form);

        here.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), com.practice.android.firstaid.Activities.BloodRequestForm.class));
            }
        });

        mRecyclerView = v.findViewById(R.id.recycler_myBR);

        getDetails();


        return v;
    }

    public void getDetails() {


        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                check = new ArrayList<>();


                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Log.e("BloodMyRequest", postSnapshot.getKey());

                    BloodRequestDetail userInfo = postSnapshot.getValue(BloodRequestDetail.class);

                    if (userInfo.getUserID() != null) {
                        if (userInfo.getUserID().equals(UserID)) {
                            ch(userInfo);
                        }
                    }
                }

                if (check.isEmpty()) {
                    placeHolderImg.setVisibility(View.VISIBLE);
                    placeHolderTv.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void ch(BloodRequestDetail detail) {

        placeHolderImg.setVisibility(View.GONE);
        placeHolderTv.setVisibility(View.GONE);

        check.add(new BloodRequestDetail(detail.getName(), detail.getPhone(), detail.getCity(), detail.getComments(),
                detail.getBloodGroup(), detail.getDate(), detail.getTime(), detail.getStatus(), detail.getUserID(),
                detail.getKey(), detail.getAcceptorID()));

        mRecyclerAdapter = new MyBrRecyclerAdapter(check);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

}
