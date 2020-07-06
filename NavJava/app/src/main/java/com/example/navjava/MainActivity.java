package com.example.navjava;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * MapsActivity is the main activity.
 * the user can switch between fragment easily
 * the first fragment is SearchParkingByName
 */

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    /**
     * UI design
     */

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView notif;

    /**
     * Fragment
     */
    private Fragment SearchByName_Fragment,
                    SearchNearBy_Fragment,CurrentLocation_Fragemnt,settings_Fragemnt,db_Fragement;



    /**
     * les ID des fragments
     */

    public static final int FRAGMENT_SearchByName = 1,
                            Fragment_SearchNearBY = 2,
                            Fragement_CurrentLocation = 3,
                            Fragement_Settings = 4,
                            Fragement_db = 5;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * if the user is connected
         * then
         *   hid the button logout (can't logout),
         *   hid the button that should be shown
         *   show button login
         * else
         *   hid the button login
         *   show the button that should be shown
         *   show the button logout
         */
        configureToolBar();
        configureDrawerLayout();
        configureNavigationView();
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_name).setVisible(true);
        /** if you want to make a menu item invisible just change the value to false*/

        /**
         * show the first fragment (first view)
         */
        showFirstFragment();
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * show fragment after click
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_name:
                this.showFragment(FRAGMENT_SearchByName);
                break;
            case R.id.nav_searchNearBy:
                this.showFragment(Fragment_SearchNearBY);
                break;
            case R.id.nav_detectCurrentLatLng:
                this.showFragment(Fragement_CurrentLocation);
                break;
            case R.id.nav_settings:
                this.showFragment(Fragement_Settings);
                break;
            case R.id.nav_db:
                this.showFragment(Fragement_db);
                break;
            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Configuration
     */

    /**
     * configure toolBar
     */
    private void configureToolBar(){
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Configure DrawerLayout
     */
    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * Configure NavigationView
     */

    private void configureNavigationView(){
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }

    /**
     * Fragments
     */

    private void showFirstFragment(){
        // Initialiser la base de données des feedbacks

        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (visibleFragment == null){

            this.showFragment(FRAGMENT_SearchByName);
            /*if(!getCurrentUser().equals(""))
                this.navigationView.getMenu().getItem(0).setChecked(true);
            else
                this.navigationView.getMenu().getItem(1).setChecked(true);*/
        }
    }

    /**
     * Show fragment according an Identifier
     * @param fragmentIdentifier
     */

    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){

            case FRAGMENT_SearchByName:
                this.showSearchByNameFragment();
                break;
            case Fragment_SearchNearBY:
                this.showSearchNearByFragment();
                break;
            case Fragement_CurrentLocation:
                this.showCurrentLocation();
                break;
            case Fragement_Settings:
                this.showSettings();
                break;
            case Fragement_db:
                this.showDB();
                break;
            default:
                break;
        }
    }



    /**
     * Create each fragment page and show it
     */


    private void showSearchByNameFragment(){

        if (this.SearchByName_Fragment == null){
            this.SearchByName_Fragment = SearchByNameFragment.newInstance();
        }
        this.startTransactionFragment(this.SearchByName_Fragment);
    }

    private void showSearchNearByFragment(){
        if (this.SearchNearBy_Fragment == null){
            this.SearchNearBy_Fragment = SearchNearBy.newInstance();
        }
        this.startTransactionFragment(this.SearchNearBy_Fragment);
    }

    private void showCurrentLocation(){
        if(this.CurrentLocation_Fragemnt == null){
            /** DtectedLatLng est le nom de la class*/
            this.CurrentLocation_Fragemnt = DetectLatLng.newInstance();
        }
        this.startTransactionFragment(this.CurrentLocation_Fragemnt);
    }

    private void showSettings(){
        if(this.settings_Fragemnt == null){
            this.settings_Fragemnt = Settings.newInstance();
        }
        this.startTransactionFragment(this.settings_Fragemnt);
    }

    private void showDB(){
        if(this.db_Fragement == null){
            this.db_Fragement = DBConnection.newInstance();
        }
        this.startTransactionFragment(this.db_Fragement);
    }


    /**
     * Generic method that will replace and show a fragment inside the MainActivity Frame Layout
     * @param fragment
     */

    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }

    public void showLogoutItem(){
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_name).setVisible(true);
    }

    /**
     *  useful Methods
     */

    /**
     * DisconnectUser is called when the user click on logout button
     */



    /**
     * getCurrentUser return "" if the client is not connected or his id
     * @return
     */
    public String getCurrentUser() {
        SharedPreferences mPrefs = getPreferences(Context.MODE_PRIVATE);
        String userID = mPrefs.getString("current_user_ID", "");
        return userID;
    }



    /**
     * getRequestUrl get latitude and logitude of the apartment and return a url to look for a directions
     * from the current location of the user to the parking
     * @param origin
     * @param dist
     * @return
     */
    public String getRequestUrl(LatLng origin, LatLng dist,String mode) {
        /** url de route
         * AIzaSyBno8M2VxQUzvG5Jm5fKYqffQuePpxrXfQ
         * AIzaSyDJu3Z5gK9a1xQOJ5Qf44vkq1Mkfp5f058*/

        /** walking  or driving*/
        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        url.append("origin=" + origin.latitude + "," + origin.longitude + "&");
        url.append("destination=" + dist.latitude + "," + dist.longitude + "&");
        url.append("mode="+mode + "&key=AIzaSyBno8M2VxQUzvG5Jm5fKYqffQuePpxrXfQ");
        return url.toString();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }




}