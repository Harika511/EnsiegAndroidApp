package com.skylka.ensieg.backend;


import android.content.Context;
import android.util.Log;

import com.skylka.ensieg.backend.models.RequestGetNotifications;
import com.skylka.ensieg.backend.models.ResponseGetNotifications;
import com.skylka.ensieg.model.NotificationModel;
import com.skylka.ensieg.constants.Constants;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NotificationsAPI {

    Context context;
    RetrofitInterface retrofitInterface;

    public interface NotificationsCallback {
        void onComplete(boolean status, int statusCode, String message, List<NotificationModel> invitedNotifications, List<NotificationModel> hostedNotificatons, List<NotificationModel> interestedNotifications);
    }

    public NotificationsAPI(Context context, RetrofitInterface retrofitInterface) {
        this.context = context;
        this.retrofitInterface = retrofitInterface;
    }

    public void getNotifications(final String sessionID, final NotificationsCallback callback){

        retrofitInterface.getNotifications(new RequestGetNotifications(sessionID), new Callback<ResponseGetNotifications>() {
            @Override
            public void success(ResponseGetNotifications responseGetNotifications, Response response) {

                if (responseGetNotifications != null && responseGetNotifications.getHosted() != null && responseGetNotifications.getInvited() != null && responseGetNotifications.getInterested() != null) {
                    Log.d(Constants.LOG_TAG, "\nNOTIFICATIONS_API - GetNotifications : Success");
                    callback.onComplete(true, 200, "Success", responseGetNotifications.getInvited(), responseGetNotifications.getHosted(), responseGetNotifications.getInterested());
                } else {
                    Log.d(Constants.LOG_TAG, "\nNOTIFICATIONS_API - GetNotifications : Failure");
                    callback.onComplete(false, 500, responseGetNotifications.getMessage(), null, null, null);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                try {
                    Log.d(Constants.LOG_TAG, "\nNOTIFICATIONS_API - GetNotifications : Error = " + error.getKind() + " , StatusCode = " + error.getResponse().getStatus());
                    callback.onComplete(false, error.getResponse().getStatus(), "Error", null, null, null);
                } catch (Exception e) {
                    Log.d(Constants.LOG_TAG, "\nNOTIFICATIONS_API - GetNotifications : Error = " + error.getKind());
                    callback.onComplete(false, -1, "Error", null, null, null);
                }
            }
        });
    }

}