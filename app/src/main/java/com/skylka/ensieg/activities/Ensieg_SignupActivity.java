package com.skylka.ensieg.activities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.skylka.ensieg.R;
import com.skylka.ensieg.constants.Ensieg_AppConstants;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.model.UserRegisterationModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
/**
 * It will collect all signup information from user
 * 
 * @author harika
 *
 */
public class Ensieg_SignupActivity extends Activity {

	Button btNext;
	EditText etfname, etlname, etnetwork, etmail, etphone, etpwd, etcountryCode;
	ImageView ivBack;
	String phoneNo;
	long userRegisteredId;
	boolean isUserRegistered = false;
	TextView tvSignUpTitle;
	TextView tvName, tvLname, tvMail, tvMobile, tvPwd;
	EnsiegDB database;
	// TextView tvName_anim, tvLname_anim, tvMail_anim, tvMobile_anim,
	// tvPwd_anim;

	/*
	 * It will diplay the view of this activity (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_registration);
		database = new EnsiegDB(this);
		initializeViews();
		Typeface font = Typeface.createFromAsset(getAssets(), "MYRIADPRO-REGULAR.ttf");
		btNext.setTypeface(font);
		etfname.setTypeface(font);
		etlname.setTypeface(font);
		etnetwork.setTypeface(font);
		etmail.setTypeface(font);
		etphone.setTypeface(font);
		etpwd.setTypeface(font);
		tvSignUpTitle.setTypeface(font);
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showPopUp();

			}
		});
	}

	@SuppressLint("NewApi")
	public void showPopUp() {

		Dialog dialog = new Dialog(Ensieg_SignupActivity.this);

		AlertDialog.Builder aDialog = new AlertDialog.Builder(Ensieg_SignupActivity.this);
		aDialog.setMessage("Are you sure you want to Go back Your details are not saved");

		aDialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// continue with delete
				// onBackPressed();
				Intent in = new Intent(Ensieg_SignupActivity.this, Ensieg_WelcomeActivity.class);
				startActivity(in);
				finish();
			}
		});
		aDialog.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		aDialog.show();
	}

	/**
	 * It will initializes all required views for the user
	 */
	private void initializeViews() {

		btNext = (Button) findViewById(R.id.btNext);
		etfname = (EditText) findViewById(R.id.usr_reg_fname);
		etlname = (EditText) findViewById(R.id.usr_reg_lname);
		etmail = (EditText) findViewById(R.id.usr_reg_email);
		etnetwork = (EditText) findViewById(R.id.et_usr_reg_network);
		etphone = (EditText) findViewById(R.id.usr_reg_mobilenumber);
		etpwd = (EditText) findViewById(R.id.et_pwd);
		ivBack = (ImageView) findViewById(R.id.signupback);
		tvSignUpTitle = (TextView) findViewById(R.id.register_txt_header);
		tvName = (TextView) findViewById(R.id.tvfname);
		tvLname = (TextView) findViewById(R.id.tvlname);
		tvMail = (TextView) findViewById(R.id.tvemail);
		tvMobile = (TextView) findViewById(R.id.tvmobile);
		tvPwd = (TextView) findViewById(R.id.tvpwd);
		etcountryCode = (EditText) findViewById(R.id.usr_reg_countrycode);
		// tvMail_anim = (TextView) findViewById(R.id.tvmail_anim);
		nextClick();

	}

