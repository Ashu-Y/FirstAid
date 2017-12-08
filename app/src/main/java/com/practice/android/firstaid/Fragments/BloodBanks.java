package com.practice.android.firstaid.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.practice.android.firstaid.Adapters.HospitalListAdapter;
import com.practice.android.firstaid.Helper.HttpHandler;
import com.practice.android.firstaid.Models.HospitalListModel;
import com.practice.android.firstaid.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.google.android.gms.internal.zzagr.runOnUiThread;


/**
 * A simple {@link Fragment} subclass.
 */
public class BloodBanks extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


//    SupportMapFragment mapFrag;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static String url;
    static int flag = 0;
    static int y = 0;
    private static String url1 = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=";
    //    private static String url2 = "&radius=5000&keyword=blood&key=AIzaSyAd6xyFMj2-K3FOQj0th8AiqzhMPGD9oHw";
    private static String url2 = "&rankby=distance&keyword=blooddonation&key=AIzaSyAd6xyFMj2-K3FOQj0th8AiqzhMPGD9oHw";
    protected ProgressDialog pDialog;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    ArrayList<HospitalListModel> hospitalList;
    HospitalListAdapter adapter;
    Context context;
    RecyclerView mRecyclerView;
    private GoogleMap mGoogleMap;
    //    private MapView mapView;
    private boolean mapsSupported = true;

    public BloodBanks() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MapsInitializer.initialize(getActivity());

        Log.e("BloodBanks", "onActivityCreated");

//        if (mapView != null) {
//            mapView.onCreate(savedInstanceState);
//        }
        initializeMap();
    }

    private void initializeMap() {
        Log.e("BloodBanks", "initializeMap");

//        if (mGoogleMap == null && mapsSupported) {
//            mapView = (MapView) getActivity().findViewById(R.id.map);
//            mapView.getMapAsync(this);
//
//            //setup markers etc...
//        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        Log.e("BloodBanks", "onMapReady");

        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

//        buildGoogleApiClient();
//        mGoogleMap.setMyLocationEnabled(true);

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
//                new HospitalAsyncTask().execute();
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
//            new HospitalAsyncTask().execute();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        Log.e("BloodBanks", "buildGoogleApiClient");

        mGoogleApiClient.connect();

//        if (flag == 1) {
//            new HospitalAsyncTask().execute();
//            flag++;
//        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.e("BloodBanks", "onConnected");


        mLocationRequest = new LocationRequest();
        mLocationRequest.setSmallestDisplacement(0.5f);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.e("BloodBanks", "onConnectionSuspended");

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e("BloodBanks", "onLocationChanged");


//        mLastLocation = location;
//        if (mCurrLocationMarker != null) {
//            mCurrLocationMarker.remove();
//        }


        url = url1 + location.getLatitude() + "," + location.getLongitude() + url2;
        Log.e("url: ", url);
        flag = 1;
        new HospitalAsyncTask().execute();

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title("Current Position");
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
//        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

        //move map camera
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));

    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("BloodBanks", "onConnectionFailed");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.e("BloodBanks", "onCreateView");

        final LinearLayout parent = (LinearLayout) inflater.inflate(R.layout.fragment_check, container, false);
//        mapView = (MapView) parent.findViewById(R.id.map);

        mRecyclerView = parent.findViewById(R.id.lv);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        context = getContext();

        hospitalList = new ArrayList<>();

        return parent;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);

        Log.e("BloodBanks", "onSaveInstanceState");
    }

    @Override
    public void onResume() {
        super.onResume();
//        mapView.onResume();

        Log.e("BloodBanks", "onResume");

        initializeMap();
        buildGoogleApiClient();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("BloodBanks", "onStart");

        buildGoogleApiClient();
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.e("BloodBanks", "onPause");

//        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e("BloodBanks", "onDestroy");

//        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        mapView.onLowMemory();

        Log.e("BloodBanks", "onLowMemory");
    }

    private class HospitalAsyncTask extends AsyncTask<Void, Void, Void> {

        String jsonStr = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);


            pDialog.show();
            Log.e("progress", "start");
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler hh = new HttpHandler();
            jsonStr = hh.makeServiceCall(url);

            hospitalList.clear();

            Log.e("AsyncTask", "Response from url: " + url + "\t" + jsonStr);

            if (jsonStr != null) {
                try {

                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray results = jsonObj.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject item = results.getJSONObject(i);

                        HospitalListModel model = new HospitalListModel();

                        String name = item.getString("name");
                        String vicinity = item.getString("vicinity");

                        JSONObject geometry = item.getJSONObject("geometry");

                        JSONObject loc = geometry.getJSONObject("location");

                        String lat = loc.getString("lat");
                        String lng = loc.getString("lng");

//                        HashMap<String, String> hospital = new HashMap<>();
//                        hospital.put("name", name);
//                        hospital.put("vicinity", vicinity);

                        model.setName(name);
                        model.setVicinity(vicinity);

                        if (hospitalList.size() < 10) {
                            hospitalList.add(model);
                        } else {
                            break;
                        }

                    }


                } catch (final JSONException e) {
                    Log.e("AsyncTask", "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Connection Error",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e("AsyncTask", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),
                                "Connection Error",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (pDialog.isShowing()) {
                pDialog.dismiss();
                y++;
            }

            /**
             * Updating parsed JSON data into ListView
             * */

            if (jsonStr != null) {

                adapter = new HospitalListAdapter(hospitalList, context);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(linearLayoutManager);

                RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
                mRecyclerView.addItemDecoration(itemDecoration);
                mRecyclerView.setAdapter(adapter);
            }
        }
    }
}
