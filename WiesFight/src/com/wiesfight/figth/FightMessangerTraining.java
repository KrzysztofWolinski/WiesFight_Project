package com.wiesfight.figth;

import com.wiesfight.objects.Fighter;
import com.wiesfight.objects.IFighter;

public class FightMessangerTraining implements IFightMessanger {

    private Fight callback;
    private IFighter trainingFighter;

    public FightMessangerTraining(Fight callback, IFighter trainingFighter) {
        this.callback = callback;
        this.trainingFighter = trainingFighter;
    }

    public void sendData(PlayerActions actions) {
        // TODO uzupełnić o przesyłanie wszystkiego co potrzebne, nie tylko siły ataku

        trainingFighter.decreaseHealth(actions.getAttackStrength());

        PlayerActions response = new PlayerActions();

        if (trainingFighter.getHealth() >= 0.0) {
            response.setAttackStrength(trainingFighter.getAttackStrength());
        }

        response.setHealth(trainingFighter.getHealth());

        callback.receivePlayerActions(response);
    }
}
