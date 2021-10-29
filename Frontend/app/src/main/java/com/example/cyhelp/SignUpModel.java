package com.example.cyhelp;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class SignUpModel {


    private RequestQueue Queue;
    protected String ErrorMessage;
    protected int UserID;
    protected int UserType;


    public SignUpModel(Context context) {
        Queue = Volley.newRequestQueue(context);
    }


    /**
     *
     * @return
     */
    public void httpCreateUserRequest(JSONObject createActor) {

        String postUrlCreateActor = "http://coms-309-051.cs.iastate.edu:8080/actors";

        ErrorMessage = "";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrlCreateActor, createActor, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    UserID = response.getInt("id");
                    UserType = response.getInt("userType");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    ErrorMessage = "Cannot connect to Internet. Check your connection!";
                } else if (error instanceof ServerError) {
                    ErrorMessage = "Server not found. Please try again later.";
                } else if (error instanceof AuthFailureError) {
                    ErrorMessage = "Cannot connect to Internet. Check your connection!";
                } else if (error instanceof ParseError) {
                    ErrorMessage = "Username already exists! Please enter a new username.";
                } else if (error instanceof NoConnectionError) {
                    ErrorMessage = "Cannot connect to Internet. Check your connection!";
                } else if (error instanceof TimeoutError) {
                    ErrorMessage = "Connection TimeOut. Check your connection!";
                }
                UserID = 0;
                UserType = 4;
            }
        });
        UserType = 5;
        Queue.add(jsonObjectRequest);

    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public int getUserID() {return UserID;}
    public int getUserType() {return UserType;}
}
