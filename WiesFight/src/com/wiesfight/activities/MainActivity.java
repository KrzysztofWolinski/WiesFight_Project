package com.wiesfight.activities;

import java.util.Locale;

import main.com.wiesfight.dto.User;
import main.com.wiesfight.persistence.UserPersistence;

import com.parse.GetCallback;
import com.parse.ParseQuery;
import com.parse.ParseException;
import com.wiesfight.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
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

        this.initializeView();
    }
    
    private void initializeView() {
    	ParseQuery<UserPersistence> query = ParseQuery.getQuery(UserPersistence.class);
		query.fromPin("currentUser");
		query.getFirstInBackground(new GetCallback<UserPersistence>() {
			public void done(UserPersistence user, ParseException e) {
		        currentUser = user.getUser();
		        setControls();
			}
		});
    }
    
    private void setControls() {
    	TextView txt = (TextView) findViewById(R.id.lblCoins);
    	//txt.setText(this.getCoinsString(this.currentUser.getUserCoins()));
    	txt = (TextView) findViewById(R.id.lblUsername);
    	txt.setText(this.currentUser.getUserName());
    	txt = (TextView) findViewById(R.id.lblLevel);
    	txt.setText("Poziom " + this.currentUser.getUserLevel());
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
    	Intent intent = new Intent(this, PreFightActivity.class);
    	startActivity(intent);
    }
    
    public void goToShop(View v) {
    	Intent intent = new Intent(this, ShopActivity.class);
    	startActivityForResult(intent, 2);
    }
    
    public void goToSettings(View v) {
    	Intent intent = new Intent(this, SettingsActivity.class);
    	startActivityForResult(intent, 1);
    }
    
    public void showStats(View v) {
    	LayoutInflater inflater = this.getLayoutInflater();
	    View view = inflater.inflate(R.layout.dialog_ok, null);
	    final AlertDialog dialog = new AlertDialog.Builder(this)
	    	.setView(view).create();
	    Button btn1 = (Button) view.findViewById(R.id.btnOk);
	    TextView txt = (TextView) view.findViewById(R.id.txtMessageOk);
	    String text = String.format(getString(R.string.statsFormat), this.currentUser.getUserCoins(),
	    		this.currentUser.getUserLevel(), this.currentUser.getUserExperience());
    	txt.setText(text);
	    btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	    dialog.show();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_CANCELED) {
                this.finish();
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                this.initializeView();
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
