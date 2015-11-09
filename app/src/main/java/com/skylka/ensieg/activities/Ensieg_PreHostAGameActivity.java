package com.skylka.ensieg.activities;

import java.util.ArrayList;

import com.skylka.ensieg.R;
import com.skylka.ensieg.adapters.HostListAdapter;
import com.skylka.ensieg.model.GameModel;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Ensieg_PreHostAGameActivity extends Activity implements OnClickListener {
	ListView host_list;
	TextView host_tx;
	ArrayList<GameModel> favoriate_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_prehostgame);

		initializeViews();

	}

	private void initializeViews() {
		host_tx = (TextView) findViewById(R.id.host_tx);
		host_list = (ListView) findViewById(R.id.host_listview);
		Typeface font = Typeface.createFromAsset(getAssets(), "MYRIADPRO-REGULAR.ttf");
		host_tx.setTypeface(font);
		ImageView hostback = (ImageView) findViewById(R.id.hostback);
		hostback.setOnClickListener(this);
		favoriate_list = (ArrayList<GameModel>) getIntent().getExtras().get("favoriate_list");
		final HostListAdapter adapter = new HostListAdapter(this, favoriate_list);
		host_list.setAdapter(adapter);
		adapter.selectedItem(-1);
		host_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				adapter.selectedItem(position);
				adapter.notifyDataSetChanged();
				Intent intent = new Intent(Ensieg_PreHostAGameActivity.this, Ensieg_HostAGameActivity.class);
				intent.putExtra("Game_Model", favoriate_list.get(position));
				startActivity(intent);
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.hostback:
			Ensieg_PreHostAGameActivity.this.finish();
			break;

		default:
			break;
		}
	}

}
