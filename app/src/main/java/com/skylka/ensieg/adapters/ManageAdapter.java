package com.skylka.ensieg.adapters;

import com.skylka.ensieg.R;
import com.skylka.ensieg.adapters.ContanctAdapter.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ManageAdapter extends BaseAdapter {
	Activity activity;
	ViewHolder holder;
	public ManageAdapter(Activity activity) {
		this.activity=activity;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 30;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.manage_adapter, null);

			holder = new ViewHolder();
			holder.tvteam1=(TextView)view.findViewById(R.id.tv_team1);
			holder.tvteam2=(TextView) view.findViewById(R.id.tv_team2);
			holder.tvteam1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					holder.tvteam1.setTextColor(activity.getResources().getColor(R.color.login_btn_end));
					holder.tvteam1.setBackgroundColor(activity.getResources().getColor(android.R.color.black));
					holder.tvteam2.setBackgroundColor(activity.getResources().getColor(R.color.transparent_actionbar));
					holder.tvteam2.setTextColor(activity.getResources().getColor(android.R.color.black));					
				}
			});
			holder.tvteam2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					holder.tvteam2.setTextColor(activity.getResources().getColor(R.color.login_btn_end));
					holder.tvteam2.setBackgroundColor(activity.getResources().getColor(android.R.color.black));
					holder.tvteam1.setBackgroundColor(activity.getResources().getColor(R.color.transparent_actionbar));
					holder.tvteam1.setTextColor(activity.getResources().getColor(android.R.color.black));					
				}
			});
			view.setTag(holder);
			// view.setTag(R.id.tvname, holder.tvname);
			// view.setTag(R.id.tvphone, holder.tvPhoneNo);
			// view.setTag(R.id.iv_ensiegContact, holder.iv_ensieg);
			// view.setTag(R.id.bt_invite, holder.bt_invite);
			// view.setTag(R.id.selected_contact_cb, holder.getSelectedcb());
		} else {
			holder = (ViewHolder) view.getTag();
		}
		return view;
	}

	public class ViewHolder {
		TextView tvname, tvteam1, tvteam2;
		ImageView ivOk, ivReject;
	}
}
