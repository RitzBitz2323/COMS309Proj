package com.example.cyhelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewUserClosedTicketsActivity extends AppCompatActivity {

    String url = "http://coms-309-051.cs.iastate.edu:8080/actors/";
    int ID;
    String actorType;
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_closed_tickets);

        Intent intent = getIntent();

        ID = intent.getIntExtra("id", 2);
        actorType = intent.getStringExtra("actorType");

        System.out.println("Actor Type: " + actorType);

        url += ID + "/tickets";

        ArrayList<String> jsonResponses = new ArrayList<> ();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    //System.out.println(response);
                    for(int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        String title = jsonObject.getString("title");
                        int state = jsonObject.getInt("state");

                        if (state == 3) {
                            jsonResponses.add(title);
                        }
                    }
                    fillList(jsonResponses);
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

        requestQueue.add(jsonArrayRequest);

        ListView listView = (ListView) findViewById(R.id.list_view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ViewUserClosedTicketsActivity.this, ViewUserClosedTicketActivity.class);
                intent.putExtra("TicketPosition", i);
                intent.putExtra("UserId", ID);
                intent.putExtra("actorType", actorType);
                startActivity(intent);
            }
        });


    }

    /**
     * This method populates the ListView on the screen with the titles of the different tickets that were
     * pulled from the server.
     * @param titles The list of titles of the tickets created by the user
     */
    public void fillList(ArrayList<String> titles) {
        adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, titles);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    public void back(View v) {
        Intent intent;
        if (actorType.equals("user")) {
            intent = new Intent(this, ViewTicketsActivity.class);
        } else {
            intent = new Intent(this, TechHomeActivity.class);
        }
        intent.putExtra("id", ID);
        startActivity(intent);
    }
}