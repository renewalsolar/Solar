package com.example.solarmap;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private MapView mapView;

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
        setContentView(R.layout.activity_main);

        txt = (TextView)findViewById(R.id.text2);
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
                        txt.setText("최고="+String.format("%.01f", max1)+ "도, "+"최저="+String.format("%.01f", min1)+"도");
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
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        protected JSONObject doInBackground(Double... params) {
            String url = "http://api.openweathermap.org/data/2.5/weather?lat=";
            url += params[0];
            url += "&lon=";
            url += params[1];
            url += "&units=metric&APPID=fa33af9f465ae60cdbc59761fb08da1d";
            HttpURLConnection con = null;
            try {
                URL myurl = new URL(url);
                con = (HttpURLConnection) myurl.openConnection();
                int response = con.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    StringBuilder builder = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return new JSONObject(builder.toString());
                } else {
                    Log.e("TAG-error", "Connection Error!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                con.disconnect();
            }
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
}
