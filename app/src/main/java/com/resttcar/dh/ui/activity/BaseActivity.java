package com.resttcar.dh.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.callback.NettyImpl;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.CheckOrderResult;
import com.resttcar.dh.entity.MessageWrap;
import com.resttcar.dh.entity.Ping;
import com.resttcar.dh.entity.SocketLogin;
import com.resttcar.dh.socket.JWebSocketClient;
import com.resttcar.dh.tools.AppManager;
import com.resttcar.dh.tools.PreferenceUtils;
import com.resttcar.dh.tools.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.resttcar.dh.api.Constant.SOCKET_URI;

public abstract class BaseActivity extends AppCompatActivity implements NettyImpl {
    /**
     * activity堆栈管理
     */
    protected AppManager appManager = AppManager.getAppManager();

    protected ImmersionBar mImmersionBar;
    private InputMethodManager imm;
    private Unbinder unBinder;
    protected String TAG;
    protected String userToken;
    protected String userId;
    protected boolean useEventBus=false;
    private JWebSocketClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (attachLayoutRes() != 0) {
            setContentView(attachLayoutRes());
            unBinder = ButterKnife.bind(this);
        }

        if (isImmersionBarEnabled())
            initImmersionBar();
        userToken= PreferenceUtils.getInstance().getUserToken();
        userId= PreferenceUtils.getInstance().getUserId();
        initViews();
        initDatas();
        connectSocket();
        loggerSimpleName();

        if (useEventBus==true){
            EventBus.getDefault().register(this);
        }
        appManager.addActivity(this);
        mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//开启心跳检测
    }


    public void connectSocket() {
        URI uri = URI.create(SOCKET_URI);
        client = new JWebSocketClient(uri,this) ;
        try {
            client.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    public void loggerSimpleName() {
        TAG=getClass().getSimpleName();
        Log.d("当前所处界面 ：", TAG);
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initTitle();
    }

    /**
     * 初始化视图控件
     */
    protected abstract void initTitle();


    /**
     * 绑定布局文件
     * @return 布局文件ID
     */
    @LayoutRes
    protected abstract int attachLayoutRes();

    /**
     * 初始化视图控件
     */
    protected abstract void initViews();

    /**
     * 获取数据
     */
    protected abstract void initDatas();

    public void finish() {
        super.finish();
        hideSoftKeyBoard();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (attachLayoutRes() != 0) {
            unBinder.unbind();
        }
        this.imm = null;
        // 从栈中移除activity
        appManager.finishActivity(this);
        if (useEventBus==true){
            EventBus.getDefault().unregister(this);
        }
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //在BaseActivity里销毁
        if (timer!=null){
            timer.cancel();
        }
    }

    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.imm == null) {
            this.imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.imm != null)) {
            this.imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }

    @Override
    public void onMessageResponse(final String msg) {
        try {
            JSONObject jsonObject=new JSONObject(msg);
            String type=jsonObject.getString("type");
            if (type.equals("ping")){
                Ping ping=new Ping();
                ping.setType("pang");
                client.send(new Gson().toJson(ping));
            }else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(MessageWrap.getInstance(msg));
                    }
                });
            }
        }catch (Exception e){
        }
    }
    private Timer timer;
    @Override
    public void onServiceStatusConnectChanged(final int statusCode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (statusCode == NettyImpl.STATUS_CONNECT_SUCCESS) {
                    SocketLogin sl=new SocketLogin();
                    sl.setGroup_id(PreferenceUtils.getInstance().getGroupId());
                    sl.setUid(PreferenceUtils.getInstance().getUserId());
                    sl.setType("login");
                    client.send(new Gson().toJson(sl));
                } else {
                    Log.e("socket连接失败","...");
//                    timer = new Timer();
//                    timer.scheduleAtFixedRate(new TimerTask() {
//                        @Override
//                        public void run() {
//
//                            client.reconnect();
//                        }
//                    },1000,10000);

                }
            }
        });
    }

    private static final long HEART_BEAT_RATE = 10 * 1000;//每隔10秒进行一次对长连接的心跳检测
    private Handler mHandler = new Handler();
    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            if (client != null) {
                if (client.isClosed()) {
                    reconnectWs();
                }
            } else {
                //如果client已为空，重新初始化websocket
                URI uri = URI.create(SOCKET_URI);
                client = new JWebSocketClient(uri,BaseActivity.this) ;
            }
            //定时对长连接进行心跳检测
            mHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    /**
     * 开启重连
     */
    private void reconnectWs() {
        mHandler.removeCallbacks(heartBeatRunnable);
        new Thread() {
            @Override
            public void run() {
                try {
                    //重连
                    if (!TextUtils.isEmpty(PreferenceUtils.getInstance().getUserToken())){
                        client.reconnectBlocking();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
