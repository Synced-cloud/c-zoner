package com.rax.c_zoner;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class ApplicationClass extends Application {
    private static final String TAG = "ApplicationClass";
    private final int httpRequestTimeout = 5000;

    public void httpRequestNew(Context mContext, final String URL, @Nullable final String paramString, final int method, final VolleyCallback callBack, int timeOut) throws Exception {
        if (mContext == null) {
            throw new Exception("Null Context");
        }
        if (URL == null) {
            throw new Exception("Null URL");
        }
        if (URL.equals("")) {
            throw new Exception("Invalid URL");
        }
        if (callBack == null) {
            throw new Exception("Null CallBack");
        }
        if (timeOut < httpRequestTimeout) {
            timeOut = httpRequestTimeout;
        }
        final RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        final Response.ErrorListener volleyErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null) {
                    Log.d(TAG, "Failure: " + error.networkResponse.statusCode);
                } else {
                    Log.d(TAG, "Failure: " + error.getMessage());
                }
                callBack.OnFailure(error);
            }
        };

        Response.Listener<String> stringListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    Log.d(TAG, "Success: " + response);
                    callBack.OnSuccess(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        final StringRequest stringRequest = new StringRequest(method, URL, stringListener, volleyErrorListener) {

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<>();
//                headers.put("x-rapidapi-host", "covid-19-data.p.rapidapi.com");
//                headers.put("x-rapidapi-key", "9638f2d37bmsh3e07fb3838bb923p1823cdjsnb2b56b223f7b");
//                return super.getHeaders();
//            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return paramString == null ? null : paramString.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", paramString, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = new String(response.data);
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(timeOut, 0, 1.0f));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "URL: " + URL);
                Log.d(TAG, "Object: " + paramString);
                requestQueue.add(stringRequest);
            }
        }, 300);
    }
}
