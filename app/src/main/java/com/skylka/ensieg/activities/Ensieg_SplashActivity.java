package com.skylka.ensieg.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.skylka.ensieg.R;
import com.skylka.ensieg.constants.Ensieg_AppConstants;
import com.skylka.ensieg.database.EnsiegDB;

public class Ensieg_SplashActivity extends Activity implements ResultCallback<LocationSettingsResult> {

	String regId;
	String deviceid = null;
	private static int SPLASH_TIME_OUT = 2000;
	LocationRequest mLocationRequest;
	LocationSettingsRequest mLocationSettingsRequest;
	LocationListener mLocationListener;
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
	private static final String TAG = "ENSIEG";
	// Google client to interact with Google API
	GoogleApiClient mGoogleApiClient;
	LocationManager locationManager;
	Location mLastLocation;
	EnsiegDB database;
	int loginRows_count;
	boolean firstrun_status;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		database = new EnsiegDB(this);
		SharedPreferences pref = getSharedPreferences("Ensieg_sharedpreferences", Context.MODE_PRIVATE);
		firstrun_status = pref.getBoolean("FirstRun_status", false);

		/* This is for logo roatation animation */
		ImageView app_logo = (ImageView) findViewById(R.id.app_logo);
		// SharedPreferences pref =
		// getSharedPreferences("Ensieg_sharedpreferences",
		// Context.MODE_PRIVATE);
		// firstrun_status = pref.getBoolean("FirstRun_status", false);
		// Animation myRotation =
		// AnimationUtils.loadAnimation(getApplicationContext(),
		// R.anim.rotate_animation);
		// app_logo.startAnimation(myRotation);

		// RotateAnimation rotate = new RotateAnimation(0, 360,
		// Animation.RELATIVE_TO_SELF, 0.5f,
		// Animation.RELATIVE_TO_SELF, 0.5f);
		// //
		// // ImageView image = findViewById(my image);
		//
		// app_logo.setAnimation(rotate);
		// rotate.start();
		// rotate.setRepeatCount(Animation.INFINITE);
		// rotate.setDuration(Animation.ABSOLUTE);

		/* This is for Location Boundary verification */

