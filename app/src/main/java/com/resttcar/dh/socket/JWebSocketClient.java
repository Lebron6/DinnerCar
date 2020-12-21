package com.resttcar.dh.socket;

import android.util.Log;

import com.resttcar.dh.callback.NettyImpl;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class JWebSocketClient extends WebSocketClient {
    private NettyImpl listener;

    public JWebSocketClient(URI serverUri) {
        super(serverUri, new Draft_6455());
    }
    public JWebSocketClient(URI serverUri, NettyImpl listener) {
        super(serverUri, new Draft_6455());
        this.listener=listener;
    }
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.e("JWebSocketClient", "onOpen()");
        listener.onServiceStatusConnectChanged(NettyImpl.STATUS_CONNECT_SUCCESS);
    }

    @Override
    public void onMessage(String message) {
        Log.e("JWebSocketClient", "onMessage()");
        listener.onMessageResponse(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        Log.e("JWebSocketClient", "onClose()");
        listener.onServiceStatusConnectChanged(NettyImpl.STATUS_CONNECT_CLOSED);
    }

    @Override
    public void onError(Exception ex) {
        Log.e("JWebSocketClient", "onError()");
        listener.onServiceStatusConnectChanged(NettyImpl.STATUS_CONNECT_ERROR);
    }
}