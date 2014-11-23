package main.com.wiesfight.objects;

import main.com.wiesfight.dto.User;

public class Fighter implements IFighter {
	private User user;
	private int health;
	private int maxHealth;
	
	public Fighter(User u) {
		this.user = u;
		this.health = this.user.getUserClass().getHealthPoints();
		this.maxHealth = this.health;
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
		return maxHealth;
	}
}
