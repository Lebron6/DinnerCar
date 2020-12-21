package com.resttcar.dh.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.CheckOrderResult;
import com.resttcar.dh.entity.CommitOrderResult;
import com.resttcar.dh.entity.MessageWrap;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.tools.Utils;
import com.resttcar.dh.widget.TitleManger;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 2020/5/25.
 * 订单提交成功/展示二维码
 */
public class OrderCommitSucActivity extends BaseActivity {


    public static final String ORDER_DATA = "order_data";
    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_QRCode)
    ImageView ivQRCode;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.layout_top)
    LinearLayout layoutTop;
    @BindView(R.id.rb_online_pay)
    RadioButton rbOnlinePay;
    @BindView(R.id.rb_balance)
    RadioButton rbBalance;
    @BindView(R.id.rg_pay_type)
    RadioGroup rgPayType;
    private CommitOrderResult result;
    private Timer timer;
    public static final  String PAY_SUCCESS="pay_success";

    public static void actionStart(Context context, String orderData) {
        Intent intent = new Intent(context, OrderCommitSucActivity.class);
        intent.putExtra(ORDER_DATA, orderData);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleManger manger = TitleManger.getInsetance();
        manger.setContext(this);
        manger.setBack();
        manger.setTitle("在线支付");
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_order_commit_suc;
    }

    @Override
    protected void initViews() {
        mImmersionBar.transparentStatusBar().statusBarAlpha(0.2f).init();
        result = new Gson().fromJson(getIntent().getStringExtra(ORDER_DATA), CommitOrderResult.class);
        tvPrice.setText("¥" + result.getData().getPay_price());
        Bitmap qrCodeBitmap = Utils.createQRCodeBitmap(result.getData().getCode_url());
        ivQRCode.setImageBitmap(qrCodeBitmap);
        rgPayType.setOnCheckedChangeListener(onCheckedChangeListener);
        rbOnlinePay.setChecked(true);
    }
    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Bitmap qrCodeBitmap;
            switch (checkedId) {
                case R.id.rb_online_pay:
                    rbOnlinePay.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.icon_wechat_sel), null, null, null);
                    rbOnlinePay.setCompoundDrawablePadding(3);
                    rbBalance.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.icon_alipay), null, null, null);
                    rbBalance.setCompoundDrawablePadding(3);
                    rbOnlinePay.setTextColor(getResources().getColor(R.color.colorBlack));
                    rbBalance.setTextColor(getResources().getColor(R.color.colorWhite));
                     qrCodeBitmap = Utils.createQRCodeBitmap(result.getData().getCode_url());
                    ivQRCode.setImageBitmap(qrCodeBitmap);
                    break;
                case R.id.rb_balance:
                    rbOnlinePay.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.icon_wechat), null, null, null);
                    rbOnlinePay.setCompoundDrawablePadding(3);
                    rbBalance.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.icon_alipay_sel), null, null, null);
                    rbBalance.setCompoundDrawablePadding(3);
                    rbOnlinePay.setTextColor(getResources().getColor(R.color.colorWhite));
                    rbBalance.setTextColor(getResources().getColor(R.color.colorBlack));
                     qrCodeBitmap = Utils.createQRCodeBitmap(result.getData().getAlipay_code_url());
                    ivQRCode.setImageBitmap(qrCodeBitmap);
                    break;
            }
        }
    };
    @Override
    protected void initDatas() {

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                HttpUtil.createRequest(TAG,BaseUrl.getInstence().checkOrder()).checkOrder(userToken,result.getData().getOrder_id()).enqueue(new Callback<ApiResponse<CheckOrderResult>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<CheckOrderResult>> call, Response<ApiResponse<CheckOrderResult>> response) {
                        if (response.body().getCode()==1){
                            if (response.body().getData().getCode()==1){
                                ToastUtil.showToast("支付成功");
                                timer.cancel();
                                EventBus.getDefault().post(MessageWrap.getInstance(PAY_SUCCESS));
                                PayCommitActivity.actionStart(OrderCommitSucActivity.this);
                                finish();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ApiResponse<CheckOrderResult>> call, Throwable t) {

                    }
                });
            }
        },1000,3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer!=null){
            timer.cancel();
        }
    }
}
