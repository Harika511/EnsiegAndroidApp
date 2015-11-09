package com.skylka.ensieg.servicecalls;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.skylka.ensieg.constants.Ensieg_AppConstants;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.model.GcmRegisterBean;
import com.skylka.ensieg.model.UserLoginModel;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class UserLoginService {
	String uname, pwd, mac;
	Context mcontext;
	boolean isLogInInserted;
	EnsiegDB database;

	public UserLoginService(Context context, String username, String password) {
		mcontext = context;
		this.uname = username;
		this.pwd = password;
		// mac = getMacAddress();
		new UserLogin().execute(Ensieg_AppConstants.appService_login);
	}
	public UserLoginService(Context context, String edit, String username, String password) {
		mcontext = context;
		this.uname = username;
		this.pwd = password;
		// mac = getMacAddress();
		new UserLogin().execute(Ensieg_AppConstants.appService_login);
	}
	public class UserLogin extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			Ensieg_AppConstants.loginSuccess = false;
		}

		@Override
		protected String doInBackground(String... urls) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			// firstname=ANKIT&lastname=VERMA&
			// network=bitspilani&email=vankit398@gmail.com&mobile=8019081149&password=happy@123&uin=123&macid=1
			// 23&deviceos=android15.0
			database = new EnsiegDB(mcontext);
			database.Open();
			GcmRegisterBean gcmBeanInstance = GcmRegisterBean.getGcmBeanInstance();
			// UserRegistrationDb dbRegister = new UserRegistrationDb(mcontext);
			ArrayList<String> allRegisters = database.getAllRegisters();
			database.close();
			nameValuePairs.add(new BasicNameValuePair("username", uname));
			nameValuePairs.add(new BasicNameValuePair("password", pwd));
			nameValuePairs.add(new BasicNameValuePair("uin", gcmBeanInstance.getGcmId()));
			nameValuePairs.add(new BasicNameValuePair("deviceos", "1"));
			nameValuePairs.add(new BasicNameValuePair("macid", Ensieg_AppConstants.macAddress));
			nameValuePairs.add(new BasicNameValuePair("firsttimelogin", "1"));
			// Log.d("values are ", mac + " " + phoneNo + " " +
			// userRegisteredId);

			return GET(urls[0], nameValuePairs);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(mcontext, result + " login", Toast.LENGTH_LONG).show();
			// if(result.contains("0")){
			// Ensieg_AppConstants.loginSuccess=true;
			// }
			parseJSON(result);
		}
	}

	/**
	 * connect with server and will give JSON data in string format
	 *
	 * @param url
	 *            --to get data
	 * @param pair
	 *            --to get
	 */

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

	/**
	 * to get json object from inputstream
	 *
	 */
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

	private boolean parseJSON(String res) {

		try {
			JSONObject jsonObj = new JSONObject(res);
			//
			// // Getting JSON Array node
			JSONObject billPos = jsonObj.getJSONObject("data");
			//
			// // looping through All Contacts
			// for (int i = 0; i < billPos.length(); i++) {
			Toast.makeText(mcontext, billPos + " some ", Toast.LENGTH_LONG).show();
			String session = billPos.getString("sessionId");
			Ensieg_AppConstants.sessionId = session.substring(0, session.length());
			// UserLoginDb login = new UserLoginDb(mcontext);
			UserLoginModel loginModel = new UserLoginModel(uname, pwd, session);
			if (database.numberOfRows_InLogin() > 0)
				database.deleteAllRows_login();

			database.insertloginData(loginModel);
			isLogInInserted = true;
			Log.d("", session.substring(0, session.length()) + "");

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return false;
	}
	// public void callProfile() {
	//
	// Intent in = new Intent(UserLoginService.this,
	// Ensieg_CreateProfileActivity.class);
	// startActivity(in);
	//
	// }
}
