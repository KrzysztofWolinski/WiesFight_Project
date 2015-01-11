package com.wiesfight.objects;

import com.wiesfight.figth.PlayerAction;

import main.com.wiesfight.dto.enums.CharacterClass;

public interface IFighter {
	CharacterClass getUserClass();

    PlayerAction getAttackStrength();
    PlayerAction evaluateDamage(PlayerAction action);

    public double getHealth();
	public double getMaxHealth();
	void decreaseHealth(int minus);
    public void setHealth(int health);
	String getName();
	
	public boolean useAttackItem();
	public boolean useDefenseItem();
	public boolean useMiscItem();
	
	public int getAttackItemCount();
	public int getDefenseItemCount();
	public int getMiscItemCount();
	
	public int getAttackItemDuration();
	public int getDefenseItemDuration();

    public void endTurn();
}
