package com.example.admin.ebuy.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
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
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.admin.ebuy.R;
import com.example.admin.ebuy.base.BaseActivity;
import com.example.admin.ebuy.base.BaseFragment;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;


public class MapsFragment extends BaseFragment implements OnMapReadyCallback {

    public final static String TAG = "MapsFragment";
    private GoogleMap mMap;
    private static final int LOCATION_REQUEST = 500;
    List<LatLng> listPoints;
    String location1;
    String currentLocation;
    int type;
    LatLng sydney3 = null;
    LatLng sydney2 = null;
    LatLng sydney1 = null;
    private FusedLocationProviderClient fusedLocationProviderClient;
    LocationListener locationListener;
    LocationManager locationManager;

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

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);

//        locationManager = (LocationManager) getActivity().getSystemService(
//                Context.LOCATION_SERVICE);
        mapFragment.getMapAsync(this);

        listPoints = new ArrayList<>();
        ((BaseActivity) getActivity()).setTitle(true, getResources().getString(R.string.delivering));
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        listPoints.clear();
    }

    @Override
    public void onStop() {
        super.onStop();
        listPoints.clear();
        mMap.clear();
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
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }
        mMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager)
                getContext().getSystemService(Context.LOCATION_SERVICE);
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (myLocation == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            String provider = locationManager.getBestProvider(criteria, true);
            myLocation = locationManager.getLastKnownLocation(provider);

        }
        sydney3 = new LatLng(myLocation.getLatitude(),myLocation.getLongitude());



//        String address1 = "Trần Trọng Kim, Phường 22, Bình Thạnh, Hồ Chí Minh, Việt Nam";
        String address1 = location1;
        sydney1 = getLocationFromAddress(getContext(),address1);
        if(type==1){
            sydney2 = sydney3;
        }
        else {
            String address2 = "227 Đường Nguyễn Văn Cừ, Phường 4, Quận 5, Hồ Chí Minh";
            sydney2 = getLocationFromAddress(getContext(),address2);
        }

        listPoints.add(sydney1);
        listPoints.add(sydney2);

        int i = 0;

        for (final LatLng latLng : listPoints){
            final MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            i++;
            if(i==1){
                if (type==1){
                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.iconshop);
                    markerOptions.icon(icon);
                }
                else {
                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.girl);
                    markerOptions.icon(icon);
                }
                //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                Log.e("triệu", "3");
            }else {
                if (type==1){
                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.girl);
                    markerOptions.icon(icon);
                }
                else {
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
    private String getRequestUrl(LatLng origin, LatLng dest){
        //Value of origin
        String str_org = "origin=" + origin.latitude +","+origin.longitude;
        //Value of destination
        String str_dest = "destination=" + dest.latitude+","+dest.longitude;
        //Set value enable the sensor
        String sensor = "sensor=false";
        //Mode for find direction
        String mode = "mode=driving";
        //Build the full param
        String param = str_org +"&" + str_dest + "&" +sensor+"&" +mode;
        //Output format
        String output = "json";
        //Create url to request
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + param+"&key=AIzaSyAqTCmGrK4J42n7pDMy1yAl9JYNiDTdeaQ";

        return url;
    }
    private String requestDirection(String reqUrl)throws IOException {
        String responseString="";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try{
            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line= bufferedReader.readLine())!=null){
                stringBuffer.append(line);
            }

            responseString = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (inputStream!=null){
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }
        return responseString;
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case LOCATION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
                break;
        }
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
            return  responseString;
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
    public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>> > {

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
            Log.e("triệu",lists+"");
            ArrayList points = null;

            PolylineOptions polylineOptions = null;

            for (List<HashMap<String, String>> path : lists) {
                points = new ArrayList();
                polylineOptions = new PolylineOptions();

                for (HashMap<String, String> point : path) {
                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lon"));
                    points.add(new LatLng(lat,lon));
                }

                polylineOptions.addAll(points);
                polylineOptions.width(10);
                polylineOptions.color(Color.BLUE);
                polylineOptions.geodesic(true);
            }


            if (polylineOptions!=null) {
                Log.e("triệu", "8");
                mMap.addPolyline(polylineOptions);
            } else {
                Log.e("triệu", "9");
                Toast.makeText(getContext(), "Direction not found!", Toast.LENGTH_SHORT).show();
            }

        }
    }
    //    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        String address = "Trần Trọng Kim, Phường 22, Bình Thạnh, Hồ Chí Minh, Việt Nam";
//        LatLng sydney = getLocationFromAddress(this,address);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }
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
