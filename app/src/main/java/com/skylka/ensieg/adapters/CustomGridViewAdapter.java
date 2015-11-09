package com.skylka.ensieg.adapters;

import java.util.ArrayList;

import com.skylka.ensieg.R;
import com.skylka.ensieg.model.GameModel;
import com.skylka.ensieg.model.GroupModel;
import com.skylka.ensieg.model.Item;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author manish.s
 *
 */
public class CustomGridViewAdapter extends ArrayAdapter<GroupModel> {
	Context context;
	int layoutResourceId;
	ArrayList<GroupModel> data = new ArrayList<GroupModel>();

	public CustomGridViewAdapter(Context context, int layoutResourceId, ArrayList<GroupModel> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.team_player, parent, false);

			holder = new RecordHolder();
			holder.txtTitle = (TextView) row.findViewById(R.id.nameTextView);
			holder.imageItem = (TextView) row.findViewById(R.id.iconTextView);

			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}

		GroupModel item = data.get(position);
		String uName = item.getName();
		holder.txtTitle.setText(uName);
		holder.imageItem.setText(uName.substring(0, 1).toUpperCase());
		// holder.imageItem.setBackground(new
		// BitmapDrawable(context.getResources(), item.getImage()));
		// holder.imageItem.setImageBitmap(item.getImage());
		return row;

	}

	static class RecordHolder {
		TextView txtTitle;
		TextView imageItem;

	}
}