package com.resttcar.dh.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.CarInfo;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.tools.Utils;
import com.resttcar.dh.widget.GlideCircleTransform;
import com.resttcar.dh.widget.TitleManger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 2020/4/22.
 * 设置
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.tv_car_name)
    TextView tvCarName;
    @BindView(R.id.tv_car_num)
    TextView tvCarNum;
    @BindView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @BindView(R.id.iv_car)
    ImageView ivCar;
    @BindView(R.id.et_car_num)
    TextView etCarNum;
    @BindView(R.id.et_brand)
    TextView etBrand;
    @BindView(R.id.et_car_manger)
    TextView etCarManger;
    @BindView(R.id.et_car_brand_num)
    TextView etCarBrandNum;
    @BindView(R.id.et_addr)
    TextView etAddr;
    @BindView(R.id.et_bank_num)
    TextView etBankNum;
    @BindView(R.id.et_bank_name)
    TextView etBankName;
    @BindView(R.id.et_date)
    TextView etDate;
    @BindView(R.id.et_bus_type)
    TextView etBusType;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleManger manger = TitleManger.getInsetance();
        manger.setContext(this);
        manger.setBack();
        manger.setTitle("设置");
        getData();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViews() {
        mImmersionBar.transparentStatusBar().statusBarAlpha(0.2f).init();
    }

    @Override
    protected void initDatas() {

    }
private void getData(){
    HttpUtil.createRequest(TAG, BaseUrl.getInstence().carIndex()).carIndex(userToken).enqueue(new Callback<ApiResponse<CarInfo>>() {
        @Override
        public void onResponse(Call<ApiResponse<CarInfo>> call, Response<ApiResponse<CarInfo>> response) {
            ApiResponse<CarInfo> body = response.body();
            if (body.getCode() == 1) {
                CarInfo.CarBean car = body.getData().getCar();
                tvCarName.setText(car.getCar_name());
                tvCarNum.setText(car.getAccount());
                tvPhoneNum.setText(car.getTelephone());
                Glide.with(SettingActivity.this).load(BaseUrl.getInstence().ipAddress+ car.getCar_avatar()).bitmapTransform(new GlideCircleTransform(SettingActivity.this)).into(ivCar);
                etCarNum.setText(car.getCar_id()+"");
                etBrand.setText(car.getBrand_name());
                etCarManger.setText(car.getCar_owner());
                etCarBrandNum.setText(car.getCar_no());
                etAddr.setText(car.getAddress());
                etBankNum.setText(car.getBank_account());
                etBankName.setText(car.getBank());
                etDate.setText(Utils.getDataYMD(car.getJointime()+""));
                etBusType.setText(car.getRun_type_name());
            } else {
                ToastUtil.showToast(body.getMsg());
            }
        }

        @Override
        public void onFailure(Call<ApiResponse<CarInfo>> call, Throwable t) {
            ToastUtil.showToast("网络异常:获取餐车信息失败");
        }
    });
}
    @OnClick(R.id.tv_edit)
    public void onViewClicked() {
        EditCarInfoActivity.actionStart(this);
    }
}
