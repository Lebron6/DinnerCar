package com.resttcar.dh.ui.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.callback.ShopCartImp;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.Dish;
import com.resttcar.dh.entity.DishMenu;
import com.resttcar.dh.entity.MaterialIndex;
import com.resttcar.dh.entity.ShopCart;
import com.resttcar.dh.tools.ArithUtil;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.ui.activity.RawOrderCommitActivity;
import com.resttcar.dh.ui.adapter.LeftMenuAdapter;
import com.resttcar.dh.ui.adapter.RightDishAdapter;
import com.resttcar.dh.widget.ShopCartDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 2020/4/8.
 * 原料采购
 */
public class RawFragment extends BaseFragment implements LeftMenuAdapter.onItemSelectedListener, ShopCartImp, ShopCartDialog.ShopCartDialogImp {
    @BindView(R.id.left_menu)
    RecyclerView leftMenu;
    @BindView(R.id.right_menu)
    RecyclerView rightMenu;
    @BindView(R.id.shopping_cart_total_tv)
    TextView shoppingCartTotalTv;
    @BindView(R.id.tv_settlement)
    TextView tvSettlement;
    @BindView(R.id.right_menu_tv)
    TextView headerView;
    @BindView(R.id.right_menu_item)
    LinearLayout headerLayout;
    @BindView(R.id.shopping_cart)
    ImageView shoppingCart;
    @BindView(R.id.shopping_cart_layout)
    FrameLayout shopingCartLayout;
    @BindView(R.id.shopping_cart_total_num)
    TextView shoppingCartTotalNum;
    @BindView(R.id.main_layout)
    LinearLayout mainLayout;
    Unbinder unbinder;
    @BindView(R.id.tv_old_price)
    TextView tvOldPrice;
    Unbinder unbinder1;


