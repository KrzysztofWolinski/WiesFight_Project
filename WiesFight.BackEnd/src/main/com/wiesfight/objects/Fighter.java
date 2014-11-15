package main.com.wiesfight.objects;

import main.com.wiesfight.dto.User;

public class Fighter implements IFighter {
	private User user;
	private int health;
	
	public Fighter(User u) {
		this.user = u;
		this.health = this.user.getUserClass().getHealthPoints();
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
	public int decreaseAndGetHealth(int minus) {
		return this.health - minus;
	}
}
