package com.example.solar.network;

public class Config {
    public final static String MAIN_URL = "http://202.31.201.221:3001/";

    public final static String POST_REGISTER = "api/person/register/";
    public final static String POST_LOGIN = "api/person/login/";

    public final static String POST_PANNEL_REGISTER = "api/pannel/register/";
    public final static String POST_PANNEL_UPDATE = "api/pannel/update/"; // + pannel_id
    public final static String GET_PANNEL_INFO = "api/pannel/info/"; // + auth_id
    public final static String POST_PANNEL_EDIT = "api/pannel/edit/"; // + pannel_id
    public final static String GET_PANNEL_DELETE = "api/pannel/delete/"; // + pannel_id

    public final static String GET_MAP_PHP = "api/map/";
}