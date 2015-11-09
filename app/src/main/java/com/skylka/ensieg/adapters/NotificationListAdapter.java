package com.skylka.ensieg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skylka.ensieg.R;
import com.skylka.ensieg.model.NotificationModel;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by sankarmanoj on 10/26/15.
 */
public class NotificationListAdapter extends ArrayAdapter<NotificationModel> {
    Context mContext;

    public NotificationListAdapter(Context context, int resource, List<NotificationModel> objects) {
        super(context, resource,objects);
        mContext=context;
    }

    public class ViewHolder {
        ImageView ProfileImageView, SportImageView;
        TextView NameTextView, VerbTextView,TimeTextView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView==null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            rowView=inflater.inflate(R.layout.listview_notification_item,null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.NameTextView=(TextView)rowView.findViewById(R.id.notificationNameTextView);
            viewHolder.VerbTextView=(TextView)rowView.findViewById(R.id.notificationVerbTextView);
            viewHolder.ProfileImageView=(ImageView)rowView.findViewById(R.id.notificationProfileImageView);
            viewHolder.SportImageView=(ImageView)rowView.findViewById(R.id.notificationSportImageView);
            viewHolder.TimeTextView=(TextView)rowView.findViewById(R.id.notificationTimeTextView);
            rowView.setTag(viewHolder);
        }

        NotificationModel item =  getItem(position);
        ViewHolder viewHolder = (ViewHolder)rowView.getTag();
        viewHolder.NameTextView.setText(item.getPersonName());
        viewHolder.VerbTextView.setText(item.getVerbString());
        viewHolder.SportImageView.setImageDrawable(mContext.getResources().getDrawable(item.getSportResource()));
        viewHolder.TimeTextView.setText(item.getEventTimeString());
        return rowView;
    }

}
