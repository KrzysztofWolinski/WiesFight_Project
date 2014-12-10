package main.com.wiesfight.dto;

import java.io.Serializable;
import java.util.Date;

import main.com.wiesfight.dto.enums.CharacterClass;

public class User implements Serializable {
	private static final long serialVersionUID = -7060210544600464481L;
	private String userName;
	private CharacterClass userClass;
	private int userExperience = 0;
	private int userLevel = 1;		// TODO wyliczanie level'a na podstawie experience powinno być bezpieczniejsze
	private int userCoins = 10;
	private int attackItemCount = 1;
	private int defenseItemCount = 1;
	private int miscItemCount = 1;
	private int fights = 0;
	private int wins = 0;
	private Date lastBonusDate = new Date();
	
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
	public int getFights() {
		return fights;
	}
	public int getWins() {
		return wins;
	}
	public void setFights(int fights) {
		this.fights = fights;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public Date getLastBonusDate() {
		return this.lastBonusDate;
	}
	public void setLastBonusDate(Date lastBonusDate) {
		this.lastBonusDate = lastBonusDate;
	}
	public int getAttackItemCount() {
		return attackItemCount;
	}
	public int getDefenseItemCount() {
		return defenseItemCount;
	}
	public int getMiscItemCount() {
		return miscItemCount;
	}
	
	public void setAttackItemCount(int attackItemCount) {
		this.attackItemCount = attackItemCount;
	}
	public void setDefenceItemCount(int defenceItemCount) {
		this.defenseItemCount = defenceItemCount;
	}
	public void setMiscItemCount(int miscItemCount) {
		this.miscItemCount = miscItemCount;
	} 
	
	public void addAttackItemCount() {
		this.attackItemCount++;
	}
	public void addDefenceItemCount() {
		this.defenseItemCount++;
	}
	public void addMiscItemCount() {
		this.miscItemCount++;
	} 
	public void addFight() {
		this.fights++;
	}
	public void addWin() {
		this.wins++;
	}
	public void addBonusCoins() {
		this.userCoins += 5;
	}
}
