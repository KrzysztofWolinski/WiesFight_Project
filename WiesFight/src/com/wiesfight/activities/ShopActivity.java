package com.wiesfight.activities;

import main.com.wiesfight.dto.User;

import com.wiesfight.R;
import com.wiesfight.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ShopActivity extends Activity {
	private User currentUser;
	
	private final int ATTACK_ITEM_PRICE = 5;
	private final int DEFENSE_ITEM_PRICE = 10;
	private final int MISC_ITEM_PRICE = 15;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop);
		Intent intent = getIntent();
		this.currentUser = (User) intent.getSerializableExtra("currentUser");
	}
	
	public void buyAttackItem(View v) {
		if (currentUser.getUserCoins() >= ATTACK_ITEM_PRICE) {
			currentUser.setAttackItemCount(currentUser.getAttackItemCount() - ATTACK_ITEM_PRICE);
			currentUser.addAttackItemCount();
			
			refreshCoins();
		}
	}
	
	public void buyDefenseItem(View v) {
		if (currentUser.getUserCoins() >= DEFENSE_ITEM_PRICE) {
			currentUser.setDefenceItemCount(currentUser.getDefenceItemCount() - DEFENSE_ITEM_PRICE);
			currentUser.addDefenceItemCount();
			
			refreshCoins();
		}
	}
	
	public void buyMiscItem(View v) {
		if (currentUser.getUserCoins() >= MISC_ITEM_PRICE) {
			currentUser.setMiscItemCount(currentUser.getMiscItemCount() - MISC_ITEM_PRICE);
			currentUser.addMiscItemCount();
			
			refreshCoins();
		}
	}
	
	private void refreshCoins() {
		TextView txt = (TextView) findViewById(R.id.lblCoins);
    	txt.setText(this.getCoinsString(this.currentUser.getUserCoins()));
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
