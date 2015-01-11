package com.wiesfight.figth;

public interface Animator {

    public void animatePlayerAttacking(boolean isCriticalStrike);
    public void animatePlayerGettingHit();

    public void animateOpponentAttacking(boolean isCriticalStrike);
    public void animateOpponentGettingHit();
}
