package com.skylka.ensieg.model;

import android.text.format.Time;

import com.skylka.ensieg.R;

import java.security.InvalidKeyException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sankarmanoj on 10/26/15.
 */
public class NotificationModel  {
    Calendar EventTime;
    String PersonName;
    String SportName;
    int SportID;
    int SportResource;
    int type;
    String VerbString;

    public String getPersonName() {
        return PersonName;
    }

    public String getSportName() {
        return SportName;
    }

    public int getSportResource() {
        return SportResource;
    }

    public String getEventTimeString()
    {
        SimpleDateFormat format = new SimpleDateFormat("E, dd LL h:m a ");
        return format.format(new Date(EventTime.getTimeInMillis()));
    }

    public String getVerbString() {
        return VerbString;
    }

    public NotificationModel(String PersonName, int SportID, String datetime, int type) throws RuntimeException
    {
        this.PersonName=PersonName;
        this.SportID=SportID;
        this.type = type;
        switch (SportID)
        {
            case 1:
                SportName="Football";
                SportResource= R.drawable.football;
                break;
            case 2:
                SportName="Volleyball";
                SportResource=R.drawable.volleyball;
                break;
            case 3:
                SportName="Badminton";
                SportResource=R.drawable.badminton;
            case 4:
                SportName="Table Tennis";
                SportResource=R.drawable.tabletennis;
                break;
            case 5:
                SportName="Basketball";
                SportResource=R.drawable.basketball;
                break;
            case 6:
                SportName="Tennis";
                SportResource=R.drawable.tennis;
                break;
            case 7:
                SportName="Cricket";
                SportResource=R.drawable.cricket;
                break;
            default :
                throw new RuntimeException("Sport ID is invalid");

        }
        switch(type)
        {
            case 1:
                VerbString="has invited you to join";
                break;
            case 2:
                VerbString="has joined";
                break;
            case 3:
                VerbString="would like to join";

                break;

        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        EventTime = Calendar.getInstance();
        try {
            EventTime.setTime(format.parse(datetime));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Invalid Date Time String");
        }
    }

}
