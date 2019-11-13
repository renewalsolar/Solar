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
import com.example.solar.map.MapActivity;
import com.example.solar.pannelManage.PersonnalAdapter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class HasPV extends Fragment {

    private Button btn_map;
    private LineChart lineChart;
    private TextView tv_generator;

    private PersonalGeneration personalGeneration;

    private UserInfo user;

    public HasPV(UserInfo user){
        this.user = user;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_installed, container, false);

        tv_generator = v.findViewById(R.id.realtime_elec);

        personalGeneration = new PersonalGeneration(getContext(), tv_generator, user);
        // Inflate the layout for this fragment

        btn_map = (Button)v.findViewById(R.id.btn_map);
        lineChart = (LineChart)v.findViewById(R.id.linechart);

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapActivity.class);
                startActivity(intent);
                //Toast.makeText(getContext(),"추후 기능 추가 예정",Toast.LENGTH_LONG).show();
            }
        });
        
        lineChart.invalidate(); //차트 초기화 작업
        lineChart.clear();

        ArrayList<Entry> values = new ArrayList<>();//차트 데이터 셋에 담겨질 데이터

        values.add(new Entry(3, 35000));
        values.add(new Entry(4, 27850));
        values.add(new Entry(5, 42650));
        values.add(new Entry(6, 41550));

        /*몸무게*/
        LineDataSet lineDataSet = new LineDataSet(values, "요금"); //LineDataSet 선언

        LineData lineData = new LineData(); //LineDataSet을 담는 그릇 여러개의 라인 데이터가 들어갈 수 있습니다.
        lineData.addDataSet(lineDataSet);

        lineData.setValueTextSize(9);
        lineChart.setData(lineData);



        return v;
    }

    public void threadStop (){
        personalGeneration.threadStop();
    }
}
