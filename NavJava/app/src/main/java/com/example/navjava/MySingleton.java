package com.example.navjava;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton minstance;
    private RequestQueue requestQueue;
    private static Context context;

    public MySingleton(Context ctx){
        context = ctx;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getInstance(Context context){
        if(minstance == null){
            minstance = new MySingleton(context);
        }
        return minstance;
    }

    public<T> void addRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }


}
