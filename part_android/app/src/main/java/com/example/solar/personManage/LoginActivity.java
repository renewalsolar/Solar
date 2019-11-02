package com.example.solar.personManage;

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
import com.example.solar.MainActivity;
import com.example.solar.Models.UserInfo;
import com.example.solar.R;
import com.example.solar.network.Config;
import com.example.solar.network.NetworkUtility;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText etId;
    private EditText etPw;
    private Button btnSignUp;
    private Button btnSignIn;
    private Button btnNonmember;

    private String serverMsg;
    private boolean serverAuth;

    private String name;
    private boolean hasPV;

    private NetworkUtility networkUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_login);

        etId = (EditText) findViewById(R.id.et_person_login_id);
        etPw = (EditText) findViewById(R.id.et_person_login_pw);
        btnSignIn = (Button) findViewById(R.id.btn_person_signin);
        btnSignUp = (Button) findViewById(R.id.btn_person_signup);
        btnNonmember = (Button) findViewById(R.id.btn_person_nonmember);

        networkUtility = new NetworkUtility(getApplicationContext());

        serverAuth = false;
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

        btnNonmember.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                intent.putExtra("USER_INFO", new UserInfo("NONE", "NONE", "비회원", false));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            etId.setText(data.getStringExtra("Id"));
        }
    }

    public void requestPostLogin() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", etId.getText().toString());
            jsonObject.put("password", etPw.getText().toString());

            networkUtility.requestServer(Request.Method.POST,
                    Config.MAIN_URL + Config.POST_LOGIN,
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

                Log.e("ERRRRRRR",response.toString());
                try {
                    serverMsg = response.getString("message");
                    serverAuth = response.getBoolean("success");
                    name = response.getString("name");
                    hasPV = response.getBoolean("hasPV");

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), serverMsg, Toast.LENGTH_LONG).show();
                } finally {
                    Toast.makeText(getApplicationContext(), serverMsg, Toast.LENGTH_LONG).show();
                }
                if (serverAuth) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("USER_INFO", new UserInfo(etId.getText().toString(), etPw.getText().toString(), name, hasPV));
                    startActivity(intent);
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
