package com.skylka.ensieg.activities;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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

import com.google.gson.Gson;
import com.skylka.ensieg.ConnectionDetector;
import com.skylka.ensieg.R;
import com.skylka.ensieg.constants.Ensieg_AppConstants;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.model.UserProfileModel;
import com.skylka.ensieg.model.UserRegisterationModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class will allow the user to edit his profile data
 * 
 * @author harika
 *
 */
@SuppressWarnings("deprecation")
public class Ensieg_UpdateProfile_Activity extends Activity {

	LinearLayout layout_edit, layout_footer;
	TextView tvHeader, tvWelcome;
	ImageView ivProfile, ivFootball, ivBasketBall, ivVolleyBall, ivTennis, ivBadminton, ivTableTennis, ivCricket;
	EditText etfName, etLname, etNetwork, etPhoneNumber, et_countrycode;
	String footballStr = "footballStr";
	String basketBallStr = "basketBallStr";
	String volleyballStr = "volleyballStr";
	String tennisStr = "tennisStr";
	String badmintonStr = "badmintonStr";
	String tableTennisstr = "tableTennisstr";
	String cricketStr = "cricketStr";
	String password = "";
	int noOfSports_count = 0;
	ArrayList<Integer> selected_Sports = new ArrayList<Integer>();
	// ArrayList<String> selected_SportsList = new ArrayList<String>();
	TextView tvSave;
	String name, lname, network, number, gender, age, emailid;
	Bitmap profileImage;
	ImageView editBack;
	String likefb = "", likebm = "", likevb = "", likecrickt = "", likett = "", likebb = "", liketens = "";
	boolean isUserCreated = false;
	EnsiegDB database;
	int id;

	/**
	 * Through this method user can display this activity layout
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit_profile);
		database = new EnsiegDB(this);
		initializeViews();
	}

	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	//
	// View rootView = inflater.inflate(R.layout.edit_profile, container,
	// false);
	//
	// return rootView;
	// }

	/**
	 * It will initializes all required views for this activity
	 * 
	 */
	@SuppressLint("NewApi")
	public void initializeViews() {
		// TODO Auto-generated method stub

		etfName = (EditText) findViewById(R.id.et_edit_Name);
		etLname = (EditText) findViewById(R.id.et_edit_LastName);
		etNetwork = (EditText) findViewById(R.id.et_edit_network);
		etPhoneNumber = (EditText) findViewById(R.id.et_edit_number);
		ivProfile = (ImageView) findViewById(R.id.iv_editImage);
		ivFootball = (ImageView) findViewById(R.id.iv_edit_football);
		ivBadminton = (ImageView) findViewById(R.id.iv_edit_badminton);
		ivVolleyBall = (ImageView) findViewById(R.id.iv_edit_volleyball);
		ivCricket = (ImageView) findViewById(R.id.iv_edit_cricket);
		ivTableTennis = (ImageView) findViewById(R.id.iv_edit_tbl_tennis);
		ivBasketBall = (ImageView) findViewById(R.id.iv_edit_basketball);
		ivTennis = (ImageView) findViewById(R.id.iv_edit_tennis);
		tvSave = (TextView) findViewById(R.id.tv_uedit_save);
		editBack = (ImageView) findViewById(R.id.updateback);
		et_countrycode = (EditText) findViewById(R.id.et_number_countrycode);
		Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "MYRIADPRO-REGULAR.ttf");
		// tvSave.setTypeface(font);
		etfName.setTypeface(font);
		etLname.setTypeface(font);
		etNetwork.setTypeface(font);

