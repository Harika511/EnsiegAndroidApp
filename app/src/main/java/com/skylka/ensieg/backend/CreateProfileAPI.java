package com.skylka.ensieg.backend;

import android.content.Context;
import android.util.Log;

import com.skylka.ensieg.backend.models.RequestCreateProfile;
import com.skylka.ensieg.backend.models.RequestGetNotifications;
import com.skylka.ensieg.backend.models.ResponseCreateProfile;
import com.skylka.ensieg.backend.models.ResponseGetNotifications;
import com.skylka.ensieg.constants.Constants;
import com.skylka.ensieg.model.NotificationModel;
import com.skylka.ensieg.model.UserProfileModel;

import java.util.List;

/**
 * Created by harika on 11/9/15.
 */
public class CreateProfileAPI {


    Context context;
    RetrofitInterface retrofitInterface;

    public interface CrateProfileCallback {
        void onComplete(boolean status, int statusCode, String message);
    }

    public CreateProfileAPI(Context context, RetrofitInterface retrofitInterface) {
        this.context = context;
        this.retrofitInterface = retrofitInterface;
    }

    public void getCreatePrifile(final String sessionID,final String operation,final String gender,final String age,final String photo,final String count, final CrateProfileCallback callback){

        retrofitInterface.getCreateProfileStatus(new RequestCreateProfile(operation,sessionID,gender,age,photo,count), new Callback<ResponseCreateProfile>() {
            @Override
            public void success(ResponseCreateProfile responseCreateProfile, Response response) {

                if (responseCreateProfile != null ) {
                    Log.d(Constants.LOG_TAG, "\nNOTIFICATIONS_API - GetNotifications : Success");
                    callback.onComplete(true, 200, "Success");
                } else {
                    Log.d(Constants.LOG_TAG, "\nNOTIFICATIONS_API - GetNotifications : Failure");
                    callback.onComplete(false, 500, responseCreateProfile.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                try {
                    Log.d(Constants.LOG_TAG, "\nCREATEPROFILE_API - GETPROFILE : Error = " + error.getKind() + " , StatusCode = " + error.getResponse().getStatus());
                    callback.onComplete(false, error.getResponse().getStatus(), "Error");
                } catch (Exception e) {
                    Log.d(Constants.LOG_TAG, "\nCREATEPROFILE_API - GETPROFILE :  Error = " + error.getKind());
                    callback.onComplete(false, -1, "Error");
                }
            }
        });
    }



}
