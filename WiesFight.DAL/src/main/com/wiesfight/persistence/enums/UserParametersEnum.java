package main.com.wiesfight.persistence.enums;

public enum UserParametersEnum {
	USER_NAME("UserName"),
	LEVEL("Level"),
	EXPERIENCE("Experience"),
	COINS("Coins"),
	ATTACK_ITEMS_COUNT("AttackItemsCount"),
	DEFENSE_ITEMS_COUNT("DefenseItemsCount"),
	MISC_ITEMS_COUNT("MiscItemsCount"),
	USER_CLASS("UserClass");
	
	private final String parameterName;

	private UserParametersEnum(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterName() {
		return this.parameterName;
	}
}
