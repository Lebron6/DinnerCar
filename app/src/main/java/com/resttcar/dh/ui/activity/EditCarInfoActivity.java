package com.resttcar.dh.ui.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.callback.OnTypeSelectImp;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.EditCarInfo;
import com.resttcar.dh.entity.UpLoadPic;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.tools.Utils;
import com.resttcar.dh.widget.GlideCircleTransform;
import com.resttcar.dh.widget.TitleManger;
import com.resttcar.dh.widget.TypeSelectWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 2020/4/22.
 * 编辑餐车信息
 */
public class EditCarInfoActivity extends BaseActivity {

    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.et_car_name)
    EditText etCarName;
    @BindView(R.id.iv_car_pic)
    ImageView ivCarPic;
    @BindView(R.id.et_car_num)
    EditText etCarNum;
    @BindView(R.id.et_brand)
    TextView etBrand;
    @BindView(R.id.et_car_manger)
    EditText etCarManger;
    @BindView(R.id.et_car_brand_num)
    EditText etCarBrandNum;
    @BindView(R.id.et_addr)
    EditText etAddr;
    @BindView(R.id.et_bank_num)
    EditText etBankNum;
    @BindView(R.id.et_bank_name)
    EditText etBankName;
    @BindView(R.id.et_date)
    TextView tvDate;
    @BindView(R.id.et_bus_type)
    TextView etBusType;
    @BindView(R.id.view_brand)
    View viewBrand;
    @BindView(R.id.view_type)
    View viewType;
    private ApiResponse<EditCarInfo> body;
    private List<String> brands;
    private List<String> types;
    private EditCarInfo.CarBean car;
    private ArrayAdapter brandAdapter;
    private ArrayAdapter typeAdapter;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, EditCarInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleManger manger = TitleManger.getInsetance();
        manger.setContext(this);
        manger.setBack();
        manger.setTitle("编辑餐车");
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_edit_car_info;
    }

    @Override
    protected void initViews() {
        mImmersionBar.transparentStatusBar().statusBarAlpha(0.2f).init();
    }

    private int brand_id;
    private int run_type_id;
    private String province;
    private String city;
    private String district;

    @Override
    protected void initDatas() {
        HttpUtil.createRequest(TAG, BaseUrl.getInstence().editCarInfo()).carEditIndex(userToken).enqueue(new Callback<ApiResponse<EditCarInfo>>() {
            @Override
            public void onResponse(Call<ApiResponse<EditCarInfo>> call, Response<ApiResponse<EditCarInfo>> response) {
                body = response.body();
                if (body.getCode() == 1) {
                    car = body.getData().getCar();
                    etCarName.setText(car.getCar_name());
                    imageUrl = car.getCar_avatar();
                    province=car.getProvince();
                    city=car.getCity();
                    district=car.getDistrict();
                    Glide.with(EditCarInfoActivity.this).load(BaseUrl.getInstence().ipAddress + car.getCar_avatar()).bitmapTransform(new GlideCircleTransform(EditCarInfoActivity.this)).into(ivCarPic);
                    etCarNum.setText(car.getCar_id()+"");
                    brands = new ArrayList<>();
                    for (int i = 0; i < body.getData().getBrands().size(); i++) {
                        if (car.getBrand_id() == body.getData().getBrands().get(i).getBrand_id()) {
                            etBrand.setText(body.getData().getBrands().get(i).getBrand_name());

                        }
                        brands.add(body.getData().getBrands().get(i).getBrand_name());
                    }
                    brandAdapter = new ArrayAdapter(EditCarInfoActivity.this, R.layout.item_type, R.id.tv_popqusetion, brands);
                    brand_id = car.getBrand_id();
                    etCarManger.setText(car.getCar_owner());
                    etCarBrandNum.setText(car.getCar_no());
                    etAddr.setText(car.getAddress());
                    etBankNum.setText(car.getBank_account());
                    etBankName.setText(car.getBank());
                    tvDate.setText(Utils.getDataYMD(car.getJointime() + ""));
                    types = new ArrayList<>();
                    for (int i = 0; i < body.getData().getRun_types().size(); i++) {
                        if (car.getRun_type_id() == body.getData().getRun_types().get(i).getRun_type_id()) {
                            etBusType.setText(body.getData().getRun_types().get(i).getRun_type_name());
                        }
                        types.add(body.getData().getRun_types().get(i).getRun_type_name());
                    }
                    typeAdapter = new ArrayAdapter(EditCarInfoActivity.this, R.layout.item_type, R.id.tv_popqusetion, types);
                    run_type_id = car.getRun_type_id();
                } else {
                    ToastUtil.showToast(body.getMsg());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<EditCarInfo>> call, Throwable t) {
                ToastUtil.showToast("网络异常:获取餐车信息失败");
            }
        });
    }

    OnTypeSelectImp brandImpl = new OnTypeSelectImp() {
        @Override
        public void select(int postion) {
            brand_id = body.getData().getBrands().get(postion).getBrand_id();
            etBrand.setText(brands.get(postion));
        }
    };
    OnTypeSelectImp typeImpl = new OnTypeSelectImp() {
        @Override
        public void select(int postion) {
            run_type_id =  body.getData().getRun_types().get(postion).getRun_type_id();
            etBusType.setText(types.get(postion));
        }
    };
    private int startYear, startMonth, startDay;

    @OnClick({R.id.tv_commit, R.id.et_date, R.id.iv_car_pic, R.id.et_brand, R.id.et_bus_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_brand:
                TypeSelectWindow brandWindow = new TypeSelectWindow(this);
                brandWindow.showView(viewBrand, brandAdapter, brandImpl);
                break;
            case R.id.et_bus_type:
                TypeSelectWindow typeSelectWindow = new TypeSelectWindow(this);
                typeSelectWindow.showView(viewType, typeAdapter, typeImpl);
                break;
            case R.id.tv_commit:
                if (TextUtils.isEmpty(etCarName.getText().toString())) {
                    ToastUtil.showToast("请输入餐车名称");
                    return;
                }
                if (TextUtils.isEmpty(imageUrl)) {
                    ToastUtil.showToast("请上传餐车头像");
                    return;
                }

                if (TextUtils.isEmpty(etBrand.getText().toString())) {
                    ToastUtil.showToast("请输入餐车品牌");
                    return;
                }
                if (TextUtils.isEmpty(etCarManger.getText().toString())) {
                    ToastUtil.showToast("请输入餐车归属者");
                    return;
                }
                if (TextUtils.isEmpty(etCarBrandNum.getText().toString())) {
                    ToastUtil.showToast("请输入车牌号");
                    return;
                }
                if (TextUtils.isEmpty(etAddr.getText().toString())) {
                    ToastUtil.showToast("请输入常规地址");
                    return;
                }
                if (TextUtils.isEmpty(etBankNum.getText().toString())) {
                    ToastUtil.showToast("请输入银行账号");
                    return;
                }
                if (TextUtils.isEmpty(etBankName.getText().toString())) {
                    ToastUtil.showToast("请输入开户行");
                    return;
                }
                if (TextUtils.isEmpty(tvDate.getText().toString())) {
                    ToastUtil.showToast("请选择投放日期");
                    return;
                }
                if (TextUtils.isEmpty(etBusType.getText().toString())) {
                    ToastUtil.showToast("请输入经营方式");
                    return;
                }
                commitCarInfo();
                break;
            case R.id.et_date:
                final Calendar ca = Calendar.getInstance();
                startYear = ca.get(Calendar.YEAR);
                startMonth = ca.get(Calendar.MONTH);
                startDay = ca.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(this, R.style.MyDatePickerDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String time = (year + "-" + (monthOfYear + 1)
                                        + "-" + dayOfMonth);
                                tvDate.setText(time);
                            }
                        }, startYear, startMonth, startDay).show();
                break;
            case R.id.iv_car_pic:
                gotoPhoto();
                break;
        }
    }

    private void commitCarInfo() {
        HttpUtil.createRequest(TAG, BaseUrl.getInstence().editCarInfo()).editCarInfo(userToken, etCarName.getText().toString(), imageUrl, etCarManger.getText().toString(), etCarBrandNum.getText().toString(), etAddr.getText().toString(), etBankName.getText().toString(), etBankNum.getText().toString(), tvDate.getText().toString(),province,city,district,brand_id,run_type_id).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body().getCode() == 1) {
                    ToastUtil.showToast("餐车信息修改成功");
                    finish();
                } else {
                    ToastUtil.showToast(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                ToastUtil.showToast("网络异常:修改餐车信息失败");
            }
        });
    }

    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    private int type = 1;
    private File tempFile;
    private String imageUrl;

    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    gotoClipActivity(uri);
                }
                break;
            case REQUEST_CROP_PHOTO:  //剪切图片返回
                if (resultCode == RESULT_OK) {
                    final Uri uri = intent.getData();
                    if (uri == null) {
                        return;
                    } else {
                        final String cropImagePath = Utils.getRealFilePathFromUri(this, uri);
                        File file = new File(cropImagePath);
                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);


                        HttpUtil.createRequest(TAG, BaseUrl.getInstence().upLoadPic()).upLoadPic(part).enqueue(new Callback<ApiResponse<UpLoadPic>>() {
                            @Override
                            public void onResponse(Call<ApiResponse<UpLoadPic>> call, Response<ApiResponse<UpLoadPic>> response) {
                                if (response.body() != null) {
                                    if (response.body().getCode() == 1) {
                                        ToastUtil.showToast("图片已成功");
                                        imageUrl = response.body().getData().getUrl();
                                        Glide.with(EditCarInfoActivity.this).load(BaseUrl.getInstence().ipAddress + imageUrl).bitmapTransform(new GlideCircleTransform(EditCarInfoActivity.this)).into(ivCarPic);
                                    } else {
                                        ToastUtil.showToast(response.body().getMsg());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ApiResponse<UpLoadPic>> call, Throwable t) {
                                ToastUtil.showToast("网络异常:上传失败");
                            }
                        });
                    }

                }
                break;
        }
    }

    /**
     * 打开截图界面
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipImageActivity.class);
        intent.putExtra("type", type);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }
}
