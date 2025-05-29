package com.example.pleapp;

import android.app.Application;
import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyActivity extends Application {
    private static VolleyActivity instance;
    private RequestQueue requestQueue;

    public static synchronized VolleyActivity getInstance(Context context) {
        if (instance == null) {
            instance = (VolleyActivity) context.getApplicationContext();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
