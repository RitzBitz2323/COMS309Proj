package com.example.cyhelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    protected String actorType;
    protected int actorTypeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_SignUpActivity);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.userTypes, R.layout.spinner_item_sign_up);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        String actorType;

    }


    public void loginUser(View view){
        String postUrl = "http://coms-309-051.cs.iastate.edu:8080/actors";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject userLogin = new JSONObject();

        EditText username = (EditText) findViewById(R.id.editTextUsername_SignUpActivity);
        EditText firstName = (EditText) findViewById(R.id.editTextFirstName_SignUpActivity);
        EditText lastName = (EditText) findViewById(R.id.editTextLastName_SignUpActivity);
        EditText password = (EditText) findViewById(R.id.editTextPassword_SignUpActivity);
        EditText confirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword_SignUpActivity);
        EditText address = (EditText) findViewById(R.id.editTextTextPostalAddress_SignUpActivity);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_SignUpActivity);

        if (username.getText() != null && firstName.getText() != null && lastName.getText() != null && password.getText() != null
                && confirmPassword.getText() != null && address.getText() != null &&
                password.getText().toString().equals(confirmPassword.getText().toString())) {

            try {
                userLogin.put("username", username.getText().toString());
                userLogin.put("firstName", firstName.getText().toString());
                userLogin.put("lastName", lastName.getText().toString());
                userLogin.put("password", password.getText().toString().hashCode());
                userLogin.put("homeAddress", address.getText().toString());
                userLogin.put("userType", actorTypeID);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            int[] userID = new int[1];

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, userLogin, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        userID[0] = response.getInt("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Intent i = new Intent(SignUpActivity.this, ViewTicketsActivity.class);
                    i.putExtra("id", userID[0]);
                    startActivity(new Intent(view.getContext(), ViewTicketsActivity.class));

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }

            });

            requestQueue.add(jsonObjectRequest);


        } else {
            //Add a way to show the cause of error
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