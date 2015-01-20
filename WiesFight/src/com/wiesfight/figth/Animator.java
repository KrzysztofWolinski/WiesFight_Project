package com.wiesfight.figth;

public interface Animator {

    public void animatePlayerAttacking(PlayerAction action);
    public void animatePlayerGettingHit(PlayerAction action);

    public void animateOpponentAttacking(PlayerAction action);
    public void animateOpponentGettingHit(PlayerAction action);
}
