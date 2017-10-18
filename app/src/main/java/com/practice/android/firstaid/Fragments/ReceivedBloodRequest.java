package com.practice.android.firstaid.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.android.firstaid.Adapters.RbrRecyclerAdapter;
import com.practice.android.firstaid.Models.BloodRequestDetail;
import com.practice.android.firstaid.Models.UserInfo;
import com.practice.android.firstaid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReceivedBloodRequest extends Fragment {

    private DatabaseReference mDatabase;
    private DatabaseReference mUserReference;
    String UserID;
    Switch filterSwitch;
    boolean mIsFilterOn = false;

    RecyclerView mRecyclerView;
    RbrRecyclerAdapter mRecyclerAdapter;

    ArrayList<BloodRequestDetail> check;
    ArrayList<BloodRequestDetail> adapterList;
    List<String> mMyCities;


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
        adapterList = new ArrayList<>();


        mDatabase = FirebaseDatabase.getInstance().getReference("BloodRequest");
        mUserReference = FirebaseDatabase.getInstance().getReference("userinfo");
        getUserDetails();


        View v = inflater.inflate(R.layout.fragment_received_blood_request, container, false);

        filterSwitch = v.findViewById(R.id.filter_Switch);
        filterSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //        Toast.makeText(getContext(), "filter : " + filterSwitch.isChecked(), Toast.LENGTH_SHORT).show();
                if (filterSwitch.isChecked()) {
                    mIsFilterOn = true;
                } else {
                    mIsFilterOn = false;
                }
                createAdapterList();

            }
        });



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

    public void getUserDetails() {
        mUserReference.child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
                mMyCities = userInfo.getCities();


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


    public void createAdapterList() {

        adapterList = new ArrayList<>();

        if (mIsFilterOn) {

            for (BloodRequestDetail detail : check)
                if (mMyCities.contains(detail.getCity())) {
                    adapterList.add(detail);

                }
        } else {
            adapterList = check;
        }
        mRecyclerAdapter.changeListData(adapterList);
    }


}
