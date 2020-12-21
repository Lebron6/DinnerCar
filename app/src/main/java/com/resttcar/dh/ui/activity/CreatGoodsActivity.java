package com.resttcar.dh.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.callback.OnTypeSelectImp;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.Dish;
import com.resttcar.dh.entity.GoodSpec;
import com.resttcar.dh.entity.GoodsType;
import com.resttcar.dh.entity.MessageWrap;
import com.resttcar.dh.entity.UpLoadPic;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.tools.Utils;
import com.resttcar.dh.widget.TitleManger;
import com.resttcar.dh.widget.TypeSelectWindow;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
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

import static com.resttcar.dh.ui.activity.AddFlavorActivity.CALLBACK;
import static com.resttcar.dh.ui.activity.AddFlavorActivity.PARMS;
import static com.resttcar.dh.ui.activity.NewTypeActivity.TYPE_CHANGE;

/**
 * Created by James on 2020/4/22.
 * 通知中心
 */
public class CreatGoodsActivity extends BaseActivity implements OnTypeSelectImp {
    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.iv_lower)
    ImageView ivLower;
    @BindView(R.id.tv_goods_type)
    TextView tvGoodsType;
    @BindView(R.id.to_chose_goods_type)
    RelativeLayout toChoseGoodsType;
    @BindView(R.id.txt_goods_name)
    TextView txtGoodsName;
    @BindView(R.id.et_goods_name)
    EditText etGoodsName;
    @BindView(R.id.txt_goods_pic)
    TextView txtGoodsPic;
    @BindView(R.id.tv_goods_pic_path)
    TextView tvGoodsPicPath;
    @BindView(R.id.btn_chose_pic)
    Button btnChosePic;
    @BindView(R.id.et_goods_details)
    EditText etGoodsDetails;
    @BindView(R.id.txt_goods_price)
    TextView txtGoodsPrice;
    @BindView(R.id.et_goods_price)
    EditText etGoodsPrice;
    @BindView(R.id.txt_goods_kc)
    TextView txtGoodsKc;
    @BindView(R.id.et_goods_kc)
    EditText etGoodsKc;
    @BindView(R.id.txt_goods_kw)
    TextView txtGoodsKw;
    @BindView(R.id.tv_goods_kw)
    TextView tvGoodsKw;
    @BindView(R.id.btn_add_kw)
    Button btnAddKw;
    @BindView(R.id.layout_line)
    LinearLayout layoutLine;
    @BindView(R.id.tv_large_nums)
    TextView tvLargeNums;
    private ArrayAdapter arrayAdapter;
    private int num = 80;
    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    private int type = 2;
    private File tempFile;

    private int scaleRatio = 20;

    private int blurRadius = 1;

    public static final String DISH_DATA = "dishData";
    private List<String> strings;
    private List<GoodSpec> specs;
    private GoodsType data;
    private Dish dish;

    public static void actionStart(Context context, String data) {
        Intent intent = new Intent(context, CreatGoodsActivity.class);
        intent.putExtra(DISH_DATA, data);
        context.startActivity(intent);
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CreatGoodsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initTitle() {
        TitleManger manger = TitleManger.getInsetance();
        manger.setContext(this);
        manger.setBack();
        manger.setTitle("编辑商品");
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_creat_goods;
    }

    @Override
    protected void initViews() {
        mImmersionBar.transparentStatusBar().statusBarAlpha(0.2f).init();
        //etNoteContent是EditText
        etGoodsDetails.addTextChangedListener(new TextWatcher() {
            private CharSequence wordNum;//记录输入的字数
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordNum = s;//实时记录输入的字数
            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = num - s.length();
                //TextView显示剩余字数
                tvLargeNums.setText(s.length() + "/" + "80");
                selectionStart = etGoodsDetails.getSelectionStart();
                selectionEnd = etGoodsDetails.getSelectionEnd();
                if (wordNum.length() > num) {
                    //删除多余输入的字（不会显示出来）
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    etGoodsDetails.setText(s);
                    etGoodsDetails.setSelection(tempSelection);//设置光标在最后
                }
            }
        });
    }

    private int typeId;
    private String imageUrl;
    private String txtSpec = "";
    private String goodId;

    @Override
    protected void initDatas() {
        getClassType();
        specs = new ArrayList<>();
        if (getIntent().getStringExtra(DISH_DATA) != null) {//编辑商品

            dish = new Gson().fromJson(getIntent().getStringExtra(DISH_DATA), Dish.class);
            typeId = dish.getClassId();
            goodId = dish.getGoodId() + "";
            imageUrl = dish.getDishPic();
            tvGoodsPicPath.setText(dish.getDishPic());
            tvGoodsType.setText(dish.getClassType());
            etGoodsName.setText(dish.getDishName());
            etGoodsDetails.setText(dish.getGoodsContent());
            etGoodsPrice.setText(dish.getDishPrice() + "");
            etGoodsKc.setText(dish.getDishAmount() + "");

            if (dish.getSelectSpec() != null && dish.getSelectSpec().length() > 0) {
                try {
                    JSONArray jsonArray = new JSONArray(dish.getSelectSpec());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        GoodSpec spec = new GoodSpec();
                        spec.setName(jsonObject.has("name") ? jsonObject.getString("name") : null);
                        spec.setValue(jsonObject.has("value") ? jsonObject.getString("value") : null);
                        txtSpec = txtSpec + spec.getName() + ",";
                        specs.add(spec);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (txtSpec.length() > 1) {
                    tvGoodsKw.setText(txtSpec.substring(0, txtSpec.length() - 1));
                }

            }

        } else {//新建商品
            Log.e("新建商品", "---");
        }
    }


    private void getClassType() {
        HttpUtil.createRequest(TAG, BaseUrl.getInstence().classList()).goodsTypes(userToken).enqueue(new Callback<ApiResponse<GoodsType>>() {
            @Override
            public void onResponse(Call<ApiResponse<GoodsType>> call, Response<ApiResponse<GoodsType>> response) {
                if (response.body() != null) {
                    if (response.body().getCode() == 1) {
                        data = response.body().getData();
                        if (data != null && data.getClasses() != null && data.getClasses().size() > 0) {
                            strings = new ArrayList<>();
                            for (int i = 0; i < data.getClasses().size(); i++) {
                                strings.add(data.getClasses().get(i).getClass_name());
                            }
                            arrayAdapter = new ArrayAdapter(CreatGoodsActivity.this, R.layout.item_type, R.id.tv_popqusetion, strings);
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

    @OnClick({R.id.tv_commit, R.id.to_chose_goods_type, R.id.btn_chose_pic, R.id.btn_add_kw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:
                upLoadNewGoodsInfo();
                break;
            case R.id.to_chose_goods_type:
                TypeSelectWindow timeSelectWindow = new TypeSelectWindow(this);
                timeSelectWindow.setBottom(false);
                timeSelectWindow.showView(layoutLine, arrayAdapter, this);
                break;
            case R.id.btn_chose_pic:
                // 3、调用从图库选取图片方法
                //权限判断
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到相册
                    gotoPhoto();
                }
                break;
            case R.id.btn_add_kw:
                if (dish==null||dish.getSelectSpec()==null||TextUtils.isEmpty(dish.getSelectSpec())){
                    AddFlavorActivity.actionStartForResult(this,"");
                }else{
                    AddFlavorActivity.actionStartForResult(this,new Gson().toJson(specs));
                }
                break;
        }
    }

    private void upLoadNewGoodsInfo() {
        if (tvGoodsType.getText().toString().equals("请选择")) {
            ToastUtil.showToast("请选择商品分类");
            return;
        }
        if (TextUtils.isEmpty(etGoodsName.getText().toString())) {
            ToastUtil.showToast("请输入商品名称");
            return;
        }
        if (TextUtils.isEmpty(tvGoodsPicPath.getText().toString())) {
            ToastUtil.showToast("请上传商品图片");
            return;
        }
        if (TextUtils.isEmpty(etGoodsDetails.getText().toString())) {
            ToastUtil.showToast("请输入商品详情");
            return;
        }
        if (TextUtils.isEmpty(etGoodsPrice.getText().toString())) {
            ToastUtil.showToast("请输入商品价格");
            return;
        }
        if (TextUtils.isEmpty(etGoodsKc.getText().toString())) {
            ToastUtil.showToast("请输入商品库存");
            return;
        }
        HttpUtil.createRequest(TAG, BaseUrl.getInstence().addGoods()).addGoods(userToken, goodId, typeId, etGoodsName.getText().toString(), etGoodsDetails.getText().toString(),
                tvGoodsPicPath.getText().toString(), etGoodsPrice.getText().toString(), etGoodsKc.getText().toString(), new Gson().toJson(specs)).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body() != null) {
                    if (response.body().getCode() == 1) {
                        ToastUtil.showToast("商品编辑成功");
                        EventBus.getDefault().post( MessageWrap.getInstance(TYPE_CHANGE));
                        finish();
                    } else {
                        ToastUtil.showToast(response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                ToastUtil.showToast("网络异常:商品编辑失败");
            }
        });
    }

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
                        tvGoodsPicPath.setText(cropImagePath.substring(cropImagePath.lastIndexOf("/") + 1));
                        Log.e("图片路径", cropImagePath);
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
                                        tvGoodsPicPath.setText(imageUrl);
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
           case PARMS:
               if (intent!=null&&intent.getExtras()!=null){
                   Bundle bundle = intent.getExtras();
                   final String result = bundle.getString(CALLBACK);
                   Log.e("返回的数据",result);
                   if (specs!=null){
                       specs.clear();
                   }

                   txtSpec="";
                   if (result != null) {
                       try {
                           JSONArray jsonArray = new JSONArray(result);
                           for (int i = 0; i < jsonArray.length(); i++) {
                               JSONObject jsonObject = jsonArray.getJSONObject(i);
                               GoodSpec spec = new GoodSpec();
                               spec.setName(jsonObject.has("name") ? jsonObject.getString("name") : null);
                               spec.setValue(jsonObject.has("value") ? jsonObject.getString("value") : null);
                               txtSpec = txtSpec + spec.getName() + ",";
                               specs.add(spec);
                           }
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                       if (txtSpec.length() > 1) {
                           tvGoodsKw.setText(txtSpec.substring(0, txtSpec.length() - 1));
                       }
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


    @Override
    public void select(int postion) {
        tvGoodsType.setText(strings.get(postion));
        typeId = data.getClasses().get(postion).getClass_id();
    }

    private class Specs {
        List<GoodSpec> list;

        public List<GoodSpec> getList() {
            return list;
        }

        public void setList(List<GoodSpec> list) {
            this.list = list;
        }
    }


}
