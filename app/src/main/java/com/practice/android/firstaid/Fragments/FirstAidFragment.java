package com.practice.android.firstaid.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.practice.android.firstaid.Activities.FragmentInfo;
import com.practice.android.firstaid.Activities.Main3Activity;
import com.practice.android.firstaid.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstAidFragment extends Fragment {


    public FirstAidFragment() {
        // Required empty public constructor
    }

    public static FirstAidFragment newInstance() {
        FirstAidFragment fragment = new FirstAidFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_aid, container, false);

        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.animal);
        linearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

//                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.content, FragmentInfo.newInstance(), "Animal");
//                transaction.commit();

                startActivity(new Intent(getActivity(), Main3Activity.class));
            }

        });
        return view;
    }

}
