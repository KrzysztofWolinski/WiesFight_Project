package com.wiesfight.application;

import main.com.wiesfight.persistence.UserPersistence;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.wiesfight.managers.PreferencesManager;

import android.app.Application;

public class WiesFightApplication extends Application {
	private static final String APPLICATION_ID = "62c4LxASRWuWfmkUiNhQQYzvBffHP3sNZVRDNS1t";
	private static final String CLIENT_KEY = "xhMJeAAqAVeGKwPnHoziBQzRMs1U5DTecIzq975g";
	
	private static final String API_KEY = "c71e30b3bb60cc315d5e590e72d8189cfcd82799135e340d83295f6c6ca88dfd";
	private static final String SECRET_KEY = "4d4c30c74ac5f0581f207b13521ece9e9b8817b5ba62af891d33d145ae0a540b";
	
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
	    
		WarpClient.initialize(API_KEY, SECRET_KEY);
	}
}
