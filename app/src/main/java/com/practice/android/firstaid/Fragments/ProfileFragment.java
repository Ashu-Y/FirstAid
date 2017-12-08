package com.practice.android.firstaid.Fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.practice.android.firstaid.Adapters.CityRecyclerAdapter;
import com.practice.android.firstaid.Models.UserInfo;
import com.practice.android.firstaid.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private static final int PICK_FROM_GALLERY = 1;
    String UserID;
    //    private GoogleApiClient mGoogleApiClient;
    ArrayList<String> cityList;
    CityRecyclerAdapter cityRecyclerAdapter;
    RecyclerView cityRecycler;
    TextView nameTv, fa_btnTv, genderTv, dobTv, bgTv, phoneTv, langTv, noCity;
    TextView donateSwitch;
    CircleImageView profile_pic;
    LinearLayout profileCityView;
    StorageReference storageRef;
    Uri filePath;
    Uri downloadUrl;
    private FirebaseAuth mAuth;
    //    private FirebaseAuth.AuthStateListener mAuthListener;
//    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;
    private String TAG = "ProfileFragment";

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        nameTv = view.findViewById(R.id.nameTv);
        profile_pic = view.findViewById(R.id.profile_pic);
//        fa_btnTv = view.findViewById(R.id.fa_btnTv);
        genderTv = view.findViewById(R.id.genderTv);
        dobTv = view.findViewById(R.id.dobTv);
        bgTv = view.findViewById(R.id.bgTv);
        phoneTv = view.findViewById(R.id.phoneTv);
//        langTv = view.findViewById(R.id.langTv);
        cityRecycler = view.findViewById(R.id.city_recycler_profile);
        noCity = view.findViewById(R.id.noCity);
        profileCityView = view.findViewById(R.id.profile_city_view);
        donateSwitch = view.findViewById(R.id.donateSwitch);

        cityList = new ArrayList<>();
        cityRecyclerAdapter = new CityRecyclerAdapter(cityList, "ProfileFragment", getContext());
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            UserID = user.getUid();
            final String curremail = user.getEmail();
            Log.d("ProfileFragment", curremail);
        }

        mDatabase = FirebaseDatabase.getInstance().getReference("userinfo");
        mDatabase.keepSynced(true);
        storageRef = FirebaseStorage.getInstance().getReference();

        getDetails();

        setHasOptionsMenu(true);

        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                    } else {

                        editProfilePic();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

//        Toolbar toolbar = view.findViewById(R.id.tool);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        toolbar.setLogo(R.drawable.settings);
//        toolbar.inflateMenu(R.menu.menu_main);

        return view;
    }


//    @Override
//        public void OnCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//            inflater.inflate(R.menu.menu_main, menu);
//            super.onCreateOptionsMenu(menu,inflater);
//
//        }

    public void getDetails() {
        mDatabase.child(UserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo userInfo = dataSnapshot.getValue(UserInfo.class);

                ch(userInfo);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void ch(UserInfo userInfo) {

        if (cityList != null) {
            cityList.clear();
        } else {
            cityList = new ArrayList<>();
        }

        cityList = (ArrayList<String>) userInfo.getCities();
        cityRecyclerAdapter = new CityRecyclerAdapter(cityList, "ProfileFragment", getContext());
        cityRecyclerAdapter.notifyDataSetChanged();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        cityRecycler.setLayoutManager(linearLayoutManager);
        cityRecycler.setAdapter(cityRecyclerAdapter);

        try {


//            if(cityList.isEmpty()){
//                noCity.setVisibility(View.VISIBLE);
//                cityRecycler.setVisibility(View.GONE);
//            } else {
//                noCity.setVisibility(View.GONE);
//                cityRecycler.setVisibility(View.VISIBLE);
//            }
//
//            noCity.setVisibility(View.GONE);
//            cityRecycler.setVisibility(View.VISIBLE);

            if (cityList.isEmpty()) {
                noCity.setVisibility(View.VISIBLE);
                cityRecycler.setVisibility(View.GONE);
            } else {
                noCity.setVisibility(View.GONE);
                cityRecycler.setVisibility(View.VISIBLE);
            }
        } catch (NullPointerException e) {
            Log.e("ProfileFragment: ", e.getMessage());
        }


        try {
            nameTv.setText(userInfo.getName());

        } catch (NullPointerException e) {
            Log.e("ProfileFragment: ", e.getMessage());
        }

//        try {
//            fa_btnTv.setText(userInfo.getBloodGroup());
//        } catch (NullPointerException e) {
//            Log.e("ProfileFragment: ", e.getMessage());
//        }

        try {
            genderTv.setText(userInfo.getGender());
        } catch (NullPointerException e) {
            Log.e("ProfileFragment: ", e.getMessage());
        }

        try {
            dobTv.setText(userInfo.getDOB());
        } catch (NullPointerException e) {
            Log.e("ProfileFragment: ", e.getMessage());
        }

        try {
            bgTv.setText(userInfo.getBloodGroup());
        } catch (NullPointerException e) {
            Log.e("ProfileFragment: ", e.getMessage());
        }

        try {
            phoneTv.setText(userInfo.getPhoneNumber());
        } catch (NullPointerException e) {
            Log.e("ProfileFragment: ", e.getMessage());
        }


//            langTv.setText(userInfo.getLanguages());

        if (userInfo.getInterestedinDonating().equals("true")) {
            donateSwitch.setText("Yes");
            profileCityView.setVisibility(View.VISIBLE);
        } else {
            donateSwitch.setText("No");
            profileCityView.setVisibility(View.GONE);

        }


        try {

            if (userInfo.getProfileUrl() != null && !userInfo.getProfileUrl().isEmpty()) {
                Glide.with(getActivity()).load(userInfo.getProfileUrl()).fitCenter().skipMemoryCache(false).into(profile_pic);
            } else {
                profile_pic.setImageResource(R.drawable.profile);
            }

        } catch (NullPointerException e) {

            profile_pic.setImageResource(R.drawable.profile);

            Log.e("ProfileFragment: ", e.getMessage());
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void editProfilePic() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {

            filePath = data.getData();
            Log.i(TAG, filePath.toString());

            profile_pic.setImageURI(data.getData());
            uploadFile(requestCode, filePath);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    editProfilePic();
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                    Toast.makeText(getActivity(), "Please allow the app to access media to continue", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void uploadFile(int requestCode, Uri filePath) {

        switch (requestCode) {

            case PICK_FROM_GALLERY: {
                if (filePath != null) {

                    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setTitle("Uploading");
                    progressDialog.show();

                    StorageReference uploadRef = storageRef.child("ProfilePicture/" + UserID + "/profileImg");
                    StorageMetadata metadata = new StorageMetadata.Builder()
                            .setContentType("image/jpg")
                            .build();

                    UploadTask uploadTask = uploadRef.putFile(filePath, metadata);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Profile picture uploaded successfully", Toast.LENGTH_SHORT).show();

                            downloadUrl = taskSnapshot.getDownloadUrl();

                            Glide.with(getActivity()).load(downloadUrl).fitCenter().into(profile_pic);

                            mDatabase.child(UserID).child("ProfileUrl").setValue(downloadUrl.toString());
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    progressDialog.dismiss();

                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                                    progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                                }
                            });

                }//if there is not any file
                else {
                    //you can display an error toast
                    Toast.makeText(getActivity(), "No file specified", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }


}


