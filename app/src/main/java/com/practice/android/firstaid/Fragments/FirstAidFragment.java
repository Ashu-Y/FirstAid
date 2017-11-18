package com.practice.android.firstaid.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.android.firstaid.Adapters.FaSubCategoryRecyclerAdapter;
import com.practice.android.firstaid.Models.FaSubCategory;
import com.practice.android.firstaid.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstAidFragment extends Fragment {

    public static String Ftag;
    RecyclerView subCategoryRecycler;
    FaSubCategoryRecyclerAdapter subCategoryRecyclerAdapter;
    ArrayList<FaSubCategory> subCategoryList;
    private DatabaseReference mDatabase;

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
        View view = inflater.inflate(R.layout.frag_info2, container, false);

//        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.animal);
//        linearLayout.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
////                transaction.replace(R.id.content, FragmentInfo.newInstance(), "Animal");
////                transaction.commit();
//
//                startActivity(new Intent(getActivity(), Main3Activity.class));
//            }
//
//        });

        mDatabase = FirebaseDatabase.getInstance().getReference("FirstAidCategory");
        mDatabase.keepSynced(true);



        subCategoryList = new ArrayList<>();

        subCategoryRecycler = view.findViewById(R.id.fa_sub_recyclerList);
        subCategoryRecyclerAdapter = new FaSubCategoryRecyclerAdapter(subCategoryList, getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        subCategoryRecycler.setLayoutManager(linearLayoutManager);
        subCategoryRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        subCategoryRecycler.setAdapter(subCategoryRecyclerAdapter);

        getSubcategories();
        return view;
    }

//    public void getSubcategories() {
//        subCategoryList.add(new FaSubCategory(R.drawable.spider, "Spider Bite"));
//        subCategoryList.add(new FaSubCategory(R.drawable.wasp, "Bee Sting"));
//        subCategoryList.add(new FaSubCategory(R.drawable.snake, "Snake Bite"));
//        subCategoryList.add(new FaSubCategory(R.drawable.dog, "Dog Bite"));
//        subCategoryList.add(new FaSubCategory(R.drawable.bleeding, "Bleeding"));
//        subCategoryList.add(new FaSubCategory(R.drawable.bones_muscles, "Broken Bone"));
//        subCategoryList.add(new FaSubCategory(R.drawable.burns, "Burns"));
//        subCategoryList.add(new FaSubCategory(R.drawable.choking, "Choking"));
//        subCategoryList.add(new FaSubCategory(R.drawable.chest_pain, "Chest Pain"));
//
//        subCategoryRecyclerAdapter.notifyDataSetChanged();
//
//    }

    public void getSubcategories() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                subCategoryList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    FaSubCategory faSubCategory = postSnapshot.getValue(FaSubCategory.class);

                    ch(faSubCategory);
                }

                subCategoryRecyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void ch(FaSubCategory faSubCategory) {
        subCategoryList.add(new FaSubCategory(faSubCategory.getIconUrl(), faSubCategory.getName()));

    }

}
