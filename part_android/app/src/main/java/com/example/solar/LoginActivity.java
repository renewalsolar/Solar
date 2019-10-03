package com.example.solar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.solar.network.Config;
import com.example.solar.network.NetworkUtility;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText etId;
    private EditText etPw;
    private Button btnSignUp;
    private Button btnSignIn;
    private Button btnNlogIn;

    private String serverMsg;
    private String userId;
    private boolean serverAuth;
    private boolean hasPV;

    private NetworkUtility networkUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        networkUtility = new NetworkUtility(getApplicationContext());

        //int resnum = getResources().getIdentifier("bibimbab","drawable","com.example.pyeonsangjin.ssmproject");

        etId = (EditText) findViewById(R.id.etId);
        etPw = (EditText) findViewById(R.id.etPassword);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignIn = (Button) findViewById(R.id.btnSignin);
        btnNlogIn = (Button) findViewById(R.id.nologin);

        serverAuth= false;
        btnSignUp.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                // intent를 보내면서 다음 액티비티로부터 데이터를 받기 위해 식별번호(1000)을 준다.
                startActivityForResult(intent, 1000);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                requestPostLogin();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // setResult를 통해 받아온 요청번호, 상태, 데이터
        Log.d("RESULT", requestCode + "");
        Log.d("RESULT", resultCode + "");
        Log.d("RESULT", data + "");

        if(requestCode == 1000 && resultCode == RESULT_OK) {
            etId.setText(data.getStringExtra("Id"));
        }
    }

    public void requestPostLogin()
    {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", etId.getText().toString());
            jsonObject.put("password", etPw.getText().toString());

            networkUtility.requestServer(Request.Method.POST,
                    Config.MAIN_URL+ Config.POST_LOGIN,
                    jsonObject,
                    networkSuccessListener(),
                    networkErrorListener());

        } catch (JSONException e) {
            throw new IllegalStateException("Failed to convert the object to JSON");
        }
    }

    private Response.Listener<JSONObject> networkSuccessListener() {
        return new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {

                try {
                    serverMsg = response.getString("message");
                    serverAuth = response.getBoolean("success");
                    hasPV = response.getBoolean("hasPV");
                } catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), serverMsg, Toast.LENGTH_LONG).show();
                    //throw new IllegalArgumentException("Failed to parse the String: " + serverMsg);
                }
                finally {
                    Toast.makeText(getApplicationContext(), serverMsg, Toast.LENGTH_LONG).show();
                }

                if(serverAuth) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    userId = etId.getText().toString();
                    intent.putExtra("id", userId);
                    intent.putExtra("auth",serverAuth);
                    startActivityForResult(intent, 1001);
                    finish();
                }
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
}
