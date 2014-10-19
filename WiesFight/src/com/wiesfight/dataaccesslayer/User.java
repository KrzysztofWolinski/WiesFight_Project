package com.wiesfight.dataaccesslayer;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Users")
public class User extends ParseObject {
	public User() {
		
	}
	
	public User(String userName, String installation, int userClass) {
		this.setUsername(userName);
		this.setInstallation(installation);
		this.setUserClass(userClass);
		this.setUserLevel(1);
		this.setExperience(0);
		this.setUserCoins(100);
		this.setAttackItemsCount(1);
		this.setDefenceItemsCount(1);
		this.setMiscItemsCount(1);
	}
	
	public void setInstallation(String value) {
		put("Installation", value);
	}
	
	public String getUsername() {
		return getString("Username");
	}
	
	public void setUsername(String value) {
	    put("Username", value);
	}
	
	public int getUserClass() {
		return getInt("Class");
	}
	
	public void setUserClass(int value) {
		put("Class", value);
	}
	
	public int getUserLevel() {
		return getInt("Level");
	}
	
	public void setUserLevel(int value) {
		put("Level", value);
	}
	
	public int getExperience() {
		return getInt("Experience");
	}
	
	public void setExperience(int value) {
		put("Experience", value);
	}
	
	public int getUserCoins() {
		return getInt("Coins");
	}
	
	public void setUserCoins(int value) {
		put("Coins", value);
	}
	
	public int getAttackItemsCount() {
		return getInt("AttackItemsCount");
	}
	
	public void setAttackItemsCount(int value) {
		put("AttackItemsCount", value);
	}
	
	public int getDefenceItemsCount() {
		return getInt("DefenceItemsCount");
	}
	
	public void setDefenceItemsCount(int value) {
		put("DefenceItemsCount", value);
	}
	
	public int getMiscItemsCount() {
		return getInt("MiscItemsCount");
	}
	
	public void setMiscItemsCount(int value) {
		put("MiscItemsCount", value);
	}
	
	public void saveUser() {
		this.saveInBackground();
	}
}
