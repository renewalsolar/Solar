package com.example.solar.map;

import android.annotation.TargetApi;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
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
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.markerview.MarkerView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

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
    private List<String> addresses;
    private List<String> outputs;
    private List<String> maxOutputs;

    double latitude = 0;
    double longitude = 0;

    TextView txt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        networkUtility = new NetworkUtility(getApplicationContext());
        addresses = new ArrayList<>();
        maxOutputs = new ArrayList<>();
        outputs = new ArrayList<>();

        Mapbox.getInstance(this, "pk.eyJ1IjoiYW55dGltZTk2IiwiYSI6ImNqdzhoN2FmdTF2NXk0YXA5NWNrZzhlZG0ifQ.smgy-n2TfOl4cOo8PcTGdA");
        setContentView(R.layout.activity_map);

        txt = (TextView)findViewById(R.id.txt);

        setupMapView(savedInstanceState);
        new JSONParse().execute();
    }

    public void convertToXY(String address)
    {
        final Geocoder geocoder = new Geocoder(this);

        List<Address> list = null;

        try {
            list = geocoder.getFromLocationName
                    (address, // 지역 이름
                            10); // 읽을 개수
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
        }

        if (list != null) {
                // 해당되는 주소로 인텐트 날리기
                Address addr = list.get(0);
                latitude = addr.getLatitude();
                longitude = addr.getLongitude();
        }
    }

    private void setupMapView(Bundle savedInstanceState) {
        mapView = findViewById(R.id.mapView);
        MapboxMapOptions options = new MapboxMapOptions()
                .camera(new CameraPosition.Builder()
                        .target(new LatLng(35.95, 128))
                        .zoom(6.5)
                        .build());

        mapView = new MapView(this, options);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {

                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        for(int i = 0; i<addresses.size(); i++)
                        {
                            convertToXY(addresses.get(i));
                            mapboxMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(latitude,longitude))
                                    .title("주소 : " + addresses.get(i) + "\n" +
                                            "최대 출력 : "+maxOutputs.get(i)+ "\n" +
                                            "오늘 실시간 발전량 : "+outputs.get(i)));

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
                        //new JSONParse().execute(marker.getPosition().getLatitude(),marker.getPosition().getLongitude());
                        txt.setText(marker.getTitle());
                        return false;
                    }
                });
            }
        });
        setContentView(mapView);
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

    private class JSONParse extends AsyncTask<Double, Double, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            requestgetArray();
        }

        @Override
        protected JSONObject doInBackground(Double... params) {

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

        }
    }

    public void requestgetArray() {
        try {
            networkUtility.requestServer(
                    Config.MAIN_URL + Config.GET_PANNEL_INFO,
                    networkSuccessListener(),
                    networkErrorListener());

        }catch (Exception e){
            Log.e("ERRRER",e.toString());
        }
    }

    private Response.Listener<JSONArray> networkSuccessListener() {
        return new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                Log.e("ERRRRRRRRR",response.toString() + "HERE");
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
                maxOutputs.add(jresponse.getString("maxOutput"));
                outputs.add(jresponse.getJSONArray("dayOutput").getJSONObject(0).getString("output"));
            }

        } catch (JSONException e) {
            throw new IllegalArgumentException("Failed to parse the String: ");
        }
    }
}
/*[{"dayOutput":[{"output":"3333"}],"address":"서울 강남구 압구정로 102 형지제2빌딩"}
,{"dayOutput":[{"output":"0"}],"address":"서울 서대문구 홍제동 285-14 "}]*/