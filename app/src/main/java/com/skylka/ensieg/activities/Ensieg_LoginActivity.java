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

import com.skylka.ensieg.ConnectionDetector;
import com.skylka.ensieg.R;
import com.skylka.ensieg.adapters.NotificationListAdapter;
import com.skylka.ensieg.backend.BackendVariables;
import com.skylka.ensieg.backend.NotificationsAPI;
import com.skylka.ensieg.constants.Ensieg_AppConstants;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.model.GcmRegisterBean;
import com.skylka.ensieg.model.NotificationModel;
import com.skylka.ensieg.model.UserLoginModel;
import com.skylka.ensieg.model.UserProfileModel;
import com.skylka.ensieg.model.UserRegisterationModel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * In this we are allowing the user to login into ensieg Account
 * 
 * @author harika
 *
 */
public class Ensieg_LoginActivity extends Activity {

	Button btlogin;
	ImageView ivBack;
	EditText etName, etPassword;
	TextView tvforgetPwd;
	String name, password;
	String loginMessage;
	TextView tvLogin;
	ProgressDialog pDialog;
	TextView tv_line_mobile, tv_line_pwd;
	EnsiegDB database;
	Context mContext;

	/**
	 * It will display the this activity layout
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_screen1);
		database = new EnsiegDB(this);
		initializeViews();
	}

	/**
	 * It will initializes all required views of this activity layout
	 */
	public void initializeViews() {
		ivBack = (ImageView) findViewById(R.id.loginback);
		etName = (EditText) findViewById(R.id.et_login_mbl);
		etPassword = (EditText) findViewById(R.id.et_login_pwd);
		btlogin = (Button) findViewById(R.id.bt_login);
		tvforgetPwd = (TextView) findViewById(R.id.tv_frgt_pwd);
		tvLogin = (TextView) findViewById(R.id.txt_login_hdr);
		Typeface font = Typeface.createFromAsset(getAssets(), "MYRIADPRO-REGULAR.ttf");
		etName.setTypeface(font);
		etPassword.setTypeface(font);
		btlogin.setTypeface(font);
		tvforgetPwd.setTypeface(font);
		tvLogin.setTypeface(font);
		tv_line_mobile = (TextView) findViewById(R.id.tv_line_mbl);
		tv_line_pwd = (TextView) findViewById(R.id.tv_line_pwd);
		String myString = "FORGOT PASSWORD?";
		SpannableString content = new SpannableString(myString);
		content.setSpan(new UnderlineSpan(), 0, myString.length(), 0);
		tvforgetPwd.setText(content);

		etName.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				tv_line_mobile.setBackgroundColor(getResources().getColor(R.color.clicking_color));
				tv_line_pwd.setBackgroundColor(getResources().getColor(android.R.color.black));
			}
		});
		etPassword.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				tv_line_mobile.setBackgroundColor(getResources().getColor(android.R.color.black));
				tv_line_pwd.setBackgroundColor(getResources().getColor(R.color.clicking_color));
			}
		});
		/**
		 * It is called whenever user want to click on the back button
		 */
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		/**
		 * It is called whenever user want to login with his details
		 */
		btlogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// btlogin.setTextColor(getResources().getColor(R.color.login_btn_end));
				// btlogin.setBackgroundColor(getResources().getColor(android.R.color.black));
				name = etName.getText().toString();
				password = etPassword.getText().toString();

				// Intent in = new Intent(Ensieg_LoginActivity.this,
				// Ensieg_HomeActivity.class);
				// startActivity(in);
				// finish();

				if (name != null && name.length() > 0 && password != null && password.length() > 0) {
					ConnectionDetector isConnected = new ConnectionDetector(getApplicationContext());
					if (isConnected.isConnectingToInternet()) {
						new UserLogin().execute(Ensieg_AppConstants.appService_login);
					} else {
						Toast.makeText(Ensieg_LoginActivity.this,
								"Please Connect To Your Data Connection and try to login into your Ensieg account",
								Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(Ensieg_LoginActivity.this, "Please Enter login details and try again",
							Toast.LENGTH_LONG).show();
				}
			}
		});
		/**
		 * It is called whenever user forget his password if he want to enter
		 * new password
		 */
		tvforgetPwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tvforgetPwd.setTextColor(getResources().getColor(R.color.clicking_color));
				Ensieg_AppConstants.isUserForgetpassword = true;
				callEmailVeriication();

			}
		});

	}

	/**
	 * It display otp screen to verify user mobile number after registration
	 */
	private void callEmailVeriication() {
		Intent otpIntent = new Intent(Ensieg_LoginActivity.this, Ensieg_EmailOtp_Activity.class);
		startActivity(otpIntent);
		finish();
	}

	public void getNotifications(){

		Toast.makeText(mContext, "Logging Into account", Toast.LENGTH_LONG).show();

		new BackendVariables(this).notificationsAPI.getNotifications(loginData.get(3), new NotificationsAPI.NotificationsCallback() {
			@Override
			public void onComplete(boolean status, int statusCode, String message, List<NotificationModel> invitedNotifications, List<NotificationModel> hostedNotificatons, List<NotificationModel> interestedNotifications) {
				if (status) {
					List<NotificationModel> allNotifications = new ArrayList<NotificationModel>();
					for (int i = 0; i < interestedNotifications.size(); i++) {
						allNotifications.add(interestedNotifications.get(i));
					}
					for (int i = 0; i < hostedNotificatons.size(); i++) {
						allNotifications.add(hostedNotificatons.get(i));
					}
					for (int i = 0; i < interestedNotifications.size(); i++) {
						allNotifications.add(interestedNotifications.get(i));
					}
					NotificationListAdapter notificationListAdapter = new NotificationListAdapter(mContext, R.layout.listview_notification_item, allNotifications);
					NotificationListView.setAdapter(notificationListAdapter);
				} else {
					Toast.makeText(mContext, "Errorin Loading Notifications , Message = " + message, Toast.LENGTH_LONG).show();
				}
			}
		});
	}




	/**
	 * It will set the user entered login details to send for the server
	 * 
	 * @author harika
	 *
	 */
	public class UserLogin extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(Ensieg_LoginActivity.this);
			pDialog.setMessage("Logging into the account");
			pDialog.setCancelable(false);
			pDialog.setCanceledOnTouchOutside(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... urls) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			GcmRegisterBean gcmBeanInstance = GcmRegisterBean.getGcmBeanInstance();

			nameValuePairs.add(new BasicNameValuePair("username", name));
			nameValuePairs.add(new BasicNameValuePair("password", password));
			nameValuePairs.add(new BasicNameValuePair("uin", gcmBeanInstance.getGcmId()));
			nameValuePairs.add(new BasicNameValuePair("deviceos", "1"));
			nameValuePairs.add(new BasicNameValuePair("macid", Ensieg_AppConstants.macAddress));
			nameValuePairs.add(new BasicNameValuePair("firsttimelogin", "0"));
			// Log.d("values are ", mac + " " + phoneNo + " " +
			// userRegisteredId);

			return GET(urls[0], nameValuePairs);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

			if (result.contains("0")) {
				Ensieg_AppConstants.loginSuccess = true;
				parseJSON(result);

			} else {
				if (pDialog != null && pDialog.isShowing())
					pDialog.dismiss();
				Toast.makeText(getApplicationContext(), "No user with Given Details", Toast.LENGTH_LONG).show();
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

	/**
	 * It parse the json value and return the boolean
	 * 
	 * @param res
	 * @return
	 */
	private boolean parseJSON(String res) {

		try {
			JSONObject jsonRootObject = new JSONObject(res);
			// Get the instance of JSONArray that contains JSONObjects
			JSONObject jsonObj = jsonRootObject.optJSONObject("data");

			// Iterate the jsonArray and print the info of JSONObjects
			String likefb = "no", likebm = "no", likevb = "no", likecrickt = "no", likett = "no", likebb = "no",
					liketens = "no";
			for (int i = 0; i < jsonObj.length() - 1; i++) {
				JSONObject jsonObject = jsonObj.getJSONObject("userData");
				String favSports = jsonObject.optString("favSports").toString();
				String phoneNumber = jsonObject.optString("phonenumber").toString();
				String firstname = jsonObject.optString("firstname").toString();
				String lastname = jsonObject.optString("lastname").toString();
				// String network = jsonObject.optString("network").toString();
				String gender = jsonObject.optString("gender").toString();
				String age = jsonObject.optString("age").toString();
				String photo = jsonObject.optString("photo").toString();
				String email = jsonObject.optString("email").toString();
				String sessionId = jsonObj.optString("sessionId").toString();
				String error = jsonRootObject.optString("error").toString();
				Log.d("", firstname + " ln " + lastname + " email " + email + " number " + phoneNumber + " gender "
						+ gender + " age " + age + " photo " + photo + " sessionId " + sessionId + " error " + error);

				ArrayList<Integer> alist = new ArrayList<Integer>();
				if (favSports.contains("1")) {
					likefb = "yes";
					alist.add(1);
				}
				if (favSports.contains("2")) {
					likebb = "yes";
					alist.add(2);
				}
				if (favSports.contains("3")) {
					likevb = "yes";
					alist.add(3);
				}
				if (favSports.contains("4")) {
					liketens = "yes";
					alist.add(4);
				}
				if (favSports.contains("5")) {
					likebm = "yes";
					alist.add(5);
				}
				if (favSports.contains("6")) {
					likett = "yes";
					alist.add(6);
				}
				if (favSports.contains("7")) {
					likecrickt = "yes";
					alist.add(7);
				}
				UserProfileModel mdl = new UserProfileModel(gender, age, favSports.length(), photo, alist, likefb,
						likebb, likevb, liketens, likebm, likett, likecrickt);
				// UserProfileDb profile = new
				// UserProfileDb(getApplicationContext());
				database.Open();
				if (database.numberOfRows_inProfile() > 0) {
					database.deleteAllProfileRows_profile();
				}
				long inserted = database.insertProfileData(mdl);
				Toast.makeText(getApplicationContext(), inserted + " inserted id ", Toast.LENGTH_LONG).show();
				// profile.insertProfileData(mdl);s

				UserLoginModel login = new UserLoginModel(name, password, sessionId);
				// UserLoginDb loginDb = new
				// UserLoginDb(getApplicationContext());
				if (database.numberOfRows_InLogin() > 0) {
					database.deleteAllRows_login();
				}
				database.insertloginData(login);
				ArrayList<String> loginData = database.getLoginData();
				Log.d("", " get login data  ");
				for (int j = 0; j < loginData.size(); j++) {
					Log.d("", " log in values are " + loginData.get(j));
				}
				UserRegisterationModel register = new UserRegisterationModel(firstname, lastname,
						"BITS Pilani,Hyderabad", email, phoneNumber, password);
				// UserRegistrationDb registerDb = new
				// UserRegistrationDb(getApplicationContext());
				if (database.numberOfRows_inRegisters() > 0) {
					database.deleteAllRows_register();
				}

				database.insertRegistrationData(register);
				database.close();
				if (pDialog != null && pDialog.isShowing())
					pDialog.dismiss();
				Intent in = new Intent(Ensieg_LoginActivity.this, Ensieg_HomeActivity.class);
				startActivity(in);
				finish();

			}
			// output.setText(data);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// try {
		// JSONObject jsonObj = new JSONObject(res);
		// //
		// // // Getting JSON Array node
		// JSONObject billPos = jsonObj.getJSONObject("data");
		// //
		// // // looping through All Contacts
		// // for (int i = 0; i < billPos.length(); i++) {
		// // Toast.makeText(getApplicationContext(), billPos + " some ",
		// // Toast.LENGTH_LONG).show();
		// String session = billPos.getString("sessionId");
		// Ensieg_AppConstants.sessionId = session.substring(0,
		// session.length());
		// // Log.d("", session.substring(0, session.length()) + "");
		//
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }

		return false;
	}

	@Override
	public void onBackPressed() {
		Intent welcome = new Intent(Ensieg_LoginActivity.this, Ensieg_WelcomeActivity.class);
		startActivity(welcome);
		finish();

	}

}
