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
            this.opponent.endTurn();
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
        this.callback.showToast("Rozpoczęła się nowa tura");
    }

    protected void applyOpponentAction(final PlayerAction action) {
    	callback.runOnUiThread(new Runnable() {
            @Override
            public void run() {
		        switch (action.getActionType()) {
		            case ATTACK : {
		                callback.animateOpponentAttacking(action.isCriticalAttack());
		                player.decreaseHealth((int) action.getDamage());

                        if (action.getDamage() > 0) {
                            if (action.isCriticalAttack() == true) {
                                callback.showToast("Zadałeś KRYTYCZNE " + action.getDamage() + " obrażeń!!");
                            } else {
                                callback.showToast("Otrzymałeś " + action.getDamage() + " obrażeń!");
                            }
                        } else {
                            callback.showToast("Przeciwnik spudłował!");
                        }

                        checkIfFightIsFinished();
                        activatePlayer();

		                break;
		            }
		            case USED_ATTACK_ITEM: {
		                opponent.useAttackItem(false);
		                callback.animateOpponentUsingItem();
		                break;
		            }
		            case USED_DEFENSE_ITEM: {
		                opponent.useDefenseItem(false);
                        callback.animateOpponentUsingItem();
		                break;
		            }
		            case USED_MISC_ITEM: {
		                opponent.useMiscItem(false);
		                callback.animateOpponentDrinking();
		                break;
		            }
		        }

		        callback.updateBattlefield();
            }
    	});
    }

    public void applyCurrentAction() {
    	callback.runOnUiThread(new Runnable() {
            @Override
            public void run() {
		        if (currentAction != null) {
		            switch (currentAction.getActionType()) {
		                case ATTACK: {
		                    opponent.decreaseHealth((int) currentAction.getDamage());

                            if (currentAction.getDamage() > 0) {
                                if (currentAction.isCriticalAttack() == true) {
                                    callback.showToast("Zadałeś KRYTYCZNE " + currentAction.getDamage() + " obrażeń!!");
                                } else {
                                    callback.showToast("Zadałeś " + currentAction.getDamage() + " obrażeń!");
                                }
                            } else {
                                callback.showToast("Pudło!");
                            }

		                    break;
		                }
		                case USED_ATTACK_ITEM: {
                            callback.animatePlayerUsingItem();
		                    break;
		                }
		                case USED_DEFENSE_ITEM: {
                            callback.animatePlayerUsingItem();
		                    break;
		                }
		                case USED_MISC_ITEM: {

		                    break;
		                }
		            }
		
		            checkIfFightIsFinished();
		            currentAction = null;
		            callback.updateBattlefield();
		        }
            }
    	});
    }

    public void useItem(PlayerActions type) {
        if (isFighter1Active == true) {
            PlayerAction action = new PlayerAction(type);
            fightMessanger.sendData(action);

            if (type.equals(PlayerActions.USED_MISC_ITEM)) {
                callback.animatePlayerDrinking();
            } else {
                callback.animatePlayerUsingItem();
            }
        }
    }

    private void checkIfFightIsFinished() {
        if ((this.player.getHealth() <= 0) || (this.opponent.getHealth() <= 0)) {
            this.isFightFinished = true;
        }
    }

	public void currentUserWon() {
		this.player.addWin();
		int levelDifference = this.opponent.getLevel() - this.player.getLevel();
		this.player.addExperience(this.calculateWinnerExperience(levelDifference));
	}

	public void currentUserLose() {
		int levelDifference = this.player.getLevel() - this.opponent.getLevel();
		this.player.addExperience(this.calculateLoserExperience(levelDifference));
	}
	
	private int calculateWinnerExperience(int lvlDiff) {
		int base = 50 + (lvlDiff * 10);
		if(base < 0)
			base = 0;
		return base + this.opponent.getWinsPercent();
	}
	
	private int calculateLoserExperience(int lvlDiff) {
		int base = 50 - (lvlDiff * 10);
		if(base < 0)
			base = 0;
		else if(base > 50)
			base = 50;
		return base;
	}
}