	/**
	 * It is called when user clicked on next It have all user entered data and
	 * save in the local db
	 */
	private void nextClick() {
		etfname.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					if (etfname.getText().toString().length() > 0)
						verifyingUserNames(etfname.getText().toString(), 1);
					etfname.setHintTextColor(getResources().getColor(R.color.clicking_color));
					etlname.setHintTextColor(getResources().getColor(android.R.color.black));
					etmail.setHintTextColor(getResources().getColor(android.R.color.black));
					etphone.setHintTextColor(getResources().getColor(android.R.color.black));
					etpwd.setHintTextColor(getResources().getColor(android.R.color.black));
					tvName.setBackgroundColor(getResources().getColor(R.color.clicking_color));
					tvLname.setBackgroundColor(getResources().getColor(android.R.color.black));
					tvMobile.setBackgroundColor(getResources().getColor(android.R.color.black));
					tvMail.setBackgroundColor(getResources().getColor(android.R.color.black));
					tvPwd.setBackgroundColor(getResources().getColor(android.R.color.black));
				} else {

				}
			}
		});
		etlname.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					if (etfname.getText().toString().length() > 0)
						verifyingUserNames(etfname.getText().toString(), 1);
					if (etlname.getText().toString().length() > 0)
						verifyingUserNames(etlname.getText().toString(), 2);
					etfname.setHintTextColor(getResources().getColor(android.R.color.black));
					etlname.setHintTextColor(getResources().getColor(R.color.clicking_color));
					etmail.setHintTextColor(getResources().getColor(android.R.color.black));
					etphone.setHintTextColor(getResources().getColor(android.R.color.black));
					etpwd.setHintTextColor(getResources().getColor(android.R.color.black));

					tvName.setBackgroundColor(getResources().getColor(android.R.color.black));
					tvLname.setBackgroundColor(getResources().getColor(R.color.clicking_color));
					tvMobile.setBackgroundColor(getResources().getColor(android.R.color.black));
					tvMail.setBackgroundColor(getResources().getColor(android.R.color.black));
					tvPwd.setBackgroundColor(getResources().getColor(android.R.color.black));
				}

			}
		});

		etmail.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// SlideToAbove();
				if (hasFocus) {
					if (etlname.getText().toString().length() > 0)
						verifyingUserNames(etlname.getText().toString(), 2);
					if (etmail.getText().toString().length() > 0)
						verifyEmail(etmail.getText().toString());
					etfname.setHintTextColor(getResources().getColor(android.R.color.black));
					etlname.setHintTextColor(getResources().getColor(android.R.color.black));
					etmail.setHintTextColor(getResources().getColor(R.color.clicking_color));
					etphone.setHintTextColor(getResources().getColor(android.R.color.black));
					etpwd.setHintTextColor(getResources().getColor(android.R.color.black));

					tvName.setBackgroundColor(getResources().getColor(android.R.color.black));
					tvLname.setBackgroundColor(getResources().getColor(android.R.color.black));
					tvMobile.setBackgroundColor(getResources().getColor(android.R.color.black));
					tvMail.setBackgroundColor(getResources().getColor(R.color.clicking_color));
					tvPwd.setBackgroundColor(getResources().getColor(android.R.color.black));
				}
			}
		});
		etphone.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					if (etmail.getText().toString().length() > 0)
						verifyEmail(etmail.getText().toString());
					if (etphone.getText().toString().length() > 0)
						verifying_phonenumber(etphone.getText().toString());
					etfname.setHintTextColor(getResources().getColor(android.R.color.black));
					etlname.setHintTextColor(getResources().getColor(android.R.color.black));
					etmail.setHintTextColor(getResources().getColor(android.R.color.black));
					// etphone.setHintTextColor(getResources().getColor(android.R.color.black));
					etpwd.setHintTextColor(getResources().getColor(android.R.color.black));
					// etphone.setText("");
					etphone.setHint("");
					etcountryCode.setVisibility(View.VISIBLE);
					tvName.setBackgroundColor(getResources().getColor(android.R.color.black));
					tvLname.setBackgroundColor(getResources().getColor(android.R.color.black));
					tvMobile.setBackgroundColor(getResources().getColor(R.color.clicking_color));
					tvMail.setBackgroundColor(getResources().getColor(android.R.color.black));
					tvPwd.setBackgroundColor(getResources().getColor(android.R.color.black));
				}
			}

		});
		etpwd.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					if (etphone.getText().toString().length() > 0)
						verifying_phonenumber(etphone.getText().toString());
					if (etpwd.getText().toString().length() > 0)
						verifying_password(etpwd.getText().toString());
					etfname.setHintTextColor(getResources().getColor(android.R.color.black));
					etlname.setHintTextColor(getResources().getColor(android.R.color.black));
					etmail.setHintTextColor(getResources().getColor(android.R.color.black));
					etphone.setHintTextColor(getResources().getColor(android.R.color.black));
					etpwd.setHintTextColor(getResources().getColor(R.color.clicking_color));

					tvName.setBackgroundColor(getResources().getColor(android.R.color.black));
					tvLname.setBackgroundColor(getResources().getColor(android.R.color.black));
					tvMobile.setBackgroundColor(getResources().getColor(android.R.color.black));
					tvMail.setBackgroundColor(getResources().getColor(android.R.color.black));
					tvPwd.setBackgroundColor(getResources().getColor(R.color.clicking_color));
				}
			}
		});

		

		// etphone.addTextChangedListener(phoneWatcher);

		etfname.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				verifyingUserNames(etfname.getText().toString().trim(), 1);
			}
		});
		etlname.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				verifyingUserNames(etfname.getText().toString().trim(), 1);
				verifyingUserNames(etlname.getText().toString().trim(), 2);

			}
		});
		etmail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				verifyingUserNames(etfname.getText().toString().trim(), 1);
				verifyingUserNames(etlname.getText().toString().trim(), 2);
				verifyEmail(etmail.getText().toString());

				// SlideToAbove();
			}
		});
		etphone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				verifyingUserNames(etfname.getText().toString().trim(), 1);
				verifyingUserNames(etlname.getText().toString().trim(), 2);
				verifyEmail(etmail.getText().toString().trim());
				verifying_phonenumber(etphone.getText().toString().trim());
			}
		});
		etpwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				verifyingUserNames(etfname.getText().toString().trim(), 1);
				verifyingUserNames(etlname.getText().toString().trim(), 2);
				verifyEmail(etmail.getText().toString().trim());
				verifying_password(etpwd.getText().toString().trim());
				verifying_phonenumber(etphone.getText().toString().trim());
			}
		});
		btNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				verifying_password(etpwd.getText().toString().trim());
				String fName = etfname.getText().toString().trim();
				String lName = etlname.getText().toString().trim();
				String networktxt = etnetwork.getText().toString().trim();
				String email = etmail.getText().toString().trim();
				phoneNo = etphone.getText().toString().trim();
				String password = etpwd.getText().toString().trim();

				if (verifyingUserNames(fName, 1) && verifyingUserNames(lName, 2) && verifyEmail(email)
						&& verifying_phonenumber(phoneNo) && verifying_password(password)) {
					UserRegisterationModel registeredModel = new UserRegisterationModel(fName, lName, networktxt, email,
							phoneNo, password);

					// UserRegistrationDb registeredDb = new
					// UserRegistrationDb(getApplicationContext());
					database.Open();
					if (database.numberOfRows_inRegisters() > 0)
						database.deleteAllRows_register();
					userRegisteredId = database.insertRegistrationData(registeredModel);
					database.close();
					Toast.makeText(Ensieg_SignupActivity.this, userRegisteredId + "", Toast.LENGTH_LONG).show();
					// callOtpActivity();

					new IsUserExist().execute(Ensieg_AppConstants.appService_userchecking);
				}
				// callOtpActivity();
			}
		});

	}

	/**
	 * After successful of user entered details we will call for the otp
	 */
	private void callOtpActivity() {
		// new GetOtp().execute(Ensieg_AppConstants.GENERATEOTP);
		Intent otpIntent = new Intent(Ensieg_SignupActivity.this, Ensieg_OTPActivity.class);
		startActivity(otpIntent);
		finish();
	}

	/**
	 * It verifies phone number
	 * 
	 * @param uPhone
	 * @return
	 */
	private boolean verifying_phonenumber(String uPhone) {
		// TODO Auto-generated method stub
		Pattern pattern = Pattern.compile("\\d{10}");
		Matcher matcher = pattern.matcher(uPhone);
		if (!matcher.matches()) {
			etphone.setError("Please enter correct phone number");
			return false;
			// Toast.makeText(this, "Phone Number must be in the form
			// 9999999999", Toast.LENGTH_LONG).show();
		}
		etphone.setError(null);
		return true;
	}

	/**
	 * It will verifies the user entered details
	 * 
	 * @param uEmail
	 * @param uPhone
	 * @return
	 */
	private boolean verifyEmail(String uEmail) {
		Pattern patternName;
		Matcher matcherName;
		String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";
		patternName = Pattern.compile(USERNAME_PATTERN);
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		// Pattern pattern = Pattern.compile("\\d{10}");
		// Matcher matcher = pattern.matcher(uPhone);

		if (!uEmail.matches(emailPattern)) {
			etmail.setError("please enter correct mail");
			// Toast.makeText(this, "Please enter correct email pattern like
			// mail@mailname.com", Toast.LENGTH_LONG).show();
			return false;
		}
		etmail.setError(null);
		return true;

	}

	/**
	 * It will verifies user entered fName,lName,network
	 * 
	 * @param uName
	 * @param position
	 * @return
	 */
	private boolean verifyingUserNames(String uName, int position) {
		Pattern patternName;
		Matcher matcherName;
		String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";
		patternName = Pattern.compile(USERNAME_PATTERN);
		matcherName = patternName.matcher(uName);
		//
		// [a-zA-z]+([ '-][a-zA-Z]+)*
		if (!uName.matches("^[\\p{L} .'-]+$") || !(uName.length() > 0 && uName.length() < 50)) {
			if (position == 1) {

				etfname.setError("Please enter your FirstName correctly ");

			} else if (position == 2) {
				etlname.setError("please enter correct last name");

			}

			return false;
		}
		if (position == 1) {
			etfname.setError(null);
		} else if (position == 2) {
			etlname.setError(null);
		}
		return true;
	}

	/**
	 * It will verifies the password
	 * 
	 * @param pwd
	 * @return
	 */
	private boolean verifying_password(String pwd) {
		if (pwd.contains(" ") || pwd.length() < 8) {
			etpwd.setError("enter correct password");
			return false;
		}
		etpwd.setError(null);
		return true;

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

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		showPopUp();
	}

	public void SlideToAbove() {
		Animation slide = null;
		slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -0.3f);

		slide.setDuration(400);
		slide.setFillAfter(true);
		slide.setFillEnabled(true);
		// tvMail_anim.startAnimation(slide);

		slide.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// tvMail_anim.clearAnimation();
				// super.onAnimationEnd(animation);
				// tvMail_anim.setVisibility(View.VISIBLE);
				// LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				// tvMail_anim.getWidth(), tvMail_anim.getHeight());
				// // lp.setMargins(0, 0, 0, 0);
				//// lp.addRule(LinearLayout.ALIGN_PARENT_TOP);
				// tvMail_anim.setLayoutParams(lp);

			}

		});

	}

	/**
	 * It will set and call the data to save the profile data to user
	 * 
	 * @author harika
	 *
	 */
	private class IsUserExist extends AsyncTask<String, Void, String> {
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// callLoader();
			// pDialog = new ProgressDialog(Ensieg_CreateProfileActivity.this);
			// pDialog.setMessage("Saving Profile Details");
			// pDialog.setCanceledOnTouchOutside(false);
			// pDialog.setCancelable(false);
			// pDialog.show();

		}

		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String... urls) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			nameValuePairs.add(new BasicNameValuePair("apikey", "539439645974657"));
			nameValuePairs.add(new BasicNameValuePair("email", etmail.getText().toString()));
			nameValuePairs.add(new BasicNameValuePair("phonenumber", etphone.getText().toString()));

			return GET(urls[0], nameValuePairs);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			// Toast.makeText(getApplicationContext(), result,
			// Toast.LENGTH_LONG).show();
			JSONObject jsonRootObject;
			try {
				jsonRootObject = new JSONObject(result);
				String error = jsonRootObject.optString("error").toString();
				if (error.equals("0")) {
					Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_LONG).show();
					callOtpActivity();
				} else {
					Toast.makeText(getApplicationContext(), "OOPS,Something went wrong!", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
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

}
