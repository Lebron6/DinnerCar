package com.resttcar.dh.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.callback.BrandAddImpl;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.BrandData;
import com.resttcar.dh.tools.LoadingManger;
import com.resttcar.dh.tools.RecyclerViewHelper;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.ui.adapter.BrandAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 2020/4/8.
 * 品牌菜单
 */
public class BrandMenuFragment extends BaseFragment implements BrandAddImpl {

    BrandAdapter adapter;
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.layout_center)
    RelativeLayout layoutCenter;
    @BindView(R.id.rv_brand)
    RecyclerView rvBrand;
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

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_brandmenu;
    }

    @Override
    protected void initViews() {
        LoadingManger.getInsetance().setView(error);
    }

    @Override
    protected void initDatas() {
        getBrandData("");
    }

    private void getBrandData(String title) {
        LoadingManger.getInsetance().startLoading();
        HttpUtil.createRequest(TAG, BaseUrl.getInstence().goodBrand()).brand(userToken, title).enqueue(new Callback<ApiResponse<BrandData>>() {
            @Override
            public void onResponse(Call<ApiResponse<BrandData>> call, Response<ApiResponse<BrandData>> response) {
                if (response.body() != null) {
                    if (response.body().getCode() == 1) {
                        if (response.body().getData().getGoods()==null||response.body().getData().getGoods().size()==0){
                            LoadingManger.getInsetance().stopFinishLoading("暂无信息",false);
                            return;
                        }
                        adapter = new BrandAdapter(getActivity(), BrandMenuFragment.this);
                        adapter.setDatas(response.body().getData());
                        RecyclerViewHelper.initRecyclerViewV(getActivity(), rvBrand, false, adapter);
                        LoadingManger.getInsetance().stopFinishLoading(true);
                    } else {
//                        ToastUtil.showToast(response.body().getMsg());
                        LoadingManger.getInsetance().stopFinishLoading(response.body().getMsg(),false);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<BrandData>> call, Throwable t) {
//                ToastUtil.showToast("网络异常:获取品牌菜单失败");
                LoadingManger.getInsetance().stopFinishLoading("网络连接异常", false);
            }
        });
    }

    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        getBrandData(etInput.getText().toString());
    }

    @Override
    public void add(int postion) {
        getBrandData("");
    }

}
