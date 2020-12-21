package com.resttcar.dh.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.MaterialOrders;
import com.resttcar.dh.tools.LoadingManger;
import com.resttcar.dh.tools.RecyclerViewHelper;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.ui.activity.PurchaseOrderDetailsActivity;
import com.resttcar.dh.ui.adapter.PurOrderAdapter;
import com.resttcar.dh.widget.SimpleFooter;
import com.resttcar.dh.widget.SimpleHeader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 2020/4/8.
 * 采购订单
 */
public class PurchaseFragment extends BaseFragment {


    @BindView(R.id.layout_center)
    RelativeLayout layoutCenter;
    @BindView(R.id.rv_order)
    RecyclerView rvOrder;
    PurOrderAdapter adapter;
    @BindView(R.id.sv_pur)
    SpringView svPur;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.et_content)
    EditText et_content;

    Unbinder unbinder;
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
    Unbinder unbinder1;
    private int page = 1;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_purchase;
    }

    @Override
    protected void initViews() {
        adapter = new PurOrderAdapter(getActivity());
        adapter.setOnItemClickLitener(new PurOrderAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                PurchaseOrderDetailsActivity.actionStart(getActivity(), datas.get(position).getOrder_id());
            }
        });
        RecyclerViewHelper.initRecyclerViewV(getActivity(), rvOrder, false, adapter);
        initSpringViewStyle();
        LoadingManger.getInsetance().setView(error);
    }

    private void initSpringViewStyle() {
        svPur.setType(SpringView.Type.FOLLOW);
        svPur.setListener(onFreshListener);
        svPur.setHeader(new SimpleHeader(getActivity()));
        svPur.setFooter(new SimpleFooter(getActivity()));
    }

    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            refreshPurchaseDatas();
        }

        @Override
        public void onLoadmore() {
            loadmorePurchaseDatas();
        }
    };

    public void refreshPurchaseDatas() {
        page = 1;
        getPurchaseDatas(page, "");
    }

    private void loadmorePurchaseDatas() {
        page = ++page;
        getPurchaseDatas(page, "");
    }

    @Override
    protected void initDatas() {
        LoadingManger.getInsetance().startLoading();
        refreshPurchaseDatas();
    }

    List<MaterialOrders.ListBean> datas = new ArrayList<>();

    private void getPurchaseDatas(final int page, String order_no) {

        HttpUtil.createRequest(TAG, BaseUrl.getInstence().materialOrders()).materialOrders(userToken, page, 10, order_no).enqueue(new Callback<ApiResponse<MaterialOrders>>() {
            @Override
            public void onResponse(Call<ApiResponse<MaterialOrders>> call, Response<ApiResponse<MaterialOrders>> response) {
                svPur.onFinishFreshAndLoad();
                if (response.body().getCode() == 1) {
                    if (page == 1) {
                        datas.clear();
                        datas.addAll(response.body().getData().getList());
                    } else {
                        datas.addAll(response.body().getData().getList());
                    }
                    if (datas==null||datas.size()==0){
                        LoadingManger.getInsetance().stopFinishLoading("暂无信息",false);
                        return;
                    }
                    adapter.setDatas(datas);
                    LoadingManger.getInsetance().stopFinishLoading(true);
                } else {
                    ToastUtil.showToast(response.body().getMsg());
                    LoadingManger.getInsetance().stopFinishLoading(true);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<MaterialOrders>> call, Throwable t) {
                svPur.onFinishFreshAndLoad();
//                ToastUtil.showToast("网络异常:获取采购订单失败");
                LoadingManger.getInsetance().stopFinishLoading("网络连接异常", false);
            }
        });
    }

    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        if (TextUtils.isEmpty(et_content.getText().toString())) {
            ToastUtil.showToast("请输入订单编号");
            return;
        } else {
            page = 1;
            getPurchaseDatas(page, et_content.getText().toString());
        }

    }
}

