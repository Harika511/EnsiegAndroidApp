package com.skylka.ensieg.activities;

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

import com.google.android.gms.appdatasearch.RegisterSectionInfo;
import com.skylka.ensieg.R;
import com.skylka.ensieg.constants.Ensieg_AppConstants;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.model.GcmRegisterBean;
import com.skylka.ensieg.servicecalls.UserLoginService;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Ensieg_Password_ForEdit_Profile_Activity extends Activity {

	Button btNewPassword;
	EnsiegDB register;
	String mailId = "";
	EditText etPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.reset_password);
		initializeViews();
	}

	public void initializeViews() {
		btNewPassword = (Button) findViewById(R.id.bt_new_pwd);
		etPassword = (EditText) findViewById(R.id.et_newPassword);
		register = new EnsiegDB(getApplicationContext());
		register.Open();

		if (register.numberOfRows_inRegisters() > 0) {
			ArrayList<String> allRegisters = register.getAllRegisters();
			register.close();
			mailId = allRegisters.get(4);
		}
		btNewPassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (etPassword.getText().toString().length() > 0) {
					new PasswordVerify().execute(Ensieg_AppConstants.appService_verifypassword);
				}
				/// new
				/// UserLoginService(Ensieg_Password_ForEdit_Profile_Activity.this,mailId,
				/// btNewPassword.getText().toString());
				// callUpdate();

			}
		});

	}

	public class PasswordVerify extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			Ensieg_AppConstants.loginSuccess = false;

		}

		@Override
		protected String doInBackground(String... urls) {

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			EnsiegDB login = new EnsiegDB(getApplicationContext());
			login.Open();
			ArrayList<String> allRegisters = login.getLoginData();
			login.close();
			String sessionId = allRegisters.get(3);
			nameValuePairs.add(new BasicNameValuePair("sessionid", sessionId));
			nameValuePairs.add(new BasicNameValuePair("password", etPassword.getText().toString()));

			return GET(urls[0], nameValuePairs);

		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {

			Toast.makeText(getApplicationContext(), result + " login", Toast.LENGTH_LONG).show();
			JSONObject jsonObj;
			try {
				jsonObj = new JSONObject(result);
				if (jsonObj.getString("error").equals("0")) {
					callUpdate();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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

	public void callUpdate() {

		Intent login = new Intent(Ensieg_Password_ForEdit_Profile_Activity.this, Ensieg_UpdateProfile_Activity.class);
		startActivity(login);
	}

}
