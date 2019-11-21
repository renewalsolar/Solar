package com.example.solar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.solar.Models.UserInfo;
import com.example.solar.R;
import com.example.solar.subThread.PersonalGeneration;
import com.example.solar.subThread.PowerGraph;
import com.example.solar.map.MapActivity;
import com.github.mikephil.charting.charts.LineChart;

public class HasPV extends Fragment {

    private Button btn_map;
    private Button btn_price;
    private LineChart lineChart;
    private EditText purchase_price;
    private TextView tv_generator;
    private TextView won;
    private TextView maked_charge;

    private PersonalGeneration personalGeneration;
    private PowerGraph powerGraph;

    private UserInfo user;

    private int purchasePrice;

    public HasPV(UserInfo user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_installed, container, false);

        tv_generator = v.findViewById(R.id.realtime_elec);
        lineChart = (LineChart) v.findViewById(R.id.linechart);

        btn_map = (Button) v.findViewById(R.id.btn_map);
        btn_price = (Button) v.findViewById(R.id.btn_price);
        purchase_price = (EditText) v.findViewById(R.id.purchase_price);
        won = (TextView) v.findViewById(R.id.won);
        maked_charge = (TextView) v.findViewById(R.id.maked_charge);

        personalGeneration = new PersonalGeneration(getContext(), tv_generator, user);
        powerGraph = new PowerGraph(getContext(), lineChart, won, user, maked_charge);

        won.setVisibility(View.GONE);

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapActivity.class);
                startActivity(intent);
            }
        });

        btn_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMonth();
            }
        });

        return v;
    }

    public void threadStop() {
        personalGeneration.threadStop();
    }

    public void showMonth() {
        // 구매가를 입력하지 않으면
        if (purchase_price.getText().toString().equals(""))
            Toast.makeText(getContext(), "구매가를 입력해주세요", Toast.LENGTH_SHORT).show();
        else // 구매가 입력 시
                powerGraph.sendMsg3(
                        Integer.parseInt(purchase_price.getText().toString()));

        purchase_price.setVisibility(View.GONE);
        btn_price.setVisibility(View.GONE);
        won.setVisibility(View.VISIBLE);
    }
}
