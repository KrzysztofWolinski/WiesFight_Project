package com.wiesfight.figth;

import com.wiesfight.enums.Items;

public class Bonus {
	private Items item;
	private int duration;
	
	public Bonus(Items item) {
		this.item = item;
		this.duration = item.getDuration();
	}
	
	public double getAttackModificator() {
		if ((item.getBonusType().equals(Bonuses.ATTACKPOWER)) && (this.isActive())) {
			this.duration--;
			return item.getBonus();
		} else {
			return 0.0;
		}
	}
	
	public double getDefenseModificator() {
		if ((item.getBonusType().equals(Bonuses.DEFENCE)) && (this.isActive())) {
			this.duration--;
			return item.getBonus();
		} else {
			return 0.0;
		}
	}
	
	public boolean isActive() {
		if (this.duration > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public double heal() {
		if ((item.getBonusType().equals(Bonuses.HEALTHPOINTS)) && (this.isActive())) {
			this.duration = 0;
			return item.getBonus();
		} else {
			return 0.0;
		}
	}
	
	public int getDuration() {
		return this.duration;
	}
	
}
