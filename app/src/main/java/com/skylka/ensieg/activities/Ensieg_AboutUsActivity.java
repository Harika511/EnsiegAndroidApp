package com.skylka.ensieg.activities;

import com.skylka.ensieg.R;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Ensieg_AboutUsActivity extends Activity implements OnClickListener {
	RelativeLayout rate_us_rl, like_us_rl, legal_rl;
	ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutus);
		rate_us_rl = (RelativeLayout) findViewById(R.id.rate_rl);
		like_us_rl = (RelativeLayout) findViewById(R.id.like_rl);
		legal_rl = (RelativeLayout) findViewById(R.id.legal_rl);
		back = (ImageView) findViewById(R.id.about_back);
		back.setOnClickListener(this);
		rate_us_rl.setOnClickListener(this);
		like_us_rl.setOnClickListener(this);
		legal_rl.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rate_rl:
			Uri uri = Uri.parse("market://details?id=com.facebook.katana");
			Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
			try {
				startActivity(goToMarket);
			} catch (ActivityNotFoundException e) {
				startActivity(new Intent(Intent.ACTION_VIEW,
						Uri.parse("https://play.google.com/store/apps/details?id=com.facebook.katana")));
			}
			break;

		case R.id.like_rl:
			try {
				Ensieg_AboutUsActivity.this.getPackageManager().getPackageInfo("com.facebook.katana", 0);
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/875985722487305")));
			} catch (Exception e) {
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/ensieg?fref=ts")));
			}
			break;

		case R.id.legal_rl:
			startActivity(new Intent(Ensieg_AboutUsActivity.this, EnsiegLegalActivity.class));
			break;
		case R.id.about_back:
			Ensieg_AboutUsActivity.this.finish();
			break;
		}

	}

}
