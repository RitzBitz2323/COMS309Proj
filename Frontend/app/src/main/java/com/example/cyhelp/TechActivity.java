package com.example.cyhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class TechActivity extends AppCompatActivity {

    FusedLocationProviderClient mFusedLocationClient;

    int PERMISSION_ID = 44;

    double latitude;
    double longitude;

    IMapController mapController;
    GeoPoint startPoint;

    MapView map = null;

    Context ctx;

    String url = "http://coms-309-051.cs.iastate.edu:8080/tickets/at";

    int techID;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        techID = intent.getIntExtra("id", 2);

        // load/initialize the osmdroid configuration, this can be done
        ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        // setting this before the layout is inflated is a good idea
        // it 'should' ensure that the map has a writable location for the map cache, even without permissions
        // if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        // see also StorageUtils
        // note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's tile servers will get you banned based on this string

        // inflate and create the map
        setContentView(R.layout.activity_tech);

        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        mapController = map.getController();
        mapController.setZoom(15);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();

    }

    public void onResume(){
        super.onResume();
        // this will refresh the osmdroid configuration on resuming.
        // if you make changes to the configuration, use
        // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause(){
        super.onPause();
        // this will refresh the osmdroid configuration on resuming.
        // if you make changes to the configuration, use
        // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if location permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // get the location of technician from FusedLocationClient object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();

                        // get coordinates of technician
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                        // center map at technician's coordinates
                        startPoint = new GeoPoint(latitude, longitude);
                        mapController.setCenter(startPoint);

                        // constructing url to fetch tickets near technician
                        url += "?lat=" + latitude + "&long=" + longitude + "&range=100.0";

                        // lists to store coordinates of tickets near technician
                        ArrayList<Double> latitudeList = new ArrayList<> ();
                        ArrayList<Double> longitudeList = new ArrayList<> ();
                        ArrayList<Integer> ticketIds = new ArrayList<> ();
                        ArrayList<String> titles = new ArrayList<> ();
                        ArrayList<String> problemDescs = new ArrayList<> ();

                        // get the tickets near the technician
                        RequestQueue requestQueue = Volley.newRequestQueue(TechActivity.this);
                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    System.out.println(response);

                                    // process each ticket returned by server
                                    for(int i = 0; i < response.length(); i++){
                                        JSONObject jsonObject = response.getJSONObject(i);

                                        // get latitude and longitude for each ticket and store in corresponding list
                                        double latitudeU = jsonObject.getDouble("latitude");
                                        double longitudeU = jsonObject.getDouble("longitude");
                                        titles.add(jsonObject.getString("title"));
                                        problemDescs.add(jsonObject.getString("description"));
                                        int ticketId = jsonObject.getInt("id");
                                        latitudeList.add(latitudeU);
                                        longitudeList.add(longitudeU);
                                        ticketIds.add(ticketId);

                                        System.out.println("JSON object: " + jsonObject);
                                        System.out.println("Ticket Latitude: " + latitudeU);
                                        System.out.println("Ticket Longitude: " + longitudeU);
                                    }

                                    // the items are the icons that will be displayed on the map
                                    // each item contains the coordinates of a ticket
                                    ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
                                    for (int j = 0; j < latitudeList.size(); j++) {
                                        items.add(new OverlayItem(titles.get(j), problemDescs.get(j), new GeoPoint(latitudeList.get(j),longitudeList.get(j)))); // Lat/Lon decimal degrees
                                    }


                                    // the overlay that will display all the icons
                                    ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items,
                                            new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                                                @Override
                                                public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                                                    //do something
                                                    System.out.println(index);
                                                    int currentTicket = ticketIds.get(index);

                                                    Intent intent = new Intent(TechActivity.this, ViewTechTicketActivity.class);
                                                    intent.putExtra("ticketID", currentTicket);
                                                    intent.putExtra("techID", techID);
                                                    startActivity(intent);
                                                    return true;
                                                }
                                                @Override
                                                public boolean onItemLongPress(final int index, final OverlayItem item) {
                                                    return false;
                                                }
                                            }, ctx);
                                    mOverlay.setFocusItemsOnTap(true);

                                    map.getOverlays().add(mOverlay);

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
//        // Initializing LocationRequest object with appropriate methods
//        LocationRequest mLocationRequest = new LocationRequest();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setInterval(5);
//        mLocationRequest.setFastestInterval(0);
//        mLocationRequest.setNumUpdates(1);
//
//        // setting LocationRequest on FusedLocationClient
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
//            url += "?lat=" + latitude + "&long=" + longitude;
//            System.out.println(latitude + ", " + longitude);
//            startPoint = new GeoPoint(latitude, longitude);
//            mapController.setCenter(startPoint);
//        }
//    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location on Android 10.0 and higher, use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
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
}