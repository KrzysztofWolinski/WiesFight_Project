package com.wiesfight.activities;

import java.util.HashMap;
import java.util.Locale;

import main.com.wiesfight.persistence.UserPersistence;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.events.ChatEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LobbyData;
import com.shephertz.app42.gaming.multiplayer.client.events.MoveEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.UpdateEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.NotifyListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener;
import com.wiesfight.R;
import com.wiesfight.enums.Items;
import com.wiesfight.enums.PlayerActions;
import com.wiesfight.figth.Animator;
import com.wiesfight.figth.Fight;
import com.wiesfight.objects.Fighter;
import com.wiesfight.objects.IFighter;
import com.wiesfight.objects.TrainingOpponent;

@SuppressLint("InflateParams")
public class FightActivity extends Activity implements RoomRequestListener, NotifyListener, Animator {
	private boolean training = false;
	private IFighter currentUser;
	private IFighter opponent;
	private Fight fight;
    private ProgressDialog progressDialog;
	private WarpClient theClient;
	private boolean isOwner = false;
	private boolean opponentLeft = false;
	private String roomId = "";
	
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
				initializeRoom();
			}
		});
	}
	
	protected void initializeRoom() {
		this.addPlayer();
		if(this.training) {
			this.opponent = new TrainingOpponent();
			this.addOpponent();
			this.fight = new Fight(this.currentUser, this.opponent, this, true);
			updateBattlefield();
		}
		else {
			this.roomId = getIntent().getStringExtra("roomId");
			this.isOwner = getIntent().getBooleanExtra("newRoom", false);
			try {
				this.progressDialog = new ProgressDialog(this, AlertDialog.THEME_HOLO_DARK);
				this.progressDialog.setMessage(getString(isOwner ? R.string.waitOpponent : R.string.progress));
				this.progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						hideProgressDialog();
					}
				});
				this.progressDialog.show();
	            this.theClient = WarpClient.getInstance();
	            this.theClient.addRoomRequestListener(this);
	            this.theClient.addNotificationListener(this);
	            this.theClient.joinRoom(this.roomId);
	        } catch (Exception ex) {
				this.error();
	        }
			
		}
	}
	
	private void error() {
		runOnUiThread(new Runnable() {
			@Override
	    	public void run() {
				hideProgressDialog();
				LayoutInflater inflater = FightActivity.this.getLayoutInflater();
			    View view = inflater.inflate(R.layout.dialog_ok, null);
			    final AlertDialog dialog = new AlertDialog.Builder(FightActivity.this)
			    	.setView(view).create();
			    Button btn1 = (Button) view.findViewById(R.id.btnOk);
			    TextView txt = (TextView) view.findViewById(R.id.txtMessageOk);
			    txt.setText(getString(R.string.connectionError));
			    btn1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						FightActivity.this.finish();
					}
				});
			    if (!FightActivity.this.isFinishing()) {
			    	dialog.show();
			    }
			}
		});
	}

	private void hideProgressDialog() {
		if(this.progressDialog != null){
			this.progressDialog.dismiss();
			this.progressDialog = null;
		}
	}
	
	protected void addPlayer() {
		String userClassName = currentUser.getUserClass().toString();
		
    	try {
    		ImageView img = (ImageView) findViewById(R.id.imgAvatarUser);
    		img.setImageResource(R.drawable.class.getField(userClassName.toLowerCase(Locale.ENGLISH)).getInt(null));
    		
    		img = (ImageView) findViewById(R.id.userCharacter);
    		img.setImageResource(R.drawable.class.getField(currentUser.getUserClass().toString().toLowerCase(Locale.ENGLISH) + "_big_fight").getInt(null));
    		
    		Items item = Items.values()[currentUser.getUserClass().getAttackItemID()];
        	img = (ImageView) findViewById(R.id.userAttackItem);
        	img.setImageResource(item.getImageFile());
        	
        	item = Items.values()[currentUser.getUserClass().getDefenceItemID()];
        	img = (ImageView) findViewById(R.id.userDefenseItem);
        	img.setImageResource(item.getImageFile());
        	
        	item = Items.values()[currentUser.getUserClass().getMiscItemID()];
        	img = (ImageView) findViewById(R.id.userMiscItem);
        	img.setImageResource(item.getImageFile());
    	}
    	catch(Exception e) {
    		
    	}
	}
	
	protected void addOpponent() {
		String opponentClassName = opponent.getUserClass().toString();
		
    	try {
    		ImageView img = (ImageView) findViewById(R.id.imgAvatarOpponent);
    		img.setImageResource(R.drawable.class.getField(opponentClassName.toLowerCase(Locale.ENGLISH)).getInt(null));
    		
    		img = (ImageView) findViewById(R.id.opponentCharacter);
    		img.setImageResource(R.drawable.class.getField(opponent.getUserClass().toString().toLowerCase(Locale.ENGLISH) + "_big_fight").getInt(null));
        	
    		Items item = Items.values()[opponent.getUserClass().getAttackItemID()];
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
    	if(this.opponent == null || this.training || this.opponentLeft) {
			this.handleLeave();
			super.onBackPressed();
    	}
    	else {
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
					handleLeave();
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
    }
    
    protected void handleLeave() {
    	if(!this.training) {
    		this.theClient.leaveRoom(this.roomId);
    		this.theClient.unsubscribeRoom(this.roomId);
    		this.theClient.removeRoomRequestListener(this);
    		this.theClient.removeNotificationListener(this);
			if(this.isOwner) {
				this.theClient.deleteRoom(this.roomId);
			}
		}
	}

	public void onPressAttackButton(View v) {
    	fight.attack();
    }
    
    public void onPressAttackItemButton(View v) {
    	if (this.currentUser.useAttackItem()) {
            this.fight.useItem(PlayerActions.USED_ATTACK_ITEM);
            this.updateBattlefield();
        }
    }
    
    public void onPressDefenseItemButton(View v) {
    	if (this.currentUser.useDefenseItem()) {
            this.fight.useItem(PlayerActions.USED_DEFENSE_ITEM);
            this.updateBattlefield();
        }
    }

    public void onPressMiscItemButton(View v) {
    	if (this.currentUser.useMiscItem()) {
            this.fight.useItem(PlayerActions.USED_MISC_ITEM);
            this.updateBattlefield();
        }
    }
    
    public void updateBattlefield() {
    	runOnUiThread(new Runnable() {
			@Override
	    	public void run() {
		    	// Sprawdzić czy walka ciągle trwa i zaaktualizować feedback (HP, itemy itd.)
		    	updateHpBars();
		    	
		    	updateItemNotifications();
		    	
		    	if (fight.isFightFinished() || opponentLeft) {
		    		handleFinish();
		    	}
			}
    	});
    }
    
    private void handleFinish() {
    	if(!this.training) {
    		
    	}
    	LayoutInflater inflater = getLayoutInflater();
	    View view = inflater.inflate(R.layout.dialog_ok, null);
	    final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();

	    TextView txt = (TextView) view.findViewById(R.id.txtMessageOk);
	    txt.setText(String.format(getString(R.string.fightWon), opponentLeft ? getString(R.string.opponentLeft) : ""
	    	,fight.getWinner(opponentLeft).getName()));

	    Button btn = (Button) view.findViewById(R.id.btnOk);
	    btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				handleLeave();
				FightActivity.this.finish();
			}
		});
	    dialog.show();
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

    @Override
    public void animatePlayerAttacking(boolean isCriticalStrike) {
        try {
            String playerClassName = this.currentUser.getUserClass().toString();
            ImageView img = (ImageView) findViewById(R.id.userCharacter);
            img.setImageResource(R.drawable.class.getField(playerClassName.toLowerCase(Locale.ENGLISH) + "_big_fight_attacking").getInt(null));

            new CountDownTimer(500, 100) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    String playerClassName = currentUser.getUserClass().toString();
                    ImageView img = (ImageView) findViewById(R.id.userCharacter);
                    try {
                        img.setImageResource(R.drawable.class.getField(playerClassName.toLowerCase(Locale.ENGLISH) + "_big_fight").getInt(null));
                    } catch (Exception e) {

                    }
                }
            }.start();
        } catch(Exception e) {
        }
    }

    @Override
    public void animatePlayerGettingHit() {
        try {
            String playerClassName = this.currentUser.getUserClass().toString();
            ImageView img = (ImageView) findViewById(R.id.userCharacter);
            img.setImageResource(R.drawable.class.getField(playerClassName.toLowerCase(Locale.ENGLISH) + "_big_fight_getting_hit").getInt(null));

            new CountDownTimer(500, 100) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    String playerClassName = currentUser.getUserClass().toString();
                    ImageView img = (ImageView) findViewById(R.id.userCharacter);
                    try {
                        img.setImageResource(R.drawable.class.getField(playerClassName.toLowerCase(Locale.ENGLISH) + "_big_fight").getInt(null));
                    } catch (Exception e) {

                    }
                }
            }.start();
        } catch(Exception e) {
        }
    }

    @Override
    public void animateOpponentAttacking(boolean isCriticalStrike) {
        try {
            String playerClassName = this.opponent.getUserClass().toString();
            ImageView img = (ImageView) findViewById(R.id.opponentCharacter);
            img.setImageResource(R.drawable.class.getField(playerClassName.toLowerCase(Locale.ENGLISH) + "_big_fight_attacking").getInt(null));

            new CountDownTimer(500, 100) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    String playerClassName = opponent.getUserClass().toString();
                    ImageView img = (ImageView) findViewById(R.id.opponentCharacter);
                    try {
                        img.setImageResource(R.drawable.class.getField(playerClassName.toLowerCase(Locale.ENGLISH) + "_big_fight").getInt(null));
                    } catch (Exception e) {

                    }
                }
            }.start();
        } catch(Exception e) {
        }
    }

    @Override
    public void animateOpponentGettingHit() {
        try {
            String playerClassName = this.opponent.getUserClass().toString();
            ImageView img = (ImageView) findViewById(R.id.opponentCharacter);
            img.setImageResource(R.drawable.class.getField(playerClassName.toLowerCase(Locale.ENGLISH) + "_big_fight_getting_hit").getInt(null));

            new CountDownTimer(500, 100) {
                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    String playerClassName = opponent.getUserClass().toString();
                    ImageView img = (ImageView) findViewById(R.id.opponentCharacter);
                    try {
                        img.setImageResource(R.drawable.class.getField(playerClassName.toLowerCase(Locale.ENGLISH) + "_big_fight").getInt(null));
                    } catch (Exception e) {

                    }
                }
            }.start();
        } catch(Exception e) {
        }
    }
    
    private void findOpponent(String username) {
    	if(findUser(username)) {
    		runOnUiThread(new Runnable() {
    			@Override
		    	public void run() {
					hideProgressDialog();
					addOpponent();
					currentUser.addFight();
					fight = new Fight(currentUser, opponent, FightActivity.this, isOwner);
					updateBattlefield();
    		     }
    		});
		}
		else {
			error();
		}
    }
    
    private boolean findUser(String username) {
    	ParseQuery<UserPersistence> query = ParseQuery.getQuery(UserPersistence.class);
    	query.whereEqualTo("Username", username);
    	try {
    		UserPersistence user = query.getFirst();
    		if(user != null) {
	    		this.opponent = new Fighter(user.loadUserFromDB());
    		}
    		return user != null;
    	}
    	catch(ParseException e) {
    		return false;
    	}
	}

	@Override
	public void onJoinRoomDone(RoomEvent event) {
		if(event.getResult() != 0 ) {
			this.error();
			return;
		}
		this.theClient.subscribeRoom(this.roomId);
	}

	@Override
	public void onSubscribeRoomDone(RoomEvent event) {
		if(event.getResult() != 0){
			this.error();
			return;
		}
		if(!this.isOwner) {
			String roomOwner = event.getData().getRoomOwner();
			this.findOpponent(roomOwner);
		}
	}

	@Override
	public void onUserJoinedRoom(RoomData data, String name) {
		this.findOpponent(name);
	}

	@Override
	public void onUserLeftRoom(RoomData data, String name) {
		runOnUiThread(new Runnable() {
			@Override
	    	public void run() {
				if(!fight.isFightFinished()) {
					opponentLeft = true;
					updateBattlefield();
				}
			}
		});
	}

	@Override
	public void onChatReceived(final ChatEvent event) {
	}

	@Override
	public void onRoomDestroyed(RoomData data) {
	}

	@Override
	public void onGameStarted(String arg0, String arg1, String arg2) {
	}

	@Override
	public void onGameStopped(String arg0, String arg1) {
	}

	@Override
	public void onMoveCompleted(MoveEvent arg0) {
	}

	@Override
	public void onNextTurnRequest(String arg0) {
	}

	@Override
	public void onPrivateChatReceived(String arg0, String arg1) {
	}

	@Override
	public void onPrivateUpdateReceived(String arg0, byte[] arg1, boolean arg2) {
	}

	@Override
	public void onRoomCreated(RoomData arg0) {
	}

	@Override
	public void onUpdatePeersReceived(UpdateEvent arg0) {
	}

	@Override
	public void onUserChangeRoomProperty(RoomData arg0, String arg1,
			HashMap<String, Object> arg2, HashMap<String, String> arg3) {
	}

	@Override
	public void onUserJoinedLobby(LobbyData arg0, String arg1) {
	}

	@Override
	public void onUserLeftLobby(LobbyData arg0, String arg1) {
	}

	@Override
	public void onUserPaused(String arg0, boolean arg1, String arg2) {
	}

	@Override
	public void onUserResumed(String arg0, boolean arg1, String arg2) {
	}

	@Override
	public void onGetLiveRoomInfoDone(LiveRoomInfoEvent arg0) {
	}

	@Override
	public void onLeaveRoomDone(RoomEvent arg0) {
	}

	@Override
	public void onLockPropertiesDone(byte arg0) {
	}

	@Override
	public void onSetCustomRoomDataDone(LiveRoomInfoEvent arg0) {
	}

	@Override
	public void onUnSubscribeRoomDone(RoomEvent arg0) {
	}

	@Override
	public void onUnlockPropertiesDone(byte arg0) {
	}

	@Override
	public void onUpdatePropertyDone(LiveRoomInfoEvent arg0) {
	}
}
