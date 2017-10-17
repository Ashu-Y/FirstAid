package com.practice.android.firstaid.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.android.firstaid.Adapters.RbrRecyclerAdapter;
import com.practice.android.firstaid.Models.BloodRequestDetail;
import com.practice.android.firstaid.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReceivedBloodRequest extends Fragment {

    private DatabaseReference mDatabase;
    String UserID;

    RecyclerView mRecyclerView;
    RbrRecyclerAdapter mRecyclerAdapter;

    ArrayList<BloodRequestDetail> check;


    public ReceivedBloodRequest() {
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

        check = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference("BloodRequest");


        View v = inflater.inflate(R.layout.fragment_received_blood_request, container, false);

        mRecyclerView = v.findViewById(R.id.recycler_receivedBR);

        getDetails();

//        mRecyclerAdapter = new RbrRecyclerAdapter(check);
//
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        mRecyclerView.setLayoutManager(linearLayoutManager);
//        mRecyclerView.setAdapter(mRecyclerAdapter);

        return v;
    }


    public void getDetails() {



        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                check.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    BloodRequestDetail userInfo = postSnapshot.getValue(BloodRequestDetail.class);

                    ch(userInfo);
                }


                mRecyclerAdapter = new RbrRecyclerAdapter(check,getContext());


                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(linearLayoutManager);
                mRecyclerView.setAdapter(mRecyclerAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void ch(BloodRequestDetail detail) {

        check.add(new BloodRequestDetail(detail.getName(), detail.getPhone(), detail.getCity(), detail.getComments(),
                detail.getBloodGroup(), detail.getDate(), detail.getTime(), detail.getStatus(), detail.getUserID(),
                detail.getKey(), detail.getAcceptorID()));

    }


}
