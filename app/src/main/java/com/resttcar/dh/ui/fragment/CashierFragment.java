package com.resttcar.dh.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.callback.ShopCartImp;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.ConfirmOrderData;
import com.resttcar.dh.entity.DeskIndex;
import com.resttcar.dh.entity.Dish;
import com.resttcar.dh.entity.DishMenu;
import com.resttcar.dh.entity.GetHang;
import com.resttcar.dh.entity.HangData;
import com.resttcar.dh.entity.MessageWrap;
import com.resttcar.dh.entity.ShopCart;
import com.resttcar.dh.tools.AppManager;
import com.resttcar.dh.tools.ArithUtil;
import com.resttcar.dh.tools.PreferenceUtils;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.ui.activity.LoginActivity;
import com.resttcar.dh.ui.activity.OrderCollectionActivity;
import com.resttcar.dh.ui.activity.OrderCommitActivity;
import com.resttcar.dh.ui.adapter.DishCashierAdapter;
import com.resttcar.dh.ui.adapter.LeftMenuAdapter;
import com.resttcar.dh.widget.ShopCartDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.resttcar.dh.api.Constant.CHANGE_STATUS;
import static com.resttcar.dh.api.Constant.DELETE;
import static com.resttcar.dh.api.Constant.GETHANG;
import static com.resttcar.dh.ui.activity.NewTypeActivity.TYPE_CHANGE;
import static com.resttcar.dh.ui.activity.OrderCommitSucActivity.PAY_SUCCESS;

/**
 * Created by James on 2020/4/8.
 */
public class CashierFragment extends BaseFragment implements LeftMenuAdapter.onItemSelectedListener, ShopCartImp, ShopCartDialog.ShopCartDialogImp {
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
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;

    private DishMenu headMenu;
    private LeftMenuAdapter leftAdapter;
    private DishCashierAdapter rightAdapter;
    private ArrayList<DishMenu> dishMenuList;//数据源
    private boolean leftClickType = false;//左侧菜单点击引发的右侧联动
    private ShopCart shopCart;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_cash;
    }

    @Override
    protected void initViews() {
        useEventBus=true;
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
        getCashierData();
    }
