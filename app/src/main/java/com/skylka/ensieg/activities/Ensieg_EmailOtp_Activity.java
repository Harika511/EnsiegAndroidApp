package com.skylka.ensieg.activities;

import java.util.ArrayList;

import com.skylka.ensieg.R;
import com.skylka.ensieg.database.EnsiegDB;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Ensieg_EmailOtp_Activity extends Activity {
	TextView tvOtp;
	EnsiegDB database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.otpverification);
		tvOtp = (TextView) findViewById(R.id.tv_sent);
		database = new EnsiegDB(getApplicationContext());
		database.Open();
		ArrayList<String> allRegisters = database.getAllRegisters();
		database.close();
		tvOtp.setText(" sent to your email" + allRegisters.get(4));
	}

}
