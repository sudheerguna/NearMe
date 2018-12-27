package com.product.nearme.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.product.nearme.EventsScreen;
import com.product.nearme.LoginActivity;
import com.product.nearme.R;
import com.product.nearme.models.Advertisements;
import com.product.nearme.models.Nearby;
import com.product.nearme.utils.AppConstantsM;
import com.product.nearme.utils.CustomInfoWindowGoogleMap;
import com.product.nearme.utils.InfoWindowData;
import com.product.nearme.utils.SharedPref;
import com.product.nearme.utils.constant;
import com.product.nearme.volley.AppController;
import com.product.nearme.volley.MyJsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TabOneFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
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
    private Boolean isStarted = false;
    private Boolean isVisible = false;
    SharedPref mSharedPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tabone_fragment, container, false);

        savedInstance = savedInstanceState;
        return rootView;
    }

    private void init() {
        mSharedPref = new SharedPref(getActivity());
        progressbar = rootView.findViewById(R.id.progressbar);
        progressbar.setVisibility(View.GONE);
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

                getrequestbasedoncity(city);
            }
        } catch (Exception e) {

        }
    }

    private void getrequestbasedoncity(String city) {
        progressbar.setVisibility(View.VISIBLE);

//        String url = "http://ec2-13-56-34-157.us-west-1.compute.amazonaws.com:8088/wyat-work/api/pbc/v1/pbcast/location?location=bengalore";
//        String url = "http://ec2-13-56-34-157.us-west-1.compute.amazonaws.com:8088/wyat-work/api/pbc/v1/pbcast/showmyads?userId=" + mSharedPref.getString(constant.UserId);
        String url = "http://ec2-13-56-34-157.us-west-1.compute.amazonaws.com:8088/wyat-work/api/pbc/v1/pbcast/showmyads?userId=2";
        StringRequest sr = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.e("getcity»»»>", "#" + response);
                Cityresponse(response);
                progressbar.setVisibility(View.GONE);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError response) {
                progressbar.setVisibility(View.GONE);
                Log.e(" response error is", "#" + response.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> jsonObject = new HashMap<String, String>();

                return jsonObject;
            }
        };
