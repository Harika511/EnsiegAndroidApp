package com.skylka.ensieg.adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.skylka.ensieg.R;
import com.skylka.ensieg.constants.SportsUtilities;
import com.skylka.ensieg.model.EventModel;
import com.skylka.ensieg.model.GameModel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GameFeedAdapter extends BaseAdapter {
	Activity context;
	public LayoutInflater inflater;
	Typeface font, font_bold;
	ArrayList<EventModel> list;
	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	SimpleDateFormat fmtOut = new SimpleDateFormat("dd MMM  hh:mm aa");

	public GameFeedAdapter(FragmentActivity fragmentActivity, ArrayList<EventModel> list) {
		this.context = fragmentActivity;
		this.list = list;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		font = Typeface.createFromAsset(context.getAssets(), "MYRIADPRO-REGULAR.ttf");
		font_bold = Typeface.createFromAsset(context.getAssets(), "MYRIADPRO-SEMIBOLD.ttf");
	}

	public GameFeedAdapter(Activity fragmentActivity, ArrayList<EventModel> list) {
		this.context = fragmentActivity;
		this.list = list;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		font = Typeface.createFromAsset(context.getAssets(), "MYRIADPRO-REGULAR.ttf");
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int id) {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();

			convertView = inflater.inflate(R.layout.feed_listitem, parent, false);
			holder.chat_count = (TextView) convertView.findViewById(R.id.chat_count);
			holder.host_name = (TextView) convertView.findViewById(R.id.host_name);
			holder.game_versers_tx = (TextView) convertView.findViewById(R.id.game_versers_tx);
			holder.date_time_tx = (TextView) convertView.findViewById(R.id.date_time_tx);
			holder.location_tx = (TextView) convertView.findViewById(R.id.location_tx);
			holder.open_places_tx = (TextView) convertView.findViewById(R.id.open_places_tx);
			holder.host_img = (ImageView) convertView.findViewById(R.id.host_profile_img);
			holder.game_img = (ImageView) convertView.findViewById(R.id.game_image);
			holder.game_versers_tx.setTypeface(font_bold);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		/*if (list.get(position).getHost_photo() != null && list.get(position).getHost_photo().length() > 5) {

			
			 * * ///Bitmap old_Profile =
			 * StringToBitMap(list.get(position).getHost_photo());
			 * System.out.println(">>>>>>>>>>>" + old_Profile); if (old_Profile
			 * != null) {
			 
			byte[] imageAsBytes = Base64.decode(list.get(position).getHost_photo().getBytes(),
					Base64.DEFAULT | Base64.NO_WRAP);
			Bitmap b = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
			System.out.println(b + ">>>>>>>>>>>>>>>>>>>>");
			holder.host_img.setImageBitmap(Bitmap.createScaledBitmap(b, 50, 50, false));

			 * System.out.println("In bitmap resource"); 

		} else

		{*/
			holder.host_img.setImageResource(R.drawable.profile_logo);
		/* } */
		holder.chat_count.setText(list.get(position).getCommentcount());
//		Log.d("", "name of host is " + list.get(position).getNameofhost() + " "
//				+ list.get(position).getNameofhost().length());
		String name1 = list.get(position).getNameofhost();

		String host = "<html><b>" + list.get(position).getNameofhost() + "</b></html>" + " is hosting game";
		holder.host_name.setText(Html.fromHtml(host));
		holder.game_versers_tx.setText(list.get(position).getTeam_list().get(0).getMaxplayer() + " V "
				+ list.get(position).getTeam_list().get(1).getMaxplayer());

		holder.date_time_tx.setText(

		getParsedDate(list.get(position).getDate()));

		holder.location_tx.setText(list.get(position).getVenue());
		holder.open_places_tx.setText(Integer.parseInt(list.get(position).getTeam_list().get(0).getOpen_positions())
				+ Integer.parseInt(list.get(position).getTeam_list().get(1).getOpen_positions()) + " Places Open");
		GameModel mdl = SportsUtilities.getGameBeanBySportID(list.get(position).getSportsId());

		holder.game_img.setImageResource(mdl.getGame_image());

		return convertView;
	}

	private String getParsedDate(String date1) {

		Date date;

		try {
			date = fmt.parse(date1);
			return fmtOut.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}

	public Bitmap StringToBitMap(String encodedString) {

		if (encodedString != null) {
			if (encodedString.length() > 0) {
				try {
					byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);

					Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
					// createScaledBitmap(bitmap, 50, 50, null)

					return bitmap;

				} catch (Exception e) {
					e.getMessage();

				}
			}
		}
		System.out.println("returning1 null");
		return null;
	}

	public static class ViewHolder {
		TextView chat_count, host_name, game_versers_tx, date_time_tx, location_tx, open_places_tx;

		ImageView host_img, game_img;

	}
}
