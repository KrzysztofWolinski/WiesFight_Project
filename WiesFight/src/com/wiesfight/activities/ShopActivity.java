package com.wiesfight.activities;

import java.util.Locale;

import main.com.wiesfight.dto.User;
import main.com.wiesfight.persistence.UserPersistence;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.wiesfight.R;

public class ShopActivity extends Activity {
	private UserPersistence currentUserPer;
	private User currentUser;
	
	private final int ATTACK_ITEM_PRICE = 1;
	private final int DEFENSE_ITEM_PRICE = 2;
	private final int MISC_ITEM_PRICE = 3;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop);
	
		ParseQuery<UserPersistence> query = ParseQuery.getQuery(UserPersistence.class);
		query.fromPin("currentUser");
		query.getFirstInBackground(new GetCallback<UserPersistence>() {
			public void done(UserPersistence user, ParseException e) {
				currentUserPer = user;
		        currentUser = user.getUser();
				refreshFeedback();
			}
		});
	}
	
	public void buyAttackItem(View v) {
		if (currentUser.getUserCoins() >= ATTACK_ITEM_PRICE) {
			currentUser.setUserCoins(currentUser.getUserCoins() - ATTACK_ITEM_PRICE);
			currentUser.addAttackItemCount();
			
			refreshFeedback();
		}
	}
	
	public void buyDefenseItem(View v) {
		if (currentUser.getUserCoins() >= DEFENSE_ITEM_PRICE) {
			currentUser.setUserCoins(currentUser.getUserCoins() - DEFENSE_ITEM_PRICE);
			currentUser.addDefenceItemCount();
			
			refreshFeedback();
		}
	}
	
	public void buyMiscItem(View v) {
		if (currentUser.getUserCoins() >= MISC_ITEM_PRICE) {
			currentUser.setUserCoins(currentUser.getUserCoins() - MISC_ITEM_PRICE);
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
			
			coinsText.setText(this.getCoinsString(this.currentUser.getUserCoins()));
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
    
    @Override
    public void onBackPressed() {
    	this.currentUserPer.setUser(this.currentUser);
		this.currentUserPer.saveUserToDB();
		setResult(RESULT_OK);
		finish();
    }
}
