package com.practice.android.firstaid.Activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practice.android.firstaid.Adapters.RecyclerAdapter;
import com.practice.android.firstaid.Fragments.FirstAidFragment;
import com.practice.android.firstaid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInfo extends Fragment {

    private List<Matter> matterlist = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;


    public FragmentInfo() {
        // Required empty public constructor
    }

    public static FragmentInfo newInstance() {
        FragmentInfo fragment = new FragmentInfo();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_info, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mAdapter = new RecyclerAdapter(matterlist);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(mAdapter);

        prepareMovieData();

        return view;
    }

    private void prepareMovieData() {
        Matter movie = new Matter("Apply cold treatment", "Wash the bitten area well to remove any remaining venom from the skin.\n"
                + "Keep the patient still to reduce the toxic effects of the venom.\n"
                + "Apply a wrapped ice pack for up to 10 minutes at a time, or a cold \n" +
                "compress that has been soaked in water to which a few ice cubes have been \n" +
                "added.");
        matterlist.add(movie);

        movie = new Matter("Raise a bitten limb", "If the bite is on a limb, raise it to limit swelling.\n"
                + "If an arm or hand is involved, apply an elevation sling to provide comfort \n" +
                "and support.");
        matterlist.add(movie);

//        movie = new Matter("Star Wars: Episode VII - The Force Awakens", "Action");
//        matterlist.add(movie);
//
//        movie = new Matter("Shaun the Sheep", "Animation");
//        matterlist.add(movie);
//
//        movie = new Matter("The Martian", "Science Fiction & Fantasy");
//        matterlist.add(movie);
//
//        movie = new Matter("Mission: Impossible Rogue Nation", "Action");
//        matterlist.add(movie);
//
//        movie = new Matter("Up", "Animation");
//        matterlist.add(movie);
//
//        movie = new Matter("Star Trek", "Science Fiction");
//        matterlist.add(movie);
//
//        movie = new Matter("The LEGO Matter()", "Animation");
//        matterlist.add(movie);


        mAdapter.notifyDataSetChanged();
    }

}
