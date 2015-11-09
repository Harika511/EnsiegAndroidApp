package com.skylka.ensieg;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.skylka.ensieg.constants.Ensieg_AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GCMIntentService extends IntentService{//GCMBaseIntentService {

	// private static final String TAG = "GCMIntentService";
	static Context mContext;
	static SharedPreferences mSharedPref;
	static Editor mEditor;
	boolean mFlagFromNotification = false;
	static String InvitedName = "";
	ArrayList<String> eventList = new ArrayList<String>();

	public GCMIntentService() {
		super(Ensieg_AppConstants.SenderID);
	}

	/*
	@Override
	public void onRegistered(Context context, String registrationId) {
		// Log.i(TAG, "Device registered: regId = " + registrationId);

		displayMessage(context, "Your device registred with GCM");
		// ServerUtilities.register(context, "Ensieg...!", "", registrationId);
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {
		// Log.i(TAG, "Device unregistered");
		displayMessage(context, getString(R.string.gcm_unregistered));
		ServerUtilities.unregister(context, registrationId);
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		// Log.i(TAG, "Received message");
		String message = intent.getExtras().getString("eventdetails");
		String message1 = intent.getExtras().getString("notificationtype");
		String message2 = intent.getExtras().getString("user");
		// notificationtype=1 1 invite
		// eventdetails=2 eventdetails
		// user=3
		displayMessage(context, message);
		System.out.println("onMessage  " + message + " 2nd " + message1 + " 3rd " + message2);
		// notifies user
		parseEventDetails(message);
		parseUserDEatils(message2);
		generateNotification(context, message);
	}*/

	private void parseUserDEatils(String userDetails) {

		try {
			JSONObject obj = new JSONObject(userDetails);
			InvitedName = obj.getString("firstname");
			// String eventid = obj.getString("eventid");
			// String host = obj.getString("host");
			// JSONArray teamsArray = obj.getJSONArray("teams");
			// String s1 = (String) teamsArray.get(0);
			// String s2 = (String) teamsArray.get(1);
			Log.d("", " invited " + InvitedName);
			// Log.d("", date + " " + eventid + " " + host + " teams data " + s1
			// + " " + s2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onHandleIntent(Intent intent) {

	}

	/*
	@Override
	protected void onDeletedMessages(Context context, int total) {
		// Log.i(TAG, "Received deleted messages notification");
		String message = getString(R.string.gcm_deleted, total);
		displayMessage(context, message);
		// notifies user
		generateNotification(context, message);
	}
	@Override
	public void onError(Context context, String errorId) {
		// Log.i(TAG, "Received error: " + errorId);
		displayMessage(context, getString(R.string.gcm_error, errorId));
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		// log message
		// Log.i(TAG, "Received recoverable error: " + errorId);
		displayMessage(context, getString(R.string.gcm_recoverable_error, errorId));
		return super.onRecoverableError(context, errorId);
	}
*/
	/*
	private static void generateNotification(Context context, String message) {

		mSharedPref = context.getSharedPreferences("", 0);
		mEditor = mSharedPref.edit();

		mEditor.putBoolean("NotificationFlag", true);
		mEditor.putString("NotificationMessage", message);

		mEditor.commit();*/

//		/*
//		 * int icon = R.drawable.ic_launcher; long when =
//		 * System.currentTimeMillis(); String title =
//		 * context.getString(R.string.app_name); NotificationManager
//		 * notificationManager = (NotificationManager) context
//		 * .getSystemService(Context.NOTIFICATION_SERVICE);
//		 *
//		 * Notification notification = new Notification(icon, message, when);
//		 * NotificationCompat.Builder mBuilder = new
//		 * NotificationCompat.Builder(context)
//		 * .setSmallIcon(R.drawable.ic_launcher).setContentTitle(title).
//		 * setContentText(message);
//		 */
/*
		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//		builder.set
		builder.setSmallIcon(R.drawable.ic_launcher);
		String title = context.getString(R.string.app_name);

		Intent notificationIntent = new Intent(context, Ensieg_MatchCenter.class);

		notificationIntent.putExtra("message", message);
		notificationIntent.putExtra("invite", "invite");

		// set intent so it does not start a new activity
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

		PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
		builder.setContentIntent(intent);
		builder.setContentTitle(title);

		// Content text, which appears in smaller text below the title
		builder.setContentText(InvitedName + " is Inviting you to play game ");
		notificationManager.notify(0, builder.build());
//		notificationManager.
//		notificationManager.cancel(0);

	}*/

	/*public void parseEventDetails(String eventDetails) {

		try {
			JSONObject obj = new JSONObject(eventDetails);
			String date = obj.getString("date");
			String invitedEventId = obj.getString("eventid");
			String host = obj.getString("host");
			JSONArray teamsArray = obj.getJSONArray("teams");
			String invitedTeam1 = (String) teamsArray.get(0);
			String invitedTeam2 = (String) teamsArray.get(1);

			Log.d("", date + " " + invitedEventId + " " + host + " teams data  " + invitedTeam1 + " " + invitedTeam2);
			// eventList.add(date);
			eventList.add(invitedEventId);
			// eventList.add(host);
			eventList.add(invitedTeam1);
			eventList.add(invitedTeam2);
			EnsiegDB event = new EnsiegDB(getApplicationContext());
			event.Open();
			if (event.numberOfRows_InEvent_Details() > 0) {
				event.deleteAllEvent_Details();
			}
			event.insertEvent_Details(eventList);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onHandleIntent(Intent intent) {

	}*/
}
