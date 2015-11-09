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
import com.skylka.ensieg.constants.Ensieg_AppConstants;
import com.skylka.ensieg.constants.SportsUtilities;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.model.EventModel;
import com.skylka.ensieg.model.TeamModel;
import com.skylka.ensieg.model.UserProfileModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Ensieg_MatchCenter2 extends Activity {
	String selectedTeam = "";
	Context mContext;
	ImageView iv;
	String team1_Id, team2_Id, event_id, teamid, max_players;
	String openPositions1, maxPlayers1, openPositions2, maxPlayers2;
	TextView tvHostName, tvDate, tvPlace, tvOpenplaces, tvVerses;
	ImageView ivgameImage, sportImage, ivDivider, ivMessage, ivShare;
	int sportId;
	int[] game_white_background_imgs = { R.drawable.football_white_background, R.drawable.bb_white_background,
			R.drawable.vb_w_background, R.drawable.t_w_background, R.drawable.bm_w_background,
			R.drawable.tt_w_background, R.drawable.cr_w_background };
	TextView fst_teamName, second_teamName, fst_openplaces, second_openplaces, teamSize;
	RecyclerView recyclerView, recyclerView2;
	RelativeLayout team1, team2;
	String te;
	PlayersListViewAdapter playerArrayAdapter;
	PlayersListViewAdapter playerArrayAdapter1;
	ArrayList<Player> team1List = new ArrayList<Player>();
	ArrayList<Player> team2List = new ArrayList<Player>();
	ArrayList<Player> team11List = new ArrayList<Player>();
	ArrayList<Player> team22List = new ArrayList<Player>();
	Button requestToJoin, join;
	String hoster = "";
	String invited = "";
	String invitedEventId = "", invitedTeam1 = "", invitedTeam2 = "";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_center);
		mContext = this;
		initializeViews();
		tvPlace.setText("BITS PILANI Hyderabad");
		EnsiegDB register = new EnsiegDB(Ensieg_MatchCenter2.this);
		ArrayList<String> allRegisters = register.getAllRegisters();
		hoster = allRegisters.get(1);
		// PlayersListViewAdapter playerArrayAdapter = new
		// PlayersListViewAdapter(team1List);

		final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
		linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		recyclerView.setLayoutManager(linearLayoutManager);
		final LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext());
		linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
		recyclerView2.setLayoutManager(linearLayoutManager2);
		if (getIntent() != null) {
			ArrayList<String> value = getIntent().getStringArrayListExtra("event");
			// invited = getIntent().getStringExtra("invite");
			// String message1 = getIntent().getStringExtra("message");
			// Log.d("", " invited " + invited + " " + message1);
			requestToJoin.setVisibility(View.GONE);
			// if (value != null) {
			// Log.d("", "values are " + value);
			// event_id = value.get(5);
			// max_players = value.get(8);
			// team1_Id = value.get(9);
			// team2_Id = value.get(10);
			// te = value.get(7);
			// Log.d("", "team id in matcch " + team1_Id + " " + team2_Id + " "
			// + te);
			// sportId = Integer.parseInt(value.get(6));
			// hoster = value.get(1);
			// tvHostName.setText(hoster);
			// fst_teamName.setText(hoster);
			// tvDate.setText(value.get(2));
			// new GetMatchData().execute(Ensieg_AppConstants.appService_match);
			// } else
			// if (invited != null && invited.equals("invite")) {
			String message = getIntent().getStringExtra("message");
			parseEventDetails(message);
			new GetMatchData().execute(Ensieg_AppConstants.appService_match);
			// }
			// else {
			// tvHostName.setText(hoster);
			// fst_teamName.setText(hoster);
			// String time = getIntent().getStringExtra("time");
			// int type = getIntent().getIntExtra("type", 0);
			// String teamSize = getIntent().getStringExtra("teamSize");
			// te = getIntent().getStringExtra("typeofevent");
			// if (time != null && type != 0 && teamSize != null && te != null)
			// {
			//
			// Log.d("", " team data " + time + " " + type + " " + teamSize);
			// tvDate.setText(time);
			// tvVerses.setText(teamSize + "Vs" + teamSize);
			// sportImage.setImageResource(game_white_background_imgs[type]);
			//
			// }
			// }
		}
		Toast.makeText(getApplicationContext(), te + " type of event  ", Toast.LENGTH_LONG).show();
		if (te != null) {
			if (te.equals("1")) {
				join.setVisibility(View.VISIBLE);
				requestToJoin.setVisibility(View.GONE);
			} else if (te.equals("2")) {
				join.setVisibility(View.GONE);
				requestToJoin.setVisibility(View.VISIBLE);
			}
		}
		team1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Toast.makeText(getApplicationContext(), "team1",
				// Toast.LENGTH_LONG).show();
				selectedTeam = hoster;
				teamid = team1_Id;
				ivDivider.setImageResource(R.drawable.teama_divider);
				if (team1List.size() > 0) {
					playerArrayAdapter = new PlayersListViewAdapter(team1List);
					recyclerView.setAdapter(playerArrayAdapter);
				}
				if (team11List.size() > 0) {
					playerArrayAdapter1 = new PlayersListViewAdapter(team11List);
					recyclerView2.setAdapter(playerArrayAdapter1);
				}
			}
		});
		team2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Toast.makeText(getApplicationContext(), "team2",
				// Toast.LENGTH_LONG).show();
				selectedTeam = "Team-B";
				teamid = team2_Id;
				ivDivider.setImageResource(R.drawable.teamb_divider);
				if (team2List.size() > 0) {
					playerArrayAdapter = new PlayersListViewAdapter(team2List);
					recyclerView.setAdapter(playerArrayAdapter);
				}
				if (team22List.size() > 0) {
					playerArrayAdapter1 = new PlayersListViewAdapter(team22List);
					recyclerView2.setAdapter(playerArrayAdapter1);
				}
			}
		});
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		// setTeamData();

		// final Button JoinButton = (Button) findViewById(R.id.joinButton1);
		join.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// if (typeOfEvent.equals(1)) {
				if (selectedTeam.length() > 0 && team1List.size() < 6 && team11List.size() < 6 && team2List.size() < 6
						&& team22List.size() < 6) {
					AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
					builder.setTitle("Do you want to join " + selectedTeam + "?");
					builder.setPositiveButton("Join", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Log.d("", "join game before service " + event_id + " " + teamid + " " + selectedTeam);
							new JoinGame().execute(Ensieg_AppConstants.appService_join);

						}
					});
					builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					AlertDialog dialog = builder.create();
					dialog.show();
				} else {
					Toast.makeText(getApplicationContext(), "Please Select Team or no more space in a team",
							Toast.LENGTH_LONG).show();

				}
			}
			// }
		});

		requestToJoin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new RequestToJoin().execute(Ensieg_AppConstants.appService_requesttojoin);
			}
		});
	}

	public void initializeViews() {
		iv = (ImageView) findViewById(R.id.back_iv_match_center);
		tvHostName = (TextView) findViewById(R.id.tv_hostname);
		tvDate = (TextView) findViewById(R.id.textView15);
		tvPlace = (TextView) findViewById(R.id.textView17);
		tvOpenplaces = (TextView) findViewById(R.id.textView18);
		sportImage = (ImageView) findViewById(R.id.imageView13);
		tvVerses = (TextView) findViewById(R.id.textView16);
		fst_teamName = (TextView) findViewById(R.id.textView21);
		fst_openplaces = (TextView) findViewById(R.id.textView23);
		second_teamName = (TextView) findViewById(R.id.textView20);
		second_openplaces = (TextView) findViewById(R.id.textView22);
		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView2);
		team1 = (RelativeLayout) findViewById(R.id.team1);
		team2 = (RelativeLayout) findViewById(R.id.team2);
		ivDivider = (ImageView) findViewById(R.id.divider_img);
		ivMessage = (ImageView) findViewById(R.id.imageView11);
		ivShare = (ImageView) findViewById(R.id.imageView10);
		requestToJoin = (Button) findViewById(R.id.joinButton1);
		join = (Button) findViewById(R.id.joinButton2);

		ivMessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent chat = new Intent(Ensieg_MatchCenter2.this, Ensieg_ChatActivity.class);
				chat.putExtra("eventid", event_id);
				startActivity(chat);
			}
		});
		ivShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent in = new Intent(Ensieg_MatchCenter2.this, Ensieg_TeamGroupsActivity.class);
				in.putExtra("match", "match");
				in.putExtra("eventid", event_id);
				in.putExtra("eventtype", te);
				startActivity(in);
			}
		});
	}

	public void setTeamData(final ArrayList<Player> team1List, final ArrayList<Player> team2List) {

		if (team1List.size() > 1) {
			final PlayersListViewAdapter playerArrayAdapter = new PlayersListViewAdapter(team1List);

			final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
			linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
			recyclerView.setLayoutManager(linearLayoutManager);
			recyclerView.setAdapter(playerArrayAdapter);
		}
		if (team2List.size() > 1) {
			final PlayersListViewAdapter playerArrayAdapter1 = new PlayersListViewAdapter(team2List);

			final LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext());
			linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
			recyclerView2.setLayoutManager(linearLayoutManager2);
			recyclerView2.setAdapter(playerArrayAdapter1);

		}

	}

	public void parseEventDetails(String eventDetails) {

		try {
			JSONObject obj = new JSONObject(eventDetails);
			String date = obj.getString("date");
			invitedEventId = obj.getString("eventid");
			String host = obj.getString("host");
			JSONArray teamsArray = obj.getJSONArray("teams");
			invitedTeam1 = (String) teamsArray.get(0);
			invitedTeam2 = (String) teamsArray.get(1);
			Log.d("", date + " " + invitedEventId + " " + host + " teams data  " + invitedTeam1 + " " + invitedTeam2);
			// eventList.add(date);
			// eventList.add(eventid);
			// eventList.add(host);
			// eventList.add(s1);
			// eventList.add(s2);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public class PlayerView extends RecyclerView.ViewHolder {
		TextView name;
		TextView icon;

		public PlayerView(View itemView) {
			super(itemView);
			name = (TextView) itemView.findViewById(R.id.nameTextView);
			icon = (TextView) itemView.findViewById(R.id.iconTextView);
			if (name == null) {
				throw new RuntimeException("Name is null");
			}
			Log.e("PlayerView", String.valueOf(name));
		}

	}

	public class PlayersListViewAdapter extends RecyclerView.Adapter<PlayerView> {
		ArrayList<Player> players;

		public PlayersListViewAdapter(ArrayList<Player> players) {
			super();
			this.players = players;
		}

		@Override
		public int getItemCount() {
			return players.size();
		}

		public void addItem(Player player, int index) {
			players.add(index, player);
			notifyItemChanged(index);
		}

		public void deleteItem(int index) {
			players.remove(index);
			notifyItemRemoved(index);
		}

		public void changeData(ArrayList<Player> players) {
			this.players = players;
			notifyDataSetChanged();
		}

		@Override
		public PlayerView onCreateViewHolder(ViewGroup viewGroup, int i) {
			Log.i("OnCreateViewHolder", String.valueOf(i));
			Context context = viewGroup.getContext();
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			View view = layoutInflater.inflate(R.layout.team_player, viewGroup, false);
			PlayerView playerView = new PlayerView(view);
			Log.i("OnCreateViewHolder", String.valueOf(view.findViewById(R.id.nameTextView)));
			return playerView;
		}

		@SuppressLint("NewApi")
		@Override
		public void onBindViewHolder(PlayerView playerView, int i) {
			String name = players.get(i).getName();
			Log.i("AdapterLog", String.valueOf(i) + name);
			playerView.icon.setText(name.substring(0, 1).toUpperCase());
			int sdk = android.os.Build.VERSION.SDK_INT;
			if (players.get(i).hosting) {

				if (sdk < 16) {
					playerView.icon.setBackgroundDrawable(getDrawable(R.drawable.blue_gradient_host));
				} else {
					// playerView.icon.setBackgroundResource(getDrawable(R.drawable.blue_gradient_host));
					playerView.icon.setBackground(getResources().getDrawable(R.drawable.blue_gradient_host));
				}

			} else {

				if (sdk < 16) {
					playerView.icon.setBackgroundDrawable(getDrawable(R.drawable.blue_gradient_tick));
				} else {
					// playerView.icon.setBackground(getDrawable(R.drawable.blue_gradient_tick));
					playerView.icon.setBackground(getResources().getDrawable(R.drawable.blue_gradient_tick));
				}
			}
			playerView.name.setText(name);
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		// Intent home = new Intent(Ensieg_MatchCenter.this,
		// Ensieg_HomeActivity.class);
		// startActivity(home);
		// finish();
	}

	/**
	 * It will set and call the data to save the profile data to user
	 * 
	 * @author harika
	 *
	 */
	private class GetMatchData extends AsyncTask<String, Void, String> {
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
			ArrayList<String> loginData = ensiegdb.getLoginData();
			ArrayList<String> ensiegEvent_Details = ensiegdb.getEnsiegEvent_Details();
			ensiegdb.close();

			nameValuePairs.add(new BasicNameValuePair("sessionid", loginData.get(3)));
			if (ensiegEvent_Details.size() > 0) {
				nameValuePairs.add(new BasicNameValuePair("eventidfk", ensiegEvent_Details.get(0)));
				nameValuePairs.add(new BasicNameValuePair("teamid-1", ensiegEvent_Details.get(1)));
				nameValuePairs.add(new BasicNameValuePair("teamid-2", ensiegEvent_Details.get(2)));
			} else {
				Log.d("", " no event details ");
			}
			// }
			// } else {
			// nameValuePairs.add(new BasicNameValuePair("eventidfk",
			// event_id));
			// nameValuePairs.add(new BasicNameValuePair("teamid-1", team1_Id));
			// nameValuePairs.add(new BasicNameValuePair("teamid-2", team2_Id));
			// }
			// nameValuePairs.add(new BasicNameValuePair("email",
			// etmail.getText().toString()));
			// nameValuePairs.add(new BasicNameValuePair("phonenumber",
			// etphone.getText().toString()));

			return GET(urls[0], nameValuePairs);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			// Toast.makeText(getApplicationContext(), result + " match center
			// ", Toast.LENGTH_LONG).show();
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
	 * It will set and call the data to save the profile data to user
	 * 
	 * @author harika
	 *
	 */
	private class RequestToJoin extends AsyncTask<String, Void, String> {
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
			nameValuePairs.add(new BasicNameValuePair("eventid", event_id));
			// nameValuePairs.add(new BasicNameValuePair("teamid-1", team1_Id));
			// nameValuePairs.add(new BasicNameValuePair("teamid-2", team2_Id));
			// nameValuePairs.add(new BasicNameValuePair("email",
			// etmail.getText().toString()));
			// nameValuePairs.add(new BasicNameValuePair("phonenumber",
			// etphone.getText().toString()));

			return GET(urls[0], nameValuePairs);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(getApplicationContext(), result + " request match center", Toast.LENGTH_LONG).show();
			Log.d("", "values are " + result);
			JSONObject jsonRootObject;
			try {
				jsonRootObject = new JSONObject(result);
				String error = jsonRootObject.optString("error").toString();
				if (error.equals("0")) {
					// parseResultStoreInDB(result);
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

	// adb shellinput text
	public void parseResultStoreInDB(String result) {
		ArrayList<EventModel> event_list = new ArrayList<EventModel>();

		ArrayList<TeamModel> team_list = new ArrayList<>();
		team1List.clear();
		team2List.clear();
		team11List.clear();
		team22List.clear();
		try {
			JSONObject object = new JSONObject(result);
			JSONObject jobj = object.getJSONObject("data");
			JSONObject team1_jsonObj = jobj.getJSONObject("team-1");
			JSONObject team2_jsonObj = jobj.getJSONObject("team-2");
			/// team 1 data
			openPositions1 = team1_jsonObj.getString("openpostions").toString();
			maxPlayers1 = team1_jsonObj.getString("maxplayers").toString();

			JSONArray json_players = new JSONArray(team1_jsonObj.getString("players"));
			Log.d("eneter here ", "eneter here  " + json_players + "");
			int dataSize = json_players.length();
			for (int i = 0; i < dataSize; i++) {
				JSONObject player_details = json_players.getJSONObject(i);
				String name = player_details.getString("firstname");
				String photo = player_details.getString("photo");
				Player player = new Player(name);
				if (i <= 5) {
					team1List.add(player);
				} else {
					team11List.add(player);
				}
			}
			fst_teamName.setText(team1List.get(0).getName());
			Integer toInt1 = Integer.parseInt(openPositions1);
			if (toInt1 > 0) {
				fst_openplaces.setText(openPositions1 + " Open Places ");
			}
			// team2 data
			openPositions2 = team2_jsonObj.getString("openpostions").toString();
			maxPlayers2 = team2_jsonObj.getString("maxplayers").toString();
			String team2Players = team2_jsonObj.getString("players");
			if (team2Players != null && team2Players.length() > 0) {
				JSONArray json_players2 = new JSONArray(team2Players);
				for (int i = 0; i < json_players2.length(); i++) {
					JSONObject player_details1 = json_players2.getJSONObject(i);
					String name = player_details1.getString("firstname");
					String photo = player_details1.getString("photo");
					Player player = new Player(name);
					if (i <= 5) {
						team2List.add(player);
					} else {
						team22List.add(player);
					}
				}
			}
			int team1_open_positions = Integer.parseInt(openPositions1);
			int team2_open_positions = Integer.parseInt(openPositions2);
			int team1_maxplayers = Integer.parseInt(maxPlayers1);
			int team2_maxplayers = Integer.parseInt(maxPlayers2);
			Integer toInt = Integer.parseInt(openPositions1);
			if (toInt > 0) {
				tvOpenplaces.setText(openPositions1 + " Open Places ");
			}
			// second_teamName.setText(name2);
			Integer toInt2 = Integer.parseInt(openPositions2);
			if (toInt2 > 0) {
				second_openplaces.setText(openPositions2 + " Open Places ");
			}
			int difference_in_players1 = team1_maxplayers - team1_open_positions;
			int difference_in_players2 = team2_maxplayers - team2_open_positions;
			if (difference_in_players1 == 1 && difference_in_players2 == 1) {
				RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
				RecyclerView recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView2);
				recyclerView.setVisibility(View.GONE);
				recyclerView2.setVisibility(View.GONE);

			} else {
				if (selectedTeam.equals("Team-B")) {

					if (team2List.size() > 0) {
						playerArrayAdapter = new PlayersListViewAdapter(team2List);
						// recyclerView.setAdapter(playerArrayAdapter);
					}
					if (team22List.size() > 0) {
						playerArrayAdapter1 = new PlayersListViewAdapter(team22List);
						// recyclerView.setAdapter(playerArrayAdapter);
					}

				} else if (team1List.size() > 0) {
					playerArrayAdapter = new PlayersListViewAdapter(team1List);
				}
				if (team11List.size() > 0) {
					playerArrayAdapter1 = new PlayersListViewAdapter(team11List);
				}
				recyclerView.setAdapter(playerArrayAdapter);
				recyclerView2.setAdapter(playerArrayAdapter1);
			}

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
	private class JoinGame extends AsyncTask<String, Void, String> {
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
			Log.d("typeOfeven", "typeOfevent" + te);
			ArrayList<String> loginData = ensiegdb.getLoginData();
			ensiegdb.close();

			nameValuePairs.add(new BasicNameValuePair("sessionid", loginData.get(3)));
			nameValuePairs.add(new BasicNameValuePair("eventid", event_id));
			nameValuePairs.add(new BasicNameValuePair("typeofevent", te));
			nameValuePairs.add(new BasicNameValuePair("teamid", teamid));
			nameValuePairs.add(new BasicNameValuePair("maxplayers", max_players));
			// nameValuePairs.add(new BasicNameValuePair("email",
			// etmail.getText().toString()));
			// nameValuePairs.add(new BasicNameValuePair("phonenumber",
			// etphone.getText().toString()));

			return GET1(urls[0], nameValuePairs);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			// Toast.makeText(getApplicationContext(), result + " join game",
			// Toast.LENGTH_LONG).show();
			Log.d("", "join game  " + result);
			JSONObject jsonRootObject;
			try {
				jsonRootObject = new JSONObject(result);
				String error = jsonRootObject.optString("error").toString();
				if (error.equals("0")) {
					// selectedTeam = "";
					// parseResultStoreInDB(result);
					Toast.makeText(getApplicationContext(), "You are Playing Now ", Toast.LENGTH_LONG).show();
					new GetMatchData().execute(Ensieg_AppConstants.appService_match);
				} else {
					Toast.makeText(getApplicationContext(), "OOPS,Something went wrong!", Toast.LENGTH_LONG).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * It will set and call the data to save the profile data to user
	 * 
	 * @author harika
	 *
	 */
	private class RequestToJoinGame extends AsyncTask<String, Void, String> {
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
			Log.d("typeOfeven", "typeOfevent" + te);
			ArrayList<String> loginData = ensiegdb.getLoginData();
			ensiegdb.close();

			nameValuePairs.add(new BasicNameValuePair("sessionid", loginData.get(3)));
			nameValuePairs.add(new BasicNameValuePair("eventid", event_id));

			// nameValuePairs.add(new BasicNameValuePair("email",
			// etmail.getText().toString()));
			// nameValuePairs.add(new BasicNameValuePair("phonenumber",
			// etphone.getText().toString()));

			return GET1(urls[0], nameValuePairs);
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			// Toast.makeText(getApplicationContext(), result + " join game",
			// Toast.LENGTH_LONG).show();
			Log.d("", "join game  " + result);
			JSONObject jsonRootObject;
			try {
				jsonRootObject = new JSONObject(result);
				String error = jsonRootObject.optString("error").toString();
				if (error.equals("0")) {
					// selectedTeam = "";
					// parseResultStoreInDB(result);
					Toast.makeText(getApplicationContext(), "You are Playing Now ", Toast.LENGTH_LONG).show();
					new GetMatchData().execute(Ensieg_AppConstants.appService_match);
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
			Log.d(json, json + " json");
			jObj = new JSONObject(json);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}
		return jObj;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		// set the string passed from the service to the original intent
		setIntent(intent);

	}
}
