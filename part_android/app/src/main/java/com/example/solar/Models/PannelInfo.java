package com.example.solar.Models;

import java.io.Serializable;

public class PannelInfo implements Serializable{
    private String _id;
    private String auth_id;
    private String maxOutput;
    private String dayOutput[];
    private String address;

    public PannelInfo(String _id, String auth_id, String maxOutput, String[] dayOutput, String address){
        this._id = _id;
        this.auth_id = auth_id;
        this.maxOutput = maxOutput;
        this.dayOutput = dayOutput;
        this.address = address;
    }

    public PannelInfo(String _id, String maxOutput, String address){
        this._id = _id;
        this.maxOutput = maxOutput;
        this.address = address;
        this.auth_id = null;
        this.dayOutput = null;
    }

    public String get_id() {
        return _id;
    }

    public String getAuth_id() {
        return auth_id;
    }

    public String getMaxOutput() {
        return maxOutput;
    }

    public String[] getDayOutput() {
        return dayOutput;
    }

    public String getAddress() {
        return address;
    }
}


