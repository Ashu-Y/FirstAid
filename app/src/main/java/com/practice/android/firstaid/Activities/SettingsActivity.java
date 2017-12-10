package com.practice.android.firstaid.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.practice.android.firstaid.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class SettingsActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    static boolean logout = false;
    GoogleApiClient mGoogleApiClient;
    TextView feedback;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    Context context;
    private Button btnLogout;

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/opensans-regular.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );

        context = getApplicationContext();

        Toolbar toolbar = findViewById(R.id.setting);

        feedback = findViewById(R.id.feedback);

        btnLogout = findViewById(R.id.btn_logout);

        toolbar.setNavigationIcon(R.drawable.arrow1);
        setSupportActionBar(toolbar);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

//                Toast.makeText(getApplicationContext(),"your icon was clicked",Toast.LENGTH_SHORT).show();
            }
        });


        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] email = {"care@firstcare101.com"};

                composeEmail(email);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Async as = new Async();
//
//                boolean sa;

//
                try {
                    logout = as.execute().get();
                } catch (InterruptedException e) {
                    Log.e("SettingsActivity", e.getMessage());
                } catch (ExecutionException e) {
                    Log.e("SettingsActivity", e.getMessage());
                }

//
//                while(as.getStatus() != AsyncTask.Status.FINISHED)
//                {
//                    Toast.makeText(getApplicationContext(),"Running",Toast.LENGTH_SHORT).show();
//                }
//                sa = as.getBool();

                //Toast.makeText(SettingsActivity.this, "" + logout, Toast.LENGTH_LONG).show();

                if (logout) {
                    signOut();
                } else {
                    Toast.makeText(SettingsActivity.this, "You are offline", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    //Sign Out for Google and Firebase
    private void signOut() {
        FirebaseAuth.getInstance().signOut();


        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        startActivity(new Intent(SettingsActivity.this, LogIn.class));


                        sharedPref = context.getSharedPreferences("IsLoggedIn", Context.MODE_PRIVATE);

                        editor = sharedPref.edit();
                        editor.putBoolean("IsLoggedIn", false);
                        editor.apply();

                    }
                });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Failed", "onConnectionFailed:" + connectionResult);


    }

    public void composeEmail(String[] addresses) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    public class Async extends AsyncTask<Void, Void, Boolean>

    {

        @Override
        protected Boolean doInBackground(Void... parms) {

            ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            boolean connected = networkInfo != null && networkInfo.isConnectedOrConnecting();
            boolean bool = false;
            if (connected) {
                try {
                    HttpURLConnection urlc = (HttpURLConnection) (new URL("http://google.com").openConnection());
                    urlc.setRequestProperty("User-Agent", "Test");
                    urlc.setRequestProperty("Connection", "close");
                    urlc.setConnectTimeout(1500);
                    urlc.connect();
                    bool = urlc.getResponseCode() == HttpURLConnection.HTTP_OK;
                } catch (IOException e) {
                    Log.e("SettingsActivity", "Error checking internet connection", e);
                }
            } else {
                Log.d("SettingsActivity", "No network available!");
            }

//            Toast.makeText(SettingsActivity.this, " " +bool , Toast.LENGTH_LONG).show();

//            boolea = bool;

            logout = bool;

            return bool;
        }


//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
//            setBool(aBoolean);
//
//        }

//        public void setBool(boolean a)
//        {
//            boolea = a;
//        }
//
//        public boolean getBool()
//        {
//            return boolea;
//        }

    }

}
