package com.example.solar.network;

public class Config {
    public final static String MAIN_URL = "http://192.168.0.2:3001/";
    public final static String POST_REGISTER = "api/person/register/";
    public final static String POST_LOGIN = "api/person/login/";
    public final static String POST_PERSON_EDIT = "api/person/edit/"; //+person_id
    public final static String GET_PERSON_DELETE ="api/person/delete/"; //+person_id

    public final static String POST_PANNEL_REGISTER = "api/pannel/register/";
    public final static String POST_PANNEL_UPDATE = "api/pannel/update/"; // + pannel_id
    public final static String GET_PANNEL_INFO = "api/pannel/info/"; // + auth_id
    public final static String POST_PANNEL_EDIT = "api/pannel/edit/"; // + pannel_id
    public final static String GET_PANNEL_DELETE = "api/pannel/delete/"; // + pannel_id 
    public final static String GET_PANNEL_PERSONAL = "api/pannel/personal_info/"; // + auth_id
    public final static String GET_PANNEL_GRAPH = "api/pannel/personal_graph/";// + auth_id

    public final static String GET_MAP_PHP = "api/map/";
}