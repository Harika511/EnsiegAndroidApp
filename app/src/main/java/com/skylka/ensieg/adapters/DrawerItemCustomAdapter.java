package com.skylka.ensieg.adapters;

import com.skylka.ensieg.R;
import com.skylka.ensieg.activities.Ensieg_HomeActivity;
import com.skylka.ensieg.activities.ObjectDrawerItem;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DrawerItemCustomAdapter extends BaseAdapter {
	Activity context;
	public LayoutInflater inflater;
	Typeface font;
	int selected_position;
	ObjectDrawerItem[] list;

	public DrawerItemCustomAdapter(Ensieg_HomeActivity ensieg_HomeActivity, ObjectDrawerItem[] drawerItem) {
		this.context = ensieg_HomeActivity;
		list = drawerItem;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		font = Typeface.createFromAsset(context.getAssets(), "MYRIADPRO-REGULAR.ttf");
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();

			convertView = inflater.inflate(R.layout.listview_item_row, parent, false);
			holder.menu_tx = (TextView) convertView.findViewById(R.id.menu_tx);
			holder.line = (ImageView) convertView.findViewById(R.id.line_menu);
			holder.menu_img = (ImageView) convertView.findViewById(R.id.menu_icon);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.menu_tx.setTypeface(font);
		holder.menu_tx.setText(list[position].name);

		if (selected_position == position) {
			holder.menu_img.setImageResource(list[position].icon_selected);
			holder.line.setVisibility(View.VISIBLE);
			holder.menu_tx.setTextColor(context.getResources().getColor(R.color.login_btn_end));
		} else {
			holder.menu_img.setImageResource(list[position].icon);
			holder.line.setVisibility(View.INVISIBLE);
			holder.menu_tx.setTextColor(Color.WHITE);
		}

		return convertView;
	}

	public static class ViewHolder {
		TextView menu_tx;

		ImageView menu_img, line;

	}

	public void selectedItem(int position) {
		selected_position = position;

	}

}