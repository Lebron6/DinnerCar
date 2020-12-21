package com.resttcar.dh.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.callback.ShopCartImp;
import com.resttcar.dh.callback.SpecSelectImpl;
import com.resttcar.dh.entity.Dish;
import com.resttcar.dh.entity.ShopCart;
import com.resttcar.dh.tools.RecyclerViewHelper;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.ui.adapter.DialogSpecAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by cheng on 16-12-22.
 */
public class SpecDialog extends Dialog implements SpecSelectImpl {

    RecyclerView recyclerView;
    DialogSpecAdapter adapter;
    Context context;
    private ShopCartImp shopCartImp;
    private int position; //当前规格位置
    private RelativeLayout layoutOk;
    private Dish dish;
    private ImageView iv_pic;
    private ImageView iv_close;
    private TextView tv_title;
    private TextView tv_price;
    private TextView tv_amount;
    private ShopCart shopCart;
    private List<String> specs=new ArrayList<>();

    public SpecDialog(Context context, int themeResId, int position, Dish dish, ShopCart shopCart) {
        super(context, themeResId);
        this.context = context;
        this.position = position;
        this.dish = dish;
        this.shopCart = shopCart;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_spec);
        recyclerView = findViewById(R.id.rv_spec);
        layoutOk = findViewById(R.id.layout_ok);
        iv_pic = findViewById(R.id.iv_pic);
        iv_close = findViewById(R.id.iv_close);
        tv_amount = findViewById(R.id.tv_stock);
        tv_title = findViewById(R.id.right_dish_name);
        tv_price = findViewById(R.id.tv_price);
        Glide.with(context).load(BaseUrl.getInstence().ipAddress + dish.getDishPic()).into(iv_pic);
        tv_title.setText(dish.getDishName());
        tv_price.setText("¥" + dish.getDishPrice());
        tv_amount.setText("库存：" + dish.getDishAmount());
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        layoutOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = 0;
                for (int i = 0; i < shopCart.getAllDishs().size(); i++) {
                    if(shopCart.getAllDishs().get(i).getGoodId() == dish.getGoodId()){
                        num+=shopCart.getAllDishs().get(i).getNum();
                    }
                }
                List<String> list=new ArrayList<>();
                for (int i = 0; i <dish.getValuesBeans().size() ; i++) {
                    if (!TextUtils.isEmpty(dish.getValuesBeans().get(i).getSelectValue())){
                        list.add(dish.getValuesBeans().get(i).getSelectValue());
                    }
                }
                if (list.size()<=0){
                    ToastUtil.showToast("请先选择规格");
                    return;
                }
                Dish dish1=new Dish();
                dish1.setDishName(dish.getDishName());
                dish1.setDishPrice(dish.getDishPrice());
                dish1.setDishAmount(dish.getDishAmount());
                dish1.setDishPic(dish.getDishPic());
                dish1.setSelectSpec(list.toString());
                dish1.setGoodId(dish.getGoodId());
                dish1.setNum(num);
                if (shopCart.addShoppingSingle(dish1)) {//如果选择了规格，则需要在购物车新增一个条目
                    if (shopCartImp != null){
                        shopCartImp.add(v, position);
                        dismiss();
                    }
                }
            }
        });
        adapter = new DialogSpecAdapter(context,this);
        adapter.setDatas(dish.getValuesBeans());

        RecyclerViewHelper.initRecyclerViewV(context, recyclerView, false, adapter);
    }

    public void setShopCartImp(ShopCartImp shopCartImp) {
        this.shopCartImp = shopCartImp;
    }


    @Override
    public void select(int postion, String spec) {
                    specs.add(spec);
    }
}
