package com.skylka.ensieg.adapters;

import java.util.ArrayList;

import com.skylka.ensieg.R;
import com.skylka.ensieg.model.MessageModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter {
	ArrayList<MessageModel> mMessages;
	Context mContext;
	LayoutInflater mLayoutInflater;

	public MessageAdapter(Context context, ArrayList<MessageModel> messages) {
		mContext = context;
		mMessages = messages;

		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return (mMessages == null ? 0 : mMessages.size());
	}

	@Override
	public Object getItem(int index) {
		if (mMessages == null || mMessages.size() < index)
			return null;
		return mMessages.get(index);
	}

	@Override
	public long getItemId(int index) {
		return 0;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder = new ViewHolder();
		// Setting image for stamp
		if (view == null) {
			holder = new ViewHolder();
			view = mLayoutInflater.inflate(R.layout.chat_item, null);
			holder.txtSender = (TextView) view.findViewById(R.id.txt_sender);
			holder.txtMessage = (TextView) view.findViewById(R.id.txt_message);
			holder.wrapper = (LinearLayout) view.findViewById(R.id.wrapper);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		MessageModel message = mMessages.get(position);
		if (message == null)
			return view;
		holder.txtMessage.setText(message.getMessage());
		holder.txtSender.setText(message.getSender());
		// holder.txtSender.setText(message.getSender() + ":");
		// if (message.isMine().equals("1")) {
		// holder.txtSender.setVisibility(View.INVISIBLE);
		// } else {
		// holder.txtSender.setVisibility(View.VISIBLE);
		// }
		holder.txtMessage.setText(message.getMessage());
		holder.txtMessage.setBackgroundResource(
				message.isMine().equals("1") ? R.drawable.bubble_green : R.drawable.bubble_yellow);
		 holder.wrapper.setGravity(message.isMine().equals("1") ?
		 Gravity.RIGHT : Gravity.LEFT);
		return view;
	}

	static class ViewHolder {
		TextView txtSender;
		TextView txtMessage;
		LinearLayout wrapper;
	}
	// android:background="@drawable/bubble_yellow"
}
