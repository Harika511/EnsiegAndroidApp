package com.skylka.ensieg.activities;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
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

import com.skylka.ensieg.ConnectionDetector;
import com.skylka.ensieg.R;
import com.skylka.ensieg.constants.Ensieg_AppConstants;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.model.UserProfileModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore.MediaColumns;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * It will allow the user to enter his profile details
 * 
 * @author harika
 *
 */
@SuppressWarnings("deprecation")
public class Ensieg_CreateProfileActivity extends Activity {

	TextView tvNext;
	ImageView ivBack, ivProfile;
	EditText etAge;
	Button btMale, btFemale;
	String gender;
	int noOfSports_count = 0;
	int reporter_img_status = 0;
	Uri file_path1, file_path2, file_path3;
	public static ArrayList<Integer> selected_Sports = new ArrayList<Integer>();
	// ArrayList<String> selected_SportsList = new ArrayList<String>();
	ImageView football, badminton, volleyball, cricket, table_tennis, basketball, tennis;
	String likefb = "no", likebm = "no", likevb = "no", likecrickt = "no", likett = "no", likebb = "no",
			liketens = "no";
	String age;
	// long sessionid;
	Bitmap profileImage;
	String footballStr = "FootBall";
	String basketBallStr = "BasketBall";
	String volleyballStr = "VolleyBall";
	String tennisStr = "TennisStr";
	String badmintonStr = "Badminton";
	String tableTennisstr = "TableTennis";
	String cricketStr = "Cricket";
	boolean isUserCreated = false;

	TextView tvEncourageFriends, userName;
	String filemanagerstring, imagePath;
	private String selectedImagePath;
	int column_index;
	EnsiegDB database;

	/**
	 * It will display the view of this activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.create_profile);
		database = new EnsiegDB(getApplicationContext());
		initializeViwes();

	}

	/**
	 * It will convert Bitmap to string form
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
			
			ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
			Bitmap bm=Bitmap.createScaledBitmap(bitmap, 10, 10, true);
			bm.compress(Bitmap.CompressFormat.PNG, 100, baos1);
			byte[] b1 = baos.toByteArray();
			String temp1 = Base64.encodeToString(b1, Base64.DEFAULT);
			
			Log.d("", "with out compress "+temp);
			Log.d("", "with compress "+temp1);
			
			return temp;
		} else
			return "";
	}

	/**
	 * It will convert String to Bitmap
	 * 
	 * @param encodedString
	 * @return bitmap (from given string)
	 */
	public Bitmap StringToBitMap(String encodedString) {
		if (encodedString != null) {
			try {
				byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
				Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
				return bitmap;
			} catch (Exception e) {
				e.getMessage();
				return null;
			}
		}
		return null;
	}

	/**
	 * Here we are displaying user Profile data
	 */
	public void callHomeScreen() {

		Intent login = new Intent(Ensieg_CreateProfileActivity.this, Ensieg_HomeActivity.class);
		startActivity(login);
		finish();
	}

