package com.example.solar.fragment;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.solar.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class CalcHelper extends Fragment {

    double pwr_qty;
    double money;
    // Inflate the layout for this fragment
    TextView text_qty;
    TextView text_bill;
    TextView text_won;
    TextView text_end;
    ImageView img_nu;
    public CalcHelper() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*JSONObject info_realtime = null;
        try {
            pwr_qty = info_realtime.getDouble("pwr_qty"); // 15분단위 전력 사용량
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        View v = inflater.inflate(R.layout.activity_calc, container, false);

        text_qty = (TextView)v.findViewById(R.id.qty);
        img_nu = (ImageView)v.findViewById(R.id.img_nu);
        text_bill = (TextView)v.findViewById(R.id.bill);
        text_won = (TextView)v.findViewById(R.id.won);
        text_end = (TextView)v.findViewById(R.id.end);

        pwr_qty = 472.2;
        money = (300 * 93.3) + (150 * 187.9) + (22.2 * 280.6);


        text_qty.setText(Double.toString(pwr_qty) + "kWh");
        if (pwr_qty < 300) {
            img_nu.setImageResource(R.drawable.nu1); // 93.3원
        }
        else if(pwr_qty < 450){
            img_nu.setImageResource(R.drawable.nu2); // 187.9원
        }else{
            img_nu.setImageResource(R.drawable.nu3); // 280.6원
        }

        text_bill.setText("편상진 님의 7월 청구금액은");
        text_won.setText(Double.toString(money) + "원");
        text_end.setText("입니다.");

        return v;
    }
}