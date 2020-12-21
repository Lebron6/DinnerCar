package com.resttcar.dh.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.CWIndex;
import com.resttcar.dh.entity.CWInfo;
import com.resttcar.dh.tools.RecyclerViewHelper;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.tools.Utils;
import com.resttcar.dh.ui.adapter.FinaStatAdapter;
import com.resttcar.dh.widget.SimpleFooter;
import com.resttcar.dh.widget.SimpleHeader;
import com.resttcar.dh.widget.TitleManger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 2020/4/22.
 * 财务统计
 */
public class FinancialStatisticsActivity extends BaseActivity {
    FinaStatAdapter adapter;
    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_can_money)
    TextView tvCanMoney;
    @BindView(R.id.tv_apply_draw_cash)
    TextView tvApplyDrawCash;
    @BindView(R.id.tv_account_money)
    TextView tvAccountMoney;
    @BindView(R.id.tv_cycle)
    TextView tvCycle;
    @BindView(R.id.tv_mang_price)
    TextView tvMangPrice;
    @BindView(R.id.tv_deduction_data)
    TextView tvDeductionData;
    @BindView(R.id.rv_fina_stat)
    RecyclerView rvFinaStat;
    @BindView(R.id.sv_pur)
    SpringView svPur;
    private CWInfo.CarBean car;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FinancialStatisticsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleManger manger = TitleManger.getInsetance();
        manger.setContext(this);
        manger.setBack();
        manger.setTitle("财务统计");
        getCWInfo();
        getCWIndex();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_financial_statistics;
    }

    @Override
    protected void initViews() {
        mImmersionBar.transparentStatusBar().statusBarAlpha(0.2f).init();
        adapter = new FinaStatAdapter(this);

        initSpringViewStyle();
    }

    private void initSpringViewStyle() {
        svPur.setType(SpringView.Type.FOLLOW);
        svPur.setListener(onFreshListener);
        svPur.setHeader(new SimpleHeader(this));
        svPur.setFooter(new SimpleFooter(this));
    }

    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            page = 1;
            getCWIndex();
        }

        @Override
        public void onLoadmore() {
            page = page++;
            getCWIndex();
        }
    };

    @Override
    protected void initDatas() {

    }

    private int page = 1;
    List<CWIndex.ListBean> listBeans = new ArrayList<>();

    private void getCWIndex() {
        HttpUtil.createRequest(TAG, BaseUrl.getInstence().cwIndex()).cwIndex(userToken, page, 10).enqueue(new Callback<ApiResponse<CWIndex>>() {
            @Override
            public void onResponse(Call<ApiResponse<CWIndex>> call, Response<ApiResponse<CWIndex>> response) {
                if (svPur!=null){
                    svPur.onFinishFreshAndLoad();
                }

                if (response.body() != null) {
                    if (response.body().getCode() == 1) {
                        if (page == 1) {
                            listBeans.clear();
                            listBeans.addAll(response.body().getData().getList());
                        } else {
                            listBeans.addAll(response.body().getData().getList());
                        }
                        adapter.setDatas(listBeans);
                        RecyclerViewHelper.initRecyclerViewV(FinancialStatisticsActivity.this, rvFinaStat, false, adapter);
                    } else {
                        ToastUtil.showToast(response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<CWIndex>> call, Throwable t) {svPur.onFinishFreshAndLoad();
                ToastUtil.showToast("网络异常:获取财务列表失败");
            }
        });
    }

    private void getCWInfo() {
        HttpUtil.createRequest(TAG, BaseUrl.getInstence().cwInfo()).cwInfo(userToken).enqueue(new Callback<ApiResponse<CWInfo>>() {
            @Override
            public void onResponse(Call<ApiResponse<CWInfo>> call, Response<ApiResponse<CWInfo>> response) {
                if (response.body() != null) {
                    if (response.body().getCode() == 1) {
                        car = response.body().getData().getCar();
                        tvAccountMoney.setText(car.getMoney() + "元");
                        tvCanMoney.setText("¥" + Utils.stringToKeep2Point(car.getTixian_money()+""));
                        tvMangPrice.setText(car.getManage_fee() + "元");
                        tvCycle.setText("T+"+car.getTixian_cycle() + "");
                        tvDeductionData.setText("每月"+car.getManage_fee_date() + "号");
                    } else {
                        ToastUtil.showToast(response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<CWInfo>> call, Throwable t) {
                ToastUtil.showToast("网络异常:获取财务信息失败");
            }
        });
    }


    @OnClick(R.id.tv_apply_draw_cash)
    public void onViewClicked() {
        if (TextUtils.isEmpty(car.getBank_account())){
            ToastUtil.showToast("暂无响应,请稍后再试");
            return;
        }
        CashWithDrawalActivity.actionStart(this,car.getBank_account(),car.getTixian_percent(),car.getTixian_money()+"");
    }
}
