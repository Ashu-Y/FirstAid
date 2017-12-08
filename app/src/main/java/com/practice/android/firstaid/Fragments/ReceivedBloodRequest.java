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

    String UserID;
    Switch filterSwitch;
    boolean mIsFilterOn = false;
    RecyclerView mRecyclerView;
    RbrRecyclerAdapter mRecyclerAdapter;
    ArrayList<BloodRequestDetail> check;
    ArrayList<BloodRequestDetail> adapterList;
    List<String> mMyCities;
    private DatabaseReference mDatabase;
    private DatabaseReference mUserReference;


    public ReceivedBloodRequest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_received_blood_request, container, false);

//        RecyclerView cardView = (RecyclerView) v.findViewById(R.id.recycler_receivedBR);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            UserID = user.getUid();

            final String curremail = user.getEmail();
            Log.d("FirstSignInSupport", curremail);
        }

        check = new ArrayList<>();
        adapterList = new ArrayList<>();


        mDatabase = FirebaseDatabase.getInstance().getReference("BloodRequest");
        mDatabase.keepSynced(true);
        mUserReference = FirebaseDatabase.getInstance().getReference("userinfo");
        mUserReference.keepSynced(true);
        getUserDetails();


        filterSwitch = v.findViewById(R.id.filter_Switch);
        filterSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //        Toast.makeText(getContext(), "filter : " + filterSwitch.isChecked(), Toast.LENGTH_SHORT).show();
                mIsFilterOn = filterSwitch.isChecked();
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

                mRecyclerAdapter = new RbrRecyclerAdapter(check, getContext());


                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
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
            if (mMyCities != null) {

                for (BloodRequestDetail detail : check)
                    if (mMyCities.contains(detail.getCity())) {
                        adapterList.add(detail);

                    }
            }
        } else {
            adapterList = check;
        }
        mRecyclerAdapter.changeListData(adapterList);
        mRecyclerView.scrollToPosition(adapterList.size() - 1);
    }


//    @Override
//    public void onResume() {
//        super.onResume();
//
//        int[] location = new int[2];
//        mRecyclerView.getLocationInWindow(location);
//
//        int x = location[0];
//        int y = location[1];
//
//        x = (int) mRecyclerView.getX() + mRecyclerView.getWidth() / 2;
//        y = (int) mRecyclerView.getY() + mRecyclerView.getHeight() / 2;
//
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        y = displayMetrics.heightPixels / 2;
//        x = displayMetrics.widthPixels / 2;
//        new SpotlightView.Builder(getActivity())
//                .introAnimationDuration(400)
//                .enableRevealAnimation(true)
//                .performClick(true)
//                .fadeinTextDuration(400)
//                .headingTvColor(Color.parseColor("#eb273f"))
//                .headingTvSize(32)
//                .headingTvText("Love")
//                .subHeadingTvColor(Color.parseColor("#ffffff"))
//                .subHeadingTvSize(16)
//                .subHeadingTvText("Like the picture?\nLet others know.")
//                .maskColor(Color.parseColor("#dc000000"))
//                .target(cardView)
//                .lineAnimDuration(400)
//                .lineAndArcColor(Color.parseColor("#eb273f"))
//                .dismissOnTouch(true)
//                .dismissOnBackPress(true)
//                .enableDismissAfterShown(true)
//                .usageId("1") //UNIQUE ID
//                .show();


//        SimpleTarget simpleTarget = new SimpleTarget.Builder(getActivity())
//                .setPoint(x,y)// position of the Target. setPoint(Point point), setPoint(View view) will work too.
//                .setRadius(200f) // radius of the Target
//                .setTitle("the title") // title
//                .setDescription("the description") // description
//                .setOnSpotlightStartedListener(new OnTargetStateChangedListener<SimpleTarget>() {
//                    @Override
//                    public void onStarted(SimpleTarget target) {
//                        // do something
//                    }
//                    @Override
//                    public void onEnded(SimpleTarget target) {
//                        // do something
//                    }
//                })
//                .build();
//
//        Spotlight.with(getActivity())
//                .setDuration(1000L) // duration of Spotlight emerging and disappearing in ms
//                .setAnimation(new DecelerateInterpolator(2f)) // animation of Spotlight
//                .setTargets(simpleTarget) // set targets. see below for more info
//                .setOnSpotlightStartedListener(new OnSpotlightStartedListener() { // callback when Spotlight starts
//                    @Override
//                    public void onStarted() {
//                        Toast.makeText(getActivity(), "spotlight is started", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setOnSpotlightEndedListener(new OnSpotlightEndedListener() { // callback when Spotlight ends
//                    @Override
//                    public void onEnded() {
//                        Toast.makeText(getActivity(), "spotlight is ended", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .start(); // start Spotlight
//    }
}

