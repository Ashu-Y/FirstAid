package com.practice.android.firstaid.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

public class UserDetails extends AppCompatActivity implements OnConnectionFailedListener, IMethodCaller {

    private static int flag = 0;
    FirebaseAuth firebaseAuth, mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    GoogleApiClient mGoogleApiClient;
    DatabaseReference mDatabase1, mDatabase2;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    Calendar myCalendar;
    Button continueButton;
    EditText etName, etDob, etPhone, etCity;
    ImageView selectCal, addNewCity;
    Spinner etGender, etBloodGroup, etLanguage;
    Switch etInterestedInDonating;
    ListView cityLV;
    LinearLayout cityView;

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
    String[] genderArray = {"Select gender", "Male", "Female", "Others"};
    String[] bloodGroupArray = {"Select blood group", "O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"};
    String[] languageArray = {"English", "Hindi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        Languages = null;

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

        cityView = (LinearLayout) findViewById(R.id.city_view);
        cityView.setVisibility(View.GONE);

        cityRecycler = (RecyclerView) findViewById(R.id.city_recycler_profile);

//        cityLV = (ListView) findViewById(R.id.city_listView);

        cityList = new ArrayList<>();
//        cityAdapter = new ArrayAdapter(UserDetails.this, android.R.layout.simple_list_item_1, cityList);

        cityRecyclerAdapter = new CityRecyclerAdapter(cityList, "UserDetails", this);

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

        etInterestedInDonating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //         Toast.makeText(UserDetails.this, "working", Toast.LENGTH_SHORT).show();
                if (etInterestedInDonating.isChecked())
                    createAlertDialog(UserDetails.this);
                else
                    cityView.setVisibility(View.GONE);
            }
        });


        etCity.setFocusable(false);
        etCity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                            .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                            .build();

                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).setFilter(typeFilter)
                                    .build(UserDetails.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });

        if (!cityList.isEmpty()) {
            noCity.setVisibility(View.GONE);
            cityRecycler.setVisibility(View.VISIBLE);
        } else {
            noCity.setVisibility(View.VISIBLE);
            cityRecycler.setVisibility(View.GONE);
        }

//        addNewCity.setOnClickListener(new OnClickListener() {
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

//        ArrayAdapter adapterLanguage = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, languageArray);
//        adapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        etLanguage.setAdapter(adapterLanguage);

        continueButton = (Button) findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                getDetails();

                if (flag != -1) {
                    flag = 0;

                    writeNewPost(Name, Gender, DOB, BloodGroup, PhoneNumber, Languages, InterestedinDonating, FirstLogin, cityList);

                    Log.d("Values", Name + "\t" + DOB + "\t" + PhoneNumber + "\t" + Gender + "\t" + BloodGroup + "\t" + Languages + "\t" + InterestedinDonating);

                    UserDetails.this.finish();
                    startActivity(new Intent(UserDetails.this, MainActivity.class));
                }


            }
        });

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

    // handle creating dialog and its functioning
    public void createAlertDialog(Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(R.string.dialog_message).setTitle(R.string.title);

        builder.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                etInterestedInDonating.setChecked(true);
                cityView.setVisibility(View.VISIBLE);

            }

        });

        builder.setNegativeButton(R.string.reject, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                etInterestedInDonating.setChecked(false);

            }
        });

        AlertDialog dialog = builder.create();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                etInterestedInDonating.setChecked(false);
            }
        });


        dialog.show();


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
                Toast.makeText(UserDetails.this, "City already added", Toast.LENGTH_SHORT).show();
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
