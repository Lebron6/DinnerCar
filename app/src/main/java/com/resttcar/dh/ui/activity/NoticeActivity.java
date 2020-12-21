package com.resttcar.dh.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.MessageWrap;
import com.resttcar.dh.entity.Notice;
import com.resttcar.dh.tools.LoadingManger;
import com.resttcar.dh.tools.RecyclerViewHelper;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.ui.adapter.NoticeAdapter;
import com.resttcar.dh.widget.SimpleFooter;
import com.resttcar.dh.widget.SimpleHeader;
import com.resttcar.dh.widget.TitleManger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 2020/4/22.
 * 通知中心
 */
public class NoticeActivity extends BaseActivity {
    NoticeAdapter adapter;
    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rv_notice)
    RecyclerView rvNotice;
    @BindView(R.id.sv_notice)
    SpringView svNotice;
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

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, NoticeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleManger manger = TitleManger.getInsetance();
        manger.setContext(this);
        manger.setBack();
        manger.setTitle("通知中心");
        getNoticeIndex();
        LoadingManger.getInsetance().startLoading();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_notice;
    }

    @Override
    protected void initViews() {
        mImmersionBar.transparentStatusBar().statusBarAlpha(0.2f).init();
        adapter = new NoticeAdapter(this);
        initSpringViewStyle();
        LoadingManger.getInsetance().setView(error);
    }

    private void initSpringViewStyle() {
        svNotice.setType(SpringView.Type.FOLLOW);
        svNotice.setListener(onFreshListener);
        svNotice.setHeader(new SimpleHeader(this));
        svNotice.setFooter(new SimpleFooter(this));
    }

    SpringView.OnFreshListener onFreshListener = new SpringView.OnFreshListener() {
        @Override
        public void onRefresh() {
            page = 1;
            getNoticeIndex();
        }

        @Override
        public void onLoadmore() {
            page = page++;
            getNoticeIndex();
        }
    };

    @Override
    protected void initDatas() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(MessageWrap message) {//接收营业状态/外卖状态改变
        try {
            JSONObject jsonObject = new JSONObject(message.message);
            String type = jsonObject.getString("type");
            if (type.equals("message")) {
                page = 1;
                getNoticeIndex();
            }
        } catch (Exception e) {
        }
    }

    private int page = 1;
    List<Notice.ListBean> listBeans = new ArrayList<>();

    private void getNoticeIndex() {
        HttpUtil.createRequest(TAG, BaseUrl.getInstence().messageIndex()).messageIndex(userToken, page, 10).enqueue(new Callback<ApiResponse<Notice>>() {
            @Override
            public void onResponse(Call<ApiResponse<Notice>> call, Response<ApiResponse<Notice>> response) {
                svNotice.onFinishFreshAndLoad();
                if (response.body() != null) {
                    if (response.body().getCode() == 1) {

                        if (page == 1) {
                            listBeans.clear();
                            listBeans.addAll(response.body().getData().getList());
                        } else {
                            listBeans.addAll(response.body().getData().getList());
                        }
                        if (listBeans==null||listBeans.size()==0){
                            LoadingManger.getInsetance().stopFinishLoading("暂无通知信息",false);
                            return;
                        }
                        adapter.setDatas(listBeans);
                        RecyclerViewHelper.initRecyclerViewV(NoticeActivity.this, rvNotice, false, adapter);
                        adapter.setOnItemClickLitener(new NoticeAdapter.OnItemClickLitener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                WebViewActivity.actionStart(NoticeActivity.this, listBeans.get(position).getTitle(), BaseUrl.getInstence().ipAddress + listBeans.get(position).getUrl());
                            }
                        });
                        LoadingManger.getInsetance().stopFinishLoading(true);
                    } else {
                        ToastUtil.showToast(response.body().getMsg());
                        LoadingManger.getInsetance().stopFinishLoading(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Notice>> call, Throwable t) {
                svNotice.onFinishFreshAndLoad();
//                ToastUtil.showToast("网络异常:获取通知列表失败");
                LoadingManger.getInsetance().stopFinishLoading("网络连接异常", false);
            }
        });
    }
}
