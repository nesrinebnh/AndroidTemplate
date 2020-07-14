package com.example.navjava;

import android.app.AlertDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.navjava.Adapter.AcceuilAdapter;
import com.example.navjava.Adapter.RecycleAdapter;
import com.example.navjava.Model.Appartment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class Acceuil extends Fragment {
    public static Acceuil newInstance() {
        return (new Acceuil());
    }

    MainActivity mapsActivity;

    ArrayList<Appartment> appartmentHashMap = new ArrayList<>();;

    private LinearLayoutManager linearLayoutManager;

    private RecyclerView recyclerView;
    private AcceuilAdapter recycleAdapter;

    AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment2, container, false);
        mapsActivity = (MainActivity) getActivity();
        ((MainActivity) getActivity()).setActionBarTitle("Acceuil");
        recyclerView = view.findViewById(R.id.rec);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        appartmentHashMap = new ArrayList<>();

        getJSON();
        recycleAdapter = new AcceuilAdapter(getContext(), appartmentHashMap, Acceuil.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new Acceuil.GridSpacingItemDecoration(1,dpToPx(10),true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycleAdapter);
        prepareData();


        //prepareData();


        return view;
    }

    public void prepareData(){
        /*appartmentHashMap.put("val1", new Appartment("name1"));
        appartmentHashMap.put("val2",new Appartment("name2"));
        appartmentHashMap.put("val3",new Appartment("name3"));*/
        Log.i("UPDATE UI", "UI UPDATED");
        recycleAdapter.notifyDataSetChanged();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration{
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int span, int space, boolean includeEdge){
            this.spanCount = span;
            this.spacing = space;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect out, View view, RecyclerView parent, RecyclerView.State state){
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if(includeEdge){
                out.left = spacing - column*spacing/spanCount;
                out.right = (column+1) * spacing / spanCount;

                if(position < spanCount) out.top = spacing;
                out.bottom = spacing;
            }else{
                out.left = column * spacing/spanCount;
                out.right = spacing - (column + 1) * spacing / spanCount;
                if(position>=spanCount){
                    out.top = spacing;
                }
            }
        }
    }

    private int dpToPx(int dp){
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,r.getDisplayMetrics()));

    }

    private void getJSON() {
        /*
         * As fetching the json string is a network operation
         * And we cannot perform a network operation in main thread
         * so we need an AsyncTask
         * The constrains defined here are
         * Void -> We are not passing anything
         * Void -> Nothing at progress update as well
         * String -> After completion it should return a string and it will be the json string
         * */
        class GetJSON extends AsyncTask<Void, Void, String> {

            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
            @Override
            protected String doInBackground(Void... voids) {


                String check_url = "http://192.168.1.2/Zahra/getappartment.php";


                    try {
                        URL url = new URL(check_url);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        StringBuilder sb = new StringBuilder();

                        //We will use a buffered reader to read the string from service
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                        //A simple string to read values from each line
                        String json;

                        //reading until we don't find null
                        while ((json = bufferedReader.readLine()) != null) {

                            //appending it to string builder
                            sb.append(json + "\n");
                        }

                        //finally returning the read string
                        return sb.toString().trim();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                return null;
            }

            @Override
            protected void onPreExecute(){
                alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle("Check status");
            }

            @Override
            protected void onPostExecute(String avoid){
                //Log.i("RESULT :: ",avoid);
                if(avoid == null){

                    alertDialog.setMessage("no result");
                    alertDialog.show();
                }else{
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(avoid);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            Appartment appartment = new Appartment(obj.getString("name"),
                                    obj.getString("type"), obj.getString("lat"),
                                    obj.getString("lng"), obj.getString("adress"),
                                obj.getString("prix"), obj.getString("image"));
                            appartmentHashMap.add(appartment);
                        }

                        Log.i("Appartment size ",String.valueOf(appartmentHashMap.size()));
                        prepareData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            protected void onProgressUpdate(Void ... values){
                super.onProgressUpdate(values);
            }
        }

        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();

        getJSON.execute();
    }


}
