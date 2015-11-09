package com.skylka.ensieg.backend.models;

/**
 * Created by harika on 11/9/15.
 */
public class RequestCreateProfile {


    String sessionid;
    String operation;
    String gender;
    String age;
    String photo;
    String count;

    public RequestCreateProfile(String operation,String sessionId,String gender,String age,String photo,String count){
        this.sessionid = sessionid;
        this.operation=operation;
        this.gender=gender;
        this.age=age;
        this.photo=photo;
        this.count=count;

    }

   /* nameValuePairs.add(new BasicNameValuePair("operation", "0"));
    nameValuePairs.add(new BasicNameValuePair("sessionid", allRegisters.get(3)));
    nameValuePairs.add(new BasicNameValuePair("gender", gender));
    nameValuePairs.add(new BasicNameValuePair("age", age));
    nameValuePairs.add(new BasicNameValuePair("photo", bm_str));
    nameValuePairs.add(new BasicNameValuePair("count", noOfSports_count + ""));*/
}
