package com.product.nearme.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.product.nearme.EventsScreen;
import com.product.nearme.R;
import com.product.nearme.utils.constant;

import java.util.List;
import java.util.Locale;

public class TabThreeFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    View rootView;
    MapView mMapView;
    GoogleMap googleMap;
    Bundle savedInstance;
    GoogleApiClient mGoogleApiClient;
    private LocationRequest locationRequest;
    FusedLocationProviderClient mFusedLocationClient;
    LocationCallback mLocationCallback;
    Boolean checklatlng = false;
    ProgressDialog progressDialog;
    ProgressBar progressbar;
    double double_lat, double_lng;
    private Boolean isStarted = true;
    private Boolean isVisible = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab_threefragment, container, false);

        savedInstance = savedInstanceState;
        init();
        return rootView;
    }

    private void init() {
//        progressbar = (ProgressBar) rootView.findViewById(R.id.progressbar);
        rootView.findViewById(R.id.progressbar).setVisibility(View.GONE);
        CheckPermissions();

        rootView.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(getActivity(), EventsScreen.class);
                startActivity(intent);
            }
        });
    }

    private void loadgooglemap() {
        mMapView = (MapView) rootView.findViewById(R.id.mapview);
        mMapView.onCreate(savedInstance);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                getLocationrequest();

                mLocationCallback = new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        try {
                            List<Location> locationList = locationResult.getLocations();
                            if (locationList.size() > 0) {

                                //The last location in the list is the newest
                                Location location = locationList.get(locationList.size() - 1);
                                Log.e("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                                LatLng mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                                googleMap.addMarker(new MarkerOptions().position(mLatLng).title("Marker Title").snippet("Marker Description"));

                                CameraPosition cameraPosition = new CameraPosition.Builder().target(mLatLng).zoom(12).build();
//                                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                // For showing a move to my location button
                                googleMap.setMyLocationEnabled(true);

                                if (checklatlng = false) {
                                    checklatlng = true;
                                    getaddressfromlatlong(location.getLatitude(), location.getLongitude());
                                }
                            }
                        } catch (Exception e) {

                        }
                    }
                };

                mFusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());

                try {
                    googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                        @Override
                        public void onCameraChange(CameraPosition cameraPosition) {
                            Log.e("cameraPosition", "#" + cameraPosition.target.latitude);

                            getaddressfromlatlong(cameraPosition.target.latitude, cameraPosition.target.longitude);
                        }
                    });
                } catch (Exception e) {

                }
            }
        });
    }

    private void getaddressfromlatlong(double latitude, double longitude) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                Log.e("city", "#" + city);

//                getrequestbasedoncity(city);
            }
        } catch (Exception e) {

        }
    }

    private void getLocationrequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(120000); // two minute interval
        locationRequest.setFastestInterval(120000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    private void CheckPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (constant.haveLocationPermissions(getActivity())) {
                loadgooglemap();
            } else {
                constant.requestLocationPermission(getActivity());
            }
        } else {
            loadgooglemap();
        }
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isStarted = false) {
//            init();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e("location", "#" + location.getLatitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;

        Log.e("isVisibleToUser3", "#" + isVisible);
        Log.e("isStarted3", "#" + isStarted);

        if (isVisible) {
//            init();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("onStart", "#");
        isStarted = true;

        if (isVisible && isStarted) {
            Log.e("valid", "#");
//            init();
        }
    }
}

