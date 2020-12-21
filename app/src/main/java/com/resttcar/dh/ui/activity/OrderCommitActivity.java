package com.resttcar.dh.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.ConfirmOrderData;
import com.resttcar.dh.entity.MessageWrap;
import com.resttcar.dh.entity.ShopCart;
import com.resttcar.dh.tools.RecyclerViewHelper;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.ui.adapter.CommitOrderAdapter;
import com.resttcar.dh.widget.TitleManger;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.resttcar.dh.ui.activity.NewTypeActivity.TYPE_CHANGE;

/**
 * Created by James on 2020/4/21.
 * 结算
 */
public class OrderCommitActivity extends BaseActivity {

    CommitOrderAdapter adapter;
    public static final String ORDER_DATA = "order_data";
    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.et_remarks)
    EditText etRemarks;
    @BindView(R.id.tv_all_price)
    TextView tvAllPrice;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.rv_commit)
    RecyclerView rvCommit;
    private ShopCart shopCart;

    public static void actionStart(Context context, String orderData) {
        Intent intent = new Intent(context, OrderCommitActivity.class);
        intent.putExtra(ORDER_DATA, orderData);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_order_commit;
    }

    @Override
    protected void initViews() {
        mImmersionBar.transparentStatusBar().statusBarAlpha(0.2f).init();
        TitleManger manger=TitleManger.getInsetance();
        manger.setContext(this);
        manger.setBack();
        manger.setTitle("提交订单");
    }

    @Override
    protected void initDatas() {
        Log.e("获取的数据json", getIntent().getStringExtra(ORDER_DATA));
        shopCart = new Gson().fromJson(getIntent().getStringExtra(ORDER_DATA), ShopCart.class);
        adapter = new CommitOrderAdapter(this);
        adapter.setDatas(shopCart);
        RecyclerViewHelper.initRecyclerViewV(this,rvCommit,false,adapter);
        tvAllPrice.setText("¥"+ shopCart.getShoppingTotalPrice()+"");
        tvPrice.setText("¥"+ shopCart.getShoppingTotalPrice()+"");
    }
    @OnClick(R.id.tv_commit)
    public void onViewClicked() {

            ConfirmOrderData confirmOrderData = new ConfirmOrderData();
            confirmOrderData.setBeizhu(etRemarks.getText().toString());
            confirmOrderData.setToken(userToken);
            List<ConfirmOrderData.OrdersBean> ordersBeans = new ArrayList<>();
            for (int i = 0; i < shopCart.getAllDishs().size(); i++) {
                ConfirmOrderData.OrdersBean ordersBean = new ConfirmOrderData.OrdersBean();
                ordersBean.setAmount(shopCart.getAllDishs().get(i).getNum() + "");
                ordersBean.setGoods_id(shopCart.getAllDishs().get(i).getGoodId() + "");
                if (shopCart.getAllDishs().get(i).getSelectSpec().length() > 0) {
                    ordersBean.setSpec(shopCart.getAllDishs().get(i).getSelectSpec().substring(1, shopCart.getAllDishs().get(i).getSelectSpec().length() - 1));
                } else {
                    ordersBean.setSpec(shopCart.getAllDishs().get(i).getSelectSpec());
                }
                ordersBeans.add(ordersBean);
            }
            confirmOrderData.setOrders(ordersBeans);
            if (confirmOrderData != null && confirmOrderData.getOrders().size() > 0) {
                HttpUtil.createRequest(BaseUrl.getInstence().getDeskConfirm()).confirmHang(confirmOrderData).enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.body().getCode() == 1) {
                            EventBus.getDefault().post( MessageWrap.getInstance(TYPE_CHANGE));
                            OrderCommitSucActivity.actionStart(OrderCommitActivity.this,new Gson().toJson(response.body()));
                      finish();
                        } else {
                            ToastUtil.showToast(response.body().getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        ToastUtil.showToast("网络异常:提交失败");
                    }
                });
            } else {
                ToastUtil.showToast("暂无订单数据");
            }
        }

}
