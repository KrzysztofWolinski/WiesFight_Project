package com.wiesfight.figth;

public enum Bonuses {
	ATTACKPOWER("Siła ataku"),
	DEFENCE("Obrona"),
	HEALTHPOINTS("Punkty życia"),
	CRITICALCHANCE("Szansa trafienia krytycznego"),
	CRITICALPOWER("Siła trafienia krytycznego");
	
	private String description;
	
	private Bonuses(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
}
