package com.example.cyhelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class JoinCompanyActivity extends AppCompatActivity {

    int techID;
    String companyID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_company);

        Intent intent = getIntent();
        techID = intent.getIntExtra("techID", 2);

    }

    public void submit(View v) throws JSONException {
        EditText companyCode = (EditText) findViewById(R.id.companyCode);
        companyID = companyCode.getText().toString();

        String postUrl = "http://coms-309-051.cs.iastate.edu:8080/company/" + companyID + "/join";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        postData.put("id", techID);

        System.out.println("Company ID: " + companyID + " Tech ID: " + techID);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);

        Intent intent = new Intent(this, TechHomeActivity.class);
        intent.putExtra("id", techID);
        startActivity(intent);
    }
}