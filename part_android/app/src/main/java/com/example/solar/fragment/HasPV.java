package com.example.solar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.solar.Models.UserInfo;
import com.example.solar.R;
import com.example.solar.SubThread.PersonalGeneration;
import com.example.solar.SubThread.PowerGraph;
import com.example.solar.map.MapActivity;
import com.github.mikephil.charting.charts.LineChart;

public class HasPV extends Fragment {

    private Button btn_map;
    private LineChart lineChart;
    private TextView tv_generator;
    private TextView won;

    private PersonalGeneration personalGeneration;
    private PowerGraph powerGraph;

    private UserInfo user;

    public HasPV(UserInfo user){
        this.user = user;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_installed, container, false);

        tv_generator = v.findViewById(R.id.realtime_elec);
        lineChart = (LineChart)v.findViewById(R.id.linechart);

        btn_map = (Button)v.findViewById(R.id.btn_map);
        won = (TextView)v.findViewById(R.id.won);

        personalGeneration = new PersonalGeneration(getContext(), tv_generator, user);
        powerGraph = new PowerGraph(getContext(), lineChart, won, user);

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    public void threadStop (){
        personalGeneration.threadStop();
    }
}
