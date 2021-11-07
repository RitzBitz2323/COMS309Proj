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
    private Boolean ResponseReceived;


    public SignUpModel(Context context) {
        Queue = Volley.newRequestQueue(context);
        ResponseReceived = false;
    }


    /**
     * This method is called by SignUpPresenter after it has collected all the user information in a JSON object
     * which is passed to this method. This method sends this JSON object to the server using a POST request to
     * create the Actor. Once it has sent the response, it calls the getActorInfo method of SignUpPresenter.
     * In the case of an error, an error message is assigned to a variable.
     */
    public void httpCreateUserRequest(JSONObject createActor, SignUpPresenter signUpPresenter) {

        String postUrlCreateActor = "http://coms-309-051.cs.iastate.edu:8080/actors";

        ErrorMessage = "";
        UserID = 0;
        UserType = 5;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrlCreateActor, createActor, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    UserID = response.getInt("id");
                    UserType = response.getInt("userType");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ResponseReceived = true;
                signUpPresenter.getActorInfo();
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
                ResponseReceived = true;
                signUpPresenter.getActorInfo();
            }
        });
        Queue.add(jsonObjectRequest);
    }


    public String getErrorMessage() {
        return ErrorMessage;
    }
    public int getUserID() {return UserID;}
    public int getUserType() {return UserType;}
}
