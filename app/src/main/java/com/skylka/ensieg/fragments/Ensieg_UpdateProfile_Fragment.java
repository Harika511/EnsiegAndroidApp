package com.skylka.ensieg.fragments;

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

import android.annotation.SuppressLint;
import android.app.Fragment;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
public class Ensieg_UpdateProfile_Fragment extends Fragment {

	LinearLayout layout_edit, layout_footer;
	TextView tvHeader, tvWelcome;
	ImageView ivProfile, ivFootball, ivBasketBall, ivVolleyBall, ivTennis, ivBadminton, ivTableTennis, ivCricket;
	EditText etfName, etLname, etNetwork, etPhoneNumber;
	String footballStr = "footballStr";
	String basketBallStr = "basketBallStr";
	String volleyballStr = "volleyballStr";
	String tennisStr = "tennisStr";
	String badmintonStr = "badmintonStr";
	String tableTennisstr = "tableTennisstr";
	String cricketStr = "cricketStr";
	int noOfSports_count = 0;
	ArrayList<Integer> selected_Sports = new ArrayList<Integer>();
	// ArrayList<String> selected_SportsList = new ArrayList<String>();
	// TextView tvSave;
	String name, lname, network, number, gender, age, emailid;
	Bitmap profileImage;
	ImageView editBack;
	String likefb = "", likebm = "", likevb = "", likecrickt = "", likett = "", likebb = "", liketens = "";
	boolean isUserCreated = false;
	EnsiegDB database;

