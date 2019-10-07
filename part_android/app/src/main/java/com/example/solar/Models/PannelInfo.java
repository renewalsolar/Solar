package com.example.solar.Models;

import java.io.Serializable;

public class PannelInfo implements Serializable{
    private String _id;
    private String auth_id;
    private String maxOutput;
    private String dayOutput[];
    private String address;

    public PannelInfo(String id, String pw, boolean hasPV){

    }
}


