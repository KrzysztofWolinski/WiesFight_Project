package com.wiesfight.activities;

import com.wiesfight.dataaccesslayer.*;
import com.wiesfight.enums.*;
import com.wiesfight.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

public class LoginActivity extends Activity {
	private ParseInstallation currentInstallation;
	static final RadioGroup.OnCheckedChangeListener ToggleListener = new RadioGroup.OnCheckedChangeListener() {
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
		Parse.initialize(this, "62c4LxASRWuWfmkUiNhQQYzvBffHP3sNZVRDNS1t", "xhMJeAAqAVeGKwPnHoziBQzRMs1U5DTecIzq975g");
        ParseObject.registerSubclass(AnywallPost.class);
        ParseObject.registerSubclass(User.class);       
        this.currentInstallation = ParseInstallation.getCurrentInstallation();
        this.currentInstallation.saveInBackground();
		if(this.userExistsForInstallation(this.currentInstallation.getInstallationId())) {
			this.goToMainActivity();
		}
		setContentView(R.layout.activity_login);
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.toggleGroup);
		for(CharacterClass c : CharacterClass.values()) {
			radioGroup.addView(this.createButton(c));
		}
		radioGroup.setOnCheckedChangeListener(ToggleListener);
	}
	
	private ToggleButton createButton(CharacterClass c) {
		ToggleButton btn = new ToggleButton(this);
		btn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT));
		btn.setText(c.name());
		btn.setTextOn(c.name());
		btn.setTextOff(c.name());
		btn.setId(c.ordinal());
		btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    
    public void addUser(View v) {
    	EditText editText = (EditText) findViewById(R.id.usernameText);
    	RadioGroup radioGroup = (RadioGroup) findViewById(R.id.toggleGroup);
    	User newUser = new User();
		newUser.setUsername(editText.getText().toString().trim());
		newUser.setUserClass(radioGroup.getCheckedRadioButtonId());
		newUser.setInstallation(this.currentInstallation.getInstallationId());
		newUser.add();
		this.goToMainActivity();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
