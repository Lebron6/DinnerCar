package com.resttcar.dh.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.callback.OnTypeSelectImp;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.GetPanelConfig;
import com.resttcar.dh.entity.MessageWrap;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.widget.GlideCircleTransform;
import com.resttcar.dh.widget.TitleManger;
import com.resttcar.dh.widget.TypeSelectWindow;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.resttcar.dh.api.Constant.CHANGE_STATUS;

/**
 * Created by James on 2020/4/22.
 * 外卖状态设置
 */
public class TakeOutBusinessSettActivity extends BaseActivity implements OnTypeSelectImp {

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
    @BindView(R.id.tv_send_type)
    TextView tvSendType;
    @BindView(R.id.et_send_round)
    EditText etSendRound;
    @BindView(R.id.et_send_price)
    EditText etSendPrice;
    @BindView(R.id.tv_close)
    TextView tvClose;
    @BindView(R.id.tv_content)
    TextView tvContent;
    ApiResponse<GetPanelConfig> getPanelConfigData;
    private List<String> types;
    private ArrayAdapter arrayAdapter;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, TakeOutBusinessSettActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleManger manger = TitleManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("外卖状态");
        manger.setBack();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_take_out_set;
    }

    @Override
    protected void initViews() {
        mImmersionBar.transparentStatusBar().statusBarAlpha(0.2f).init();
    }

    private String typeName;
    private int sendTypeId;
    private int clickSendStatus;

    @Override
    protected void initDatas() {
        HttpUtil.createRequest(TAG, BaseUrl.getInstence().getPanelCinfig()).getPanelConfig(userToken).enqueue(new Callback<ApiResponse<GetPanelConfig>>() {
            @Override
            public void onResponse(Call<ApiResponse<GetPanelConfig>> call, Response<ApiResponse<GetPanelConfig>> response) {
                if (response.body().getCode() == 1) {
                    getPanelConfigData = response.body();
                    types = new ArrayList<>();
                    for (int i = 0; i < getPanelConfigData.getData().getCar_send_types().size(); i++) {
                        types.add(getPanelConfigData.getData().getCar_send_types().get(i).getType_name());
                        if (getPanelConfigData.getData().getCar_send_types().get(i).getSend_type_id() == getPanelConfigData.getData().getCar().getSend_type_id()) {
                            typeName = getPanelConfigData.getData().getCar_send_types().get(i).getType_name();
                        }
                    }
                    sendTypeId = getPanelConfigData.getData().getCar().getSend_type_id();
                    Glide.with(TakeOutBusinessSettActivity.this).load(BaseUrl.getInstence().ipAddress + getPanelConfigData.getData().getCar().getCar_avatar()).bitmapTransform(new GlideCircleTransform(TakeOutBusinessSettActivity.this)).into(ivPic);
                    tvStatus.setText(getPanelConfigData.getData().getCar().getSend_status() == 1 ? "停止接单中" : "外卖接单中");
                    tvSendType.setText(typeName);
                    etSendPrice.setText(getPanelConfigData.getData().getCar().getStart_money());
                    etSendRound.setText(getPanelConfigData.getData().getCar().getSend_range());
                    tvContent.setText(getPanelConfigData.getData().getCar().getSend_status() == 1 ? "正常营业，并接收新订单。" : "停止后，餐车将无法再接收新订单。");
                    tvClose.setText(getPanelConfigData.getData().getCar().getSend_status() == 1 ? "恢复接单" : "停止接单");
                    tvClose.setBackground(getPanelConfigData.getData().getCar().getSend_status() == 1 ? getResources().getDrawable(R.drawable.bg_open_shop) : getResources().getDrawable(R.drawable.bg_close_shop));
                    tvClose.setTextColor(getPanelConfigData.getData().getCar().getSend_status() == 1 ? getResources().getColor(R.color.colorGreen) : getResources().getColor(R.color.colorMain));
                    arrayAdapter = new ArrayAdapter(TakeOutBusinessSettActivity.this, R.layout.item_type, R.id.tv_popqusetion, types);

                } else {
                    ToastUtil.showToast(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<GetPanelConfig>> call, Throwable t) {
                ToastUtil.showToast("网络异常:获取外卖状态失败");
            }
        });
    }


    @OnClick({R.id.tv_send_type, R.id.tv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_type:
                TypeSelectWindow timeSelectWindow = new TypeSelectWindow(this);
                timeSelectWindow.showView(tvSendType, arrayAdapter, this);
                break;
            case R.id.tv_close:
                if (TextUtils.isEmpty(etSendRound.getText().toString())) {
                    ToastUtil.showToast("配送范围为空");
                    return;
                }
                if (TextUtils.isEmpty(etSendPrice.getText().toString())) {
                    ToastUtil.showToast("起送价格为空");
                    return;
                }
                HttpUtil.createRequest(TAG, BaseUrl.getInstence().panelCinfig()).cimmitPanelConfig(userToken,
                        getPanelConfigData.getData().getCar().getSend_status()==1?2:1, sendTypeId, etSendRound.getText().toString(),
                        etSendPrice.getText().toString()).enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.body().getCode() == 1) {
                            ToastUtil.showToast("外卖状态已修改");
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
                break;
        }
    }

    @Override
    public void select(int postion) {
        tvSendType.setText(getPanelConfigData.getData().getCar_send_types().get(postion).getType_name());
        sendTypeId = getPanelConfigData.getData().getCar_send_types().get(postion).getSend_type_id();
    }
}
