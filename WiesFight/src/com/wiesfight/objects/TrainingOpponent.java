package com.wiesfight.objects;

import com.wiesfight.figth.PlayerAction;

import main.com.wiesfight.dto.User;
import main.com.wiesfight.dto.enums.CharacterClass;

public class TrainingOpponent implements IFighter {
	private User user;
	private double health;
	private double maxHealth;
	
	public TrainingOpponent() {
		this.user = new User();
		this.user.setUserClass(CharacterClass.TRENING);
		this.user.setUserName("Rocky Pierdoła");
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
	public void useAttackItem() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void useDefenseItem() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void useMiscItem() {
		// TODO Auto-generated method stub
		
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
    public void endTurn() {
    }
}
