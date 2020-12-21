package com.resttcar.dh.jpush;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.resttcar.dh.R;
import com.resttcar.dh.entity.Extras;
import com.resttcar.dh.entity.MessageWrap;
import com.resttcar.dh.ui.activity.MainActivity;
import com.resttcar.dh.ui.activity.NoticeActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.CmdMessage;
import cn.jpush.android.api.CustomMessage;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.api.NotificationMessage;
import cn.jpush.android.service.JPushMessageReceiver;

import static android.content.Context.NOTIFICATION_SERVICE;

public class PushMessageReceiver extends JPushMessageReceiver{
    private static final String TAG = "PushMessageReceiver";
    private static final int NOTIFICATION_SHOW_SHOW_AT_MOST = 1;   //推送通知最多显示条数
    @Override
    public void onMessage(Context context, CustomMessage customMessage) {
        Log.e(TAG,"[onMessage] "+new Gson().toJson(customMessage));
        processCustomMessage(context,customMessage);
    }

    @Override
    public void onNotifyMessageOpened(Context context, NotificationMessage message) {
        Log.e(TAG,"[onNotifyMessageOpened] "+message);
        Extras extras=new Gson().fromJson(message.notificationExtras,Extras.class);
        try{
            //打开自定义的Activity
            Intent i = new Intent();
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
            if (extras.getType().equals("order")){
                i.setClass(context,MainActivity.class);
            }else{
                i.setClass(context,NoticeActivity.class);
            }
            context.startActivity(i);
        }catch (Throwable throwable){

        }

    }

    @Override
    public void onMultiActionClicked(Context context, Intent intent) {
        Log.e(TAG, "[onMultiActionClicked] 用户点击了通知栏按钮");
        String nActionExtra = intent.getExtras().getString(JPushInterface.EXTRA_NOTIFICATION_ACTION_EXTRA);

        //开发者根据不同 Action 携带的 extra 字段来分配不同的动作。
        if(nActionExtra==null){
            Log.d(TAG,"ACTION_NOTIFICATION_CLICK_ACTION nActionExtra is null");
            return;
        }
        if (nActionExtra.equals("my_extra1")) {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮一");
        } else if (nActionExtra.equals("my_extra2")) {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮二");
        } else if (nActionExtra.equals("my_extra3")) {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮三");
        } else {
            Log.e(TAG, "[onMultiActionClicked] 用户点击通知栏按钮未定义");
        }
    }

    @Override
    public void onNotifyMessageArrived(Context context, NotificationMessage message) {
        Log.e(TAG,"[onNotifyMessageArrived] "+message);
        JPushInterface.clearAllNotifications(context);
        processCustomMessage(context,message);
    }

    @Override
    public void onNotifyMessageDismiss(Context context, NotificationMessage message) {
        Log.e(TAG,"[onNotifyMessageDismiss] "+message);
    }

    @Override
    public void onRegister(Context context, String registrationId) {
        Log.e(TAG,"[onRegister] "+registrationId);
    }

    @Override
    public void onConnected(Context context, boolean isConnected) {
        Log.e(TAG,"[onConnected] "+isConnected);
    }

    @Override
    public void onCommandResult(Context context, CmdMessage cmdMessage) {
        Log.e(TAG,"[onCommandResult] "+cmdMessage);
    }



    //send msg to MainActivity
    private void processCustomMessage(Context context, CustomMessage customMessage) {
        EventBus.getDefault().post(MessageWrap.getInstance(customMessage.extra));
    }

    @Override
    public void onNotificationSettingsCheck(Context context, boolean isOn, int source) {
        super.onNotificationSettingsCheck(context, isOn, source);
        Log.e(TAG,"[onNotificationSettingsCheck] isOn:"+isOn+",source:"+source);
    }
    /**
     * 实现自定义推送声音
     * @param context
     */
    private void processCustomMessage(Context context, NotificationMessage notificationMessage) {

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context);
        String title = notificationMessage.notificationTitle;
        String msg = notificationMessage.notificationContent;
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
        Extras extras=new Gson().fromJson(notificationMessage.notificationExtras,Extras.class);
        Intent mIntent = new Intent();
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        try{
            if (extras.getType().equals("order")){
                Log.e("通知订单","-----");
                notification.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" +R.raw.new_order));
                mIntent.setClass(context,MainActivity.class);
            }else{
                notification.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" +R.raw.msg));
                mIntent.setClass(context,NoticeActivity.class);
            }
        }catch (Throwable throwable){
            Log.e("通知解析异常","-----");
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0);

        notification.setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentText(msg)
                .setContentTitle(title.equals("") ? "title": title)
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(bitmap)
                .setNumber(NOTIFICATION_SHOW_SHOW_AT_MOST);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_SHOW_SHOW_AT_MOST, notification.build());  //id随意，正好使用定义的常量做id，0除外，0为默认的Notification
    }
}
