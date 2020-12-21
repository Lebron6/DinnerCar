package com.resttcar.dh.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.resttcar.dh.R;
import com.resttcar.dh.widget.TitleManger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by James on 2020/5/8.
 * 支付成功
 */
public class PayCommitActivity extends BaseActivity {

    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_apply)
    ImageView ivApply;
    @BindView(R.id.tv_back)
    TextView tvBack;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, PayCommitActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleManger manger = TitleManger.getInsetance();
        manger.setContext(this);
        manger.setBack();
        manger.setTitle("支付成功");
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_pay_success;
    }

    @Override
    protected void initViews() {
        mImmersionBar.transparentStatusBar().statusBarAlpha(0f).init();
    }

    @Override
    protected void initDatas() {

    }


    @OnClick(R.id.tv_back)
    public void onViewClicked() {
        finish();
    }
}
