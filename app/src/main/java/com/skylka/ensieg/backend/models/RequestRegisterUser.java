package com.skylka.ensieg.backend.models;

/**
 * Created by harika on 11/9/15.
 */
public class RequestRegisterUser {

    String uin;
    String sessionid;
    String fName;
    String lName;
    String network;
    String email;
    String number;
    String password;

    public RequestRegisterUser( String uin,String sessionid,String fName ,String lName,String network,String email,String number,String password){
        this.uin=uin;
        this.sessionid = sessionid;
        this.fName=fName;
        this.lName=lName;
        this.network=network;
        this.email=email;
        this.number=number;
        this.password=password;

    }


}
