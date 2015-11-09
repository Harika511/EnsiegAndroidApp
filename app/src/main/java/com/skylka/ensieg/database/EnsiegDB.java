package com.skylka.ensieg.database;

import java.util.ArrayList;
import java.util.List;

import com.skylka.ensieg.model.ContactBean;
import com.skylka.ensieg.model.EventModel;
import com.skylka.ensieg.model.GroupModel;
import com.skylka.ensieg.model.MessageModel;
import com.skylka.ensieg.model.TeamModel;
import com.skylka.ensieg.model.UserLoginModel;
import com.skylka.ensieg.model.UserProfileModel;
import com.skylka.ensieg.model.UserRegisterationModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class EnsiegDB extends SQLiteOpenHelper {

	private static SQLiteDatabase db;
	private Context context;

	// public static final String DATABASE_NAME = "MyDBName.db";
	// static int DATABASE_VERSION = 5;

	public static final String DATABASE_NAME = "EnsiegDatabase.db";
	// <<<<<<< HEAD
	// static int DATABASE_VERSION = 5;

	static int DATABASE_VERSION = 6;
	static String CARDS_TABLE = "Cards_tbl";
	static String UPCOMING_TABLE = "Upcoming_tbl";
	static String HISTORY_TABLE = "History_tbl";
	static String GROUP_TABLE = "Group_tbl";
	static String Ensieg_Contacts = "Ensieg_Contacts_tbl";
	static String Non_Ensieg_Contacts = "Contacts_tbl";
	static String Event_Details = "Event_details";

	public static String SID = "id";
	public static String EVENT_ID = "eventId";
	public static String COMMENTS_COUNT = "comments_count";
	public static String VENUE = "venue";
	public static String DATE = "date";
	// public static String TIME = "time";

	public static String GEO_LOCATION = "geolocation";
	public static String SPORTS_ID = "sports_id";
	public static String TYPE_OF_EVENT = "type_of_event";
	public static String USER_ID_OF_HOST = "userId_host";
	public static String NAME_OF_HOST = "name_host";
	public static String PHOTO = "photo";

	public static String GROUP_ID = "group_id";
	public static String GROUP_NAME = "group_name";
	public static String GROUP_ICON = "group_icon";

	public static String TEAM_1_ID = "team1_id";
	public static String TEAM_1_NAME = "team1_name";
	public static String TEAM_1_MAXPLAYERS = "team1_maxplayers";
	public static String TEAM_2_ID = "team2_id";
	public static String TEAM_2_NAME = "team2_name";
	public static String TEAM_2_MAXPLAYERS = "team2_maxplayers";
	public static String TEAM_1_OPEN_PLACES = "team1_openplaces";
	public static String TEAM_2_OPEN_PLACES = "team2_openplaces";

	public static final String LOGIN_TABLE_NAME = "ULogin";
	public static final String LOGIN_COLUMN_ID = "id";
	public static final String LOGIN_COLUMN_Loginid = "loginId";
	public static final String LOGIN_COLUMN_PASSWORD = "password";
	public static final String LOGIN_COLUMN_SESSIONID = "sessionId";

	public static final String PROFILE_TABLE_NAME = "creat_profile";
	public static final String PROFILE_COLUMN_ID = "id";
	public static final String PROFILE_COLUMN_AGE = "age";
	public static final String PROFILE_COLUMN_GENDER = "gender";
	public static final String PROFILE_COLUMN_BITMAP = "bitmap";
	public static final String PROFILE_COLUMN_FOOTBALL = "football";
	public static final String PROFILE_COLUMN_BASKETBALL = "basketball";
	public static final String PROFILE_COLUMN_VOLLEYBALL = "volleyball";
	public static final String PROFILE_COLUMN_TENNIS = "tennis";
	public static final String PROFILE_COLUMN_BADMINTON = "badminton";
	public static final String PROFILE_COLUMN_TABLETENNIS = "tabletennis";
	public static final String PROFILE_COLUMN_CRICKET = "cricket";

	public static final String REGISTRATION_TABLE_NAME = "registration";
	public static final String REGISTRATION_COLUMN_ID = "id";
	public static final String REGISTRATION_COLUMN_NAME = "name";
	public static final String REGISTRATION_COLUMN_LNAME = "lname";
	public static final String REGISTRATION_COLUMN_NETWORK = "network";
	public static final String REGISTRATION_COLUMN_EMAIL = "email";
	public static final String REGISTRATION_COLUMN_PHONE = "phone";
	public static final String REGISTRATION_COLUMN_PASSWORD = "password";

	public static final String CHAT_TABLE_NAME = "comments";
	public static final String CHAT_COLUMN_ID = "id";
	public static final String CHAT_COLUMN_Commentid = "commentid";
	public static final String CHAT_COLUMN_COMMENT = "comment";
	public static final String CHAT_COLUMN_NAME = "name";
	public static final String CHAT_COLUMN_MINE = "ismine";

	public static final String CONTACTS_COLUMN_ID = "id";
	public static final String CONTACTS_COLUMN_NAME = "name";
	public static final String CONTACTS_COLUMN_NUMBER = "number";
	public static final String CONTACTS_IS_ENSIEG = "isensiegNumber";

	public static final String EVENT_COLUMN_ID = "id";
	public static final String EVENT__EVENT__ID = "eventid";
	public static final String EVENT__TEAM1_ID = "teamid1";
	public static final String EVENT__TEAM2_ID = "teamid2";

	public EnsiegDB(Context cntxt) {
		super(cntxt, DATABASE_NAME, null, DATABASE_VERSION);
		System.out.println("EnsiegDB");
		context = cntxt;
	}

	public void Open() {
		if (db == null || !db.isOpen()) {
			db = this.getWritableDatabase();
		}
	}

	public void close() {
		db.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("in create table");

		final String Creat_cards_tbl = "create table  if not exists " + CARDS_TABLE + " ( " + SID
				+ " INTEGER PRIMARY KEY   AUTOINCREMENT,  " + EVENT_ID + " text , " + COMMENTS_COUNT + " text, " + VENUE
				+ "  text, " + GEO_LOCATION + "  text, " + DATE + "  text, " + SPORTS_ID + "   text, " + TYPE_OF_EVENT
				+ " text, " + NAME_OF_HOST + "   text, " + USER_ID_OF_HOST + "   Text, " + PHOTO + " text, " + TEAM_1_ID
				+ "  text, " + TEAM_1_NAME + "  text, " + TEAM_1_MAXPLAYERS + "  text, " + TEAM_2_ID + "   text, "
				+ TEAM_2_NAME + " text, " + TEAM_2_MAXPLAYERS + "   text , " + TEAM_1_OPEN_PLACES + "   text, "
				+ TEAM_2_OPEN_PLACES + " text " + ")";
		db.execSQL(Creat_cards_tbl);
		final String upcoming_table = "create table  if not exists " + UPCOMING_TABLE + " ( " + SID
				+ " INTEGER PRIMARY KEY   AUTOINCREMENT,  " + EVENT_ID + " text , " + COMMENTS_COUNT + " text, " + VENUE
				+ "  text, " + GEO_LOCATION + "  text, " + DATE + "  text, " + SPORTS_ID + "   text, " + TYPE_OF_EVENT
				+ " text, " + NAME_OF_HOST + "   text, " + USER_ID_OF_HOST + "   Text, " + PHOTO + " text, " + TEAM_1_ID
				+ "  text, " + TEAM_1_NAME + "  text, " + TEAM_1_MAXPLAYERS + "  text, " + TEAM_2_ID + "   text, "
				+ TEAM_2_NAME + " text, " + TEAM_2_MAXPLAYERS + "   text , " + TEAM_1_OPEN_PLACES + "   text, "
				+ TEAM_2_OPEN_PLACES + " text " + ")";
		db.execSQL(upcoming_table);

		final String history_table = "create table  if not exists " + HISTORY_TABLE + " ( " + SID
				+ " INTEGER PRIMARY KEY   AUTOINCREMENT,  " + EVENT_ID + " text , " + COMMENTS_COUNT + " text, " + VENUE
				+ "  text, " + GEO_LOCATION + "  text, " + DATE + "  text, " + SPORTS_ID + "   text, " + TYPE_OF_EVENT
				+ " text, " + NAME_OF_HOST + "   text, " + USER_ID_OF_HOST + "   Text, " + PHOTO + " text, " + TEAM_1_ID
				+ "  text, " + TEAM_1_NAME + "  text, " + TEAM_1_MAXPLAYERS + "  text, " + TEAM_2_ID + "   text, "
				+ TEAM_2_NAME + " text, " + TEAM_2_MAXPLAYERS + "   text , " + TEAM_1_OPEN_PLACES + "   text, "
				+ TEAM_2_OPEN_PLACES + " text " + ")";
		db.execSQL(history_table);

		db.execSQL("create table  if not exists  " + LOGIN_TABLE_NAME + " ( id integer primary key autoincrement, "
				+ LOGIN_COLUMN_Loginid + " text, " + LOGIN_COLUMN_PASSWORD + " text, " + LOGIN_COLUMN_SESSIONID
				+ " text );");
		db.execSQL("create table  if not exists  " + PROFILE_TABLE_NAME + "(id integer primary key autoincrement, "
				+ PROFILE_COLUMN_AGE + " text, " + PROFILE_COLUMN_GENDER + " text, " + PROFILE_COLUMN_BITMAP + " text, "
				+ PROFILE_COLUMN_FOOTBALL + " text, " + PROFILE_COLUMN_BASKETBALL + " text, "
				+ PROFILE_COLUMN_VOLLEYBALL + " text, " + PROFILE_COLUMN_TENNIS + " text, " + PROFILE_COLUMN_BADMINTON
				+ " text, " + PROFILE_COLUMN_TABLETENNIS + " text, " + PROFILE_COLUMN_CRICKET + " text " + " );");
		db.execSQL("create table  if not exists registration "
				+ "(id integer primary key autoincrement, name text,lname text,network text,email text,phone text, password text);");
		db.execSQL("create table  if not exists  " + CHAT_TABLE_NAME + " ( id integer primary key autoincrement, "
				+ CHAT_COLUMN_Commentid + " text, " + CHAT_COLUMN_COMMENT + " text, " + CHAT_COLUMN_NAME + " text, "
				+ CHAT_COLUMN_MINE + " text );");
		db.execSQL("create table  if not exists  " + GROUP_TABLE + " ( group_id integer primary key autoincrement, "
				+ GROUP_NAME + " text, " + GROUP_ICON + " text);");

		db.execSQL("create table  if not exists  " + Ensieg_Contacts + " ( group_id integer primary key autoincrement, "
				+ CONTACTS_COLUMN_NAME + " text, " + CONTACTS_IS_ENSIEG + " text, " + CONTACTS_COLUMN_NUMBER
				+ " text);");
		db.execSQL("create table  if not exists  " + Non_Ensieg_Contacts
				+ " ( group_id integer primary key autoincrement, " + CONTACTS_COLUMN_NAME + " text, "
				+ CONTACTS_IS_ENSIEG + " text, " + CONTACTS_COLUMN_NUMBER + " text);");
		db.execSQL("create table  if not exists  " + Event_Details + " ( group_id integer primary key autoincrement, "
				+ EVENT__EVENT__ID + " text, " + EVENT__TEAM1_ID + " text, " + EVENT__TEAM2_ID + " text);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		context.deleteDatabase(DATABASE_NAME);

		this.onCreate(db);
	}

	public void insertCards(EventModel bean, int state) {

		// state is 1 for cards_table, 2 for upcoming table,3 for history table
		db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put(EVENT_ID, bean.getEventId());
		cv.put(COMMENTS_COUNT, bean.getCommentcount());
		cv.put(DATE, bean.getDate());
		// cv.put(TIME, bean.getTime());
		cv.put(GEO_LOCATION, bean.getGeolocation());
		cv.put(SPORTS_ID, bean.getSportsId());
		cv.put(NAME_OF_HOST, bean.getNameofhost());
		cv.put(PHOTO, bean.getHost_photo());
		cv.put(VENUE, bean.getVenue());
		cv.put(TYPE_OF_EVENT, bean.getTypeofEvent());
		cv.put(USER_ID_OF_HOST, bean.getUserIdOfhost().trim());
		cv.put(USER_ID_OF_HOST, bean.getUserIdOfhost());
		cv.put(TEAM_1_ID, bean.getTeam_list().get(0).getTeamId());
		cv.put(TEAM_1_NAME, bean.getTeam_list().get(0).getTeamName());
		cv.put(TEAM_1_MAXPLAYERS, bean.getTeam_list().get(0).getMaxplayer());
		cv.put(TEAM_2_ID, bean.getTeam_list().get(1).getTeamId());
		cv.put(TEAM_2_NAME, bean.getTeam_list().get(1).getTeamName());
		cv.put(TEAM_2_MAXPLAYERS, bean.getTeam_list().get(1).getMaxplayer());
		cv.put(TEAM_1_OPEN_PLACES, bean.getTeam_list().get(0).getOpen_positions());
		cv.put(TEAM_2_OPEN_PLACES, bean.getTeam_list().get(1).getOpen_positions());
		switch (state) {
		case 1:
			db.insert(CARDS_TABLE, null, cv);
			break;

		case 2:
			db.insert(UPCOMING_TABLE, null, cv);
			break;
		case 3:
			db.insert(HISTORY_TABLE, null, cv);
			break;
		}
		/*
		 * Toast.makeText(context, "cards added successfully.",
		 * Toast.LENGTH_LONG).show();
		 */

	}

	public void deleteCardRecords() {
		SQLiteDatabase db = this.getWritableDatabase();
		int c = db.delete(CARDS_TABLE, "1", null);
		System.out.println(c + " records deleted");
		db.close();
	}
	// public void deleteUpcomingCardRecords() {
	// SQLiteDatabase db = this.getWritableDatabase();
	// int c = db.delete(UPCOMING_TABLE, "1", null);
	// System.out.println(c + " records deleted upcoming");
	// db.close();
	// }
	//
	// public void deleteHistoryCardRecords() {
	// SQLiteDatabase db = this.getWritableDatabase();
	// int c = db.delete(HISTORY_TABLE, "1", null);
	// System.out.println(c + " records deleted history");
	// db.close();
	// }

	public void deleteUpcomingCardRecords() {
		SQLiteDatabase db = this.getWritableDatabase();
		int c = db.delete(UPCOMING_TABLE, "1", null);
		System.out.println(c + " records deleted upcoming");
		db.close();
	}

	public void deleteHistoryCardRecords() {
		SQLiteDatabase db = this.getWritableDatabase();
		int c = db.delete(HISTORY_TABLE, "1", null);
		System.out.println(c + " records deleted history");
		db.close();
	}

	public ArrayList<EventModel> getCardsFromDB(int sportId) {

		String query;
		if (sportId == 0) {
			query = "select * from " + CARDS_TABLE;
		} else {
			query = "select * from " + CARDS_TABLE + " where " + SPORTS_ID + " = " + sportId;
		}

		db = this.getWritableDatabase();
		Cursor c = db.rawQuery(query, null);

		return populateBean(c);

	}

	public ArrayList<EventModel> getUpcomingCardsFromDB() {

		String query;
		query = "select * from " + UPCOMING_TABLE;
		db = this.getWritableDatabase();
		Cursor c = db.rawQuery(query, null);

		return populateBean(c);

	}

	public ArrayList<EventModel> getHistoryCardsFromDB() {

		String query;
		query = "select * from " + HISTORY_TABLE;
		db = this.getWritableDatabase();
		Cursor c = db.rawQuery(query, null);

		return populateBean(c);

	}

	private ArrayList<EventModel> populateBean(Cursor c) {
		ArrayList<EventModel> event_list = new ArrayList<EventModel>();
		if (c != null && c.getCount() > 0) {
			if (c.moveToFirst()) {
				do {
					EventModel bean = new EventModel();
					bean.setEventId(c.getString(c.getColumnIndex(EVENT_ID)));
					bean.setCommentcount(c.getString(c.getColumnIndex(COMMENTS_COUNT)));
					bean.setDate(c.getString(c.getColumnIndex(DATE)));
					// bean.setTime(c.getString(c.getColumnIndex(TIME)));
					bean.setGeolocation(c.getString(c.getColumnIndex(GEO_LOCATION)));
					bean.setHost_photo(c.getString(c.getColumnIndex(PHOTO)));
					bean.setNameofhost(c.getString(c.getColumnIndex(NAME_OF_HOST)));
					bean.setSportsId(c.getString(c.getColumnIndex(SPORTS_ID)));
					String type = c.getString(c.getColumnIndex(TYPE_OF_EVENT));
					bean.setTypeofEvent(type);
					bean.setUserIdOfhost(c.getString(c.getColumnIndex(USER_ID_OF_HOST)));
					bean.setVenue(c.getString(c.getColumnIndex(VENUE)));

					ArrayList<TeamModel> team_list = new ArrayList<>();
					for (int j = 0; j < 2; j++) {
						TeamModel model = new TeamModel();
						if (j == 0) {
							model.setTeamId(c.getString(c.getColumnIndex(TEAM_1_ID)));
							model.setTeamName(c.getString(c.getColumnIndex(TEAM_1_NAME)));
							model.setMaxplayer(c.getString(c.getColumnIndex(TEAM_1_MAXPLAYERS)));
							model.setOpen_positions(c.getString(c.getColumnIndex(TEAM_1_OPEN_PLACES)));
						} else {
							model.setTeamId(c.getString(c.getColumnIndex(TEAM_2_ID)));
							model.setTeamName(c.getString(c.getColumnIndex(TEAM_2_NAME)));
							model.setMaxplayer(c.getString(c.getColumnIndex(TEAM_2_MAXPLAYERS)));
							model.setOpen_positions(c.getString(c.getColumnIndex(TEAM_2_OPEN_PLACES)));
						}
						team_list.add(model);
					}

					bean.setTeam_list(team_list);

					event_list.add(bean);

				} while (c.moveToNext());
			}

		}
		c.close();
		return event_list;

	}

	public ArrayList<EventModel> getCardsFromDBUserName(String hostName, int typeOfInfo) {
		ArrayList<EventModel> event_list = new ArrayList<EventModel>();
		String query = "";
		// if (sportId == 0) {
		// query = "select * from " + CARDS_TABLE;
		// } else {
		try {
			if (typeOfInfo == 0) {
				query = "select * from " + CARDS_TABLE + " where " + NAME_OF_HOST + " = '" + hostName + "'";
			} else if (typeOfInfo == 1) {
				query = "select * from " + CARDS_TABLE + " where " + DATE + " like '" + hostName + " %'";
			}
			// }
			// Log.d("query is", " query is " + query);

			Cursor c = db.rawQuery(query, null);
			db = this.getWritableDatabase();
			if (c != null && c.getCount() > 0) {
				if (c.moveToFirst()) {
					do {
						EventModel bean = new EventModel();
						bean.setEventId(c.getString(c.getColumnIndex(EVENT_ID)));
						bean.setCommentcount(c.getString(c.getColumnIndex(COMMENTS_COUNT)));
						bean.setDate(c.getString(c.getColumnIndex(DATE)));
						// bean.setTime(c.getString(c.getColumnIndex(TIME)));
						bean.setGeolocation(c.getString(c.getColumnIndex(GEO_LOCATION)));
						bean.setHost_photo(c.getString(c.getColumnIndex(PHOTO)));
						bean.setNameofhost(c.getString(c.getColumnIndex(NAME_OF_HOST)));
						bean.setSportsId(c.getString(c.getColumnIndex(SPORTS_ID)));
						String type = c.getString(c.getColumnIndex(TYPE_OF_EVENT));
						bean.setTypeofEvent(type);
						bean.setUserIdOfhost(c.getString(c.getColumnIndex(USER_ID_OF_HOST)));
						bean.setVenue(c.getString(c.getColumnIndex(VENUE)));

						ArrayList<TeamModel> team_list = new ArrayList<>();
						for (int j = 0; j < 2; j++) {
							TeamModel model = new TeamModel();
							if (j == 0) {
								model.setTeamId(c.getString(c.getColumnIndex(TEAM_1_ID)));
								model.setTeamName(c.getString(c.getColumnIndex(TEAM_1_NAME)));
								model.setMaxplayer(c.getString(c.getColumnIndex(TEAM_1_MAXPLAYERS)));
							} else {
								model.setTeamId(c.getString(c.getColumnIndex(TEAM_2_ID)));
								model.setTeamName(c.getString(c.getColumnIndex(TEAM_2_NAME)));
								model.setMaxplayer(c.getString(c.getColumnIndex(TEAM_2_MAXPLAYERS)));
							}
							team_list.add(model);
						}

						bean.setTeam_list(team_list);

						event_list.add(bean);

					} while (c.moveToNext());
				}

			}
			c.close();
			return event_list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return event_list;

	}

	public void deleteAllRows_login() {

		db.execSQL("delete from " + LOGIN_TABLE_NAME);

	}

	public long insertloginData(UserLoginModel usrlogin) {
		SQLiteDatabase db = this.getWritableDatabase();
		onCreate(db);
		ContentValues contentValues = new ContentValues();
		contentValues.put(LOGIN_COLUMN_Loginid, usrlogin.getuName());
		contentValues.put(LOGIN_COLUMN_PASSWORD, usrlogin.getPassword());
		contentValues.put(LOGIN_COLUMN_SESSIONID, usrlogin.getSessionId());

		long insertedId = db.insert(LOGIN_TABLE_NAME, null, contentValues);
		return insertedId;
	}

	public Cursor getLogindata(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + LOGIN_TABLE_NAME + " where id=" + id + "", null);
		return res;
	}

	public int numberOfRows_InLogin() {
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			int numRows = (int) DatabaseUtils.queryNumEntries(db, LOGIN_TABLE_NAME);
			return numRows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean updatelogin(Integer id, UserLoginModel usrlogin) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(LOGIN_COLUMN_Loginid, usrlogin.getuName());
		contentValues.put(LOGIN_COLUMN_PASSWORD, usrlogin.getPassword());
		contentValues.put(LOGIN_COLUMN_SESSIONID, usrlogin.getSessionId());

		db.update(LOGIN_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) });
		return true;
	}

	public Integer deletelogin(Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();

		return db.delete(LOGIN_TABLE_NAME, "id = ? ", new String[] { Integer.toString(id) });
	}

	public ArrayList<String> getLoginData() {
		ArrayList<String> array_list = new ArrayList<String>();

		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + LOGIN_TABLE_NAME, null);
		res.moveToFirst();
		Log.d("", " it is session id " + res.getCount());
		while (res.isAfterLast() == false) {

			array_list.add(res.getInt(res.getColumnIndex("id")) + "");
			array_list.add(res.getString(res.getColumnIndex(LOGIN_COLUMN_Loginid)));
			array_list.add(res.getString(res.getColumnIndex(LOGIN_COLUMN_PASSWORD)));
			array_list.add(res.getString(res.getColumnIndex(LOGIN_COLUMN_SESSIONID)));
			Log.d("", " it is session id " + res.getColumnIndex(LOGIN_COLUMN_SESSIONID));
			res.moveToNext();
		}
		return array_list;
	}

	public void deleteAllProfileRows_profile() {
		db.execSQL("delete from " + PROFILE_TABLE_NAME);

	}

	public long insertProfileData(UserProfileModel usrprofile) {
		SQLiteDatabase db = this.getWritableDatabase();
		onCreate(db);
		ContentValues contentValues = new ContentValues();
		contentValues.put(PROFILE_COLUMN_AGE, usrprofile.getAge());
		contentValues.put(PROFILE_COLUMN_GENDER, usrprofile.getGender());
		contentValues.put(PROFILE_COLUMN_BITMAP, usrprofile.getProfile());
		contentValues.put(PROFILE_COLUMN_FOOTBALL, usrprofile.getLikefb());
		contentValues.put(PROFILE_COLUMN_BASKETBALL, usrprofile.getLikebb());
		contentValues.put(PROFILE_COLUMN_VOLLEYBALL, usrprofile.getLikevb());
		contentValues.put(PROFILE_COLUMN_TENNIS, usrprofile.getLiketens());
		contentValues.put(PROFILE_COLUMN_BADMINTON, usrprofile.getLikebm());
		contentValues.put(PROFILE_COLUMN_TABLETENNIS, usrprofile.getLikett());
		contentValues.put(PROFILE_COLUMN_CRICKET, usrprofile.getLikecrickt());
		long insertedId = db.insert(PROFILE_TABLE_NAME, null, contentValues);
		return insertedId;
	}

	public Cursor geProfiletData(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + PROFILE_TABLE_NAME + " where id=" + id + "", null);
		return res;
	}

	public int numberOfRows_inProfile() {
		SQLiteDatabase db = this.getWritableDatabase();

		try {
			int numRows = (int) DatabaseUtils.queryNumEntries(db, PROFILE_TABLE_NAME);
			return numRows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean updateProfile(Integer id, UserProfileModel usrprofile) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(PROFILE_COLUMN_AGE, usrprofile.getAge());
		contentValues.put(PROFILE_COLUMN_GENDER, usrprofile.getGender());
		contentValues.put(PROFILE_COLUMN_BITMAP, usrprofile.getProfile());
		contentValues.put(PROFILE_COLUMN_FOOTBALL, usrprofile.getLikefb());
		contentValues.put(PROFILE_COLUMN_BASKETBALL, usrprofile.getLikebb());
		contentValues.put(PROFILE_COLUMN_VOLLEYBALL, usrprofile.getLikevb());
		contentValues.put(PROFILE_COLUMN_TENNIS, usrprofile.getLiketens());
		contentValues.put(PROFILE_COLUMN_BADMINTON, usrprofile.getLikebm());
		contentValues.put(PROFILE_COLUMN_TABLETENNIS, usrprofile.getLikett());
		contentValues.put(PROFILE_COLUMN_CRICKET, usrprofile.getLikecrickt());
		db.update(PROFILE_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) });
		return true;
	}

	public Integer deleteProfile(Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();

		return db.delete(PROFILE_TABLE_NAME, "id = ? ", new String[] { Integer.toString(id) });
	}

	public ArrayList<String> getAllProfileData() {
		ArrayList<String> array_list = new ArrayList<String>();
		try {

			// hp = new HashMap();
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor res = db.rawQuery("select * from " + PROFILE_TABLE_NAME, null);
			res.moveToFirst();

			while (res.isAfterLast() == false) {

				array_list.add(res.getInt(res.getColumnIndex("id")) + "");
				array_list.add(res.getString(res.getColumnIndex(PROFILE_COLUMN_AGE)));
				array_list.add(res.getString(res.getColumnIndex(PROFILE_COLUMN_GENDER)));
				array_list.add(res.getString(res.getColumnIndex(PROFILE_COLUMN_BITMAP)));
				array_list.add(res.getString(res.getColumnIndex(PROFILE_COLUMN_FOOTBALL)));
				array_list.add(res.getString(res.getColumnIndex(PROFILE_COLUMN_BASKETBALL)));
				array_list.add(res.getString(res.getColumnIndex(PROFILE_COLUMN_VOLLEYBALL)));
				array_list.add(res.getString(res.getColumnIndex(PROFILE_COLUMN_TENNIS)));
				array_list.add(res.getString(res.getColumnIndex(PROFILE_COLUMN_BADMINTON)));
				array_list.add(res.getString(res.getColumnIndex(PROFILE_COLUMN_TABLETENNIS)));
				array_list.add(res.getString(res.getColumnIndex(PROFILE_COLUMN_CRICKET)));
				res.moveToNext();

			}
			return array_list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array_list;
	}

	public void deleteAllRows_register() {

		db.execSQL("delete from " + REGISTRATION_TABLE_NAME);

	}

	public long insertRegistrationData(UserRegisterationModel addRegister) {
		SQLiteDatabase db = this.getWritableDatabase();
		onCreate(db);
		ContentValues contentValues = new ContentValues();
		contentValues.put(REGISTRATION_COLUMN_NAME, addRegister.getuName());
		contentValues.put(REGISTRATION_COLUMN_LNAME, addRegister.getlName());
		contentValues.put(REGISTRATION_COLUMN_NETWORK, addRegister.getNetwork());
		contentValues.put(REGISTRATION_COLUMN_EMAIL, addRegister.getEmail());
		contentValues.put(REGISTRATION_COLUMN_PHONE, addRegister.getPhoneNumber());
		contentValues.put(REGISTRATION_COLUMN_PASSWORD, addRegister.getPassword());
		long insertedId = db.insert(REGISTRATION_TABLE_NAME, null, contentValues);
		return insertedId;
	}

	public Cursor getDataRegisters(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from registration where id=" + id + "", null);
		return res;
	}

	public int numberOfRows_inRegisters() {
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			int numRows = (int) DatabaseUtils.queryNumEntries(db, REGISTRATION_TABLE_NAME);
			return numRows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean updateRegistration(Integer id, UserRegisterationModel updateRegister) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(REGISTRATION_COLUMN_NAME, updateRegister.getuName());
		contentValues.put(REGISTRATION_COLUMN_LNAME, updateRegister.getlName());
		contentValues.put(REGISTRATION_COLUMN_NETWORK, updateRegister.getNetwork());
		contentValues.put(REGISTRATION_COLUMN_EMAIL, updateRegister.getEmail());
		contentValues.put(REGISTRATION_COLUMN_PHONE, updateRegister.getPhoneNumber());
		contentValues.put(REGISTRATION_COLUMN_PASSWORD, updateRegister.getPassword());
		db.update("registration", contentValues, "id = ? ", new String[] { Integer.toString(id) });
		return true;
	}

	public Integer deleteRegistration(Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();

		return db.delete("registration", "id = ? ", new String[] { Integer.toString(id) });
	}

	public ArrayList<String> getAllRegisters() {
		ArrayList<String> array_list = new ArrayList<String>();

		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from registration", null);
		if (res != null && res.getCount() > 0) {
			res.moveToFirst();

			while (res.isAfterLast() == false) {

				array_list.add(res.getInt(res.getColumnIndex("id")) + "");// -->0
				array_list.add(res.getString(res.getColumnIndex(REGISTRATION_COLUMN_NAME)));// -->1
				array_list.add(res.getString(res.getColumnIndex(REGISTRATION_COLUMN_LNAME)));// -->2
				array_list.add(res.getString(res.getColumnIndex(REGISTRATION_COLUMN_NETWORK)));// -->3
				array_list.add(res.getString(res.getColumnIndex(REGISTRATION_COLUMN_EMAIL)));// -->4
				array_list.add(res.getString(res.getColumnIndex(REGISTRATION_COLUMN_PHONE)));// -->5
				array_list.add(res.getString(res.getColumnIndex(REGISTRATION_COLUMN_PASSWORD)));// -->6
				res.moveToNext();
			}
		}
		return array_list;
	}

	public void deleteAllRows_chat() {

		db.execSQL("delete from " + CHAT_TABLE_NAME);

	}

	public long insertChatData(MessageModel msg) {
		SQLiteDatabase db = this.getWritableDatabase();
		onCreate(db);
		ContentValues contentValues = new ContentValues();
		contentValues.put(CHAT_COLUMN_Commentid, msg.getSender());
		contentValues.put(CHAT_COLUMN_COMMENT, msg.getMessage());
		contentValues.put(CHAT_COLUMN_NAME, msg.getSender());

		long insertedId = db.insert(CHAT_TABLE_NAME, null, contentValues);
		return insertedId;
	}

	public Cursor getChatdata(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + CHAT_TABLE_NAME + " where id=" + id + "", null);
		return res;
	}

	public int numberOfRows_InChat() {
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			int numRows = (int) DatabaseUtils.queryNumEntries(db, CHAT_TABLE_NAME);
			return numRows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean updatechat(Integer id, UserLoginModel usrlogin) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(LOGIN_COLUMN_Loginid, usrlogin.getuName());
		contentValues.put(LOGIN_COLUMN_PASSWORD, usrlogin.getPassword());
		contentValues.put(LOGIN_COLUMN_SESSIONID, usrlogin.getSessionId());

		db.update(LOGIN_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) });
		return true;
	}

	public Integer deletechat(Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();

		return db.delete(LOGIN_TABLE_NAME, "id = ? ", new String[] { Integer.toString(id) });
	}

	public ArrayList<String> getChatData() {
		ArrayList<String> array_list = new ArrayList<String>();

		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + LOGIN_TABLE_NAME, null);
		res.moveToFirst();

		while (res.isAfterLast() == false) {

			array_list.add(res.getInt(res.getColumnIndex("id")) + "");
			array_list.add(res.getString(res.getColumnIndex(LOGIN_COLUMN_Loginid)));
			array_list.add(res.getString(res.getColumnIndex(LOGIN_COLUMN_PASSWORD)));
			array_list.add(res.getString(res.getColumnIndex(LOGIN_COLUMN_SESSIONID)));
			res.moveToNext();
		}
		return array_list;
	}

	/**
	 * It have Group data
	 */
	public void deleteAllRows_group() {

		db.execSQL("delete from " + GROUP_TABLE);

	}

	public long insertGroupData(ArrayList<GroupModel> grouplist) {
		SQLiteDatabase db = this.getWritableDatabase();
		onCreate(db);
		for (int i = 0; i < grouplist.size(); i++) {
			GroupModel group = grouplist.get(i);
			ContentValues contentValues = new ContentValues();
			contentValues.put(GROUP_NAME, group.getName());
			contentValues.put(GROUP_ICON, group.getPhoto());
			long insertedId = db.insert(GROUP_TABLE, null, contentValues);
			return insertedId;
		}
		return 0;
	}

	public Cursor getGroupdata(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + GROUP_TABLE + " where id=" + id + "", null);
		return res;
	}

	public int numberOfRows_InGroup() {
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			int numRows = (int) DatabaseUtils.queryNumEntries(db, GROUP_TABLE);
			return numRows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean updateGroup(Integer id, GroupModel group) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(GROUP_NAME, group.getName());
		contentValues.put(GROUP_ICON, group.getPhoto());

		db.update(GROUP_TABLE, contentValues, "id = ? ", new String[] { Integer.toString(id) });
		return true;
	}

	public Integer deleteGroup(Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();

		return db.delete(GROUP_TABLE, "id = ? ", new String[] { Integer.toString(id) });
	}

	public ArrayList<GroupModel> getGroupData() {
		ArrayList<GroupModel> array_list = new ArrayList<GroupModel>();

		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + GROUP_TABLE, null);
		res.moveToFirst();

		while (res.isAfterLast() == false) {
			GroupModel group = new GroupModel();
			// array_list.add(res.getInt(res.getColumnIndex("id")) + "");

			group.setName(res.getString(res.getColumnIndex(GROUP_NAME)));
			group.setPhoto(res.getString(res.getColumnIndex(GROUP_ICON)));
			array_list.add(group);
			res.moveToNext();
		}
		return array_list;
	}

	/**
	 * It have Contacts data
	 */
	public void deleteAllRows_Contacts() {

		db.execSQL("delete from " + Ensieg_Contacts);

	}

	public long insertContactsData(List<ContactBean> listContact) {
		SQLiteDatabase db = this.getWritableDatabase();
		onCreate(db);

		long insertedId = 0;
		for (int i = 0; i < listContact.size(); i++) {
			ContactBean contact = listContact.get(i);
			ContentValues contentValues = new ContentValues();
			contentValues.put(CONTACTS_COLUMN_NAME, contact.getName());
			// if(contact.isEnsiegContact()){
			contentValues.put(CONTACTS_IS_ENSIEG, "yes");
			// }
			// else{
			// contentValues.put(CONTACTS_IS_ENSIEG, "no");
			// }
			contentValues.put(CONTACTS_COLUMN_NUMBER, contact.getPhoneNo());
			insertedId = db.insert(Ensieg_Contacts, null, contentValues);
		}

		// long insertedId = db.insert(Ensieg_Contacts, null, contentValues);
		return insertedId;
	}

	public Cursor getContactsdata(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + Ensieg_Contacts + " where id=" + id + "", null);
		return res;
	}

	public int numberOfRows_InContacts() {
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			int numRows = (int) DatabaseUtils.queryNumEntries(db, Ensieg_Contacts);
			return numRows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean updateContacts(Integer id, ContactBean contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(CONTACTS_COLUMN_NAME, contact.getName());
		// if (contact.isEnsiegContact()) {

		contentValues.put(CONTACTS_IS_ENSIEG, "yes");

		// else {
		// contentValues.put(CONTACTS_IS_ENSIEG, "no");
		// }
		contentValues.put(CONTACTS_COLUMN_NUMBER, contact.getPhoneNo());

		db.update(Ensieg_Contacts, contentValues, "id = ? ", new String[] { Integer.toString(id) });
		return true;
	}

	public Integer deleteContacts(Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();

		return db.delete(Ensieg_Contacts, "id = ? ", new String[] { Integer.toString(id) });
	}

	public ArrayList<ContactBean> getEnsiegContactsData() {
		ArrayList<ContactBean> array_list = new ArrayList<ContactBean>();

		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + Ensieg_Contacts, null);
		res.moveToFirst();

		while (res.isAfterLast() == false) {
			ContactBean contact = new ContactBean();
			// array_list.add(res.getInt(res.getColumnIndex("id")) + "");

			// if
			// (res.getString(res.getColumnIndex(CONTACTS_IS_ENSIEG)).equals("yes"))
			// {
			contact.setName(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
			contact.setEnsiegContact(true);
			contact.setPhoneNo(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NUMBER)));
			array_list.add(contact);
			// }

			res.moveToNext();
		}
		return array_list;
	}

	/**
	 * It have Non Ensieg Contacts data
	 */
	public void deleteAllRows_NonContacts() {

		db.execSQL("delete from " + Non_Ensieg_Contacts);

	}

	public long insertNonContactsData(List<ContactBean> listContact) {
		SQLiteDatabase db = this.getWritableDatabase();
		onCreate(db);

		long insertedId = 0;
		for (int i = 0; i < listContact.size(); i++) {
			ContactBean contact = listContact.get(i);
			ContentValues contentValues = new ContentValues();
			// Log.d("", " non ensieg are " + contact.getName() + " " +
			// contact.getPhoneNo());
			contentValues.put(CONTACTS_COLUMN_NAME, contact.getName());
			// if(contact.isEnsiegContact()){
			// contentValues.put(CONTACTS_IS_ENSIEG, "yes");
			// }
			// else{
			contentValues.put(CONTACTS_IS_ENSIEG, "no");
			// }
			contentValues.put(CONTACTS_COLUMN_NUMBER, contact.getPhoneNo());
			insertedId = db.insert(Non_Ensieg_Contacts, null, contentValues);
		}

		// long insertedId = db.insert(Ensieg_Contacts, null, contentValues);
		return insertedId;
	}

	public Cursor getNonContactsdata(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + Non_Ensieg_Contacts + " where id=" + id + "", null);
		return res;
	}

	public int numberOfRows_InNonContacts() {
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			int numRows = (int) DatabaseUtils.queryNumEntries(db, Non_Ensieg_Contacts);
			return numRows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean updateNonContacts(Integer id, ContactBean contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(CONTACTS_COLUMN_NAME, contact.getName());
		// if (contact.isEnsiegContact()) {
		//
		// contentValues.put(CONTACTS_IS_ENSIEG, "yes");
		// } else {
		contentValues.put(CONTACTS_IS_ENSIEG, "no");
		// }
		contentValues.put(CONTACTS_COLUMN_NUMBER, contact.getPhoneNo());

		db.update(Non_Ensieg_Contacts, contentValues, "id = ? ", new String[] { Integer.toString(id) });
		return true;
	}

	public Integer deleteNonContacts(Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();

		return db.delete(Non_Ensieg_Contacts, "id = ? ", new String[] { Integer.toString(id) });
	}

	public ArrayList<ContactBean> getEnsiegNonContactsData() {
		ArrayList<ContactBean> array_list = new ArrayList<ContactBean>();

		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + Non_Ensieg_Contacts, null);
		res.moveToFirst();

		while (res.isAfterLast() == false) {
			ContactBean contact = new ContactBean();
			// array_list.add(res.getInt(res.getColumnIndex("id")) + "");

			// if
			// (res.getString(res.getColumnIndex(CONTACTS_IS_ENSIEG)).equals("no"))
			// {
			String name = res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME));
			String number = res.getString(res.getColumnIndex(CONTACTS_COLUMN_NUMBER));
			Log.d("", "retriving " + name + " " + number);
			contact.setName(name);
			contact.setEnsiegContact(false);
			contact.setPhoneNo(number);
			array_list.add(contact);
			// }

			res.moveToNext();
		}
		return array_list;
	}

	/**
	 * It have Event Details
	 */
	public void deleteAllEvent_Details() {

		db.execSQL("delete from " + Event_Details);

	}

	public long insertEvent_Details(List<String> listContact) {
		SQLiteDatabase db = this.getWritableDatabase();
		onCreate(db);

		long insertedId = 0;
		for (int i = 0; i < listContact.size(); i++) {
			ContentValues contentValues = new ContentValues();
			contentValues.put(EVENT__EVENT__ID, listContact.get(0));
			contentValues.put(EVENT__TEAM1_ID, listContact.get(1));
			contentValues.put(EVENT__TEAM2_ID, listContact.get(2));
			insertedId = db.insert(Event_Details, null, contentValues);
		}
		Log.d("", " inserted id " + insertedId);
		return insertedId;
	}

	public Cursor getEvent_Details(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + Event_Details + " where id=" + id + "", null);
		return res;
	}

	public int numberOfRows_InEvent_Details() {
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			int numRows = (int) DatabaseUtils.queryNumEntries(db, Event_Details);
			return numRows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean updateEvent_Details(Integer id, List<String> listContact) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(EVENT__EVENT__ID, listContact.get(0));
		contentValues.put(EVENT__TEAM1_ID, listContact.get(1));
		contentValues.put(EVENT__TEAM2_ID, listContact.get(2));

		db.update(Non_Ensieg_Contacts, contentValues, "id = ? ", new String[] { Integer.toString(id) });
		return true;
	}

	public Integer deleteEvent_Details(Integer id) {
		SQLiteDatabase db = this.getWritableDatabase();

		return db.delete(Non_Ensieg_Contacts, "id = ? ", new String[] { Integer.toString(id) });
	}

	public ArrayList<String> getEnsiegEvent_Details() {
		ArrayList<String> array_list = new ArrayList<String>();

		// hp = new HashMap();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from " + Event_Details, null);
		res.moveToFirst();

		while (res.isAfterLast() == false) {
			// array_list.add(res.getInt(res.getColumnIndex("id")) + "");

			// if
			// (res.getString(res.getColumnIndex(CONTACTS_IS_ENSIEG)).equals("no"))
			// {
			array_list.add(res.getString(res.getColumnIndex(EVENT__EVENT__ID)));
			array_list.add(res.getString(res.getColumnIndex(EVENT__TEAM1_ID)));
			array_list.add(res.getString(res.getColumnIndex(EVENT__TEAM2_ID)));

			// }

			res.moveToNext();
		}
		return array_list;
	}

}
