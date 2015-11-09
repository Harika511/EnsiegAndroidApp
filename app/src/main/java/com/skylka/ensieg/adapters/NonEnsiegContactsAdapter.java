package com.skylka.ensieg.adapters;

import java.util.List;

import com.skylka.ensieg.R;
import com.skylka.ensieg.model.ContactBean;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NonEnsiegContactsAdapter extends BaseAdapter {
	Activity activity;
	List<ContactBean> items;

	public NonEnsiegContactsAdapter(Activity act, List<ContactBean> items) {
		activity = act;
		this.items = items;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
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
		ViewHolder holder;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.alluser_row, null);
			holder = new ViewHolder();
			view.setTag(holder);
			holder.tvname = (TextView) view.findViewById(R.id.tvname);
			holder.tvPhoneNo = (TextView) view.findViewById(R.id.tvphone);
			holder.iv_ensieg = (ImageView) view.findViewById(R.id.iv_ensiegContact);
			holder.bt_invite = (Button) view.findViewById(R.id.bt_invite);

		} else {
			holder = (ViewHolder) view.getTag();
		}
		ContactBean bean = items.get(position);
		Log.d("", "bean is " + bean + " " + holder);
		if (bean != null) {
			holder.tvname.setText(bean.getName());
			holder.tvPhoneNo.setText(bean.getPhoneNo());
			holder.iv_ensieg.setVisibility(View.GONE);
			holder.bt_invite.setVisibility(View.VISIBLE);
		}
		return view;
	}

	public class ViewHolder {
		public TextView tvname, tvPhoneNo;
		ImageView iv_ensieg;
		Button bt_invite;
	}
}
