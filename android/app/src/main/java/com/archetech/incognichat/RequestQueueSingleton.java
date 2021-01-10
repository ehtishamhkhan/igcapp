package com.archetech.incognichat;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestQueueSingleton {

    public final String TAG = this.getClass().getSimpleName() +" ---> ";

    private static RequestQueueSingleton queueInstance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private RequestQueueSingleton(Context _ctx){
        ctx = _ctx;
        requestQueue = getRequestQueue();
    }

    public static synchronized RequestQueueSingleton getInstance(Context ctx){
        if(queueInstance == null){
            queueInstance = new RequestQueueSingleton(ctx);
        }
        return queueInstance;
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public<T> void addToRequestQueue(Request<T> request){

        request.setRetryPolicy(new DefaultRetryPolicy(
            180000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(request);
    }

}
