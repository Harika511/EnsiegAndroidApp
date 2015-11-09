package com.skylka.ensieg.backend;

import com.skylka.ensieg.backend.models.RequestCreateProfile;
import com.skylka.ensieg.backend.models.RequestGetNotifications;
import com.skylka.ensieg.backend.models.RequestLogin;
import com.skylka.ensieg.backend.models.RequestRegisterUser;
import com.skylka.ensieg.backend.models.ResponseCreateProfile;
import com.skylka.ensieg.backend.models.ResponseGetNotifications;
import com.skylka.ensieg.backend.models.ResponseLogin;
import com.skylka.ensieg.backend.models.ResponseRegisterUser;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by venkatesh on 29/6/15.
 */

public interface RetrofitInterface {

    //-------------- AddressBook API Calls --------------//

    @POST("/getnotification")
    void getNotifications(@Body RequestGetNotifications requestGetNotifications, Callback<ResponseGetNotifications> callback);
    @POST("/getCreateProfileStatus")
    void getCreateProfileStatus(@Body RequestCreateProfile requestCreateProfile, Callback<ResponseCreateProfile> callback);
    @POST("/getRegisterUser")
    void getUserRegistration(@Body RequestRegisterUser requestRegisterUser, Callback<ResponseRegisterUser> callback);
    @POST("/getUserLogin")
    void getUserLogin(@Body RequestLogin requestUserLogin, Callback<ResponseLogin> callback);




    //-----------------------------------------------//

}

