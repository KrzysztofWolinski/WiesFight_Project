package com.wiesfight.activities;

import java.util.Locale;

import main.com.wiesfight.dto.enums.*;
import main.com.wiesfight.dto.*;
import main.com.wiesfight.persistence.UserPersistence;

import com.wiesfight.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private static final String APPLICATION_ID = "62c4LxASRWuWfmkUiNhQQYzvBffHP3sNZVRDNS1t";
	private static final String CLIENT_KEY = "xhMJeAAqAVeGKwPnHoziBQzRMs1U5DTecIzq975g";
	private ParseInstallation currentInstallation;
	private int currentClass = 0;
	private User currentUser;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
        this.currentInstallation = ParseInstallation.getCurrentInstallation();
        this.currentInstallation.saveInBackground();
        ParseObject.registerSubclass(UserPersistence.class);
		if(this.userExistsForInstallation(this.currentInstallation.getInstallationId())) {
			this.goToMainActivity();
		}
		setContentView(R.layout.activity_login);
		String className = CharacterClass.values()[this.currentClass].toString();
    	TextView txt = (TextView) findViewById(R.id.lblClass);
    	txt.setText(className);
	}

    private boolean userExistsForInstallation(String installation) {
    	ParseQuery<UserPersistence> query = ParseQuery.getQuery(UserPersistence.class);
    	query.whereEqualTo("Installation", installation);
    	try {
    		UserPersistence user = query.getFirst();
    		this.currentUser = user.loadUserFromDB();
    		return user != null;
    	}
    	catch(ParseException e) {
    		return false;
    	}
	}
    
    private void goToMainActivity() {
    	Intent intent = new Intent(this, MainActivity.class);
    	intent.putExtra("currentUser", this.currentUser);
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
    		img.setImageResource(R.drawable.class.getField(className.toLowerCase(Locale.ENGLISH)).getInt(null));
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
    		this.currentUser = new User();
    		this.currentUser.setUserClass(CharacterClass.values()[this.currentClass]);
    		this.currentUser.setUserName(username);
        	UserPersistence user = new UserPersistence(this.currentUser, this.currentInstallation.getInstallationId());
        	user.saveUserToDB();
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
		ParseQuery<UserPersistence> query = ParseQuery.getQuery(UserPersistence.class);
    	query.whereEqualTo("Username", username);
    	try {
    		UserPersistence user = query.getFirst();
    		return user == null;
    	}
    	catch(ParseException e) {
    		return true;
    	}
	}
}
