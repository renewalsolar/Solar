package com.example.solar.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.solar.SubThread.PredictGeneration;
import com.example.solar.R;
import com.example.solar.UnityPlayerActivity;
import com.example.solar.crawling.BusinessParsingActivity;
import com.example.solar.crawling.PanelParsingActivity;

public class DontHasPV extends Fragment {

    public DontHasPV() {
        // Required empty public constructor
    }

    Button btn_ar;
    Button btn_jiwon;
    Button btn_upchae;
    TextView curpos;
    TextView predictbenefit;
    ImageView loading_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_noninstalled, container, false);
        // Inflate the layout for this fragment

        btn_ar = (Button)v.findViewById(R.id.arbutton);
        btn_jiwon = (Button)v.findViewById(R.id.btn_jiwon);
        btn_upchae = (Button)v.findViewById(R.id.btn_upchae);
        loading_view = (ImageView) v.findViewById(R.id.loading_view);
        curpos = (TextView) v.findViewById(R.id.curpos);
        predictbenefit = (TextView) v.findViewById(R.id.predictbenefit);

        Glide.with(this).load(R.drawable.loading).into(loading_view);
        new PredictGeneration(v.getContext(), curpos, predictbenefit, loading_view);

        btn_ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UnityPlayerActivity.class);
                startActivity(intent);
                //Toast.makeText(getContext(),"추후 기능 추가 예정",Toast.LENGTH_LONG).show();
            }
        });

        btn_jiwon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BusinessParsingActivity.class);
                startActivity(intent);
            }
        });

        btn_upchae.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PanelParsingActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }
}
