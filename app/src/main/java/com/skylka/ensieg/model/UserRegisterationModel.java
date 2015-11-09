package com.skylka.ensieg.model;

public class UserRegisterationModel {
	String uName, lName, network, phoneNumber;
	private String email;
	String password;
	int uid;

	public UserRegisterationModel(String fname, String lname, String usrNetwork, String eMail, String number,
			String pwd) {
		this.uName = fname;
		this.lName=lname;
		this.network=usrNetwork;
		this.email=eMail;
		this.phoneNumber=number;
		this.password=pwd;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uname) {
		this.uName = uname;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
