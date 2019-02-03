package com.sample.jcdecaux;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ProgressDialog pDialog;

    ArrayList<HashMap<String, String>> stations_list;


    // URL to get JSON
    String url_stations = "https://api.jcdecaux.com/vls/v1/stations?apiKey=36c36d80c9c7741357a61447242407d004b38e58";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        stations_list = new ArrayList<>();

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);



        HttpHandler sh = new HttpHandler();

      /*  RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET, url_stations, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject c) {

                        try {

                        String number = c.getString("number");

                        String contract_name = c.getString("contract_name");
                        String name = c.getString("name");
                        String address = c.getString("address");
                        // Position node is JSON Object
                        JSONObject position = c.getJSONObject("position");
                        String lat = position.getString("lat");
                        String lng = position.getString("lng");

                        String banking = c.getString("banking");
                        String bonus = c.getString("bonus");
                        String bike_stands = c.getString("bike_stands");
                        String available_bike_stands = c.getString("available_bike_stands");
                        String status = c.getString("status");
                        String last_updated = c.getString("last_updated");




                        HashMap<String, String> hashMap = new HashMap<>();

                        hashMap.put("number", number);
                        hashMap.put("contract_name", contract_name);
                        hashMap.put("name", name);
                        hashMap.put("address", address);
                        hashMap.put("lat", lat);
                        hashMap.put("lng", lng);
                        hashMap.put("banking", banking);
                        hashMap.put("bonus", bonus);
                        hashMap.put("bike_stands", bike_stands);
                        hashMap.put("available_bike_stands", available_bike_stands);
                        hashMap.put("status", status);
                        hashMap.put("last_updated", last_updated);


                        LatLng pStation = new LatLng(Double.parseDouble(hashMap.get(lat)),Double.parseDouble(hashMap.get(lng)));
                        mMap.addMarker(new MarkerOptions().position(pStation).title(hashMap.get(contract_name)));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MapsActivity.this, "error exception", Toast.LENGTH_SHORT).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MapsActivity.this, "error listener", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(objectRequest);
        */
        String jsonStr = sh.makeServiceCall(url_stations);


        if (jsonStr != null) {
            try {

                JSONArray stations = new JSONArray(jsonStr);


                for (int i = 0; i < stations.length(); i++) {
                    JSONObject c = stations.getJSONObject(i);

                    String number = c.getString("number");
                    String contract_name = c.getString("contract_name");
                    String name = c.getString("name");
                    String address = c.getString("address");
                    // Position node is JSON Object
                    JSONObject position = c.getJSONObject("position");
                    String lat = position.getString("lat");
                    String lng = position.getString("lng");

                    String banking = c.getString("banking");
                    String bonus = c.getString("bonus");
                    String bike_stands = c.getString("bike_stands");
                    String available_bike_stands = c.getString("available_bike_stands");
                    String status = c.getString("status");
                    String last_updated = c.getString("last_updated");



                    HashMap<String, String> hashMap = new HashMap<>();

                    hashMap.put("number", number);
                    hashMap.put("contract_name", contract_name);
                    hashMap.put("name", name);
                    hashMap.put("address", address);
                    hashMap.put("lat", lat);
                    hashMap.put("lng", lng);
                    hashMap.put("banking", banking);
                    hashMap.put("bonus", bonus);
                    hashMap.put("bike_stands", bike_stands);
                    hashMap.put("available_bike_stands", available_bike_stands);
                    hashMap.put("status", status);
                    hashMap.put("last_updated", last_updated);

                    LatLng pStation = new LatLng(Double.parseDouble(hashMap.get(lat)),Double.parseDouble(hashMap.get(lng)));
                    mMap.addMarker(new MarkerOptions().position(pStation).title(hashMap.get(contract_name)));


                    // adding contact to contact list
                    stations_list.add(hashMap);

                }
            } catch (final JSONException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
        } else {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG)
                            .show();
                }
            });

        }



        // Add a marker in Sydney and move the camera
       /* LatLng amiens = new LatLng(49.894066, 2.295753);
        mMap.addMarker(new MarkerOptions().position(amiens).title("Marker in Amiens"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(amiens));*/





    }
}
