package com.wiesfight.activities;

import com.wiesfight.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
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
