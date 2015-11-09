package com.skylka.ensieg.activities;

import java.util.ArrayList;

import com.skylka.ensieg.R;
import com.skylka.ensieg.adapters.CustomGridViewAdapter;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.model.GroupModel;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Ensieg_TeamsActivity extends Activity {

	ImageView ivBAck;
	TextView tv;
	private PlayersListViewAdapter playerArrayAdapter, playerArrayAdapter1, playerArrayAdapter2;
	RecyclerView recyclerView, recyclerView1, recyclerView2;
	GridView gridView;
	CustomGridViewAdapter customGridAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.teams);

		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Toast.makeText(Ensieg_TeamsActivity.this, "" + position, Toast.LENGTH_SHORT).show();
			}
		});
		recyclerView = (RecyclerView) findViewById(R.id.recyclerView_teams);
		recyclerView1 = (RecyclerView) findViewById(R.id.recyclerView_teams1);
		recyclerView2 = (RecyclerView) findViewById(R.id.recyclerView_teams2);

		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.HORIZONTAL);
		recyclerView.setLayoutManager(llm);
		LinearLayoutManager llm1 = new LinearLayoutManager(this);
		llm1.setOrientation(LinearLayoutManager.HORIZONTAL);
		recyclerView1.setLayoutManager(llm1);
		LinearLayoutManager llm2 = new LinearLayoutManager(this);
		llm2.setOrientation(LinearLayoutManager.HORIZONTAL);
		recyclerView2.setLayoutManager(llm2);
		// recyclerView.addItemDecoration(new
		// DividerItemDecoration(getActivity(),
		// DividerItemDecoration.VERTICAL_LIST));
		EnsiegDB db = new EnsiegDB(getApplicationContext());
		db.Open();
		ArrayList<GroupModel> maingroup = db.getGroupData();
		db.close();
		customGridAdapter = new CustomGridViewAdapter(this, R.layout.row_grid, maingroup);
		gridView.setAdapter(customGridAdapter);
		playerArrayAdapter = new PlayersListViewAdapter(maingroup);
		// recyclerView.setAdapter(playerArrayAdapter);
		// recyclerView.addOnItemTouchListener(
		// new RecyclerItemClickListener(context, new
		// RecyclerItemClickListener.OnItemClickListener() {
		// @Override public void onItemClick(View view, int position) {
		// // TODO Handle item click
		// }
		// })
		// );

		GestureDetector mGestureDetector;

		recyclerView.addOnItemTouchListener(new OnItemTouchListener() {

			@Override
			public void onTouchEvent(RecyclerView arg0, MotionEvent arg1) {
				Toast.makeText(getApplicationContext(), "touchevent", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onRequestDisallowInterceptTouchEvent(boolean arg0) {
				Toast.makeText(getApplicationContext(), "onRequestDisallowInterceptTouchEvent", Toast.LENGTH_LONG)
						.show();
			}

			@Override
			public boolean onInterceptTouchEvent(RecyclerView arg0, MotionEvent arg1) {
				Toast.makeText(getApplicationContext(), "onInterceptTouchEvent", Toast.LENGTH_LONG).show();

				return false;
			}
		});
		// Log.d("", " group size is " + group.size());
		// if (maingroup.size() > 0) {
		// ArrayList<GroupModel> group ;
		// ArrayList<GroupModel> tempGroup ;
		// ArrayList<GroupModel> tempGroup1 ;
		// ArrayList<GroupModel> group1;
		// ArrayList<GroupModel> group2;
		// if(maingroup.size()>5){
		// group=(ArrayList<GroupModel>) maingroup.subList(0, 5);
		// playerArrayAdapter = new PlayersListViewAdapter(group);
		// recyclerView.setAdapter(playerArrayAdapter);
		// tempGroup=(ArrayList<GroupModel>) maingroup.subList(5,
		// maingroup.size());
		// if(tempGroup.size()>5){
		// group1=(ArrayList<GroupModel>) tempGroup.subList(0, 5);
		// playerArrayAdapter1 = new PlayersListViewAdapter(group1);
		// recyclerView1.setAdapter(playerArrayAdapter1);
		// tempGroup1=(ArrayList<GroupModel>) maingroup.subList(5,
		// tempGroup.size());
		// if(tempGroup1.size()>5){
		// group2=(ArrayList<GroupModel>) tempGroup1.subList(0, 5);
		// playerArrayAdapter2 = new PlayersListViewAdapter(group2);
		// recyclerView2.setAdapter(playerArrayAdapter2);
		// // tempGroup1=(ArrayList<GroupModel>) maingroup.subList(5,
		// tempGroup.size());
		// }
		// else{
		// playerArrayAdapter2 = new PlayersListViewAdapter(tempGroup1);
		// recyclerView2.setAdapter(playerArrayAdapter2);
		// }
		// }
		// else{
		// playerArrayAdapter1 = new PlayersListViewAdapter(tempGroup);
		// recyclerView1.setAdapter(playerArrayAdapter1);
		// }
		// }else{
		//
		// playerArrayAdapter = new PlayersListViewAdapter(maingroup);
		// recyclerView.setAdapter(playerArrayAdapter);
		// }
		// }
		ivBAck = (ImageView) findViewById(R.id.back);
		ivBAck.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Ensieg_TeamsActivity.this.finish();

			}
		});
		tv = (TextView) findViewById(R.id.add_team_tv);
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent contact = new Intent(Ensieg_TeamsActivity.this, Ensieg_TeamGroupsActivity.class);
				startActivity(contact);
			}

		});
	}

	// public RecyclerItemClickListener(Context context, OnItemClickListener
	// listener) {
	// mListener = listener;
	// mGestureDetector = new GestureDetector(context, new
	// GestureDetector.SimpleOnGestureListener() {
	// @Override
	// public boolean onSingleTapUp(MotionEvent e) {
	// return true;
	// }
	// });
	// }

	public class GroupView extends RecyclerView.ViewHolder {
		TextView name;
		TextView icon;

		public GroupView(View itemView) {
			super(itemView);
			name = (TextView) itemView.findViewById(R.id.nameTextView);
			icon = (TextView) itemView.findViewById(R.id.iconTextView);
			if (name == null) {
				throw new RuntimeException("Name is null");
			}
			Log.e("PlayerView", String.valueOf(name));
		}

	}

	public class PlayersListViewAdapter extends RecyclerView.Adapter<GroupView> {
		ArrayList<GroupModel> groups;

		public PlayersListViewAdapter(ArrayList<GroupModel> groups) {
			super();
			this.groups = groups;
		}

		@Override
		public int getItemCount() {
			return groups.size();
		}

		public void addItem(GroupModel group, int index) {
			groups.add(index, group);
			notifyItemChanged(index);
		}

		public void deleteItem(int index) {
			groups.remove(index);
			notifyItemRemoved(index);
		}

		public void changeData(ArrayList<GroupModel> groups) {
			this.groups = groups;
			notifyDataSetChanged();
		}

		@Override
		public GroupView onCreateViewHolder(ViewGroup viewGroup, int i) {
			Log.i("OnCreateViewHolder", String.valueOf(i));
			Context context = viewGroup.getContext();
			LayoutInflater layoutInflater = LayoutInflater.from(context);
			View view = layoutInflater.inflate(R.layout.team_player, viewGroup, false);
			GroupView playerView = new GroupView(view);
			Log.i("OnCreateViewHolder", String.valueOf(view.findViewById(R.id.nameTextView)));
			return playerView;
		}

		@SuppressLint("NewApi")
		@Override
		public void onBindViewHolder(GroupView playerView, int i) {
			String name = groups.get(i).getName();
			Log.i("AdapterLog", String.valueOf(i) + name);
			playerView.icon.setText(name.substring(0, 1).toUpperCase());
			int sdk = android.os.Build.VERSION.SDK_INT;
			// // if (groups.get(i).hosting) {
			//
			// if (sdk < 16) {
			// playerView.icon.setBackgroundDrawable(getDrawable(R.drawable.blue_gradient_host));
			// } else {
			// //
			// playerView.icon.setBackgroundResource(getDrawable(R.drawable.blue_gradient_host));
			// playerView.icon.setBackground(getResources().getDrawable(R.drawable.blue_gradient_host));
			// }
			//
			// } else {

			if (sdk < 16) {
				playerView.icon.setBackgroundDrawable(getDrawable(R.drawable.profile_logo));
			} else {
				//
				playerView.icon.setBackground(getResources().getDrawable(R.drawable.profile_logo));
			}
			// }
			playerView.name.setText(name);
		}
	}

}
