package main.com.wiesfight.dto.enums;

public enum CharacterClass {
	INFORMATYK(20, 125, 0.1, 0.9, 0.15, 1.5, 0, 1, 2),
	KLOSZARD(15, 120, 0.1, 0.9, 0.15, 1.5, 3, 4, 5),
	KIBIC(15, 150, 0.1, 0.9, 0.15, 1.5, 6, 7, 8),
	MOHER(10, 140, 0.1, 0.9, 0.15, 1.5, 9, 10, 11),
	BLACHARA(9, 150, 0.1, 0.9, 0.15, 1.5, 12, 13, 14),
	TRENING(7, 160, 0.1, 0.9, 0.15, 1.5, -1, -1, -1);
	
	// zadawane obrażenia
	private final int attackPower;
	// punkty życia
	private final int healthPoints;
	// zmniejszone obrażenia zadane przez przeciwnika
	private final double defence;
	// szansa na trafienie
	private final double accuracy;
	// szansa na trafienie krytyczne
	private final double criticalChance;
	// obrażenia zadawane podczas trafienia krytycznego (mnożnik do obrażeń podstawowych)
	private final double criticalPower;
	
	private final int attackItemID;
	
	private final int defenceItemID;
	
	private final int miscItemID;
	
	private CharacterClass(int attackPower, int healthPoints, double defence, double accuracy, 
			double criticalChance, double criticalPower, int attackID, int defenceID, int miscID) {
		this.attackPower = attackPower;
		this.healthPoints = healthPoints;
		this.defence = defence;
		this.accuracy = accuracy;
		this.criticalChance = criticalChance;
		this.criticalPower = criticalPower;
		this.attackItemID = attackID;
		this.defenceItemID = defenceID;
		this.miscItemID = miscID;
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
	
	public int getAttackItemID() {
		return this.attackItemID;
	}
	
	public int getDefenceItemID() {
		return this.defenceItemID;
	}
	
	public int getMiscItemID() {
		return this.miscItemID;
	}
}
