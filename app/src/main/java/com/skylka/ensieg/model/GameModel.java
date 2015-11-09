package com.skylka.ensieg.model;

import java.io.Serializable;

public class GameModel implements Serializable {
	public String game_name;
	public int game_image;
	public int game_small_image;
	public int game_big_image;

	public int getGame_big_image() {
		return game_big_image;
	}

	public void setGame_big_image(int game_big_image) {
		this.game_big_image = game_big_image;
	}

	public String getGame_name() {
		return game_name;
	}

	public void setGame_name(String game_name) {
		this.game_name = game_name;
	}

	public int getGame_image() {
		return game_image;
	}

	public void setGame_image(int game_image) {
		this.game_image = game_image;
	}

	public int getGame_small_image() {
		return game_small_image;
	}

	public void setGame_small_image(int game_small_image) {
		this.game_small_image = game_small_image;
	}

	public int getSportID() {
		return sportID;
	}

	public void setSportID(int sportID) {
		this.sportID = sportID;
	}

	public int sportID;

}
