package com.example.solar.Models;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private String id;
    private String pw;
    private String name;
    private boolean hasPV;


    public UserInfo(String id, String pw, String name, boolean hasPV) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.hasPV = hasPV;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getName() {
        return name;
    }

    public boolean isHasPV() {
        return hasPV;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHasPV(boolean hasPV) {
        this.hasPV = hasPV;
    }
}
