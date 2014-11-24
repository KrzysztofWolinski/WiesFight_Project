package com.wiesfight.objects;

import main.com.wiesfight.dto.enums.CharacterClass;

public interface IFighter {
	CharacterClass getUserClass();
	int getHealth();
	int getAttackStrength();
	int getMaxHealth();
	void decreaseHealth(int minus);
	String getName();
	
	public void useAttackItem();
	public void useDefenseItem();
	public void useMiscItem();
}
