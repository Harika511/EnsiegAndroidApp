package com.skylka.ensieg.backend;

import android.content.Context;
import android.util.Log;

import com.skylka.ensieg.backend.models.RequestGetNotifications;
import com.skylka.ensieg.backend.models.RequestRegisterUser;
import com.skylka.ensieg.backend.models.ResponseGetNotifications;
import com.skylka.ensieg.backend.models.ResponseRegisterUser;
import com.skylka.ensieg.constants.Constants;
import com.skylka.ensieg.model.NotificationModel;

import java.util.List;

/**
 * Created by harika on 11/9/15.
 */
public class RegisterUserAPI {

    Context context;
    RetrofitInterface retrofitInterface;

    public interface NotificationsCallback {
        void onComplete(boolean status, int statusCode, String message);
    }

    public RegisterUserAPI(Context context, RetrofitInterface retrofitInterface) {
        this.context = context;
        this.retrofitInterface = retrofitInterface;
    }

    public void getRegistration(final String uin,final String sessionid,final String fName ,final String lName,final String network,final String email, final String number, final String password , final NotificationsCallback callback){

        retrofitInterface.getUserRegistration(new RequestRegisterUser(uin,sessionid,fName,lName,network,email,number,password), new Callback<ResponseRegisterUser>() {
            @Override
            public void success(ResponseRegisterUser responseRegisterUser, Response response) {

                if (responseRegisterUser != null ) {
                    Log.d(Constants.LOG_TAG, "\nNOTIFICATIONS_API - GetNotifications : Success");
                    callback.onComplete(true, 200, "Success");
                } else {
                    Log.d(Constants.LOG_TAG, "\nNOTIFICATIONS_API - GetNotifications : Failure");
                    callback.onComplete(false, 500, responseRegisterUser.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                try {
                    Log.d(Constants.LOG_TAG, "\nNOTIFICATIONS_API - GetNotifications : Error = " + error.getKind() + " , StatusCode = " + error.getResponse().getStatus());
                    callback.onComplete(false, error.getResponse().getStatus(), "Error", null, null, null);
                } catch (Exception e) {
                    Log.d(Constants.LOG_TAG, "\nNOTIFICATIONS_API - GetNotifications : Error = " + error.getKind());
                    callback.onComplete(false, -1, "Error");
                }
            }
        });
    }


}
