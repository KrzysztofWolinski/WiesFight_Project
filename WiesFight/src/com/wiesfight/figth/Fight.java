package com.wiesfight.figth;

import android.os.CountDownTimer;

import com.wiesfight.activities.FightActivity;
import com.wiesfight.enums.PlayerActions;
import com.wiesfight.objects.IFighter;
import com.wiesfight.objects.TrainingOpponent;

import main.com.wiesfight.dto.enums.CharacterClass;

public class Fight {

	private IFighter player, opponent;
	private boolean isFighter1Active, isFightFinished;
    private IFightMessanger fightMessanger;
    private FightActivity callback;
    private PlayerAction currentAction;

	public Fight(IFighter player, IFighter opponent, FightActivity callback, boolean isStarting) {
		this.player = player;
		this.opponent = opponent;
		this.callback = callback;

		this.isFightFinished = false;

        if (opponent.getClass().equals(TrainingOpponent.class)) {
            fightMessanger = new FightMessangerTraining(this, opponent);
        } else {
            fightMessanger = new FightMessanger(this, opponent.getName());
        }

        // Randomly decide which fighter should start
       /* if (((int)((Math.random() * 10) % 2)) == 0) {
            this.isFighter1Active = false;

            PlayerAction initAction = new PlayerAction(PlayerActions.ATTACK);
            initAction.setDamage(0.0);

            this.fightMessanger.sendData(initAction);
        } else {
            this.isFighter1Active = true;
        } */

        if (isStarting == true) {
            this.activatePlayer();
        } else {
            this.deactivatePlayer();
        }
	}

	public void attack() {
		if ((this.isFightFinished == false) && (isFighter1Active == true)) {
            // Wyliczanie obrażeń
            this.currentAction = this.player.getAttackStrength();
            this.currentAction.setActionType(PlayerActions.ATTACK);
            this.currentAction = opponent.evaluateDamage(this.currentAction);

            // Animacja
            callback.animatePlayerAttacking(this.currentAction.isCriticalAttack());

            // Zakończenie tury
            fightMessanger.sendData(this.currentAction);
            this.player.endTurn();
            deactivatePlayer();
		}
	}
	
	public boolean isFightFinished() {
		return this.isFightFinished;
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
        switch (action.getActionType()) {
            case ATTACK : {
                callback.animateOpponentAttacking(action.isCriticalAttack());
                callback.animatePlayerGettingHit();

                player.decreaseHealth((int) action.getDamage());
                break;
            }
            case USED_ATTACK_ITEM: {
                this.opponent.useAttackItem();
                // TODO dodać odpowiednią animację
                break;
            }
            case USED_DEFENSE_ITEM: {
                this.opponent.useDefenseItem();
                // TODO dodać odpowiednią animację
                break;
            }
            case USED_MISC_ITEM: {
                this.opponent.useMiscItem();
                // TODO dodać odpowiednią animację
                break;
            }
        }

        checkIfFightIsFinished();
        activatePlayer();
        this.callback.updateBattlefield();
    }

    public void applyCurrentAction() {
        if (currentAction != null) {
            switch (currentAction.getActionType()) {
                case ATTACK: {
                    opponent.decreaseHealth((int) currentAction.getDamage());
                    callback.animateOpponentGettingHit();
                    break;
                }
                case USED_ATTACK_ITEM: {

                    break;
                }
                case USED_DEFENSE_ITEM: {

                    break;
                }
                case USED_MISC_ITEM: {

                    break;
                }
            }

            checkIfFightIsFinished();
            this.currentAction = null;
            this.callback.updateBattlefield();
        }
    }

    public void useItem(PlayerActions type) {
        PlayerAction action = new PlayerAction(type);
        fightMessanger.sendData(action);

    }

    private void checkIfFightIsFinished() {
        if ((this.player.getHealth() <= 0) || (this.opponent.getHealth() <= 0)) {
            this.isFightFinished = true;
        }
    }
}
