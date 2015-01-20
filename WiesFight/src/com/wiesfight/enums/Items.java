package com.wiesfight.enums;


import com.wiesfight.R;
import com.wiesfight.figth.Bonuses;

public enum Items {
	KLAWIATURA(R.drawable.informatyk_attack, R.string.klawiatura, Bonuses.ATTACKPOWER, 1.2, 3),
	FLANELA(R.drawable.informatyk_defense, R.string.koszula, Bonuses.DEFENCE, 0.1, 3),
	KAWA(R.drawable.informatyk_misc, R.string.kawa, Bonuses.CRITICALPOWER, 1.5, 3),
	
	TULIPAN(R.drawable.kloszard_attack, R.string.tulipan, Bonuses.ATTACKPOWER, 1.2, 3),
	POKRYWA(R.drawable.kloszard_defense, R.string.tarcza, Bonuses.DEFENCE, 0.1, 3),
	WINO(R.drawable.kloszard_misc, R.string.wino, Bonuses.HEALTHPOINTS, 10, 3),
	
	BASEBALL(R.drawable.kibic_attack, R.string.kij, Bonuses.ATTACKPOWER, 1.2, 3),
	SZALIK(R.drawable.kibic_defense, R.string.szalik, Bonuses.DEFENCE, 0.1, 3),
	ENERGETYK(R.drawable.kibic_misc, R.string.red, Bonuses.CRITICALPOWER, 1.5, 3),
	
	TOREBKA(R.drawable.moher_attack, R.string.torba, Bonuses.ATTACKPOWER, 1.2, 3),
	CHODZIK(R.drawable.moher_defense, R.string.chodzik, Bonuses.DEFENCE, 0.1, 3),
	WODA(R.drawable.moher_misc, R.string.woda, Bonuses.HEALTHPOINTS, 5, 2),
	
	TIPSY(R.drawable.blachara_attack, R.string.tipsy, Bonuses.ATTACKPOWER, 1.2, 3),
	IMPLANTY(R.drawable.blachara_defense, R.string.imp, Bonuses.DEFENCE, 0.1, 3),
	DRINK(R.drawable.blachara_misc, R.string.drink, Bonuses.CRITICALCHANCE, 1.5, 3);
	
	private int file;
	
	private int description;
	
	private Bonuses bonusType;
	
	private double bonus;
	
	private int duration;
	
	private Items(int file, int description, Bonuses bonusType, double bonus, int duration) {
		this.file = file;
		this.description = description;
		this.bonusType = bonusType;
		this.bonus = bonus;
		this.duration = duration;
	}
	
	public int getImageFile() {
		return this.file;
	}
	
	public int getDescription() {
		return this.description;
	}
	
	public Bonuses getBonusType() {
		return this.bonusType;
	}
	
	public double getBonus() {
		return this.bonus;
	}
	
	public int getDuration() {
		return this.duration;
	}
}
