package com.practice.android.firstaid.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.practice.android.firstaid.Fragments.BloodNetworkFragment;
import com.practice.android.firstaid.Fragments.FirstAidFragment;
import com.practice.android.firstaid.Fragments.MapFragment;
import com.practice.android.firstaid.Fragments.ProfileFragment;
import com.practice.android.firstaid.Helper.BottomNavigationViewHelper;
import com.practice.android.firstaid.Interfaces.HideFirstAidToolbar;
import com.practice.android.firstaid.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements HideFirstAidToolbar {


    private static int pid;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
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

                    item.setIcon(R.drawable.menu_first_aid2);

                    mToolbar.setTitle(R.string.tool_first_aid);
                    mToolbar.setVisibility(View.VISIBLE);
//                    mToolbar.inflateMenu(R.menu.activity_screen_slide);
                    mToolbar.getMenu().clear();

//                    getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.content)).commit();

                    selectedFragment = FirstAidFragment.newInstance();
                    break;
                case R.id.navigation_bloodNetwork:

                    item.setIcon(R.drawable.menu_blood_net2);

                    mToolbar.setTitle(R.string.tool_blood_network);
                    mToolbar.setVisibility(View.VISIBLE);
                    mToolbar.getMenu().clear();
                    pid = R.id.navigation_bloodNetwork;
                    selectedFragment = BloodNetworkFragment.newInstance();
                    break;
                case R.id.navigation_searchHospital:

                    item.setIcon(R.drawable.menu_hospital2);

                    mToolbar.setTitle(R.string.tool_search_hospital);
                    mToolbar.setVisibility(View.VISIBLE);
                    mToolbar.getMenu().clear();
                    pid = R.id.navigation_searchHospital;
                    selectedFragment = MapFragment.newInstance();
                    break;
                case R.id.navigation_profile:

                    item.setIcon(R.drawable.menu_profile2);

                    mToolbar.setTitle(R.string.tool_profile);
                    mToolbar.setVisibility(View.VISIBLE);
                    mToolbar.inflateMenu(R.menu.menu_main);
//                    mToolbar.showOverflowMenu();
                    pid = R.id.navigation_profile;
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, LogIn.class));
            finish();
            return;
        }

//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/opensans-regular.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );

        pid = R.id.navigation_firstAid;

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
//        toolbar.setLogo(R.drawable.settings);
//        toolbar.inflateMenu(R.menu.menu_main);

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        switch (pid) {
//            case R.id.navigation_profile:
//                getMenuInflater().inflate(R.menu.menu_main, menu);
//                break;
//
//            default:
//                menu.clear();
////                supportInvalidateOptionsMenu();
//        }
//
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miCompose:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.editProfileTmenu:
                Intent intent1 = new Intent(this, EditProfile.class);
                startActivity(intent1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void hideToolbar(String s) {
//        mToolbar.setVisibility(View.GONE);
        mToolbar.setTitle(s);
    }

}
