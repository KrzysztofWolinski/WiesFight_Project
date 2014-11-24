package com.wiesfight.figth;

import com.wiesfight.objects.IFighter;

public class Fight {

	IFighter fighter1, fighter2;
	boolean isFighter1Active, isFightFinished;
	
	public Fight(IFighter fighter1, IFighter fighter2) {
		this.fighter1 = fighter1;
		this.fighter2 = fighter2;
		
		// Randomly decide which fighter should start
		if (((int)((Math.random() * 10) % 2)) == 0) {
			this.isFighter1Active = false;
		} else {
			this.isFighter1Active = true;
		}
		
		this.isFightFinished = false;
	}

	public void attack() {
		if (this.isFightFinished == false) {
			int attackStrength = getActiveFighter().getAttackStrength();
			getPassiveFighter().decreaseHealth(attackStrength);
			
			if ((this.fighter1.getHealth() <= 0) || (this.fighter2.getHealth() <= 0)) {
				this.isFightFinished = true;
			} else {
				this.switchPlayers();
			}
		}
	}
	
	public boolean isFightFinished() {
		return this.isFightFinished;
	}
	
	private IFighter getActiveFighter() {
		return this.isFighter1Active ? this.fighter1 : this.fighter2; 
	}
	
	private IFighter getPassiveFighter() {
		return this.isFighter1Active ?  this.fighter2 : this.fighter1; 
	} 
	
	private void switchPlayers() {
		this.isFighter1Active = !this.isFighter1Active;
	}
	
	public IFighter getWinner() {
		if (this.isFightFinished()) {
			return this.getActiveFighter();
		} else {
			return null;
		}		
	}
}
