package com.practice.android.firstaid.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practice.android.firstaid.Adapters.RbrRecyclerAdapter;
import com.practice.android.firstaid.Models.Post;
import com.practice.android.firstaid.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReceivedBloodRequest extends Fragment {

    RecyclerView mRecyclerView;
    RbrRecyclerAdapter mRecyclerAdapter;

    ArrayList<Post> check ;


    public ReceivedBloodRequest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        check = new ArrayList<>();
        check.add(new Post("Robin", "Pending", "27th May, 2017", "08:00 AM"));
        check.add(new Post("Robin", "Pending", "27th May, 2017", "08:00 AM"));
        check.add(new Post("Robin", "Pending", "27th May, 2017", "08:00 AM"));
        check.add(new Post("Robin", "Pending", "27th May, 2017", "08:00 AM"));
        check.add(new Post("Robin", "Pending", "27th May, 2017", "08:00 AM"));
        check.add(new Post("Robin", "Pending", "27th May, 2017", "08:00 AM"));
        check.add(new Post("Robin", "Pending", "27th May, 2017", "08:00 AM"));
        check.add(new Post("Robin", "Pending", "27th May, 2017", "08:00 AM"));

        View v = inflater.inflate(R.layout.fragment_received_blood_request, container, false);

        mRecyclerView = v.findViewById(R.id.recycler_receivedBR);

        mRecyclerAdapter = new RbrRecyclerAdapter(check);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        return v;
    }

}
