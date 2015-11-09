package com.skylka.ensieg.activities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.skylka.ensieg.R;
import com.skylka.ensieg.activities.Ensieg_TeamsActivity.GroupView;
import com.skylka.ensieg.activities.Ensieg_TeamsActivity.PlayersListViewAdapter;
import com.skylka.ensieg.adapters.ContanctAdapter;
import com.skylka.ensieg.adapters.GroupAdapter;
import com.skylka.ensieg.adapters.GroupAdapter.ViewHolder1;
import com.skylka.ensieg.adapters.ContanctAdapter.ViewHolder;
import com.skylka.ensieg.constants.Ensieg_AppConstants;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.model.ContactBean;
import com.skylka.ensieg.model.GroupMember_Details;
import com.skylka.ensieg.model.GroupModel;
import com.skylka.ensieg.model.MessageModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Ensieg_TeamGroupsActivity extends Activity {

	private ListView listView;
	private List<ContactBean> list = new ArrayList<ContactBean>();
	private List<ContactBean> ensieg_list = new ArrayList<ContactBean>();
	private List<ContactBean> ensieg_group_list = new ArrayList<ContactBean>();
	Map<String, String> allContacts = new HashMap<String, String>();
	List<String> contact_list = new ArrayList<String>();
	List<String> ensieg_contact = new ArrayList<String>();
	List<String> selected_contact = new ArrayList<String>();
	ProgressDialog pDialog;
	LinearLayout layout;
	TextView tvContacts, tvEnsieg_contacts;
	Button btOk, btCancel;
	private Map<List<ContactBean>, String> ensieg_group_data = new HashMap<List<ContactBean>, String>();
	ListView list_groups;
	private PlayersListViewAdapter playerArrayAdapter;
	TextView tvGroupText;
	String groupName;
	List<GroupMember_Details> group_member;
	ArrayList<GroupModel> groupModel;
	String event_id = "", typeOfevent = "";
	Map<Integer, ArrayList<String>> contactData;
	ArrayList<String> groupContactData;
	String fromClass = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		listView = (ListView) findViewById(R.id.list);
		layout = (LinearLayout) findViewById(R.id.accepted_options);
		tvContacts = (TextView) findViewById(R.id.contacts);
		tvEnsieg_contacts = (TextView) findViewById(R.id.ensieg_contacts);
		btOk = (Button) findViewById(R.id.addTeam_bt);

		btCancel = (Button) findViewById(R.id.cancel_bt);
		tvContacts.setText("ENSIEG CONTACTS");
		tvEnsieg_contacts.setVisibility(View.GONE);
		// listView.setOnItemClickListener(this);
		layout.setVisibility(View.VISIBLE);
		list_groups = (ListView) findViewById(R.id.list_ensieg_groups);
		tvGroupText = (TextView) findViewById(R.id.tv_ensieg_grpText);
		if (groupContactData != null) {
			groupContactData.clear();
		}
		fromClass = getIntent().getStringExtra("match");
		if (fromClass.equals("match")) {
			list_groups.setVisibility(View.VISIBLE);
			tvGroupText.setVisibility(View.VISIBLE);
			event_id = getIntent().getStringExtra("eventid");
			typeOfevent = getIntent().getStringExtra("eventtype");
			EnsiegDB db = new EnsiegDB(getApplicationContext());
			db.Open();
			ArrayList<GroupModel> group = db.getGroupData();
			db.close();
			btOk.setText("INVITE");
			// GroupAdapter adapter = new
			// GroupAdapter(Ensieg_TeamGroupsActivity.this,
			// R.layout.alluser_row, group);
			// list_groups.setAdapter(adapter);
		} else {
			list_groups.setVisibility(View.GONE);
			tvGroupText.setVisibility(View.GONE);
		}
		EnsiegDB contacts = new EnsiegDB(getApplicationContext());
		contacts.Open();
		List<ContactBean> ensiegContactsData = contacts.getEnsiegContactsData();
		ContanctAdapter adapter = new ContanctAdapter(Ensieg_TeamGroupsActivity.this, R.layout.alluser_row,
				ensiegContactsData, "teams");

		listView.setAdapter(adapter);
		list_groups.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ViewHolder1 viewHolder = (ViewHolder1) view.getTag();
				if (viewHolder.getSelectedcb().isChecked()) {
					viewHolder.getSelectedcb().setChecked(false);
				} else {
					viewHolder.getSelectedcb().setChecked(true);
				}
				Iterator it = contactData.entrySet().iterator();
				int i = 0;
				Log.d("", i + " i is " + position);
				Toast.makeText(Ensieg_TeamGroupsActivity.this, i + " i is " + position, Toast.LENGTH_LONG).show();
				while (it.hasNext()) {
					if (i == position) {
						Map.Entry pair = (Map.Entry) it.next();
						groupContactData = (ArrayList<String>) pair.getValue();
					} else {
						++i;
					}

				}
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ViewHolder viewHolder = (ViewHolder) view.getTag();
				if (viewHolder.getSelectedcb().isChecked()) {
					viewHolder.getSelectedcb().setChecked(false);
				} else {
					viewHolder.getSelectedcb().setChecked(true);
				}
				// ContactBean bean = ensieg_list.get(position);
				ContactBean bean = (ContactBean) listView.getItemAtPosition(position);
				// ensieg_group_list.add(bean);
				if (groupContactData == null) {
					groupContactData = new ArrayList<String>();
				}
				if (!groupContactData.contains(bean.getPhoneNo()))
					groupContactData.add(bean.getPhoneNo());
			}
		});
		btOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (fromClass.equals("match")) {
					if (groupContactData != null && groupContactData.size() > 0) {
						new SendInvite().execute(Ensieg_AppConstants.appService_sendinvite);
					} else {
						Toast.makeText(getApplicationContext(), "please select any contact", Toast.LENGTH_LONG).show();
					}
				} else {

					if (ensieg_group_list.size() > 0) {

						AlertDialog.Builder builder = new AlertDialog.Builder(Ensieg_TeamGroupsActivity.this);
						builder.setTitle("Enter your Group Name ");

						// Set up the input
						final EditText input = new EditText(Ensieg_TeamGroupsActivity.this);
						// Specify the type of input expected; this, for
						// example,
						// sets the input as a password, and will mask the text
						input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
						builder.setView(input);

						// Set up the buttons
						builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								groupName = input.getText().toString();
								Toast.makeText(getApplicationContext(),
										"your " + groupName + " group is created sucessfully", Toast.LENGTH_LONG)
										.show();
								EnsiegDB groupDb = new EnsiegDB(getApplicationContext());
								GroupModel model = new GroupModel();
								model.setName(groupName);
								// groupDb.insertGroupData(model);
								new AddGroup().execute(Ensieg_AppConstants.appService_createGroup);
							}
						});
						builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						});

						builder.show();

					} else {
						Toast.makeText(getApplicationContext(), "please select any contact", Toast.LENGTH_LONG).show();
					}
				}
			}
		});

		btCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		new GetGroup().execute(Ensieg_AppConstants.appService_getgroups);
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

	// @Override
	// public void onItemClick(AdapterView<?> listview, View v, int position,
	// long id) {
	// ViewHolder viewHolder = (ViewHolder) v.getTag();
	// if (viewHolder.getSelectedcb().isChecked()) {
	// viewHolder.getSelectedcb().setChecked(false);
	// } else {
	// viewHolder.getSelectedcb().setChecked(true);
	// }
	// ContactBean bean = ensieg_list.get(position);
	// // listview.getItemAtPosition(position);
	// ensieg_group_list.add(bean);
	// selected_contact.add(bean.getPhoneNo());
	// // showCallDialog(bean.getName(), bean.getPhoneNo());
	// }

	/**
	 * It will set and call the data to save the profile data to user
	 * 
	 * @author harika
	 *
	 */
	private class GetGroup extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// callLoader();
			pDialog = new ProgressDialog(Ensieg_TeamGroupsActivity.this);
			pDialog.setMessage("getting ensieg contacts");
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
			nameValuePairs.add(new BasicNameValuePair("sessionid", loginData.get(3)));// sb.split(",")
			return GET(urls[0], nameValuePairs);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null && pDialog.isShowing()) {
				pDialog.dismiss();
			}
			// Toast.makeText(getApplicationContext(), result + " getgroups ",
			// Toast.LENGTH_LONG).show();
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
					// if (pDialog != null && pDialog.isShowing()) {
					// pDialog.dismiss();
					// }
					Toast.makeText(getApplicationContext(), "OOPS,Something went wrong!", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
				if (pDialog != null && pDialog.isShowing()) {
					pDialog.dismiss();
				}
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
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(pair));
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
		if (group_member != null) {
			group_member.clear();
		}
		group_member = new ArrayList<GroupMember_Details>();
		if (groupModel != null) {
			groupModel.clear();
		}
		groupModel = new ArrayList<GroupModel>();
		contactData = new HashMap<Integer, ArrayList<String>>();
		try {
			JSONObject object = new JSONObject(result);
			JSONObject jData = object.getJSONObject("data");
			JSONArray json_groups = jData.getJSONArray("groups");
			for (int i = 0; i < json_groups.length(); i++) {
				JSONObject groupdata = json_groups.getJSONObject(0);
				groupdata.get("groupadmin");
				GroupModel group = new GroupModel();
				group.setName(groupdata.get("groupname").toString());
				group.setPhoto(groupdata.get("groupicon").toString());
				groupModel.add(group);
				ArrayList<String> list = new ArrayList<String>();
				JSONArray group_members = groupdata.getJSONArray("members");
				for (int j = 0; j < group_members.length(); j++) {
					JSONObject memberdata = group_members.getJSONObject(j);
					String fname = memberdata.getString("firstname");
					String lname = memberdata.getString("lastname");
					String pnumber = memberdata.getString("phonenumber");
					String email = memberdata.getString("email");
					String photo = memberdata.getString("photo");
					GroupMember_Details details = new GroupMember_Details();
					details.setFname(fname);
					details.setLname(lname);
					details.setPhoto(photo);
					details.setMail(email);
					details.setMobile(pnumber);
					list.add(pnumber);
					group_member.add(details);

				}
				contactData.put(i, list);
			}
			EnsiegDB group = new EnsiegDB(getApplicationContext());
			group.Open();
			if (group.numberOfRows_InGroup() > 0) {
				group.deleteAllRows_group();
			}
			group.insertGroupData(groupModel);
			group.close();
			GroupAdapter adapter = new GroupAdapter(Ensieg_TeamGroupsActivity.this, groupModel);
			list_groups.setAdapter(adapter);

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * It will set and call the data to save the profile data to user
	 * 
	 * @author harika
	 *
	 */
	private class AddGroup extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// callLoader();
			// pDialog = new ProgressDialog(Ensieg_ContactListActivity.this);
			// pDialog.setMessage("Saving Profile Details");
			// pDialog.setCanceledOnTouchOutside(false);
			// pDialog.setCancelable(false);
			// pDialog.show();

		}

		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String... urls) {
			// List<NameValuePair> nameValuePairs = new
			// ArrayList<NameValuePair>();
			// nameValuePairs.add(new BasicNameValuePair(name, value))
			EnsiegDB database = new EnsiegDB(getApplicationContext());
			database.Open();
			ArrayList<String> loginData = database.getLoginData();
			database.close();
			String[] contactArray = selected_contact.toArray(new String[selected_contact.size()]);
			JSONArray jsonArray = new JSONArray(Arrays.asList(contactArray));

			JSONObject obj = new JSONObject();
			try {
				obj.put("sessionid", loginData.get(3));
				obj.put("groupname", groupName);
				obj.put("phoneno", jsonArray);
				obj.put("photo", "sabvfdsfjvdcxjhixhfi");
				// Log.d("", " contact data " + contactArray);
				// // System.out.println(" contact data " + new
				// // Gson().toJson(obj));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return GET1(urls[0], obj);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(getApplicationContext(), result + " add group ", Toast.LENGTH_LONG).show();
			// stopLoader();
			// if (pDialog != null && pDialog.isShowing())
			// pDialog.dismiss();
			if (result != null) {
				JSONObject jsonRootObject;
				try {
					jsonRootObject = new JSONObject(result);
					String error = jsonRootObject.optString("error").toString();
					if (error.equals("0")) {
						// parseResultStoreInDB(result);
						// Toast.makeText(getApplicationContext(), "" + error,
						// Toast.LENGTH_LONG).show();
					} else {
						// if (pDialog != null || pDialog.isShowing()) {
						// pDialog.dismiss();
						// }
						Toast.makeText(getApplicationContext(), "OOPS,Something went wrong!", Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
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

	public static String GET1(String url, JSONObject pair) {
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
				result = convertIsToJson1(inputStream) + " ";
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
	private static JSONObject convertIsToJson1(InputStream iso) {
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
	public void parseResultStoreInDB1(String result) {
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
				// ensieg_contact.add(contactsSplit[i]);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public class GroupView extends RecyclerView.ViewHolder {
		TextView name;
		TextView icon;

		public GroupView(View itemView) {
			super(itemView);
			name = (TextView) itemView.findViewById(R.id.nameTextView);
			icon = (TextView) itemView.findViewById(R.id.iconTextView);
			if (name == null) {
				throw new RuntimeException("Name is null");
			}
			Log.e("PlayerView", String.valueOf(name));
		}

	}

	/**
	 * It will set and call the data to save the profile data to user
	 * 
	 * @author harika
	 *
	 */
	private class SendInvite extends AsyncTask<String, Void, String> {
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// callLoader();
			// pDialog = new ProgressDialog(Ensieg_MatchCenter1.this);
			// pDialog.setMessage("Saving Profile Details");
			// pDialog.setCanceledOnTouchOutside(false);
			// pDialog.setCancelable(false);
			// pDialog.show();

		}

		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String... urls) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			EnsiegDB ensiegdb = new EnsiegDB(getApplicationContext());
			ensiegdb.Open();
			ArrayList<String> loginData = ensiegdb.getLoginData();
			ensiegdb.close();
			String[] contactArray = groupContactData.toArray(new String[groupContactData.size()]);
			JSONArray jsonArray = new JSONArray(Arrays.asList(contactArray));
			JSONObject obj = new JSONObject();
			try {
				obj.put("sessionid", loginData.get(3));
				obj.put("eventid", event_id);
				obj.put("phonenumbers", jsonArray);
				obj.put("typeofevent", typeOfevent);
				// Log.d("", " contact data " + contactArray);
				// // System.out.println(" contact data " + new
				// // Gson().toJson(obj));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			// notificationtype=1 1 invite
			// eventdetails=2 eventdetails
			// user=3

			return GET1(urls[0], obj);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(getApplicationContext(), result + " send invite", Toast.LENGTH_LONG).show();
			Log.d("", "join game  " + result);
			JSONObject jsonRootObject;
			try {
				jsonRootObject = new JSONObject(result);
				String error = jsonRootObject.optString("error").toString();
				if (error.equals("0")) {
					// selectedTeam = "";
					// parseResultStoreInDB(result);
					Toast.makeText(getApplicationContext(), " You are Playing Now ", Toast.LENGTH_LONG).show();
					// new
					// GetMatchData().execute(Ensieg_AppConstants.appService_match);
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

}
