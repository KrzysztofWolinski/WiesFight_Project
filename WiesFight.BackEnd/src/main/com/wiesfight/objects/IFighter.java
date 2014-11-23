package main.com.wiesfight.objects;

public interface IFighter {
	String getUserClass();
	int getHealth();
	int getAttackStrength();
	int getMaxHealth();
	void decreaseHealth(int minus);
}
