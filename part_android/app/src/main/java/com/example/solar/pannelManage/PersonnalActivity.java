package com.example.solar.pannelManage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.solar.Models.PannelInfo;
import com.example.solar.Models.UserInfo;
import com.example.solar.R;
import com.example.solar.network.Config;
import com.example.solar.network.NetworkUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PersonnalActivity extends AppCompatActivity {

    private UserInfo user;
    private NetworkUtility networkUtility;
    private List<PannelInfo> pannels;
    private PersonnalAdapter personnalAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        Intent i = getIntent();
        user = (UserInfo)i.getSerializableExtra("USER_INFO");

        networkUtility = new NetworkUtility(getApplicationContext());
        pannels = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        new JSONParse().execute();
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
            personnalAdapter = new PersonnalAdapter(pannels, user);
            mRecyclerView.setAdapter(personnalAdapter);
            personnalAdapter.notifyDataSetChanged();
        }
    }


    public void requestgetArray() {
        try {
            networkUtility.requestServer(
                    Config.MAIN_URL + Config.GET_PANNEL_INFO + user.getId(),
                    networkSuccessListener(),
                    networkErrorListener());

        }catch (Exception e){
            Log.e("ERRRER",e.toString());
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
        try {
            JSONObject jresponse;

            for (int i = 0; i < response.length(); i++) {
                jresponse = response.getJSONObject(i);
                pannels.add(new PannelInfo(jresponse.getString("_id"),
                        jresponse.getString("maxOutput"),
                        jresponse.getString("address")));

                personnalAdapter.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            throw new IllegalArgumentException("Failed to parse the String: ");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1004 && resultCode == RESULT_OK) {
            int index = data.getIntExtra("INDEX",0);
            String maxOutput = data.getStringExtra("MAXOUTPUT");
            String address = data.getStringExtra("ADDRESS");

            pannels.get(index).setMaxOutput(maxOutput);
            pannels.get(index).setAddress(address);

            personnalAdapter.notifyItemChanged(index);
        }
    }
}
