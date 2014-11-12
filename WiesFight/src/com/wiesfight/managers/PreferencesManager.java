package com.wiesfight.managers;

import android.content.Context;
import android.content.SharedPreferences;

public final class PreferencesManager {
	private static final String SHARED_PREFERENCES_NAME = "com.wiesfight.preferences";
	private static final String SOUND_PREFERENCE_NAME = "com.wiesfight.preferences.sound";
	private static final String INSTALLATION_ID_NAME = "com.wiesfight.preferences.installationid";
	
	public static void putSoundPreference(Context context, Boolean value) {
		SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		prefs.edit().putBoolean(SOUND_PREFERENCE_NAME, value).apply();
	}
	
	public static Boolean getSoundPreference(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		return prefs.getBoolean(SOUND_PREFERENCE_NAME, true);
	}
	
	public static void putInstallationId(Context context, String value) {
		SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		prefs.edit().putString(INSTALLATION_ID_NAME, value).apply();
	}
	
	public static String getInstallationId(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		return prefs.getString(INSTALLATION_ID_NAME, "");
	}
}