//        queue.add(sr);
        AppController.getInstance().addToRequestQueue(sr, "string_req", getActivity());
    }

    private void Cityresponse(String response) {
//        Log.e("getcity»»»>", "#" + response);
        Gson gson = new Gson();
        Advertisements sResult = gson.fromJson(response, Advertisements.class);

        getJsonKey_value(response);
        Log.e("getStatus", "#" + sResult.getStatus().toString());
//        Log.e("getCategoryName", "#" + sResult.getAdvertisements().get(0).getCategoryName().toString());

        View marker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.info_window_layout, null);
        final TextView servicename = (TextView) marker.findViewById(R.id.servicename);
        ImageView image = (ImageView) marker.findViewById(R.id.serviceimage);
        image.setImageResource(R.mipmap.markernew);
        servicename.setVisibility(View.GONE);

        try{
            if (sResult != null) {
                Log.e("size", "#" + sResult.getAdvertisements().size());
                String UserId = sResult.getUserId().toString();
                for (int i = 0; i < sResult.getAdvertisements().size(); i++) {
                    String lat = sResult.getAdvertisements().get(i).getLocLat();
                    String lng = sResult.getAdvertisements().get(i).getLocLong();
                    int userId = sResult.getAdvertisements().get(i).getUserId();
                    String categoryName = sResult.getAdvertisements().get(i).getCategoryName();
                    String CategoryId = sResult.getAdvertisements().get(i).getCategoryId();

                    String d_lat = sResult.getAdvertisements().get(i).getUser().getLocLat();
                    String d_lng = sResult.getAdvertisements().get(i).getUser().getLocLang();
                    String user = sResult.getAdvertisements().get(i).getUser().getUserName();
                    String locationName = sResult.getAdvertisements().get(i).getLocationName();

                    Log.e("lat", "#" + lat);
                    Log.e("d_lat", "#" + d_lat);
                    load_marker(lat, lng, userId, categoryName, CategoryId, user, locationName, marker);
//                load_marker(d_lat,d_lng,marker);
                }
            }
        }catch (Exception e){

        }

        /*try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String id = jsonObject.getString("id");
                String categoryid = jsonObject.getString("categoryId");
                String category_name = jsonObject.getString("categoryName");
                String loc_lat = jsonObject.getString("loc_lat");
                String loc_long = jsonObject.getString("loc_long");

                JSONObject user_json = jsonObject.getJSONObject("user");
                String registrationType = user_json.getString("registrationType");

                Log.e("registrationType»»»>", "#" + registrationType);
            }
            loadmarkers();
        } catch (Exception e) {

        }*/
    }

    private void getJsonKey_value(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            Iterator it = jsonObject.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = jsonObject.getString(key);
                Log.e("Key:Value", "" + key );
            }
        } catch (Exception e) {

        }
    }

    private void load_marker(String lat, String lng, int userId, String categoryName, String CategoryId, String user, String locationName, View marker) {
        double d_lat = Double.parseDouble(lat);
        double d_lng = Double.parseDouble(lng);

        Marker marker1 = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(d_lat, d_lng))
                .title(user)
                .snippet(locationName)
                .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), marker))));

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Log.e("marker", "#" + marker.getTitle());
            }
        });

    }

    private void loadmarkers() {
        Marker marker1 = null;
        Marker m = null;
        /*try{
            marker1.remove();
        }catch (Exception e){

        }*/

        View marker = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.info_window_layout, null);
        final TextView servicename = (TextView) marker.findViewById(R.id.servicename);
        ImageView image = (ImageView) marker.findViewById(R.id.serviceimage);
        image.setImageResource(R.mipmap.markernew);
        servicename.setVisibility(View.GONE);

        final ArrayList<String> al = new ArrayList<>();
        al.clear();
        al.add("12.9592");
        al.add("12.9971");
        al.add("12.9177");

        ArrayList<String> al2 = new ArrayList<>();
        al2.clear();
        al2.add("77.6974");
        al2.add("77.6692");
        al2.add("77.6233");

        InfoWindowData info = new InfoWindowData();

        MarkerOptions markerOptions = new MarkerOptions();

        for (int i = 0; i < al.size(); i++) {
            marker1 = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(al.get(i)), Double.parseDouble(al2.get(i))))
                    .title("Maps")
                    .snippet("Description")
                    .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), marker))));

            /*markerOptions.position(new LatLng(Double.parseDouble(al.get(i)), Double.parseDouble(al2.get(i))))
//                    .title("Maps")
//                    .snippet("Description")
                    .icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(getActivity(), marker)));

            if(i==1){
                info.setImage("snowqualmie");
                info.setHotel("marathalli");
                info.setFood("Whitefield");
                info.setTransport("Silkboard");
            }else{
                info.setImage("snow");
                info.setHotel("marath");
                info.setFood("White");
                info.setTransport("Silk");
            }

            CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(getActivity());
            googleMap.setInfoWindowAdapter(customInfoWindow);

            m = googleMap.addMarker(markerOptions);
            m.setTag(info);
            m.hideInfoWindow();*/
        }


        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.e("position", "#" + marker.getId());
                servicename.setText(marker.getTitle());
                return false;
            }
        });
//        CameraPosition cameraPosition = new CameraPosition.Builder().target(
//                new LatLng(Double.parseDouble("13.5560"), Double.parseDouble("78.5010"))).zoom(8.50f).build();
//        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        // Drawing polyline in the Google Map for the i-th route

    }

    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
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
        init();
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
        Log.e("isVisibleToUser1", "#" + isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisible && isStarted) {
            init();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        isStarted = true;
        if (isVisible && isStarted) {
            init();
        }
    }
}
