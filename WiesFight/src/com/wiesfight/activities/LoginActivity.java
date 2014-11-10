package com.wiesfight.activities;

import java.util.Locale;

import main.com.wiesfight.dto.enums.*;
import main.com.wiesfight.dto.*;
import main.com.wiesfight.persistence.UserPersistence;

import com.wiesfight.R;
import com.wiesfight.managers.PreferencesManager;
import com.google.gson.Gson;
import com.parse.ParseException;
import com.parse.ParseQuery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private String currentInstallation;
	private int currentClass = 0;
	private User currentUser;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.currentInstallation = PreferencesManager.getInstallationId(this);
		setContentView(R.layout.activity_login);
		String className = CharacterClass.values()[this.currentClass].toString();
    	TextView txt = (TextView) findViewById(R.id.lblClass);
    	txt.setText(className);
	}
    
    private void goToMainActivity() {
    	SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
    	Editor prefsEditor = mPrefs.edit();
    	Intent intent = new Intent(this, MainActivity.class);
        Gson gs = new Gson();
        String jsonUser = gs.toJson(this.currentUser);
        prefsEditor.putString("currentUser", jsonUser);
        prefsEditor.commit();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(intent);
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
    
    public void addUser(View v) {
    	EditText editText = (EditText) findViewById(R.id.txtNick);
    	String username = editText.getText().toString().trim();
    	if(this.isUsernameCorrect(username)) {
    		this.currentUser = new User();
    		this.currentUser.setUserClass(CharacterClass.values()[this.currentClass]);
    		this.currentUser.setUserName(username);
        	UserPersistence user = new UserPersistence(this.currentUser, this.currentInstallation);
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

	private boolean isUsernameCorrect(String username) {
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
