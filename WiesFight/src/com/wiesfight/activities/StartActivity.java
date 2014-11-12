package com.wiesfight.activities;

import main.com.wiesfight.persistence.UserPersistence;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.wiesfight.R;
import com.wiesfight.managers.ConnectionManager;
import com.wiesfight.managers.PreferencesManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class StartActivity extends Activity {
	private static final String APPLICATION_ID = "62c4LxASRWuWfmkUiNhQQYzvBffHP3sNZVRDNS1t";
	private static final String CLIENT_KEY = "xhMJeAAqAVeGKwPnHoziBQzRMs1U5DTecIzq975g";
	private ParseInstallation currentInstallation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		if(!ConnectionManager.isConnectedToInternet(this)) {
			this.showInfoDialog();
		}
		else {
			this.initializeParse();
			if(this.userExistsForInstallation()) {
				this.goToMainActivity();
			}
			else {
				this.goToLoginActivity();
			}
		}
	}

	private void initializeParse() {
	    ParseObject.registerSubclass(UserPersistence.class);
	    Parse.enableLocalDatastore(this);
		Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
		ParseObject.unpinAllInBackground("currentUser");
	    this.currentInstallation = ParseInstallation.getCurrentInstallation();
	    this.currentInstallation.saveInBackground();
	    PreferencesManager.putInstallationId(this, this.currentInstallation.getInstallationId());
	}

    private boolean userExistsForInstallation() {
    	ParseQuery<UserPersistence> query = ParseQuery.getQuery(UserPersistence.class);
    	query.whereEqualTo("Installation", this.currentInstallation.getInstallationId());
    	try {
    		UserPersistence user = query.getFirst();
    		if(user != null) {
	    		user.loadUserFromDB();
	    		user.pin("currentUser");
    		}
    		return user != null;
    	}
    	catch(ParseException e) {
    		return false;
    	}
	}
    
    private void goToMainActivity() {
    	Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(intent);
    	finish();
    }
	
	private void goToLoginActivity() {
    	Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(intent);
    	finish();
	}

	@SuppressLint("InflateParams")
	private void showInfoDialog() {
		LayoutInflater inflater = this.getLayoutInflater();
	    View view = inflater.inflate(R.layout.dialog_ok, null);
	    final AlertDialog dialog = new AlertDialog.Builder(this)
	    	.setView(view).create();
	    Button btn1 = (Button) view.findViewById(R.id.btnOk);
	    TextView txt = (TextView) view.findViewById(R.id.txtMessageOk);
	    txt.setTextAppearance(this, android.R.attr.textAppearanceLarge);
	    txt.setText(getString(R.string.noInternetConnection));
	    btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				finish();
			}
		});
	    dialog.show();
	}
}
