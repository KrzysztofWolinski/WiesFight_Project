package com.wiesfight.activities;

import main.com.wiesfight.dto.User;
import main.com.wiesfight.persistence.UserPersistence;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode;
import com.shephertz.app42.gaming.multiplayer.client.events.AllRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.AllUsersEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LiveUserInfoEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.MatchedRoomsEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.ZoneRequestListener;
import com.wiesfight.R;
import com.wiesfight.managers.DialogManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PreFightActivity extends Activity implements ConnectionRequestListener, ZoneRequestListener {
	private WarpClient theClient;
    private boolean isConnected = false;
	private User currentUser;
    private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pre_fight);
		this.getUser();
	}
	
	private void getUser() {
		ParseQuery<UserPersistence> query = ParseQuery.getQuery(UserPersistence.class);
		query.fromPin("currentUser");
		query.getFirstInBackground(new GetCallback<UserPersistence>() {
			public void done(UserPersistence user, ParseException e) {
		        currentUser = user.getUser();
			}
		});
	}

	public void findOponent(View v) {
        if(this.currentUser.getUserCoins() < 5) {
            Toast toast = Toast.makeText(this, "Masz zbyt mało monet żeby grać! \nWymagana suma to 5 monet.", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            this.tryConnect();
        }
	}
	
	private void tryConnect() {
		try {
			this.progressDialog = new ProgressDialog(this, AlertDialog.THEME_HOLO_DARK);
			this.progressDialog.setMessage(getString(R.string.progress));
			this.progressDialog.show();
			if(!this.isConnected) {
	            this.theClient = WarpClient.getInstance();
	            this.theClient.addConnectionRequestListener(this);
				this.theClient.addZoneRequestListener(this);
	            this.theClient.connectWithUserName(this.currentUser.getUserName());
			}
			else {
				this.theClient.getRoomInRange(1, 1);
			}
        } catch (Exception ex) {
			this.error();
        }
	}

	private void hideProgressDialog() {
		if(this.progressDialog != null){
			this.progressDialog.dismiss();
			this.progressDialog = null;
		}
	}

	public void startTraining(View v) {
		Intent intent = new Intent(this, FightActivity.class);
		intent.putExtra("training", true);
    	startActivity(intent);
	}

	private void joinRoom(String roomId, boolean newRoom) {
		if(roomId != null && roomId.length() > 0) {
			this.goToGameScreen(roomId, newRoom);
		} else {
			this.error();
		}
	}
	
	private void goToGameScreen(String roomId, boolean newRoom) {
		this.hideProgressDialog();
		Intent intent = new Intent(this, FightActivity.class);
		intent.putExtra("roomId", roomId);
		intent.putExtra("newRoom", newRoom);
		startActivity(intent);
	}
	
	private void error() {
		this.hideProgressDialog();
		DialogManager.showInfoDialog(this, getString(R.string.connectionError));
	}

	@Override
	public void onConnectDone(final ConnectEvent event) {
		if(event.getResult() == WarpResponseResultCode.SUCCESS){
			isConnected = true;
			this.theClient.getRoomInRange(1, 1);
		}else{
			isConnected = false;
			this.error();
		}
	}

	@Override
	public void onGetMatchedRoomsDone(MatchedRoomsEvent event) {
		RoomData[] roomDataList = event.getRoomsData();
		if(roomDataList != null && roomDataList.length > 0) {
			this.joinRoom(roomDataList[0].getId(), false);
		} else {
			this.theClient.createRoom(this.currentUser.getUserName(), this.currentUser.getUserName(), 2, null);
		}
	}

	@Override
	public void onCreateRoomDone(RoomEvent event) {
    	if(event.getResult() == WarpResponseResultCode.SUCCESS){
			String roomId = event.getData().getId();
			this.joinRoom(roomId, true);
		} else {
			this.error();
		}
	}

	@Override
	public void onDisconnectDone(ConnectEvent arg0) {
		this.isConnected = false;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if(this.theClient != null && this.isConnected){
			this.theClient.removeZoneRequestListener(this);
			this.theClient.removeConnectionRequestListener(this);
			this.theClient.disconnect();
		}
	}

	@Override
	public void onInitUDPDone(byte arg0) {
		
	}

	@Override
	public void onDeleteRoomDone(RoomEvent arg0) {
		
	}

	@Override
	public void onGetAllRoomsDone(AllRoomsEvent event) {
	}

	@Override
	public void onGetLiveUserInfoDone(LiveUserInfoEvent arg0) {
		
	}

	@Override
	public void onGetOnlineUsersDone(AllUsersEvent event) {
	}

	@Override
	public void onSetCustomUserDataDone(LiveUserInfoEvent arg0) {
		
	}
}
