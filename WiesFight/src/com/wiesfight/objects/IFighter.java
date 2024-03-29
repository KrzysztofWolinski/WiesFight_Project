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
	
	public boolean useAttackItem(boolean removeDB);
	public boolean useDefenseItem(boolean removeDB);
	public boolean useMiscItem(boolean removeDB);
	
	public int getAttackItemCount();
	public int getDefenseItemCount();
	public int getMiscItemCount();
	
	public int getAttackItemDuration();
	public int getDefenseItemDuration();
	public int getMiscItemDuration();

    public String getActiveImageName();

	public void addExperience(int xp);
	public void addFight();
	public void addWin();
	public int getWinsPercent();
	public int getLevel();

    public void endTurn();
}
