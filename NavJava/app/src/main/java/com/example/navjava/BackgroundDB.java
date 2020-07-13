package com.example.navjava;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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

public class BackgroundDB extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;

    public BackgroundDB(Context ctx){
        this.context = ctx;
    }

    @Override
    protected String doInBackground(String... voids) {
        String type = voids[0];



        String check_url = "http://192.168.1.7/Zahra/login.php";

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
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Check status");
    }

    @Override
    protected void onPostExecute(String avoid){
        //Log.i("RESULT :: ",avoid);
        if(avoid == null){
            alertDialog.setMessage("no result");
            alertDialog.show();
        }else{
            alertDialog.setMessage(avoid);
            alertDialog.show();
        }

    }

    @Override
    protected void onProgressUpdate(Void ... values){
        super.onProgressUpdate(values);
    }
}
