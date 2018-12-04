package com.example.admin.ebuy.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.example.admin.ebuy.interfaces.LocationManagerInterface;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import static android.content.Context.LOCATION_SERVICE;


public class MapsFragment extends BaseFragment implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, LocationManagerInterface {

    public final static String TAG = "MapsFragment";
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    List<LatLng> listPoints;
    String location1;
    String title;
    SmartLocationManager mLocationManager;

    int type;
    LatLng sydney3 = null;
    LatLng sydney2 = null;
    LatLng sydney1 = null;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted = false;
    private GoogleApiClient googleApiClient;
    private SupportMapFragment mapFragment;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 500;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.maps_fragment;
    }

    @Override
    protected void onSetBodyView(View view, ViewGroup container, Bundle savedInstanceState) {

        ((BaseActivity) getActivity()).setVisibleFinish(false);
        savedInstanceState = getActivity().getIntent().getExtras();

        type = savedInstanceState.getInt("Type");
        location1 = savedInstanceState.getString("LocationShop");
        title = savedInstanceState.getString("Tiltle");

        Toast.makeText(getContext(), location1.toString(), Toast.LENGTH_SHORT).show();

        getLocationPermission();

        mLocationManager = new SmartLocationManager(getContext(), getActivity(), this, SmartLocationManager.ALL_PROVIDERS, LocationRequest.PRIORITY_HIGH_ACCURACY, 10 * 1000, 1 * 1000, SmartLocationManager.LOCATION_PROVIDER_RESTRICTION_NONE);

        listPoints = new ArrayList<>();
        ((BaseActivity) getActivity()).setTitle(true, title);
    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mLocationManager.startLocationFetching();
    }

    @Override
    public void onPause() {
        super.onPause();
        listPoints.clear();
        mLocationManager.pauseLocationFetching();
    }

    @Override
    public void onStop() {
        super.onStop();
        listPoints.clear();
        mMap.clear();
        mLocationManager.abortLocationFetching();
    }


    @Override
    public String getTagName() {
        return TAG;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        Log.e("triệu", "2");
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        mMap.setMyLocationEnabled(true);

        Location mlocation = mLocationManager.getLastKnownLocation();
        sydney3 = new LatLng(mlocation.getLatitude(), mlocation.getLongitude());
        String address1 = location1;
        sydney1 = getLocationFromAddress(getContext(), address1);
        if (type == 1) {
            if (sydney3 != null) {
                sydney2 = sydney3;
            } else {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }
        } else {
            String address2 = "227 Đường Nguyễn Văn Cừ, Phường 4, Quận 5, Hồ Chí Minh";
            sydney2 = getLocationFromAddress(getContext(), address2);
        }

        listPoints.add(sydney1);
        listPoints.add(sydney2);

        int i = 0;

        for (final LatLng latLng : listPoints) {
            final MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            i++;
            if (i == 1) {
                if (type == 1) {
                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.iconshop);
                    markerOptions.icon(icon);
                } else {
                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.girl);
                    markerOptions.icon(icon);
                }
                //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                Log.e("triệu", "3");
            } else {
                if (type == 1) {
                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.girl);
                    markerOptions.icon(icon);
                } else {
                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.car);
                    markerOptions.icon(icon);
                }

                // markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                Log.e("triệu", "5");
            }
            mMap.addMarker(markerOptions);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15), new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {
                    googleMap.getUiSettings().setScrollGesturesEnabled(true);
                }

                @Override
                public void onCancel() {
                    googleMap.getUiSettings().setAllGesturesEnabled(true);
                }
            });
        }
        String url = getRequestUrl(listPoints.get(0), listPoints.get(1));

        TaskRequestDirections taskRequestDirections = new TaskRequestDirections();
        taskRequestDirections.execute(url);

    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(getContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    private String getRequestUrl(LatLng origin, LatLng dest) {
        //Value of origin
        String str_org = "origin=" + origin.latitude + "," + origin.longitude;
        //Value of destination
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        //Set value enable the sensor
        String sensor = "sensor=false";
        //Mode for find direction
        String mode = "mode=driving";
        //Build the full param
        String param = str_org + "&" + str_dest + "&" + sensor + "&" + mode;
        //Output format
        String output = "json";
        //Create url to request
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + param + "&key=AIzaSyDzGTfU97JHRG9n2U29EAojIhFll2RE09I";

        return url;
    }

    private String requestDirection(String reqUrl) throws IOException {
        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            responseString = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }
        return responseString;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void locationFetched(Location mLocation, Location oldLocation, String time, String locationProvider) {
        sydney3 = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
    }

    public class TaskRequestDirections extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try {
                responseString = requestDirection(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Parse json here
            Log.e("triệu", "5");
            Log.e("triệu", s);
            TaskParser taskParser = new TaskParser();
            taskParser.execute(s);
        }
    }

    public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject = null;
            List<List<HashMap<String, String>>> routes = null;
            try {
                Log.e("triệu", "6");
                jsonObject = new JSONObject(strings[0]);
                DirectionsParser directionsParser = new DirectionsParser();
                routes = directionsParser.parse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            //Get list route and display it into the map
            Log.e("triệu", "7");
            Log.e("triệu", lists + "");
            ArrayList points = null;

            PolylineOptions polylineOptions = null;

            for (List<HashMap<String, String>> path : lists) {
                points = new ArrayList();
                polylineOptions = new PolylineOptions();

                for (HashMap<String, String> point : path) {
                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lon"));
                    points.add(new LatLng(lat, lon));
                }

                polylineOptions.addAll(points);
                polylineOptions.width(10);
                polylineOptions.color(Color.BLUE);
                polylineOptions.geodesic(true);
            }


            if (polylineOptions != null) {
                Log.e("triệu", "8");
                mMap.addPolyline(polylineOptions);
            } else {
                Log.e("triệu", "9");
                Toast.makeText(getContext(), "Direction not found!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    // get location from address input
    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

}
