package com.practice.android.firstaid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.android.firstaid.Adapters.CityRecyclerAdapter;
import com.practice.android.firstaid.Models.BloodRequestDetail;
import com.practice.android.firstaid.Models.UserInfo;
import com.practice.android.firstaid.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BloodRequestForm extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    GoogleApiClient mGoogleApiClient;
    DatabaseReference mDatabase1, mDatabase2;
    Calendar myCalender;
    String UserID;

    private static int flag = 0;

    Spinner bgSpinner;
    Button uploadBtn;
    EditText etName, etPhone, etCity, etComments;
    String name, phone, city, comments, bg, date, time;

    ArrayList<String> bloodGroupArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_request_form);

        myCalender = Calendar.getInstance();
        bloodGroupArray.add("Select blood group");
        bloodGroupArray.add("O+");
        bloodGroupArray.add("O-");
        bloodGroupArray.add("A+");
        bloodGroupArray.add("A-");
        bloodGroupArray.add("B+");
        bloodGroupArray.add("B-");
        bloodGroupArray.add("AB+");
        bloodGroupArray.add("AB-");

        uploadBtn = (Button) findViewById(R.id.upload_btn);
        etName = (EditText) findViewById(R.id.etName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etCity = (EditText) findViewById(R.id.etCity);
        etComments = (EditText) findViewById(R.id.etComments);

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(10);
        etPhone.setFilters(filterArray);

        bgSpinner = (Spinner) findViewById(R.id.bgSpinner);
        ArrayAdapter adapterBloodGroup = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bloodGroupArray);
        adapterBloodGroup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bgSpinner.setAdapter(adapterBloodGroup);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        UserID = user.getUid();

        final String currentEmail = user.getEmail();
        Log.d("UserDetails", currentEmail);
        mDatabase1 = FirebaseDatabase.getInstance().getReference("BloodRequest");
        mDatabase2 = FirebaseDatabase.getInstance().getReference("userinfo");

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDetails();

                if (flag != -1) {
                    flag = 0;
                    BloodRequestForm.this.finish();
                }
            }
        });

        setDetails();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_close:
                Intent intent = new Intent(BloodRequestForm.this, MainActivity.class);
                intent.putExtra("SelectedId", "Blood Network");
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getDetails() {

        name = etName.getText().toString();
        phone = etPhone.getText().toString();
        city = etCity.getText().toString();
        comments = etComments.getText().toString();


        if(bgSpinner.getSelectedItem().toString().equals("Select blood group")){
            Toast.makeText(BloodRequestForm.this, "Blood group can not be left blank", Toast.LENGTH_SHORT).show();
            flag = -1;
            return;
        }else {
            bg = bgSpinner.getSelectedItem().toString();
        }

//        bg = bgSpinner.getSelectedItem().toString();

        if (TextUtils.isEmpty(name)) {
            etName.setError("Cannot be empty.");
            flag = -1;
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            etPhone.setError("Cannot be empty.");
            flag = -1;
            return;
        }

        if (TextUtils.isEmpty(city)) {
            flag = -1;
            etCity.setError("Cannot be empty.");
            return;
        }

        flag = 0;

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, getResources().getConfiguration().locale);

        date = sdf.format(myCalender.getTime());

        myFormat = "hh:mm";
        sdf = new SimpleDateFormat(myFormat, getResources().getConfiguration().locale);
//        date = "" + myCalender.get(Calendar.DATE) + "/" + (myCalender.get(Calendar.MONTH) + 1) + "/" + myCalender.get(Calendar.YEAR);
//        time = "" + myCalender.get(Calendar.HOUR) + ":" + myCalender.get(Calendar.MINUTE);

        time = sdf.format(myCalender.getTime());

        if ((int) myCalender.get(Calendar.AM_PM) == 0) {
            time = time + (" am");
        } else {
            time = time + (" pm");
        }

        writeNewPost(name, phone, city, comments, bg, date, time);
    }

    public void writeNewPost(String name, String phone, String city, String comments, String bg, String date, String time) {

        String Key = mDatabase1.child(UserID).push().getKey();
        BloodRequestDetail userinfo = new BloodRequestDetail(name, phone, city, comments, bg, date, time, "Pending", UserID, Key, null);
        Map<String, Object> postValues = userinfo.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + Key, postValues);

        mDatabase1.updateChildren(childUpdates);


    }

    public void setDetails() {
        mDatabase2.child(UserID).addValueEventListener(new ValueEventListener() {
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

//        cityList.clear();
//
//        cityList = (ArrayList<String>) userInfo.getCities();
//        cityRecyclerAdapter = new CityRecyclerAdapter(cityList);
//        cityRecyclerAdapter.notifyDataSetChanged();
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        cityRecycler.setLayoutManager(linearLayoutManager);
//        cityRecycler.setAdapter(cityRecyclerAdapter);

//        try {
//
//
////            if(cityList.isEmpty()){
////                noCity.setVisibility(View.VISIBLE);
////                cityRecycler.setVisibility(View.GONE);
////            } else {
////                noCity.setVisibility(View.GONE);
////                cityRecycler.setVisibility(View.VISIBLE);
////            }
////
////            noCity.setVisibility(View.GONE);
////            cityRecycler.setVisibility(View.VISIBLE);
//
//            if (cityList.isEmpty()) {
//                noCity.setVisibility(View.VISIBLE);
//                cityRecycler.setVisibility(View.GONE);
//            } else {
//                noCity.setVisibility(View.GONE);
//                cityRecycler.setVisibility(View.VISIBLE);
//            }
//        } catch (NullPointerException e) {
//            Log.e("BloodRequestForm: ", e.getMessage());
//        }

        try {
            etName.setText(userInfo.getName());
        } catch (NullPointerException e) {
            Log.e("BloodRequestForm: ", e.getMessage());
        }

        try {
            if (userInfo.getBloodGroup().isEmpty()) {
                bgSpinner.setSelection(0);
            } else {
                bgSpinner.setSelection(bloodGroupArray.indexOf(userInfo.getBloodGroup()));
            }
        } catch (NullPointerException e) {
            Log.e("BloodRequestForm: ", e.getMessage());
        }

//        try {
//            if (userInfo.getGender().isEmpty()) {
//                etGender.setSelection(0);
//            } else {
//                etGender.setSelection(genderArray.indexOf(userInfo.getGender()));
//            }
//
//        } catch (NullPointerException e) {
//            Log.e("BloodRequestForm: ", e.getMessage());
//        }


//        try {
//            etDob.setText(userInfo.getDOB());
//        } catch (NullPointerException e) {
//            Log.e("BloodRequestForm: ", e.getMessage());
//        }


        try {
//            bgTv.setText(userInfo.getBloodGroup());
            etPhone.setText(userInfo.getPhoneNumber());
//            langTv.setText(userInfo.getLanguages());

        } catch (NullPointerException e) {
            Log.e("BloodRequestForm: ", e.getMessage());
        }

//        try {
//            if (userInfo.getInterestedinDonating().equals("true")) {
//                etInterestedInDonating.setChecked(true);
//            }
//        } catch (NullPointerException e) {
//            Log.e("BloodRequestForm: ", e.getMessage());
//        }


    }
}
