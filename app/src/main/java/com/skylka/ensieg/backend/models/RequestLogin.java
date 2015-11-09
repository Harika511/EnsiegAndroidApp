package com.skylka.ensieg.backend.models;

/**
 * Created by harika on 11/9/15.
 */
public class RequestLogin {

    String uin;
    String uName;
    String password;

    public RequestLogin(String uin,String uName,String password){
        this.uin = uin;
        this.uName=uName;
        this.password=password;
    }

}
