package com.practice.android.firstaid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.practice.android.firstaid.Models.BloodRequestDetail;
import com.practice.android.firstaid.Models.UserInfo;
import com.practice.android.firstaid.R;

import java.text.SimpleDateFormat;
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

    Spinner bgSpinner;
    Button uploadBtn;
    EditText etName, etPhone, etCity, etComments;
    String name, phone, city, comments, bg, date, time;

    String[] bloodGroupArray = {"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_request_form);

        myCalender = Calendar.getInstance();

        uploadBtn = (Button) findViewById(R.id.upload_btn);
        etName = (EditText) findViewById(R.id.etName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etCity = (EditText) findViewById(R.id.etCity);
        etComments = (EditText) findViewById(R.id.etComments);

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

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDetails();
               BloodRequestForm.this.finish();
            }
        });

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

    private void getDetails(){

        name = etName.getText().toString();
        phone = etPhone.getText().toString();
        city = etCity.getText().toString();
        comments = etComments.getText().toString();
        bg = bgSpinner.getSelectedItem().toString();

//        String myFormat = "dd/MM/yyyy"; //In which you need put here
//
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, getResources().getConfiguration().locale);

        date = "" + myCalender.get(Calendar.DATE) + "/" + (myCalender.get(Calendar.MONTH) + 1) + "/" + myCalender.get(Calendar.YEAR);
        time = "" + myCalender.get(Calendar.HOUR) + ":" + myCalender.get(Calendar.MINUTE);
        if ((int)myCalender.get(Calendar.AM_PM) == 0){
            time = time + (" am");
        }else {
            time = time + (" pm");
        }

        writeNewPost(name, phone, city, comments, bg, date, time);
    }

    public void writeNewPost(String name, String phone, String city, String comments, String bg, String date, String time) {

        String Key = mDatabase1.child(UserID).push().getKey();
        BloodRequestDetail userinfo = new BloodRequestDetail(name, phone, city, comments, bg, date, time, "Pending");
        Map<String, Object> postValues = userinfo.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + UserID, postValues);

        mDatabase1.updateChildren(childUpdates);


    }
}
