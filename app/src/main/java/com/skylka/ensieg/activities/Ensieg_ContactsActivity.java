package com.skylka.ensieg.activities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.skylka.ensieg.ConnectionDetector;
import com.skylka.ensieg.R;
import com.skylka.ensieg.constants.Ensieg_AppConstants;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.fragments.Ensieg_ContactsFragment;
import com.skylka.ensieg.fragments.NonEnsieg_ContactsFragment;
import com.skylka.ensieg.model.ContactBean;
import com.skylka.ensieg.model.MessageModel;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
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

public class Ensieg_ContactsActivity extends ActionBarActivity implements ActionBar.TabListener {
	private ViewPager viewPager;
	private TabsPagerAdapterTimeline mAdapter;
	private ActionBar actionBar;
	private String[] tabs = { "CONTACTS", "ENSIEG CONTACTS" };
	ProgressDialog pDialog;
	EnsiegDB database;
	TextView noTimeline_tx;
	private List<ContactBean> list = new ArrayList<ContactBean>();
	private List<ContactBean> ensieg_list = new ArrayList<ContactBean>();
	Map<String, String> allContacts = new HashMap<String, String>();
	List<String> contact_list = new ArrayList<String>();
	List<String> ensieg_contact = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		database = new EnsiegDB(this);
		viewPager = (ViewPager) findViewById(R.id.pager_timeline);
		noTimeline_tx = (TextView) findViewById(R.id.notimeline_text);
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
		TextView tv = (TextView) actionbar_view.findViewById(R.id.timeline);
		tv.setText("CONTACTS");
		ImageView back = (ImageView) actionbar_view.findViewById(R.id.timelineback);

		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Ensieg_ContactsActivity.this.finish();
			}
		});

		Toolbar parent = (Toolbar) actionbar_view.getParent();
		parent.setContentInsetsAbsolute(0, 0);
		populateData();

//		Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null,
//				null);
//		while (phones.moveToNext()) {
//
//			String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//
//			String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//			allContacts.put(name, phoneNumber);
//
//		}
//		phones.close();
//		if (allContacts.size() > 0) {
//			createMap(allContacts);
//			if (null != list && list.size() != 0) {
//				Collections.sort(list, new Comparator<ContactBean>() {
//
//					@Override
//					public int compare(ContactBean lhs, ContactBean rhs) {
//						return lhs.getName().toLowerCase().compareTo(rhs.getName().toLowerCase());
//					}
//				});
//				// ContanctAdapter objAdapter = new
//				// ContanctAdapter(Ensieg_ContactListActivity.this,
//				// R.layout.alluser_row,
//				// list);
//				// listView.setAdapter(objAdapter);
//			}
//			callWebService();
//			// new
//			// GetEnsiegContacts().execute(Ensieg_AppConstants.appService_getEnsiegContacts);
//		} else {
//			// showToast("No Contact Found!!!");
//		}

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

//	private void callWebService() {
//		ConnectionDetector isConnected = new ConnectionDetector(getApplicationContext());
//		if (isConnected.isConnectingToInternet()) {
//			new GetEnsiegContacts().execute(Ensieg_AppConstants.appService_getEnsiegContacts);
//		} else {
//
//			Toast.makeText(Ensieg_ContactsActivity.this, "Please Connect to your Data Connection And try it again",
//					Toast.LENGTH_LONG).show();
//		}
//
//	}

