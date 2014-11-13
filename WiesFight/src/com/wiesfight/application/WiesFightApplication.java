package com.wiesfight.application;

import main.com.wiesfight.persistence.UserPersistence;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.wiesfight.managers.PreferencesManager;

import android.app.Application;

public class WiesFightApplication extends Application {
	private static final String APPLICATION_ID = "62c4LxASRWuWfmkUiNhQQYzvBffHP3sNZVRDNS1t";
	private static final String CLIENT_KEY = "xhMJeAAqAVeGKwPnHoziBQzRMs1U5DTecIzq975g";
	
	@Override
	public void onCreate() {
		super.onCreate();
		this.initializeParse();
	}

	private void initializeParse() {
		ParseObject.registerSubclass(UserPersistence.class);
	    Parse.enableLocalDatastore(this);
		Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
		ParseObject.unpinAllInBackground("currentUser");
		ParseInstallation currentInstallation = ParseInstallation.getCurrentInstallation();
	    currentInstallation.saveInBackground();
	    PreferencesManager.putInstallationId(this, currentInstallation.getInstallationId());
	}
}
