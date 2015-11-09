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
import org.json.JSONException;
import org.json.JSONObject;

import com.skylka.ensieg.ConnectionDetector;
import com.skylka.ensieg.R;
import com.skylka.ensieg.constants.Ensieg_AppConstants;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Ensieg_OutOfCampusActivity extends Activity implements OnClickListener {
	EditText email_et;
	Button notify_btn;
	Typeface customfont_regular, custom_font_bold;
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_outofcampus);
		initializeViews();

	}

	private void initializeViews() {
		TextView main_tx, sub_tx;

		customfont_regular = Typeface.createFromAsset(getAssets(), "MYRIADPRO-REGULAR.ttf");
		main_tx = (TextView) findViewById(R.id.main_tx);
		sub_tx = (TextView) findViewById(R.id.sub_tx);
		email_et = (EditText) findViewById(R.id.emailid_et);
		notify_btn = (Button) findViewById(R.id.notify_btn);
		main_tx.setTypeface(customfont_regular);
		sub_tx.setTypeface(customfont_regular);
		email_et.setTypeface(customfont_regular);
		notify_btn.setTypeface(customfont_regular);

		notify_btn.setOnClickListener(this);

	}

	private boolean verifyingEmail(String uEmail) {

		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		if (!uEmail.matches(emailPattern)) {

			/*
			 * Toast.makeText(this,
			 * "Please enter correct email pattern like mail@mailname.com",
			 * Toast.LENGTH_LONG).show();
			 */
			email_et.setError("Please enter correct email pattern like mail@mailname.com");
			return false;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.notify_btn:
			if (verifyingEmail(email_et.getText().toString().trim())) {
				callWebService();
			}
			break;

		default:
			break;
		}

	}

	private void callWebService() {
		ConnectionDetector isConnected = new ConnectionDetector(getApplicationContext());
		if (isConnected.isConnectingToInternet()) {
			new AddEmail().execute(Ensieg_AppConstants.appService_notify);
		} else {

			Toast.makeText(Ensieg_OutOfCampusActivity.this, "Please Connect to your Data Connection And try it again",
					Toast.LENGTH_LONG).show();
		}
	}

	private class AddEmail extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(Ensieg_OutOfCampusActivity.this);
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);
			pDialog.setCanceledOnTouchOutside(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... urls) {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("email", email_et.getText().toString().trim()));
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
			JSONObject obj;
			try {
				obj = new JSONObject(result);
				if (result.contains("0")) {
					Toast.makeText(Ensieg_OutOfCampusActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
}
