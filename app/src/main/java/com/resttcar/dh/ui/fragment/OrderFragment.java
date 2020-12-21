package com.resttcar.dh.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.MessageWrap;
import com.resttcar.dh.entity.OrderInfo;
import com.resttcar.dh.tools.AppManager;
import com.resttcar.dh.tools.LoadingManger;
import com.resttcar.dh.tools.PreferenceUtils;
import com.resttcar.dh.tools.RecyclerViewHelper;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.ui.activity.LoginActivity;
import com.resttcar.dh.ui.adapter.OrderAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 2020/4/8.
 */
public class OrderFragment extends BaseFragment {
    @BindView(R.id.rc_order)
    RecyclerView rcOrder;
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
    Unbinder unbinder;

    private OrderAdapter adapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initViews() {
        useEventBus = true;
        LoadingManger.getInsetance().setView(error);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(MessageWrap message) {//接收营业状态/外卖状态改变
        try {
            JSONObject jsonObject = new JSONObject(message.message);
            String type = jsonObject.getString("type");
            if (type.equals("order")) {
                getOrderData();
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void initDatas() {
        getOrderData();
    }

    private void getOrderData() {
        LoadingManger.getInsetance().startLoading();
        HttpUtil.createRequest(TAG, BaseUrl.getInstence().panelOrders()).panelOrders(userToken).enqueue(new Callback<ApiResponse<OrderInfo>>() {
            @Override
            public void onResponse(Call<ApiResponse<OrderInfo>> call, Response<ApiResponse<OrderInfo>> response) {
                if (response.body() != null) {
                    if (response.body().getCode() == 1) {
                        if (response.body().getData().getOrders() == null || response.body().getData().getOrders().size() == 0) {
                            LoadingManger.getInsetance().stopFinishLoading("暂无订单信息", false);
                            return;
                        }
                        adapter = new OrderAdapter(getActivity());
                        adapter.setDatas(response.body().getData());
                        RecyclerViewHelper.initRecyclerViewV(getActivity(), rcOrder, false, adapter);
                        LoadingManger.getInsetance().stopFinishLoading(true);
                    } else if (response.body().getCode() == 888) {//登录信息过期
                        ToastUtil.showToast(response.body().getMsg());
                        PreferenceUtils.getInstance().loginOut();
                        AppManager.getAppManager().finishAllActivity();
                        LoginActivity.actionStart(getActivity());
                    } else {
                        ToastUtil.showToast(response.body().getMsg());
                        LoadingManger.getInsetance().stopFinishLoading(response.body().getMsg(), true);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<OrderInfo>> call, Throwable t) {
//                ToastUtil.showToast("网络异常:获取订单异常");
                LoadingManger.getInsetance().stopFinishLoading("网络连接异常", false);
            }
        });
    }

}
