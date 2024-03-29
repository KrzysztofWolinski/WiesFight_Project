package com.wiesfight.figth;

import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shephertz.app42.gaming.multiplayer.client.WarpClient;
import com.shephertz.app42.gaming.multiplayer.client.events.ChatEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.LobbyData;
import com.shephertz.app42.gaming.multiplayer.client.events.MoveEvent;
import com.shephertz.app42.gaming.multiplayer.client.events.RoomData;
import com.shephertz.app42.gaming.multiplayer.client.events.UpdateEvent;
import com.shephertz.app42.gaming.multiplayer.client.listener.ChatRequestListener;
import com.shephertz.app42.gaming.multiplayer.client.listener.NotifyListener;

public class FightMessanger implements IFightMessanger, NotifyListener, ChatRequestListener {
    private Fight callback;
	private WarpClient theClient;
	private String opponentName;

    public FightMessanger(Fight callback, String opponentName) {
        this.callback = callback;
        this.opponentName = opponentName;
        initializeConnection();
    }

    // Interface do wysyłania danych po wykonaniu ataku
    public void sendData(PlayerAction actions) {
        this.theClient.sendChat(actions.toString());
    }

    private void onDataReceived(String message) {
    	GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        PlayerAction action = gson.fromJson(message, PlayerAction.class);
        if(action == null) {
        	// TODO  blad
        }
        else {
            callback.applyOpponentAction(action);
        }
    }

    private void initializeConnection() {
        try {
			this.theClient = WarpClient.getInstance();
			this.theClient.addNotificationListener(this);
			this.theClient.addChatRequestListener(this);
		} catch (Exception e) {
			
		}
    }

	@Override
	public void onChatReceived(ChatEvent event) {
		String sender = event.getSender();
		if(sender.equals(this.opponentName)) {
    		this.onDataReceived(event.getMessage());
		}
	}

	@Override
	public void onSendChatDone(byte result) {
		if(result == 0) {
			this.callback.applyCurrentAction();
		}
		else {
			// TODO blad wyslania wiadomosci, mozna poprosic o sprobowanie ponownie
		}
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
	public void onRoomDestroyed(RoomData arg0) {
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
	public void onUserJoinedRoom(RoomData arg0, String arg1) {
	}

	@Override
	public void onUserLeftLobby(LobbyData arg0, String arg1) {
	}

	@Override
	public void onUserLeftRoom(RoomData arg0, String arg1) {
	}

	@Override
	public void onUserPaused(String arg0, boolean arg1, String arg2) {
	}

	@Override
	public void onUserResumed(String arg0, boolean arg1, String arg2) {
	}

	@Override
	public void onSendPrivateChatDone(byte arg0) {
	}
}
