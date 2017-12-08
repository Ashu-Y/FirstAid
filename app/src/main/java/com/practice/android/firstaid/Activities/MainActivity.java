package com.practice.android.firstaid.Activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.practice.android.firstaid.Fragments.BloodNetworkFragment;
import com.practice.android.firstaid.Fragments.FirstAidFragment;
import com.practice.android.firstaid.Fragments.MapFragment;
import com.practice.android.firstaid.Fragments.ProfileFragment;
import com.practice.android.firstaid.Helper.BottomNavigationViewHelper;
import com.practice.android.firstaid.Helper.Config;
import com.practice.android.firstaid.Interfaces.HideFirstAidToolbar;
import com.practice.android.firstaid.R;
import com.practice.android.firstaid.utility.NotificationUtils;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements HideFirstAidToolbar {


    private static final String TAG = MainActivity.class.getSimpleName();
    public static int i = 0;
    public static String FTAG = "BLANK";
    private static int pid;
    NotificationCompat.Builder mBuilder;
    // Sets an ID for the notification
    int mNotificationId = 001;
    SharedPreferences sharedPref;
    Context context;
    boolean doubleBackToExitPressedOnce = false;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private Toolbar mToolbar;
    private FirstAidFragment mFirstAidFragment;
    private FragmentManager mFragmentManager;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
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

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_main);


        FTAG = "BLANK";
        context = getApplicationContext();

        sharedPref = context.getSharedPreferences("IsLoggedIn", Context.MODE_PRIVATE);

        boolean isLoggedIn = sharedPref.getBoolean("IsLoggedIn", false);

        if (!isLoggedIn) {
            startActivity(new Intent(MainActivity.this, LogIn.class));
            finish();
        }

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

        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
//        toolbar.setLogo(R.drawable.settings);
//        toolbar.inflateMenu(R.menu.menu_main);

        mFirstAidFragment = new FirstAidFragment();
        mFragmentManager = getSupportFragmentManager();
        BottomNavigationView navigation = findViewById(R.id.navigation);
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


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

//TODO change the following code
                    mBuilder =
                            new NotificationCompat.Builder(MainActivity.this)
                                    .setSmallIcon(R.drawable.logo1)
                                    .setContentTitle("New Blood Request")
                                    .setContentText(message);

                    Intent resultIntent = new Intent(MainActivity.this, MainActivity.class);

                    PendingIntent resultPendingIntent =
                            PendingIntent.getActivity(
                                    MainActivity.this,
                                    0,
                                    resultIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );

                    mBuilder.setContentIntent(resultPendingIntent);

                    NotificationManager mNotifyMgr =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    // Builds the notification and issues it.
                    mNotifyMgr.notify(mNotificationId, mBuilder.build());

                    //end TODO
                }
            }
        };


    }

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

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onBackPressed() {

        if (FTAG.equals("FirstAidFragment")) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content, FirstAidFragment.newInstance(), null);
            ft.commit();

            mToolbar.setTitle(R.string.tool_first_aid);

            FTAG = "BLANK";

        } else if (FTAG.equals("BLANK")) {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK){
//
//            if (FTAG.equals("FirstAidFragment"))
//            {
//                FragmentManager fm = getSupportFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.content,FirstAidFragment.newInstance(),null);
//                ft.commit();
//
//                FTAG = "BLANK";
//
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
