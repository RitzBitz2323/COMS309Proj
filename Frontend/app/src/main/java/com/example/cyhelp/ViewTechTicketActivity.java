package com.example.cyhelp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

/**
 *  ViewTechTicketActivity allows a technician to view a user ticket they select on the map.
 *
 * @author Parthiv Ganguly
 */
public class ViewTechTicketActivity extends AppCompatActivity {

    String ticketDataURL = "http://coms-309-051.cs.iastate.edu:8080/tickets/";
    String acceptTicketURL = "http://coms-309-051.cs.iastate.edu:8080/tickets/";

    int techID;
    int categoryID;

    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tech_ticket);

        Intent intent = getIntent();

        categoryID = intent.getIntExtra("categoryID", 2);

        int ticketID = intent.getIntExtra("ticketID", 2);
        techID = intent.getIntExtra("techID", 2);
        ticketDataURL += ticketID;
        acceptTicketURL += ticketID + "/accept";
        System.out.println("ID of ticket: " + ticketID);

        ArrayList<String> jsonResponses = new ArrayList<> ();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ticketDataURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println(response);
                    String title = response.getString("title");
                    String description = response.getString("description");
                    String address = response.getString("address");
                    latitude = response.getDouble("latitude");
                    longitude = response.getDouble("longitude");

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

        Button backButton = (Button) findViewById(R.id.back_Button_viewTechTicketActivity);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewTechTicketActivity.this, TechActivity.class);
                intent.putExtra("id", techID);
                intent.putExtra("categoryID", categoryID);
                startActivity(intent);
            }
        });

    }

    /**
     * This method is called when the Technician presses the Accept button.
     * It sends a request to the server with the Tech ID to show that the Technician has accepted this ticket.
     * Once the request is sent, the methods starts TechActivity
     * @param view
     */
    public void acceptTicket(View view) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject techData = new JSONObject();
        try {
            techData.put("id", techID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("Tech ID: " + techID);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, acceptTicketURL, techData, new Response.Listener<JSONObject>() {
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

    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}