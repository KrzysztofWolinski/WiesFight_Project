package com.wiesfight.enums;

public enum Bonuses {
	ATTACKPOWER("Si�a ataku"),
	DEFENCE("Obrona"),
	HEALTHPOINTS("Punkty �ycia"),
	CRITICALCHANCE("Szansa trafienia krytycznego"),
	CRITICALPOWER("Si�a trafienia krytycznego");
	
	private String description;
	
	private Bonuses(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
}
