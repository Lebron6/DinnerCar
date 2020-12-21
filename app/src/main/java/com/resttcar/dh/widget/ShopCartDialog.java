package com.resttcar.dh.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.resttcar.dh.R;
import com.resttcar.dh.callback.ShopCartImp;
import com.resttcar.dh.entity.ShopCart;
import com.resttcar.dh.tools.ArithUtil;
import com.resttcar.dh.ui.activity.OrderCommitActivity;
import com.resttcar.dh.ui.adapter.PopupDishAdapter;


/**
 * Created by cheng on 16-12-22.
 */
public class ShopCartDialog extends Dialog implements View.OnClickListener, ShopCartImp {

    private LinearLayout linearLayout, bottomLayout, clearLayout;
    private FrameLayout shopingcartLayout;
    private ShopCart shopCart;
    private TextView tvSett;
    private TextView totalPriceTextView;
    private TextView totalPriceNumTextView;
    private TextView tvOldPrice;
    private View viewBottom;
    private int showPosition;
    private TextView tvSettlement;
    private RecyclerView recyclerView;
    private PopupDishAdapter dishAdapter;
    private ShopCartDialogImp shopCartDialogImp;
    private Context context;

    public static final int BOTTOM_VISIBLE = 0;
    public static final int BOTTOM_GONE = 1;

    public ShopCartDialog(Context context, ShopCart shopCart, int themeResId, int showPosition) {
        super(context, themeResId);
        this.shopCart = shopCart;
        this.context = context;
        this.showPosition = showPosition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_popupview);
        linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
        viewBottom = (View) findViewById(R.id.view_bottom);
        tvSett = (TextView) findViewById(R.id.tv_settlement);
        tvOldPrice = (TextView) findViewById(R.id.tv_old_price);
        if (showPosition == BOTTOM_VISIBLE) {
            viewBottom.setVisibility(View.VISIBLE);
            tvOldPrice.setVisibility(View.GONE);
        } else {
            viewBottom.setVisibility(View.GONE);
            tvOldPrice.setVisibility(View.VISIBLE);
        }
        clearLayout = (LinearLayout) findViewById(R.id.clear_layout);
        tvSettlement = (TextView) findViewById(R.id.tv_settlement);
        shopingcartLayout = (FrameLayout) findViewById(R.id.shopping_cart_layout);
        totalPriceTextView = (TextView) findViewById(R.id.shopping_cart_total_tv);
        totalPriceNumTextView = (TextView) findViewById(R.id.shopping_cart_total_num);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        shopingcartLayout.setOnClickListener(this);
        tvSett.setOnClickListener(this);
        clearLayout.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dishAdapter = new PopupDishAdapter(getContext(), shopCart);
        recyclerView.setAdapter(dishAdapter);
        dishAdapter.setShopCartImp(this);
        showTotalPrice();
    }

    @Override
    public void show() {
        super.show();
        animationShow(1000);
    }

    @Override
    public void dismiss() {
        animationHide(1000);
    }

    private double realPrice;

    private void showTotalPrice() {
        if (shopCart != null && shopCart.getShoppingTotalPrice() > 0) {
            totalPriceTextView.setVisibility(View.VISIBLE);
            tvSettlement.setVisibility(View.VISIBLE);
            if (showPosition == BOTTOM_VISIBLE) {
                totalPriceTextView.setText("￥ " + shopCart.getShoppingTotalPrice());
            } else {
                realPrice = 0;
                for (int i = 0; i < shopCart.getAllDishs().size(); i++) {
                    realPrice = ArithUtil.add(realPrice, shopCart.getAllDishs().get(i).getTypeDishPrice());
                }
                totalPriceTextView.setText("￥ " + realPrice);
                tvOldPrice.setText("￥ " + shopCart.getShoppingTotalPrice());
                // 中间加横线 ， 添加Paint.ANTI_ALIAS_FLAG是线会变得清晰去掉锯齿
                tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            }
            tvOldPrice.setText("￥ " + shopCart.getShoppingTotalPrice());
            totalPriceNumTextView.setVisibility(View.VISIBLE);
            totalPriceNumTextView.setText("" + shopCart.getShoppingAccount());
        } else {
            totalPriceTextView.setVisibility(View.GONE);
            totalPriceNumTextView.setVisibility(View.GONE);
            tvSettlement.setVisibility(View.GONE);
            tvOldPrice.setVisibility(View.GONE);
        }
    }

    private void animationShow(int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(linearLayout, "translationY", 1000, 0).setDuration(mDuration)
        );
        animatorSet.start();
    }

    private void animationHide(int mDuration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(linearLayout, "translationY", 0, 1000).setDuration(mDuration)
        );
        animatorSet.start();

        if (shopCartDialogImp != null) {
            shopCartDialogImp.dialogDismiss();
        }

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ShopCartDialog.super.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shopping_cart_layout:
                this.dismiss();
                break;
            case R.id.clear_layout:
                clear();
                break;
            case R.id.tv_settlement:
                OrderCommitActivity.actionStart(context, new Gson().toJson(shopCart));
                break;
        }
    }

    @Override
    public void add(View view, int postion) {
        showTotalPrice();
    }

    @Override
    public void remove(View view, int postion) {
        showTotalPrice();
        if (shopCart.getShoppingAccount() == 0) {
            this.dismiss();
        }
    }

    public ShopCartDialogImp getShopCartDialogImp() {
        return shopCartDialogImp;
    }

    public void setShopCartDialogImp(ShopCartDialogImp shopCartDialogImp) {
        this.shopCartDialogImp = shopCartDialogImp;
    }

    public interface ShopCartDialogImp {
        public void dialogDismiss();
    }

    public void clear() {
        shopCart.clear();
        showTotalPrice();
        if (shopCart.getShoppingAccount() == 0) {
            this.dismiss();
        }
    }
}
