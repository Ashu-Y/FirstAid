package com.practice.android.firstaid.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.android.firstaid.Adapters.CityRecyclerAdapter;
import com.practice.android.firstaid.Models.UserInfo;
import com.practice.android.firstaid.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    //    private FirebaseAuth.AuthStateListener mAuthListener;
//    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;
    String UserID;
    //    private GoogleApiClient mGoogleApiClient;
    ArrayList<String> cityList;
    CityRecyclerAdapter cityRecyclerAdapter;
    RecyclerView cityRecycler;
    TextView nameTv, fa_btnTv, genderTv, dobTv, bgTv, phoneTv, langTv, noCity;
    Switch donateSwitch;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        nameTv = view.findViewById(R.id.nameTv);
        fa_btnTv = view.findViewById(R.id.fa_btnTv);
        genderTv = view.findViewById(R.id.genderTv);
        dobTv = view.findViewById(R.id.dobTv);
        bgTv = view.findViewById(R.id.bgTv);
        phoneTv = view.findViewById(R.id.phoneTv);
//        langTv = view.findViewById(R.id.langTv);
        cityRecycler = view.findViewById(R.id.city_recycler_profile);
        noCity = view.findViewById(R.id.noCity);

        donateSwitch = view.findViewById(R.id.donateSwitch);

        cityList = new ArrayList<>();
        cityRecyclerAdapter = new CityRecyclerAdapter(cityList);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            UserID = user.getUid();
            final String curremail = user.getEmail();
            Log.d("ProfileFragment", curremail);
        }

        mDatabase = FirebaseDatabase.getInstance().getReference("userinfo");

        getDetails();

        setHasOptionsMenu(true);
//        Toolbar toolbar = view.findViewById(R.id.tool);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        toolbar.setLogo(R.drawable.settings);
//        toolbar.inflateMenu(R.menu.menu_main);

        return view;
    }


//    @Override
//        public void OnCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//            inflater.inflate(R.menu.menu_main, menu);
//            super.onCreateOptionsMenu(menu,inflater);
//
//        }

    public void getDetails() {
        mDatabase.child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);

                ch(userInfo);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void ch(UserInfo userInfo) {

        cityList.clear();

        cityList = (ArrayList<String>) userInfo.getCities();
        cityRecyclerAdapter = new CityRecyclerAdapter(cityList);
        cityRecyclerAdapter.notifyDataSetChanged();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        cityRecycler.setLayoutManager(linearLayoutManager);
        cityRecycler.setAdapter(cityRecyclerAdapter);

        try {



//            if(cityList.isEmpty()){
//                noCity.setVisibility(View.VISIBLE);
//                cityRecycler.setVisibility(View.GONE);
//            } else {
//                noCity.setVisibility(View.GONE);
//                cityRecycler.setVisibility(View.VISIBLE);
//            }
//
//            noCity.setVisibility(View.GONE);
//            cityRecycler.setVisibility(View.VISIBLE);

            if(cityList.isEmpty()){
                noCity.setVisibility(View.VISIBLE);
                cityRecycler.setVisibility(View.GONE);
            } else {
                noCity.setVisibility(View.GONE);
                cityRecycler.setVisibility(View.VISIBLE);
            }

            nameTv.setText(userInfo.getName());
            fa_btnTv.setText(userInfo.getBloodGroup());
            genderTv.setText(userInfo.getGender());
            dobTv.setText(userInfo.getDOB());
            bgTv.setText(userInfo.getBloodGroup());
            phoneTv.setText(userInfo.getPhoneNumber());
//            langTv.setText(userInfo.getLanguages());

            if (userInfo.getInterestedinDonating().equals("true")) {
                donateSwitch.setChecked(true);
            }
        } catch (NullPointerException e) {
            Log.e("ProfileFragment: ", e.getMessage());
        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }
}


