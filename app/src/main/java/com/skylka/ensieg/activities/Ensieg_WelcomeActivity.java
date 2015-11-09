package com.skylka.ensieg.activities;

import com.skylka.ensieg.R;
import com.skylka.ensieg.constants.Ensieg_AppConstants;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * It allows the user for signup or login
 * 
 * @author harika
 *
 */
public class Ensieg_WelcomeActivity extends Activity {

	Button btsignup, btlogin;

	Typeface custom_font_bold, custom_font_semi;

	LinearLayout logoLayout;
	private static int SPLASH_TIME_OUT = 0;

	/*
	 * It will display the layout of this Activity
	 * 
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.logoscreen);
		btsignup = (Button) findViewById(R.id.bt_logo_signup);
		btlogin = (Button) findViewById(R.id.bt_logo_login);
		Typeface font = Typeface.createFromAsset(getAssets(), "MYRIADPRO-REGULAR.ttf");
		btsignup.setTypeface(font);
		btlogin.setTypeface(font);

		logoLayout = (LinearLayout) findViewById(R.id.loholayout1);
		
		/*
		 * It is called whenever new user want to signup.
		 */

		btsignup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent in = new Intent(Ensieg_WelcomeActivity.this, Ensieg_SignupActivity.class);
				startActivity(in);
				 finish();

			}
		});
		/*
		 * It is called whenever old user want to login.
		 */
		btlogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Ensieg_AppConstants.loginFromTheDifferentDevice = true;

				Intent in = new Intent(Ensieg_WelcomeActivity.this, Ensieg_LoginActivity.class);
				startActivity(in);
				 finish();

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		return super.onOptionsItemSelected(item);
	}

}
