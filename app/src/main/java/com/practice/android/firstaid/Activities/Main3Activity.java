package com.practice.android.firstaid.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.practice.android.firstaid.R;

public class Main3Activity extends AppCompatActivity {


    private FragmentManager mFragmentManager;
    private com.practice.android.firstaid.Activities.FragmentInfo FragmentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        mFragmentManager = getSupportFragmentManager();
        FragmentInfo = new FragmentInfo();

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.frame,FragmentInfo);
        transaction.commit();



    }


}
