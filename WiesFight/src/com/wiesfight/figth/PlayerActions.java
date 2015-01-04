package com.wiesfight.figth;

public class PlayerActions {

    private int attackStrength;
    private double health;
    private boolean isCriticalAttack;

    public int getAttackStrength() {
        return attackStrength;
    }

    public void setAttackStrength(int attackStrength) {
        this.attackStrength = attackStrength;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setIsCriticalAttack(boolean isCriticalAttack) {
        this.isCriticalAttack = isCriticalAttack;
    }

    public boolean isCriticalAttack() {
        return this.isCriticalAttack;
    }

    // TODO dodać potrzebne pola + gettery i settery
}
