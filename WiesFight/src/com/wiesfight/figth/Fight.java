package com.wiesfight.figth;

import android.os.CountDownTimer;

import com.wiesfight.activities.FightActivity;
import com.wiesfight.objects.IFighter;
import com.wiesfight.objects.TrainingOpponent;

public class Fight {

	IFighter player, opponent;
	boolean isFighter1Active, isFightFinished;
    IFightMessanger fightMessanger;
    Animator callback;

	public Fight(IFighter player, IFighter opponent, FightActivity callback) {
		this.player = player;
		this.opponent = opponent;
		this.callback = callback;

		this.isFightFinished = false;

        if (opponent.getClass().equals(TrainingOpponent.class)) {
            fightMessanger = new FightMessangerTraining(this, opponent);
        } else {
            fightMessanger = new FightMessanger(this);
        }

        // Randomly decide which fighter should start
        if (((int)((Math.random() * 10) % 2)) == 0) {
            this.isFighter1Active = false;
            this.fightMessanger.sendData(new PlayerAction());
        } else {
            this.isFighter1Active = true;
        }
	}

	public void attack() {
		if ((this.isFightFinished == false) && (isFighter1Active == true)) {

            PlayerAction actions = getActiveFighter().getAttackStrength();

            // TODO wyliczyć faktyczne obrażenia

            callback.animatePlayerCriticalStrike(actions.isCriticalAttack());
            callback.animatePlayerAttacking();

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

	public IFighter getWinner(boolean opponentLeft) {
		if(opponentLeft) {
			return this.player;
		}
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

    protected void applyOpponentAction(PlayerAction action) {
        // TODO  (refactor)

        new CountDownTimer(550, 550) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                callback.animateOpponentGettingHit();

                new CountDownTimer(550, 550) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        callback.animateOpponentAttacking();
                        new CountDownTimer(550, 550) {
                            public void onTick(long millisUntilFinished) {
                            }

                            public void onFinish() {
                                callback.animatePlayerGettingHit();
                                activatePlayer();
                            }
                        }.start();
                    }
                }.start();
            }
        }.start();

        opponent.setHealth((int) action.getHealth());
        player.decreaseHealth(action.getAttackStrength());
    }

    public void applyCurrentAction() {

    }
}
