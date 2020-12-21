package com.resttcar.dh.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.GetPanelConfig;
import com.resttcar.dh.entity.MessageWrap;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.widget.GlideCircleTransform;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.resttcar.dh.api.Constant.CHANGE_STATUS;

/**
 * Created by James on 2020/4/22.
 * 现场营业状态设置
 */
public class SceneBusinessSettActivity extends BaseActivity {

    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_close)
    TextView tvClose;
    @BindView(R.id.tv_content)
    TextView tvContent;
    ApiResponse<GetPanelConfig> getPanelConfigData;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SceneBusinessSettActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_buss_set;
    }

    @Override
    protected void initViews() {
        mImmersionBar.transparentStatusBar().statusBarAlpha(0.2f).init();
    }

    @Override
    protected void initDatas() {
        HttpUtil.createRequest(TAG, BaseUrl.getInstence().getPanelCinfig()).getPanelConfig(userToken).enqueue(new Callback<ApiResponse<GetPanelConfig>>() {
            @Override
            public void onResponse(Call<ApiResponse<GetPanelConfig>> call, Response<ApiResponse<GetPanelConfig>> response) {
                if (response.body().getCode() == 1) {
                    getPanelConfigData = response.body();
                    Glide.with(SceneBusinessSettActivity.this).load(BaseUrl.getInstence().ipAddress + getPanelConfigData.getData().getCar().getCar_avatar()).bitmapTransform(new GlideCircleTransform(SceneBusinessSettActivity.this)).into(ivPic);
                    tvStatus.setText(getPanelConfigData.getData().getCar().getStatus() == 1 ? "停止接单中" : "正常接单中");
                    tvContent.setText(getPanelConfigData.getData().getCar().getStatus() == 1 ? "正常营业，并接收新订单。" : "打烊后，餐车将无法再接收新订单。");
                    tvClose.setText(getPanelConfigData.getData().getCar().getStatus() == 1 ? "恢复营业" : "打烊");
                    tvClose.setBackground(getPanelConfigData.getData().getCar().getStatus() == 1 ? getResources().getDrawable(R.drawable.bg_open_shop) : getResources().getDrawable(R.drawable.bg_close_shop));
                    tvClose.setTextColor(getPanelConfigData.getData().getCar().getStatus() == 1 ? getResources().getColor(R.color.colorGreen) : getResources().getColor(R.color.colorMain));

                } else {
                    ToastUtil.showToast(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<GetPanelConfig>> call, Throwable t) {
                ToastUtil.showToast("网络异常:获取营业状态失败");
            }
        });
    }

    @OnClick(R.id.tv_close)
    public void onViewClicked() {
        HttpUtil.createRequest(TAG, BaseUrl.getInstence().panelCinfig()).cimmitPanelConfig(userToken,
                getPanelConfigData.getData().getCar().getStatus()==1?2:1).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body().getCode() == 1) {
                    ToastUtil.showToast("营业状态已修改");

                    finish();
                } else {
                    ToastUtil.showToast(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                ToastUtil.showToast("网络异常:状态修改失败");
            }
        });
    }
}
