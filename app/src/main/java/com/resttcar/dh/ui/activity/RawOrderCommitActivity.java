package com.resttcar.dh.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.ConfirmMaterialData;
import com.resttcar.dh.entity.ShopCart;
import com.resttcar.dh.tools.RecyclerViewHelper;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.ui.adapter.CommitOrderAdapter;
import com.resttcar.dh.widget.TitleManger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 2020/4/21.
 * 原料采购订单提交
 */
public class RawOrderCommitActivity extends BaseActivity {
    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rv_commit)
    RecyclerView rvGoos;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.et_remarks)
    EditText etRemarks;
    @BindView(R.id.txt_pay_type)
    TextView txtPayType;
    @BindView(R.id.rb_online_pay)
    RadioButton rbOnlinePay;
    @BindView(R.id.rb_balance)
    RadioButton rbBalance;
    @BindView(R.id.tv_all_price)
    TextView tvAllPrice;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.rg_pay_type)
    RadioGroup rgPayType;
    public static final String ORDER_DATA = "order_data";
    private ShopCart shopCart;

    public static void actionStart(Context context, String orderData) {
        Intent intent = new Intent(context, RawOrderCommitActivity.class);
        intent.putExtra(ORDER_DATA, orderData);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        mImmersionBar.transparentStatusBar().statusBarAlpha(0.2f).init();
        TitleManger manger = TitleManger.getInsetance();
        manger.setContext(this);
        manger.setTitle("提交订单");
        manger.setBack();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_raw_order_commit;
    }

    @Override
    protected void initViews() {
        rgPayType.setOnCheckedChangeListener(onCheckedChangeListener);
        rbOnlinePay.setChecked(true);
    }

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_online_pay:
                    rbOnlinePay.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.icon_line_sel), null, null, null);
                    rbOnlinePay.setCompoundDrawablePadding(3);
                    rbBalance.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.icon_balance), null, null, null);
                    rbBalance.setCompoundDrawablePadding(3);
                    rbOnlinePay.setTextColor(getResources().getColor(R.color.colorWhite));
                    rbBalance.setTextColor(getResources().getColor(R.color.colorTextGray));
                    payType = 1;
                    break;
                case R.id.rb_balance:
                    rbOnlinePay.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.icon_line), null, null, null);
                    rbOnlinePay.setCompoundDrawablePadding(3);
                    rbBalance.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.icon_balance_sel), null, null, null);
                    rbBalance.setCompoundDrawablePadding(3);
                    rbOnlinePay.setTextColor(getResources().getColor(R.color.colorTextGray));
                    rbBalance.setTextColor(getResources().getColor(R.color.colorWhite));
                    payType = 2;
                    break;
            }
        }
    };

    @Override
    protected void initDatas() {
        Log.e("获取的数据json", getIntent().getStringExtra(ORDER_DATA));
        shopCart = new Gson().fromJson(getIntent().getStringExtra(ORDER_DATA), ShopCart.class);
        CommitOrderAdapter adapter = new CommitOrderAdapter(this);
        adapter.setDatas(shopCart);
        RecyclerViewHelper.initRecyclerViewV(this, rvGoos, false, adapter);
        tvAllPrice.setText("¥" + shopCart.getShoppingDiscountTotalPrice() + "");
        tvPrice.setText("¥" + shopCart.getShoppingDiscountTotalPrice() + "");
    }

    private int payType = 1;
    private String remarks = "";

    @OnClick(R.id.tv_commit)
    public void onViewClicked() {
        if (TextUtils.isEmpty(etRemarks.getText().toString())) {
            remarks = "";
        } else {
            remarks = etRemarks.getText().toString();
        }
        ConfirmMaterialData data = new ConfirmMaterialData();
        data.setBeizhu(remarks);
        data.setPay_type(payType);
        data.setToken(userToken);
        List<ConfirmMaterialData.Order> orders = new ArrayList<>();
        for (int i = 0; i < shopCart.getAllDishs().size(); i++) {
            ConfirmMaterialData.Order order = new ConfirmMaterialData.Order();
            order.setAmount(shopCart.getAllDishs().get(i).getNum());
            order.setMaterial_id(shopCart.getAllDishs().get(i).getMaterial_id());
            orders.add(order);
        }
        data.setOrders(orders);
        Log.e("提交的数据", new Gson().toJson(data));
        HttpUtil.createRequest(BaseUrl.getInstence().materialConfirm()).materialConfirm(data).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body().getCode() == 1) {
                    if (payType == 2) {//余额支付，跳转支付成功
                        PayCommitActivity.actionStart(RawOrderCommitActivity.this);
                        finish();
                    } else {
                        OrderCommitSucActivity.actionStart(RawOrderCommitActivity.this, new Gson().toJson(response.body()));
                        finish();
                    }

                } else {
                    ToastUtil.showToast(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                ToastUtil.showToast("网络异常:提交原料订单失败");
            }
        });
    }
}
