package com.practice.android.firstaid.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.practice.android.firstaid.Adapters.CityRecyclerAdapter;
import com.practice.android.firstaid.Interfaces.IMethodCaller;
import com.practice.android.firstaid.Models.UserInfo;
import com.practice.android.firstaid.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EditProfile extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, IMethodCaller {

    private static int flag = 0;
    FirebaseAuth firebaseAuth, mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    GoogleApiClient mGoogleApiClient;
    DatabaseReference mDatabase1, mDatabase2;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    Calendar myCalendar;
    Button saveButton;
    EditText etName, etDob, etPhone, etCity;
    ImageView selectCal, addNewCity;
    Spinner etGender, etBloodGroup, etLanguage;
    Switch etInterestedInDonating;
    ListView cityLV;

    TextView noCity;
    ArrayList<String> cityList;
    CityRecyclerAdapter cityRecyclerAdapter;
    RecyclerView cityRecycler;

    NestedScrollView scrollView;
    String UserID, Name, Gender, DOB, BloodGroup, PhoneNumber, Languages, InterestedinDonating, FirstLogin;

    String[] cities;

    //    ArrayList<String> cityList;
    ArrayAdapter cityAdapter;

    int i = 0;
    ArrayList<String> genderArray = new ArrayList<>();
    ArrayList<String> bloodGroupArray = new ArrayList<>();
    String[] languageArray = {"English", "Hindi"};


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/opensans-regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        Languages = null;

//        {"Select blood group", "O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};
        bloodGroupArray.add("Select blood group");
        bloodGroupArray.add("O+");
        bloodGroupArray.add("O-");
        bloodGroupArray.add("A+");
        bloodGroupArray.add("A-");
        bloodGroupArray.add("B+");
        bloodGroupArray.add("B-");
        bloodGroupArray.add("AB+");
        bloodGroupArray.add("AB-");

//        {"Select gender", "Male", "Female", "Others"};

        genderArray.add("Select gender");
        genderArray.add("Male");
        genderArray.add("Female");
        genderArray.add("Others");

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

//        addNewCity = (ImageView) findViewById(R.id.add_newCity);

        noCity = (TextView) findViewById(R.id.noCity);

        cityRecycler = (RecyclerView) findViewById(R.id.city_recycler_profile);

//        cityLV = (ListView) findViewById(R.id.city_listView);

        cityList = new ArrayList<>();
//        cityAdapter = new ArrayAdapter(UserDetails.this, android.R.layout.simple_list_item_1, cityList);

        cityRecyclerAdapter = new CityRecyclerAdapter(cityList, "EditProfile", this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        cityRecycler.setLayoutManager(linearLayoutManager);

        scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        scrollView.setVerticalScrollBarEnabled(false);

        etName = (EditText) findViewById(R.id.name);
        etName.setText(user.getDisplayName());

        etDob = (EditText) findViewById(R.id.edit_date);
        etPhone = (EditText) findViewById(R.id.phone);
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(10);
        etPhone.setFilters(filterArray);

        etCity = (EditText) findViewById(R.id.edit_city);

        etGender = (Spinner) findViewById(R.id.gender);
        etBloodGroup = (Spinner) findViewById(R.id.blood_group);
//        etLanguage = (Spinner) findViewById(R.id.languages);

        etInterestedInDonating = (Switch) findViewById(R.id.interested);


        etCity.setFocusable(false);
        etCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                            .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                            .build();

                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).setFilter(typeFilter)
                                    .build(EditProfile.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });

//        addNewCity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!etCity.getText().toString().isEmpty()) {
//                    cityList.add(etCity.getText().toString());
//
//                    etCity.setText("");
//                }
//
//                cityRecyclerAdapter.notifyDataSetChanged();
////                cityAdapter.notifyDataSetChanged();
////                cityRecyclerAdapter = new CityRecyclerAdapter(cityList);
//                cityRecycler.setAdapter(cityRecyclerAdapter);
////                cityLV.setAdapter(cityAdapter);
//
//                if (!cityList.isEmpty()) {
//                    noCity.setVisibility(View.GONE);
//                    cityRecycler.setVisibility(View.VISIBLE);
//                } else {
//                    noCity.setVisibility(View.VISIBLE);
//                    cityRecycler.setVisibility(View.GONE);
//                }
//            }
//        });

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

        selectCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditProfile.this, date, myCalendar
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