		database.Open();
		loginRows_count = database.numberOfRows_InLogin();
		if (loginRows_count <= 0) {
			database.close();
			if (checkPlayServices()) {
				// Building the GoogleApi client
				buildGoogleApiClient();
				buildLocationSettingsRequest();
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("Please Install Play Services ");
				builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

					}

				});
				builder.create().show();
			}

			try {
				// Thread.sleep(3000);
				mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
				if (mLastLocation != null)
					System.out.println("MY location is " + mLastLocation.getLatitude());
				else {
					System.out.println("my location is null");
				}
				mLocationListener = new LocationListener() {

					@Override
					public void onLocationChanged(Location location) {
						mLastLocation = location;
						System.out.println("In Location change");
					}

					@Override
					public void onStatusChanged(String provider, int status, Bundle extras) {
					}

					@Override
					public void onProviderEnabled(String provider) {
					}

					@Override
					public void onProviderDisabled(String provider) {

					}

				};
			} catch (Exception e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			if (loginRows_count > 0) {

				if (database.numberOfRows_inProfile() > 0) {
					// logoLayout.setVisibility(View.GONE);
					/*
					 * Intent home = new Intent(Ensieg_SplashActivity.this,
					 * Ensieg_HomeActivity.class); startActivity(home);
					 * finish();
					 */

					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {

							Intent i = new Intent(Ensieg_SplashActivity.this, Ensieg_HomeActivity.class);
							startActivity(i);

							// close this activity
							finish();
						}
					}, SPLASH_TIME_OUT);
				}

			}
			database.close();

		}
		// This is for push notificaitons */
		// regId = registerGCM(Ensieg_SplashActivity.this);

		regId = registerGCM(Ensieg_SplashActivity.this);
		System.out.println("RegID: " + regId);
		deviceid =

		getDeviceId(Ensieg_SplashActivity.this);
		deviceid = getDeviceId(Ensieg_SplashActivity.this);
		// try {
		// Thread.sleep(3000);
		// Intent home = new Intent(Ensieg_SplashActivity.this,
		// Ensieg_HomeActivity.class);
		// startActivity(home);
		// } catch (InterruptedException e) { // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		

	}
	

	
	public String registerGCM(Context context) {
		/*String TAG = "GCM Already register";
		String SENDER_ID = Ensieg_AppConstants.SenderID;

		GCMRegistrar.checkDevice(context);
		GCMRegistrar.checkManifest(context);

		String gcmRegId = GCMRegistrar.getRegistrationId(context);
		System.out.println("GCM Reg id is ======>" + gcmRegId);
		// Toast.makeText(getApplicationContext(), gcmRegId + " register id ",
		// Toast.LENGTH_LONG).show();
		if (gcmRegId.equals("")) {
			GCMRegistrar.register(context, SENDER_ID);
			System.out.println("GCM Reg id is ======>blank");
			String gcmregID = GCMRegistrar.getRegistrationId(context);
			System.out.println("GCM Reg id is ======>" + gcmregID);
			GcmRegisterBean bean = GcmRegisterBean.getGcmBeanInstance();
			bean.setGcmId(gcmregID);
			Log.d("", " gcm id  " + gcmregID.length() + " " + gcmregID);
			return gcmregID;
		} else {
			Log.v(TAG, "Already registered");
		}
		GcmRegisterBean bean = GcmRegisterBean.getGcmBeanInstance();
		bean.setGcmId(gcmRegId);
		Log.d("", " gcm id is " + gcmRegId.length() + " " + gcmRegId);
		return gcmRegId;*/
		return "";
	}

	public static String getDeviceId(Context ctx) {
		TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);

		String tmDevice = tm.getDeviceId();
		String androidId = Secure.getString(ctx.getContentResolver(), Secure.ANDROID_ID);
		String serial = null;
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO)
			serial = Build.SERIAL;

		if (tmDevice != null)
			return "01" + tmDevice;
		if (androidId != null)
			return "02" + androidId;
		if (serial != null)
			return "03" + serial;

		return tmDevice;
	}

	// Checks if the Google Play APIs are available
	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Toast.makeText(getApplicationContext(), "This device is not supported.", Toast.LENGTH_LONG).show();
				finish();
			}
			return false;
		}
		return true;
	}

	// Builds a LocationSettingsRequest that check the match
	protected void buildLocationSettingsRequest() {
		LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
		builder.addLocationRequest(mLocationRequest);
		builder.setAlwaysShow(true);
		mLocationSettingsRequest = builder.build();
	}

	// Created a Google API Client
	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
					@Override
					public void onConnected(Bundle bundle) {
						// Callback for when Connection is Established for the
						// API Client
						checkLocationSettings(); // Check if Location Settings
													// are that which is
													// required
					}

					@Override
					public void onConnectionSuspended(int i) {
					}
				}).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
					@Override
					public void onConnectionFailed(ConnectionResult connectionResult) {
					}
				}).addApi(LocationServices.API).build();
		createLocationRequest();
	}

	protected void createLocationRequest() {
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(120 * 1000); // Interval between updated in
													// milliseconds
		mLocationRequest.setFastestInterval(1 * 1000);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // Use
																				// GPS
	}

	// Checks if the Location Settings Match what we require
	protected void checkLocationSettings() {

		PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
				.checkLocationSettings(mGoogleApiClient, mLocationSettingsRequest);
		result.setResultCallback(this);

	}

	@Override
	public void onResult(LocationSettingsResult locationSettingsResult) {
		final Status status = locationSettingsResult.getStatus();
		switch (status.getStatusCode()) {

		case LocationSettingsStatusCodes.SUCCESS:
			Log.i(TAG, "All location settings are satisfied." + mLastLocation);
			if (mLastLocation == null) {
				mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient); // Gets
				Log.i(TAG, "All location settings are satisfied.11"); // Location
				// CheckButton.setEnabled(true);
				checkLocation();
				// Stops updating Location. Already got what we needed
				// mGoogleApiClient.disconnect(); // Close Connection
			} else {
				checkLocation();
			}
			break;

		case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
			Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to" + "upgrade location settings ");
			try {
				status.startResolutionForResult(this, 0x1); // Show the
															// dialog by
															// calling
															// startResolutionForResult(),
															// and check
															// the
															// result
			} catch (IntentSender.SendIntentException e) {
				Log.i(TAG, "PendingIntent unable to execute request.");
			}
			break;

		case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
			Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog " + "not created.");
			break;
		}
	}

	private void checkLocation() {
		System.out.println("IN check locations");
		float InputLatitude = Float.parseFloat(Ensieg_AppConstants.latitude);
		float InputLongitude = Float.parseFloat(Ensieg_AppConstants.longitude); // Gets

		float[] results = new float[2];
		if (mLastLocation != null) {
			Location.distanceBetween(InputLatitude, InputLongitude, mLastLocation.getLatitude(),
					mLastLocation.getLongitude(), results);
			// Returns distance in meters as an array in results
			final float result = results[0] / (float) 1000; // Converting to
			// kilometers
			System.out.println("REsult in Locations is" + result);
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					if (result > Float.parseFloat(Ensieg_AppConstants.radius))
					// Comparing distance

					{
						// Outside Campus
						startActivity(new Intent(Ensieg_SplashActivity.this, Ensieg_OutOfCampusActivity.class));
						Ensieg_SplashActivity.this.finish();

					} else if (!Ensieg_AppConstants.userPressedBack)

					{

						// UserProfileDb profiledb = new
						// UserProfileDb(getApplicationContext());
						// UserLoginDb login = new
						// UserLoginDb(getApplicationContext());
						database.Open();
						int loginRows = database.numberOfRows_InLogin();
						/*
						 * Toast.makeText(getApplicationContext(),
						 * "login number of rows is " + loginRows,
						 * Toast.LENGTH_LONG).show();
						 */

						if (loginRows > 0) {

							if (database.numberOfRows_inProfile() > 0) {
								// logoLayout.setVisibility(View.GONE);

								Intent home = new Intent(Ensieg_SplashActivity.this, Ensieg_HomeActivity.class);
								startActivity(home);
								finish();
							}

							else {

								Intent profile = new Intent(Ensieg_SplashActivity.this,
										Ensieg_CreateProfileActivity.class);
								startActivity(profile);
								finish();

							}

						} else {

							if (!firstrun_status) {
								startActivity(new Intent(Ensieg_SplashActivity.this, Ensieg_FirstRunExpActivity.class));
								finish();
							} else {
								while (true) {
									ProgressDialog pDialog = new ProgressDialog(Ensieg_SplashActivity.this);
									pDialog.setTitle("Loading please wait");
									pDialog.setCancelable(false);
									pDialog.show();
                                    pDialog.dismiss();
									break;
									/*if (GcmRegisterBean.getGcmBeanInstance().getGcmId().isEmpty()) {
										registerGCM(getApplicationContext());
									} else {
										pDialog.dismiss();
										break;
									}*/
								}
								startActivity(new Intent(Ensieg_SplashActivity.this, Ensieg_WelcomeActivity.class));
								finish();
							}
						}
						database.close();

					}
				}
			}, SPLASH_TIME_OUT);

		} else {
			mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

			checkLocationSettings();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// Check for the integer request code originally supplied to
		// startResolutionForResult().
		case 0x01:
			switch (resultCode) {
			case Activity.RESULT_OK:
				Log.i(TAG, "User agreed to make required location settings changes.");
				// mLastLocation =
				// LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
				Log.i(TAG, "User agreed to make required location settings changes." + mLastLocation);
				checkLocationSettings();
				break;
			case Activity.RESULT_CANCELED: // User refused to Change
				AlertDialog dialog;
				AlertDialog.Builder builder = new AlertDialog.Builder(this); // Dialog
																				// to
																				// tell
																				// user,
																				// that
																				// Location
																				// is
																				// a
																				// requirement
				builder.setTitle("Location is a requirement");
				builder.setMessage("If you would like to proceed, please enable Location Services");
				builder.setPositiveButton("Enable it", new DialogInterface.OnClickListener() { // User
																								// agreed
																								// to
																								// enable
					@Override
					public void onClick(DialogInterface dialog, int which) {
						checkLocationSettings(); // Reopen Location Settings
													// Dialog
						dialog.dismiss();
					}
				});
				builder.setNegativeButton("I don't want Location!", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						android.os.Process.killProcess(android.os.Process.myPid()); // Kill
																					// application
																					// if
																					// user
																					// declines

					}
				});
				dialog = builder.create();
				dialog.show();

				break;
			}
			break;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (loginRows_count <= 0) {
			mGoogleApiClient.connect();
		}
	}

	/*
	 * @Override protected void onResume() { // TODO Auto-generated method stub
	 * super.onResume(); checkLocationSettings(); }
	 */
	@Override
	protected void onStop() {
		super.onStop();
		if (loginRows_count <= 0) {
			if (mGoogleApiClient.isConnected()) {
				mGoogleApiClient.disconnect();
			}
		}
	}
	
}
