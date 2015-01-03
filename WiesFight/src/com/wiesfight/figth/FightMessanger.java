package com.wiesfight.figth;

public class FightMessanger implements IFightMessanger {
    private Fight callback;

    public FightMessanger(Fight callback) {
        this.callback = callback;

        initializeConnection();
    }

    // Interface do wysyłania danych po wykonaniu ataku
    public void sendData(PlayerActions actions) {
        // TODO unmock
        callback.receivePlayerActions(actions);
    }

    private void onDataReceived() {
        // TODO unmock
        //callback.switchPlayers();
    }

    private void initializeConnection() {
        // TODO implement
    }
}