		// UserProfileDb profile = new UserProfileDb(getApplicationContext());
		database.Open();
		if (database.numberOfRows_inProfile() > 0) {
			ArrayList<String> allProfileData = database.getAllProfileData();
			// UserRegistrationDb registration = new
			// UserRegistrationDb(getApplicationContext());
			ArrayList<String> allRegisters = database.getAllRegisters();
			// 1235
			database.close();
			id = Integer.parseInt(allProfileData.get(0));
			etfName.setText(allRegisters.get(1).toString());
			etLname.setText(allRegisters.get(2).toString());
			etNetwork.setText(allRegisters.get(3).toString());
			etPhoneNumber.setText(allRegisters.get(5).toString());
			emailid = allRegisters.get(4).toString();
			gender = allProfileData.get(2).toString();
			age = allProfileData.get(1).toString();
			password = allRegisters.get(6).toString();
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
			profileImage = old_Profile;
			// ivProfile.setImageBitmap(Ensieg_AppConstants.profile);
			Log.d("profile data is ", "profile " + allProfileData.get(4).toString());
			// Log.d("login data is ", "login " + allRegisters.size());
			String allSports = allProfileData.get(4).toString();
			// ArrayList<Integer> favSports =
			// Ensieg_CreateProfileActivity.selected_Sports;
			// Log.d("sports length is ", favSports.size() + " size ");
			// setting selected favorite sports background
			if (likefb.equals("yes")) {
				ivFootball.setBackgroundResource(R.drawable.circle);
				selected_Sports.add(1);
				noOfSports_count++;
			}
			if (likebb.equals("yes")) {
				ivBasketBall.setBackgroundResource(R.drawable.circle);
				noOfSports_count++;
				selected_Sports.add(2);
			}
			if (likevb.equals("yes")) {
				ivVolleyBall.setBackgroundResource(R.drawable.circle);
				noOfSports_count++;
				selected_Sports.add(3);
			}

			if (liketens.equals("yes")) {
				ivTennis.setBackgroundResource(R.drawable.circle);
				noOfSports_count++;
				selected_Sports.add(4);
			}

			if (likebm.equals("yes")) {
				ivBadminton.setBackgroundResource(R.drawable.circle2);
				noOfSports_count++;
				selected_Sports.add(5);

			}

			if (likett.equals("yes")) {
				ivTableTennis.setBackgroundResource(R.drawable.circle);
				noOfSports_count++;
				selected_Sports.add(6);
			}

			if (likecrickt.equals("yes")) {
				ivCricket.setBackgroundResource(R.drawable.circle);
				noOfSports_count++;
				selected_Sports.add(7);
			}
			// calling back functionality by clicking this image
			editBack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onBackPressed();
				}
			});
			// to change his profile we are asking user to select image and crop
			// it
			ivProfile.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					Intent intent = new Intent();
					// call android default gallery
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					// ******** code for crop image
					intent.putExtra("crop", "true");
					intent.putExtra("aspectX", 0);
					intent.putExtra("aspectY", 0);
					intent.putExtra("outputX", 200);
					intent.putExtra("outputY", 150);

					try {
						intent.putExtra("return-data", true);
						startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
					} catch (ActivityNotFoundException e) {
						e.printStackTrace();
					}

					// Intent intent = new Intent();
					// intent.setType("image/*");
					// intent.setAction(Intent.ACTION_GET_CONTENT);
					// startActivityForResult(Intent.createChooser(intent,
					// "SelectPicture"), 1);

				}
			});
			// allow the user to select his favorite sports
			ivFootball.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (selected_Sports.contains(1)) {
						selected_Sports.remove(selected_Sports.indexOf(1));
						likefb = "no";
						// selected_SportsList.remove(footballStr);
						--noOfSports_count;
						ivFootball.setBackgroundResource(R.color.white);
					} else {
						selected_Sports.add(1);
						++noOfSports_count;
						likefb = "yes";
						// selected_SportsList.add(footballStr);
						ivFootball.setBackgroundResource(R.drawable.circle);
					}
				}
			});

			ivBasketBall.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (selected_Sports.contains(2)) {
						selected_Sports.remove(selected_Sports.indexOf(2));
						likebb = "no";
						// selected_SportsList.remove(badmintonStr);
						--noOfSports_count;
						ivBasketBall.setBackgroundResource(R.color.white);
					} else {
						selected_Sports.add(2);
						// selected_SportsList.add(badmintonStr);
						likebb = "yes";
						++noOfSports_count;
						ivBasketBall.setBackgroundResource(R.drawable.circle2);
					}
				}
			});
			ivVolleyBall.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (selected_Sports.contains(3)) {
						selected_Sports.remove(selected_Sports.indexOf(3));
						// selected_SportsList.remove(volleyballStr);
						likevb = "no";
						--noOfSports_count;
						ivVolleyBall.setBackgroundResource(R.color.white);
					} else {
						selected_Sports.add(3);
						// selected_SportsList.add(volleyballStr);
						likevb = "yes";
						++noOfSports_count;
						ivVolleyBall.setBackgroundResource(R.drawable.circle);
					}
				}
			});
			ivTennis.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (selected_Sports.contains(4)) {
						selected_Sports.remove(selected_Sports.indexOf(4));
						// selected_SportsList.remove(tennisStr);
						liketens = "no";
						--noOfSports_count;
						ivTennis.setBackgroundResource(R.color.white);
					} else {
						selected_Sports.add(4);
						// selected_SportsList.add(tennisStr);
						liketens = "yes";
						++noOfSports_count;
						ivTennis.setBackgroundResource(R.drawable.circle);
					}
				}
			});

			ivBadminton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (selected_Sports.contains(5)) {
						selected_Sports.remove(selected_Sports.indexOf(5));
						// selected_SportsList.remove(basketBallStr);
						likebm = "no";
						--noOfSports_count;
						ivBadminton.setBackgroundResource(R.color.white);
					} else {
						selected_Sports.add(5);
						++noOfSports_count;
						// selected_SportsList.add(basketBallStr);
						likebm = "yes";
						ivBadminton.setBackgroundResource(R.drawable.circle);
					}
				}
			});

			ivTableTennis.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (selected_Sports.contains(6)) {
						selected_Sports.remove(selected_Sports.indexOf(6));
						// selected_SportsList.remove(tableTennisstr);
						likett = "no";
						--noOfSports_count;
						ivTableTennis.setBackgroundResource(R.color.white);
					} else {
						selected_Sports.add(6);
						++noOfSports_count;
						// selected_SportsList.add(tableTennisstr);
						likett = "yes";
						ivTableTennis.setBackgroundResource(R.drawable.circle);
					}
				}
			});

			ivCricket.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (selected_Sports.contains(7)) {
						selected_Sports.remove(selected_Sports.indexOf(7));
						--noOfSports_count;
						// selected_SportsList.remove(cricketStr);
						likecrickt = "no";

						ivCricket.setBackgroundResource(R.color.white);
					} else {
						selected_Sports.add(7);
						++noOfSports_count;
						// selected_SportsList.add(cricketStr);
						likecrickt = "yes";
						ivCricket.setBackgroundResource(R.drawable.circle);
					}
				}
			});
		} else {
			database.close();
			Toast.makeText(getApplicationContext(), "No user Please Login or Register", Toast.LENGTH_LONG).show();
		}

		etfName.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				verifyingUserNames(etfName.getText().toString(), 1);

			}
		});
		etLname.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				verifyingUserNames(etfName.getText().toString(), 1);
				verifyingUserNames(etLname.getText().toString(), 2);

			}
		});
		etPhoneNumber.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				etPhoneNumber.setHint("");
				verifyingUserNames(etfName.getText().toString(), 1);
				verifyingUserNames(etLname.getText().toString(), 2);
				verifying_phonenumber(etPhoneNumber.getText().toString());
				et_countrycode.setVisibility(View.VISIBLE);

			}
		});
		etfName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				verifyingUserNames(etfName.getText().toString(), 1);

			}
		});
		etLname.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				verifyingUserNames(etfName.getText().toString(), 1);
				verifyingUserNames(etLname.getText().toString(), 2);
			}
		});
		etPhoneNumber.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				verifyingUserNames(etfName.getText().toString(), 1);
				verifyingUserNames(etLname.getText().toString(), 2);
				verifying_phonenumber(etPhoneNumber.getText().toString());
			}
		});
		// all edited details are saving in server
		tvSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				name = etfName.getText().toString();
				lname = etLname.getText().toString();
				network = etNetwork.getText().toString();
				number = etPhoneNumber.getText().toString();
				ConnectionDetector isConnected = new ConnectionDetector(getApplicationContext());
				if (verifyingUserNames(name, 1) && verifyingUserNames(lname, 2) && verifying_phonenumber(number)
						&& userSelectedSports()) {
					if (isConnected.isConnectingToInternet()) {
						new UpdateProfile().execute(Ensieg_AppConstants.appService_update);
					} else {
						Toast.makeText(getApplicationContext(),
								"Please Connect to your Data Connection And try to Edit Your Profile",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		});

	}

	private boolean userSelectedSports() {
		if (selected_Sports.size() <= 0) {
			Toast.makeText(getApplicationContext(), "Please Select Your Favorite Sports", Toast.LENGTH_LONG).show();
			return false;

		}
		return true;
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
		return "";
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
			etPhoneNumber.setError("Please enter correct phone number");
			return false;
			// Toast.makeText(this, "Phone Number must be in the form
			// 9999999999", Toast.LENGTH_LONG).show();
		}
		etPhoneNumber.setError(null);
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

				etfName.setError("Please enter your FirstName correctly ");
				return false;
				// Toast.makeText(this,
				// "Please enter your FirstName correctly eg: Skylka (minimum
				// length is 1 and maximum length is 50)",
				// Toast.LENGTH_LONG).show();
			} else if (position == 2) {
				etLname.setError("please enter correct last name");
				return false;
				// Toast.makeText(this,
				// "Please enter your LastName correctly eg: Skylka (minimum
				// length is 1 and maximum length is 50)",
				// Toast.LENGTH_LONG).show();
			}

		}
		if (position == 1) {
			etfName.setError(null);
		}
		if (position == 2) {
			etLname.setError(null);
		}
		return true;
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
					return null;
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent) After user selected image result we are setting
	 * image to profile with cropping
	 */

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1 && data != null) {
			Bundle extras2 = data.getExtras();
			if (extras2 != null) {
				Bitmap photo = extras2.getParcelable("data");
				profileImage = getRoundedCroppedBitmap(photo, 150);
				ivProfile.setImageBitmap(profileImage);
				Ensieg_AppConstants.profile = profileImage;

			}
		} else {
			Toast.makeText(getApplicationContext(), "You are not selected the image", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 
	 * @param bitmap
	 * @param radius
	 * @return
	 * 
	 * 		calling this method for circular crop
	 */
	public static Bitmap getRoundedCroppedBitmap(Bitmap bitmap, int radius) {
		Bitmap finalBitmap;
		if (bitmap.getWidth() != radius || bitmap.getHeight() != radius)
			finalBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius, false);
		else
			finalBitmap = bitmap;
		Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(), finalBitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, finalBitmap.getWidth(), finalBitmap.getHeight());

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(Color.parseColor("#BAB399"));
		canvas.drawCircle(finalBitmap.getWidth() / 2 + 0.7f, finalBitmap.getHeight() / 2 + 0.7f,
				finalBitmap.getWidth() / 2 + 0.1f, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(finalBitmap, rect, rect, paint);

		return output;
	}

	/**
	 * Through this class we are calling update profile service
	 * 
	 * @author harika
	 *
	 */
	private class UpdateProfile extends AsyncTask<String, Void, String> {
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(Ensieg_UpdateProfile_Activity.this);
			pDialog.setMessage("Saving Edit Profile Details");
			pDialog.setCancelable(false);
			pDialog.setCanceledOnTouchOutside(false);
			pDialog.show();
		}

		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String... urls) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			// UserLoginDb login = new UserLoginDb(getApplicationContext());
			database.Open();
			ArrayList<String> allRegisters1 = database.getLoginData();
			database.close();
			Log.d("", "size is " + allRegisters1.size());
			nameValuePairs.add(new BasicNameValuePair("operation", "1"));
			nameValuePairs.add(new BasicNameValuePair("sessionid", allRegisters1.get(3)));
			nameValuePairs.add(new BasicNameValuePair("photo", Ensieg_AppConstants.profile + ""));
			nameValuePairs.add(new BasicNameValuePair("count", noOfSports_count + ""));
			nameValuePairs.add(new BasicNameValuePair("first_name", name));
			nameValuePairs.add(new BasicNameValuePair("last_name", lname));
			nameValuePairs.add(new BasicNameValuePair("phone_number", number));
			nameValuePairs.add(new BasicNameValuePair("email", emailid));
			nameValuePairs.add(new BasicNameValuePair("gender", gender));
			nameValuePairs.add(new BasicNameValuePair("age", age));
			for (int i = 0; i < noOfSports_count; i++) {
				nameValuePairs.add(new BasicNameValuePair("sports-" + i, selected_Sports.get(i) + ""));
			}
			System.out.println("Surekha ____________________________" + new Gson().toJson(nameValuePairs) + "..."
					+ new Gson().toJson(selected_Sports));

			return GET(urls[0], nameValuePairs);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
			if (pDialog != null)
				pDialog.dismiss();

			JSONObject jsonRootObject;
			try {
				jsonRootObject = new JSONObject(result);
				String error = jsonRootObject.optString("error").toString();
				if (error.equals("0")) {
					Bitmap bmap;
					String stMap = null;
					if (profileImage != null) {
						stMap = BitMapToString(profileImage);
					}
					UserRegisterationModel model1 = new UserRegisterationModel(name, lname, network, emailid, number,
							password);
					Log.d("", " user edited values " + name + " " + lname + " " + network + " " + emailid + " " + number + " " + password);
					UserProfileModel model = new UserProfileModel(gender, age, noOfSports_count, stMap, selected_Sports,
							likefb, likebb, likevb, liketens, likebm, likett, likecrickt);
					database.Open();
					if (database.numberOfRows_inProfile() > 0) {
						database.deleteAllProfileRows_profile();
					}
					if (database.numberOfRows_inRegisters() > 0) {
						database.deleteAllRows_register();
					}
					database.insertProfileData(model);
					database.insertRegistrationData(model1);
					database.close();
					Intent in = new Intent(Ensieg_UpdateProfile_Activity.this, Ensieg_ProfileActivity.class);
					startActivity(in);
					finish();

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
	}

}
