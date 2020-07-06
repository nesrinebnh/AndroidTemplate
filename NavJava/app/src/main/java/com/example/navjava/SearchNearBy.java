package com.example.navjava;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.navjava.appartment.Places;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;;

import static android.content.Context.LOCATION_SERVICE;

public class SearchNearBy extends Fragment implements OnMapReadyCallback,LocationListener, LocationSource {
    private static final int PERMISSIONS_REQUEST_ENABLE_GPS = 9003;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9002;
    private static GoogleMap mMap;
    private LocationManager locationManager;
    private LocationSource.OnLocationChangedListener locationChangedListener;
    private ImageView start;
    private double lat = 0,lng = 0;
    private  static final int RADIUS = 10000;
    private static final String KEY ="AIzaSyCduOQmMTrZYWuMAp2g1mxjf1HrJqMaQsE";
    private MainActivity mapsActivity;
    private Button zoomin, zoomout;

    public static SearchNearBy newInstance() {
        return (new SearchNearBy());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mapsActivity = (MainActivity) getActivity();
        ((MainActivity) getActivity()).setActionBarTitle("Search near by");
        View view = inflater.inflate(R.layout.searchnearby, container, false);
        zoomin = view.findViewById(R.id.zoomin);
        zoomout = view.findViewById(R.id.zoomout);
        start = view.findViewById(R.id.btnFind);

        /**
         * initialiser map
         */
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        zoomin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());

            }
        });
        zoomout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());

            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((lat == 0) || (lng == 0)) {
                    Toast.makeText(getActivity(), "wait for gps", Toast.LENGTH_SHORT).show();
                } else {
                    /**
                     * google nous propose une methode qui permet de faire la recherche
                     * avec un url en proposant les parametre indiquer plus bas
                     */
                    String url = getUrl(lat,lng);
                    Object dataTransfer [] = new Object[2];
                    dataTransfer[0] = mMap;
                    dataTransfer[1] = url;
                    /**
                     * la classe places contient une methode qui travail en background
                     * qu'elle permet de recherche les parkings et le retourner en String
                     */
                    Places places = new Places();
                    places.setMainActivity(SearchNearBy.this);
                    places.execute(dataTransfer);
                    /**
                     * message d'indication
                     * */
                    Toast.makeText(getActivity(), "Showing parking ...",Toast.LENGTH_LONG).show();
                }
            }
        });






        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }



    private void buildAlertMessageNoGps() {
        /**
         * show dialog if GPS persmissions are not allowed
         */
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void buildAlertMessageNoInternet() {
        /**
         * show dialog if GPS permission are not allowed
         */
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("This application requires Network to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        WifiManager wifi = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        wifi.setWifiEnabled(true);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * LocationListner
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        if (locationChangedListener != null) {
            locationChangedListener.onLocationChanged(location);
            lat = location.getLatitude();
            lng = location.getLongitude();

            Log.i("changed0","ok");
            final int latSave;
            final int lngSave;

        }
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

    /**
     * LocationSource methods
     * @param onLocationChangedListener
     */
    @Override
    public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
        locationChangedListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        locationChangedListener = null;
    }

    /**
     * OnMapReadyCallback
     * @param googleMap
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.ACCESS_COARSE_LOCATION};
            ActivityCompat.requestPermissions(getActivity(), permissions, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        Boolean isAnswered = false;

        while (!isAnswered) {

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            } else {
                isAnswered = true;


            }
        }
        mMap.setMyLocationEnabled(true);
        mMap.setLocationSource(this);
        SharedPreferences mPrefs = getActivity().getPreferences(getActivity().MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        String satellitOption = mPrefs.getString("satellite", "");
        if(satellitOption.equals("true")){
            mMap.setMapType(mMap.MAP_TYPE_SATELLITE);
        }



    }



    /**
     * useful method
     */
    private String getUrl(double lat, double lng) {
        /**
         * on cherche les appartements donc on precise dans l'attribut type appartement,
         * la key c'est la key de google map places (elle est utilisable une fois par jours
         * et donc il faut la changer apres chaque test et pour cela nous avons creer une liste d'api
         * qui sont les suivantes:
         * AIzaSyDuyIv_d9i89QWqCjGEEuvPdO1X49NPNN0
         * AIzaSyCi0gcm2nyMaf_kexNjGV_QUJvoRWoMyDE
         * AIzaSyDxYMUeCtMG-n23idvYNTbsDrgXLKGqhLQ
         * AIzaSyBf8VIGKetwwI6qbBkkwmevBVBSZnws2yc
         * AIzaSyCduOQmMTrZYWuMAp2g1mxjf1HrJqMaQsE
         * AIzaSyAUFDk6a14L0MyQCQwD6xQr6QyY37ScNjc
         *
         * le radius a 3000m = 3km
         * lat et lng initialiser a 0
         */
        StringBuilder url = new StringBuilder();
        url.append("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        url.append("location=" + lat + "," + lng  + "&radius=" + RADIUS);
        url.append("&type=parking&key=" + KEY);
        return url.toString();
    }







    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void onStart(){
        super.onStart();
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (locationManager != null) {
            boolean gpsIsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            /**
             *  check for GPS permission
             */
            if (gpsIsEnabled) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION
                            , Manifest.permission.ACCESS_COARSE_LOCATION};
                    ActivityCompat.requestPermissions(getActivity(), permissions, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                }
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION
                            , Manifest.permission.ACCESS_COARSE_LOCATION};
                    ActivityCompat.requestPermissions(getActivity(), permissions, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                }


                Boolean isAnswered = false;

                while (!isAnswered) {

                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    } else {
                        isAnswered = true;
                    }
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000L, 10F, this);
            } else {
                buildAlertMessageNoGps();
            }
            if(isNetworkAvailable()== false){
                buildAlertMessageNoInternet();
            }
        }
    }

    public static GoogleMap getmMap() {
        return mMap;
    }
    public GoogleMap  getmMapVoid(){
        return mMap;
    }
}