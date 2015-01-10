package com.wiesfight.figth;

import com.wiesfight.activities.FightActivity;
import com.wiesfight.objects.IFighter;
import com.wiesfight.objects.TrainingOpponent;

public class Fight {

	IFighter player, opponent;
	boolean isFighter1Active, isFightFinished;
    IFightMessanger fightMessanger;
    FightActivity callback;

	public Fight(IFighter player, IFighter opponent, FightActivity callback) {
		this.player = player;
		this.opponent = opponent;
		this.callback = callback;

		// Randomly decide which fighter should start
		if (((int)((Math.random() * 10) % 2)) == 0) {
			this.isFighter1Active = false;
		} else {
			this.isFighter1Active = true;
		}

		this.isFightFinished = false;

        if (opponent.getClass().equals(TrainingOpponent.class)) {
            fightMessanger = new FightMessangerTraining(this, opponent);
        } else {
            fightMessanger = new FightMessanger(this);
        }
	}

	public void attack() {
		if (this.isFightFinished == false) {

            PlayerActions actions = getActiveFighter().getAttackStrength();
            callback.animatePlayerCriticalAttack(actions.isCriticalAttack());

            fightMessanger.sendData(actions);
            this.player.endTurn();
            deactivatePlayer();

			if ((this.player.getHealth() <= 0) || (this.opponent.getHealth() <= 0)) {
				this.isFightFinished = true;
			}
		}
	}
	
	public boolean isFightFinished() {
		return this.isFightFinished;
	}
	
	private IFighter getActiveFighter() {
		return this.isFighter1Active ? this.player : this.opponent;
	}

	public IFighter getWinner() {
		if (this.isFightFinished()) {
			if (this.player.getHealth() <= 0) {
                return this.opponent;
            } else if (this.opponent.getHealth() <= 0) {
                return this.player;
            }
		}
        return null;
	}

    protected void deactivatePlayer() {
        this.isFighter1Active = false;
    }

    protected void activatePlayer() {
        this.isFighter1Active = true;
    }

    protected void receivePlayerActions(PlayerActions actions) {
        // TODO dodać wykrywanie czy walka ciągle trwa (refactor)

        player.decreaseHealth(actions.getAttackStrength());

        opponent.setHealth((int) actions.getHealth());

        activatePlayer();
    }
}
