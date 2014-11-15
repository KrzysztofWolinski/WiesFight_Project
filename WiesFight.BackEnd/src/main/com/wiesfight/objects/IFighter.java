package main.com.wiesfight.objects;

public interface IFighter {
	String getUserClass();
	int getHealth();
	int decreaseAndGetHealth(int minus);
}
