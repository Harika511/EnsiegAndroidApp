package com.skylka.ensieg.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.skylka.ensieg.R;
import com.skylka.ensieg.model.ContactBean;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ContanctAdapter extends BaseAdapter {

	private static Activity activity;
	private static List<ContactBean> items;
	private static int row;
	// private ContactBean objBean;
	String displayType;
	private List<ContactBean> ensieg_group = new ArrayList<ContactBean>();

	public ContanctAdapter(Activity act, int row, List<ContactBean> items, String type) {
		// super(act, row, items);
		this.activity = act;
		this.row = row;
		this.items = items;
		displayType = type;
	}

	public ContanctAdapter() {
		// super(activity, row, items);
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		ContactBean bean = items.get(position);
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(row, null);

			holder = new ViewHolder();
			view.setTag(holder);
			view.setTag(R.id.tvname, holder.tvname);
			view.setTag(R.id.tvphone, holder.tvPhoneNo);
			view.setTag(R.id.iv_ensiegContact, holder.iv_ensieg);
			view.setTag(R.id.bt_invite, holder.bt_invite);
			view.setTag(R.id.selected_contact_cb, holder.getSelectedcb());
		} else {
			holder = (ViewHolder) view.getTag();
		}

		// if ((items == null) || ((position + 1) > items.size()))
		// return view;

		

		holder.tvname = (TextView) view.findViewById(R.id.tvname);
		holder.tvPhoneNo = (TextView) view.findViewById(R.id.tvphone);
		holder.iv_ensieg = (ImageView) view.findViewById(R.id.iv_ensiegContact);
		holder.bt_invite = (Button) view.findViewById(R.id.bt_invite);
		holder.setSelectedcb((CheckBox) view.findViewById(R.id.selected_contact_cb));
		Log.d("", "  contacts adapter" + bean.getName().trim() + " " + bean.getPhoneNo());
		if (holder.tvname != null && null != bean.getName()) {
			holder.tvname.setText(Html.fromHtml(bean.getName()));
		}
		if (holder.tvPhoneNo != null && null != bean.getPhoneNo()) {
			holder.tvPhoneNo.setText(Html.fromHtml(bean.getPhoneNo()));
		}
		if (displayType.equals("contacts")) {
			holder.getSelectedcb().setVisibility(View.GONE);
			// if (bean.isEnsiegContact()) {
			holder.iv_ensieg.setVisibility(View.VISIBLE);
			holder.bt_invite.setVisibility(View.GONE);

			// }
		} else if (displayType.equals("non_ensieg_contacts")) {
			holder.getSelectedcb().setVisibility(View.GONE);

			holder.iv_ensieg.setVisibility(View.GONE);
			holder.bt_invite.setVisibility(View.VISIBLE);
		} else if (displayType.equals("teams")) {

			holder.iv_ensieg.setVisibility(View.GONE);
			holder.bt_invite.setVisibility(View.GONE);
			holder.getSelectedcb().setVisibility(View.VISIBLE);

		}
		// holder.selectedcb.setOnCheckedChangeListener(new
		// CompoundButton.OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView, boolean
		// isChecked) {
		// int getPosition = (Integer) buttonView.getTag();
		// ContactBean bean=items.get(getPosition);
		// bean.setAddToGroup(buttonView.isChecked());
		// ensieg_group.add(bean);
		// }
		// });
		return view;
	}

	public class ViewHolder {
		public TextView tvname, tvPhoneNo;
		ImageView iv_ensieg;
		Button bt_invite;
		private CheckBox selectedcb;

		public CheckBox getSelectedcb() {
			return selectedcb;
		}

		public void setSelectedcb(CheckBox selectedcb) {
			this.selectedcb = selectedcb;
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}
