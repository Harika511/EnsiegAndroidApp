package com.skylka.ensieg.activities;

import com.skylka.ensieg.R;
import com.skylka.ensieg.adapters.ManageAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class Ensieg_HostManageActivity extends Activity{
	ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_activity);
		lv=(ListView) findViewById(R.id.manage_list);
		lv.setAdapter(new ManageAdapter(Ensieg_HostManageActivity.this));
	}

}
