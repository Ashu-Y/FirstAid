package com.practice.android.firstaid.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.practice.android.firstaid.Fragments.BloodNetworkFragment;
import com.practice.android.firstaid.Fragments.FirstAidFragment;
import com.practice.android.firstaid.Fragments.ProfileFragment;
import com.practice.android.firstaid.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private FirstAidFragment mFirstAidFragment;
    private FragmentManager mFragmentManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_firstAid:
                    mToolbar.setTitle(R.string.tool_first_aid);
                        selectedFragment = FirstAidFragment.newInstance();
                    break;
                case R.id.navigation_bloodNetwork:
                    mToolbar.setTitle(R.string.tool_blood_network);
                    selectedFragment = BloodNetworkFragment.newInstance();
                    break;
                case R.id.navigation_searchHospital:
                    mToolbar.setTitle(R.string.tool_search_hospital);
                    selectedFragment = null;
                    return true;
                case R.id.navigation_profile:
                    mToolbar.setTitle(R.string.tool_profile);
                    selectedFragment = ProfileFragment.newInstance();
                    break;
            }

            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.replace(R.id.content, selectedFragment, "First Aid Fragment");
            transaction.commit();

            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFirstAidFragment = new FirstAidFragment();
        mFragmentManager = getSupportFragmentManager();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        String selectedId = getIntent().getExtras().getString("SelectedId");

//        if(selectedId.equals("Blood Network")){
//            FragmentTransaction transaction = mFragmentManager.beginTransaction();
//            transaction.replace(R.id.content, BloodNetworkFragment.newInstance(), "First Aid Fragment");
//            transaction.commit();
//
//            return;
//        }

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.content, mFirstAidFragment, "First Aid Fragment");
        transaction.commit();

    }

}
