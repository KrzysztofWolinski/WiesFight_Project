package com.wiesfight.objects;

import com.wiesfight.enums.Items;
import com.wiesfight.figth.Bonuses;
import com.wiesfight.figth.PlayerAction;

import main.com.wiesfight.dto.User;
import main.com.wiesfight.dto.enums.CharacterClass;

public class TrainingOpponent implements IFighter {
	private User user;
	private double health;
	private double maxHealth;
	
	public TrainingOpponent(String name) {
		this.user = new User();
		this.user.setUserClass(CharacterClass.TRENING);
		this.user.setUserName(name);
		this.health = this.user.getUserClass().getHealthPoints();
		this.maxHealth = this.user.getUserClass().getHealthPoints();
	}

	@Override
	public CharacterClass getUserClass() {
		return this.user.getUserClass();
	}

	@Override
	public double getHealth() {
		return this.health;
	}

	@Override
	public void decreaseHealth(int minus) {
		this.health -= minus;
	}

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
	public PlayerAction getAttackStrength() {
		PlayerAction actions = new PlayerAction();

        actions.setDamage(this.user.getUserClass().getAttackPower());

        return actions;
	}

    @Override
    public PlayerAction evaluateDamage(PlayerAction action) {
    	double damage = action.getDamage();
    	action.setDamage((double)((int)(damage + 0.5)));
        return action;
    }

    @Override
	public double getMaxHealth() {
		return this.maxHealth; 
	}

	@Override
	public String getName() {
		return this.user.getUserName();
	}

	@Override
	public boolean useAttackItem(boolean removeDB) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean useDefenseItem(boolean removeDB) {
		// TODO Auto-generated method stub
        return true;
	}

	@Override
	public boolean useMiscItem(boolean removeDB) {
		// TODO Auto-generated method stub
        return true;
	}
	
	@Override
	public int getAttackItemCount() {
		return this.user.getAttackItemCount();
	}

	@Override
	public int getDefenseItemCount() {
		return this.user.getDefenseItemCount();
	}

	@Override
	public int getMiscItemCount() {
		return this.user.getMiscItemCount();
	}

	@Override
	public int getAttackItemDuration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDefenseItemDuration() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int getMiscItemDuration() {
		return 0;
	}

    @Override
    public void endTurn() {
    }

	@Override
	public void addFight() {
	}

	@Override
	public void addWin() {
	}
	
	@Override
	public void addExperience(int xp) {
	}

	@Override
	public int getLevel() {
		return 0;
	}

	@Override
	public int getWinsPercent() {
		return 0;
	}

    @Override
    public String getActiveImageName() {
        return "_big_fight";
    }
}
