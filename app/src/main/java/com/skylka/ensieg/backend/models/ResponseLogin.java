package com.skylka.ensieg.backend.models;

import com.skylka.ensieg.model.NotificationModel;
import com.skylka.ensieg.model.UserLoginModel;

import java.util.List;

/**
 * Created by harika on 11/9/15.
 */
public class ResponseLogin {

    int error;
    String message;

    List<UserLoginModel> login;


    public int getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<UserLoginModel> getSeesionId() {
        return login;
    }

}
