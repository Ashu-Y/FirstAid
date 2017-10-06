package com.practice.android.firstaid.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.android.firstaid.Models.UserInfo;
import com.practice.android.firstaid.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDetails extends AppCompatActivity implements OnConnectionFailedListener {

    FirebaseAuth firebaseAuth, mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    GoogleApiClient mGoogleApiClient;
    DatabaseReference mDatabase1, mDatabase2;

    Calendar myCalendar;
    Button continueButton;
    EditText etName, etDob, etPhone, etCity;
    ImageView selectCal, addNewCity;
    Spinner etGender, etBloodGroup, etLanguage;
    Switch etInterestedInDonating;
    ListView cityLV;

    String UserID, Name, Gender, DOB, BloodGroup, PhoneNumber, Languages, InterestedinDonating, FirstLogin;
    String[] cities;

    ArrayList<String> cityList;
    ArrayAdapter cityAdapter;

    int i = 0;
    String[] genderArray = {"Male", "Female", "Others"};
    String[] bloodGroupArray = {"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};
    String[] languageArray = {"English", "Hindi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        UserID = user.getUid();

        final String currentEmail = user.getEmail();
        Log.d("UserDetails", currentEmail);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mDatabase1 = FirebaseDatabase.getInstance().getReference("userinfo");

        myCalendar = Calendar.getInstance();
        selectCal = (ImageView) findViewById(R.id.select_cal);

        addNewCity = (ImageView) findViewById(R.id.add_newCity);

        cityLV = (ListView) findViewById(R.id.city_listView);

        cityList = new ArrayList<>();
        cityAdapter = new ArrayAdapter(UserDetails.this, android.R.layout.simple_list_item_1, cityList);

        etName = (EditText) findViewById(R.id.name);
        etDob = (EditText) findViewById(R.id.edit_date);
        etPhone = (EditText) findViewById(R.id.phone);
        etCity = (EditText) findViewById(R.id.edit_city);

        etGender = (Spinner) findViewById(R.id.gender);
        etBloodGroup = (Spinner) findViewById(R.id.blood_group);
        etLanguage = (Spinner) findViewById(R.id.languages);

        etInterestedInDonating = (Switch) findViewById(R.id.interested);

        addNewCity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                cityList.add(etCity.getText().toString());

                etCity.setText("");

                cityAdapter.notifyDataSetChanged();
                cityLV.setAdapter(cityAdapter);
                cityLV.setVisibility(View.VISIBLE);
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        selectCal.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(UserDetails.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        ArrayAdapter adapterGender = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, genderArray);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etGender.setAdapter(adapterGender);

        ArrayAdapter adapterBloodGroup = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bloodGroupArray);
        adapterBloodGroup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etBloodGroup.setAdapter(adapterBloodGroup);

        ArrayAdapter adapterLanguage = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, languageArray);
        adapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etLanguage.setAdapter(adapterLanguage);

        continueButton = (Button) findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                getDetails();

                startActivity(new Intent(UserDetails.this, MainActivity.class));
            }
        });

    }

    private void getDetails() {
        Name = etName.getText().toString();
        DOB = etDob.getText().toString();
        PhoneNumber = etPhone.getText().toString();

        FirstLogin = "true";
        Gender = etGender.getSelectedItem().toString();
        BloodGroup = etBloodGroup.getSelectedItem().toString();
        Languages = etLanguage.getSelectedItem().toString();

        if (etInterestedInDonating.isChecked()) {
            InterestedinDonating = "true";
        } else {
            InterestedinDonating = "false";
        }

        writeNewPost(Name, Gender, DOB, BloodGroup, PhoneNumber, Languages, InterestedinDonating, FirstLogin, cityList);

        Log.d("Values", Name + "\t" + DOB + "\t" + PhoneNumber + "\t" + Gender + "\t" + BloodGroup + "\t" + Languages + "\t" + InterestedinDonating);
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, getResources().getConfiguration().locale);

        etDob.setText(sdf.format(myCalendar.getTime()));
    }

    public void writeNewPost(String Name, String Gender, String DOB, String BloodGroup, String PhoneNumber, String Languages, String InterestedinDonating, String FirstLogin, List Cities) {

        String Key = mDatabase1.child(UserID).push().getKey();
        UserInfo userinfo = new UserInfo(Name, Gender, DOB, BloodGroup, PhoneNumber, Languages, InterestedinDonating, FirstLogin, Cities);
        Map<String, Object> postValues = userinfo.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + UserID, postValues);

        mDatabase1.updateChildren(childUpdates);


    }

    //    @Override
//    public void onBackPressed() {
//        Intent startMain = new Intent(Intent.ACTION_MAIN);
//        startMain.addCategory(Intent.CATEGORY_HOME);
//        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(startMain);
//    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
