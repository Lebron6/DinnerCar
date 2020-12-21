package com.resttcar.dh.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.GetPanelConfig;
import com.resttcar.dh.entity.MessageWrap;
import com.resttcar.dh.entity.UserCenter;
import com.resttcar.dh.tools.AppManager;
import com.resttcar.dh.tools.PreferenceUtils;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.ui.activity.EditCarInfoActivity;
import com.resttcar.dh.ui.activity.FinancialStatisticsActivity;
import com.resttcar.dh.ui.activity.LoginActivity;
import com.resttcar.dh.ui.activity.MenuManageActivity;
import com.resttcar.dh.ui.activity.NoticeActivity;
import com.resttcar.dh.ui.activity.OrderMangerActivity;
import com.resttcar.dh.ui.activity.RawMaterialProcurementActivity;
import com.resttcar.dh.ui.activity.SceneBusinessSettActivity;
import com.resttcar.dh.ui.activity.SettingActivity;
import com.resttcar.dh.ui.activity.TakeOutBusinessSettActivity;
import com.resttcar.dh.widget.GlideCircleTransform;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.resttcar.dh.api.Constant.CHANGE_STATUS;
import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * Created by James on 2020/4/8.
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @BindView(R.id.tv_car_num)
    TextView tvCarNum;
    @BindView(R.id.img_left_status)
    ImageView imgLeftStatus;
    @BindView(R.id.tv_business_status_left)
    TextView tvBusinessStatusLeft;
    @BindView(R.id.layout_status_left)
    LinearLayout layoutStatusLeft;
    @BindView(R.id.img_right_status)
    ImageView imgRightStatus;
    @BindView(R.id.tv_business_status_right)
    TextView tvBusinessStatusRight;
    @BindView(R.id.layout_status_right)
    LinearLayout layoutStatusRight;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_sell_num)
    TextView tvSellNum;
    @BindView(R.id.layout_menu)
    LinearLayout layoutMenu;
    @BindView(R.id.layout_order)
    LinearLayout layoutOrder;
    @BindView(R.id.layout_purc)
    LinearLayout layoutPurc;
    @BindView(R.id.layout_fin)
    LinearLayout layoutFin;
    @BindView(R.id.layout_notice)
    LinearLayout layoutNotice;
    @BindView(R.id.layout_set)
    LinearLayout layoutSet;
    @BindView(R.id.layout_login_out)
    LinearLayout layoutLoginOut;
    Unbinder unbinder;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        getUserCenterInfo();
    }

    private void getUserCenterInfo() {
        HttpUtil.createRequest(TAG, BaseUrl.getInstence().userCenter()).userCenter(userToken).enqueue(new Callback<ApiResponse<UserCenter>>() {
            @Override
            public void onResponse(Call<ApiResponse<UserCenter>> call, Response<ApiResponse<UserCenter>> response) {
                if (response.body().getCode() == 1) {
                    tvBusinessStatusRight.setText(response.body().getData().getCar().getSend_status()==1?"外卖停业中":"外卖接单中");
                    tvBusinessStatusLeft.setText(response.body().getData().getCar().getStatus()==1?"现场打烊中":"现场营业中");
                   tvCarNum.setText(response.body().getData().getCar().getCar_name());
                    Glide.with(getActivity()).load(BaseUrl.getInstence().ipAddress + response.body().getData().getCar().getCar_avatar()).bitmapTransform(new GlideCircleTransform(getActivity())).into(ivUserIcon);
                    tvOrderNum.setText(response.body().getData().getStatistics().getNum()+"");
                    tvSellNum.setText(response.body().getData().getStatistics().getMoney()+"");
                } else if (response.body().getCode() == 888) {//登录信息过期
                    ToastUtil.showToast(response.body().getMsg());
                    PreferenceUtils.getInstance().loginOut();
                    AppManager.getAppManager().finishAllActivity();
                    LoginActivity.actionStart(getActivity());
                }  else {
                    ToastUtil.showToast(response.body().getMsg());
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<UserCenter>> call, Throwable t) {
                ToastUtil.showToast("网络异常:获取外卖状态失败");
            }
        });
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onReceiveMsg(MessageWrap message) {//接收营业状态/外卖状态改变
//      if (message.message.equals(CHANGE_STATUS)){
//          getUserCenterInfo();
//      }
//    }

    @OnClick({R.id.iv_user_icon, R.id.layout_status_left, R.id.layout_status_right, R.id.layout_menu, R.id.layout_order, R.id.layout_purc, R.id.layout_fin, R.id.layout_notice, R.id.layout_set, R.id.layout_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user_icon:
                break;
            case R.id.layout_status_left:
                SceneBusinessSettActivity.actionStart(getActivity());
                break;
            case R.id.layout_status_right:
                TakeOutBusinessSettActivity.actionStart(getActivity());
                break;
            case R.id.layout_menu:
                MenuManageActivity.actionStart(getActivity());
                break;
            case R.id.layout_order:
                OrderMangerActivity.actionStart(getActivity());
                break;
            case R.id.layout_purc:
                RawMaterialProcurementActivity.actionStart(getActivity());
                break;
            case R.id.layout_fin:
                FinancialStatisticsActivity.actionStart(getActivity());
                break;
            case R.id.layout_notice:
                NoticeActivity.actionStart(getActivity());
                break;
            case R.id.layout_set:
                SettingActivity.actionStart(getActivity());
                break;
            case R.id.layout_login_out:
                loginOut();
                break;
        }
    }

    private void loginOut() {
        HttpUtil.createRequest(BaseUrl.getInstence().loginOut()).loginOut(userToken).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body().getCode() == 1) {
                    ToastUtil.showToast("已注销");
                    PreferenceUtils.getInstance().loginOut();
                    AppManager.getAppManager().finishAllActivity();
                    LoginActivity.actionStart(getActivity());
                } else {
                    ToastUtil.showToast(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                ToastUtil.showToast("网络异常:排序失败");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
