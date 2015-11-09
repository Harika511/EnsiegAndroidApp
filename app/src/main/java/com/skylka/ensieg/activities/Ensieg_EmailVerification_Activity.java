package com.skylka.ensieg.activities;

import com.skylka.ensieg.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 
 * @author harika This class is for to verify the email of the user when user
 *         forget his password
 */
public class Ensieg_EmailVerification_Activity extends Activity implements OnClickListener {

	EditText etMail;
	Button emailSubmit;
  
	/**
	 * By this view can be displayed
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email_verification);
		etMail = (EditText) findViewById(R.id.et_usr_email);
		emailSubmit = (Button) findViewById(R.id.bt_send_email);
		emailSubmit.setOnClickListener(this);
	}

	/**
	 * This will called when user clicked on emailSubmit button to submit email
	 * to the service
	 */
	@Override
	public void onClick(View v) {
		etMail.getText().toString();

		Intent resetPwd = new Intent(Ensieg_EmailVerification_Activity.this, Ensieg_HomeActivity.class);
		startActivity(resetPwd);
		
	}

}
