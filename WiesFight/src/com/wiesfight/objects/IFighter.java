package com.wiesfight.objects;

import main.com.wiesfight.dto.enums.CharacterClass;

public interface IFighter {
	CharacterClass getUserClass();
	public double getHealth();
	int getAttackStrength();
	public double getMaxHealth();
	void decreaseHealth(int minus);
    public void setHealth(int health);
	String getName();
	
	public void useAttackItem();
	public void useDefenseItem();
	public void useMiscItem();
	
	public int getAttackItemCount();
	public int getDefenseItemCount();
	public int getMiscItemCount();
	
	public int getAttackItemDuration();
	public int getDefenseItemDuration();

    public void endTurn();
}
