package com.example.solar.Models;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private String id;
    private String pw;
    private boolean hasPV;

    public UserInfo(String id, String pw, boolean hasPV){
        this.id = id;
        this.pw = pw;
        this.hasPV = hasPV;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public boolean isHasPV() {
        return hasPV;
    }
}
