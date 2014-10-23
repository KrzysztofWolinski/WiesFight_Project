package com.wiesfight.datamodels;

import com.wiesfight.enums.*;

public class UserModel {
	private final String username;
	private final CharacterClass userClass;
	private int coins;
	private int level;
	private int experience;
	private int attackItemsCount;
	private int defenceItemsCount;
	private int miscItemsCount;
	
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
	
	public void setLevel(int value) {
		this.level = value;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void setExperience(int value) {
		this.experience = value;
	}
	
	public int getExperience() {
		return this.experience;
	}
}
