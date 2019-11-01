package com.example.solar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.solar.R;
import com.example.solar.map.MapActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class HasPV extends Fragment {

    public HasPV() {
        // Required empty public constructor
    }

    Button btn_map;
    LineChart lineChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_installed, container, false);
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

//        money = (300 * 93.3) + (150 * 187.9) + (22.2 * 280.6);
//
//        text_qty.setText(Double.toString(pwr_qty) + "kWh");
//        if (pwr_qty < 300) {
//            img_nu.setImageResource(R.drawable.nu1); // 93.3원
//        }
//        else if(pwr_qty < 450){
//            img_nu.setImageResource(R.drawable.nu2); // 187.9원
//        }else{
//            img_nu.setImageResource(R.drawable.nu3); // 280.6원
//        }

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
}
