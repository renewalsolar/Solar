package com.example.solar.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.solar.R;
import com.example.solar.UnityPlayerActivity;
import com.example.solar.map.MapActivity;

public class EcoHelper extends Fragment {

    public EcoHelper() {
        // Required empty public constructor
    }

    Button btn_ar;
    Button btn_jiwon;
    Button btn_upchae;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_green, container, false);

        btn_ar = (Button)v.findViewById(R.id.arbutton);
        btn_jiwon = (Button)v.findViewById(R.id.btn_jiwon);
        btn_upchae = (Button)v.findViewById(R.id.btn_upchae);


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
                String url ="https://www.gov.kr/search/news/local?srhQuery=%ED%83%9C%EC%96%91%EA%B4%91+%EC%84%A4%EC%B9%98+%EC%A7%80%EC%9B%90&policyType=&sort=&dateDvs=&sdate=&edate=&sfield=";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        btn_upchae.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String url ="http://search.danawa.com/dsearch.php?query=%ED%83%9C%EC%96%91%EA%B4%91%ED%8C%A8%EB%84%90&originalQuery=%ED%83%9C%EC%96%91%EA%B4%91%ED%8C%A8%EB%84%90&cate_c1=57906&volumeType=allvs&page=1&limit=30&sort=saveDESC&list=list&boost=true&addDelivery=N&tab=main&tab=main";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });



        // Inflate the layout for this fragment
        return v;

    }

}
