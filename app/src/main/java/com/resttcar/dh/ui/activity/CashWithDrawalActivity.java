package com.resttcar.dh.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.tools.Utils;
import com.resttcar.dh.widget.TitleManger;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 2020/5/8.
 */
public class CashWithDrawalActivity extends BaseActivity {
    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_bank_num)
    TextView tvBankNum;
    @BindView(R.id.tv_tax_rate)
    TextView tvTaxRate;
    @BindView(R.id.et_cash_num)
    EditText etCashNum;
    @BindView(R.id.tv_can_cash_num)
    TextView tvCanCashNum;
    @BindView(R.id.tv_cash_all)
    TextView tvCashAll;
    @BindView(R.id.tv_apply_draw_cash)
    TextView tvApplyDrawCash;
public static final String BANK_ACCOUNT="bank_account";
public static final String TIXIAN_PERCENT="tixian_percent";
public static final String TIXIAN_MONEY="tixian_money";
    public static void actionStart(Context context, String bank_account,String tixian_percent,String tixian_money) {
        Intent intent = new Intent(context, CashWithDrawalActivity.class);
        intent.putExtra(BANK_ACCOUNT,bank_account);
        intent.putExtra(TIXIAN_PERCENT,tixian_percent);
        intent.putExtra(TIXIAN_MONEY,tixian_money);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleManger manger = TitleManger.getInsetance();
        manger.setContext(this);
        manger.setBack();
        manger.setTitle("申请提现");
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_cash_with_drawal;
    }

    @Override
    protected void initViews() {
        mImmersionBar.transparentStatusBar().statusBarAlpha(0.2f).init();
    }

    @Override
    protected void initDatas() {
        tvBankNum.setText(getIntent().getStringExtra(BANK_ACCOUNT));
        tvTaxRate.setText("提现税率："+getIntent().getStringExtra(TIXIAN_PERCENT)+"%");
        tvCanCashNum.setText("可提现金额："+ Utils.stringToKeep2Point(getIntent().getStringExtra(TIXIAN_MONEY)+"")+"元");
    }

    @OnClick({R.id.tv_cash_all, R.id.tv_apply_draw_cash})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cash_all:
                etCashNum.setText(Utils.stringToKeep2Point(getIntent().getStringExtra(TIXIAN_MONEY)));
                break;
            case R.id.tv_apply_draw_cash:
                String price = etCashNum.getText().toString();
                if (TextUtils.isEmpty(price)){
                    ToastUtil.showToast("请输入提现金额");
                    return;
                }
                if (Double.valueOf(price)<=0){
                    ToastUtil.showToast("可提现余额不足");
                    return;
                }
                HttpUtil.createRequest(TAG, BaseUrl.getInstence().cwTiXian()).cwTiXian(userToken, price).enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.body().getCode()==1){
                            ToastUtil.showToast("提交成功");//
                            DrawCashCommitActivity.actionStart(CashWithDrawalActivity.this);
                            finish();
                        }else{
                            ToastUtil.showToast(response.body().getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        ToastUtil.showToast("网络异常:提现操作失败");
                    }
                });
                break;
        }
    }
}
