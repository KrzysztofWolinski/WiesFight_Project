package com.wiesfight.managers;

import android.content.Context;
import android.content.SharedPreferences;

public final class PreferencesManager {
	private static final String SHARED_PREFERENCES_NAME = "com.wiesfight.preferences";
	private static final String SOUND_PREFERENCE_NAME = "com.wiesfight.preferences.sound";
	
	public static void putSoundPreference(Context context, Boolean value) {
		SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		prefs.edit().putBoolean(SOUND_PREFERENCE_NAME, value).apply();
	}
	
	public static Boolean getSoundPreference(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
		return prefs.getBoolean(SOUND_PREFERENCE_NAME, true);
	}
}
