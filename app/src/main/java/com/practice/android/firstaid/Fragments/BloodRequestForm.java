package com.practice.android.firstaid.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practice.android.firstaid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BloodRequestForm extends Fragment {


    public BloodRequestForm() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_blood_request_form, container, false);

        Toolbar mToolbar = (Toolbar) v.findViewById(R.id.form_toolbar);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.form, menu);

    }
}
