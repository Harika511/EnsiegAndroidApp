package com.skylka.ensieg.activities;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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
import org.json.JSONObject;

import com.skylka.ensieg.R;
import com.skylka.ensieg.constants.Ensieg_AppConstants;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.model.UserProfileModel;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class Ensieg_ProfileActivity extends ActionBarActivity implements OnClickListener {
	TextView tvEdit;
	LinearLayout layout1;
	String password,likefb = "", likebm = "", likevb = "", likecrickt = "", likett = "", likebb = "", liketens = "";
	ImageView ivProfile, ivBack, ivFootball, ivBasketBall, ivVolleyBall, ivTennis, ivBadminton, ivTableTennis,
			ivCricket;
	EnsiegDB database;

	public Ensieg_ProfileActivity() {
	}

	RelativeLayout rlFootball, rlVolleyBall, rlBasketball, rlTennis, rlBadminton, rlTableTennis, rlCricket;
	TextView tvName, tvAge, tvNetwork, tvPhoneNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile);
		database = new EnsiegDB(this);
		initializeViews();
		getSupportActionBar().setTitle("PROFILE");
		getSupportActionBar().setIcon(R.drawable.back);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setProfileData();
	}
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	//
	// View rootView = inflater.inflate(R.layout.user_profile, container,
	// false);
	//
	// return rootView;
	// }

	@SuppressLint("NewApi")
	// @Override
	// public void onViewCreated(View view, Bundle savedInstanceState) {
	// super.onViewCreated(view, savedInstanceState);
	public void initializeViews() {
		ivProfile = (ImageView) findViewById(R.id.iv_profile_display);
		tvName = (TextView) findViewById(R.id.tv_name_profile);
		tvAge = (TextView) findViewById(R.id.tv_age_profile);
		tvNetwork = (TextView) findViewById(R.id.tv_network_profile);
		tvPhoneNumber = (TextView) findViewById(R.id.tv_pNumber_profile);
		rlFootball = (RelativeLayout) findViewById(R.id.rlfb);
		rlVolleyBall = (RelativeLayout) findViewById(R.id.rlvb);
		rlBasketball = (RelativeLayout) findViewById(R.id.rlbb);
		rlTennis = (RelativeLayout) findViewById(R.id.rltennis);
		rlTableTennis = (RelativeLayout) findViewById(R.id.rltt);
		rlBadminton = (RelativeLayout) findViewById(R.id.rlbm);
		rlCricket = (RelativeLayout) findViewById(R.id.rlcricket);
		ivFootball = (ImageView) findViewById(R.id.iv_fb_Profile);
		ivBasketBall = (ImageView) findViewById(R.id.iv_bb_Profile);
		ivVolleyBall = (ImageView) findViewById(R.id.iv_vb_Profile);
		ivTennis = (ImageView) findViewById(R.id.iv_tennis_Profile);
		ivBadminton = (ImageView) findViewById(R.id.iv_bm_Profile);
		ivTableTennis = (ImageView) findViewById(R.id.iv_tt_Profile);
		ivCricket = (ImageView) findViewById(R.id.iv_cricket_Profile);
		// ivBack = (ImageView) findViewById(R.id.back);
		Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "MYRIADPRO-REGULAR.ttf");
		tvName.setTypeface(font);
		tvNetwork.setTypeface(font);
		tvAge.setTypeface(font);
		tvPhoneNumber.setTypeface(font);

		// UserProfileDb profile = new UserProfileDb(getApplicationContext());
		
	}
    private void setProfileData(){
    	
    	database.Open();
		if (database.numberOfRows_inProfile() > 0) {
			ArrayList<String> allProfileData = database.getAllProfileData();

			/*
			 * UserRegistrationDb registration = new
			 * UserRegistrationDb(getApplicationContext());
			 */
			if (database.numberOfRows_inRegisters() > 0) {
				ArrayList<String> allRegisters = database.getAllRegisters();
				Log.d("", " profile db size " + allProfileData.size() + " " + allRegisters.size() + " "
						+ allRegisters.get(1).toString());

				tvName.setText(allRegisters.get(1).toString() + " " + allRegisters.get(2).toString());
				tvAge.setText(allProfileData.get(2).toString() + "," + allProfileData.get(1).toString());
				tvNetwork.setText(allRegisters.get(3).toString());
				tvPhoneNumber.setText(allRegisters.get(5).toString());
				
				Log.d("profile db size is ", "profile db size is " + allProfileData.size());
				likefb = allProfileData.get(4).toString();
				likebb = allProfileData.get(5).toString();
				likevb = allProfileData.get(6).toString();
				liketens = allProfileData.get(7).toString();
				likebm = allProfileData.get(8).toString();
				likett = allProfileData.get(9).toString();
				likecrickt = allProfileData.get(10).toString();
				Bitmap old_Profile = StringToBitMap(allProfileData.get(3));
				if (old_Profile != null) {
					ivProfile.setImageBitmap(old_Profile);
				} else {
					ivProfile.setBackgroundResource(R.drawable.edit_profile);
				}
				// ivProfile.setImageBitmap(Ensieg_AppConstants.profile);
				Log.d("profile data is ", "profile " + allProfileData.get(4).toString());
				// Log.d("login data is ", "login " + allRegisters.size());
				String allSports = allProfileData.get(4).toString();
				// ArrayList<Integer> favSports =
				// Ensieg_CreateProfileActivity.selected_Sports;
				// Log.d("sports length is ", favSports.size() + " size ");
				// setting selected favorite sports background

				// ivBack.setOnClickListener(new OnClickListener() {
				//
				// @Override
				// public void onClick(View v) {
				// onBackPressed();
				// }
				// });
				if (likefb.equals("yes")) {
					ivFootball.setBackgroundResource(R.drawable.circle);
					rlFootball.setVisibility(View.VISIBLE);
				}
				if (likebb.equals("yes")) {
					ivBasketBall.setBackgroundResource(R.drawable.circle);
					rlBasketball.setVisibility(View.VISIBLE);
				}
				if (likevb.equals("yes")) {
					ivVolleyBall.setBackgroundResource(R.drawable.circle);
					rlVolleyBall.setVisibility(View.VISIBLE);
				}

				if (liketens.equals("yes")) {
					ivTennis.setBackgroundResource(R.drawable.circle);
					rlTennis.setVisibility(View.VISIBLE);
				}

				if (likebm.equals("yes")) {
					ivBadminton.setBackgroundResource(R.drawable.circle2);
					rlBadminton.setVisibility(View.VISIBLE);
				}

				if (likett.equals("yes")) {
					ivTableTennis.setBackgroundResource(R.drawable.circle);
					rlTableTennis.setVisibility(View.VISIBLE);
				}

				if (likecrickt.equals("yes")) {
					ivCricket.setBackgroundResource(R.drawable.circle);
					rlCricket.setVisibility(View.VISIBLE);
				}

			}

		}
		database.close();
    	
    }
	/**
	 * It will convert bitmap to string
	 * 
	 * @param bitmap
	 * @return
	 */
	public String BitMapToString(Bitmap bitmap) {
		if (bitmap != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
			byte[] b = baos.toByteArray();
			String temp = Base64.encodeToString(b, Base64.DEFAULT);
			return temp;
		}
		return null;
	}

	/**
	 * It will convert String to Bitmap
	 * 
	 * @param encodedString
	 * @return bitmap (from given string)
	 */
	public Bitmap StringToBitMap(String encodedString) {
		if (encodedString != null) {
			if (encodedString.length() > 0) {
				try {
					byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
					Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
					return bitmap;
				} catch (Exception e) {
					e.getMessage();

				}
			}
		}
		return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		if (id == android.R.id.home) {
			Intent update = new Intent(Ensieg_ProfileActivity.this, Ensieg_HomeActivity.class);
			startActivity(update);
			finish();
			return true;
		}

		if (id == R.id.editProfile) {
			Intent update = new Intent(Ensieg_ProfileActivity.this, Ensieg_Password_ForEdit_Profile_Activity.class);
			startActivity(update);
//			 finish();
			return true;
		}
		if (id == R.id.signout) {
			// UserLoginDb login = new UserLoginDb(getApplicationContext());
			database.Open();
			new UserLogout().execute(Ensieg_AppConstants.appService_logout);
			//// database.deleteAllRows_login();
			// // UserProfileDb profile = new
			// // UserProfileDb(getApplicationContext());
			// database.deleteAllProfileRows_profile();
			// database.close();
			// Toast.makeText(getApplicationContext(), "You are Successfully
			// Signed out", Toast.LENGTH_LONG).show();

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * It will set and call the data to save the profile data to user
	 * 
	 * @author harika
	 *
	 */
	private class UserLogout extends AsyncTask<String, Void, String> {
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
			// nameValuePairs.add(new BasicNameValuePair(name, value))
			EnsiegDB database1 = new EnsiegDB(getApplicationContext());
			// database1.Open();
			ArrayList<String> allRegisters1 = database1.getLoginData();
			// database1.close();

			nameValuePairs.add(new BasicNameValuePair("username", allRegisters1.get(1)));
			nameValuePairs.add(new BasicNameValuePair("sessionid", allRegisters1.get(3)));

			return GET(urls[0], nameValuePairs);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
			// stopLoader();
			// if (pDialog != null && pDialog.isShowing())
			// pDialog.dismiss();
			if (result.contains("0")) {
				database.deleteAllRows_login();
				// UserProfileDb profile = new
				// UserProfileDb(getApplicationContext());
				database.deleteAllProfileRows_profile();
				database.close();
				Toast.makeText(getApplicationContext(), "You Logged out Successfully", Toast.LENGTH_LONG).show();
				Intent welcome = new Intent(Ensieg_ProfileActivity.this, Ensieg_WelcomeActivity.class);
				startActivity(welcome);
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "OOPS,Something went wrong!", Toast.LENGTH_LONG).show();
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent welcome = new Intent(Ensieg_ProfileActivity.this, Ensieg_HomeActivity.class);
		startActivity(welcome);
		finish();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
@Override
protected void onResume() {
	super.onResume();
	setProfileData();
}
	/*
	 * @Override public void onClick(View v) { switch (v.getId()) { case
	 * android.R.id.home: Ensieg_ProfileActivity.this.finish(); break;
	 * 
	 * default: break; }
	 * 
	 * }
	 */
}
