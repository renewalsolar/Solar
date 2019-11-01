package com.example.solar.personManage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    private EditText etUserName;
    private EditText etAddress;
    private EditText etId;
    private EditText etPassword;
    private EditText etPasswordConfirm;
    private Button btnDone;
    private Button btnCancel;

    private Boolean serverAuth;

    private NetworkUtility networkUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_register);

        etUserName = (EditText) findViewById(R.id.et_person_register_name);
        etAddress = (EditText) findViewById(R.id.et_person_register_address);
        etId = (EditText) findViewById(R.id.et_person_register_id);
        etPassword = (EditText) findViewById(R.id.et_person_register_pw);
        etPasswordConfirm = (EditText) findViewById(R.id.et_person_register_pwconfirm);

        btnDone = (Button) findViewById(R.id.btn_person_register_done);
        btnCancel = (Button) findViewById(R.id.btn_person_register_cancel);

        networkUtility = new NetworkUtility(getApplicationContext());

        // 비밀번호 일치 검사
        etPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = etPassword.getText().toString();
                String confirm = etPasswordConfirm.getText().toString();

                if (password.equals(confirm)) {
                    etPassword.setBackgroundColor(Color.GREEN);
                    etPasswordConfirm.setBackgroundColor(Color.GREEN);
                } else {
                    etPassword.setBackgroundColor(Color.RED);
                    etPasswordConfirm.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkSignUp()) {
                    requestPostRegister();

                    Intent result = new Intent();
                    result.putExtra("Id", etId.getText().toString());

                    // 자신을 호출한 Acstivity로 데이터를 보낸다.
                    setResult(RESULT_OK, result);
                    finish();
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
        if (etUserName.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "이름을 입력하세요!", Toast.LENGTH_SHORT).show();
            etUserName.requestFocus();
            return false;
        }

        //생년월일 입력 확인
        else if (etAddress.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "생년월일을 입력하세요!", Toast.LENGTH_SHORT).show();
            etAddress.requestFocus();
            return false;
        }

        // 아이디 입력 확인
        else if (etId.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "아이디를 입력하세요!", Toast.LENGTH_SHORT).show();
            etId.requestFocus();
            return false;
        }

        // 비밀번호 입력 확인
        else if (etPassword.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return false;
        }

        // 비밀번호 확인 입력 확인
        else if (etPasswordConfirm.getText().toString().length() == 0) {
            Toast.makeText(RegisterActivity.this, "비밀번호 확인을 입력하세요!", Toast.LENGTH_SHORT).show();
            etPasswordConfirm.requestFocus();
            return false;
        }

        // 비밀번호 일치 확인
        else if (!etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
            etPassword.setText("");
            etPasswordConfirm.setText("");
            etPassword.requestFocus();
            return false;
        }

        return true;
    }

    public void requestPostRegister() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", etId.getText().toString());
            jsonObject.put("password", etPasswordConfirm.getText().toString());
            jsonObject.put("name", etUserName.getText().toString());
            jsonObject.put("address", etAddress.getText().toString());


            networkUtility.requestServer(Request.Method.POST,
                    Config.MAIN_URL+Config.POST_REGISTER,
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
                        Toast.makeText(RegisterActivity.this, "가입 성공!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "가입 실패 다시 시도 해 주세요.", Toast.LENGTH_SHORT).show();
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