	/**
	 * Through this method user can display this activity layout
	 */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.edit_profile, container, false);

		return rootView;
	}

	/**
	 * It will initializes all required views for this activity
	 * 
	 */
	@SuppressLint("NewApi")
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		database = new EnsiegDB(getActivity());

		etfName = (EditText) view.findViewById(R.id.et_edit_Name);
		etLname = (EditText) view.findViewById(R.id.et_edit_LastName);
		etNetwork = (EditText) view.findViewById(R.id.et_edit_network);
		etPhoneNumber = (EditText) view.findViewById(R.id.et_edit_number);
		ivProfile = (ImageView) view.findViewById(R.id.iv_editImage);
		ivFootball = (ImageView) view.findViewById(R.id.iv_edit_football);
		ivBadminton = (ImageView) view.findViewById(R.id.iv_edit_badminton);
		ivVolleyBall = (ImageView) view.findViewById(R.id.iv_edit_volleyball);
		ivCricket = (ImageView) view.findViewById(R.id.iv_edit_cricket);
		ivTableTennis = (ImageView) view.findViewById(R.id.iv_edit_tbl_tennis);
		ivBasketBall = (ImageView) view.findViewById(R.id.iv_edit_basketball);
		ivTennis = (ImageView) view.findViewById(R.id.iv_edit_tennis);
		// tvSave = (TextView) view.findViewById(R.id.tv_uedit_save);
		// editBack = (ImageView)view. findViewById(R.id.editback);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "MYRIADPRO-REGULAR.ttf");
		// tvSave.setTypeface(font);
		etfName.setTypeface(font);
		etLname.setTypeface(font);
		etNetwork.setTypeface(font);

		// UserProfileDb profile = new UserProfileDb(getActivity());
		database.Open();
		if (database.numberOfRows_inProfile() > 0) {
			ArrayList<String> allProfileData = database.getAllProfileData();
			// UserRegistrationDb registration = new
			// UserRegistrationDb(getActivity());
			ArrayList<String> allRegisters = database.getAllRegisters();
			database.close();
			// 1235
			etfName.setText(allRegisters.get(1).toString());
			etLname.setText(allRegisters.get(2).toString());
			etNetwork.setText(allRegisters.get(3).toString());
			etPhoneNumber.setText(allRegisters.get(5).toString());
			emailid = allRegisters.get(4).toString();
			gender = allProfileData.get(2).toString();
			age = allProfileData.get(1).toString();
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
				ivProfile.setBackgroundResource(R.drawable.default_profile);
			}
			ivProfile.setImageBitmap(Ensieg_AppConstants.profile);
			Log.d("profile data is ", "profile " + allProfileData.get(4).toString());
			// Log.d("login data is ", "login " + allRegisters.size());
			String allSports = allProfileData.get(4).toString();
			// ArrayList<Integer> favSports =
			// Ensieg_CreateProfileActivity.selected_Sports;
			// Log.d("sports length is ", favSports.size() + " size ");
			// setting selected favorite sports background
			if (likefb.equals("yes")) {
				ivFootball.setBackgroundResource(R.drawable.circle);
			}
			if (likebb.equals("yes")) {
				ivBasketBall.setBackgroundResource(R.drawable.circle);
			}
			if (likevb.equals("yes")) {
				ivVolleyBall.setBackgroundResource(R.drawable.circle);
			}

			if (liketens.equals("yes")) {
				ivTennis.setBackgroundResource(R.drawable.circle);
			}

			if (likebm.equals("yes")) {
				ivBadminton.setBackgroundResource(R.drawable.circle);

			}

			if (likett.equals("yes")) {
				ivTableTennis.setBackgroundResource(R.drawable.circle);
			}

			if (likecrickt.equals("yes")) {
				ivCricket.setBackgroundResource(R.drawable.circle);
			}
			// calling back functionality by clicking this image
			// editBack.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			//// onBackPressed();
			// }
			// });
			// to change his profile we are asking user to select image and crop
			// it
			ivProfile.setOnClickListener(new OnClickListener() {

				@Override
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

			ivBadminton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (selected_Sports.contains(2)) {
						selected_Sports.remove(selected_Sports.indexOf(2));
						likebm = "no";
						// selected_SportsList.remove(badmintonStr);
						--noOfSports_count;
						ivBadminton.setBackgroundResource(R.color.white);
					} else {
						selected_Sports.add(2);
						// selected_SportsList.add(badmintonStr);
						likebm = "yes";
						++noOfSports_count;
						ivBadminton.setBackgroundResource(R.drawable.circle);
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
						liketens = "no";
						++noOfSports_count;
						ivTennis.setBackgroundResource(R.drawable.circle);
					}
				}
			});

			ivBasketBall.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (selected_Sports.contains(5)) {
						selected_Sports.remove(selected_Sports.indexOf(5));
						// selected_SportsList.remove(basketBallStr);
						likebb = "no";
						--noOfSports_count;
						ivBasketBall.setBackgroundResource(R.color.white);
					} else {
						selected_Sports.add(5);
						++noOfSports_count;
						// selected_SportsList.add(basketBallStr);
						likebb = "yes";
						ivBasketBall.setBackgroundResource(R.drawable.circle);
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
						likett = "no";
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
			Toast.makeText(getActivity(), "No user Please Login or Register", Toast.LENGTH_LONG).show();
		}

		// all edited details are saving in server
		// tvSave.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// name = etfName.getText().toString();
		// lname = etLname.getText().toString();
		// network = etNetwork.getText().toString();
		// number = etPhoneNumber.getText().toString();
		// ConnectionDetector isConnected = new
		// ConnectionDetector(getActivity());
		// if (isConnected.isConnectingToInternet()) {
		// new UpdateProfile().execute(Ensieg_AppConstants.appService_update);
		// } else {
		// Toast.makeText(getActivity(),
		// "Please Connect to your Data Connection And try to Edit Your
		// Profile", Toast.LENGTH_LONG)
		// .show();
		// }
		// }
		// });

	}

	/**
	 * It will convert bitmap to string
	 * 
	 * @param bitmap
	 * @return
	 */
	public String BitMapToString(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] b = baos.toByteArray();
		String temp = Base64.encodeToString(b, Base64.DEFAULT);
		return temp;
	}

	/**
	 * It will convert String to Bitmap
	 * 
	 * @param encodedString
	 * @return bitmap (from given string)
	 */
	public Bitmap StringToBitMap(String encodedString) {
		if (encodedString.length() > 0) {
			try {
				byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
				Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
				return bitmap;
			} catch (Exception e) {
				e.getMessage();

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
				Ensieg_AppConstants.profile = getRoundedCroppedBitmap(photo, 150);
				ivProfile.setImageBitmap(profileImage);

			}
		} else {
			Toast.makeText(getActivity(), "You are not selected the image", Toast.LENGTH_LONG).show();
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
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Saving Edit Profile Details");
			pDialog.setCancelable(false);
			pDialog.setCanceledOnTouchOutside(false);
			pDialog.show();
		}

		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String... urls) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			// UserLoginDb login = new UserLoginDb(getActivity());
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

			return GET(urls[0], nameValuePairs);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
			if (pDialog != null)
				pDialog.dismiss();
			if (result.contains("0")) {

			} else {
				Toast.makeText(getActivity(), "OOPS,Something went wrong", Toast.LENGTH_LONG).show();
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
}
