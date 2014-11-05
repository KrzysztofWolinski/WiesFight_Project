package com.wiesfight.dataaccesslayer;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

public final class ParseHelper {
	
	private ParseHelper() {
		
	}
	
	public static ParseInstallation initializeParse(Context context) {
        registerSubclasses();    
        ParseInstallation currentInstallation = ParseInstallation.getCurrentInstallation();
        currentInstallation.saveInBackground();
		return currentInstallation;
	}
	
	private static void registerSubclasses() {
	}
}
