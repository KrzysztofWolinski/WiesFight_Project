package com.wiesfight.activities;

import java.util.Locale;

import main.com.wiesfight.dto.User;
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
import com.wiesfight.enums.Items;
import com.wiesfight.figth.Fight;
import com.wiesfight.objects.Fighter;
import com.wiesfight.objects.IFighter;
import com.wiesfight.objects.TrainingOpponent;

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
		
		updateBattlefield();
		
    	try {
    		ImageView img = (ImageView) findViewById(R.id.imgAvatarUser);
    		img.setImageResource(R.drawable.class.getField(userClassName.toLowerCase(Locale.ENGLISH)).getInt(null));
    		img = (ImageView) findViewById(R.id.imgAvatarOpponent);
    		img.setImageResource(R.drawable.class.getField(opponentClassName.toLowerCase(Locale.ENGLISH)).getInt(null));
    		
    		img = (ImageView) findViewById(R.id.userCharacter);
    		img.setImageResource(R.drawable.class.getField(currentUser.getUserClass().toString().toLowerCase(Locale.ENGLISH) + "_big_fight").getInt(null));
    		
    		img = (ImageView) findViewById(R.id.opponentCharacter);
    		img.setImageResource(R.drawable.class.getField(opponent.getUserClass().toString().toLowerCase(Locale.ENGLISH) + "_big_fight").getInt(null));
    		
    		// User items
    		Items item = Items.values()[currentUser.getUserClass().getAttackItemID()];
        	img = (ImageView) findViewById(R.id.userAttackItem);
        	img.setImageResource(item.getImageFile());
        	
        	item = Items.values()[currentUser.getUserClass().getDefenceItemID()];
        	img = (ImageView) findViewById(R.id.userDefenseItem);
        	img.setImageResource(item.getImageFile());
        	
        	item = Items.values()[currentUser.getUserClass().getMiscItemID()];
        	img = (ImageView) findViewById(R.id.userMiscItem);
        	img.setImageResource(item.getImageFile());
        	
        	// Opponent items
        	item = Items.values()[opponent.getUserClass().getAttackItemID()];
        	img = (ImageView) findViewById(R.id.opponentAttackItem);
        	img.setImageResource(item.getImageFile());
        	
        	item = Items.values()[opponent.getUserClass().getDefenceItemID()];
        	img = (ImageView) findViewById(R.id.opponentDefenseItem);
        	img.setImageResource(item.getImageFile());
        	
        	item = Items.values()[opponent.getUserClass().getMiscItemID()];
        	img = (ImageView) findViewById(R.id.opponentMiscItem);
        	img.setImageResource(item.getImageFile());
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
    
    public void onPressAttackItemButton(View v) {
    	this.currentUser.useAttackItem();
    	this.updateBattlefield();
    }
    
    public void onPressDefenseItemButton(View v) {
    	this.currentUser.useDefenseItem();
    	this.updateBattlefield();
    }

    public void onPressMiscItemButton(View v) {
    	this.currentUser.useMiscItem();
    	this.updateBattlefield();
    }
    
    private void updateBattlefield() {
    	// Sprawdzić czy walka ciągle trwa i zaaktualizować feedback (HP, itemy itd.)
    	updateHpBars();
    	
    	updateItemNotifications();
    	
    	if (fight.isFightFinished()) {
    		LayoutInflater inflater = this.getLayoutInflater();
    	    View view = inflater.inflate(R.layout.dialog_ok, null);
    	    final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

    	    TextView txt = (TextView) view.findViewById(R.id.txtMessageOk);
    	    txt.setText("Walka zostałą zakończona zwycięstwem gracza " + fight.getWinner().getName());

    	    Button btn = (Button) view.findViewById(R.id.btnOk);
    	    btn.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				dialog.dismiss();
    				FightActivity.super.onBackPressed();
    			}
    		});
    	    dialog.show();
    	}
    }

    private void updateHpBars() {
        // User
        View hpBar = (View) findViewById(R.id.userHpBar);
        TextView hpText = (TextView) findViewById(R.id.userHpText);

        hpText.setText(String.valueOf(currentUser.getHealth()));

        float scale = (float)((currentUser.getHealth() / currentUser.getMaxHealth()));
        hpBar.setScaleX(scale);
        hpBar.setTranslationX((scale / 2) * hpBar.getWidth() - (hpBar.getWidth() / 2));

        // Opponent
        hpBar = (View) findViewById(R.id.opponentHpBar);
        hpText = (TextView) findViewById(R.id.opponentHpText);

        hpText.setText(String.valueOf(opponent.getHealth()));

        scale = (float)((opponent.getHealth() / opponent.getMaxHealth()));
        hpBar.setScaleX(scale);
        hpBar.setTranslationX( - ((scale / 2) * hpBar.getWidth() - (hpBar.getWidth() / 2)));
    }

    private void updateItemNotifications() {
    	TextView attackItemsText = (TextView) findViewById(R.id.userAttackItemCount);
    	TextView defenceItemsText = (TextView) findViewById(R.id.userDefenseItemCount);
    	TextView miscItemsText = (TextView) findViewById(R.id.userMiscItemCount);
    	String format = getString(R.string.count);
    	
    	attackItemsText.setText(String.format(format, this.currentUser.getAttackItemCount()));
		miscItemsText.setText(String.format(format, this.currentUser.getMiscItemCount()));
		defenceItemsText.setText(String.format(format, this.currentUser.getDefenseItemCount()));
		
		TextView attackItemsDurationText = (TextView) findViewById(R.id.userAttackItemDuration);
    	TextView defenceItemsDurationText = (TextView) findViewById(R.id.userDefenseItemDuration);
    	
    	attackItemsDurationText.setText(String.valueOf(this.currentUser.getAttackItemDuration()));
    	defenceItemsDurationText.setText(String.valueOf(this.currentUser.getDefenseItemDuration()));
    }
}
