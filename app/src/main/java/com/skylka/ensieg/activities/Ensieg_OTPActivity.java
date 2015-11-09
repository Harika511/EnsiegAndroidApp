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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.skylka.ensieg.ConnectionDetector;
import com.skylka.ensieg.R;
import com.skylka.ensieg.constants.Ensieg_AppConstants;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.model.GcmRegisterBean;
import com.skylka.ensieg.servicecalls.UserLoginService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * This class consists of otp verification code and save the user Registration
 * Details to the server and will do automatic Login.
 * 
 * @author harika
 *
 */
public class Ensieg_OTPActivity extends Activity {

	ImageView iv;
	private Button btVerify, resendOtp;
	EditText etOtp;
	String email, pwd;
	TextView tvOtp;
	EnsiegDB database;

	/**
	 * It display the activity layout
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.otpverification);
		database = new EnsiegDB(this);
		initializeViews();
		callGenerateOTP();
	}

	/**
	 * It initializes all required views of this activity layout
	 * 
	 */
	public void initializeViews() {
		iv = (ImageView) findViewById(R.id.otpback);
		btVerify = (Button) findViewById(R.id.btverify);
		resendOtp = (Button) findViewById(R.id.bt_resend_otp);
		etOtp = (EditText) findViewById(R.id.etOtp);
		tvOtp = (TextView) findViewById(R.id.tv_sent);
		// etOtp.setSelection(50);
		// UserRegistrationDb register = new
		// UserRegistrationDb(getApplicationContext());
		database.Open();
		if (database.numberOfRows_inRegisters() > 0) {
			ArrayList<String> allRegisters = database.getAllRegisters();
			tvOtp.setText("sent to your mobile " + allRegisters.get(5));
		}
		database.close();
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		/**
		 * It verifies whether user entered otp is valid otp or not
		 */
		btVerify.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				etOtp.getText();
				callProfile();
				ConnectionDetector isConnected = new ConnectionDetector(getApplicationContext());
				if (isConnected.isConnectingToInternet()) {
					new FirstRequestForRegister().execute(Ensieg_AppConstants.appService);
				} else {
					Toast.makeText(Ensieg_OTPActivity.this, "Please Connect to your Data Connection And try it again",
							Toast.LENGTH_LONG).show();
				}

				callVerifyOTP();
			}
		});
		/**
		 * It is called whenever user want to ask for resend otp
		 * 
		 */
		resendOtp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				etOtp.setText("");
				callGenerateOTP();

			}
		});
	}

	protected void callVerifyOTP() {
		ConnectionDetector isConnected = new ConnectionDetector(getApplicationContext());
		if (isConnected.isConnectingToInternet()) {
			// new VerifyOTP().execute(Ensieg_AppConstants.VERIFYOTP);
		} else {
			Toast.makeText(Ensieg_OTPActivity.this, "Please Connect to your Data Connection And try it again",
					Toast.LENGTH_LONG).show();
		}

	}

	protected void callGenerateOTP() {
		ConnectionDetector isConnected = new ConnectionDetector(getApplicationContext());
		if (isConnected.isConnectingToInternet()) {
			// new GenerateOTP().execute(Ensieg_AppConstants.GENERATEOTP);
		} else {
			Toast.makeText(Ensieg_OTPActivity.this, "Please Connect to your Data Connection And try it again",
					Toast.LENGTH_LONG).show();
		}
	}

	public void callReSetPassword() {

	}

	public void callProfile() {

		Intent in = new Intent(Ensieg_OTPActivity.this, Ensieg_CreateProfileActivity.class);
		startActivity(in);
		finish();

	}

	private String getMacAddress() {

		WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		String address = info.getMacAddress();
		Ensieg_AppConstants.macAddress = address;
		return address;
	}

	private class FirstRequestForRegister extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			if (pDialog == null) {
				pDialog = new ProgressDialog(Ensieg_OTPActivity.this);
				pDialog.setMessage("Saving Registration Details");
				pDialog.setCancelable(false);
				pDialog.setCanceledOnTouchOutside(false);
				pDialog.show();
			}
		}

		@Override
		protected String doInBackground(String... urls) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			String mac = getMacAddress();

			System.out.println();
			// UserRegistrationDb dbRegister = new
			// UserRegistrationDb(getApplicationContext());
			database.Open();
			ArrayList<String> allRegisters = database.getAllRegisters();
			database.close();

			Log.d("all data", allRegisters.size() + "");
			// Toast.makeText(Ensieg_OTPActivity.this, allRegisters.size(),
			// Toast.LENGTH_LONG).show();
			email = allRegisters.get(4).toString();
			pwd = allRegisters.get(6).toString();
			GcmRegisterBean gcmBeanInstance = GcmRegisterBean.getGcmBeanInstance();
			nameValuePairs.add(new BasicNameValuePair("uin", gcmBeanInstance.getGcmId()));
			nameValuePairs.add(new BasicNameValuePair("firstname", allRegisters.get(1)));
			nameValuePairs.add(new BasicNameValuePair("lastname", allRegisters.get(2)));
			nameValuePairs.add(new BasicNameValuePair("network", allRegisters.get(3)));
			nameValuePairs.add(new BasicNameValuePair("email", email));
			nameValuePairs.add(new BasicNameValuePair("phonenumber", allRegisters.get(5)));
			nameValuePairs.add(new BasicNameValuePair("password", pwd));
			nameValuePairs.add(new BasicNameValuePair("macid", mac));
			nameValuePairs.add(new BasicNameValuePair("deviceos", "1"));
			// Log.d("values are ", mac + " " + phoneNo + " " +
			// userRegisteredId);

			return GET(urls[0], nameValuePairs);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			// Toast.makeText(getApplicationContext(), result + " register ",
			// Toast.LENGTH_LONG).show();
			if (pDialog != null && pDialog.isShowing()) {
				// Toast.makeText(getApplicationContext(), result + " register
				// ", Toast.LENGTH_LONG).show();
				if (pDialog != null && pDialog.isShowing())
					pDialog.dismiss();
				pDialog = null;
			}
			Log.d("Ensieg", "REGISTration response" + result);
			if (result.contains("0")) {
				ConnectionDetector isConnected = new ConnectionDetector(Ensieg_OTPActivity.this);
				if (isConnected.isConnectingToInternet()) {
					new UserLoginService(Ensieg_OTPActivity.this, email, pwd);
				} else {
					Toast.makeText(Ensieg_OTPActivity.this, "Please Connect to your Data Connection And try it again",
							Toast.LENGTH_LONG).show();
				}
				// if (Ensieg_AppConstants.loginSuccess) {
				//
				// callProfile();
				// }
			} else {
				Toast.makeText(Ensieg_OTPActivity.this, "OOPS,Unable to connect to the server", Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	private class GenerateOTP extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			// UserRegistrationDb dbRegister = new
			// UserRegistrationDb(getApplicationContext());
			database.Open();
			ArrayList<String> allRegisters = database.getAllRegisters();
			database.close();
			nameValuePairs.add(new BasicNameValuePair("countryCode", "91"));
			nameValuePairs.add(new BasicNameValuePair("mobileNumber", allRegisters.get(5)));
			InputStream inputStream = null;
			String result = "";
			try {

				// create HttpClient
				HttpClient httpClient = new DefaultHttpClient();

				HttpPost httpPost = new HttpPost(urls[0]);
				httpPost.setHeader("Content-Type", "application/json");
				httpPost.setHeader("application-Key", Ensieg_AppConstants.otp_application_key);
				JSONObject obj = new JSONObject();
				obj.put("countryCode", 91);
				obj.put("mobileNumber", allRegisters.get(5));
				httpPost.setEntity(new StringEntity(obj.toString()));
				

				HttpResponse httpResponse = httpClient.execute(httpPost);
				Log.d("http client ", httpResponse + " response ");
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

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			/*
			 * Toast.makeText(getApplicationContext(), result,
			 * Toast.LENGTH_LONG).show(); Log.d("ENSIEG", result);
			 */
			try {
				JSONObject result_obj = new JSONObject(result);
				String status = result_obj.getString("status");
				String code = result_obj.getJSONObject("response").getString("code");
				if (status.equalsIgnoreCase("success")) {
					Toast.makeText(getApplicationContext(), code, Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(), code, Toast.LENGTH_LONG).show();
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private class VerifyOTP extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			database.Open();
			// UserRegistrationDb dbRegister = new
			// UserRegistrationDb(getApplicationContext());
			ArrayList<String> allRegisters = database.getAllRegisters();
			database.close();
			InputStream inputStream = null;
			String result = "";
			try {

				// create HttpClient
				HttpClient httpClient = new DefaultHttpClient();

				HttpPost httpPost = new HttpPost(urls[0]);
				httpPost.setHeader("Content-Type", "application/json");
				httpPost.setHeader("application-Key", Ensieg_AppConstants.otp_application_key);

				JSONObject obj = new JSONObject();
				obj.put("countryCode", 91);
				obj.put("mobileNumber", allRegisters.get(5));
				obj.put("oneTimePassword", etOtp.getText().toString().trim());
				httpPost.setEntity(new StringEntity(obj.toString()));
				HttpResponse httpResponse = httpClient.execute(httpPost);
				Log.d("http client ", httpResponse + " response ");
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

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {

			try {
				JSONObject result_obj = new JSONObject(result);
				String status = result_obj.getString("status");
				String code = result_obj.getJSONObject("response").getString("code");
				if (status.equalsIgnoreCase("success")) {
					Toast.makeText(getApplicationContext(), code, Toast.LENGTH_LONG).show();
					callProfile();
					new FirstRequestForRegister().execute(Ensieg_AppConstants.appService);

				} else {
					Toast.makeText(getApplicationContext(), code, Toast.LENGTH_LONG).show();
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
			Log.d("http client ", httpResponse + " response ");
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
			Log.d(json, json + " json");
			jObj = new JSONObject(json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}
		return jObj;
	}

}
