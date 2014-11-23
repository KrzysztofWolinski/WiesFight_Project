package com.wiesfight.activities;

import java.util.Locale;

import main.com.wiesfight.dto.User;
import main.com.wiesfight.figth.Fight;
import main.com.wiesfight.objects.Fighter;
import main.com.wiesfight.objects.IFighter;
import main.com.wiesfight.objects.TrainingOpponent;
import main.com.wiesfight.persistence.UserPersistence;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.wiesfight.R;

public class FightActivity extends Activity {
	private Boolean training = false;
	private IFighter currentUser;
	private IFighter opponent;
	private Fight fight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fight);
		this.training = getIntent().getBooleanExtra("training", false);
		ParseQuery<UserPersistence> query = ParseQuery.getQuery(UserPersistence.class);
		query.fromPin("currentUser");
		query.getFirstInBackground(new GetCallback<UserPersistence>() {
			public void done(UserPersistence user, ParseException e) {
				currentUser = new Fighter(user.getUser());
				initializeView();
			}
		});
	}
	
	protected void initializeView() {
		if(this.training) {
			this.opponent = new TrainingOpponent();
		}
		else {
			// przeciwnik powinien przyjsc z poprzedniego widoku
			this.opponent = new Fighter((User)getIntent().getSerializableExtra("opponent"));
		}

		this.fight = new Fight(this.currentUser, this.opponent);
		
		String userClassName = this.currentUser.getUserClass().toString();
		String opponentClassName = this.opponent.getUserClass().toString();
		
    	try {
    		ImageView img = (ImageView) findViewById(R.id.imgAvatarUser);
    		img.setImageResource(R.drawable.class.getField(userClassName.toLowerCase(Locale.ENGLISH)).getInt(null));
    		img = (ImageView) findViewById(R.id.imgAvatarOpponent);
    		img.setImageResource(R.drawable.class.getField(opponentClassName.toLowerCase(Locale.ENGLISH)).getInt(null));
    		
    		img = (ImageView) findViewById(R.id.userCharacter);
    		img.setImageResource(R.drawable.class.getField(currentUser.getUserClass().toLowerCase(Locale.ENGLISH) + "_big").getInt(null));
    		
    		img = (ImageView) findViewById(R.id.opponentCharacter);
    		img.setImageResource(R.drawable.class.getField(opponent.getUserClass().toLowerCase(Locale.ENGLISH) + "_big").getInt(null));
    	}
    	catch(Exception e) {
    		
    	}
	}
    
    @SuppressLint("InflateParams")
	@Override
    public void onBackPressed() {
    	LayoutInflater inflater = this.getLayoutInflater();
	    View view = inflater.inflate(R.layout.dialog_yesno, null);
	    final AlertDialog dialog = new AlertDialog.Builder(this)
	    	.setView(view).create();
	    Button btn1 = (Button) view.findViewById(R.id.btnYes);
	    Button btn2 = (Button) view.findViewById(R.id.btnNo);
	    TextView txt = (TextView) view.findViewById(R.id.txtMessage);
	    txt.setText(getString(R.string.confirmLeaveFight));
	    btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				FightActivity.super.onBackPressed();
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
    
    public void onPressAttackButton(View v) {
    	// TODO
    	fight.attack();
    	
    	updateBattlefield();
    }
    
    private void updateBattlefield() {
    	// Sprawdzić czy walka ciągle trwa i zaaktualizować feedback (HP, itemy itd.)
    	ProgressBar hpBar = (ProgressBar) findViewById(R.id.userHpBar);
    	hpBar.setProgress(currentUser.getHealth());
    	hpBar = (ProgressBar) findViewById(R.id.opponentHpBar);
    	hpBar.setProgress(opponent.getHealth());
    }
}
