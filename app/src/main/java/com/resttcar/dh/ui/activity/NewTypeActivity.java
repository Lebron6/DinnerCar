package com.resttcar.dh.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.MessageWrap;
import com.resttcar.dh.tools.PreferenceUtils;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.widget.TitleManger;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 2020/5/13.
 * 新建分类
 */
public class NewTypeActivity extends BaseActivity {

    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_new)
    TextView tvNew;
    @BindView(R.id.et_input_type)
    EditText etInputType;

    public static final String TYPE_CHANGE="type_change";//更新收银台数据

    public static void actionStart(Context context, int class_id) {
        Intent intent = new Intent(context, NewTypeActivity.class);
        intent.putExtra("class_id", class_id);
        context.startActivity(intent);
    }

    private int class_Id;

    @Override
    protected void initTitle() {
        TitleManger manger = TitleManger.getInsetance();
        manger.setContext(this);
        manger.setBack();
        manger.setTitle("编辑分类");
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_new_type;
    }

    @Override
    protected void initViews() {
        mImmersionBar.transparentStatusBar().statusBarAlpha(0.2f).init();
    }

    @Override
    protected void initDatas() {
        class_Id = getIntent().getIntExtra("class_id", -1);
    }

    @OnClick(R.id.tv_new)
    public void onViewClicked() {
        if (TextUtils.isEmpty(etInputType.getText().toString())) {
            ToastUtil.showToast("请输入分类名");
            return;
        }
        String classId="";
        if(class_Id!=-1){
            classId=getIntent().getIntExtra("class_id", -1)+"";
        }
        HttpUtil.createRequest(TAG, BaseUrl.getInstence().addClass()).addClass(PreferenceUtils.getInstance().getUserToken(), etInputType.getText().toString(), classId).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body().getCode() == 1) {
                    ToastUtil.showToast("添加成功");
                    EventBus.getDefault().post( MessageWrap.getInstance(TYPE_CHANGE));
                    finish();
                } else {
                    ToastUtil.showToast(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                ToastUtil.showToast("网络异常:添加失败");
            }
        });
    }
}
