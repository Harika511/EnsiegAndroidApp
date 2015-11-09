package com.skylka.ensieg.adapters;

import java.util.List;

import com.skylka.ensieg.R;
import com.skylka.ensieg.model.GroupModel;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class GroupAdapter extends BaseAdapter {

	private static Activity activity;
	private static List<GroupModel> items;
	private static int row;
	private GroupModel objBean;
	String displayType;

	public GroupAdapter(Activity act, List<GroupModel> items) {

		this.activity = act;
		this.items = items;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder1 holder;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.group_data, null);
			holder = new ViewHolder1();
			view.setTag(holder);
			view.setTag(R.id.tv_group_name, holder.tvname);
			view.setTag(R.id.iv_group_logo, holder.iv_ensieg);
			view.setTag(R.id.selected_group_cb, holder.getSelectedcb());
		} else {
			holder = (ViewHolder1) view.getTag();
		}

		if ((items == null) || ((position + 1) > items.size()))
			return view;

		objBean = items.get(position);

		holder.tvname = (TextView) view.findViewById(R.id.tv_group_name);
		holder.iv_ensieg = (ImageView) view.findViewById(R.id.iv_group_logo);
		holder.setSelectedcb((CheckBox) view.findViewById(R.id.selected_group_cb));
		if (holder.tvname != null && null != objBean.getName() && objBean.getName().trim().length() > 0) {
			holder.tvname.setText(Html.fromHtml(objBean.getName()));
		}

		return view;
	}

	public class ViewHolder1 {
		public TextView tvname;
		ImageView iv_ensieg;
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
