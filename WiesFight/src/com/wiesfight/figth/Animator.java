package com.wiesfight.figth;

public interface Animator {

    public void animatePlayerCriticalStrike(boolean isCriticalStrike);
    public void animatePlayerAttacking();
    public void animatePlayerGettingHit();

    public void animateOpponentCriticalStrike(boolean isCriticalStrike);
    public void animateOpponentAttacking();
    public void animateOpponentGettingHit();
}
