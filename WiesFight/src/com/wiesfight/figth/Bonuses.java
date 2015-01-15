package com.wiesfight.figth;

public enum Bonuses {
	ATTACKPOWER("Sila ataku"),
	DEFENCE("Obrona"),
	HEALTHPOINTS("Punkty zycia"),
	CRITICALCHANCE("Szansa trafienia krytycznego"),
	CRITICALPOWER("Sila trafienia krytycznego");
	
	private String description;
	
	private Bonuses(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
}
