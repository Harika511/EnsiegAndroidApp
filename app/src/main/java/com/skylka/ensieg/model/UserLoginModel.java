package com.skylka.ensieg.model;

public class UserLoginModel {

	String uName, password;
	private String sessionId;

	public UserLoginModel(String name, String pwd,String sessionId) {
		this.uName = name;
		this.password = pwd;
		this.sessionId = sessionId;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
