package com.skylka.ensieg.activities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.skylka.ensieg.R;
import com.skylka.ensieg.adapters.ContanctAdapter;
import com.skylka.ensieg.adapters.MessageAdapter;
import com.skylka.ensieg.constants.Ensieg_AppConstants;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.model.ContactBean;
import com.skylka.ensieg.model.MessageModel;
import com.skylka.ensieg.model.UserProfileModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class Ensieg_ContactListActivity1 extends Activity implements OnItemClickListener {

	private ListView listView, listViewGroups;
	LinearLayout layout;
	private List<ContactBean> list = new ArrayList<ContactBean>();
	private List<ContactBean> ensieg_list = new ArrayList<ContactBean>();
	Map<String, String> allContacts = new HashMap<String, String>();
	List<String> contact_list = new ArrayList<String>();
	List<String> ensieg_contact = new ArrayList<String>();
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		listView = (ListView) findViewById(R.id.list);
		listViewGroups = (ListView) findViewById(R.id.list_ensieg_groups);
		listView.setOnItemClickListener(this);
		layout = (LinearLayout) findViewById(R.id.accepted_options);
		layout.setVisibility(View.GONE);
		listViewGroups.setVisibility(View.GONE);
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
			new GetEnsiegContacts().execute(Ensieg_AppConstants.appService_getEnsiegContacts);
		} else {
			showToast("No Contact Found!!!");
		}
	}

	private void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	public Map<String, String> createMap(Map<String, String> m) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> tmpMap = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : m.entrySet()) {
			if (!tmpMap.containsKey(entry.getValue())) {
				tmpMap.put(entry.getValue(), entry.getKey());
				ContactBean objContact = new ContactBean();
				objContact.setName(entry.getKey());
				objContact.setPhoneNo(entry.getValue());
				objContact.setEnsiegContact(false);
				list.add(objContact);
				contact_list.add(entry.getValue().toString());
				Log.d("", entry.getValue() + " contacts ");
			}
		}
		for (Map.Entry<String, String> entry : tmpMap.entrySet()) {
			map.put(entry.getValue(), entry.getKey());
		}

		return map;
	}

	@Override
	public void onItemClick(AdapterView<?> listview, View v, int position, long id) {
		ContactBean bean = (ContactBean) listview.getItemAtPosition(position);
		// showCallDialog(bean.getName(), bean.getPhoneNo());
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
			// TODO Auto-generated method stub
			super.onPreExecute();
			// callLoader();
			pDialog = new ProgressDialog(Ensieg_ContactListActivity1.this);
			pDialog.setMessage("Get Ensieg Contacts");
			pDialog.setCanceledOnTouchOutside(false);
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String... urls) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			// nameValuePairs.add(new BasicNameValuePair(name, value))
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
			// Toast.makeText(getApplicationContext(), result + " ensieg
			// contacts ", Toast.LENGTH_LONG).show();
			// stopLoader();
			// if (pDialog != null && pDialog.isShowing())
			// pDialog.dismiss();
			JSONObject jsonRootObject;
			try {
				jsonRootObject = new JSONObject(result);
				String error = jsonRootObject.optString("error").toString();
				if (error.equals("0")) {
					parseResultStoreInDB(result);
					// Toast.makeText(getApplicationContext(), "" + error,
					// Toast.LENGTH_LONG).show();
				} else {
					if (pDialog != null && pDialog.isShowing()) {
						pDialog.dismiss();
					}
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
			Log.d("", "pair is " + pair);
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

	// It parse the data and will store in the db
	@SuppressLint("NewApi")
	public void parseResultStoreInDB(String result) {
		ArrayList<MessageModel> msg_list = new ArrayList<MessageModel>();

		try {
			JSONObject object = new JSONObject(result);
			Log.d("", object.getJSONArray("data") + " json array ");
			JSONArray json_players = object.getJSONArray("data");
			Log.d("enter here ", "enter here  " + json_players + "");
			int contactsSize = json_players.length();
			String contactsJson = json_players.getString(0).toString();
			// ensieg_contact.add();
			String[] contactsSplit = contactsJson.split(",");
			int contactSplitSize = contactsSplit.length;
			for (int i = 0; i < contactSplitSize; i++) {
				ensieg_contact.add(contactsSplit[i]);

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
					temp_list.add(contactBean);
				}

			}
			int lsize1 = temp_list.size();
			Log.d("", "list size is " + lsize + " " + lsize1);
			for (int i = 0; i < lsize1; i++) {
				ContactBean contactBean = temp_list.get(i);
				contactBean.setEnsiegContact(false);
				ensieg_list.add(contactBean);
			}
			ContanctAdapter objAdapter = new ContanctAdapter(Ensieg_ContactListActivity1.this, R.layout.alluser_row,
					ensieg_list, "contacts");

			listView.setAdapter(objAdapter);
			if (pDialog != null && pDialog.isShowing()) {
				pDialog.dismiss();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}
