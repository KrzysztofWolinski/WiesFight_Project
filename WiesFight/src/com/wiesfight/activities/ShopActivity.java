package com.wiesfight.activities;

import java.util.Locale;

import main.com.wiesfight.dto.User;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wiesfight.R;

public class ShopActivity extends Activity {
	private User currentUser;
	
	private final int ATTACK_ITEM_PRICE = 5;
	private final int DEFENSE_ITEM_PRICE = 10;
	private final int MISC_ITEM_PRICE = 15;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop);
		
		SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = mPrefs.getString("currentUser", "");
        this.currentUser = gson.fromJson(json, User.class);
        
        // TODO uaktualnić usera po zmianach
		
		refreshFeedback();
	}
	
	public void buyAttackItem(View v) {
		if (currentUser.getUserCoins() >= ATTACK_ITEM_PRICE) {
			currentUser.setAttackItemCount(currentUser.getAttackItemCount() - ATTACK_ITEM_PRICE);
			currentUser.addAttackItemCount();
			
			refreshFeedback();
		}
	}
	
	public void buyDefenseItem(View v) {
		if (currentUser.getUserCoins() >= DEFENSE_ITEM_PRICE) {
			currentUser.setDefenceItemCount(currentUser.getDefenseItemCount() - DEFENSE_ITEM_PRICE);
			currentUser.addDefenceItemCount();
			
			refreshFeedback();
		}
	}
	
	public void buyMiscItem(View v) {
		if (currentUser.getUserCoins() >= MISC_ITEM_PRICE) {
			currentUser.setMiscItemCount(currentUser.getMiscItemCount() - MISC_ITEM_PRICE);
			currentUser.addMiscItemCount();
			
			refreshFeedback();
		}
	}
	
	private void refreshFeedback() {
		if (this.currentUser != null) {
			TextView coinsText = (TextView) findViewById(R.id.coinsText);
			TextView attackItemsText = (TextView) findViewById(R.id.attackItemsText);
			TextView miscItemsText = (TextView) findViewById(R.id.miscItemsText);
			TextView defenseItemsText = (TextView) findViewById(R.id.defenseItemsText);
			
			coinsText.setText("Dostępne środki: " + this.getCoinsString(this.currentUser.getUserCoins()));
			attackItemsText.setText("Ilość: " + this.currentUser.getAttackItemCount());
			miscItemsText.setText("Ilość: " + this.currentUser.getMiscItemCount());
			defenseItemsText.setText("Ilość: " + this.currentUser.getDefenseItemCount());
			
			ImageView img = (ImageView) findViewById(R.id.imgAvatarShopBig);
	    	String className = this.currentUser.getUserClass().toString();
	    	try {
	    		img.setImageResource(R.drawable.class.getField(className.toLowerCase(Locale.ENGLISH) + "_big").getInt(null));
	    	}
	    	catch(Exception e) {
	    		
	    	}
		}
	}
	
	private String getCoinsString(int coins) {
    	String coinString = "monet";
    	int units = coins % 10;
    	int dozens = (coins % 100) / 10;
    	if(coins == 1)
    		coinString = "moneta";
    	else if((units == 2 || units == 3 || units == 4) && dozens != 1)
    		coinString = "monety";
		return coins + " " + coinString;
    }
}
