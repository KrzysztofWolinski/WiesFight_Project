package main.com.wiesfight.dto;

import java.io.Serializable;

import main.com.wiesfight.dto.enums.CharacterClass;

public class User implements Serializable {
	private static final long serialVersionUID = -7060210544600464481L;
	private String userName;
	private CharacterClass userClass;
	private int userExperience;
	private int userLevel;		// TODO wyliczanie level'a na podstawie experience powinno być bezpieczniejsze
	private int userCoins = 10;
	private int attackItemCount = 1;
	private int defenceItemCount = 1;
	private int miscItemCount = 1;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public CharacterClass getUserClass() {
		return userClass;
	}
	public void setUserClass(CharacterClass userClass) {
		this.userClass = userClass;
	}
	public int getUserExperience() {
		return userExperience;
	}
	public void setUserExperience(int userExperience) {
		this.userExperience = userExperience;
	}
	public int getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}
	public int getUserCoins() {
		return userCoins;
	}
	public void setUserCoins(int userCoins) {
		this.userCoins = userCoins;
	}
	public int getAttackItemCount() {
		return attackItemCount;
	}
	public int getDefenceItemCount() {
		return defenceItemCount;
	}
	public int getMiscItemCount() {
		return miscItemCount;
	}
	
	public void setAttackItemCount(int attackItemCount) {
		this.attackItemCount = attackItemCount;
	}
	public void setDefenceItemCount(int defenceItemCount) {
		this.defenceItemCount = defenceItemCount;
	}
	public void setMiscItemCount(int miscItemCount) {
		this.miscItemCount = miscItemCount;
	} 
	
	public void addAttackItemCount() {
		this.attackItemCount++;
	}
	public void addDefenceItemCount() {
		this.defenceItemCount++;
	}
	public void addMiscItemCount() {
		this.miscItemCount++;
	} 
}
