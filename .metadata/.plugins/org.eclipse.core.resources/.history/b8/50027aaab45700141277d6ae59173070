package com.wiesfight.dataaccesslayer;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Users")
public class User extends ParseObject {
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
	
	public void setInstallation(String value) {
		put("Installation", value);
	}
	
	public void add() {
		this.saveInBackground();
	}
}
