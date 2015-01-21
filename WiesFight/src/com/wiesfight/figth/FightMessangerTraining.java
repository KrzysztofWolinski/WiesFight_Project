package com.wiesfight.figth;

import android.os.CountDownTimer;

import com.wiesfight.enums.PlayerActions;
import com.wiesfight.objects.IFighter;

public class FightMessangerTraining implements IFightMessanger {

    private Fight callback;
    private IFighter trainingFighter;
    private PlayerAction currentAction;

    public FightMessangerTraining(Fight callback, IFighter trainingFighter) {
        this.callback = callback;
        this.trainingFighter = trainingFighter;
    }

    public void sendData(PlayerAction actions) {
    	if(actions.getActionType() != PlayerActions.ATTACK) {
    		return;
    	}
    	
        trainingFighter.decreaseHealth((int) actions.getDamage());

        PlayerAction response = new PlayerAction(PlayerActions.ATTACK);

        if (trainingFighter.getHealth() >= 0.0) {
            response.setDamage(trainingFighter.getAttackStrength().getDamage());
        }

        this.currentAction = response;

        callback.applyCurrentAction();
    }
}
