package main.com.wiesfight.objects;

import main.com.wiesfight.dto.User;
import main.com.wiesfight.dto.enums.CharacterClass;

public class TrainingOpponent implements IFighter {
	private User user;
	private int health;
	private int maxHealth;
	
	public TrainingOpponent() {
		this.user = new User();
		this.user.setUserClass(CharacterClass.TRENING);
		this.user.setUserName("Rocky Pierdoła");
		this.health = this.user.getUserClass().getHealthPoints();
		this.maxHealth = this.user.getUserClass().getHealthPoints();
	}

	@Override
	public String getUserClass() {
		return this.user.getUserClass().toString();
	}

	@Override
	public int getHealth() {
		return this.health;
	}

	@Override
	public void decreaseHealth(int minus) {
		this.health -= minus;
	}

	@Override
	public int getAttackStrength() {
		return this.user.getUserClass().getAttackPower();
	}

	@Override
	public int getMaxHealth() {
		return this.maxHealth; 
	}

	@Override
	public String getName() {
		return this.user.getUserName();
	}
}
