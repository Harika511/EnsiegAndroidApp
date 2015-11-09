package com.skylka.ensieg.fragments;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import com.skylka.ensieg.R;
import com.skylka.ensieg.constants.Ensieg_AppConstants;
import com.skylka.ensieg.database.EnsiegDB;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Ensieg_ProfileFragment extends Fragment {
	TextView tvEdit;
	LinearLayout layout1;
	String likefb = "", likebm = "", likevb = "", likecrickt = "", likett = "", likebb = "", liketens = "";
	ImageView ivProfile, ivFootball, ivBasketBall, ivVolleyBall, ivTennis, ivBadminton, ivTableTennis, ivCricket;

	public Ensieg_ProfileFragment() {
	}

	RelativeLayout rlFootball, rlVolleyBall, rlBasketball, rlTennis, rlBadminton, rlTableTennis, rlCricket;
	TextView tvName, tvAge, tvNetwork, tvPhoneNumber;
	EnsiegDB database;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.user_profile, container, false);

		return rootView;
	}

	@SuppressLint("NewApi")
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		database = new EnsiegDB(getActivity());

		ivProfile = (ImageView) view.findViewById(R.id.iv_profile_display);
		tvName = (TextView) view.findViewById(R.id.tv_name_profile);
		tvAge = (TextView) view.findViewById(R.id.tv_age_profile);
		tvNetwork = (TextView) view.findViewById(R.id.tv_network_profile);
		tvPhoneNumber = (TextView) view.findViewById(R.id.tv_pNumber_profile);
		rlFootball = (RelativeLayout) view.findViewById(R.id.rlfb);
		rlVolleyBall = (RelativeLayout) view.findViewById(R.id.rlvb);
		rlBasketball = (RelativeLayout) view.findViewById(R.id.rlbb);
		rlTennis = (RelativeLayout) view.findViewById(R.id.rltennis);
		rlTableTennis = (RelativeLayout) view.findViewById(R.id.rltt);
		rlCricket = (RelativeLayout) view.findViewById(R.id.rlcricket);
		ivFootball = (ImageView) view.findViewById(R.id.iv_fb_Profile);
		ivBasketBall = (ImageView) view.findViewById(R.id.iv_bb_Profile);
		ivVolleyBall = (ImageView) view.findViewById(R.id.iv_vb_Profile);
		ivTennis = (ImageView) view.findViewById(R.id.iv_tennis_Profile);
		ivBadminton = (ImageView) view.findViewById(R.id.iv_bm_Profile);
		ivTableTennis = (ImageView) view.findViewById(R.id.iv_tt_Profile);
		ivCricket = (ImageView) view.findViewById(R.id.iv_cricket_Profile);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "MYRIADPRO-REGULAR.ttf");
		tvName.setTypeface(font);
		tvNetwork.setTypeface(font);
		tvAge.setTypeface(font);
		tvPhoneNumber.setTypeface(font);
		// UserProfileDb profile = new UserProfileDb(getActivity());
		database.Open();
		if (database.numberOfRows_inProfile() > 0) {
			ArrayList<String> allProfileData = database.getAllProfileData();
			// UserRegistrationDb registration = new
			// UserRegistrationDb(getActivity());
			ArrayList<String> allRegisters = database.getAllRegisters();

			tvName.setText(allRegisters.get(1).toString() + " " + allRegisters.get(2).toString());
			tvAge.setText(allProfileData.get(2).toString() + "," + allProfileData.get(1).toString());
			tvNetwork.setText(allRegisters.get(3).toString());
			tvPhoneNumber.setText(allRegisters.get(5).toString());
			Log.d("profile db size is ", "profile db size is " + allProfileData.size());
			likefb = allProfileData.get(4).toString();
			likebb = allProfileData.get(5).toString();
			likevb = allProfileData.get(6).toString();
			liketens = allProfileData.get(7).toString();
			likebm = allProfileData.get(8).toString();
			likett = allProfileData.get(9).toString();
			likecrickt = allProfileData.get(10).toString();
			Bitmap old_Profile = StringToBitMap(allProfileData.get(3));
			if (old_Profile != null) {
				ivProfile.setImageBitmap(old_Profile);
			} else {
				ivProfile.setBackgroundResource(R.drawable.default_profile);
			}
			ivProfile.setImageBitmap(Ensieg_AppConstants.profile);
			Log.d("profile data is ", "profile " + allProfileData.get(4).toString());
			// Log.d("login data is ", "login " + allRegisters.size());
			String allSports = allProfileData.get(4).toString();
			// ArrayList<Integer> favSports =
			// Ensieg_CreateProfileActivity.selected_Sports;
			// Log.d("sports length is ", favSports.size() + " size ");
			// setting selected favorite sports background
			if (likefb.equals("yes")) {
				ivFootball.setBackgroundResource(R.drawable.circle);
				rlFootball.setVisibility(View.VISIBLE);
			}
			if (likebb.equals("yes")) {
				ivBasketBall.setBackgroundResource(R.drawable.circle);
				rlBasketball.setVisibility(View.VISIBLE);
			}
			if (likevb.equals("yes")) {
				ivVolleyBall.setBackgroundResource(R.drawable.circle);
				rlVolleyBall.setVisibility(View.VISIBLE);
			}

			if (liketens.equals("yes")) {
				ivTennis.setBackgroundResource(R.drawable.circle);
				rlTennis.setVisibility(View.VISIBLE);
			}

			if (likebm.equals("yes")) {
				ivBadminton.setBackgroundResource(R.drawable.circle);
				rlBadminton.setVisibility(View.VISIBLE);

			}

			if (likett.equals("yes")) {
				ivTableTennis.setBackgroundResource(R.drawable.circle);
				rlTableTennis.setVisibility(View.VISIBLE);
			}

			if (likecrickt.equals("yes")) {
				ivCricket.setBackgroundResource(R.drawable.circle);
				rlCricket.setVisibility(View.VISIBLE);
			}

		}
		database.close();

	}

	/**
	 * It will convert bitmap to string
	 * 
	 * @param bitmap
	 * @return
	 */
	public String BitMapToString(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] b = baos.toByteArray();
		String temp = Base64.encodeToString(b, Base64.DEFAULT);
		return temp;
	}

	/**
	 * It will convert String to Bitmap
	 * 
	 * @param encodedString
	 * @return bitmap (from given string)
	 */
	public Bitmap StringToBitMap(String encodedString) {
		if (encodedString.length() > 0) {
			try {
				byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
				Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
				return bitmap;
			} catch (Exception e) {
				e.getMessage();

			}
		}
		return null;
	}
	// TextView tvEdit;
	// LinearLayout layout1;
	// String likefb = "", likebm = "", likevb = "", likecrickt = "", likett =
	// "", likebb = "", liketens = "";
	// ImageView ivProfile, ivFootball, ivBasketBall, ivVolleyBall, ivTennis,
	// ivBadminton, ivTableTennis, ivCricket;
	//
	// public Ensieg_ProfileFragment() {
	// }
	//
	// RelativeLayout rlFootball, rlVolleyBall, rlBasketball, rlTennis,
	// rlBadminton, rlTableTennis, rlCricket;
	// TextView tvName, tvAge, tvNetwork, tvPhoneNumber;
	//
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	//
	// View rootView = inflater.inflate(R.layout.user_profile, container,
	// false);
	//
	// return rootView;
	// }
	//
	// @SuppressLint("NewApi")
	// @Override
	// public void onViewCreated(View view, Bundle savedInstanceState) {
	// super.onViewCreated(view, savedInstanceState);
	//
	// ivProfile = (ImageView) view.findViewById(R.id.iv_profile_display);
	// tvName = (TextView) view.findViewById(R.id.tv_name_profile);
	// tvAge = (TextView) view.findViewById(R.id.tv_age_profile);
	// tvNetwork = (TextView) view.findViewById(R.id.tv_network_profile);
	// tvPhoneNumber = (TextView) view.findViewById(R.id.tv_pNumber_profile);
	// rlFootball = (RelativeLayout) view.findViewById(R.id.rlfb);
	// rlVolleyBall = (RelativeLayout) view.findViewById(R.id.rlvb);
	// rlBasketball = (RelativeLayout) view.findViewById(R.id.rlbb);
	// rlTennis = (RelativeLayout) view.findViewById(R.id.rltennis);
	// rlTableTennis = (RelativeLayout) view.findViewById(R.id.rltt);
	// rlCricket = (RelativeLayout) view.findViewById(R.id.rlcricket);
	// ivFootball = (ImageView) view.findViewById(R.id.iv_fb_Profile);
	// ivBasketBall = (ImageView) view.findViewById(R.id.iv_bb_Profile);
	// ivVolleyBall = (ImageView) view.findViewById(R.id.iv_vb_Profile);
	// ivTennis = (ImageView) view.findViewById(R.id.iv_tennis_Profile);
	// ivBadminton = (ImageView) view.findViewById(R.id.iv_bm_Profile);
	// ivTableTennis = (ImageView) view.findViewById(R.id.iv_tt_Profile);
	// ivCricket = (ImageView) view.findViewById(R.id.iv_cricket_Profile);
	// Typeface font = Typeface.createFromAsset(getActivity().getAssets(),
	// "MYRIADPRO-REGULAR.ttf");
	// tvName.setTypeface(font);
	// tvNetwork.setTypeface(font);
	// tvAge.setTypeface(font);
	// tvPhoneNumber.setTypeface(font);
	// UserProfileDb profile = new UserProfileDb(getActivity());
	// if (profile.numberOfRows() > 0) {
	// ArrayList<String> allProfileData = profile.getAllProfileData();
	// UserRegistrationDb registration = new UserRegistrationDb(getActivity());
	// ArrayList<String> allRegisters = registration.getAllRegisters();
	//
	// tvName.setText(allRegisters.get(1).toString() + " " +
	// allRegisters.get(2).toString());
	// tvAge.setText(allProfileData.get(2).toString() + "," +
	// allProfileData.get(1).toString());
	// tvNetwork.setText(allRegisters.get(3).toString());
	// tvPhoneNumber.setText(allRegisters.get(5).toString());
	// Log.d("profile db size is ", "profile db size is " +
	// allProfileData.size());
	// likefb = allProfileData.get(4).toString();
	// likebb = allProfileData.get(5).toString();
	// likevb = allProfileData.get(6).toString();
	// liketens = allProfileData.get(7).toString();
	// likebm = allProfileData.get(8).toString();
	// likett = allProfileData.get(9).toString();
	// likecrickt = allProfileData.get(10).toString();
	// Bitmap old_Profile = StringToBitMap(allProfileData.get(3));
	// if (old_Profile != null) {
	// ivProfile.setImageBitmap(old_Profile);
	// } else {
	// ivProfile.setBackgroundResource(R.drawable.profile);
	// }
	// ivProfile.setImageBitmap(Ensieg_AppConstants.profile);
	// Log.d("profile data is ", "profile " + allProfileData.get(4).toString());
	// // Log.d("login data is ", "login " + allRegisters.size());
	// String allSports = allProfileData.get(4).toString();
	// // ArrayList<Integer> favSports =
	// // Ensieg_CreateProfileActivity.selected_Sports;
	// // Log.d("sports length is ", favSports.size() + " size ");
	// // setting selected favorite sports background
	// if (likefb.equals("yes")) {
	// ivFootball.setBackgroundResource(R.drawable.circle);
	// rlFootball.setVisibility(View.VISIBLE);
	// }
	// if (likebb.equals("yes")) {
	// ivBasketBall.setBackgroundResource(R.drawable.circle);
	// rlBasketball.setVisibility(View.VISIBLE);
	// }
	// if (likevb.equals("yes")) {
	// ivVolleyBall.setBackgroundResource(R.drawable.circle);
	// rlVolleyBall.setVisibility(View.VISIBLE);
	// }
	//
	// if (liketens.equals("yes")) {
	// ivTennis.setBackgroundResource(R.drawable.circle);
	// rlTennis.setVisibility(View.VISIBLE);
	// }
	//
	// if (likebm.equals("yes")) {
	// ivBadminton.setBackgroundResource(R.drawable.circle);
	// rlBadminton.setVisibility(View.VISIBLE);
	//
	// }
	//
	// if (likett.equals("yes")) {
	// ivTableTennis.setBackgroundResource(R.drawable.circle);
	// rlTableTennis.setVisibility(View.VISIBLE);
	// }
	//
	// if (likecrickt.equals("yes")) {
	// ivCricket.setBackgroundResource(R.drawable.circle);
	// rlCricket.setVisibility(View.VISIBLE);
	// }
	//
	// }
	//
	// }
	//
	// /**
	// * It will convert bitmap to string
	// *
	// * @param bitmap
	// * @return
	// */
	// public String BitMapToString(Bitmap bitmap) {
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
	// byte[] b = baos.toByteArray();
	// String temp = Base64.encodeToString(b, Base64.DEFAULT);
	// return temp;
	// }
	//
	// /**
	// * It will convert String to Bitmap
	// *
	// * @param encodedString
	// * @return bitmap (from given string)
	// */
	// public Bitmap StringToBitMap(String encodedString) {
	// if (encodedString.length() > 0) {
	// try {
	// byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
	// Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
	// encodeByte.length);
	// return bitmap;
	// } catch (Exception e) {
	// e.getMessage();
	//
	// }
	// }
	// return null;
	// }

}
