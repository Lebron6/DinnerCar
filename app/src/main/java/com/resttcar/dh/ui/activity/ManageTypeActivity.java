package com.resttcar.dh.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.GoodsType;
import com.resttcar.dh.tools.RecyclerViewHelper;
import com.resttcar.dh.tools.SimpleItemTouchHelperCallback;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.ui.adapter.ManagerTypeAdapter;
import com.resttcar.dh.widget.TitleManger;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 2020/5/13.
 * 分类管理
 */
public class ManageTypeActivity extends BaseActivity {
    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rv_manager_tyoe)
    RecyclerView rvManagerTyoe;
    @BindView(R.id.tv_new)
    TextView tvNew;
    private ManagerTypeAdapter adapter;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ManageTypeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleManger manger = TitleManger.getInsetance();
        manger.setContext(this);
        manger.setBack();
        manger.setTitle("管理分类");
        getClassType();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_manager_type;
    }

    @Override
    protected void initViews() {
        mImmersionBar.transparentStatusBar().statusBarAlpha(0.2f).init();
        adapter = new ManagerTypeAdapter(ManageTypeActivity.this);
    }

    @Override
    protected void initDatas() {

    }
    private void getClassType() {
        HttpUtil.createRequest(TAG, BaseUrl.getInstence().classList()).goodsTypes(userToken).enqueue(new Callback<ApiResponse<GoodsType>>() {
            @Override
            public void onResponse(Call<ApiResponse<GoodsType>> call, Response<ApiResponse<GoodsType>> response) {
                if (response.body() != null) {
                    if (response.body().getCode() == 1) {
                         GoodsType  data = response.body().getData();
                        if (data != null && data.getClasses() != null && data.getClasses().size() > 0) {

                            adapter.setDatas(data);
                            RecyclerViewHelper.initRecyclerViewV(ManageTypeActivity.this, rvManagerTyoe, false, adapter);

                            //创建SimpleItemTouchHelperCallback
                            ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
                            //用Callback构造ItemtouchHelper
                            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
                            //调用ItemTouchHelper的attachToRecyclerView方法建立联系
                            touchHelper.attachToRecyclerView(rvManagerTyoe);
                        }

                    } else {
                        ToastUtil.showToast(response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<GoodsType>> call, Throwable t) {
                ToastUtil.showToast("网络异常:商品分类获取失败");
            }
        });
    }

    @OnClick(R.id.tv_new)
    public void onViewClicked() {
        NewTypeActivity.actionStart(this,-1);
    }
}
