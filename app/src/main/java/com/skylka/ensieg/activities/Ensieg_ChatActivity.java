package com.skylka.ensieg.activities;

import java.io.BufferedReader;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.skylka.ensieg.R;
import com.skylka.ensieg.adapters.MessageAdapter;
import com.skylka.ensieg.constants.Ensieg_AppConstants;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.model.MessageModel;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Ensieg_ChatActivity extends Activity {

	EditText mEditMessage;
	Button mBtnSend;
	ListView mListMessage;
	MessageAdapter mMessageAdapter;
	ArrayList<MessageModel> mMessages = new ArrayList<MessageModel>();
	String eventId = "";
	String comment = "";
	String date = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_chat);
		eventId = getIntent().getStringExtra("eventid");
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mEditMessage = (EditText) findViewById(R.id.txt_message);
		mListMessage = (ListView) findViewById(R.id.list_message);
		new GetComment().execute(Ensieg_AppConstants.appService_getcomment);

		mBtnSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				mMessageAdapter = new MessageAdapter(Ensieg_ChatActivity.this, mMessages);
				mListMessage.setAdapter(mMessageAdapter);
				MsgEvent message = new MsgEvent();
				message.nickname = "";
				comment = mEditMessage.getText().toString();
				message.text = comment;
				try {
					sendMessage(message);
					mEditMessage.getText().clear();
					mMessages.add(new MessageModel(message.nickname, message.text, "1"));
					mMessageAdapter.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

	}

	public void scrollMyListViewToBottom() {
		mListMessage.post(new Runnable() {
			@Override
			public void run() {
				// Select the last row so it will scroll into view...
				mMessageAdapter.notifyDataSetChanged();
				mListMessage.setSelection(mMessageAdapter.getCount() - 1);
			}
		});
	}

	public void showAlert(String message) {
		AlertDialog.Builder dlg = new AlertDialog.Builder(Ensieg_ChatActivity.this);
		dlg.setMessage(message);
		dlg.setTitle("alert");
		dlg.setCancelable(true);
		dlg.create();
		dlg.show();
	}

	private void sendMessage(MsgEvent message) throws JSONException {

		new AddComment().execute(Ensieg_AppConstants.appService_comment);
	}

	/**
	 * It will set and call the data to save the profile data to user
	 * 
	 * @author harika
	 *
	 */
	private class AddComment extends AsyncTask<String, Void, String> {
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
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
			;
			ArrayList<String> loginData = ensiegdb.getLoginData();
			ensiegdb.close();

			nameValuePairs.add(new BasicNameValuePair("sessionid", loginData.get(3)));
			nameValuePairs.add(new BasicNameValuePair("eventidfk", eventId));
			nameValuePairs.add(new BasicNameValuePair("comment", comment));
			if (date.isEmpty()) {				
				nameValuePairs.add(new BasicNameValuePair("date", "'0000-00-00 00:00:00'"));
//				nameValuePairs.add(new BasicNameValuePair("requestflag", "0"));
			}
			else {				
				nameValuePairs.add(new BasicNameValuePair("date", "'"+date+"'"));
//				nameValuePairs.add(new BasicNameValuePair("requestflag", "0"'));
			}

			// nameValuePairs.add(new BasicNameValuePair("email",
			// etmail.getText().toString()));
			// nameValuePairs.add(new BasicNameValuePair("phonenumber",
			// etphone.getText().toString()));

			return GET(urls[0], nameValuePairs);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			 Toast.makeText(getApplicationContext(), result + " comments ",
			 Toast.LENGTH_LONG).show();
			Log.d("", "values are " + result);
			JSONObject jsonRootObject;
			try {
				jsonRootObject = new JSONObject(result);
				String error = jsonRootObject.optString("error").toString();
				if (error.equals("0")) {
					 parseResultStoreInDB(result);
					// Toast.makeText(getApplicationContext(), "" + error,
					// Toast.LENGTH_LONG).show();
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
			Log.d(json, json + " json");
			jObj = new JSONObject(json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}
		return jObj;
	}

	/**
	 * It will set and call the data to save the profile data to user
	 * 
	 * @author harika
	 *
	 */
	private class GetComment extends AsyncTask<String, Void, String> {
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
			nameValuePairs.add(new BasicNameValuePair("sessionid", loginData.get(3)));
			nameValuePairs.add(new BasicNameValuePair("eventidfk", eventId));
			nameValuePairs.add(new BasicNameValuePair("requestflag", "0"));

			// nameValuePairs.add(new BasicNameValuePair("email",
			// etmail.getText().toString()));
			// nameValuePairs.add(new BasicNameValuePair("phonenumber",
			// etphone.getText().toString()));

			return GET1(urls[0], nameValuePairs);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(getApplicationContext(), result + " get comments", Toast.LENGTH_LONG).show();
			Log.d("", "values are " + result);
			JSONObject jsonRootObject;
			try {
				jsonRootObject = new JSONObject(result);
				String error = jsonRootObject.optString("error").toString();
				if (error.equals("0")) {
					parseResultStoreInDB(result);
					// Toast.makeText(getApplicationContext(), "" + error,
					// Toast.LENGTH_LONG).show();
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

	public static String GET1(String url, List<NameValuePair> pair) {
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
			Log.d(json, json + " json");
			jObj = new JSONObject(json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}
		return jObj;
	}

	// adb shellinput text
	@SuppressLint("NewApi")
	public void parseResultStoreInDB(String result) {
		ArrayList<MessageModel> msg_list = new ArrayList<MessageModel>();

		try {
			JSONObject object = new JSONObject(result);
			Log.d("", object.getJSONArray("data") + " json array ");
			JSONArray json_players = object.getJSONArray("data");

			Log.d("enter here ", "enter here  " + json_players + "");
			int commentSize = json_players.length();
			EnsiegDB register = new EnsiegDB(getApplicationContext());
			register.Open();
			ArrayList<String> allRegisters = register.getAllRegisters();
			register.close();
			String uName = allRegisters.get(1).toLowerCase() + " " + allRegisters.get(2).toLowerCase();
			String existed = "0";

			for (int i = commentSize - 1; i > 0; i--) {
				JSONObject player_details = json_players.getJSONObject(i);
				String comment = player_details.getString("comment");
				String name = player_details.getString("username");
				if (i == commentSize - 1) {
					date = player_details.getString("cre_ts");
				}
				if (name.toLowerCase().contains(uName)) {
					existed = "1";
				}
				MessageModel msg = new MessageModel(name, comment, existed);
				mMessages.add(msg);
			}
			mMessageAdapter = new MessageAdapter(this, mMessages);
			mListMessage.setAdapter(mMessageAdapter);
			mMessageAdapter.notifyDataSetChanged();

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * We want PubSub events delivered to us in JSON payload to be automatically
	 * converted to this domain POJO. We specify this class later when we
	 * subscribe.
	 */
	private static class MsgEvent {

		public String text;
		public String nickname;

		@Override
		public String toString() {
			return "\ntext: " + text + "\nnickname: " + nickname;
		}
	}

	private static class UserJoinedEvent {

		public String nickname;

		@Override
		public String toString() {
			return "\nnickname: " + nickname;
		}
	}

}
// android:textColor="@color/textFieldColor"
// <! android:background="@drawable/message_field" --txt_message -->
// <!-- android:background="@drawable/message_bar" --bottom_write_bar -->
// android:background="@drawable/send_button"