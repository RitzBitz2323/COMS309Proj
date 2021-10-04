package com.example.cyhelp;

import static com.example.cyhelp.R.id.editTextTextPassword_loginActivity;
import static com.example.cyhelp.R.id.editTextTextPersonName_loginActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class LoginActivity extends AppCompatActivity {

    protected int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.loginButton_loginActivity);
        System.out.println("Login Screen");
    }

    public void loginUser(View view){
        String postUrl = "http://coms-309-051.cs.iastate.edu:8080/actors/login";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject userLogin = new JSONObject();

        EditText username = (EditText) findViewById(R.id.editTextTextPersonName_loginActivity);
        EditText password = (EditText) findViewById(R.id.editTextTextPassword_loginActivity);
        try {
            userLogin.put("username", username.getText().toString());
            userLogin.put("password", password.getText().toString().hashCode());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, userLogin, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    userID = response.getInt("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(LoginActivity.this, ViewTicketsActivity.class);
                i.putExtra("id", userID);
                startActivity(i);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


}
