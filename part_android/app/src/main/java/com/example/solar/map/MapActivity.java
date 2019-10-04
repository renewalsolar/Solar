package com.example.solar.map;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.solar.MainActivity;
import com.example.solar.Models.UserInfo;
import com.example.solar.R;
import com.example.solar.network.Config;
import com.example.solar.network.NetworkUtility;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {
    private MapView mapView;

    private NetworkUtility networkUtility;
    private List<String> addresses = new ArrayList<>();


    double[] lat = {0, 40, 50, 60};
    double[] lon = {40, 120, 130, 110};
    int i = 0;

    double max1;
    double min1;

    TextView txt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Mapbox.getInstance(this, "pk.eyJ1IjoiYW55dGltZTk2IiwiYSI6ImNqdzhoN2FmdTF2NXk0YXA5NWNrZzhlZG0ifQ.smgy-n2TfOl4cOo8PcTGdA");
        setContentView(R.layout.activity_map);

        txt = (TextView)findViewById(R.id.text2);

        setupMapView(savedInstanceState);


    }

    private void setupMapView(Bundle savedInstanceState) {
        mapView = findViewById(R.id.mapView);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        for(int i = 0; i<lat.length; i++)
                        {
                            mapboxMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(lat[i],lon[i])));
                        }
                        mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                            @Override
                            public boolean onMapClick(@NonNull LatLng point) {
                                return false;
                            }
                        });
                    }
                });

                mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        new JSONParse().execute(marker.getPosition().getLatitude(),marker.getPosition().getLongitude());
                        txt.setText("최고="+ String.format("%.01f", max1)+ "도, "+"최저="+ String.format("%.01f", min1)+"도");
                        return false;
                    }
                });
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    private class MarkerViewManager {
        public MarkerViewManager(MapView mapView, MapboxMap mapboxMap) {
        }

        public void addMarker(MarkerView markerView,  MapboxMap mapboxMap) {
        }

        public void onDestroy() {
        }
    }

    private class JSONParse extends AsyncTask<Double, Double, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(Double... params) {

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                JSONObject mainArray = (JSONObject) json.get("main");
                double max = Double.parseDouble(mainArray.get("temp_max").toString());
                double min = Double.parseDouble(mainArray.get("temp_min").toString());

                max1 = max;
                min1 = min;
                i++;
            } catch (Exception e) {
            }
        }
    }

    public void requestgetArray() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", "q");

            networkUtility.requestServer(
                    Config.MAIN_URL + Config.GET_PANNEL_INFO,
                    networkSuccessListener(),
                    networkErrorListener());

        } catch (JSONException e) {
            throw new IllegalStateException("Failed to convert the object to JSON");
        }
    }

    private Response.Listener<JSONArray> networkSuccessListener() {
        return new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                getPannels(response);
            }
        };
    }



    private Response.ErrorListener networkErrorListener() {
        return new Response.ErrorListener() {

            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }


    private void getPannels(JSONArray response) {
        Log.e("ERRRRRRR", response.toString());
        try {
            JSONObject jresponse;
            for (int i = 0; i < response.length(); i++) {
                jresponse = response.getJSONObject(i);
                addresses.add(jresponse.getString("address"));
            }

        } catch (JSONException e) {
            throw new IllegalArgumentException("Failed to parse the String: ");
        }
    }
}
