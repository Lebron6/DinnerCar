package com.resttcar.dh.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.resttcar.dh.R;
import com.resttcar.dh.entity.GoodSpec;
import com.resttcar.dh.tools.RecyclerViewHelper;
import com.resttcar.dh.ui.adapter.AddFlavorAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by James on 2020/5/12.
 */
public class AddFlavorActivity extends BaseActivity {
    AddFlavorAdapter addFlavorAdapter;
    @BindView(R.id.view_status_bar)
    TextView viewStatusBar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.rv_flavor)
    RecyclerView rvFlavor;
    @BindView(R.id.layout_add_flavor)
    LinearLayout layoutAddFlavor;
    public static final String SPEC_DATA = "specData";
    public static final int PARMS = 93;
    private List<GoodSpec> specs;

    public static void actionStartForResult(Activity context, String data) {
        Intent intent = new Intent(context, AddFlavorActivity.class);
        intent.putExtra(SPEC_DATA, data);
        context.startActivityForResult(intent, PARMS);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_add_flavor;
    }

    @Override
    protected void initViews() {
        mImmersionBar.transparentStatusBar().statusBarAlpha(0.2f).init();
        addFlavorAdapter = new AddFlavorAdapter(this);
        RecyclerViewHelper.initRecyclerViewV(this, rvFlavor, false, addFlavorAdapter);
    }

    @Override
    protected void initDatas() {
        if (getIntent().getStringExtra(SPEC_DATA) != null) {
            specs = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(getIntent().getStringExtra(SPEC_DATA));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    GoodSpec spec = new GoodSpec();
                    spec.setName(jsonObject.has("name") ? jsonObject.getString("name") : null);
                    spec.setValue(jsonObject.has("value") ? jsonObject.getString("value") : null);
                    specs.add(spec);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            addFlavorAdapter.setDatas(specs);
        }
    }

    public static final String CALLBACK = "callBack";

    @OnClick({R.id.tv_commit, R.id.layout_add_flavor})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:
                if (specs != null && specs.size() > 0) {
                    for (int i = 0; i < specs.size(); i++) {
                        if (TextUtils.isEmpty(specs.get(i).getValue())) {
                            specs.remove(i);
                        }
                    }
                    Log.e("上传商品规格",specs.toString());
                }

                Intent intent = new Intent();
                intent.putExtra(CALLBACK, new Gson().toJson(specs));
                setResult(3, intent);
                finish();
                break;
            case R.id.layout_add_flavor:
                GoodSpec spec = new GoodSpec();
                specs.add(0, spec);
                addFlavorAdapter.setDatas(specs);
                break;
        }
    }
}
