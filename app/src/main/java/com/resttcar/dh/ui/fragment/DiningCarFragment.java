package com.resttcar.dh.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.PointF;
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
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.callback.GoodsDeleteImpl;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.DeskIndex;
import com.resttcar.dh.entity.Dish;
import com.resttcar.dh.entity.DishMenu;
import com.resttcar.dh.entity.GoodIndex;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.ui.activity.CreatGoodsActivity;
import com.resttcar.dh.ui.activity.ManageTypeActivity;
import com.resttcar.dh.ui.adapter.LeftMenuAdapter;
import com.resttcar.dh.ui.adapter.RightGoodsAdapter;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 2020/4/8.
 * 餐车菜单
 */
public class DiningCarFragment extends BaseFragment implements LeftMenuAdapter.onItemSelectedListener, GoodsDeleteImpl {

    @BindView(R.id.tv_coll_order)
    TextView tvCollOrder;
    @BindView(R.id.tv_hang_up)
    TextView tvHangUp;
    @BindView(R.id.layout_top)
    RelativeLayout layoutTop;
    @BindView(R.id.left_menu)
    RecyclerView leftMenu;
    @BindView(R.id.right_menu)
    RecyclerView rightMenu;
    @BindView(R.id.right_menu_tv)
    TextView headerView;
    @BindView(R.id.right_menu_item)
    LinearLayout headerLayout;
    @BindView(R.id.main_layout)
    LinearLayout mainLayout;
    @BindView(R.id.tv_new_type)
    TextView tvNewType;
    @BindView(R.id.tv_new_goods)
    TextView tvNewGoods;
    @BindView(R.id.layout_bottom)
    RelativeLayout layoutBottom;
    Unbinder unbinder;

    private DishMenu headMenu;
    private LeftMenuAdapter leftAdapter;
    private RightGoodsAdapter rightAdapter;
    private ArrayList<DishMenu> dishMenuList;//数据源
    private boolean leftClickType = false;//左侧菜单点击引发的右侧联动

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_diningcar;
    }


    private void initAdapter() {
        leftAdapter = new LeftMenuAdapter(getActivity(), dishMenuList);
        rightAdapter = new RightGoodsAdapter(getActivity(), dishMenuList, this);
        rightMenu.setAdapter(rightAdapter);
        leftMenu.setAdapter(leftAdapter);
        leftAdapter.addItemSelectedListener(this);

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
//                            Log.e("onScrolled: ", menu.getMenuName());
                        }
                    }
                }
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


    @Override
    protected void initDatas() {
        getData();
    }

    private void getData() {
        HttpUtil.createRequest(TAG, BaseUrl.getInstence().goodsIndex()).goodIndex(userToken).enqueue(new Callback<ApiResponse<GoodIndex>>() {
            @Override
            public void onResponse(Call<ApiResponse<GoodIndex>> call, Response<ApiResponse<GoodIndex>> response) {
                if (response.body() != null) {
                    if (response.body().getCode() == 1) {
                        dishMenuList = new ArrayList<>();
                        GoodIndex index = response.body().getData();
                        if (index.getGoods_classes() != null && index.getGoods_classes().size() > 0) {
                            for (int i = 0; i < index.getGoods_classes().size(); i++) {
                                if (index.getGoods_classes().get(i).getList() != null && index.getGoods_classes().get(i).getList().size() > 0) {
                                    ArrayList<Dish> dishs = new ArrayList<>();

                                    for (int j = 0; j < index.getGoods_classes().get(i).getList().size(); j++) {

                                        GoodIndex.GoodsClassesBean.ListBean bean = index.getGoods_classes().get(i).getList().get(j);
                                        Dish dish = new Dish();
                                        dish.setDishName(bean.getTitle());
                                        dish.setDishPrice(Double.valueOf(bean.getPrice()));
                                        dish.setDishAmount(bean.getLast_amount());
                                        dish.setDishPic(bean.getImage());
                                        dish.setGoodId(bean.getGoods_id());
                                        dish.setClassId(bean.getClass_id());
                                        dish.setClassType(bean.getClass_name());
                                        dish.setSell_Amount(bean.getSell_amount());
                                        dish.setGoodsContent(bean.getContent());
                                        dish.setStatus(bean.getStatus());
                                        dish.setSelectSpec(bean.getSpec());
                                        dishs.add(dish);
                                    }
                                    DishMenu menus = new DishMenu(index.getGoods_classes().get(i).getClass_name(), dishs);
                                    dishMenuList.add(menus);
                                    initAdapter();
                                }


                            }
                        }

                    } else {
                        ToastUtil.showToast(response.body().getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<GoodIndex>> call, Throwable t) {
                ToastUtil.showToast("网络异常:菜单获取失败");
            }
        });
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


    @OnClick({R.id.tv_new_type, R.id.tv_new_goods})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_new_type:
                ManageTypeActivity.actionStart(getActivity());
                break;
            case R.id.tv_new_goods:
                CreatGoodsActivity.actionStart(getActivity());
                break;
        }
    }

    @Override
    public void change(int postion) {
        getData();
    }
}
