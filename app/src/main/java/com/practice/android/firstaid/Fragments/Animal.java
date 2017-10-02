package com.practice.android.firstaid.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.practice.android.firstaid.Activities.Main3Activity;
import com.practice.android.firstaid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Animal extends Fragment {

    public static Animal newInstance() {
        Animal fragment = new Animal();
        return fragment;
    }

    GridView grid;
    String[] animal = {
            "White Tailed Spider",
            "Bee or Wasp",
            "Snake",
            "Dog"

    };
    int[] imageId = {
            R.drawable.spider,
            R.drawable.wasp,
            R.drawable.snake,
            R.drawable.dog

    };


    public Animal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animal, container, false);
        CustomGrid adapter = new CustomGrid(getActivity(), animal, imageId);
        grid=(GridView)view.findViewById(R.id.grid_view);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id){

                Intent intent = new Intent(getActivity(), Main3Activity.class);
                startActivity(intent);

            }
        });



        return view;
    }



}
