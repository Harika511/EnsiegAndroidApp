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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.skylka.ensieg.ConnectionDetector;
import com.skylka.ensieg.R;
import com.skylka.ensieg.constants.Ensieg_AppConstants;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.fragments.HistoryFragment;
import com.skylka.ensieg.fragments.UpcomingFragment;
import com.skylka.ensieg.model.EventModel;
import com.skylka.ensieg.model.TeamModel;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Ensieg_TimeLineActivity extends ActionBarActivity implements ActionBar.TabListener {
	private ViewPager viewPager;
	private TabsPagerAdapterTimeline mAdapter;
	private ActionBar actionBar;
	private String[] tabs = { "UPCOMING", "HISTORY" };
	ProgressDialog pDialog;
	EnsiegDB database;
	TextView noTimeline_tx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		database = new EnsiegDB(this);
		viewPager = (ViewPager) findViewById(R.id.pager_timeline);
		noTimeline_tx = (TextView) findViewById(R.id.notimeline_text);
		callWebService();
		initializeViwes();

	}

	private void initializeViwes() {

		actionBar = getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View actionbar_view = inflater.inflate(R.layout.custom_actionbar_timeline, null);
		actionBar.setCustomView(actionbar_view);
		ImageView back = (ImageView) actionbar_view.findViewById(R.id.timelineback);

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Ensieg_TimeLineActivity.this.finish();
			}
		});

		Toolbar parent = (Toolbar) actionbar_view.getParent();
		parent.setContentInsetsAbsolute(0, 0);

	}

	private void callWebService() {
		ConnectionDetector isConnected = new ConnectionDetector(getApplicationContext());
		if (isConnected.isConnectingToInternet()) {
			new GetTimeline().execute(Ensieg_AppConstants.appService_getTimeline);
		} else {

			Toast.makeText(Ensieg_TimeLineActivity.this, "Please Connect to your Data Connection And try it again",
					Toast.LENGTH_LONG).show();
		}

	}

	private class GetTimeline extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(Ensieg_TimeLineActivity.this);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);
			pDialog.setCanceledOnTouchOutside(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... urls) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			database.Open();
			ArrayList<String> allRegisters = database.getLoginData();
			database.close();
			Calendar cal = Calendar.getInstance();
			Date currentLocalTime = cal.getTime();

			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String localTime = date.format(currentLocalTime);
			String localTime_withquotes = "'" + localTime + "'";
			String local = "'" + "2015-10-23 16:21:12" + "'";
//			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//			Date date = new Date();
//			System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48

			System.out.println(local + "   time " + allRegisters.get(3));
			nameValuePairs.add(new BasicNameValuePair("sessionid", allRegisters.get(3)));
			nameValuePairs.add(new BasicNameValuePair("timestamp", localTime_withquotes));

			InputStream inputStream = null;
			String result = "";
			try {

				// create HttpClient
				HttpClient httpClient = new DefaultHttpClient();

				HttpPost httpPost = new HttpPost(urls[0]);

				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse httpResponse = httpClient.execute(httpPost);
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
			if (pDialog != null && pDialog.isShowing())
				pDialog.dismiss();
			if (result.contains("0")) {
				parseResultStoreInDB(result);
				populateData();
				/*
				 * database.Open(); //ArrayList<EventModel> event_list =
				 * database.getCardsFromDB(0); database.close();
				 */
				// System.out.println("EVENT LIST FROM DB " + event_list);

			} else {
				viewPager.setVisibility(View.GONE);
				noTimeline_tx.setVisibility(View.VISIBLE);

			}
		}
	}

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

	public void populateData() {
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		viewPager.setVisibility(View.VISIBLE);
		noTimeline_tx.setVisibility(View.GONE);
		mAdapter = new TabsPagerAdapterTimeline(getSupportFragmentManager());
		viewPager.setAdapter(mAdapter);
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
		}

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {

		viewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	public class TabsPagerAdapterTimeline extends FragmentPagerAdapter {

		public TabsPagerAdapterTimeline(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int index) {

			switch (index) {
			case 0:
				// Top Rated fragment activity
				return new UpcomingFragment();
			case 1:
				// Games fragment activity
				return new HistoryFragment();

			}

			return null;
		}

		@Override
		public int getCount() {
			// get item count - equal to number of tabs
			return 2;
		}

	}

	public void parseResultStoreInDB(String result) {

		try {
			JSONObject object = new JSONObject(result);
			JSONObject data_obj = object.getJSONObject("data");
			JSONArray array_history = data_obj.getJSONArray("history");
			ArrayList<EventModel> event_list_history = parseArray(array_history);
			JSONArray array_future = data_obj.getJSONArray("future");
			ArrayList<EventModel> event_list_upcoming = parseArray(array_future);
			/* delete the existing records and adding new records to DB */
			database.Open();
			if (event_list_history.size() > 0) {
				database.deleteHistoryCardRecords();
				for (int i = 0; i < event_list_history.size(); i++) {
					database.insertCards(event_list_history.get(i), 3);
				}
			}
			if (event_list_upcoming != null && event_list_upcoming.size() > 0) {
				database.deleteUpcomingCardRecords();
				for (int i = 0; i < event_list_history.size(); i++) {
					database.insertCards(event_list_upcoming.get(i), 2);
				}
			} else {
				database.deleteUpcomingCardRecords();
			}
			database.close();

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private ArrayList<EventModel> parseArray(JSONArray array) {
		ArrayList<EventModel> event_list = new ArrayList<EventModel>();
		try {
			for (int i = 0; i < array.length(); i++) {
				EventModel bean = new EventModel();
				JSONObject obj = array.getJSONObject(i);
				bean.setEventId(obj.getString("eventid"));
				bean.setDate(obj.getString("date"));
				bean.setGeolocation(obj.getString("geolocation"));
				bean.setHost_photo(obj.getString("photo"));
				bean.setNameofhost(obj.getString("nameofhost"));
				bean.setSportsId(obj.getString("sportsid"));
				bean.setTypeofEvent(obj.getString("typeofevent"));
				bean.setUserIdOfhost(obj.getString("useridofhost"));
				bean.setVenue(obj.getString("venue"));
				bean.setCommentcount(obj.getString("commentcount"));
				JSONObject team_obj = obj.getJSONObject("team");
				ArrayList<TeamModel> team_list = new ArrayList<>();
				for (int j = 0; j < 2; j++) {
					TeamModel team_bean = new TeamModel();
					JSONObject team_inner;
					if (j == 0) {
						team_inner = team_obj.getJSONObject("team-1");
						team_bean.setTeamId(team_inner.getString("teamid"));
						team_bean.setTeamName(team_inner.getString("teamname"));
						team_bean.setMaxplayer(team_inner.getString("maxplayer"));
						team_bean.setOpen_positions(team_inner.getInt("openpostion") + "");
					} else {
						team_inner = team_obj.getJSONObject("team-2");
						team_bean.setTeamId(team_inner.getString("teamid"));
						team_bean.setTeamName(team_inner.getString("teamname"));
						team_bean.setMaxplayer(team_inner.getString("maxplayer"));
						team_bean.setOpen_positions(team_inner.getInt("openpostion") + "");
					}

					team_list.add(team_bean);

				}
				bean.setTeam_list(team_list);
				event_list.add(bean);

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return event_list;
	}
}
