package com.wiesfight.activities;

import main.com.wiesfight.dto.enums.CharacterClass;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseQuery;
import com.wiesfight.R;
import com.wiesfight.dataaccesslayer.User;

public class LoginActivity extends Activity {
	private ParseInstallation currentInstallation;
	
	private RadioGroup.OnCheckedChangeListener ToggleListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(final RadioGroup radioGroup, final int i) {
            for (int j = 0; j < radioGroup.getChildCount(); j++) {
                final ToggleButton view = (ToggleButton) radioGroup.getChildAt(j);
                view.setChecked(view.getId() == i);
            }
        }
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        /*this.currentInstallation = ParseHelper.initializeParse(this);
		if(this.userExistsForInstallation(this.currentInstallation.getInstallationId())) {
			this.goToMainActivity();
		}*/
		setContentView(R.layout.activity_login);
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
    
    public void goToMenu(View v) {
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	finish();
    }
    
    public void addUser(View v) {
    	/*EditText editText = (EditText) findViewById(R.id.usernameText);
    	String username = editText.getText().toString().trim();
    	int userClass = ((RadioGroup) findViewById(R.id.toggleGroup)).getCheckedRadioButtonId();
    	if(this.correctUsername(username)) {
        	User newUser = new User(username, this.currentInstallation.getInstallationId(), userClass);
    		newUser.saveUser();
    		this.goToMainActivity();
    	}
    	else {
    		Context context = getApplicationContext();
    		CharSequence text = "Nazwa u�ytkownika za kr�tka lub zaj�ta";
    		int duration = Toast.LENGTH_SHORT;

    		Toast toast = Toast.makeText(context, text, duration);
    		toast.show();
    	}*/
    }

	private boolean correctUsername(String username) {
		if(username.length() < 3)
			return false;
		ParseQuery<User> query = ParseQuery.getQuery(User.class);
    	query.whereEqualTo("Username", username);
    	try {
    		User user = query.getFirst();
    		return user == null;
    	}
    	catch(ParseException e) {
    		return true;
    	}
	}
}
