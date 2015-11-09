package com.skylka.ensieg.adapters;

import java.util.ArrayList;

import com.skylka.ensieg.R;
import com.skylka.ensieg.activities.Ensieg_PreHostAGameActivity;
import com.skylka.ensieg.model.GameModel;

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

public class HostListAdapter extends BaseAdapter {

	Activity context;
	public LayoutInflater inflater;
	Typeface font;
	int selected_position;
	ArrayList<GameModel> list;

	public HostListAdapter(Ensieg_PreHostAGameActivity ensieg_PreHostAGameActivity,
			ArrayList<GameModel> favoriate_list) {
		this.context = ensieg_PreHostAGameActivity;
		list = favoriate_list;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		font = Typeface.createFromAsset(context.getAssets(), "MYRIADPRO-REGULAR.ttf");
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();

			convertView = inflater.inflate(R.layout.host_listitem, parent, false);
			holder.host_tx = (TextView) convertView.findViewById(R.id.host_item_tx);
			holder.line = (ImageView) convertView.findViewById(R.id.line);
			holder.host_img = (ImageView) convertView.findViewById(R.id.host_img);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.host_tx.setTypeface(font);
		holder.host_tx.setText(list.get(position).getGame_name());
		holder.host_img.setImageResource(list.get(position).getGame_image());
		if (selected_position == position) {
			holder.host_img.setBackgroundResource(R.drawable.circle_green);
			holder.line.setVisibility(View.VISIBLE);
			holder.host_tx.setTextColor(context.getResources().getColor(R.color.login_btn_end));
		} else {
			holder.host_img.setBackgroundResource(R.drawable.circle_white);
			holder.line.setVisibility(View.INVISIBLE);
			holder.host_tx.setTextColor(Color.WHITE);
		}

		return convertView;
	}

	public static class ViewHolder {
		TextView host_tx;

		ImageView host_img, line;

	}

	public void selectedItem(int position) {
		selected_position = position;

	}
}
