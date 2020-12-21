package com.resttcar.dh.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.resttcar.dh.R;
import com.resttcar.dh.ui.adapter.ViewPagerAdapter;
import com.resttcar.dh.ui.fragment.OrderListFragment;
import com.resttcar.dh.ui.fragment.OrderStatisticsFragment;
import com.resttcar.dh.ui.fragment.PurchaseFragment;
import com.resttcar.dh.ui.fragment.RawFragment;
import com.resttcar.dh.widget.NavitationLayout;
import com.resttcar.dh.widget.TitleManger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by James on 2020/4/26.
 * 订单管理
 */
public class OrderMangerActivity extends BaseActivity {
    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tab_raw)
    NavitationLayout tabRaw;
    @BindView(R.id.vp_raw)
    ViewPager vpRaw;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, OrderMangerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleManger manger = TitleManger.getInsetance();
        manger.setContext(this);
        manger.setBack();
        manger.setTitle("订单管理");
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_raw;
    }

    @Override
    protected void initViews() {
        mImmersionBar.transparentStatusBar().statusBarAlpha(0.2f).init();
       List<String> titles2 = new ArrayList<>();
       titles2.add("订单统计");
       titles2.add("订单列表");
        List<Fragment> fragments= new ArrayList<>();
        fragments.add(new OrderStatisticsFragment());
        fragments.add(new OrderListFragment());
        ViewPagerAdapter vpa = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        vpRaw.setAdapter(vpa);
        vpRaw.setOffscreenPageLimit(2);
        tabRaw.setViewPager(this,titles2,vpRaw,R.color.colorTextGray,R.color.colorMain,15,15,0,8,true);
    tabRaw.setNavLine(this,1,R.color.colorMain,0);
    }

    @Override
    protected void initDatas() {

    }


}