	/**
	 * It will initializes the all the required views
	 */
	public void initializeViwes() {

		ivBack = (ImageView) findViewById(R.id.profileback);
		ivProfile = (ImageView) findViewById(R.id.profileImage);
		tvNext = (TextView) findViewById(R.id.tv_profile_Next);
		etAge = (EditText) findViewById(R.id.et_age);
		btMale = (Button) findViewById(R.id.btMale);
		btFemale = (Button) findViewById(R.id.btFemale);
		football = (ImageView) findViewById(R.id.iv_profile_football);
		badminton = (ImageView) findViewById(R.id.iv_profile_badminton);
		volleyball = (ImageView) findViewById(R.id.iv_profile_volleyball);
		cricket = (ImageView) findViewById(R.id.iv_profile_cricket);
		table_tennis = (ImageView) findViewById(R.id.iv_profile_tbl_tennis);
		basketball = (ImageView) findViewById(R.id.iv_profile_basketball);
		tennis = (ImageView) findViewById(R.id.iv_profile_tennis);
		tvEncourageFriends = (TextView) findViewById(R.id.encorageFriends_play);
		userName = (TextView) findViewById(R.id.txt_profile_welcome);

		database.Open();
		if (database.numberOfRows_inRegisters() > 0) {
			ArrayList<String> allRegisters = database.getAllRegisters();
			userName.setText("Welcome " + allRegisters.get(1).toString());
		}
		database.close();
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				onBackPressed();

			}
		});
		/**
		 * allow to user to select his image
		 */
		ivProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);

				try {

					// intent.putExtra("return-data", true);
					startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);

				} catch (ActivityNotFoundException e) {
					e.printStackTrace();
					// Do nothing for now
				}

			}
		});
		/**
		 * When user clicked on here we will save user entered profile details
		 * in servers
		 */
		tvNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					age = etAge.getText().toString();
					if (validatingUserEnteredDetails()) {
						ConnectionDetector isConnected = new ConnectionDetector(getApplicationContext());
						if (isConnected.isConnectingToInternet()) {
							new CreateProfile().execute(Ensieg_AppConstants.appService_profile);
						} else {
							Toast.makeText(getApplicationContext(),
									"Please Connect to Your Data Connection And Try to do Create Your Profile",
									Toast.LENGTH_LONG).show();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		/**
		 * It responds when user selected his gender as male
		 */
		btMale.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gender = "Male";
				btMale.setTextColor(getResources().getColor(R.color.login_btn_end));
				btMale.setBackgroundColor(getResources().getColor(android.R.color.black));
				btFemale.setBackgroundColor(getResources().getColor(R.color.transparent_actionbar));
				btFemale.setTextColor(getResources().getColor(android.R.color.black));
			}
		});
		/**
		 * It responds when user selected his gender as Female
		 */
		btFemale.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gender = "Female";
				btFemale.setTextColor(getResources().getColor(R.color.login_btn_end));
				btFemale.setBackgroundColor(getResources().getColor(android.R.color.black));
				btMale.setBackgroundColor(getResources().getColor(R.color.transparent_actionbar));
				btMale.setTextColor(getResources().getColor(android.R.color.black));

			}
		});
		/**
		 * It responds when user selected his favorite sport as football
		 */
		football.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (selected_Sports.contains(1)) {
					selected_Sports.remove(selected_Sports.indexOf(1));
					likefb = "no";
					--noOfSports_count;
					football.setBackgroundResource(R.color.white);
				} else {
					selected_Sports.add(1);
					++noOfSports_count;
					football.setBackgroundResource(R.drawable.circle);
					likefb = "yes";
				}
			}
		});
		/**
		 * It responds when user selected his favorite sport as basketball
		 */
		basketball.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (selected_Sports.contains(2)) {
					selected_Sports.remove(selected_Sports.indexOf(2));
					likebb = "no";
					// selected_SportsList.remove(basketBallStr);
					--noOfSports_count;
					basketball.setBackgroundResource(R.color.white);
				} else {
					selected_Sports.add(2);
					// selected_SportsList.add(basketBallStr);
					++noOfSports_count;
					likebb = "yes";
					basketball.setBackgroundResource(R.drawable.circle);
				}
			}
		});

		/**
		 * It responds when user selected his favorite sport as volleyball
		 */
		volleyball.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (selected_Sports.contains(3)) {
					selected_Sports.remove(selected_Sports.indexOf(3));
					// selected_SportsList.remove(volleyballStr);
					--noOfSports_count;
					likevb = "no";
					volleyball.setBackgroundResource(R.color.white);
				} else {
					selected_Sports.add(3);
					// selected_SportsList.add(volleyballStr);
					++noOfSports_count;
					likevb = "yes";
					volleyball.setBackgroundResource(R.drawable.circle);
				}
			}
		});
		/**
		 * It responds when user selected his favorite sport as tennis
		 */
		tennis.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (selected_Sports.contains(4)) {
					selected_Sports.remove(selected_Sports.indexOf(4));
					// selected_SportsList.remove(tennisStr);
					--noOfSports_count;
					liketens = "no";
					tennis.setBackgroundResource(R.color.white);
				} else {
					selected_Sports.add(4);
					// selected_SportsList.add(tennisStr);
					++noOfSports_count;
					liketens = "yes";
					tennis.setBackgroundResource(R.drawable.circle);
				}
			}
		});
		/**
		 * It responds when user selected his favorite sport as badminton
		 */
		badminton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (selected_Sports.contains(5)) {
					selected_Sports.remove(selected_Sports.indexOf(5));
					// selected_SportsList.remove(badmintonStr);
					likebm = "no";
					--noOfSports_count;
					badminton.setBackgroundResource(R.color.white);
				} else {
					selected_Sports.add(5);
					++noOfSports_count;
					likebm = "yes";
					// selected_SportsList.add(badmintonStr);
					badminton.setBackgroundResource(R.drawable.badminton_clicked);
				}
			}
		});
		/**
		 * It responds when user selected his favorite sport as table tennis
		 */
		table_tennis.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (selected_Sports.contains(6)) {
					selected_Sports.remove(selected_Sports.indexOf(6));
					// selected_SportsList.remove(tableTennisstr);
					likett = "no";
					--noOfSports_count;
					table_tennis.setBackgroundResource(R.color.white);
				} else {
					selected_Sports.add(6);
					++noOfSports_count;
					likett = "yes";
					// selected_SportsList.add(tableTennisstr);
					table_tennis.setBackgroundResource(R.drawable.circle);
				}
			}
		});
		/**
		 * It responds when user selected his favorite sport as cricket
		 */
		cricket.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (selected_Sports.contains(7)) {
					selected_Sports.remove(selected_Sports.indexOf(7));
					--noOfSports_count;
					likecrickt = "no";
					// selected_SportsList.remove(cricketStr);
					cricket.setBackgroundResource(R.color.white);
				} else {
					selected_Sports.add(7);
					++noOfSports_count;
					likecrickt = "yes";
					// selected_SportsList.add(cricketStr);
					cricket.setBackgroundResource(R.drawable.circle);
				}
			}
		});

		tvEncourageFriends.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String shareBody = "Would You Join in Cricket, Please download Ensieg app and accept My invitation";
				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Ensieg Game Request");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
				startActivity(Intent.createChooser(sharingIntent, "Share Via"));
			}
		});

	}

	/**
	 * It will validate the user entered details
	 * 
	 * @return
	 */
	public boolean validatingUserEnteredDetails() {
		// if (Ensieg_AppConstants.profile == null) {
		// Toast.makeText(getApplicationContext(), "Please select your profile",
		// Toast.LENGTH_LONG).show();
		// return false;
		// }
		if (gender == null || gender.length() <= 0) {
			Toast.makeText(getApplicationContext(), "Please select your Gender", Toast.LENGTH_LONG).show();
			return false;

		}
		if (age == null || age.length() <= 0 || age.length() > 2) {
			Toast.makeText(getApplicationContext(), "Please Enter Proper Age", Toast.LENGTH_LONG).show();
			return false;

		}
		if (selected_Sports.size() <= 0) {
			Toast.makeText(getApplicationContext(), "Please Select Your Favorite Sports", Toast.LENGTH_LONG).show();
			return false;

		}
		return true;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent) After user selected image result we are setting
	 * image to profile with cropping
	 */

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1 && data != null) {

			Uri uri = data.getData();

			if (Build.VERSION.SDK_INT < 19) {
				// OI FILE Manager
				filemanagerstring = uri.getPath();
				// MEDIA GALLERY
				selectedImagePath = getPath(uri);

				imagePath.getBytes();
				Bitmap bm = BitmapFactory.decodeFile(imagePath);
				profileImage = getRoundedCroppedBitmap(bm, 400);
				file_path1 = uri;

				ivProfile.setImageBitmap(profileImage);
				// addImage1.setImageBitmap(bmp);

			} else {
				ParcelFileDescriptor parcelFileDescriptor;
				try {
					parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
					FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
					Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
					profileImage = getRoundedCroppedBitmap(image, 400);
					parcelFileDescriptor.close();

					file_path1 = uri;

					ivProfile.setImageBitmap(profileImage);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}
	/*
	 * else { Toast.makeText(getApplicationContext(),
	 * "you didnot selected any image", Toast.LENGTH_LONG).show(); }
	 */

	@SuppressLint("NewApi")
	private String getPath(Uri uri) {
		/*
		 * if (uri == null) { return null; }
		 * 
		 * String[] projection = { MediaStore.Images.Media.DATA };
		 * 
		 * Cursor cursor; if (Build.VERSION.SDK_INT > 19) { String wholeID =
		 * DocumentsContract.getDocumentId(uri); String id =
		 * wholeID.split(":")[1]; String sel = MediaStore.Images.Media._ID +
		 * "=?"; cursor = getContentResolver().query(MediaStore.Images.Media.
		 * EXTERNAL_CONTENT_URI, projection, sel, new String[] { id }, null); }
		 * else { cursor = getContentResolver().query(uri, projection, null,
		 * null, null); } String path = null; try { int column_index =
		 * cursor.getColumnIndex(MediaStore.Images.Media.DATA);
		 * cursor.moveToFirst(); path =
		 * cursor.getString(column_index).toString(); cursor.close(); } catch
		 * (NullPointerException e) {
		 * 
		 * } return path;
		 */
		String[] projection = { MediaColumns.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		imagePath = cursor.getString(column_index);

		return cursor.getString(column_index);
	}

	/**
	 * It will give circle cropped bitmap
	 * 
	 * @param bitmap
	 * @param radius
	 * @return
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

	public void callLoader() {

	}

	public void stopLoader() {

	}

	/**
	 * It will set and call the data to save the profile data to user
	 * 
	 * @author harika
	 *
	 */
	private class CreateProfile extends AsyncTask<String, Void, String> {
		String bm_str = BitMapToString(profileImage);
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
			/* EnsiegDB database = new EnsiegDB(getApplicationContext()); */
			database.Open();
			ArrayList<String> allRegisters = database.getLoginData();
			database.close();
if(allRegisters.size()>0){
			nameValuePairs.add(new BasicNameValuePair("operation", "0"));
			nameValuePairs.add(new BasicNameValuePair("sessionid", allRegisters.get(3)));
			nameValuePairs.add(new BasicNameValuePair("gender", gender));
			nameValuePairs.add(new BasicNameValuePair("age", age));
			nameValuePairs.add(new BasicNameValuePair("photo", bm_str));
			nameValuePairs.add(new BasicNameValuePair("count", noOfSports_count + ""));

			for (int i = 0; i < noOfSports_count; i++) {
				nameValuePairs.add(new BasicNameValuePair("sports-" + i, selected_Sports.get(i) + ""));
			}
}
			Log.d("sending data is ", Ensieg_AppConstants.sessionId + " " + gender + " " + age + " " + profileImage
					+ " " + noOfSports_count);
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

				UserProfileModel profileModel = new UserProfileModel(gender, age, noOfSports_count, bm_str,
						selected_Sports, likefb, likebb, likevb, liketens, likebm, likett, likecrickt);
				// UserProfileDb profile = new
				// UserProfileDb(getApplicationContext());
				database.Open();
				if (database.numberOfRows_inProfile() > 0) {

					database.deleteAllProfileRows_profile();
				}
				// if (profile.getAllProfileData() != null) {

				// profile.deleteProfile(1);
				// }
				long inserted = database.insertProfileData(profileModel);
				database.close();
				Toast.makeText(getApplicationContext(), inserted + " profile ", Toast.LENGTH_LONG).show();
				isUserCreated = true;
				if (inserted > 0) {
					callHomeScreen();
				}
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

}
