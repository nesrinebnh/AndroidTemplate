package com.example.navjava;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;

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

public class DBConnection extends Fragment {

    MainActivity mapsActivity;

    private EditText edit;
    private TextView txt;
    private Button btn;
    AlertDialog alertDialog;

    public static DBConnection newInstance() {
        return (new DBConnection());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dbconnection, container, false);
        mapsActivity = (MainActivity) getActivity();

        ((MainActivity) getActivity()).setActionBarTitle("Connec database");

        edit = view.findViewById(R.id.edit);
        txt = view.findViewById(R.id.txt);
        btn = view.findViewById(R.id.btn);

        txt.setVisibility(View.INVISIBLE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String check = edit.getText().toString();
                String type = "login";
                /*BackgroundDB db = new BackgroundDB(getContext());
                db.execute(type,check);*/
                getJSON(type, check);
                txt.setVisibility(View.VISIBLE);
            }
        });



        return view;
    }

    private void getJSON(String type, String check) {
        /*
         * As fetching the json string is a network operation
         * And we cannot perform a network operation in main thread
         * so we need an AsyncTask
         * The constrains defined here are
         * Void -> We are not passing anything
         * Void -> Nothing at progress update as well
         * String -> After completion it should return a string and it will be the json string
         * */
        class GetJSON extends AsyncTask<String, Void, String> {

            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
            @Override
            protected String doInBackground(String... voids) {
                Log.i("TAG :: ",voids[0]);
                String type = voids[0];

                String check_url = "http://192.168.1.2/Zahra/login.php";

                if(type.equals("login")){
                    try {
                        URL url = new URL(check_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        String post_data = URLEncoder.encode("check","UTF-8")+"="+URLEncoder.encode(voids[1],"UTF-8");
                        bufferedWriter.write(post_data);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();

                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                        String result="", line="";
                        while((line =bufferedReader.readLine()) != null){
                            result += line;
                        }
                        bufferedReader.close();
                        inputStream.close();
                        httpURLConnection.disconnect();
                        return result;
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
                    txt.setText(avoid);
                    alertDialog.setMessage(avoid);
                    alertDialog.show();
                }

            }

            @Override
            protected void onProgressUpdate(Void ... values){
                super.onProgressUpdate(values);
            }
        }

        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();
        Log.i("Check :: ", type+ check);
        getJSON.execute(type, check);
    }


}
