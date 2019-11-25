package com.example.solar.subThread;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.solar.Models.UserInfo;
import com.example.solar.network.Config;
import com.example.solar.network.NetworkUtility;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PowerGraph {

    private UserInfo user;
    private Context context;
    private LineChart lineChart;
    private TextView won;
    private TextView maked_charge;

    private NetworkUtility networkUtility;

    private Thread thread;
    private List<ArrayList<String>> lists;
    private LineData lineData;

    private float sumOfDays = 0;
    private int purchasePrice, dayCnt = 0;

    public PowerGraph(final Context context, final LineChart lineChart, final TextView won,
                      UserInfo user, final TextView maked_charge) {
        this.context = context;
        this.lineChart = lineChart;
        this.user = user;
        this.won = won;
        this.maked_charge = maked_charge;

        networkUtility = new NetworkUtility(context);
        lists = new ArrayList<ArrayList<String>>();
        lineData = new LineData();

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

    public void requestGetPower() {
        networkUtility.requestServer(
                Config.MAIN_URL + Config.GET_PANNEL_GRAPH + user.getId(),
                networkSuccessListener(),
                networkErrorListener());
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
        try {
            JSONObject jresponse;

            for (int i = 0; i < response.length(); i++) {
                jresponse = response.getJSONObject(i); //판넬 별

                JSONArray jsonArray = jresponse.getJSONArray("dayOutput");

                ArrayList<String> data = new ArrayList<>();

                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(j);
                    data.add(jsonObject.getString("output")); // 값
                }
                lists.add(data);
            }

        } catch (JSONException e) {
            throw new IllegalArgumentException("Failed to parse the String: ");
        } finally {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mHandler.sendEmptyMessage(2);
                }
            });
        }
    }

    public void sendMsg3(int purPrsVar) {
        this.purchasePrice = purPrsVar;

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(3);
            }
        });
    }

    public int calcRate(double kWh) {
        float multiVal;
        if (kWh <= 100) {
            multiVal = 60.7f;
        } else if (kWh > 100 && kWh <= 200) {
            multiVal = 125.9f;
        } else if (kWh > 200 && kWh <= 300) {
            multiVal = 187.9f;
        } else if (kWh > 300 && kWh <= 400) {
            multiVal = 280.6f;
        } else if (kWh > 400 && kWh <= 500) {
            multiVal = 417.7f;
        } else { // kWh > 500
            multiVal = 709.5f;
        }
        return (int) Math.round(kWh * multiVal);
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 2) {
                lineChart.invalidate();
                lineChart.clear();

                for (int i = 0; i < lists.size(); i++) {
                    ArrayList<Entry> values = new ArrayList<>();
                    ArrayList<String> data = lists.get(i);

                    for (int j = 0; j < data.size(); j++) {
                        sumOfDays += Float.parseFloat(data.get(j));
                        dayCnt++;
                        values.add(new Entry(j, Math.round(Float.parseFloat(data.get(j)))));
                    }

                    LineDataSet lineDataSet = new LineDataSet(values, "panel of " + i);

                    lineData.addDataSet(lineDataSet);
                    lineData.setValueTextSize(9);
                    lineChart.setData(lineData);
                }

                maked_charge.setText(String.valueOf(new DecimalFormat("#,###").format(
                                calcRate(sumOfDays / 3600 * 93.3)) + "원"));
            } else if (msg.what == 3) {
                // 상환일 = 원금 / 월 발전량
                int repayMonths = Math.round(purchasePrice / (sumOfDays / (float) dayCnt * 30));
                // layout update
                won.setText(String.valueOf(repayMonths) + " 달");
            }
        }
    };
}