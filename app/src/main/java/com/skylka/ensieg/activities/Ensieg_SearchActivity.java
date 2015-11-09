package com.skylka.ensieg.activities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.skylka.ensieg.R;
import com.skylka.ensieg.adapters.GameFeedAdapter;
import com.skylka.ensieg.constants.SportsUtilities;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.model.EventModel;
import com.skylka.ensieg.model.GameModel;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class Ensieg_SearchActivity extends Activity implements OnClickListener {
	EditText etSearchWord;
	ImageButton ivGo;
	ListView lv;
	ArrayList<GameModel> favoriate_list;
	Set<String> nameOfHost = new HashSet<String>();
	ArrayList<String> date = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_list);
		initializeViews();
	}

	private void initializeViews() {
		etSearchWord = (EditText) findViewById(R.id.search_word);
		ivGo = (ImageButton) findViewById(R.id.search_iv);
		lv = (ListView) findViewById(R.id.search_lv);
		ivGo.setOnClickListener(this);
		EnsiegDB database = new EnsiegDB(Ensieg_SearchActivity.this);
		database.Open();
		final ArrayList<EventModel> alllist = database.getCardsFromDB(0);
		database.close();
		int size = alllist.size();
		for (int i = 0; i < size; i++) {
			nameOfHost.add(alllist.get(i).getNameofhost());
			date.add(alllist.get(i).getDate());
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.search_iv:
			String searchWord = etSearchWord.getText().toString();

			if (searchWord.length() > 0 && !searchWord.isEmpty()) {

				favoriate_list = new ArrayList<GameModel>();
				EnsiegDB database = new EnsiegDB(Ensieg_SearchActivity.this);

				database.Open();
				// for (int i = 0; i < favoriate_list.size(); i++) {
				// database.getCardsFromDBUserName(searchWord, 1);
				if (searchWord.toLowerCase().contains("bits")) {

					final ArrayList<EventModel> list = database.getCardsFromDB(0);
					database.close();

					if (list.size() > 0) {
						GameFeedAdapter adapter = new GameFeedAdapter(Ensieg_SearchActivity.this, list);
						lv.setAdapter(adapter);
						lv.setVisibility(View.VISIBLE);

					} else {
						Toast.makeText(Ensieg_SearchActivity.this, "There is no data with given value",
								Toast.LENGTH_LONG).show();
					}
				} else {
					String lowercase = searchWord.toLowerCase().trim();

					Iterator iterator = nameOfHost.iterator();

					// check values
					while (iterator.hasNext()) {
						String existed = iterator.next().toString();
						String nameLower = existed.toLowerCase();
						if (nameLower.contains(lowercase)) {
							database.Open();
							final ArrayList<EventModel> list = database.getCardsFromDBUserName(existed, 0);
							database.close();
							if (list.size() > 0) {
								GameFeedAdapter adapter = new GameFeedAdapter(Ensieg_SearchActivity.this, list);
								lv.setAdapter(adapter);
								lv.setVisibility(View.VISIBLE);
							} else {
								Toast.makeText(Ensieg_SearchActivity.this, "There is no data with given value",
										Toast.LENGTH_LONG).show();
								// System.exit(0);
								break;
							}
						} else {
							String lowercaseDate = searchWord.toLowerCase().trim();
							int dateSize = date.size();
							boolean enteredElse = false;
							for (int d = 0; d < dateSize; d++) {
								String existedDate = date.get(d);
								if (existedDate.toLowerCase().contains(lowercaseDate)) {
									database.Open();
									final ArrayList<EventModel> list = database.getCardsFromDBUserName(lowercaseDate,
											1);
									database.close();
									if (list.size() > 0) {
										enteredElse = true;
										GameFeedAdapter adapter = new GameFeedAdapter(Ensieg_SearchActivity.this, list);
										lv.setAdapter(adapter);
										lv.setVisibility(View.VISIBLE);
									}
								}
							}
							if (!enteredElse) {
								enteredElse = false;
								Toast.makeText(Ensieg_SearchActivity.this, "There is no data with given value",
										Toast.LENGTH_LONG).show();
								break;
								// System.exit(0);
							}

						}
						// System.out.println("Value: "+iterator.next() + " ");
					}

					Toast.makeText(Ensieg_SearchActivity.this, "in name " + searchWord + " " + searchWord.length(),
							Toast.LENGTH_LONG).show();

				}

				// }
			} else {
				Toast.makeText(Ensieg_SearchActivity.this, "Please enter search word", Toast.LENGTH_LONG).show();
			}
			break;

		default:
			break;
		}

	}

}
