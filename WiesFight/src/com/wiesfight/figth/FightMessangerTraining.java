package com.wiesfight.figth;

import com.wiesfight.objects.IFighter;

public class FightMessangerTraining implements IFightMessanger {

    private Fight callback;
    private IFighter trainingFighter;

    public FightMessangerTraining(Fight callback, IFighter trainingFighter) {
        this.callback = callback;
        this.trainingFighter = trainingFighter;
    }

    public void sendData(PlayerAction actions) {
        // TODO uzupełnić o przesyłanie wszystkiego co potrzebne, nie tylko siły ataku

        trainingFighter.decreaseHealth(actions.getAttackStrength());

        PlayerAction response = new PlayerAction();

        if (trainingFighter.getHealth() >= 0.0) {
            response.setAttackStrength(trainingFighter.getAttackStrength().getAttackStrength());
        }

        response.setHealth(trainingFighter.getHealth());

        callback.receivePlayerActions(response);
    }
}
