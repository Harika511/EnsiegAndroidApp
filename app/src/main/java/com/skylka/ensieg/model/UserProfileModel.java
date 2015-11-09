package com.skylka.ensieg.model;

import java.util.ArrayList;

public class UserProfileModel {

	String gender, age;
	private int selectedSportsCount;
	private String profile;
	// ArrayList<String> selectedSportsList = new ArrayList<String>();
	ArrayList<Integer> selectedSportsId;
	String likefb, likebm, likevb, likecrickt, likett, likebb, liketens;

	public String getLikefb() {
		return likefb;
	}

	public void setLikefb(String likefb) {
		this.likefb = likefb;
	}

	public String getLikebm() {
		return likebm;
	}

	public void setLikebm(String likebm) {
		this.likebm = likebm;
	}

	public String getLikevb() {
		return likevb;
	}

	public void setLikevb(String likevb) {
		this.likevb = likevb;
	}

	public String getLikecrickt() {
		return likecrickt;
	}

	public void setLikecrickt(String likecrickt) {
		this.likecrickt = likecrickt;
	}

	public String getLikett() {
		return likett;
	}

	public void setLikett(String likett) {
		this.likett = likett;
	}

	public String getLikebb() {
		return likebb;
	}

	public void setLikebb(String likebb) {
		this.likebb = likebb;
	}

	public String getLiketens() {
		return liketens;
	}

	public void setLiketens(String liketens) {
		this.liketens = liketens;
	}

	public UserProfileModel(String uGender, String uAge, int noOfSports_count, String uProfile,
			ArrayList<Integer> uselectedSportsId, String likefb, String likebb, String likevb, String liketens,
			String likebm, String likett, String likecrickt) {
		this.gender = uGender;
		this.age = uAge;
		this.selectedSportsCount=noOfSports_count;
		this.profile=uProfile;
		this.selectedSportsId = uselectedSportsId;
		this.likefb = likefb;
		this.likebb = likebb;
		this.likevb = likevb;
		this.likecrickt = likecrickt;
		this.likebm = likebm;
		this.likett = likett;
		this.liketens = liketens;
		// this.selectedSportsList = uselectedSportsList;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	// public String getSelectedSportsCount() {
	// return selectedSportsCount;
	// }
	//
	// public void setSelectedSportsCount(String selectedSportsCount) {
	// this.selectedSportsCount = selectedSportsCount;
	// }

	// public Bitmap getProfile() {
	// return profile;
	// }
	//
	// public void setProfile(Bitmap profile) {
	// this.profile = profile;
	// }

	// public ArrayList<String> getSelectedSportsList() {
	// return selectedSportsList;
	// }
	//
	// public void setSelectedSportsList(ArrayList<String> selectedSportsList) {
	// this.selectedSportsList = selectedSportsList;
	// }

	public ArrayList<Integer> getSelectedSportsId() {
		return selectedSportsId;
	}

	public void setSelectedSportsId(ArrayList<Integer> selectedSportsId) {
		this.selectedSportsId = selectedSportsId;
	}

	public int getSelectedSportsCount() {
		return selectedSportsCount;
	}

	public void setSelectedSportsCount(int selectedSportsCount) {
		this.selectedSportsCount = selectedSportsCount;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
}
