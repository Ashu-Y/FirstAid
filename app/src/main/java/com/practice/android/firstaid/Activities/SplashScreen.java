package com.practice.android.firstaid.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


public class SplashScreen extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    String UserID;
    SharedPreferences sharedPref;
    Context context;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        context = getApplicationContext();

        sharedPref = context.getSharedPreferences("IsLoggedIn", Context.MODE_PRIVATE);

        boolean isLoggedIn = sharedPref.getBoolean("IsLoggedIn", false);

        if (isLoggedIn) {
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
            finish();

        } else {
            startActivity(new Intent(SplashScreen.this, LogIn.class));
            finish();
        }


////        setContentView(R.layout.activity_log_in);
//
////        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
////                .setDefaultFontPath("fonts/opensans-regular.ttf")
////                .setFontAttrId(R.attr.fontPath)
////                .build()
////        );
//
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (user != null) {
//
//            UserID = user.getUid();
//            final String curremail = user.getEmail();
//            Log.d("FirstSignInSupport", curremail);
//        }
//
//        // [START configure_signin]
//        // Configure sign-in to request the user's ID, email address, and basic
//        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//        // [END configure_signin]
//
//
//        // [START build_client]
//        // Build a GoogleApiClient with access to the Google Sign-In API and the
//        // options specified by gso.
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//        // [END build_client]
//
//
//
////        findViewById(R.id.signInButton).setOnClickListener(this);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
//        if (opr.isDone()) {
//            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
//            // and the GoogleSignInResult will be available instantly.
//            Log.d(TAG, "Got cached sign-in");
//            GoogleSignInResult result = opr.get();
//            handleSignInResult(result);
//        } else {
//            // If the user has not previously signed in on this device or the sign-in has expired,
//            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
//            // single sign-on will occur in this branch.
//            showProgressDialog();
//            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
//                @Override
//                public void onResult(GoogleSignInResult googleSignInResult) {
//                    hideProgressDialog();
//                    handleSignInResult(googleSignInResult);
//                }
//            });
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        hideProgressDialog();
//    }
//
//    // [START onActivityResult]
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
//        }
//    }
//    // [END onActivityResult]
//
//    // [START handleSignInResult]
//    private void handleSignInResult(GoogleSignInResult result) {
//        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
//        if (result.isSuccess()) {
//            // Signed in successfully, show authenticated UI.
//            GoogleSignInAccount acct = result.getSignInAccount();
//            firebaseAuthWithGoogle(acct);
//
//            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
//            mDatabase = FirebaseDatabase.getInstance().getReference("userinfo");
//
//        } else {
//            // Signed out, show unauthenticated UI.
//            next(null);
//        }
//    }
//    // [END handleSignInResult]
//
//    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
//
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//
//                            firebaseUser = mAuth.getCurrentUser();
//                            next(firebaseUser);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(SplashScreen.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            next(null);
//                        }
//                    }
//                });
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (mProgressDialog != null) {
//            mProgressDialog.dismiss();
//        }
//    }
//
//    private void showProgressDialog() {
//        if (mProgressDialog == null) {
//            mProgressDialog = new ProgressDialog(this);
//            mProgressDialog.setMessage(getString(R.string.loading));
//            mProgressDialog.setIndeterminate(true);
//        }
//
//        mProgressDialog.show();
//    }
//
//    private void hideProgressDialog() {
//        if (mProgressDialog != null && mProgressDialog.isShowing()) {
//            mProgressDialog.hide();
//        }
//    }
//
//    // [START signIn]
//    private void signIn() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//    // [END signIn]
//
////    @Override
////    public void onClick(View v) {
////        switch (v.getId()) {
////            case R.id.signInButton:
////                signIn();
////                break;
////        }
////    }
//
//    private void next(FirebaseUser user) {
//        hideProgressDialog();
//
//        if (user != null) {
//
//            FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
//
//            if (user1 != null) {
//
//                UserID = user.getUid();
//                final String curremail = user.getEmail();
//                Log.d("FirstSignInSupport", curremail);
//            }
//
//            first();
//
//
////            if (check != null && check.getFirstLogin().equals("true")) {
////                startActivity(new Intent(SplashScreen.this, MainActivity.class));
////                this.finish();
////            } else {
////                startActivity(new Intent(this, UserDetails.class));
////                this.finish();
////            }
//        } else {
//
//            startActivity(new Intent(this, LogIn.class));
//            finish();
//            Log.w(TAG, "No Authenticated User Found!");
//        }
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
//        // be available.
//        Log.d(TAG, "onConnectionFailed:" + connectionResult);
//    }
//
//
//    private void first() {
//
//        mDatabase.child(UserID).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);
//
//                if (userInfo != null) {
//
//                    if (userInfo.getFirstLogin().equals("true")) {
//                        startActivity(new Intent(SplashScreen.this, MainActivity.class));
//                        SplashScreen.this.finish();
//                    } else {
//                        startActivity(new Intent(SplashScreen.this, UserDetails.class));
//                        SplashScreen.this.finish();
//                    }
//
//                    Log.e("first method", userInfo.getFirstLogin());
//                } else {
//                    startActivity(new Intent(SplashScreen.this, UserDetails.class));
//                    SplashScreen.this.finish();
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }


    @Override
    public void onBackPressed() {

        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

    }

}