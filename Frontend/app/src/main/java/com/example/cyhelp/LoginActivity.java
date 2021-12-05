package com.example.cyhelp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

/**
 * LoginActivity allows an existing user to sign in to the app. It also warns the user if the either the username or password being incorrect.
 *
 * @author Nick Sandeen
 */
public class LoginActivity extends AppCompatActivity {

    protected int userID;
    protected int userType;

    TextView Loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.loginButton_loginActivity);
        System.out.println("Login Screen");

        Loading = (TextView) findViewById(R.id.loading_LoginActivity);
        Loading.setVisibility(View.GONE);
    }

    /**
     * This method is called when the Log In button is pressed
     * It sends the username and password entered by the user to the server
     * Once it gets back a response, it starts another activity based on the actor type
     * If there was an error in the response (like incorrect username and password), then the appropriate error message is displayed
     * @param view
     */
    public void loginUser(View view){
        EditText username = (EditText) findViewById(R.id.editTextTextPersonName_loginActivity);
        EditText password = (EditText) findViewById(R.id.editTextTextPassword_loginActivity);

        if (username.getText().toString().length() != 0 && password.getText().toString().length() != 0) {
            Loading.setVisibility(View.VISIBLE);

            String postUrl = "http://coms-309-051.cs.iastate.edu:8080/actors/login";
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            JSONObject userLogin = new JSONObject();

            try {
                userLogin.put("username", username.getText().toString());
                userLogin.put("password", password.getText().toString().hashCode());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            /**
             * Creates JsonObjectRequest
             */
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, userLogin, new Response.Listener<JSONObject>() {

                /**
                 * On Response determine userID and userType if login is successful.
                 * Uses userType to route user to correct activity.
                 *
                 * @param response json object response from server
                 */
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        userID = response.getInt("id");
                        userType = response.getInt("userType");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //Check user type to determine what screen to send them to upon successful login.
                    if (userType == 0){
                        Intent i = new Intent(LoginActivity.this, ViewTicketsActivity.class);
                        i.putExtra("id", userID);
                        i.putExtra("userType", userType);
                        startActivity(i);
                    } else if (userType == 1) {
                        Intent i = new Intent(LoginActivity.this, TechHomeActivity.class);
                        i.putExtra("id", userID);
                        i.putExtra("userType", userType);
                        startActivity(i);
                    } else if (userType == 2) {
                        Intent i = new Intent(LoginActivity.this, CompanyActivity.class);
                        i.putExtra("id", userID);
                        i.putExtra("userType", userType);
                        startActivity(i);
                    } else if (userType == 3) {
                        Intent i = new Intent(LoginActivity.this, AdminActivity.class);
                        i.putExtra("id", userID);
                        i.putExtra("userType", userType);
                        startActivity(i);
                    } else {
                        //Should never be reached
                        Toast.makeText(getApplicationContext(), "Invalid User Type", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                /**
                 * Checks Json request errors and informs users of error.
                 * @param error
                 */
                @Override
                public void onErrorResponse(VolleyError error) {
                    String errorMessage = null;
                    if (error instanceof NetworkError) {
                        errorMessage = "Cannot connect to Internet. Check your connection!";
                    } else if (error instanceof ServerError) {
                        errorMessage = "Server not found. Please try again later.";
                    } else if (error instanceof AuthFailureError) {
                        errorMessage = "Cannot connect to Internet. Check your connection!";
                    } else if (error instanceof ParseError) {
                        errorMessage = "Username and or Password Incorrect! Please try again!";
                    } else if (error instanceof NoConnectionError) {
                        errorMessage = "Cannot connect to Internet. Check your connection!";
                    } else if (error instanceof TimeoutError) {
                        errorMessage = "Connection TimeOut. Check your connection!";
                    }
                    Loading.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        } else {
            Toast.makeText(getApplicationContext(), "Please enter username and password!", Toast.LENGTH_SHORT).show();
        }

    }
}
