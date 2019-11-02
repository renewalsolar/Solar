package com.example.solar.personManage;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.solar.network.Config;
import com.example.solar.network.NetworkUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DeleteFunc {
    private Context context;
    private NetworkUtility networkUtility;

    public DeleteFunc(Context context, String person_id){
        this.context = context;
        networkUtility = new NetworkUtility(context);

        requestgetArray(person_id);
    }

    public void requestgetArray(String person_id) {
        try {
            networkUtility.requestServer(
                    Config.MAIN_URL + Config.GET_PERSON_DELETE + person_id,
                    null,
                    networkSuccessListener(),
                    networkErrorListener());
        }catch (Exception e){
            Log.e("ERRRER",e.toString());
        }
    }

    private Response.Listener<JSONObject> networkSuccessListener() {
        return new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                Toast.makeText(context, "정상적으로 삭제 되었습니다", Toast.LENGTH_SHORT).show();
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
}
