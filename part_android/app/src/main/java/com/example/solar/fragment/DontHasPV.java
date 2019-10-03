package com.example.solar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.solar.R;

public class DontHasPV extends Fragment {

    public DontHasPV() {
        // Required empty public constructor
    }

    Button btn_ar;
    Button btn_jiwon;
    Button btn_upchae;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.installnope, container, false);
        // Inflate the layout for this fragment

        return v;

    }
}
