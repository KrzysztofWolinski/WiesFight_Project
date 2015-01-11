package com.wiesfight.figth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wiesfight.enums.PlayerActions;

public class PlayerAction {

    private PlayerActions actionType;
    private double damage;
    private boolean isCriticalAttack;

    public PlayerAction() {

    }

    public PlayerAction(PlayerActions type) {
        this.actionType = type;
    }

    public PlayerActions getActionType() {
        return actionType;
    }

    public void setActionType(PlayerActions actionType) {
        this.actionType = actionType;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setIsCriticalAttack(boolean isCriticalAttack) {
        this.isCriticalAttack = isCriticalAttack;
    }

    public boolean isCriticalAttack() {
        return this.isCriticalAttack;
    }
    
    public String toString() {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(this);
    }
}
