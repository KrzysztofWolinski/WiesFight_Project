package main.com.wiesfight.objects;

import main.com.wiesfight.dto.User;
import main.com.wiesfight.dto.enums.CharacterClass;

public class TrainingOpponent implements IFighter {
	private User user;
	private int health;
	
	public TrainingOpponent() {
		this.user = new User();
		this.user.setUserClass(CharacterClass.TRENING);
		this.user.setUserName("Rocky Pierdo�a");
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