package com.example.cyhelp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * CreateNewTicketActivity allows a user to create a new ticket. The user's location is then sent to the server so technicians can see where there are tickets available.
 *
 * @author Parthiv Ganguly
 */
public class CreateNewTicketActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // initializing
    // FusedLocationProviderClient
    // object
    FusedLocationProviderClient mFusedLocationClient;

    int PERMISSION_ID = 44;

    double latitude;
    double longitude;

    String technicianType;
    int categoryID;
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_ticket);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Intent intent = getIntent();

        ID = intent.getIntExtra("id", 2);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // method to get the location
        getLastLocation();
    }

    /**
     * This method uses Google Play Services to fetch the most recent location of the User.
     * The most recent location of the user is the User's current location almost all of the time.
     * Once it fetches the location, the latitude and longitude are stored in global variables.
     */
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

//    @SuppressLint("MissingPermission")
//    private void requestNewLocationData() {
//
//        // Initializing LocationRequest
//        // object with appropriate methods
//        LocationRequest mLocationRequest = new LocationRequest();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setInterval(5);
//        mLocationRequest.setFastestInterval(0);
//        mLocationRequest.setNumUpdates(1);
//
//        // setting LocationRequest
//        // on FusedLocationClient
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
//    }
//
//    private LocationCallback mLocationCallback = new LocationCallback() {
//
//        @Override
//        public void onLocationResult(LocationResult locationResult) {
//            Location mLastLocation = locationResult.getLastLocation();
//            latitude = mLastLocation.getLatitude();
//            longitude = mLastLocation.getLongitude();
//        }
//    };

    /**
     * This method is used to check whether Location permission has been granted to app
     * @return if location has been enabled
     */
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    /** This method requests for Location permissions
     */
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    /**
     * This method checks if Location is enabled
     * @return whether the Location is enabled
     */
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /**
     * This method checks if the request for Location permissions has been granted
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

    /**
     * This method is called when User clicks the Submit button
     * It collects all the information entered by the User and packages it into a JSON object
     * Then, the method send the JSON object to the server along with the User's ID
     * Once the request has been added to the Request Queue, the method starts the View Ticket Activity
     * @param view the View that was clicked
     */
    public void submitTicket(View view) {
        String postUrl = "http://coms-309-051.cs.iastate.edu:8080/tickets";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        JSONObject customerData = new JSONObject();
        try {
            customerData.put("id", ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("Category ID: " + categoryID);

        JSONObject categoryData = new JSONObject();
        try {
            categoryData.put("id", categoryID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        EditText problemDesc = (EditText) findViewById(R.id.problemDesc);

        EditText homeAddress = (EditText) findViewById(R.id.homeAddress);

        EditText problemTitle = (EditText) findViewById(R.id.problem_title);

        try {
            postData.put("customer", customerData);
            postData.put("description", problemDesc.getText().toString());
//            postData.put("home_address", homeAddress.getText().toString());
            postData.put("latitude", latitude);
            postData.put("longitude", longitude);
            postData.put("category", categoryData);
            postData.put("title", problemTitle.getText().toString());
            System.out.println(postData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

        Intent intent = new Intent(this, ViewTicketsActivity.class);
        intent.putExtra("id", ID);
        startActivity(intent);
    }

    /**
     * This method is called when an item is selected from the Category drop down menu
     * It records the item selected so that it can be sent to the server as part of a ticket
     * @param parent The AdapterView where the selection happened
     * @param view The View within the AdapterView that was selected
     * @param pos The position of the item selected, starting at 0
     * @param id The row ID of the item that is selected
     */
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        System.out.println("Position: " + pos);
        categoryID = pos + 2;
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }


}