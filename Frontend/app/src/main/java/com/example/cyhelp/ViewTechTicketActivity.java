package com.example.cyhelp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewTechTicketActivity extends AppCompatActivity {

    String url = "http://coms-309-051.cs.iastate.edu:8080/tickets/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tech_ticket);

        Intent intent = getIntent();

        int id = intent.getIntExtra("id", 2);
        url += id;
        System.out.println("ID of ticket: " + id);

        ArrayList<String> jsonResponses = new ArrayList<> ();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println(response);
                    String title = response.getString("title");
                    String description = response.getString("description");
                    String address = response.getJSONObject("customer").getString("homeAddress");

                    TextView titleView = (TextView) findViewById(R.id.title);
                    TextView descriptionView = (TextView) findViewById(R.id.description);
                    TextView addressView = (TextView) findViewById(R.id.address);

                    titleView.setText(title);
                    descriptionView.setText(description);
                    addressView.setText(address);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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