package com.cali.geohole.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.cali.geohole.R;
import com.cali.geohole.model.User;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    // Global Variables
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mLastLocation;
    private GoogleMap mMap;
    // Lat Lon
    LatLng locationUser = null;
    User user;
    // Elements View
    Button btnOpenAddPhoto;
    EditText txtAddress;
    EditText txtLength;
    EditText txtWidth;
    EditText txtHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Init View
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Get Data B
        this.user = (User) getIntent().getSerializableExtra("user");

        // Init Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Start elements View
        btnOpenAddPhoto = findViewById(R.id.btnGeolocation);
        txtAddress = findViewById(R.id.txtAddress);
        txtLength = findViewById(R.id.txtLength);
        txtWidth = findViewById(R.id.txtWidth);
        txtHeight = findViewById(R.id.txtHeight);

        // Change View
        viewPhoto();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        checkPermission();
    }

    public void viewPhoto() {
        btnOpenAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                /*FragmentManager manager = ;
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.nav_host_fragment, manager)
                transaction.commit()*/
            }
        });
    }


    private void checkPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Dexter.withActivity(this)
                    .withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.isAnyPermissionPermanentlyDenied()) {
                                Toast.makeText(getApplicationContext(), "deber aceptar los permisos de localización", Toast.LENGTH_SHORT).show();
                            } else if (report.areAllPermissionsGranted()) {
                                mLastLocation = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    return;
                                }
                                mLastLocation.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        locationUser = new LatLng(location.getLatitude(), location.getLongitude());
                                        addMark();
                                        setAddress();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    })
                    .check();
        } else {
            mLastLocation = LocationServices.getFusedLocationProviderClient(this);
            mLastLocation.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        locationUser = new LatLng(location.getLatitude(), location.getLongitude());
                        addMark();
                        setAddress();
                    } else {
                        Toast.makeText(MapsActivity.this, R.string.error_maps, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void addMark() {
        mMap.addMarker(new MarkerOptions().position(locationUser).title(getString(R.string.my_location)));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(locationUser, 17);
        mMap.animateCamera(cameraUpdate);

    }

    public void setAddress() {
        double latitude = locationUser.latitude;
        double longitude = locationUser.longitude;
        try {
            Geocoder geo = new Geocoder(MapsActivity.this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
            if (addresses.isEmpty()) {
                txtAddress.setText("Waiting for Location");
            } else {
                if (addresses.size() > 0) {
                    txtAddress.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                    //Toast.makeText(getApplicationContext(), "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }
    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
