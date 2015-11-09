package com.skylka.ensieg.backend;

import android.content.Context;
import android.util.Log;

import com.skylka.ensieg.backend.models.RequestLogin;
import com.skylka.ensieg.backend.models.ResponseLogin;
import com.skylka.ensieg.backend.models.ResponseRegisterUser;
import com.skylka.ensieg.constants.Constants;
import com.skylka.ensieg.model.UserLoginModel;

import java.util.List;

/**
 * Created by harika on 11/9/15.
 */
public class LoginAPI {

    Context context;
    RetrofitInterface retrofitInterface;

    public interface NotificationsCallback {
        void onComplete(boolean status, int statusCode, String message, List<UserLoginModel> loginSessionId);
    }

    public LoginAPI(Context context, RetrofitInterface retrofitInterface) {
        this.context = context;
        this.retrofitInterface = retrofitInterface;
    }

    public void getLogin(final String uin,final String email,final String password , final NotificationsCallback callback){

        retrofitInterface.getUserLogin(new RequestLogin(uin, email, password), new Callback<ResponseRegisterUser>() {
            @Override
            public void success(ResponseLogin responseLogin, Response response) {

                if (responseLogin != null) {
                    Log.d(Constants.LOG_TAG, "\nLOGIN_API - Login : Success");
                    callback.onComplete(true, 200, "Success",responseLogin.getSeesionId());
                } else {
                    Log.d(Constants.LOG_TAG, "\nLOGIN_API - Login: Failure");
                    callback.onComplete(false, 500, responseLogin.getMessage(),null);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                try {
                    Log.d(Constants.LOG_TAG, "\nLOGIN_API - Login : Error = " + error.getKind() + " , StatusCode = " + error.getResponse().getStatus());
                    callback.onComplete(false, error.getResponse().getStatus(), "Error", null);
                } catch (Exception e) {
                    Log.d(Constants.LOG_TAG, "\nLOGIN_API - Login : Error = " + error.getKind());
                    callback.onComplete(false, -1, "Error", null);
                }
            }
        });
    }


}
