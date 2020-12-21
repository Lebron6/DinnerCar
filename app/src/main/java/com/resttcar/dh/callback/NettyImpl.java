package com.resttcar.dh.callback;



public interface NettyImpl {

    int STATUS_CONNECT_SUCCESS = 1;

    int STATUS_CONNECT_CLOSED = 0;

    int STATUS_CONNECT_ERROR = 0;


    /**
     * 当接收到系统消息
     */
    void onMessageResponse(String msg);

    /**
     * 当服务状态发生变化时触发
     */
    void onServiceStatusConnectChanged(int statusCode);
}
