package com.wiesfight.activities;

import java.util.Locale;

import main.com.wiesfight.dto.User;
import main.com.wiesfight.dto.enums.CharacterClass;
import main.com.wiesfight.persistence.UserPersistence;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.wiesfight.R;
import com.wiesfight.enums.Items;
import com.wiesfight.managers.DialogManager;

public class ShopActivity extends Activity {
	private UserPersistence currentUserPer;
	private User currentUser;
	TextView coinsText;
	TextView attackItemsText;
	TextView miscItemsText;
	TextView defenceItemsText;
	ProgressDialog progressDialog;
	Boolean changes = false;
	
	private final int ATTACK_ITEM_PRICE = 2;
	private final int DEFENSE_ITEM_PRICE = 2;
	private final int MISC_ITEM_PRICE = 3;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop);
	
		ParseQuery<UserPersistence> query = ParseQuery.getQuery(UserPersistence.class);
		query.fromPin("currentUser");
		query.getFirstInBackground(new GetCallback<UserPersistence>() {
			public void done(UserPersistence user, ParseException e) {
				currentUserPer = user;
		        currentUser = user.getUser();
		        initializeFeedback();
				refreshFeedback();
			}
		});
	}
	
	protected void initializeFeedback() {
		this.coinsText = (TextView) findViewById(R.id.txtCoins);
		this.coinsText.setTextColor(Color.YELLOW);
		this.attackItemsText = (TextView) findViewById(R.id.txtAttackItem);
		this.miscItemsText = (TextView) findViewById(R.id.txtMiscItem);
		this.defenceItemsText = (TextView) findViewById(R.id.txtDefenceItem);
		TextView txt = (TextView) findViewById(R.id.txtDefenceItemPrice);
		txt.setTextColor(Color.YELLOW);
		txt.setText(this.getCoinsString(DEFENSE_ITEM_PRICE));
		txt = (TextView) findViewById(R.id.txtAttackItemPrice);
		txt.setTextColor(Color.YELLOW);
		txt.setText(this.getCoinsString(ATTACK_ITEM_PRICE));
		txt = (TextView) findViewById(R.id.txtMiscItemPrice);
		txt.setTextColor(Color.YELLOW);
		txt.setText(this.getCoinsString(MISC_ITEM_PRICE));
		ImageView img = (ImageView) findViewById(R.id.imgAvatarShopBig);
		CharacterClass charClass = this.currentUser.getUserClass();
    	String className = charClass.toString();
    	try {
    		img.setImageResource(R.drawable.class.getField(className.toLowerCase(Locale.ENGLISH) + "_big_fight").getInt(null));
    	}
    	catch(Exception e) {
    		
    	}
    	Items item = Items.values()[charClass.getAttackItemID()];
    	img = (ImageView) findViewById(R.id.imgAttackItem);
    	img.setImageResource(item.getImageFile());
    	img.setTag(item);
    	item = Items.values()[charClass.getDefenceItemID()];
    	img = (ImageView) findViewById(R.id.imgDefenceItem);
    	img.setImageResource(item.getImageFile());
    	img.setTag(item);
    	item = Items.values()[charClass.getMiscItemID()];
    	img = (ImageView) findViewById(R.id.imgMiscItem);
    	img.setImageResource(item.getImageFile());
    	img.setTag(item);
	}
	
	@SuppressLint("InflateParams")
	public void showDialog(View v) {
		Items item = (Items) v.getTag();
		final int viewId = v.getId();
		LayoutInflater inflater = this.getLayoutInflater();
	    View view = inflater.inflate(R.layout.dialog_yesno, null);
	    final AlertDialog dialog = new AlertDialog.Builder(this)
	    	.setView(view).create();
	    Button btn1 = (Button) view.findViewById(R.id.btnYes);
	    Button btn2 = (Button) view.findViewById(R.id.btnNo);
	    btn1.setVisibility(this.canAfford(viewId));
	    TextView txt = (TextView) view.findViewById(R.id.txtMessage);
	    txt.setTextAppearance(this, android.R.attr.textAppearanceSmall);
	    txt.setText(String.format(getString(R.string.itemFormat), getString(item.getDescription()), 
	    		item.getBonusType().getDescription(), item.getBonus(), item.getDuration()));
	    btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				changes = true;
				if(viewId == R.id.imgAttackItem)
					buyAttackItem();
				else if(viewId == R.id.imgDefenceItem)
					buyDefenceItem();
				else if(viewId == R.id.imgMiscItem)
					buyMiscItem();
				dialog.dismiss();
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
	
	private int canAfford(int viewId) {
		if(this.currentUser.getUserCoins() < ATTACK_ITEM_PRICE) {
	    	return View.INVISIBLE;
	    }
	    else if(this.currentUser.getUserCoins() < MISC_ITEM_PRICE && viewId == R.id.imgMiscItem) {
	    	return View.INVISIBLE;
	    }
		return View.VISIBLE;
	}

	private void buyAttackItem() {
		if (this.currentUser.getUserCoins() >= ATTACK_ITEM_PRICE) {
			this.currentUser.setUserCoins(currentUser.getUserCoins() - ATTACK_ITEM_PRICE);
			this.currentUser.addAttackItemCount();
			
			refreshFeedback();
		}
	}
	
	private void buyDefenceItem() {
		if (this.currentUser.getUserCoins() >= DEFENSE_ITEM_PRICE) {
			this.currentUser.setUserCoins(currentUser.getUserCoins() - DEFENSE_ITEM_PRICE);
			this.currentUser.addDefenceItemCount();
			
			refreshFeedback();
		}
	}
	
	private void buyMiscItem() {
		if (this.currentUser.getUserCoins() >= MISC_ITEM_PRICE) {
			this.currentUser.setUserCoins(currentUser.getUserCoins() - MISC_ITEM_PRICE);
			this.currentUser.addMiscItemCount();
			
			this.refreshFeedback();
		}
	}
	
	private void refreshFeedback() {
		if (this.currentUser != null) {
			this.coinsText.setText(this.getCoinsString(this.currentUser.getUserCoins()));
			this.attackItemsText.setText(this.currentUser.getAttackItemCount()+"");
			this.miscItemsText.setText(this.currentUser.getMiscItemCount()+"");
			this.defenceItemsText.setText(this.currentUser.getDefenseItemCount()+"");
		}
	}
	
	private String getCoinsString(int coins) {
    	String coinString = "monet";
    	int units = coins % 10;
    	int dozens = (coins % 100) / 10;
    	if(coins == 1)
    		coinString = "moneta";
    	else if((units == 2 || units == 3 || units == 4) && dozens != 1)
    		coinString = "monety";
		return coins + " " + coinString;
    }
    
    @Override
    public void onBackPressed() {
    	if(!this.changes) {
			setResult(RESULT_CANCELED);
			finish();
    	}
    	else {
	    	this.progressDialog = new ProgressDialog(this, AlertDialog.THEME_HOLO_DARK);
	    	this.progressDialog.setMessage(getString(R.string.progress));
	    	this.progressDialog.show();
	    	this.currentUserPer.setUser(this.currentUser);
			if(this.currentUserPer.saveUserToDB()) {
				this.progressDialog.dismiss();
				setResult(RESULT_OK);
				finish();
			}
			else {
				this.progressDialog.dismiss();
				DialogManager.showInfoDialog(this, getString(R.string.connectionError));
			}
    	}
    }
}
