package com.example.solar.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.solar.R;
import com.example.solar.UnityPlayerActivity;

public class EcoHelper extends Fragment {

    public EcoHelper() {
        // Required empty public constructor
    }

    Button btn_ar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_green, container, false);
        btn_ar = (Button)v.findViewById(R.id.arbutton);

        btn_ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UnityPlayerActivity.class);
                startActivity(intent);
                //Toast.makeText(getContext(),"추후 기능 추가 예정",Toast.LENGTH_LONG).show();
            }
        });

        // Inflate the layout for this fragment
        return v;

    }

}
