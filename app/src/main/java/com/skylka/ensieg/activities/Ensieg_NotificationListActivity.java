package com.skylka.ensieg.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.skylka.ensieg.R;
import com.skylka.ensieg.adapters.NotificationListAdapter;
import com.skylka.ensieg.backend.BackendVariables;
import com.skylka.ensieg.backend.NotificationsAPI;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.model.NotificationModel;

import java.util.ArrayList;
import java.util.List;

public class Ensieg_NotificationListActivity extends Activity {
    ListView NotificationListView;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        mContext=this;
        NotificationListView = (ListView)findViewById(R.id.notificationListView);
        //GetNotifications getNotifications = new GetNotifications();
        //getNotifications.execute(Ensieg_AppConstants.appService_getNotifications);

        getNotifications();

    }

    public void getNotifications(){

        Toast.makeText(mContext, "Loading Notifications", Toast.LENGTH_LONG).show();
        EnsiegDB dataBase=new EnsiegDB(getApplicationContext());
        ArrayList<String> loginData=dataBase.getLoginData();
        new BackendVariables(this).notificationsAPI.getNotifications(loginData.get(3), new NotificationsAPI.NotificationsCallback() {
            @Override
            public void onComplete(boolean status, int statusCode, String message, List<NotificationModel> invitedNotifications, List<NotificationModel> hostedNotificatons, List<NotificationModel> interestedNotifications) {
                if (status) {
                    List<NotificationModel> allNotifications = new ArrayList<NotificationModel>();
                    for (int i=0;i<interestedNotifications.size();i++){
                        allNotifications.add(interestedNotifications.get(i));
                    }
                    for (int i=0;i<hostedNotificatons.size();i++){
                        allNotifications.add(hostedNotificatons.get(i));
                    }
                    for (int i=0;i<interestedNotifications.size();i++){
                        allNotifications.add(interestedNotifications.get(i));
                    }
                    NotificationListAdapter notificationListAdapter = new NotificationListAdapter(mContext, R.layout.listview_notification_item, allNotifications);
                    NotificationListView.setAdapter(notificationListAdapter);
                } else {
                    Toast.makeText(mContext, "Errorin Loading Notifications , Message = "+message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /*

    public static String GET(String url, List<NameValuePair> pair) {
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpClient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(pair));
            HttpResponse httpResponse = httpClient.execute(httpPost);

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            // convert inputstream to string
            if (inputStream != null) {
                result = convertIsToJson(inputStream) + " ";
            } else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        Log.d("", "result is " + result);
        return result;
    }
    private static JSONObject convertIsToJson(InputStream iso) {
        JSONObject jObj = null;
        String json;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(iso, "iso-8859-1"), 8);

            StringBuilder sb = new StringBuilder();
            String line = null;
            if (reader != null)
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            iso.close();
            json = sb.toString();
            // Log.d(" jobj", json+"");
            jObj = new JSONObject(json);

        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        return jObj;
    }
    public class GetNotifications extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("sessionid","e02574113d561c72d04351bbd01ff087"));

            // Log.d("values are ", mac + " " + phoneNo + " " +
            // userRegisteredId);

            return GET(urls[0], nameValuePairs);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            try
            {
                JSONObject ReplyFromServer = new JSONObject(result);
                if ( ! ReplyFromServer.getString("error").equals("0" +
                        "")) {
                    Log.d("GetNotifications", ReplyFromServer.getString("error"));
                    throw new RuntimeException("Server returned error");
                }
                JSONObject data = new JSONObject(ReplyFromServer.getString("data"));
                JSONArray invited = new JSONArray(data.getString("invited"));
                JSONArray hosted = new JSONArray(data.getString("hosted"));
                JSONArray interested = new JSONArray(data.getString("interested"));
                List<NotificationModel> notifications = new ArrayList<>();
                for (int x =0 ; x<invited.length();x++)
                {
                    JSONObject person = invited.getJSONObject(x);
                    notifications.add(new NotificationModel(person.getString("name"),Integer.parseInt(person.getString("sportsidfk")),person.getString("date"),1));
                }
                for (int x =0 ; x<hosted.length();x++)
                {
                    JSONObject person = hosted.getJSONObject(x);
                    notifications.add(new NotificationModel(person.getString("name"),Integer.parseInt(person.getString("sportsidfk")),person.getString("date"),2));
                }
                for (int x =0 ; x<interested.length();x++)
                {
                    JSONObject person = interested.getJSONObject(x);
                    notifications.add(new NotificationModel(person.getString("name"),Integer.parseInt(person.getString("sportsidfk")),person.getString("date"),3));
                }

                NotificationListAdapter notificationListAdapter = new NotificationListAdapter(mContext,R.layout.listview_notification_item,notifications);
                NotificationListView.setAdapter(notificationListAdapter);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                throw  new RuntimeException("Unable to connect to Server");
            }
        }
    }*/
}