package com.skylka.ensieg.model;

import java.util.ArrayList;

public class EventModel {
	
	public String eventId,venue,date;
//	private String time;
	public String geolocation;
	public String sportsId;
	public String typeofEvent;
	public String userIdOfhost;
	public String nameofhost;
	public String host_photo;
	public String commentcount;
	public ArrayList<TeamModel> team_list;

	public String getCommentcount() {
		return commentcount;
	}

	public void setCommentcount(String commentcount) {
		this.commentcount = commentcount;
	}

	public ArrayList<TeamModel> getTeam_list() {
		return team_list;
	}

	public void setTeam_list(ArrayList<TeamModel> team_list) {
		this.team_list = team_list;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getGeolocation() {
		return geolocation;
	}

	public void setGeolocation(String geolocation) {
		this.geolocation = geolocation;
	}

	public String getSportsId() {
		return sportsId;
	}

	public void setSportsId(String sportsId) {
		this.sportsId = sportsId;
	}

	public String getTypeofEvent() {
		return typeofEvent;
	}

	public void setTypeofEvent(String typeofEvent) {
		this.typeofEvent = typeofEvent;
	}

	public String getUserIdOfhost() {
		return userIdOfhost;
	}

	public void setUserIdOfhost(String userIdOfhost) {
		this.userIdOfhost = userIdOfhost;
	}

	public String getNameofhost() {
		return nameofhost;
	}

	public void setNameofhost(String nameofhost) {
		this.nameofhost = nameofhost;
	}

	public String getHost_photo() {
		return host_photo;
	}

	public void setHost_photo(String host_photo) {
		this.host_photo = host_photo;
	}

	

//	public String getTime() {
//		return time;
//	}
//
//	public void setTime(String time) {
//		this.time = time;
//	}
	

}
