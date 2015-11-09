package com.skylka.ensieg.model;

public class ContactBean {
	private String name;
	private String phoneNo;
	private boolean ensiegContact;
	private boolean addToGroup;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public boolean isEnsiegContact() {
		return ensiegContact;
	}
	public void setEnsiegContact(boolean ensiegContact) {
		this.ensiegContact = ensiegContact;
	}
	public boolean isAddToGroup() {
		return addToGroup;
	}
	public void setAddToGroup(boolean addToGroup) {
		this.addToGroup = addToGroup;
	}
	
	
}
