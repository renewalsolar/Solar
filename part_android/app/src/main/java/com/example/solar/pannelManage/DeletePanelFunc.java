package com.example.solar.pannelManage;

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

public class DeletePanelFunc {
    private Context context;
    private NetworkUtility networkUtility;

    public DeletePanelFunc(Context context, String panel_id){
        this.context = context;
        networkUtility = new NetworkUtility(context);

        requestgetArray(panel_id);
    }

    public void requestgetArray(String panel_id) {
        try {
            networkUtility.requestServer(
                    Config.MAIN_URL + Config.GET_PANNEL_DELETE + panel_id,
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
