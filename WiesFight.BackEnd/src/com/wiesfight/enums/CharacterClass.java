package com.wiesfight.enums;

public enum CharacterClass {
	INFORMATYK(20, 5, 0.1, 0.9, 0.15, 1.1),
	KLOSZARD(5, 20, 0.1, 0.9, 0.15, 1.1),
	KIBIC(15, 10, 0.1, 0.9, 0.15, 1.1),
	MOHER(2, 10, 0.1, 0.9, 0.15, 1.1),
	BLACHARA(8, 10, 0.1, 0.9, 0.15, 1.1);
	
	// zadawane obra¿enia
	private final int attackPower;
	// punkty ¿ycia
	private final int healthPoints;
	// zmniejszone obra¿enia zadane przez przeciwnika
	private final double defence;
	// szansa na trafienie
	private final double accuracy;
	// szansa na trafienie krytyczne
	private final double criticalChance;
	// obra¿enia zadawane podczas trafienia krytycznego (mno¿nik do obra¿eñ podstawowych)
	private final double criticalPower;
	
	private CharacterClass(int attackPower, int healthPoints, double defence, double accuracy, 
			double criticalChance, double criticalPower) {
		this.attackPower = attackPower;
		this.healthPoints = healthPoints;
		this.defence = defence;
		this.accuracy = accuracy;
		this.criticalChance = criticalChance;
		this.criticalPower = criticalPower;
	}
	
	public int getAttackPower() {
		return this.attackPower;
	}
	
	public int getHealthPoints() {
		return this.healthPoints;
	}
	
	public double getDefence() {
		return this.defence;
	}
	
	public double getAccuracy() {
		return this.accuracy;
	}
	
	public double getCriticalChance() {
		return this.criticalChance;
	}
	
	public double getCriticalPower() {
		return this.criticalPower;
	}
}
