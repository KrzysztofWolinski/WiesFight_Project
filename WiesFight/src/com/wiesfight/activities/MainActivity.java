package com.wiesfight.activities;

import java.util.Locale;

import main.com.wiesfight.dto.User;

import com.google.gson.Gson;
import com.wiesfight.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("InflateParams")
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
    	startActivityForResult(intent, 1);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_CANCELED) {
                this.finish();
            }
        }
    }
    
    @Override
    public void onBackPressed() {
    	LayoutInflater inflater = this.getLayoutInflater();
	    View view = inflater.inflate(R.layout.dialog_yesno, null);
	    final AlertDialog dialog = new AlertDialog.Builder(this)
	    	.setView(view).create();
	    Button btn1 = (Button) view.findViewById(R.id.btnYes);
	    Button btn2 = (Button) view.findViewById(R.id.btnNo);
	    TextView txt = (TextView) view.findViewById(R.id.txtMessage);
	    txt.setText(getString(R.string.confirmExit));
	    btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
        		finish();    
			}
		});
	    btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	    dialog.show();
    }
}