//        ArrayAdapter adapterLanguage = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, languageArray);
//        adapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        etLanguage.setAdapter(adapterLanguage);

        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getDetails();

                if (flag != -1) {
                    flag = 0;

                    writeNewPost(Name, Gender, DOB, BloodGroup, PhoneNumber, Languages, InterestedinDonating, FirstLogin, cityList);

                    Log.d("Values", Name + "\t" + DOB + "\t" + PhoneNumber + "\t" + Gender + "\t" + BloodGroup + "\t" + Languages + "\t" + InterestedinDonating);

                    EditProfile.this.finish();
                    onBackPressed();
                }


            }
        });

        setDetails();
    }

    private void getDetails() {


        flag = 0;

        Name = etName.getText().toString();
        DOB = etDob.getText().toString();
        PhoneNumber = etPhone.getText().toString();

        FirstLogin = "true";
//        Gender = etGender.getSelectedItem().toString();

        if (etGender.getSelectedItem().toString().equals("Select gender")) {
            Gender = null;
        } else {
            Gender = etGender.getSelectedItem().toString();
        }

//        BloodGroup = etBloodGroup.getSelectedItem().toString();

        if (etBloodGroup.getSelectedItem().toString().equals("Select blood group")) {
            BloodGroup = null;
        } else {
            BloodGroup = etBloodGroup.getSelectedItem().toString();
        }

//        Languages = etLanguage.getSelectedItem().toString();

        if (TextUtils.isEmpty(Name)) {
            etName.setError("Cannot be empty.");
            flag = -1;
            return;
        }
//
//        if (TextUtils.isEmpty(DOB)) {
//            etDob.setError("Cannot be empty.");
//            flag = -1;
//            return;
//        }
//        if (TextUtils.isEmpty(PhoneNumber)) {
//            etPhone.setError("Cannot be empty.");
//            flag = -1;
//            return;
//        }

        if (etInterestedInDonating.isChecked()) {
            InterestedinDonating = "true";
        } else {
            InterestedinDonating = "false";
        }

//        writeNewPost(Name, Gender, DOB, BloodGroup, PhoneNumber, Languages, InterestedinDonating, FirstLogin, cityList);
//
//        Log.d("Values", Name + "\t" + DOB + "\t" + PhoneNumber + "\t" + Gender + "\t" + BloodGroup + "\t" + Languages + "\t" + InterestedinDonating);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i("UserDetails: ", "Place: " + place.getName());
                addCity(place.getName().toString());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i("UserDetails: ", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    public void setDetails() {
        mDatabase1.child(UserID).addValueEventListener(new ValueEventListener() {
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

        cityList.clear();

        cityList = (ArrayList<String>) userInfo.getCities();
        cityRecyclerAdapter = new CityRecyclerAdapter(cityList, "EditProfile", this);
        cityRecyclerAdapter.notifyDataSetChanged();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
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
            etName.setText(userInfo.getName());
        } catch (NullPointerException e) {
            Log.e("ProfileFragment: ", e.getMessage());
        }

        try {
            if (userInfo.getBloodGroup().isEmpty()) {
                etBloodGroup.setSelection(0);
            } else {

                etBloodGroup.setSelection(bloodGroupArray.indexOf(userInfo.getBloodGroup()));
            }
        } catch (NullPointerException e) {
            Log.e("ProfileFragment: ", e.getMessage());
        }

        try {
            if (userInfo.getGender().isEmpty()) {
                etGender.setSelection(0);
            } else {
                etGender.setSelection(genderArray.indexOf(userInfo.getGender()));
            }

        } catch (NullPointerException e) {
            Log.e("ProfileFragment: ", e.getMessage());
        }

        try {
            if (userInfo.getGender().isEmpty()) {
                etGender.setSelection(0);
            } else {
                etGender.setSelection(genderArray.indexOf(userInfo.getGender()));
            }
        } catch (NullPointerException e) {
            Log.e("ProfileFragment: ", e.getMessage());
        }

        try {
            etDob.setText(userInfo.getDOB());
        } catch (NullPointerException e) {
            Log.e("ProfileFragment: ", e.getMessage());
        }


        try {
//            bgTv.setText(userInfo.getBloodGroup());
            etPhone.setText(userInfo.getPhoneNumber());
//            langTv.setText(userInfo.getLanguages());

        } catch (NullPointerException e) {
            Log.e("ProfileFragment: ", e.getMessage());
        }

        try {
            if (userInfo.getInterestedinDonating().equals("true")) {
                etInterestedInDonating.setChecked(true);
            }
        } catch (NullPointerException e) {
            Log.e("ProfileFragment: ", e.getMessage());
        }


    }

    @Override
    public void checkNoCity() {
        if (!cityList.isEmpty()) {
            noCity.setVisibility(View.GONE);
            cityRecycler.setVisibility(View.VISIBLE);
        } else {
            noCity.setVisibility(View.VISIBLE);
            cityRecycler.setVisibility(View.GONE);
        }
    }

    public void addCity(String city) {

        etCity.setText(city);


        //add city to list
        if (!etCity.getText().toString().isEmpty()) {

            int k = 0;

            for (String i : cityList) {
                if ((etCity.getText().toString()).equals(i)) {
                    k = -1;
                    break;
                }
            }

            if (k == 0) {
                cityList.add(etCity.getText().toString());
                etCity.setText("");
            } else {
                Toast.makeText(EditProfile.this, "City already added", Toast.LENGTH_SHORT).show();
                etCity.setText("");
            }
        }

        cityRecyclerAdapter.notifyDataSetChanged();
//                cityAdapter.notifyDataSetChanged();
//                cityRecyclerAdapter = new CityRecyclerAdapter(cityList);
//                cityRecycler.setAdapter(cityRecyclerAdapter);
//                cityLV.setAdapter(cityAdapter);

        if (!cityList.isEmpty()) {
            noCity.setVisibility(View.GONE);
            cityRecycler.setVisibility(View.VISIBLE);
        } else {
            noCity.setVisibility(View.VISIBLE);
            cityRecycler.setVisibility(View.GONE);
        }


    }
}
