package com.example.navjava.appartment;

import android.os.AsyncTask;

import com.example.navjava.SearchTheNearestInGoogleMap;
import com.google.android.gms.maps.GoogleMap;

import java.io.IOException;

/*
    getting the results on map
 */
public class Places extends AsyncTask<Object,String,String> {
    String placesData;
    GoogleMap mMap;
    String url;
    private SearchTheNearestInGoogleMap mainActivity;


    public void setMainActivity(SearchTheNearestInGoogleMap activity){
        mainActivity = activity;
    }


    @Override
    protected String doInBackground(Object... objects) {

        /* retourner le resultats des parkings */
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];
        UrlReader urlR = new UrlReader();
        try{
            placesData = urlR.read(url);
        }catch(IOException e){
            e.printStackTrace();
        }
        return placesData;
    }


    @Override
    protected void onPostExecute(String s){
        /*retourne la liste des parkings trouvés*/
        GetAndSaveParkings parser = new GetAndSaveParkings();
        parser.setMainActivity(mainActivity);
        parser.getListParking(s);

    }







}
