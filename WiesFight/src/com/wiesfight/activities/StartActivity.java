package com.wiesfight.activities;

import java.util.Calendar;
import java.util.Date;

import main.com.wiesfight.dto.User;
import main.com.wiesfight.persistence.UserPersistence;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.wiesfight.R;
import com.wiesfight.managers.ConnectionManager;
import com.wiesfight.managers.DialogManager;
import com.wiesfight.managers.PreferencesManager;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class StartActivity extends Activity {
	// troche flag musialem porobic, zeby wszystko ladnie dzialalo
	private Boolean videoFinished = false;
	private Boolean initFinished = false;
	private Boolean userExists = false;
	private Boolean isConnected = false;
	private Boolean methodCalled = false;
	private Boolean addBonus = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		this.playIntro();
		if(ConnectionManager.isConnectedToInternet(this)) {
			this.isConnected = true;
			this.userExists = this.userExistsForInstallation();
		}
		this.initFinished = true;
		this.goToNextActivity();
	}
	
	private void playIntro() {
		VideoView vv = (VideoView) findViewById(R.id.vvIntro);
		vv.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer arg0) {
				videoFinished = true;
				goToNextActivity();
			}
		});
		MediaController ctrl = new MediaController(this);
		ctrl.setVisibility(View.GONE);
		vv.setMediaController(ctrl);
		Uri video = Uri.parse("android.resource://" + getPackageName() + "/" 
				+ R.raw.animacja); 
		vv.setVideoURI(video);
		// wyciszenie jezeli tak jest w ustawieniach
        if(PreferencesManager.getSoundPreference(this))
        	vv.start();
        else
        	vv.setOnPreparedListener(PreparedListener);
	}

	// wyciszyc VideoView da sie tylko w ten sposob
	MediaPlayer.OnPreparedListener PreparedListener = new MediaPlayer.OnPreparedListener() {
	     @Override
	     public void onPrepared(MediaPlayer m) {
	         try {
		         if (m.isPlaying()) {
			         m.stop();
			         m.release();
			         m = new MediaPlayer();
		         }
		         m.setVolume(0f, 0f);
		         m.start();
	         } catch (Exception e) {
	        	 e.printStackTrace();
	         }    
	     }
	 };

	private void goToNextActivity() {
		if(this.initFinished && this.videoFinished && !this.methodCalled) {
			this.methodCalled = true;
			if(!this.isConnected) {
				DialogManager.showInfoDialog(this, getString(R.string.noInternetConnection));
			}
			else {
				if(this.userExists)
					this.goToMainActivity();
				else
					this.goToLoginActivity();
			}
		}
	}

    private boolean userExistsForInstallation() {
    	ParseQuery<UserPersistence> query = ParseQuery.getQuery(UserPersistence.class);
    	query.whereEqualTo("Installation", PreferencesManager.getInstallationId(this));
    	try {
    		UserPersistence user = query.getFirst();
    		if(user != null) {
	    		user.loadUserFromDB();
	    		this.checkBonus(user);
	    		user.pin("currentUser");
    		}
    		return user != null;
    	}
    	catch(ParseException e) {
    		return false;
    	}
	}
    
    private void checkBonus(UserPersistence user) {
		User u = user.loadUserFromDB();
		Date currentDate = new Date();
		if(u.getLastBonusDate() == null || this.giveBonus(currentDate, u.getLastBonusDate())) {
			this.addBonus = true;
			u.setLastBonusDate(currentDate);
			u.addBonusCoins();
			user.setUser(u);
			user.saveUserToDB();
		}
	}
    
    private Boolean giveBonus(Date current, Date last) {
    	Calendar currentCal = Calendar.getInstance();
    	currentCal.setTime(current);
    	Calendar lastCal = Calendar.getInstance();
    	lastCal.setTime(last);
    	if(currentCal.get(Calendar.YEAR) > lastCal.get(Calendar.YEAR))
    		return true;
    	if(currentCal.get(Calendar.MONTH) > lastCal.get(Calendar.MONTH))
    		return true;
    	if(currentCal.get(Calendar.DAY_OF_MONTH) > lastCal.get(Calendar.DAY_OF_MONTH))
    		return true;
    	return false;
    }

	private void goToMainActivity() {
    	Intent intent = new Intent(this, MainActivity.class);
    	intent.putExtra("bonus", addBonus);
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
}
