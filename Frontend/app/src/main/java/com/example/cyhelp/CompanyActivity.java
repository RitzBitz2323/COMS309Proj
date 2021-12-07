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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * CompanyActivity is used by companies, who have multiple technicians under them, to manage their technicians and track their technicians current ticket or past tickets.
 *
 * @author Nick Sandeen
 */
public class CompanyActivity extends AppCompatActivity {

    int companyID;
    String url = "http://coms-309-051.cs.iastate.edu:8080/actors/";
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        Intent intent = getIntent();
        companyID = intent.getIntExtra("id", 10);

        url += companyID + "/company";

        ArrayList<String> jsonResponses = new ArrayList<> ();
        ArrayList<Integer> techIDs = new ArrayList<> ();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println("Company object: " + response);
                    JSONArray employees = response.getJSONArray("employees");
                    for(int i = 0; i < employees.length() - 1; i++){
                        JSONObject jsonObject = employees.getJSONObject(i);
                        String firstName = jsonObject.getString("firstName");
                        String lastName = jsonObject.getString("lastName");
                        String username = jsonObject.getString("username");
                        int techID = jsonObject.getInt("id");
                        techIDs.add(techID);
                        jsonResponses.add(firstName + " " + lastName + " @" + username);
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

        requestQueue.add(jsonObjectRequest);

        ListView listView = (ListView) findViewById(R.id.list_view);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CompanyActivity.this, TechHomeActivity.class);
                intent.putExtra("id", techIDs.get(i));
                startActivity(intent);
            }
        });

        Button logout = (Button) findViewById(R.id.logout_Button_CompanyActivity);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompanyActivity.this, MainActivity.class);
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
}