//	/**
//	 * It will set and call the data to save the profile data to user
//	 * 
//	 * @author harika
//	 *
//	 */
//	private class GetEnsiegContacts extends AsyncTask<String, Void, String> {
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			// callLoader();
//			pDialog = new ProgressDialog(Ensieg_ContactsActivity.this);
//			pDialog.setMessage("Get Ensieg Contacts . . . ");
//			pDialog.setCanceledOnTouchOutside(false);
//			pDialog.setCancelable(false);
//			pDialog.show();
//
//		}
//
//		@SuppressWarnings("deprecation")
//		@Override
//		protected String doInBackground(String... urls) {
//			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//			EnsiegDB database = new EnsiegDB(getApplicationContext());
//			database.Open();
//			ArrayList<String> loginData = database.getLoginData();
//			database.close();
//			String[] contactArray = contact_list.toArray(new String[contact_list.size()]);
//			JSONArray jsonArray = new JSONArray(Arrays.asList(contactArray));
//			nameValuePairs.add(new BasicNameValuePair("sessionid", loginData.get(3)));// sb.split(",")
//			nameValuePairs.add(new BasicNameValuePair("phonenumber", jsonArray.toString()));//
//			JSONObject obj = new JSONObject();
//			try {
//				obj.put("sessionid", loginData.get(3));
//				// obj.put("phonenumber", new String("9912283333").split(","));
//				//
//				obj.put("phonenumber", jsonArray);
//				// Log.d("", " contact data " + contactArray);
//				// // System.out.println(" contact data " + new
//				// // Gson().toJson(obj));
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			return GET(urls[0], obj);
//		}
//
//		// onPostExecute displays the results of the AsyncTask.
//		@Override
//		protected void onPostExecute(String result) {
//			Toast.makeText(getApplicationContext(), "" + result, Toast.LENGTH_LONG).show();
//			if (pDialog != null && pDialog.isShowing()) {
//				pDialog.dismiss();
//			}
//			JSONObject jsonRootObject;
//			try {
//				jsonRootObject = new JSONObject(result);
//				String error = jsonRootObject.optString("error").toString();
//				if (error.equals("0")) {
//					parseResultStoreInDB(result);
//					populateData();
//					// Toast.makeText(getApplicationContext(), "" + error,
//					// Toast.LENGTH_LONG).show();
//				} else {
//					// if (pDialog != null && pDialog.isShowing()) {
//					// pDialog.dismiss();
//					// }
//					Toast.makeText(getApplicationContext(), "OOPS,Something went wrong!", Toast.LENGTH_LONG).show();
//				}
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//		}
//
//	}
//
//	/**
//	 * connect with server and will give JSON data in string format
//	 *
//	 * @param url
//	 *            --to get data
//	 * @param pair
//	 *            --to get
//	 */
//
//	public static String GET(String url, JSONObject pair) {
//		InputStream inputStream = null;
//		String result = "";
//		try {
//			HttpClient httpClient = new DefaultHttpClient();
//			HttpPost httpPost = new HttpPost(url);
//			httpPost.setHeader("Accept", "application/json");
//			httpPost.setHeader("Content-type", "application/json");
//			httpPost.setEntity(new StringEntity(pair.toString()));
//			HttpResponse httpResponse = httpClient.execute(httpPost);
//			// receive response as inputStrea run chey ok m
//			inputStream = httpResponse.getEntity().getContent();
//			Log.d("", " pair is " + pair);
//			// convert inputstream to string
//			if (inputStream != null) {
//				result = convertIsToJson(inputStream) + " ";
//			} else
//				result = "Did not work!";
//
//		} catch (Exception e) {
//			Log.d("InputStream", e.getLocalizedMessage());
//		}
//		Log.d("", "result is " + result);
//		return result;
//	}
//
//	/**
//	 * 
//	 * to get json object from inputstream
//	 *
//	 */
//	private static JSONObject convertIsToJson(InputStream iso) {
//		JSONObject jObj = null;
//		String json;
//		try {
//			BufferedReader reader = new BufferedReader(new InputStreamReader(iso, "iso-8859-1"), 8);
//
//			StringBuilder sb = new StringBuilder();
//			String line = null;
//			if (reader != null)
//				while ((line = reader.readLine()) != null) {
//					sb.append(line + "\n");
//				}
//			iso.close();
//			json = sb.toString();
//			// Log.d(" jobj", json+"");
//			jObj = new JSONObject(json);
//		} catch (Exception e) {
//			Log.e("Buffer Error", "Error converting result " + e.toString());
//		}
//		return jObj;
//	}
//
//	// It parse the data and will store in the db
//	@SuppressLint("NewApi")
//	public void parseResultStoreInDB(String result) {
//		ArrayList<MessageModel> msg_list = new ArrayList<MessageModel>();
//
//		try {
//			JSONObject object = new JSONObject(result);
//			Log.d("", object.getJSONArray("data") + " json array ");
//			JSONArray json_players = object.getJSONArray("data");
//			// Log.d("enter here ", "enter here " + json_players + "");
//			int contactsSize = json_players.length();
//			String contactsJson = json_players.getString(0).toString();
//			// ensieg_contact.add();
//			String[] contactsSplit = contactsJson.split(",");
//			int contactSplitSize = contactsSplit.length;
//			for (int i = 0; i < contactSplitSize; i++) {
//				ensieg_contact.add(contactsSplit[i]);
//
//			}
//			// Log.d("", "ensieg contacts are
//			// "+json_players.getString(0).toString());
//
//			int lsize = list.size();
//			List<ContactBean> temp_list = new ArrayList<ContactBean>();
//			for (int i = 0; i < lsize; i++) {
//				ContactBean contactBean = list.get(i);
//				String checkCountryCode = contactBean.getPhoneNo();
//				if (contactBean.getPhoneNo().contains("+91")) {
//					checkCountryCode = checkCountryCode.replace("+91", "");
//				}
//
//				if (ensieg_contact.contains(checkCountryCode)) {
//					contactBean.setEnsiegContact(true);
//					ensieg_list.add(contactBean);
//					// list.remove(i);
//				} else {
//					contactBean.setEnsiegContact(false);
//					temp_list.add(contactBean);
//				}
//
//			}
//			EnsiegDB contacts = new EnsiegDB(getApplicationContext());
//			contacts.Open();
//			if (contacts.numberOfRows_InContacts() > 0) {
//				contacts.deleteAllRows_Contacts();
//			}
//			if (contacts.numberOfRows_InNonContacts() > 0) {
//				contacts.deleteAllRows_NonContacts();
//			}
//			contacts.insertContactsData(ensieg_list);
//			contacts.insertNonContactsData(temp_list);
//			contacts.close();
//			// int lsize1 = temp_list.size();
//			// Log.d("", "list size is " + lsize + " " + lsize1);
//			// for (int i = 0; i < lsize1; i++) {
//			// ContactBean contactBean = temp_list.get(i);
//			// contactBean.setEnsiegContact(false);
//			// ensieg_list.add(contactBean);
//			// }
//			// ContanctAdapter objAdapter = new
//			// ContanctAdapter(Ensieg_ContactsActivity.this,
//			// R.layout.alluser_row,
//			// ensieg_list, "contacts");
//
//			// listView.setAdapter(objAdapter);
//			// if (pDialog != null && pDialog.isShowing()) {
//			// pDialog.dismiss();
//			// }
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//
//	}

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
				return new NonEnsieg_ContactsFragment();
			case 1:
				// Games fragment activity
				return new Ensieg_ContactsFragment();

			}

			return null;
		}

		@Override
		public int getCount() {
			// get item count - equal to number of tabs
			return 2;
		}

	}

}
