package com.skylka.ensieg.activities;

/**
 * Created by sankarmanoj on 10/17/15.
 */
public class Player {
	String name;
	Boolean hosting;

	public Player(String name) {
		this.name = name;
		this.hosting = false;
	}

	public Player(String name, Boolean hosting) {
		this.name = name;
		this.hosting = hosting;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
