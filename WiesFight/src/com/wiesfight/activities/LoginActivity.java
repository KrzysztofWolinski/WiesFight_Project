package com.wiesfight.activities;

import com.wiesfight.dataaccesslayer.*;
import main.com.wiesfight.dto.enums.*;
import com.wiesfight.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseQuery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class LoginActivity extends Activity {
	private ParseInstallation currentInstallation;
	private int currentClass = 0;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        /*this.currentInstallation = ParseHelper.initializeParse(this);
		if(this.userExistsForInstallation(this.currentInstallation.getInstallationId())) {
			this.goToMainActivity();
		}*/
		setContentView(R.layout.activity_login);
		String className = CharacterClass.values()[this.currentClass].toString();
    	TextView txt = (TextView) findViewById(R.id.lblClass);
    	txt.setText(className);
		//this.populateRadioGroup();
	}
	
	private void populateRadioGroup() {
//		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.toggleGroup);
//		for(CharacterClass c : CharacterClass.values()) {
//			radioGroup.addView(this.createButton(c));
//		}
//		radioGroup.setOnCheckedChangeListener(ToggleListener);
//		radioGroup.check(0);
	}

	private ToggleButton createButton(CharacterClass c) {
		ToggleButton btn = new ToggleButton(this);
		btn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		btn.setTextSize(9f);
		btn.setText(c.name());
		btn.setTextOn(c.name());
		btn.setTextOff(c.name());
		btn.setId(c.ordinal());
		btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	((RadioGroup)view.getParent()).clearCheck();
            	((RadioGroup)view.getParent()).check(view.getId());
            }
        });
		return btn;
	}

    private boolean userExistsForInstallation(String installation) {
    	ParseQuery<User> query = ParseQuery.getQuery(User.class);
    	query.whereEqualTo("Installation", installation);
    	try {
    		User user = query.getFirst();
    		return user != null;
    	}
    	catch(ParseException e) {
    		return false;
    	}
	}
    
    private void goToMainActivity() {
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	finish();
    }
    
    public void changeClass(View v) {
    	if(v.getId() == R.id.ibtnLeft ) {
    		if(this.currentClass > 0)
    			this.currentClass--;
    		else
    			return;
    	}
    	else if(v.getId() == R.id.ibtnRight) {
    		if(this.currentClass < 4)
    			this.currentClass++;
    		else
    			return;
    	}
    	String className = CharacterClass.values()[this.currentClass].toString();
    	TextView txt = (TextView) findViewById(R.id.lblClass);
    	txt.setText(className);
    	ImageView img = (ImageView) findViewById(R.id.imgAvatar);
    	try {
    		img.setImageResource(R.drawable.class.getField(className.toLowerCase()).getInt(null));
    	}
    	catch(Exception e) {
    		
    	}
    }
    
    public void goToMenu(View v) {
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	finish();
    }
    
    public void addUser(View v) {
    	EditText editText = (EditText) findViewById(R.id.txtNick);
    	String username = editText.getText().toString().trim();
    	if(this.correctUsername(username)) {
        	//User newUser = new User(username, this.currentInstallation.getInstallationId(), userClass);
    		//newUser.saveUser();
    		this.goToMainActivity();
    	}
    	else {
    		Context context = getApplicationContext();
    		CharSequence text = "Nazwa uzytkownika za krotka lub zajeta";
    		int duration = Toast.LENGTH_SHORT;

    		Toast toast = Toast.makeText(context, text, duration);
    		toast.show();
    	}
    }

	private boolean correctUsername(String username) {
		if(username.length() < 3)
			return false;
		return true;
		/*ParseQuery<User> query = ParseQuery.getQuery(User.class);
    	query.whereEqualTo("Username", username);
    	try {
    		User user = query.getFirst();
    		return user == null;
    	}
    	catch(ParseException e) {
    		return true;
    	}*/
	}
}
