package com.skylka.ensieg.backend;

import android.content.Context;

import com.skylka.ensieg.R;

import retrofit.RestAdapter;

/**
 * Created by venkatesh on 21/7/15.
 */
public class BackendVariables {

    Context context;

    public RestAdapter restAdapter;
    public RetrofitInterface retrofitInterface;

    public NotificationsAPI notificationsAPI;
    public CreateProfileAPI createProfileAPI;
    public RegisterUserAPI registerUserAPI;
    public LoginAPI loginApi;
    public BackendVariables(Context context){

        this.context = context;

        restAdapter = new RestAdapter.Builder().setEndpoint(context.getResources().getString(R.string.ensig_base_url)).setLogLevel(RestAdapter.LogLevel.FULL).build();
        retrofitInterface = restAdapter.create(RetrofitInterface.class);
        notificationsAPI = new NotificationsAPI(context, retrofitInterface);
        createProfileAPI=new CreateProfileAPI(context,retrofitInterface);
        registerUserAPI=new RegisterUserAPI(context,retrofitInterface);
        loginApi=new LoginAPI(context,retrofitInterface);
    }
}