//获取收银台信息
private void getCashierData(){
    HttpUtil.createRequest(TAG, BaseUrl.getInstence().getdeskIndexData()).getDeskIndex(userToken).enqueue(new Callback<ApiResponse<DeskIndex>>() {
        @Override
        public void onResponse(Call<ApiResponse<DeskIndex>> call, Response<ApiResponse<DeskIndex>> response) {
            ApiResponse<DeskIndex> body = response.body();
            if (body.getCode() == 1) {
                if (body.getData() != null && body.getData().getGoods_classes() != null && body.getData().getGoods_classes().size() > 0) {
                    dishMenuList = new ArrayList<>();
                    hungNum = body.getData().getHang_num();
                    updataHungNumInfo();
                    for (int i = 0; i < body.getData().getGoods_classes().size(); i++) {
                        ArrayList<Dish> dishs = new ArrayList<>();
                        if (body.getData().getGoods_classes() != null && body.getData().getGoods_classes().size() > 0) {
                            if (body.getData().getGoods_classes().get(i).getList() != null && body.getData().getGoods_classes().get(i).getList().size() > 0) {
                                for (int j = 0; j < body.getData().getGoods_classes().get(i).getList().size(); j++) {
                                    DeskIndex.GoodsClassesBean.ListBean bean = body.getData().getGoods_classes().get(i).getList().get(j);
                                    Dish dish = new Dish();
                                    dish.setDishName(bean.getTitle());
                                    dish.setDishPrice(Double.valueOf(bean.getPrice()));
                                    dish.setDishAmount(bean.getLast_amount());
                                    dish.setDishPic(bean.getImage());
                                    dish.setGoodId(bean.getGoods_id());

                                    if (bean.getSpec() != null && bean.getSpec().size() > 0) {
                                        List<Dish.SpecValuesBean> specValuesBeans = new ArrayList<>();
                                        for (int k = 0; k < bean.getSpec().size(); k++) {
                                            Dish.SpecValuesBean specValuesBean = new Dish.SpecValuesBean();
                                            specValuesBean.setValueName(bean.getSpec().get(k).getName());
                                            Dish.SpecValuesBean.ValueBean valueBean = new Dish.SpecValuesBean.ValueBean();
                                            String[] arr = bean.getSpec().get(k).getValue().split(",");
                                            List<Dish.SpecValuesBean.ValueBean.Tag> tags = new ArrayList<>();
                                            for (int l = 0; l < arr.length; l++) {
                                                Dish.SpecValuesBean.ValueBean.Tag tag = new Dish.SpecValuesBean.ValueBean.Tag();
                                                tag.setVa(arr[l]);
                                                tags.add(tag);
                                            }
                                            valueBean.setTags(tags);
                                            specValuesBean.setValueBean(valueBean);
                                            specValuesBeans.add(specValuesBean);
                                        }
                                        dish.setValuesBeans(specValuesBeans);
                                    }
                                    dishs.add(dish);
                                }

                            }

                        }

                        DishMenu menus = new DishMenu(body.getData().getGoods_classes().get(i).getClass_name(), dishs);
                        dishMenuList.add(menus);
                    }
                    shopCart = new ShopCart();
                    initAdapter();
                } else {
                    ToastUtil.showToast("收银台暂无数据");
                }
            } else if (response.body().getCode() == 888) {//登录信息过期
                ToastUtil.showToast(response.body().getMsg());
                PreferenceUtils.getInstance().loginOut();
                AppManager.getAppManager().finishAllActivity();
                LoginActivity.actionStart(getActivity());
            }  else {
                ToastUtil.showToast(body.getMsg());
            }
        }

        @Override
        public void onFailure(Call<ApiResponse<DeskIndex>> call, Throwable t) {
            ToastUtil.showToast("网络异常:获取收银台数据失败");
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
            ShopCartDialog dialog = new ShopCartDialog(getActivity(), shopCart, R.style.cartdialog, ShopCartDialog.BOTTOM_VISIBLE);
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

    private int hungNum;

    private void updataHungNumInfo() {
        if (hungNum > 0) {
            tvOrderNum.setVisibility(View.VISIBLE);
            tvOrderNum.setText(hungNum + "");
        } else {
            tvOrderNum.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void initDatas() {


    }

    private void initAdapter() {
        leftAdapter = new LeftMenuAdapter(getActivity(), dishMenuList);
        rightAdapter = new DishCashierAdapter(getActivity(), dishMenuList, shopCart);
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


    @Override
    public void remove(View view, int position) {
        showTotalPrice();
    }


    private void showTotalPrice() {
        if (shopCart != null && shopCart.getShoppingTotalPrice() > 0) {
            shoppingCartTotalTv.setVisibility(View.VISIBLE);
            tvSettlement.setVisibility(View.VISIBLE);
            shoppingCartTotalTv.setText("￥ " + shopCart.getShoppingTotalPrice());
            shoppingCartTotalNum.setVisibility(View.VISIBLE);
            shoppingCartTotalNum.setText("" + shopCart.getShoppingAccount());
        } else {
            tvSettlement.setVisibility(View.GONE);
            shoppingCartTotalTv.setVisibility(View.GONE);
            shoppingCartTotalNum.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.tv_coll_order, R.id.tv_hang_up, R.id.tv_settlement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_coll_order:
                OrderCollectionActivity.actionStart(getActivity());
                break;
            case R.id.tv_hang_up:
                ConfirmOrderData confirmOrderData = new ConfirmOrderData();
                confirmOrderData.setBeizhu("");
                confirmOrderData.setHang(1 + "");
                confirmOrderData.setToken(userToken);
                List<ConfirmOrderData.OrdersBean> ordersBeans = new ArrayList<>();
                for (int i = 0; i < shopCart.getAllDishs().size(); i++) {
                    ConfirmOrderData.OrdersBean ordersBean = new ConfirmOrderData.OrdersBean();
                    ordersBean.setAmount(shopCart.getAllDishs().get(i).getNum() + "");
                    ordersBean.setGoods_id(shopCart.getAllDishs().get(i).getGoodId() + "");
                    if (shopCart.getAllDishs().get(i).getSelectSpec().length() > 0) {
                        ordersBean.setSpec(shopCart.getAllDishs().get(i).getSelectSpec().substring(1, shopCart.getAllDishs().get(i).getSelectSpec().length() - 1));
                    } else {
                        ordersBean.setSpec(shopCart.getAllDishs().get(i).getSelectSpec());
                    }
                    ordersBeans.add(ordersBean);
                }
                confirmOrderData.setOrders(ordersBeans);
                if (confirmOrderData != null && confirmOrderData.getOrders().size() > 0) {
                    HttpUtil.createRequest(BaseUrl.getInstence().getDeskConfirm()).confirmHang(confirmOrderData).enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                            if (response.body().getCode() == 1) {
                                ToastUtil.showToast("挂单成功");
                                hungNum = hungNum + 1;
                                updataHungNumInfo();
                                shopCart.clear();
                                showTotalPrice();
                                rightAdapter.notifyDataSetChanged();
                            } else {
                                ToastUtil.showToast(response.body().getMsg());
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            ToastUtil.showToast("网络异常:提交失败");
                        }
                    });
                } else {
                    ToastUtil.showToast("暂无待挂单数据");
                }
                break;
            case R.id.tv_settlement:
                OrderCommitActivity.actionStart(getActivity(), new Gson().toJson(shopCart));
                break;
        }
    }

    private int shoppingAccount;//商品总数
    private double shoppingTotalPrice;//商品总价钱

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(MessageWrap message) {
        if (message.message.equals(DELETE)) {
            hungNum = hungNum - 1;
            updataHungNumInfo();
        }else if (message.message.equals(PAY_SUCCESS)){
            shopCart.clear();
            rightAdapter.notifyDataSetChanged();
            showTotalPrice();
        }
        else if (message.message.equals(TYPE_CHANGE)){
           getCashierData();
        }
        else if (message.message.substring(0,7).equals("GETHANG")) {//避免因修改外卖和营业状态 导致执行这里crash

            HttpUtil.createRequest(TAG, BaseUrl.getInstence().getHang_get()).hangGet(userToken, message.message.substring(7,message.message.length())).enqueue(new Callback<ApiResponse<GetHang>>() {
                @Override
                public void onResponse(Call<ApiResponse<GetHang>> call, Response<ApiResponse<GetHang>> response) {

                    if (response.body() != null) {
                        if (response.body().getCode() == 1) {
                            ApiResponse<GetHang> body = response.body();
                            List<Dish> dishes = new ArrayList<>();
                            hungNum = hungNum - 1;
                            updataHungNumInfo();
                            shoppingAccount = 0;
                            shoppingTotalPrice = 0;
                            shopCart.clear();
                            for (int i = 0; i < body.getData().getOrder().getList().size(); i++) {
                                Dish dish = new Dish();
                                dish.setGoodId(body.getData().getOrder().getList().get(i).getGoods_id());
                                dish.setSelectSpec(body.getData().getOrder().getList().get(i).getSpec());
                                dish.setNum(body.getData().getOrder().getList().get(i).getAmount());
                                dish.setDishName(body.getData().getOrder().getList().get(i).getTitle());
                                dish.setDishPic(body.getData().getOrder().getList().get(i).getImage());
                                dish.setDishPrice(Double.valueOf(body.getData().getOrder().getList().get(i).getPrice()));
                                dishes.add(dish);
                                shoppingAccount = shoppingAccount + body.getData().getOrder().getList().get(i).getAmount();
                                shoppingTotalPrice = ArithUtil.add(shoppingTotalPrice, Double.valueOf(body.getData().getOrder().getList().get(i).getPrice()));
                            }
                            shopCart.setShoppingAccount(shoppingAccount);
                            shopCart.setShoppingTotalPrice(shoppingTotalPrice);
                            shopCart.setAllDishs(dishes);
                            showTotalPrice();
                        } else {
                            ToastUtil.showToast(response.body().getMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<GetHang>> call, Throwable t) {
                    ToastUtil.showToast("网络异常:取单失败");
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
