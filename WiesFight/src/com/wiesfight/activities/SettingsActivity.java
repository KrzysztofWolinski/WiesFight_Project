package com.wiesfight.activities;

import main.com.wiesfight.persistence.UserPersistence;

import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.wiesfight.R;
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
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class SettingsActivity extends Activity {
	Boolean soundOn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		this.setResult(RESULT_OK);
		this.soundOn = PreferencesManager.getSoundPreference(this);
		if(!this.soundOn) {
			ImageView img = (ImageView) findViewById(R.id.imgSound);
			img.setImageResource(R.drawable.wyl);
		}
	}
	
	public void switchSound(View v) {
		ImageView img = (ImageView) findViewById(R.id.imgSound);
		if(!this.soundOn) {
			this.soundOn = true;
			img.setImageResource(R.drawable.wl);
		}
		else {
			this.soundOn = false;
			img.setImageResource(R.drawable.wyl);
		}
		PreferencesManager.putSoundPreference(this, this.soundOn);
	}
	
	public void showHowToPlay(View v) {
		LayoutInflater inflater = this.getLayoutInflater();
	    View view = inflater.inflate(R.layout.dialog_ok, null);
	    final AlertDialog dialog = new AlertDialog.Builder(this)
	    	.setView(view).create();
	    Button btn1 = (Button) view.findViewById(R.id.btnOk);
	    btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	    dialog.show();
	}
	
	public void deleteCharacter(View v) {
	    LayoutInflater inflater = this.getLayoutInflater();
	    View view = inflater.inflate(R.layout.dialog_yesno, null);
	    final AlertDialog dialog = new AlertDialog.Builder(this)
	    	.setView(view).create();
	    Button btn1 = (Button) view.findViewById(R.id.btnYes);
	    Button btn2 = (Button) view.findViewById(R.id.btnNo);
	    TextView txt = (TextView) view.findViewById(R.id.txtMessage);
	    txt.setText(getString(R.string.confirmDelete));
	    btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				deleteCharacter();
			}
		});
	    btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	    dialog.show();
	}

	private void deleteCharacter() {
		ParseQuery<UserPersistence> query = ParseQuery.getQuery(UserPersistence.class);
		query.fromPin("currentUser");
		query.getFirstInBackground(new GetCallback<UserPersistence>() {
			public void done(UserPersistence user, ParseException e) {
				ParseObject.unpinAllInBackground("currentUser");
				user.deleteInBackground(new DeleteCallback() {
					@Override
					public void done(ParseException e) {
						returnToLogin();
					}
				});
			}
		});
	}
	
	private void returnToLogin() {
		Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    	startActivity(intent);
    	finish();
	}
	
	public void leaveGame(View v) {
		LayoutInflater inflater = this.getLayoutInflater();
	    View view = inflater.inflate(R.layout.dialog_yesno, null);
	    final AlertDialog dialog = new AlertDialog.Builder(this)
	    	.setView(view).create();
	    Button btn1 = (Button) view.findViewById(R.id.btnYes);
	    Button btn2 = (Button) view.findViewById(R.id.btnNo);
	    TextView txt = (TextView) view.findViewById(R.id.txtMessage);
	    txt.setText(getString(R.string.confirmExit));
	    btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
        		setResult(RESULT_CANCELED);
        		finish();    
			}
		});
	    btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	    dialog.show();
	}
}
