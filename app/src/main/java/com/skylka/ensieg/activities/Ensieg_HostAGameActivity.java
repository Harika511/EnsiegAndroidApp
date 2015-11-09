package com.skylka.ensieg.activities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.google.gson.Gson;
import com.skylka.ensieg.ConnectionDetector;
import com.skylka.ensieg.R;
import com.skylka.ensieg.constants.Ensieg_AppConstants;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.model.GameModel;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Ensieg_HostAGameActivity extends Activity {
	private DatePickerDialog datePicker;
	private TimePickerDialog timePicker;
	private Calendar calendar;
	private TextView dateView, timeview;
	private int year, month, day;
	LinearLayout layout, layouttime;
	RelativeLayout rlDone;
	private SimpleDateFormat dateFormatter;
	// TimePicker timePicker;
	ImageView ivTime, ivDate, ivLocation, ivHostgameImage;
	String noOfPlayers;
	String date;
	/*
	 * String value; int gameImage;//team selection second part
	 */// invite friends
		// all icon pending
		// badminton icon pending
		// notify me-- web service call
	EditText etLocation;
	/*
	 * String sportIds[] = { "1", "2", "3", "4", "5", "6", "7" }; String[]
	 * host_text = { "Football", "Basketball", "Volleyball", "Tennis",
	 * "Badminton", "Table Tennis", "Cricket" };
	 */
	int gameIndex = 0;
	private Calendar cal;
	private int hour;
	private int min;
	GameModel model;
	EnsiegDB database;
	Button btOpen, btClose;
	Spinner spTeamAMax, spTeamBMax;
	String typeOfEvent = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.host_layout);
		database = new EnsiegDB(this);
		model = (GameModel) getIntent().getExtras().get("Game_Model");
		btClose = (Button) findViewById(R.id.closed_btn);
		btOpen = (Button) findViewById(R.id.open_btn);
		spTeamAMax = (Spinner) findViewById(R.id.teamA_max_players);
		spTeamBMax = (Spinner) findViewById(R.id.teamB_max_players);

		List<String> teamCount = new ArrayList<String>();
		teamCount.add("1");
		teamCount.add("2");
		teamCount.add("3");
		teamCount.add("4");
		teamCount.add("5");
		teamCount.add("6");
		teamCount.add("7");
		teamCount.add("8");
		teamCount.add("9");
		teamCount.add("10");
		teamCount.add("11");
		// Creating adapter for spinner

		// Log.d("", "game details are "+model.getSportID()+"
		// "+model.getGame_name());
		if (model != null) {
			gameIndex = model.getSportID();
		} else {

			gameIndex = getIntent().getIntExtra("favoriate_list", 0);
		}
		/*
		 * Bundle extras = getIntent().getExtras();
		 * 
		 * if (extras != null) { value = extras.getString("game_name");
		 * gameImage = extras.getInt("game_image"); for (int i = 0; i
		 * <host_text.length; i++) { if (value.equals(host_text[i])) {
		 * 
		 * gameIndex = i+1; } } }
		 */
		cal = Calendar.getInstance();
		hour = cal.get(Calendar.HOUR_OF_DAY);
		min = cal.get(Calendar.MINUTE);
		layout = (LinearLayout) findViewById(R.id.dateid);
		rlDone = (RelativeLayout) findViewById(R.id.rldone);
		dateView = (TextView) findViewById(R.id.calender_tx);
		// timePicker = (TimePicker) findViewById(R.id.timePicker1);
		dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		ivTime = (ImageView) findViewById(R.id.timer);
		ivLocation = (ImageView) findViewById(R.id.ivlocation);
		etLocation = (EditText) findViewById(R.id.et_location);
		timeview = (TextView) findViewById(R.id.tv_host_time);
		layouttime = (LinearLayout) findViewById(R.id.layouttime);
		ivHostgameImage = (ImageView) findViewById(R.id.host_game_img);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd");
		// get current date time with Date()
		Date date = new Date();
		dateView.setText(dateFormat.format(date));
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		timeview.setText(sdf.format(cal.getTime()));
		if (model != null)
			ivHostgameImage.setImageResource(model.getGame_big_image());
		// calendar = Calendar.getInstance();
		// year = calendar.get(Calendar.YEAR);
		//
		// month = calendar.get(Calendar.MONTH);
		// day = calendar.get(Calendar.DAY_OF_MONTH);
		// showDate(year, month + 1, day);
		setDateTimeField();
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// datePicker.show();
				Intent in = new Intent(Ensieg_HostAGameActivity.this, MyCalendarActivity.class);
				startActivity(in);

			}
		});
		layouttime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		rlDone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (typeOfEvent.length() > 0) {
					ConnectionDetector detector = new ConnectionDetector(getApplicationContext());
					if (detector.isConnectingToInternet()) {
						new HostAGame().execute(Ensieg_AppConstants.appService_host);
					} else {
						Toast.makeText(Ensieg_HostAGameActivity.this,
								"Please Connect to your data connection and try again", Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(Ensieg_HostAGameActivity.this, "Please select Type Of Event either Open or Closed",
							Toast.LENGTH_LONG).show();
				}

			}
		});
		timeview.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				ivTime.setBackgroundResource(R.drawable.timer);
			}
		});
		layouttime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setTimeField();
				ivTime.setBackgroundResource(R.drawable.timer);
			}
		});
		etLocation.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				ivLocation.setBackgroundResource(R.drawable.location);
			}
		});
		etLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ivLocation.setBackgroundResource(R.drawable.location);
			}
		});

		btOpen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				typeOfEvent = "1";
				btOpen.setTextColor(getResources().getColor(R.color.login_btn_end));
				btOpen.setBackgroundColor(getResources().getColor(android.R.color.black));
				btClose.setBackgroundColor(getResources().getColor(R.color.transparent_actionbar));
				btClose.setTextColor(getResources().getColor(android.R.color.black));
			}
		});

		btClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				typeOfEvent = "2";
				btClose.setTextColor(getResources().getColor(R.color.login_btn_end));
				btClose.setBackgroundColor(getResources().getColor(android.R.color.black));
				btOpen.setBackgroundColor(getResources().getColor(R.color.transparent_actionbar));
				btOpen.setTextColor(getResources().getColor(android.R.color.black));
			}
		});

		// btTeamAMax.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		if (gameIndex == 1 || gameIndex == 0) {
			// noOfPlayers = 11;
			// spTeamAMax.setText("11");
			// spTeamBMax.setText("11");

			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
					teamCount);

			// Drop down layout style - list view with radio button
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			// attaching data adapter to spinner
			spTeamAMax.setAdapter(dataAdapter);
			spTeamBMax.setAdapter(dataAdapter);
			// spTeamBMax.setSelection(spTeamAMax.getSelectedItemPosition());

		} else if (gameIndex == 2) {
			// noOfPlayers = 5;
			// spTeamAMax.setText("5");
			// spTeamBMax.setText("5");
			ArrayList<String> teamCount2 = new ArrayList<String>();
			teamCount2.add("1");
			teamCount2.add("2");
			teamCount2.add("3");
			teamCount2.add("4");
			teamCount2.add("5");
			ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
					teamCount2);

			// Drop down layout style - list view with radio button
			dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			// attaching data adapter to spinner
			spTeamAMax.setAdapter(dataAdapter2);
			spTeamBMax.setAdapter(dataAdapter2);

		} else if (gameIndex == 3) {
			// noOfPlayers = 6;

			ArrayList<String> teamCount3 = new ArrayList<String>();
			teamCount3.add("1");
			teamCount3.add("2");
			teamCount3.add("3");
			teamCount3.add("4");
			teamCount3.add("5");
			teamCount3.add("6");
			ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
					teamCount3);

			// Drop down layout style - list view with radio button
			dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			// attaching data adapter to spinner
			spTeamAMax.setAdapter(dataAdapter3);
			spTeamBMax.setAdapter(dataAdapter3);

			// spTeamAMax.setText("6");
			// spTeamBMax.setText("6");
		} else if (gameIndex == 4) {
			// noOfPlayers = 2;

			ArrayList<String> teamCount4 = new ArrayList<String>();
			teamCount4.add("1");
			teamCount4.add("2");

			ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
					teamCount4);

			// Drop down layout style - list view with radio button
			dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			// attaching data adapter to spinner
			spTeamAMax.setAdapter(dataAdapter4);
			// spTeamBMax.setSelection(dataAdapter4);
			spTeamBMax.setAdapter(dataAdapter4);

			// spTeamAMax.setText("2");
			// spTeamBMax.setText("2");
		} else if (gameIndex == 5) {
			// noOfPlayers = 2;

			ArrayList<String> teamCount4 = new ArrayList<String>();
			teamCount4.add("1");
			teamCount4.add("2");

			ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
					teamCount4);

			// Drop down layout style - list view with radio button
			dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			// attaching data adapter to spinner
			spTeamAMax.setAdapter(dataAdapter4);
			spTeamBMax.setAdapter(dataAdapter4);

			// spTeamAMax.setText("2");
			// spTeamBMax.setText("2");
		} else if (gameIndex == 6) {
			// noOfPlayers = 2;

			ArrayList<String> teamCount4 = new ArrayList<String>();
			teamCount4.add("1");
			teamCount4.add("2");

			ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
					teamCount4);

			// Drop down layout style - list view with radio button
			dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			// attaching data adapter to spinner
			spTeamAMax.setAdapter(dataAdapter4);
			spTeamBMax.setAdapter(dataAdapter4);

			// spTeamAMax.setText("2");
			// spTeamBMax.setText("2");
		} else if (gameIndex == 7) {
			// noOfPlayers = 11;
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
					teamCount);

			// Drop down layout style - list view with radio button
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			// attaching data adapter to spinner
			spTeamAMax.setAdapter(dataAdapter);
			spTeamBMax.setAdapter(dataAdapter);
		}

		spTeamAMax.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				noOfPlayers = spTeamAMax.getItemAtPosition(spTeamAMax.getSelectedItemPosition()).toString();
				spTeamBMax.setSelection(spTeamAMax.getSelectedItemPosition());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void setTimeField() {
		// timePicker=new TimePickerDialog(getApplicationContext(), callBack,
		// hourOfDay, minute, is24HourView)
		new TimePickerDialog(this, timePickerListener, hour, min, false).show();
		;

	}

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			int hour;
			String am_pm;
			String completeMinute = "";
			String completeHour = "";
			if (minute < 10) {
				completeMinute = "0" + minute;
			} else {
				completeMinute = minute + "";
			}
			if (hourOfDay < 10) {
				completeHour = "0" + hourOfDay;
			} else {
				completeHour = hourOfDay + "";
			}
			// if (hourOfDay > 12) {
			// hour = hourOfDay - 12;
			// am_pm = "PM";
			// } else {
			// hour = hourOfDay;
			// am_pm = "AM";
			// }
			timeview.setText(completeHour + ":" + completeMinute);
		}
	};

	private void setDateTimeField() {

		Calendar newCalendar = Calendar.getInstance();
		datePicker = new DatePickerDialog(this, new OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);

				dateView.setText(dateFormatter.format(newDate.getTime()));

			}

		}, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
	}
	// @Override
	// protected Dialog onCreateDialog(int id) {
	// // TODO Auto-generated method stub
	// return new DatePickerDialog(this, myDateListener, year, month, day);
	// }

	private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			arg1 = year;
			arg2 = month;
			arg3 = day;
			showDate(arg1, arg2 + 1, arg3);
		}
	};

	private void showDate(int year, int month, int day) {
		dateView.setText(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
	}

	public class HostAGame extends AsyncTask<String, Void, String> {

		// int sportId;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			Ensieg_AppConstants.loginSuccess = false;
		}

		@Override
		protected String doInBackground(String... urls) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			database.Open();
			ArrayList<String> loginData = database.getLoginData();
			database.close();
			date = dateView.getText().toString() + " " + timeview.getText().toString();
			// if(model!=null)
			// sportId = model.getSportID();

			nameValuePairs.add(new BasicNameValuePair("sessionid", loginData.get(3)));
			nameValuePairs.add(new BasicNameValuePair("venue", "Bits_pilani"));
			nameValuePairs.add(new BasicNameValuePair("date", "'" + date + "'"));
			nameValuePairs.add(new BasicNameValuePair("typeofevent", typeOfEvent));
			nameValuePairs.add(new BasicNameValuePair("sportsid", gameIndex + ""));
			nameValuePairs.add(new BasicNameValuePair("geolocation", "'17.4165,78.4382'"));
			nameValuePairs.add(new BasicNameValuePair("team-0", "team-a"));
			nameValuePairs.add(new BasicNameValuePair("team-1", "team-b"));
			nameValuePairs.add(new BasicNameValuePair("players-0", "" + noOfPlayers));
			nameValuePairs.add(new BasicNameValuePair("players-1", "" + noOfPlayers));
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + new Gson().toJson(nameValuePairs));
			return GET(urls[0], nameValuePairs);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(getApplicationContext(), result + " host a game", Toast.LENGTH_LONG).show();
			JSONObject jsonRootObject;
			try {
				jsonRootObject = new JSONObject(result);
				String error = jsonRootObject.optString("error").toString();
				if (error.equals("0")) {
					callMatchCenter(date, gameIndex);
					// parseJSON(result);
					Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_LONG).show();

				} else {
					Toast.makeText(getApplicationContext(), "OOPS,Something went wrong!", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}

	public void callMatchCenter(String hostTime, int SportType) {
		Intent match = new Intent(Ensieg_HostAGameActivity.this, Ensieg_MatchCenter.class);
		match.putExtra("time", hostTime);
		match.putExtra("type", SportType);
		match.putExtra("teamSize", noOfPlayers);
		match.putExtra("typeofevent", typeOfEvent);
		startActivity(match);
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
			} else {
				result = "Did not work!";
			}

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

	// private boolean parseJSON(String res) {
	//
	// try {
	// JSONObject jsonObj = new JSONObject(res);
	// //
	// // // Getting JSON Array node
	// JSONObject billPos = jsonObj.getJSONObject("data");
	// //
	// // // looping through All Contacts
	// // for (int i = 0; i < billPos.length(); i++) {
	// // Toast.makeText(mcontext, billPos + " some ",
	// // Toast.LENGTH_LONG).show();
	// String session = billPos.getString("sessionId");
	// Ensieg_AppConstants.sessionId = session.substring(0, session.length());
	// // UserLoginDb login = new UserLoginDb(mcontext);
	// // UserLoginModel loginModel = new UserLoginModel(uname, pwd,
	// // session);
	// // if (login.numberOfRows() > 0)
	// // login.deleteAllRows();
	// //
	// // login.insertloginData(loginModel);
	// // isLogInInserted = true;
	// Log.d("", session.substring(0, session.length()) + "");
	//
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	//
	// return false;
	// }

	@Override
	protected void onResume() {
		super.onResume();
		String selectedDate = MyCalendarActivity.getSelectedDate();
		if (selectedDate != null && !selectedDate.isEmpty()) {
			dateView.setText(selectedDate);
		}
	}

}
