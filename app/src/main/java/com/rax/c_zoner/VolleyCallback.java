package com.rax.c_zoner;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface VolleyCallback {
    public void OnSuccess(JSONObject object);

    public void OnFailure(VolleyError error);
}
