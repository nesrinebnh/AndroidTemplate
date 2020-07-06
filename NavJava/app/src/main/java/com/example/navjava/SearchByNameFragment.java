package com.example.navjava;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

/**
 * user knows the name of the appartment but not the location
 * we can help him to find it by using Geocoder
 * and give him options like add/remove favorite , show direction,..
 */

public class SearchByNameFragment extends Fragment implements OnMapReadyCallback,LocationListener, LocationSource {
    /**
     * Glocal variable
     */
    private static final int PERMISSIONS_REQUEST_ENABLE_GPS = 9003;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9002;
    private static GoogleMap mMap;
    private LocationManager locationManager;
    private LocationSource.OnLocationChangedListener locationChangedListener;
    private Address address ;
    private LatLng latLng;
    private boolean connect;
    private Button zoomin,zoomout;
    private String apartmentName;
    private MaterialSearchView searchView;
    private double lat = 0, lng = 0;
    private MainActivity mapsActivity;



    public static SearchByNameFragment newInstance() {
        return (new SearchByNameFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * initial UI
         */

        mapsActivity = (MainActivity) getActivity();

        ((MainActivity) getActivity()).setActionBarTitle("Search by name");

        View view = inflater.inflate(R.layout.searchbar, container, false);
        setHasOptionsMenu(true);
        searchView = (MaterialSearchView) view.findViewById(R.id.search_view);
        zoomin = view.findViewById(R.id.zoomin);
        zoomout = view.findViewById(R.id.zoomout);
        /**
         * current location
         */

        /**
         * initial map
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

        /**
         * search for apartment
         */

        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                apartmentName = query;
                mMap.clear();
                if (apartmentName.equals("")) {/** if no place , show error message*/
                    Toast.makeText(getActivity(), "no apartment enter", Toast.LENGTH_LONG).show();
                } else {
                    /** place inserted start searching for apartment */
                    Geocoder geocoder = new Geocoder(getActivity());
                    List<Address> addressList=null;
                    try {
                        addressList = geocoder.getFromLocationName(apartmentName,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(addressList != null){
                        if ((addressList.size() > 0)){
                            address = addressList.get(0);
                            latLng = new LatLng(address.getLatitude(),address.getLongitude());
                            Log.i(String.valueOf(address.getLatitude()),String.valueOf(address.getLongitude()));
                            /**insert your marker*/
                            mMap.addMarker(new MarkerOptions().position(latLng).title(address.getCountryName()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


                        }else{
                            Toast.makeText(getActivity(), "Not found!", Toast.LENGTH_SHORT).show();

                        }

                    }else Toast.makeText(getActivity(), "Surcharge of requests\nplease repeat after a while", Toast.LENGTH_SHORT).show();

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
            }

            @Override
            public void onSearchViewClosed() {
            }
        });

        return view;
    }


    /** add SearchMateriel to appbar*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_dash, menu);
        MaterialSearchView searchView = (MaterialSearchView) getActivity().findViewById(R.id.search_view);
        final MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);


    }

    /** add actions when click on SearchMateriel*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void buildAlertMessageNoGps() {
        /**
         * show dialog if GPS permission are not allowed
         */
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("This application requires GPS and network to work properly, do you want to enable them?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        WifiManager wifi = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        wifi.setWifiEnabled(true);
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
     * LocationListener methods
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        if (locationChangedListener != null) {
            locationChangedListener.onLocationChanged(location);
            lat = location.getLatitude();
            lng = location.getLongitude();
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
     * locationSource methods
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

    public static GoogleMap getmMap() {
        return mMap;
    }

    /**
     * OnMapReadyCallback method
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

}



