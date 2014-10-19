package com.wiesfight.datamodels;

import com.wiesfight.enums.*;

public class UserModel {
	private String username;
	private int coins;
	private CharacterClass userClass;
	
	public UserModel(String username, CharacterClass userClass) {
		this.username = username;
		this.userClass = userClass;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public CharacterClass getCharacterClass() {
		return this.userClass;
	}
	
	public void setCoins(int value) {
		this.coins = value;
	}
	
	public int getCoins() {
		return this.coins;
	}
}