package com.example.user.checkindomaret;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationListener;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mGoogleMap;
    private SupportMapFragment mapFrag;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private ArrayList<LatLng> listLatLng;
    private RelativeLayout rlMapLayout;
    HashMap<Marker,LatLngBean> hashMapMarker = new HashMap<Marker,LatLngBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        rlMapLayout=(RelativeLayout) findViewById(R.id.rlMapLayout);



//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap=googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
        setData();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    @Override
    public void onLocationChanged(Location location)
    {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

        //move map camera
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));

        //optionally, stop location updates if only current location is needed
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
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
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void setData()
    {
        ArrayList<LatLngBean> arrayList=new ArrayList<LatLngBean>();
        LatLngBean bean=new LatLngBean();
        bean.setTitle("Indomaret");
        bean.setSnippet("Indomaret");
        bean.setLatitude("-6.978530");
        bean.setLongitude("107.634994");
        arrayList.add(bean);

        LatLngBean bean1=new LatLngBean();
        bean1.setTitle("Indomaret");
        bean1.setSnippet("Indomaret Hybrid");
        bean1.setLatitude("-6.976571");
        bean1.setLongitude("107.633114");
        arrayList.add(bean1);

        LatLngBean bean2=new LatLngBean();
        bean2.setTitle("Indomaret");
        bean2.setSnippet("Indomaret");
        bean2.setLatitude("-6.969130");
        bean2.setLongitude("107.637249");
        arrayList.add(bean2);

        LatLngBean bean3=new LatLngBean();
        bean3.setTitle("Indomaret");
        bean3.setSnippet("Indomaret Poin");
        bean3.setLatitude("-6.975080");
        bean3.setLongitude("107.635538");
        arrayList.add(bean3);

        LoadingGoogleMap(arrayList);
    }

    private void LoadingGoogleMap(ArrayList<LatLngBean> arrayList)
    {
        if (mGoogleMap != null)
        {
            mGoogleMap.clear();
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

            if(arrayList.size()>0)
            {
                try
                {
                    listLatLng=new ArrayList<LatLng>();
                    for (int i = 0; i < arrayList.size(); i++)
                    {
                        LatLngBean bean=arrayList.get(i);
                        if(bean.getLatitude().length()>0 && bean.getLongitude().length()>0)
                        {
                            double lat=Double.parseDouble(bean.getLatitude());
                            double lon=Double.parseDouble(bean.getLongitude());

                            Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(lat,lon))
                                    .title(bean.getTitle())
                                    .snippet(bean.getSnippet())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.markerindomaret)));

                            mGoogleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());

                            //Add Marker to Hashmap
                            hashMapMarker.put(marker,bean);

                            //Set Zoom Level of Map pin
                            LatLng object=new LatLng(lat, lon);
                            listLatLng.add(object);
                        }
                    }
                    SetZoomlevel(listLatLng);
                }
                catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }

                mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                    @Override
                    public void onInfoWindowClick(Marker position)
                    {
                        LatLngBean bean=hashMapMarker.get(position);
                        Toast.makeText(getApplicationContext(), bean.getTitle(),Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }else{
            Toast.makeText(getApplicationContext(),"Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
        }
    }

    public void  SetZoomlevel(ArrayList<LatLng> listLatLng)
    {
        if (listLatLng != null && listLatLng.size() == 1)
        {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(listLatLng.get(0), 16));
        }
        else if (listLatLng != null && listLatLng.size() > 1)
        {
            final Builder builder = LatLngBounds.builder();
            for (int i = 0; i < listLatLng.size(); i++)
            {
                builder.include(listLatLng.get(i));
            }

            final ViewTreeObserver treeObserver = rlMapLayout.getViewTreeObserver();
            treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
            {
                @SuppressWarnings("deprecation")
                @Override
                public void onGlobalLayout()
                {
                    if(mGoogleMap != null){
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), findViewById(R.id.map)
                                .getWidth(), findViewById(R.id.map).getHeight(), 80));
                        rlMapLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            });

        }
    }

    public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter{
        public MarkerInfoWindowAdapter() {

        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View view = getLayoutInflater().inflate(R.layout.infomarker, null);
            LatLngBean markers = hashMapMarker.get(marker);
            RatingBar markerRB = (RatingBar)view.findViewById(R.id.infowindow_rating);
            markerRB.setRating(4);
            TextView markerLabel = (TextView)view.findViewById(R.id.infowindow_name);
            markerLabel.setText(markers.getTitle());
            TextView markerType = (TextView)view.findViewById(R.id.infowindow_type);
            markerType.setText(markers.getSnippet());
            return view;
        }
    }
}
