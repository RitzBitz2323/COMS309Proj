package com.example.cyhelp;

import static com.android.volley.Response.ErrorListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    protected String actorType;
    protected int actorTypeID;
    TextView loading;
    protected boolean UsernameAvailable;
    protected int[] userID;
    protected int[] userType;

    String TempTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_SignUpActivity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.userTypes, R.layout.spinner_item_sign_up);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        loading = (TextView) findViewById(R.id.loading_SignUpActivity);
        loading.setVisibility(View.GONE);

        UsernameAvailable = false;
    }


    public void loginUser(View view){

        EditText username = (EditText) findViewById(R.id.editTextUsername_SignUpActivity);
        EditText firstName = (EditText) findViewById(R.id.editTextFirstName_SignUpActivity);
        EditText lastName = (EditText) findViewById(R.id.editTextLastName_SignUpActivity);
        EditText password = (EditText) findViewById(R.id.editTextPassword_SignUpActivity);
        EditText confirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword_SignUpActivity);
        EditText address = (EditText) findViewById(R.id.editTextTextPostalAddress_SignUpActivity);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_SignUpActivity);

        String postUrlUsernameAvailable = "http://coms-309-051.cs.iastate.edu:8080/actors/exists?username=" + username.getText().toString();
        String postUrlCreateActor = "http://coms-309-051.cs.iastate.edu:8080/actors";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        System.out.println(postUrlUsernameAvailable);

        JSONObject UniqueUsername = new JSONObject();
        JSONObject createActor = new JSONObject();

        if (username.getText().toString().length() != 0 &&
                firstName.getText().length() != 0 &&
                lastName.getText().length() != 0 &&
                password.getText().length() != 0 &&
                confirmPassword.getText().length() != 0 &&
                address.getText().length() != 0 &&
                spinner.toString().length() != 0 &&
                password.getText().toString().equals(confirmPassword.getText().toString())) {

            loading.setVisibility(View.VISIBLE);



            //JsonRequest to verify that the username provided is not already in use.
            JsonObjectRequest UsernameAvailableRequest = new JsonObjectRequest(Request.Method.GET, postUrlUsernameAvailable, UniqueUsername, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String UsernameAvailableResponse = response.getString("message");
                        TempTest = "hello";
                        if (UsernameAvailableResponse.equals("false")) {
                            UsernameAvailable = true;
                            TempTest = TempTest + " True";
                        }
                        else {
                            UsernameAvailable = false;
                            TempTest = TempTest + " False";
                        }
                    } catch (JSONException e) {
                        TempTest = "bye";
                        //e.printStackTrace();
                    }
                }
            }, new ErrorListener() {
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
                        errorMessage = "Parsing Error. Please try again later.";
                    } else if (error instanceof NoConnectionError) {
                        errorMessage = "Cannot connect to Internet. Check your connection!";
                    } else if (error instanceof TimeoutError) {
                        errorMessage = "Connection TimeOut. Check your connection!";
                    }
                    loading.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(UsernameAvailableRequest);

            //System.out.println(UsernameAvailableResponse + "\n\n\n\n\n");
            System.out.println(TempTest + "\n\n\n\n\n");





            /**
             * If username is unique entered user info is added to the Json object and a JsonObjectRequest is sent
             * creating a new user. Otherwise an error message is sent informing the user that the username is not
             * available.
             */
            if (UsernameAvailable) {

                try {
                    createActor.put("username", username.getText().toString());
                    createActor.put("firstName", firstName.getText().toString());
                    createActor.put("lastName", lastName.getText().toString());
                    createActor.put("password", password.getText().toString().hashCode());
                    createActor.put("homeAddress", address.getText().toString());
                    createActor.put("userType", actorTypeID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                userID = new int[1];
                userType = new int[1];

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrlCreateActor, createActor, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            userID[0] = response.getInt("id");
                            userType[0] = response.getInt( "userType");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent i = new Intent(SignUpActivity.this, ViewTicketsActivity.class);
                        i.putExtra("id", userID[0]);
                        i.putExtra("userType", userType[0]);
                        startActivity(i);

                    }
                }, new ErrorListener() {
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
                            errorMessage = "Parsing Error. Please try again later.";
                        } else if (error instanceof NoConnectionError) {
                            errorMessage = "Cannot connect to Internet. Check your connection!";
                        } else if (error instanceof TimeoutError) {
                            errorMessage = "Connection TimeOut. Check your connection!";
                        }
                        loading.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });

                requestQueue.add(jsonObjectRequest);
            } else {
                Toast.makeText(getApplicationContext(), "Username unavailable! Please select a unique username.", Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
            }
        }
        //User error messages
          else if (username.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter a username!", Toast.LENGTH_SHORT).show();
        } else if (firstName.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter your first name!", Toast.LENGTH_SHORT).show();
        } else if (lastName.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter your last name!", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Please enter a password!", Toast.LENGTH_SHORT).show();
        } else if (confirmPassword.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Please confirm your password!", Toast.LENGTH_SHORT).show();
        } else if (address.getText().toString().length() == 00) {
            Toast.makeText(getApplicationContext(), "Please enter your home address!", Toast.LENGTH_SHORT).show();
        } else if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Passwords must match!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        actorType = adapterView.getItemAtPosition(i).toString();
        if (actorType.equals("User")) {
            actorTypeID = 0;
        }
        if (actorType.equals("Technician")){
            actorTypeID = 1;
        }
        if (actorType.equals("Company")){
            actorTypeID = 2;
        }
        if (actorType.equals("Administrator")){
            actorTypeID = 3;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}