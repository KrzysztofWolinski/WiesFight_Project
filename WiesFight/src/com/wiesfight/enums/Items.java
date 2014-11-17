package com.wiesfight.enums;

import com.wiesfight.R;

public enum Items {
	KLAWIATURA(R.drawable.klawiatura, R.string.klawiatura, Bonuses.ATTACKPOWER, 1.2, 3),
	FLANELA(R.drawable.koszula, R.string.koszula, Bonuses.DEFENCE, 1.2, 3),
	KAWA(R.drawable.kawa, R.string.kawa, Bonuses.CRITICALPOWER, 1.2, 3),
	TULIPAN(R.drawable.tulipan, R.string.tulipan, Bonuses.ATTACKPOWER, 1.2, 3),
	POKRYWA(R.drawable.tarcza, R.string.tarcza, Bonuses.DEFENCE, 1.2, 3),
	WINO(R.drawable.wino, R.string.wino, Bonuses.DEFENCE, 1.2, 3),
	BASEBALL(R.drawable.kij, R.string.kij, Bonuses.ATTACKPOWER, 1.2, 3),
	SZALIK(R.drawable.szalik, R.string.szalik, Bonuses.DEFENCE, 1.2, 3),
	ENERGETYK(R.drawable.red, R.string.red, Bonuses.ATTACKPOWER, 1.1, 3),
	TOREBKA(R.drawable.torba, R.string.torba, Bonuses.ATTACKPOWER, 1.2, 3),
	CHODZIK(R.drawable.chodzik, R.string.chodzik, Bonuses.DEFENCE, 1.2, 3),
	WODA(R.drawable.woda, R.string.woda, Bonuses.HEALTHPOINTS, 5, 3),
	TIPSY(R.drawable.tipsy, R.string.tipsy, Bonuses.ATTACKPOWER, 1.2, 3),
	IMPLANTY(R.drawable.imp, R.string.imp, Bonuses.DEFENCE, 1.2, 3),
	DRINK(R.drawable.drink, R.string.drink, Bonuses.CRITICALCHANCE, 1.2, 3);
	
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
