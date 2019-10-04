package com.example.solar.pannelManage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.solar.R;
import com.example.solar.network.Config;
import com.example.solar.network.NetworkUtility;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private EditText etAuth;
    private EditText etMaxoutput;
    private EditText etlanX;
    private EditText etlanY;

    private Button btnDone;
    private Button btnCancel;

    private Boolean serverAuth;

    private NetworkUtility networkUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pannel_register);

        etAuth = (EditText) findViewById(R.id.et_pannel_auth);
        etMaxoutput = (EditText) findViewById(R.id.et_pannel_maxoutput);
        etlanX = (EditText) findViewById(R.id.et_pannel_lanx);
        etlanY = (EditText) findViewById(R.id.et_pannel_lany);

        btnDone = (Button) findViewById(R.id.btn_pannel_done);
        btnCancel = (Button) findViewById(R.id.btn_pannel_cancel);

        networkUtility = new NetworkUtility(getApplicationContext());

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkSignUp()) {
                    requestPostRegister();
                }
                else
                    Toast.makeText(getApplicationContext(),"회원가입 정보를 해주세요", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public boolean checkSignUp() {
        //유저이름 입력 확인
        if (etAuth.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "소유자 id를 입력하세요!", Toast.LENGTH_SHORT).show();
            etAuth.requestFocus();
            return false;
        }

        //생년월일 입력 확인
        else if (etMaxoutput.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "최대 출력량를 입력하세요!", Toast.LENGTH_SHORT).show();
            etMaxoutput.requestFocus();
            return false;
        }

        // 아이디 입력 확인
        else if (etlanX.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "X좌표를 입력하세요!", Toast.LENGTH_SHORT).show();
            etlanX.requestFocus();
            return false;
        }

        // 비밀번호 입력 확인
        else if (etlanY.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "Y좌표를 입력하세요!", Toast.LENGTH_SHORT).show();
            etlanY.requestFocus();
            return false;
        }

        return true;
    }

    public void requestPostRegister() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("auth_id", etAuth.getText().toString());
            jsonObject.put("maxOutput", etMaxoutput.getText().toString());
            jsonObject.put("lanx", etlanX.getText().toString());
            jsonObject.put("lany", etlanY.getText().toString());


            networkUtility.requestServer(Request.Method.POST,
                    Config.MAIN_URL+Config.POST_PANNEL_REGISTER,
                    jsonObject,
                    networkSuccessListener(),
                    networkErrorListener());
        }
        catch (JSONException e) {
            throw new IllegalStateException("Failed to convert the object to JSON");
        }
    }

    private Response.Listener<JSONObject> networkSuccessListener() {
        return new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {

                try {
                    serverAuth = response.getBoolean("success");

                } catch (JSONException e){
                    throw new IllegalArgumentException("Failed to parse the String");
                }

                finally {
                    if(serverAuth){
                        Toast.makeText(RegisterActivity.this, "등록 성공!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "등록 실패 다시 시도 해 주세요.", Toast.LENGTH_SHORT).show();
                    }
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
