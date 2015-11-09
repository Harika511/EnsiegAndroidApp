package com.skylka.ensieg.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.skylka.ensieg.ConnectionDetector;
import com.skylka.ensieg.R;
import com.skylka.ensieg.adapters.DrawerItemCustomAdapter;
import com.skylka.ensieg.constants.Ensieg_AppConstants;
import com.skylka.ensieg.constants.SportsUtilities;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.floatingaction_menu.FloatingActionButton;
import com.skylka.ensieg.fragments.CreateFragment;
import com.skylka.ensieg.model.ContactBean;
import com.skylka.ensieg.model.EventModel;
import com.skylka.ensieg.model.GameModel;
import com.skylka.ensieg.model.MessageModel;
import com.skylka.ensieg.model.TeamModel;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * It displays HomeScreen of our App
 *
 * @author harika
 */

public class Ensieg_HomeActivity extends AppCompatActivity implements OnClickListener, OnTabSelectedListener {

	// declare properties
	private String[] mNavigationDrawerItemTitles;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	Fragment fragment = null;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	public static int NUMBER_OF_PAGES;

	int selectedPostion;
	boolean selectedEdit = false;
	ViewPager viewpager;
	private TabsPagerAdapter mAdapter;
	// ActionBar actionBar;
	DrawerItemCustomAdapter adapter;
	private TabLayout tabLayout;
	CharSequence mTitle;
	ProgressDialog pDialog;
	FloatingActionButton fab;
	/*
	 * ArrayList<String> game_names_ = new ArrayList<>(); ArrayList<Integer>
	 * game_image_ = new ArrayList<>();
	 */
	private List<ContactBean> list = new ArrayList<ContactBean>();
	private List<ContactBean> ensieg_list = new ArrayList<ContactBean>();
	Map<String, String> allContacts = new HashMap<String, String>();
	List<String> contact_list = new ArrayList<String>();
	List<String> ensieg_contact = new ArrayList<String>();
	ArrayList<GameModel> favoriate_list = new ArrayList<GameModel>();
	EnsiegDB database;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Toast.makeText(getApplicationContext(), "hello",
		// Toast.LENGTH_LONG).show();
		setContentView(R.layout.left_panel);
		database = new EnsiegDB(this);

		SportsUtilities.initializeSports();
		callWebService();

		// for proper titles
		mTitle = mDrawerTitle = getTitle();

		// initialize properties
		mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// list the drawer items
		ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[5];
		// drawerItem[0] = new ObjectDrawerItem(R.drawable.about_normal,
		// R.drawable.about_selected, "HOME");
		drawerItem[0] = new ObjectDrawerItem(R.drawable.profile_normal, R.drawable.profile_selected, "PROFILE");
		drawerItem[1] = new ObjectDrawerItem(R.drawable.teams_normal, R.drawable.teams_selected, "TEAMS");
		drawerItem[2] = new ObjectDrawerItem(R.drawable.contacts_normal, R.drawable.contacts_selected, "CONTACTS");
		drawerItem[3] = new ObjectDrawerItem(R.drawable.help_normal, R.drawable.help_selected, "HELP");
		// drawerItem[4] = new ObjectDrawerItem(R.drawable.settings_normal,
		// R.drawable.settings_selected, "SETTINGS");
		drawerItem[4] = new ObjectDrawerItem(R.drawable.about_normal, R.drawable.about_selected, "ABOUT");
		// Pass the folderData to our ListView adapter
		adapter = new DrawerItemCustomAdapter(this, drawerItem);
		LayoutInflater inflater_ = getLayoutInflater();
		View header = inflater_.inflate(R.layout.menu_listview_header, null, false);
		ImageView profile_img = (ImageView) header.findViewById(R.id.profile_img);
		TextView profile_name = (TextView) header.findViewById(R.id.profile_name);
		try {
			database.Open();
			if (database.numberOfRows_inProfile() > 0) {
				ArrayList<String> allProfileData = database.getAllProfileData();

				if (database.numberOfRows_inRegisters() > 0) {
					ArrayList<String> allRegisters = database.getAllRegisters();
					String firstname = allRegisters.get(1).toString();
					String lastname = allRegisters.get(2).toString();
					profile_name.setText(firstname.substring(0, 1).toUpperCase() + firstname.substring(1) + " "
							+ lastname.substring(0, 1).toUpperCase() + lastname.substring(1));

				}
				if (allProfileData.size() > 0) {
					Bitmap old_Profile = StringToBitMap(allProfileData.get(3));
					if (old_Profile != null) {
						profile_img.setImageBitmap(old_Profile);
					} else {
						profile_img.setBackgroundResource(R.drawable.edit_profile);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mDrawerList.addHeaderView(header, null, false);
		mDrawerList.setAdapter(adapter);

		mDrawerLayout.setScrimColor(Color.TRANSPARENT);
		adapter.selectedItem(-1);
		// set the item click listener
		// adapter.selectedItem(0);
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				adapter.selectedItem(position - 1);
				adapter.notifyDataSetChanged();
				selectItem(position - 1);

			}
		});

		// for app icon control for nav drawer
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
				mDrawerLayout, /* DrawerLayout object */
				R.drawable.ic_drawer, /*
										 * nav drawer icon to replace 'Up' caret
										 */
				R.string.drawer_open, /* "open drawer" description */
				R.string.drawer_close /* "close drawer" description */
		) {

			/**
			 * Called when a drawer has settled in a completely closed state.
			 */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getSupportActionBar().setTitle(mTitle);
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getSupportActionBar().setTitle(mDrawerTitle);
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		// getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// getSupportActionBar().setIcon(R.drawable.app_logo_48);
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View actionbar_view = inflater.inflate(R.layout.custom_actionbar_home, null);
		getSupportActionBar().setCustomView(actionbar_view);
		ImageView logo = (ImageView) actionbar_view.findViewById(R.id.app_logo);
		ImageView timer = (ImageView) actionbar_view.findViewById(R.id.timer_img);
		ImageView notification = (ImageView) actionbar_view.findViewById(R.id.notification_img);
		ImageView search = (ImageView) actionbar_view.findViewById(R.id.search_img);

		logo.setOnClickListener(this);
		timer.setOnClickListener(this);
		notification.setOnClickListener(this);
		search.setOnClickListener(this);
		if (savedInstanceState == null) {
			// on first time display view for first nav item
			selectItem(6);
		}
		fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.hide(false);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				fab.show(true);
				fab.setShowAnimation(AnimationUtils.loadAnimation(Ensieg_HomeActivity.this, R.anim.show_from_bottom));
				fab.setHideAnimation(AnimationUtils.loadAnimation(Ensieg_HomeActivity.this, R.anim.hide_to_bottom));
			}
		}, 300);

		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (favoriate_list.size() > 1) {
					Intent intent = new Intent(Ensieg_HomeActivity.this, Ensieg_PreHostAGameActivity.class);
					if ((favoriate_list.get(0).getGame_name()).equals("All")) {
						favoriate_list.remove(0);
					}
					intent.putExtra("favoriate_list", favoriate_list);

					startActivity(intent);
				} else {
					Intent intent1 = new Intent(Ensieg_HomeActivity.this, Ensieg_HostAGameActivity.class);
					intent1.putExtra("favoriate_list", favoriate_list.get(0).getSportID());

					startActivity(intent1);
				}
			}
		});
		getContacts();
	}

	private void callWebService() {
		ConnectionDetector isConnected = new ConnectionDetector(getApplicationContext());
		if (isConnected.isConnectingToInternet()) {
			new GetCards().execute(Ensieg_AppConstants.appService_getCards);
		} else {
			database.Open();
			if (database.getCardsFromDB(0).size() > 0) {
				database.close();
				populateData();
			} else {
				database.close();
				Toast.makeText(Ensieg_HomeActivity.this, "Please Connect to your Data Connection And try it again",
						Toast.LENGTH_LONG).show();
			}

		}

	}

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

	private class GetCards extends AsyncTask<String, Void, String> {
		ProgressDialog pDialog1;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog1 = new ProgressDialog(Ensieg_HomeActivity.this);
			pDialog1.setMessage("Loading...");
			pDialog1.setCancelable(false);
			pDialog1.setCanceledOnTouchOutside(false);
			pDialog1.show();
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

			// System.out.println(localTime + " time " + allRegisters.get(3));
			nameValuePairs.add(new BasicNameValuePair("sessionid", allRegisters.get(3).trim()));
			nameValuePairs.add(new BasicNameValuePair("timestamp", local));
			System.out.println("SessionID: " + allRegisters.get(3));
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
			// Toast.makeText(getApplicationContext(), result + " get cards ",
			// Toast.LENGTH_LONG).show();

			if (result.contains("0")) {

				parseResultStoreInDB(result);
				populateData();

			} else {
				Toast.makeText(getApplicationContext(), " Something Went wrong,Check your input ", Toast.LENGTH_LONG)
						.show();
			}
			if (pDialog1 != null && pDialog1.isShowing())
				pDialog1.dismiss();
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

		database.Open();
		ArrayList<String> allProfileData = database.getAllProfileData();
		database.close();

		int count = 4;

		for (int i1 = 0; i1 < 7; i1++) {
			if (allProfileData.get(count).toString().equalsIgnoreCase("yes")) {

				favoriate_list.add(SportsUtilities.sport_list.get(i1));
			}
			count++;
		}
		if (favoriate_list.size() > 1) {
			NUMBER_OF_PAGES = favoriate_list.size() + 1;
			GameModel bean = new GameModel();
			bean.setGame_name("All");
			bean.setSportID(0);
			bean.setGame_small_image(R.drawable.all_img);
			bean.setGame_big_image(R.drawable.all_img);
			bean.setGame_image(R.drawable.all_img);
			favoriate_list.add(0, bean);
		} else {
			NUMBER_OF_PAGES = favoriate_list.size();
		}
		System.out.println("numbero fpaged  " + NUMBER_OF_PAGES);
		tabLayout = (TabLayout) findViewById(R.id.tabs);
		if (NUMBER_OF_PAGES == 1) {
			tabLayout.setVisibility(View.GONE);
			fragment = new CreateFragment();
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

		} else if (NUMBER_OF_PAGES <= 3) {
			tabLayout.setVisibility(View.VISIBLE);
			tabLayout = (TabLayout) findViewById(R.id.tabs);
			viewpager = (ViewPager) findViewById(R.id.pager);
			// actionBar = getSupportActionBar();
			mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
			viewpager.setAdapter(mAdapter);
			tabLayout.setupWithViewPager(viewpager);

			// tabLayout.setupWithViewPager(viewpager);
			tabLayout.setOnTabSelectedListener(this);
			tabLayout.setTabMode(TabLayout.MODE_FIXED);

			setupTabIcons();
		} else {
			tabLayout.setVisibility(View.VISIBLE);
			tabLayout = (TabLayout) findViewById(R.id.tabs);
			viewpager = (ViewPager) findViewById(R.id.pager);
			// actionBar = getSupportActionBar();
			mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
			viewpager.setAdapter(mAdapter);
			tabLayout.setupWithViewPager(viewpager);

			tabLayout.setOnTabSelectedListener(this);
			tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

			setupTabIcons();
		}
	}

	public void parseResultStoreInDB(String result) {
		ArrayList<EventModel> event_list = new ArrayList<EventModel>();

		try {
			JSONObject object = new JSONObject(result);
			JSONArray array = object.getJSONArray("data");
			Log.d("", array + " json data ");
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
						team_bean.setMaxplayer(team_inner.getString("maxplayers"));
						team_bean.setOpen_positions(team_inner.getInt("openpostion") + "");
					} else {
						team_inner = team_obj.getJSONObject("team-2");
						team_bean.setTeamId(team_inner.getString("teamid"));
						team_bean.setTeamName(team_inner.getString("teamname"));
						team_bean.setMaxplayer(team_inner.getString("maxplayers"));
						team_bean.setOpen_positions(team_inner.getInt("openpostion") + "");
					}

					team_list.add(team_bean);

				}
				bean.setTeam_list(team_list);
				event_list.add(bean);

			}
			/* delete the existing records and adding new records to DB */

			database.Open();
			if (event_list.size() > 0) {
				database.deleteCardRecords();
				for (int i = 0; i < event_list.size(); i++) {
					database.insertCards(event_list.get(i), 1);
				}
			}
			database.close();
			if (pDialog != null && pDialog.isShowing()) {
				pDialog.dismiss();

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		 catch (Exception e) {
				e.printStackTrace();
			}

	}

	private void setupTabIcons() {

		for (int i = 0; i < NUMBER_OF_PAGES; i++) {
			View custom_ = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
			TextView tv = (TextView) custom_.findViewById(R.id.tab_tx);
			ImageView img = (ImageView) custom_.findViewById(R.id.tab_img);
			tv.setText(favoriate_list.get(i).getGame_name());
			img.setImageResource(favoriate_list.get(i).getGame_small_image());
			custom_.setId(i);
			tabLayout.getTabAt(i).setCustomView(custom_);

		}
/*
		View v = tabLayout.getTabAt(0).getCustomView();
		TextView tv = (TextView) v.findViewById(R.id.tab_tx);
		ImageView img = (ImageView) v.findViewById(R.id.tab_img);

		tv.setTextColor(getResources().getColor(R.color.login_btn_end));
		img.setBackgroundResource(R.drawable.circle_white);*/

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	// to change up caret
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	private void selectItem(int position) {

		// update the main content by replacing fragments

		switch (position) {
		case 0:
			Intent team = new Intent(Ensieg_HomeActivity.this, Ensieg_ProfileActivity.class);
			startActivity(team);
			finish();
			break;
		case 1:
			Intent help = new Intent(Ensieg_HomeActivity.this, Ensieg_TeamsActivity.class);
			startActivity(help);
			break;
		case 2:
			Intent settings = new Intent(Ensieg_HomeActivity.this, Ensieg_ContactsActivity.class);
			startActivity(settings);
			break;
		case 3:
			Intent about = new Intent(Ensieg_HomeActivity.this, Ensieg_HostManageActivity.class);
			startActivity(about);
			break;
		case 4:
			Intent contacts = new Intent(Ensieg_HomeActivity.this, Ensieg_AboutUsActivity.class);
			startActivity(contacts);
			break;

		}

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.app_logo:
			mDrawerLayout.openDrawer(Gravity.LEFT);
			break;
		case R.id.search_img:
			Intent search = new Intent(Ensieg_HomeActivity.this, Ensieg_SearchActivity.class);
			startActivity(search);
			break;// notification_img
		case R.id.notification_img:
			Intent notification = new Intent(Ensieg_HomeActivity.this, Ensieg_NotificationListActivity.class);
			startActivity(notification);
			break;//
		case R.id.timer_img:
			startActivity(new Intent(Ensieg_HomeActivity.this, Ensieg_TimeLineActivity.class));
			break;
		}
	}

	@Override
	public void onTabReselected(android.support.design.widget.TabLayout.Tab arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(android.support.design.widget.TabLayout.Tab tab) {
		// TODO Auto-generated method stub

		for (int i = 0; i < NUMBER_OF_PAGES; i++) {
			/*View v = tabLayout.getTabAt(i).getCustomView();
			TextView tv = (TextView) v.findViewById(R.id.tab_tx);
			ImageView img = (ImageView) v.findViewById(R.id.tab_img);
			if (i == tab.getPosition()) {
				tv.setTextColor(getResources().getColor(R.color.login_btn_end));
				img.setBackgroundResource(R.drawable.circle_white);
			} else {
				tv.setTextColor(Color.WHITE);
				img.setBackgroundResource(android.R.color.transparent);
			}*/
		}
		viewpager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(android.support.design.widget.TabLayout.Tab arg0) {
		// TODO Auto-generated method stub

	}

	public class TabsPagerAdapter extends FragmentPagerAdapter {

		public TabsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int index) {

			return new CreateFragment().create(index);

		}

		@Override
		public int getCount() {
			// get item count - equal to number of tabs
			return NUMBER_OF_PAGES;
		}

	}

	private void getContacts() {

		Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null,
				null);
		while (phones.moveToNext()) {

			String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

			String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

			allContacts.put(name, phoneNumber);

		}
		phones.close();
		if (allContacts.size() > 0) {
			createMap(allContacts);
			if (null != list && list.size() != 0) {
				Collections.sort(list, new Comparator<ContactBean>() {

					@Override
					public int compare(ContactBean lhs, ContactBean rhs) {
						return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
					}
				});
				// ContanctAdapter objAdapter = new
				// ContanctAdapter(Ensieg_ContactListActivity.this,
				// R.layout.alluser_row,
				// list);
				// listView.setAdapter(objAdapter);
			}
			callContactsWebService();
			// new
			// GetEnsiegContacts().execute(Ensieg_AppConstants.appService_getEnsiegContacts);
		} else {
			// showToast("No Contact Found!!!");
		}
	}

	public Map<String, String> createMap(Map<String, String> m) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> tmpMap = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : m.entrySet()) {
			if (!tmpMap.containsKey(entry.getValue())) {
				tmpMap.put(entry.getValue(), entry.getKey());
				ContactBean objContact = new ContactBean();
				String phoneNumber = entry.getValue().replace(" ", "");
				String phoneNumber1 = phoneNumber.replace("-", "");
				String phoneNumber2 = phoneNumber1.replace("?", "");
				String phone = "";
				if (phoneNumber2.contains("+91")) {
					phone = phoneNumber2.replace("+91", "");
				} else {
					phone = phoneNumber2;
				}

				objContact.setName(entry.getKey());
				objContact.setPhoneNo(phone);
				objContact.setEnsiegContact(false);
				list.add(objContact);
				contact_list.add(phone);
				Log.d("", " contacts " + phone);
			}
		}
		for (Map.Entry<String, String> entry : tmpMap.entrySet()) {
			map.put(entry.getValue(), entry.getKey());
		}

		return map;
	}

	private void callContactsWebService() {
		ConnectionDetector isConnected = new ConnectionDetector(getApplicationContext());
		if (isConnected.isConnectingToInternet()) {
			new GetEnsiegContacts().execute(Ensieg_AppConstants.appService_getEnsiegContacts);
		} else {

			Toast.makeText(Ensieg_HomeActivity.this, "Please Connect to your Data Connection And try it again",
					Toast.LENGTH_LONG).show();
		}

	}

	/**
	 * It will set and call the data to save the profile data to user
	 * 
	 * @author harika
	 *
	 */
	private class GetEnsiegContacts extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// callLoader();
			pDialog = new ProgressDialog(Ensieg_HomeActivity.this);
			pDialog.setMessage("Get Ensieg Contacts . . . ");
			pDialog.setCanceledOnTouchOutside(false);
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String... urls) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			EnsiegDB database = new EnsiegDB(getApplicationContext());
			database.Open();
			ArrayList<String> loginData = database.getLoginData();
			database.close();
			String[] contactArray = contact_list.toArray(new String[contact_list.size()]);
			JSONArray jsonArray = new JSONArray(Arrays.asList(contactArray));
			nameValuePairs.add(new BasicNameValuePair("sessionid", loginData.get(3)));// sb.split(",")
			nameValuePairs.add(new BasicNameValuePair("phonenumber", jsonArray.toString()));//
			JSONObject obj = new JSONObject();
			try {
				obj.put("sessionid", loginData.get(3));
				// obj.put("phonenumber", new String("9912283333").split(","));
				//
				obj.put("phonenumber", jsonArray);
				// Log.d("", " contact data " + contactArray);
				// // System.out.println(" contact data " + new
				// // Gson().toJson(obj));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return GET(urls[0], obj);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			// Toast.makeText(getApplicationContext(), "" + result,
			// Toast.LENGTH_LONG).show();
			Log.d("", result + " result is ");
			if (pDialog != null && pDialog.isShowing()) {
				pDialog.dismiss();
			}
			JSONObject jsonRootObject;
			try {
				jsonRootObject = new JSONObject(result);
				String error = jsonRootObject.optString("error").toString();
				if (error.equals("0")) {
					// parseResultStoreInDB(result);
					// populateData();
					parseContactsResultStoreInDB(result);
					// Toast.makeText(getApplicationContext(), "" + error,
					// Toast.LENGTH_LONG).show();
				} else {
					// if (pDialog != null && pDialog.isShowing()) {
					// pDialog.dismiss();
					// }
					Toast.makeText(getApplicationContext(), "OOPS,Something went wrong!", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

	//
	/**
	 * connect with server and will give JSON data in string format
	 *
	 * @param url
	 *            --to get data
	 * @param pair
	 *            --to get
	 */

	public static String GET(String url, JSONObject pair) {
		InputStream inputStream = null;
		String result = "";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			httpPost.setEntity(new StringEntity(pair.toString()));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			// receive response as inputStrea run chey ok m
			inputStream = httpResponse.getEntity().getContent();
			Log.d("", " pair is " + pair);
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

	/// **
	// *
	// * to get json object from inputstream
	// *
	// */
	// private static JSONObject convertIsToJson(InputStream iso) {
	// JSONObject jObj = null;
	// String json;
	// try {
	// BufferedReader reader = new BufferedReader(new InputStreamReader(iso,
	/// "iso-8859-1"), 8);
	//
	// StringBuilder sb = new StringBuilder();
	// String line = null;
	// if (reader != null)
	// while ((line = reader.readLine()) != null) {
	// sb.append(line + "\n");
	// }
	// iso.close();
	// json = sb.toString();
	// // Log.d(" jobj", json+"");
	// jObj = new JSONObject(json);
	// } catch (Exception e) {
	// Log.e("Buffer Error", "Error converting result " + e.toString());
	// }
	// return jObj;
	// }
	//
	//// It parse the data and will store in the db
	// @SuppressLint("NewApi")
	public void parseContactsResultStoreInDB(String result) {
		ArrayList<MessageModel> msg_list = new ArrayList<MessageModel>();

		try {
			JSONObject object = new JSONObject(result);
			Log.d("", object.getJSONArray("data") + " json array ");
			JSONArray json_players = object.getJSONArray("data");
			// Log.d("enter here ", "enter here " + json_players + "");
			int contactsSize = json_players.length();
			String contactsJson = json_players.getString(0).toString();
			// ensieg_contact.add();
			String[] contactsSplit = contactsJson.split(",");
			int contactSplitSize = contactsSplit.length;
			for (int i = 0; i < contactsSize; i++) {
				ensieg_contact.add(json_players.getJSONObject(i).getString("phonenumber"));

			}
			// Log.d("", "ensieg contacts are
			// "+json_players.getString(0).toString());

			int lsize = list.size();
			List<ContactBean> temp_list = new ArrayList<ContactBean>();
			for (int i = 0; i < lsize; i++) {
				ContactBean contactBean = list.get(i);
				String checkCountryCode = contactBean.getPhoneNo();
				if (contactBean.getPhoneNo().contains("+91")) {
					checkCountryCode = checkCountryCode.replace("+91", "");
				}

				if (ensieg_contact.contains(checkCountryCode)) {
					contactBean.setEnsiegContact(true);
					ensieg_list.add(contactBean);
					// list.remove(i);
				} else {
					contactBean.setEnsiegContact(false);
					temp_list.add(contactBean);
				}

			}
			EnsiegDB contacts = new EnsiegDB(getApplicationContext());
			contacts.Open();
			if (contacts.numberOfRows_InContacts() > 0) {
				contacts.deleteAllRows_Contacts();
			}
			if (contacts.numberOfRows_InNonContacts() > 0) {
				contacts.deleteAllRows_NonContacts();
			}
			contacts.insertContactsData(ensieg_list);
			contacts.insertNonContactsData(temp_list);
			contacts.close();
			// int lsize1 = temp_list.size();
			// Log.d("", "list size is " + lsize + " " + lsize1);
			// for (int i = 0; i < lsize1; i++) {
			// ContactBean contactBean = temp_list.get(i);
			// contactBean.setEnsiegContact(false);
			// ensieg_list.add(contactBean);
			// }
			// ContanctAdapter objAdapter = new
			// ContanctAdapter(Ensieg_ContactsActivity.this,
			// R.layout.alluser_row,
			// ensieg_list, "contacts");

			// listView.setAdapter(objAdapter);
			// if (pDialog != null && pDialog.isShowing()) {
			// pDialog.dismiss();
			// }
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
