package com.example.solar.SubThread;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.solar.MainActivity;
import com.example.solar.Models.UserInfo;
import com.example.solar.network.Config;
import com.example.solar.network.NetworkUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PersonalGeneration {

    private UserInfo user;

    private Context context;
    private TextView tv_generation;

    private NetworkUtility networkUtility;
    private String value;

    private Thread thread;


    public PersonalGeneration(final Context context, final TextView tv_generation, UserInfo user) {
        this.context = context;
        this.tv_generation = tv_generation;
        this.user = user;

        networkUtility = new NetworkUtility(context);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // run Thread
        thread = new Thread(new Runnable() {
            public void run() {
                Looper.prepare();
                requestGetPower();

                Looper.loop();
            }
        });

        thread.start();
    }

    public void threadStop(){
        thread.interrupt();
    }



    public void requestGetPower() {
        try {
            while(true) {
                networkUtility.requestServer(
                        Config.MAIN_URL + Config.GET_PANNEL_PERSONAL + user.getId(),
                        networkSuccessListener(),
                        networkErrorListener());
                Thread.sleep(5 * 1000);
            }
        }catch (InterruptedException e){

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
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void getPannels(JSONArray response) {
        Log.e("ERRRRRRR", response.toString());
        try {
            JSONObject jresponse;
            value = "";

            for (int i = 0; i < response.length(); i++) {
                jresponse = response.getJSONObject(i);
                float tval = Float.parseFloat( jresponse.getJSONArray("dayOutput").getJSONObject(0).getString("output"));
                tval = Math.round(tval * 100) / 100.0000f;
                Log.e("ERRRRRRR", String.valueOf(tval));

                value += tval + "Wh\n";
            }

        } catch (JSONException e) {
            throw new IllegalArgumentException("Failed to parse the String: ");
        }finally {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(1);
                }
            });
        }
    }


    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                tv_generation.setText(value);
            }
        }
    };


}
