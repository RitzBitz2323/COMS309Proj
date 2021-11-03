package com.example.cyhelp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

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

public class ViewTicketsActivity extends AppCompatActivity {

    String url = "http://coms-309-051.cs.iastate.edu:8080/actors/";
    int ID;
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tickets);

        Intent intent = getIntent();

        ID = intent.getIntExtra("id", 2);

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

                        try {
                            jsonObject.getJSONObject("technician");
                            title += " (Accepted)";
                        } catch (JSONException e) {

                        }

                        jsonResponses.add(title);
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
                Intent intent = new Intent(ViewTicketsActivity.this, ViewUserTicketActivity.class);
                intent.putExtra("TicketPosition", i);
                intent.putExtra("UserId", ID);
                startActivity(intent);
            }
        });

        Button logout = (Button) findViewById(R.id.logout_Button_ViewTicketsActivity);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewTicketsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }

    public void createNewTicket(View view) {
        Intent intent = new Intent(this, CreateNewTicketActivity.class);
        intent.putExtra("id", ID);
        startActivity(intent);
    }

    public void fillList(ArrayList<String> titles) {
        adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, titles);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

}