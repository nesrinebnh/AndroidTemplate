package com.example.navjava.appartment;

import android.widget.Toast;


import com.example.navjava.Model.Appartment;
import com.example.navjava.SearchNearBy;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class GetAndSaveParkings {
    /*
     *   la classe suivante sert a recuperer les perkings dans a partir d'un hash map
     *   googleParkingMap, ensuite envoyer le resultat vers firebase puisque l'api est utilisable
     *   uniquement une fois
     */
    private SearchNearBy mainActivity;

    public void setMainActivity(SearchNearBy activity){
        mainActivity = activity;
    }

    /*
     *   recuperer les cooredonnés d'un parking a partir du JSONObject
     *   et les mettre dans un hashMap
     */

    private void getParking(JSONObject googlePlaceJson){
        Appartment geo = new Appartment();
        HashMap<String,String> googleParkingMap = new HashMap<>();
        String appartmentName = "-NA-";
        String vicinity = "-NA-";
        String lat = "";
        String log ="";
        String ref ="";

        try {
            if((!googlePlaceJson.isNull("name"))&&(!googlePlaceJson.isNull("vicinity")) ) {

                appartmentName = googlePlaceJson.getString("name");
                vicinity =  googlePlaceJson.getString("vicinity");
                geo.setVicinity(vicinity);
                geo.setName(appartmentName);
            }
            lat = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            log = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");

            ref = googlePlaceJson.getString("reference");
            geo.setLat(lat);
            geo.setLng(log);
            geo.setRef(ref);


            /**
             * cette instruction permet d'envoyer la liste trouvé à firebase
             */
            //mainActivity.databaseReference.child("lat_lng"+String.valueOf(mainActivity.getKey_genrate())).push().setValue(geo);
            /** this method exist in Search Near by which will diplay the appartment from firebase*/
           // mainActivity.getListParking();

            /** for me i will display them directly but the previous way is possible and useful
             * if you want to add extra function onClickOnMarker for example*/

            /** display the results on map*/
            LatLng latLng = new LatLng(Double.valueOf(lat),Double.valueOf(log));
            mainActivity.getmMapVoid().addMarker(new MarkerOptions().position(latLng));
            mainActivity.getmMapVoid().moveCamera(CameraUpdateFactory.newLatLng(latLng));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /*
     * envoyer la liste des parkings trouvée vers firebase
     * */
    public void getListParking(String jsonData){

        JSONArray array  = null;
        String status = "";
        try {
            JSONObject obj = new JSONObject(jsonData);
            /* l'url contient les resultats sous forme d'un array son key c'est results */
            array = obj.getJSONArray("results");
            status = obj.getString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if( status.equals("OK") ) {
            int size = array.length();
            if(size>0){
                for (int i = 0; i < size; i++) {
                    try {
                        getParking((JSONObject) array.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }else Toast.makeText(mainActivity.getActivity(),"no parking found",Toast.LENGTH_SHORT).show();

        }else Toast.makeText(mainActivity.getActivity(),"error\n"+status,Toast.LENGTH_SHORT).show();
    }

}
