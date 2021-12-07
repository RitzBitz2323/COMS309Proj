package com.example.cyhelp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewTechHomeTicket extends AppCompatActivity {

    protected int techID;
    protected int TicketPosition;
    protected int ticketID;
    protected String Title;
    protected String Description;
    protected String Category;
    protected String Address;
    protected String Username;
    protected String UserFirstName;
    protected String UserLastName;
    protected String UserFullName;
    protected String techUsername;
    protected TextView TitleText;
    protected TextView UserFullNameText;
    protected TextView UsernameText;
    protected TextView CategoryText;
    protected TextView AddressText;
    protected TextView DescriptionText;
    protected double latitude;
    protected double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tech_home_ticket);

        Intent intent = getIntent();
        TicketPosition = intent.getIntExtra("ticketPosition", 1);
        techID = intent.getIntExtra("techID", 2);
        System.out.println("Tech ID in ViewTechHomeTicket: " + techID);
        ticketID = intent.getIntExtra("ticketID", 2);
        techUsername = "N/A";

        TitleText = (TextView) findViewById(R.id.TicketTitle_ViewUserTicketActivity);
        UserFullNameText = (TextView) findViewById(R.id.UserFullName_ViewUserTicketActivity);
        UsernameText = (TextView) findViewById(R.id.username_ViewUserTicketActivity);
        CategoryText = (TextView) findViewById(R.id.Category_ViewUserTicketActivity);
        AddressText = (TextView) findViewById(R.id.address_ViewUserTicketActivity);
        DescriptionText = (TextView) findViewById(R.id.description_ViewUserTicketActivity);


        String url = "http://coms-309-051.cs.iastate.edu:8080/actors/";

        String postURL = url + techID + "/tickets";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, postURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("In JSON Request");
                try {
                    System.out.println("Received Server Response");
                    System.out.println(response);
                    System.out.println("Ticket Position: " + TicketPosition);

                    int count = -1;

                    JSONObject jsonObject = null;

                    for(int i = 0; i < response.length(); i++){
                        int state = response.getJSONObject(i).getInt("state");

                        if (state == 1) {
                            count++;
                        }

                        System.out.println("State: " + state + " Count: " + count);

                        if (count == TicketPosition) {
                            jsonObject = response.getJSONObject(i);
                            ticketID = jsonObject.getInt("id");
                            techUsername = jsonObject.getJSONObject("technician").getString("username");
                            break;
                        }
                    }


                    System.out.println(jsonObject.toString());

                    latitude = jsonObject.getDouble("latitude");
                    longitude = jsonObject.getDouble("longitude");

                    Title = jsonObject.getString("title");
                    Description = jsonObject.getString("description");
                    Address = jsonObject.getString("address");
                    Category = jsonObject.getJSONObject("category").getString("title");
                    Username = jsonObject.getJSONObject("customer").getString("username");
                    UserFirstName = jsonObject.getJSONObject("customer").getString("firstName");
                    UserLastName = jsonObject.getJSONObject("customer").getString("lastName");
                    UserFullName = UserFirstName + " " + UserLastName;

                    TitleText.setText(Title);
                    UserFullNameText.setText(UserFullName);
                    UsernameText.setText(Username);
                    CategoryText.setText(Category);
                    AddressText.setText(Address);
                    DescriptionText.setText(Description);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                String ErrorMessage = "";
                if (error instanceof NetworkError) {
                    ErrorMessage = "Cannot connect to Internet. Check your connection!";
                } else if (error instanceof ServerError) {
                    ErrorMessage = "Server not found. Please try again later.";
                } else if (error instanceof AuthFailureError) {
                    ErrorMessage = "Cannot connect to Internet. Check your connection!";
                } else if (error instanceof ParseError) {
                    ErrorMessage = "Parse Error! Please try again later.";
                } else if (error instanceof NoConnectionError) {
                    ErrorMessage = "Cannot connect to Internet. Check your connection!";
                } else if (error instanceof TimeoutError) {
                    ErrorMessage = "Connection TimeOut. Check your connection!";
                }
                Toast.makeText(getApplicationContext(),ErrorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);

        Button backButton = (Button) findViewById(R.id.back_Button_ViewUserTicketActivity);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewTechHomeTicket.this, TechHomeActivity.class);
                intent.putExtra("id", techID);
                startActivity(intent);
            }
        });
    }

    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void closeTicket(View view) {
        System.out.println("Ticket ID: " + ticketID);
        String closeURL = "http://coms-309-051.cs.iastate.edu:8080/tickets/" + ticketID + "/close";

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, closeURL, null, new Response.Listener<JSONObject>() {
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

    public void showDirections(View view) {
        Uri ticketLocation = Uri.parse("geo:0,0?q=" + latitude + "," + longitude + " (ticket)");
        showMap(ticketLocation);
    }

    public void openChat(View view) {
        Intent intent1 = new Intent(ViewTechHomeTicket.this, User_Chat_Activity.class);
        intent1.putExtra("TicketPosition", TicketPosition);
        intent1.putExtra("ticketID", ticketID);
        intent1.putExtra("actorId", techID);
        intent1.putExtra("userName", techUsername);
        intent1.putExtra("ticketTitle", Title);
        intent1.putExtra("actorType", 1);
        startActivity(intent1);
    }
}