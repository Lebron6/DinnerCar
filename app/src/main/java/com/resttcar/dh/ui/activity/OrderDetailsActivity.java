package com.resttcar.dh.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.OrderDetails;
import com.resttcar.dh.entity.PurDetails;
import com.resttcar.dh.tools.RecyclerViewHelper;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.tools.Utils;
import com.resttcar.dh.ui.adapter.OrderDetailsAdapter;
import com.resttcar.dh.ui.adapter.PurchOrderDetailsAdapter;
import com.resttcar.dh.widget.TitleManger;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 2020/5/8.
 * 订单详情
 */
public class OrderDetailsActivity extends BaseActivity {

    public static final String ORDER_ID = "order_id";
    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rv_orders)
    RecyclerView rvOrders;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_order_type)
    TextView tvOrderType;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_order_state)
    TextView tvOrderState;
    @BindView(R.id.tv_remarks)
    TextView tvRemarks;

    public static void actionStart(Context context, int order_id) {
        Intent intent = new Intent(context, OrderDetailsActivity.class);
        intent.putExtra(ORDER_ID, order_id);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleManger manger = TitleManger.getInsetance();
        manger.setContext(this);
        manger.setBack();
        manger.setTitle("采购订单详情");
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initViews() {
        mImmersionBar.transparentStatusBar().statusBarAlpha(0.2f).init();
    }

    @Override
    protected void initDatas() {

        HttpUtil.createRequest(TAG, BaseUrl.getInstence().orderDetails()).getOrderDetails(userToken,getIntent().getIntExtra(ORDER_ID,-1)).enqueue(new Callback<ApiResponse<OrderDetails>>() {
            @Override
            public void onResponse(Call<ApiResponse<OrderDetails>> call, Response<ApiResponse<OrderDetails>> response) {
                ApiResponse<OrderDetails> body = response.body();
                if (body.getCode()==1){
                    tvOrderNum.setText(body.getData().getOrder().getOrder_no());
                    tvOrderType.setText("现场订单");//暂无其他类型
                    tvPrice.setText("¥"+body.getData().getOrder().getPrice());
                    tvOrderTime.setText(Utils.getDataYMD(body.getData().getOrder().getCreatetime()+""));
                    switch (body.getData().getOrder().getOrder_status()) {
                        case 1:
                          tvOrderState.setText("待接单");
                            break;
                        case 2:
                            tvOrderState.setText("已接单");
                            break;
                        case 3:
                            tvOrderState.setText("挂单中");
                            break;
                        case 4:
                            tvOrderState.setText("已完成");
                            break;
                        case 5:
                            tvOrderState.setText("退款中");
                            break;
                        case 6:
                            tvOrderState.setText("已退款");
                            break;
                        case -1:
                            tvOrderState.setText("已取消");
                            break;
                    }
                    tvRemarks.setText(body.getData().getOrder().getBeizhu());
                    OrderDetailsAdapter adapter = new OrderDetailsAdapter(OrderDetailsActivity.this);
                    adapter.setDatas(body.getData().getGoods());
                    RecyclerViewHelper.initRecyclerViewV(OrderDetailsActivity.this,rvOrders,false,adapter);
                }else{
                    ToastUtil.showToast(body.getMsg());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<OrderDetails>> call, Throwable t) {
                ToastUtil.showToast("网络异常：获取订单详情失败");
            }
        });


    }

}
