package com.resttcar.dh.app;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by James on 2020/4/7.
 */
public class DiningCarAPP extends Application {

    private static Context application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }
    public static Context getApplication(){
        return application;
    }
  public static String PATH_LOGCAT;

}
