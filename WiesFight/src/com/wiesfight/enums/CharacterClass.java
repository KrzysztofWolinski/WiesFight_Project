package com.wiesfight.enums;

public enum CharacterClass {
	INFORMATYK(20),
	KLOSZARD(5),
	KIBIC(15),
	MOHER(2),
	BLACHARA(8);
	
	private final int attackPower;
	
	private CharacterClass(int attackPower) {
		this.attackPower = attackPower;
	}
	
	public int getAttackPower() {
		return this.attackPower;
	}
}
