package com.wiesfight.activities;

import java.util.Locale;

import main.com.wiesfight.dto.enums.*;
import main.com.wiesfight.dto.*;
import main.com.wiesfight.persistence.UserPersistence;

import com.wiesfight.R;
import com.wiesfight.managers.PreferencesManager;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private String currentInstallation;
	private int currentClass = 0;
	private User currentUser;
    private ProgressDialog progressDialog;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.currentInstallation = PreferencesManager.getInstallationId(this);
		setContentView(R.layout.activity_login);
		this.drawControls();
	}
    
    private void drawControls() {
    	CharacterClass charClass = CharacterClass.values()[this.currentClass];
    	String className = charClass.toString();
    	TextView txt = (TextView) findViewById(R.id.lblClass);
    	txt.setText(className);
    	ImageView img = (ImageView) findViewById(R.id.imgAvatar);
    	try {
    		img.setImageResource(R.drawable.class.getField(className.toLowerCase(Locale.ENGLISH)).getInt(null));
    	}
    	catch(Exception e) {
    		
    	}
    	txt = (TextView) findViewById(R.id.txtClassFeatures);
    	String text = String.format(getString(R.string.charFormat), charClass.getAttackPower(),
    			charClass.getHealthPoints(), (int)(charClass.getDefence() * 100), (int)(charClass.getAccuracy() * 100),
    			(int)(charClass.getCriticalChance() * 100), (int)(charClass.getCriticalPower() * 100));
    	txt.setText(text);
	}

	private void goToMainActivity() {
		if(this.progressDialog != null){
			this.progressDialog.dismiss();
			this.progressDialog = null;
		}
    	Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(intent);
    	finish();
    }
    
    public void changeClass(View v) {
    	if(v.getId() == R.id.ibtnLeft ) {
    		this.currentClass = this.currentClass > 0 ? this.currentClass - 1 : 4;
    	}
    	else if(v.getId() == R.id.ibtnRight) {
    		this.currentClass = this.currentClass < 4 ? this.currentClass + 1 : 0;
    	}
    	this.drawControls();
    }
    
    public void validateUser(View v) {
    	EditText editText = (EditText) findViewById(R.id.txtNick);
    	String username = editText.getText().toString().trim();
    	if(this.isUsernameCorrect(username)) {
    		this.addUser(username);
    	}
    	else {
    		this.showToast();
    	}
    }
    
    private void showToast() {
		Toast toast = Toast.makeText(this, getString(R.string.addingError), Toast.LENGTH_SHORT);
		toast.show();
	}

	private void addUser(String username) {
		this.progressDialog = new ProgressDialog(this, AlertDialog.THEME_HOLO_DARK);
		this.progressDialog.setMessage(getString(R.string.progress));
		this.progressDialog.show();
    	this.currentUser = new User();
		this.currentUser.setUserClass(CharacterClass.values()[this.currentClass]);
		this.currentUser.setUserName(username);
    	UserPersistence user = new UserPersistence(this.currentUser, this.currentInstallation);
    	user.saveUserToDB();
		user.pinInBackground("currentUser", new SaveCallback() {
			@Override
			public void done(ParseException e) {
	    		goToMainActivity();
			}
		});
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
