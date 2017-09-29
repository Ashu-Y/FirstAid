package com.practice.android.firstaid.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.practice.android.firstaid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BloodMyRequest extends Fragment {


    public BloodMyRequest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_blood_my_request, container, false);

        TextView here = v.findViewById(R.id.openForm);

        here.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), com.practice.android.firstaid.Activities.BloodRequestForm.class));
            }
        });

        return v;
    }

}
