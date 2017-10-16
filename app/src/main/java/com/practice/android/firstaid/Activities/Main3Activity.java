package com.practice.android.firstaid.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.practice.android.firstaid.Adapters.CityRecyclerAdapter;
import com.practice.android.firstaid.Adapters.FaSubCategoryRecyclerAdapter;
import com.practice.android.firstaid.Models.FaSubCategory;
import com.practice.android.firstaid.R;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {


    private FragmentManager mFragmentManager;
    private com.practice.android.firstaid.Activities.FragmentInfo FragmentInfo;
    RecyclerView subCategoryRecycler;
    FaSubCategoryRecyclerAdapter subCategoryRecyclerAdapter;

    ArrayList<FaSubCategory> subCategoryList;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main3);
        setContentView(R.layout.frag_info2);

//        mFragmentManager = getSupportFragmentManager();
//        FragmentInfo = new FragmentInfo();
//
//        FragmentTransaction transaction = mFragmentManager.beginTransaction();
//        transaction.add(R.id.frame,FragmentInfo);
//        transaction.commit();
        toolbar = (Toolbar) findViewById(R.id.toolbarFaSub);
        toolbar.setNavigationIcon(R.drawable.arrow1);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

//                Toast.makeText(getApplicationContext(),"your icon was clicked",Toast.LENGTH_SHORT).show();
            }
        });

        subCategoryList = new ArrayList<>();

        subCategoryRecycler = (RecyclerView) findViewById(R.id.fa_sub_recyclerList);
        subCategoryRecyclerAdapter = new FaSubCategoryRecyclerAdapter(subCategoryList, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        subCategoryRecycler.setLayoutManager(linearLayoutManager);

        subCategoryRecycler.setAdapter(subCategoryRecyclerAdapter);

        getSubcategories();
    }

    public void getSubcategories(){
        subCategoryList.add(new FaSubCategory(R.drawable.spider, "Spider"));
        subCategoryList.add(new FaSubCategory(R.drawable.wasp, "Wasp"));
        subCategoryList.add(new FaSubCategory(R.drawable.snake, "Snake"));
        subCategoryList.add(new FaSubCategory(R.drawable.dog, "Dog"));

        subCategoryRecyclerAdapter.notifyDataSetChanged();

    }


}
