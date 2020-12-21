package com.resttcar.dh.ui.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.resttcar.dh.R;
import com.resttcar.dh.app.DiningCarAPP;
import com.resttcar.dh.jpush.ExampleUtil;
import com.resttcar.dh.socket.JWebSocketClient;
import com.resttcar.dh.tools.PreferenceUtils;
import com.resttcar.dh.ui.adapter.MainPageAdapter;
import com.resttcar.dh.widget.CustomViewPager;


import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rb_order)
    RadioButton rbOrder;
    @BindView(R.id.rb_cash)
    RadioButton rbCash;
    @BindView(R.id.rb_mine)
    RadioButton rbMine;
    @BindView(R.id.rg_group)
    RadioGroup rgGroup;
    @BindView(R.id.vp_content)
    CustomViewPager vpContent;
    private MainPageAdapter adapter;
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        mImmersionBar.transparentStatusBar().statusBarAlpha(0.2f).init();
        adapter = new MainPageAdapter(getSupportFragmentManager());
        vpContent.setAdapter(adapter);
        vpContent.setOffscreenPageLimit(2);
        vpContent.setOnPageChangeListener(onPagerChangerListener);
        rgGroup.setOnCheckedChangeListener(onCheckedChangeListener);
        rbOrder.setChecked(true);
        JPushInterface.resumePush(DiningCarAPP.getApplication());
    }


    @Override
    protected void initDatas() {
        Set<String> tags=new HashSet<>();
        tags.add(PreferenceUtils.getInstance().getGroupId());
        JPushInterface.setAliasAndTags(getApplicationContext(),
                PreferenceUtils.getInstance().getUserId(),tags,
                new TagAliasCallback() {
                    @Override
                    public void gotResult(int responseCode, String alias, Set<String> tags) {
                        Log.e(TAG,"responseCode:"+responseCode+",alias:"+alias+",tags:"+tags.toString());
                    }
                }
        );

    }

    ViewPager.OnPageChangeListener onPagerChangerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    rbOrder.setChecked(true);
                    break;
                case 1:
                    rbCash.setChecked(true);
                    break;
                case 2:
                    rbMine.setChecked(true);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            setTabSelection(checkedId);
        }
    };

    private void setTabSelection(int checkedId) {
        switch (checkedId) {
            case R.id.rb_order:
                vpContent.setCurrentItem(0, false);
                break;
            case R.id.rb_cash:
                vpContent.setCurrentItem(1, false);
                break;
            case R.id.rb_mine:
                vpContent.setCurrentItem(2, false);
                break;
        }
    }

}
