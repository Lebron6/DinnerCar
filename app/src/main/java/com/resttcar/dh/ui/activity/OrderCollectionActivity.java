package com.resttcar.dh.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.HangData;
import com.resttcar.dh.tools.LoadingManger;
import com.resttcar.dh.tools.RecyclerViewHelper;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.ui.adapter.OrderCollectionAdapter;
import com.resttcar.dh.widget.TitleManger;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 2020/4/20.
 * 取单
 */
public class OrderCollectionActivity extends BaseActivity {
    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rv_oc)
    RecyclerView rvOc;
    @BindView(R.id.msg)
    TextView msg;
    @BindView(R.id.res)
    LinearLayout res;
    @BindView(R.id.loading)
    ImageView loading;
    @BindView(R.id.layout_loading)
    LinearLayout layoutLoading;
    @BindView(R.id.error)
    RelativeLayout error;
    private OrderCollectionAdapter adapter;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, OrderCollectionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleManger manger = TitleManger.getInsetance();
        manger.setContext(this);
        manger.setBack();
        manger.setTitle("取单");
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_order_collection;
    }

    @Override
    protected void initViews() {
        mImmersionBar.transparentStatusBar().statusBarAlpha(0.2f).init();
        LoadingManger.getInsetance().setView(error);
    }

    @Override
    protected void initDatas() {
        LoadingManger.getInsetance().startLoading();
        HttpUtil.createRequest(TAG, BaseUrl.getInstence().getHang()).getHang(userToken).enqueue(new Callback<ApiResponse<HangData>>() {
            @Override
            public void onResponse(Call<ApiResponse<HangData>> call, Response<ApiResponse<HangData>> response) {
                if (response.body().getCode() == 1) {
                    adapter = new OrderCollectionAdapter(OrderCollectionActivity.this);
                    adapter.setDatas(response.body().getData());
                    RecyclerViewHelper.initRecyclerViewV(OrderCollectionActivity.this, rvOc, false, adapter);
                    LoadingManger.getInsetance().stopFinishLoading(true);
                } else {
                    ToastUtil.showToast(response.body().getMsg());
                    LoadingManger.getInsetance().stopFinishLoading(response.body().getMsg(),true);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<HangData>> call, Throwable t) {
//                ToastUtil.showToast("网络异常:获取挂单数据失败");
                LoadingManger.getInsetance().stopFinishLoading("网络连接异常", false);
            }
        });
    }

}
