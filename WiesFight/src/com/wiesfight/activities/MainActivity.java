package com.wiesfight.activities;

import java.util.Locale;

import main.com.wiesfight.dto.User;

import com.google.gson.Gson;
import com.wiesfight.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      
        setContentView(R.layout.activity_main);

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = mPrefs.getString("currentUser", "");
        this.currentUser = gson.fromJson(json, User.class);
        
        //Intent intent = getIntent();
        //this.currentUser = (User) intent.getSerializableExtra("currentUser");
        TextView txt = (TextView) findViewById(R.id.lblCoins);
    	txt.setText(this.getCoinsString(this.currentUser.getUserCoins()));
    	txt = (TextView) findViewById(R.id.lblUsername);
    	txt.setText(this.currentUser.getUserName() + " (lvl " + this.currentUser.getUserLevel() + ")");
    	ImageView img = (ImageView) findViewById(R.id.imgAvatarMain);
    	String className = this.currentUser.getUserClass().toString();
    	try {
    		img.setImageResource(R.drawable.class.getField(className.toLowerCase(Locale.ENGLISH)).getInt(null));
    	}
    	catch(Exception e) {
    		
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
    
    public void goToFight(View v) {
    	Intent intent = new Intent(this, FightActivity.class);
    	startActivity(intent);
    }
    
    public void goToShop(View v) {
    	Intent intent = new Intent(this, ShopActivity.class);
    	startActivity(intent);
    }
    
    public void goToSettings(View v) {
    	Intent intent = new Intent(this, SettingsActivity.class);
    	startActivity(intent);
    }
}
