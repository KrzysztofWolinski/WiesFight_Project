package com.wiesfight.dataaccesslayer;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

public final class ParseHelper {
	private static final String APPLICATION_ID = "62c4LxASRWuWfmkUiNhQQYzvBffHP3sNZVRDNS1t";
	private static final String CLIENT_KEY = "xhMJeAAqAVeGKwPnHoziBQzRMs1U5DTecIzq975g";
	
	private ParseHelper() {
		
	}
	
	public static ParseInstallation initializeParse(Context context) {
		Parse.initialize(context, APPLICATION_ID, CLIENT_KEY);
        registerSubclasses();    
        ParseInstallation currentInstallation = ParseInstallation.getCurrentInstallation();
        currentInstallation.saveInBackground();
		return currentInstallation;
	}
	
	private static void registerSubclasses() {
		ParseObject.registerSubclass(AnywallPost.class);
        ParseObject.registerSubclass(User.class);
	}
}
