package com.skylka.ensieg.commonlib.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.skylka.ensieg.commonlib.R;
import com.skylka.ensieg.commonlib.interfaces.ActionCallback;


public class CustomActivity extends Activity {

    public Context context;
    public LayoutInflater inflater;
    public InputMethodManager inputMethodManager;

    BroadcastReceiver activityReceiver;

    private ViewSwitcher viewSwitcher;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        context = this;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    //==============   RECEIVER METHODS    ========//

    public void registerActivityReceiver(final String activityReceiverName) {

        if (activityReceiver == null) {

            IntentFilter intentFilter = new IntentFilter(activityReceiverName);
            activityReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    try {
                        onActivityReceiverMessage(activityReceiverName, intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            this.registerReceiver(activityReceiver, intentFilter);  //registering receiver
        }
    }

    public void onActivityReceiverMessage(String activityReceiverName, Intent receivedData) {
    }

    //==============  SNACKBAR METHODS  ==============//

    public void showSnackbarMessage(String message) {
        showSnackbar(findViewById(R.id.container), message, null, null);
    }

    public void showSnackbarMessage(String message, String actionName, ActionCallback callback) {
        showSnackbar(findViewById(R.id.container), message, actionName, callback);
    }

    public void showSnackbarMessage(View view, String message, String actionName, ActionCallback callback) {
        showSnackbar(view, message, actionName, callback);
    }

    private void showSnackbar(View view, String message, String actionName, final ActionCallback callback) {
        if (view == null) {
            view = findViewById(R.id.container);
        }

        if (view != null) {
            Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
            if (callback != null && actionName != null) {
                snackbar.setAction(actionName, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callback.onAction();
                    }
                });
            }
            snackbar.show();
        } else {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    public void showToastMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    //==============  LOADING VIEW METHODS  ==============//

}
