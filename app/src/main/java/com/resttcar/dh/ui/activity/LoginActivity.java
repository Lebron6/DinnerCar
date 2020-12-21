package com.resttcar.dh.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.app.DiningCarAPP;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.User;
import com.resttcar.dh.tools.PreferenceUtils;
import com.resttcar.dh.tools.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 2020/4/7.
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.cb_show_password)
    CheckBox cbShowPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    public static void actionStart(Activity context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void initTitle() {
    }
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        if (PreferenceUtils.getInstance().getLoginStatus()==true){
            MainActivity.actionStart(this);
            finish();
        }
        cbShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，隐藏密码
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    //否则显示密码
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
        cbShowPassword.setChecked(true);
        JPushInterface.stopPush(DiningCarAPP.getApplication());
    }

    @Override
    protected void initDatas() {

    }


    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        if (TextUtils.isEmpty(etAccount.getText().toString())){
            ToastUtil.showToast("请输入账号");
            return;
        }

        if (TextUtils.isEmpty(etPassword.getText().toString())){
            ToastUtil.showToast("请输入密码");
            return;
        }
        HttpUtil.createRequest(TAG, BaseUrl.getInstence().getUserLoginUrl()).userLogin(etAccount.getText().toString(),etPassword.getText().toString()).enqueue(new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                if (response.body().getCode()==1){
                    PreferenceUtils.getInstance().setUserToken(response.body().getData().getToken());
                    PreferenceUtils.getInstance().setUserID(response.body().getData().getUid());
                    PreferenceUtils.getInstance().setGroupId(response.body().getData().getGroup_id());
                    PreferenceUtils.getInstance().setLoginStatus(true);
                    MainActivity.actionStart(LoginActivity.this);
                    finish();
                }else{
                    ToastUtil.showToast(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                ToastUtil.showToast("网络异常:登录失败");
            }
        });
    }
}
