package com.resttcar.dh.tools;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.resttcar.dh.R;
import com.resttcar.dh.app.DiningCarAPP;

/**
 * Created by Administrator on 2017/2/9.
 */

public class LoadingManger {
    private static LoadingManger loadingManger;
    private View view;
    private View error;
    private TextView error_msg;
    private LinearLayout loading;
    private Animation operatingAnim;
    private View result;
    private ImageView imgLoading;

    /**
     * 获取一个实例
     * @return
     */
    public static LoadingManger getInsetance() {
        if (loadingManger==null){
            loadingManger = new LoadingManger();
        }
        return loadingManger;
    }


    /**
     * 设置view
     */
    public void setView(View v) {
        this.view = v;
        error = v.findViewById(R.id.error);
        result = v.findViewById(R.id.res);
        error_msg = (TextView)v.findViewById(R.id.msg);
        loading = (LinearLayout)v.findViewById(R.id.layout_loading);
        imgLoading = (ImageView) v.findViewById(R.id.loading);
    }

    /**
     * 开始加载
     */
    public void startLoading() {
        if(loading.getVisibility()== View.GONE){
            error.setVisibility(View.VISIBLE);
            result.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            operatingAnim = AnimationUtils.loadAnimation(DiningCarAPP.getApplication(), R.anim.xuanzhuan);
            LinearInterpolator lin = new LinearInterpolator();
            operatingAnim.setInterpolator(lin);
        imgLoading.startAnimation(operatingAnim);
        }
    }

    /**
     * 结束加载
     */
    public void stopFinishLoading(boolean loadResult){
        stopFinishLoading("加载成功",loadResult);
    }

    public void stopFinishLoading(String text, boolean loadSuccess) {
        if(loadSuccess==true){
            error.setVisibility(View.GONE);
            result.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            if(imgLoading!=null){
                imgLoading.clearAnimation();
            }
        }else{
            error.setVisibility(View.VISIBLE);
            result.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            if(imgLoading!=null){
                imgLoading.clearAnimation();
            }
            error_msg.setText(text);
        }
    }

}
