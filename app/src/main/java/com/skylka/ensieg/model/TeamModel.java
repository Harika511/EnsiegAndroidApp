package com.skylka.ensieg.model;

public class TeamModel {

	public String teamId, teamName, maxplayer, open_positions;

	public String getMaxplayer() {
		return maxplayer;
	}

	public String getOpen_positions() {
		return open_positions;

	}

	public void setMaxplayer(String maxplayer) {
		this.maxplayer = maxplayer;
	}

	public void setOpen_positions(String open_positions) {
		this.open_positions = open_positions;

	}

	public String getTeamId() {
		return teamId;

	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;

	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;

	}

}