    private DishMenu headMenu;
    private LeftMenuAdapter leftAdapter;
    private RightDishAdapter rightAdapter;
    private ArrayList<DishMenu> dishMenuList;//数据源
    private boolean leftClickType = false;//左侧菜单点击引发的右侧联动
    private ShopCart shopCart;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_raw;
    }

    private void initAdapter() {
        leftAdapter = new LeftMenuAdapter(getActivity(), dishMenuList);
        rightAdapter = new RightDishAdapter(getActivity(), dishMenuList, shopCart);
        rightMenu.setAdapter(rightAdapter);
        leftMenu.setAdapter(leftAdapter);
        leftAdapter.addItemSelectedListener(this);
        rightAdapter.setShopCartImp(this);
        initHeadView();
    }

    private void initHeadView() {
        headMenu = rightAdapter.getMenuOfMenuByPosition(0);
        headerLayout.setContentDescription("0");
        headerView.setText(headMenu.getMenuName());
    }

    @Override
    protected void initViews() {
        leftMenu.setLayoutManager(new LinearLayoutManager(getActivity()));
        rightMenu.setLayoutManager(new LinearLayoutManager(getActivity()));
        rightMenu.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.canScrollVertically(1) == false) {//无法下滑
                    showHeadView();
                    return;
                }
                View underView = null;
                if (dy > 0)
                    underView = rightMenu.findChildViewUnder(headerLayout.getX(), headerLayout.getMeasuredHeight() + 1);
                else
                    underView = rightMenu.findChildViewUnder(headerLayout.getX(), 0);
                if (underView != null && underView.getContentDescription() != null) {
                    int position = Integer.parseInt(underView.getContentDescription().toString());
                    DishMenu menu = rightAdapter.getMenuOfMenuByPosition(position);

                    if (leftClickType || !menu.getMenuName().equals(headMenu.getMenuName())) {
                        if (dy > 0 && headerLayout.getTranslationY() <= 1 && headerLayout.getTranslationY() >= -1 * headerLayout.getMeasuredHeight() * 4 / 5 && !leftClickType) {// underView.getTop()>9
                            int dealtY = underView.getTop() - headerLayout.getMeasuredHeight();
                            headerLayout.setTranslationY(dealtY);
//                            Log.e(TAG, "onScrolled: "+headerLayout.getTranslationY()+"   "+headerLayout.getBottom()+"  -  "+headerLayout.getMeasuredHeight() );
                        } else if (dy < 0 && headerLayout.getTranslationY() <= 0 && !leftClickType) {
                            headerView.setText(menu.getMenuName());
                            int dealtY = underView.getBottom() - headerLayout.getMeasuredHeight();
                            headerLayout.setTranslationY(dealtY);
//                            Log.e(TAG, "onScrolled: "+headerLayout.getTranslationY()+"   "+headerLayout.getBottom()+"  -  "+headerLayout.getMeasuredHeight() );
                        } else {
                            headerLayout.setTranslationY(0);
                            headMenu = menu;
                            headerView.setText(headMenu.getMenuName());
                            for (int i = 0; i < dishMenuList.size(); i++) {
                                if (dishMenuList.get(i) == headMenu) {
                                    leftAdapter.setSelectedNum(i);
                                    break;
                                }
                            }
                            if (leftClickType) leftClickType = false;
                            Log.e("onScrolled: ", menu.getMenuName());
                        }
                    }
                }
            }
        });

        shopingCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCart(view);
            }
        });
        getRawData();
    }

    private void getRawData() {
        HttpUtil.createRequest(TAG, BaseUrl.getInstence().materialIndex()).materialIndex(userToken).enqueue(new Callback<ApiResponse<MaterialIndex>>() {
            @Override
            public void onResponse(Call<ApiResponse<MaterialIndex>> call, Response<ApiResponse<MaterialIndex>> response) {
                if (response.body().getCode() == 1) {
                    ApiResponse<MaterialIndex> body = response.body();
                    if (body.getData() != null && body.getData().getMaterial_types() != null && body.getData().getMaterial_types().size() > 0) {
                        dishMenuList = new ArrayList<>();

                        for (int i = 0; i < body.getData().getMaterial_types().size(); i++) {
                            ArrayList<Dish> dishs = new ArrayList<>();
                            if (body.getData().getMaterial_types() != null && body.getData().getMaterial_types().size() > 0) {
                                if (body.getData().getMaterial_types().get(i).getList() != null && body.getData().getMaterial_types().get(i).getList().size() > 0) {
                                    for (int j = 0; j < body.getData().getMaterial_types().get(i).getList().size(); j++) {
                                        MaterialIndex.MaterialTypesBean.ListBean bean = body.getData().getMaterial_types().get(i).getList().get(j);
                                        Dish dish = new Dish();
                                        dish.setDishName(bean.getName());
                                        dish.setDishPrice(Double.valueOf(bean.getPrice()));
                                        dish.setDishAmount(bean.getLast_amount());
                                        dish.setDishPic(bean.getImage());
                                        dish.setTypeId(bean.getType_id());
                                        dish.setMaterial_id(bean.getMaterial_id());
                                        dish.setIs_discount(bean.getIs_discount());
                                        dish.setFull(bean.getFull());
                                        dish.setDiscount(bean.getDiscount());
                                        dishs.add(dish);
                                    }
                                }
                            }
                            DishMenu menus = new DishMenu(body.getData().getMaterial_types().get(i).getType_name(), dishs);
                            dishMenuList.add(menus);
                        }
                        shopCart = new ShopCart();
                        initAdapter();
                    } else {
                        ToastUtil.showToast("原料暂无数据");
                    }
                } else {
                    ToastUtil.showToast(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<MaterialIndex>> call, Throwable t) {
                ToastUtil.showToast("网络异常:获取原料数据失败");
            }
        });
    }


    private void showHeadView() {
        headerLayout.setTranslationY(0);
        View underView = rightMenu.findChildViewUnder(headerView.getX(), 0);
        if (underView != null && underView.getContentDescription() != null) {
            int position = Integer.parseInt(underView.getContentDescription().toString());
            DishMenu menu = rightAdapter.getMenuOfMenuByPosition(position + 1);
            headMenu = menu;
            headerView.setText(headMenu.getMenuName());
            for (int i = 0; i < dishMenuList.size(); i++) {
                if (dishMenuList.get(i) == headMenu) {
                    leftAdapter.setSelectedNum(i);
                    break;
                }
            }
        }
    }

    private void showCart(View view) {
        if (shopCart != null && shopCart.getShoppingAccount() > 0) {
            ShopCartDialog dialog = new ShopCartDialog(getActivity(), shopCart, R.style.cartdialog, ShopCartDialog.BOTTOM_GONE);
            Window window = dialog.getWindow();
            dialog.setShopCartDialogImp(this);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setCancelable(true);
            dialog.show();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM;
            params.dimAmount = 0.5f;
            window.setAttributes(params);
        }
    }

    @Override
    protected void initDatas() {
    }

    @Override
    public void onLeftItemSelected(int postion, DishMenu menu) {
        int sum = 0;
        for (int i = 0; i < postion; i++) {
            sum += dishMenuList.get(i).getDishList().size() + 1;
        }
        LinearLayoutManager layoutManager = (LinearLayoutManager) rightMenu.getLayoutManager();
        layoutManager.scrollToPositionWithOffset(sum, 0);
        leftClickType = true;
    }

    @Override
    public void dialogDismiss() {
        showTotalPrice();
        rightAdapter.notifyDataSetChanged();
    }

    @Override
    public void add(View view, int position) {

        showTotalPrice();
    }

    private double realPrice;

    private void showTotalPrice() {
        if (shopCart != null && shopCart.getShoppingTotalPrice() > 0) {
            shoppingCartTotalTv.setVisibility(View.VISIBLE);
            tvSettlement.setVisibility(View.VISIBLE);
            shoppingCartTotalNum.setVisibility(View.VISIBLE);
            shoppingCartTotalNum.setText("" + shopCart.getShoppingAccount());
            realPrice = 0;
            for (int i = 0; i < shopCart.getAllDishs().size(); i++) {
                realPrice = ArithUtil.add(realPrice, shopCart.getAllDishs().get(i).getTypeDishPrice())  ;
            }
            shoppingCartTotalTv.setText("￥ " + realPrice);
            tvOldPrice.setText("￥ " + shopCart.getShoppingTotalPrice());
            // 中间加横线 ， 添加Paint.ANTI_ALIAS_FLAG是线会变得清晰去掉锯齿
            tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        } else {
            tvSettlement.setVisibility(View.GONE);
            shoppingCartTotalTv.setVisibility(View.GONE);
            shoppingCartTotalNum.setVisibility(View.GONE);
            tvOldPrice.setVisibility(View.GONE);
        }
    }

    @Override
    public void remove(View view, int position) {
        showTotalPrice();
    }


    @OnClick(R.id.tv_settlement)
    public void onViewClicked() {
        RawOrderCommitActivity.actionStart(getActivity(), new Gson().toJson(shopCart));
    }

}
