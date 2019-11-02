package com.example.solar.pannelManage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.solar.Models.PannelInfo;
import com.example.solar.Models.UserInfo;
import com.example.solar.R;
import com.example.solar.addressApi.AddressApiActivity;
import com.example.solar.network.Config;
import com.example.solar.network.NetworkUtility;

import org.json.JSONException;
import org.json.JSONObject;

public class EditActivity extends AppCompatActivity {
    private EditText etAuth;
    private EditText etMaxoutput;
    private EditText etAddress;

    private Button searchBtn;
    private Button btnDone;
    private Button btnCancel;

    private UserInfo user;
    private PannelInfo panel;
    private int index;

    private Boolean serverAuth;

    private NetworkUtility networkUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pannel_register);

        etAuth = (EditText) findViewById(R.id.et_pannel_auth);
        etMaxoutput = (EditText) findViewById(R.id.et_pannel_maxoutput);
        etAddress = (EditText) findViewById(R.id.et_pannel_address);

        searchBtn = (Button) findViewById(R.id.btn_search_address);
        btnDone = (Button) findViewById(R.id.btn_pannel_done);
        btnCancel = (Button) findViewById(R.id.btn_pannel_cancel);

        networkUtility = new NetworkUtility(getApplicationContext());

        Intent i = getIntent();
        user = (UserInfo)i.getSerializableExtra("USER_INFO");
        panel = (PannelInfo)i.getSerializableExtra("PANNEL_INFO");
        index = (int)i.getIntExtra("INDEX",0);

        etAuth.setText(user.getId());
        etMaxoutput.setText(panel.getMaxOutput());
        etAddress.setText(panel.getAddress());

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddressApiActivity.class );
                startActivityForResult(intent, 1002);
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkSignUp()) {

                    requestPostRegister();

                    Intent result = new Intent();
                    result.putExtra("INDEX",index);
                    result.putExtra("MAXOUTPUT",etMaxoutput.getText().toString());
                    result.putExtra("ADDRESS",etAddress.getText().toString());

                    setResult(RESULT_OK, result);
                }
                else
                    Toast.makeText(getApplicationContext(),"정보를 입력 해주세요", Toast.LENGTH_SHORT).show();
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
        if (etAuth.getText().toString().length() == 0) {
            Toast.makeText(this, "소유자 id를 입력하세요!", Toast.LENGTH_SHORT).show();
            etAuth.requestFocus();
            return false;
        }

        else if (etMaxoutput.getText().toString().length() == 0) {
            Toast.makeText(this, "최대 출력량를 입력하세요!", Toast.LENGTH_SHORT).show();
            etMaxoutput.requestFocus();
            return false;
        }

        else if (etAddress.getText().toString().length() == 0) {
            Toast.makeText(this, "주소를 입력하세요!", Toast.LENGTH_SHORT).show();
            etAddress.requestFocus();
            return false;
        }
        return true;
    }

    public void requestPostRegister() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("auth_id", etAuth.getText().toString());
            jsonObject.put("maxOutput", etMaxoutput.getText().toString());
            jsonObject.put("address", etAddress.getText().toString());

            networkUtility.requestServer(Request.Method.POST,
                    Config.MAIN_URL+Config.POST_PANNEL_EDIT + panel.get_id(),
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
                        Toast.makeText(EditActivity.this, "등록 성공!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(EditActivity.this, "등록 실패 다시 시도 해 주세요.", Toast.LENGTH_SHORT).show();
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
