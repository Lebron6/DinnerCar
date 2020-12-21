package com.resttcar.dh.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.callback.GoodsDeleteImpl;
import com.resttcar.dh.callback.ShopCartImp;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.Dish;
import com.resttcar.dh.entity.DishMenu;
import com.resttcar.dh.tools.PreferenceUtils;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.ui.activity.CreatGoodsActivity;
import com.resttcar.dh.widget.SpecDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cheng on 16-11-10.
 */
public class RightGoodsAdapter extends RecyclerView.Adapter {
    private final int MENU_TYPE = 0;
    private final int DISH_TYPE = 1;
    private final int HEAD_TYPE = 2;


    private Context mContext;
    private ArrayList<DishMenu> mMenuList;
    private int mItemCount;
    private ShopCartImp shopCartImp;

    public RightGoodsAdapter(Context mContext, ArrayList<DishMenu> mMenuList,GoodsDeleteImpl goodsDelete) {
        this.mContext = mContext;
        this.mMenuList = mMenuList;
        this.goodsDelete = goodsDelete;
        this.mItemCount = mMenuList.size();
        for (DishMenu menu : mMenuList) {
            mItemCount += menu.getDishList().size();
        }

    }

    @Override
    public int getItemViewType(int position) {
        int sum = 0;
        for (DishMenu menu : mMenuList) {
            if (position == sum) {
                return MENU_TYPE;
            }
            sum += menu.getDishList().size() + 1;
        }
        return DISH_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MENU_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_menu_item, parent, false);
            MenuViewHolder viewHolder = new MenuViewHolder(view);
            return viewHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_goods_item, parent, false);
            DishViewHolder viewHolder = new DishViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == MENU_TYPE) {
            MenuViewHolder menuholder = (MenuViewHolder) holder;
            if (menuholder != null) {
                menuholder.right_menu_title.setText(getMenuByPosition(position).getMenuName());
                menuholder.right_menu_layout.setContentDescription(position + "");
            }
        } else {
            final DishViewHolder dishholder = (DishViewHolder) holder;
            if (dishholder != null) {
                final Dish dish = getDishByPosition(position);
                Glide.with(mContext).load(BaseUrl.getInstence().ipAddress+dish.getDishPic()).into(dishholder.ivGoodsPic);
                dishholder.tvGoodsName.setText(dish.getDishName());
                dishholder.tvGoodsKc.setText("库存："+dish.getDishAmount());
                dishholder.tvGoodsYs.setText("月售："+dish.getSell_Amount());
                dishholder.tvGoodsPrice.setText("¥"+dish.getDishPrice()+"");
                dishholder.rightDishItem.setContentDescription(position + "");
               dishholder.tvGoodsDown.setText(dish.getStatus()==1?"下架":"上架");//1 上架中  -1已下架


            dishholder.tvGoodsDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HttpUtil.createRequest(getClass().getSimpleName(), BaseUrl.getInstence().goodsOnOff()).goodsOnOff(PreferenceUtils.getInstance().getUserToken(),dish.getGoodId(),dish.getStatus()==1?-1:1).enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                            if (response.body()!=null){
                                if (response.body().getCode()==1){
                                    ToastUtil.showToast("操作成功");//执行出单的adapter操作
                                    dish.setStatus(dish.getStatus()==1?-1:1);//改变状态 更新adapter
                                    notifyDataSetChanged();
                                }else{
                                    ToastUtil.showToast(response.body().getMsg());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            ToastUtil.showToast("网络异常:操作失败");
                        }
                    });
                }
            });
            dishholder.tvGoodsEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreatGoodsActivity.actionStart(mContext,new Gson().toJson(dish));
                }
            });
            dishholder.tvGoodsDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HttpUtil.createRequest(getClass().getSimpleName(), BaseUrl.getInstence().goodsDelete()).goodsDelete(PreferenceUtils.getInstance().getUserToken(),dish.getGoodId()).enqueue(new Callback<ApiResponse>() {
                        @Override
                        public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                            if (response.body()!=null){
                                if (response.body().getCode()==1){
                                    ToastUtil.showToast("已删除");//执行出单的adapter操作
                                  goodsDelete.change(position);
                                }else{
                                    ToastUtil.showToast(response.body().getMsg());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponse> call, Throwable t) {
                            ToastUtil.showToast("网络异常:删除失败");
                        }
                    });
                }
            });
            }
        }

    }
    GoodsDeleteImpl goodsDelete;
    public DishMenu getMenuByPosition(int position) {
        int sum = 0;
        for (DishMenu menu : mMenuList) {
            if (position == sum) {
                return menu;
            }
            sum += menu.getDishList().size() + 1;
        }
        return null;
    }

    public Dish getDishByPosition(int position) {
        for (DishMenu menu : mMenuList) {
            if (position > 0 && position <= menu.getDishList().size()) {
                return menu.getDishList().get(position - 1);
            } else {
                position -= menu.getDishList().size() + 1;
            }
        }
        return null;
    }

    public DishMenu getMenuOfMenuByPosition(int position) {
        for (DishMenu menu : mMenuList) {
            if (position == 0) return menu;
            if (position > 0 && position <= menu.getDishList().size()) {
                return menu;
            } else {
                position -= menu.getDishList().size() + 1;
            }
        }
        return null;
    }

    private void showSpecView(int position) {
//        SpecDialog dialog = new SpecDialog(mContext, R.style.cartdialog, position, dish);
//        dialog.setShopCartImp(shopCartImp);
//        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                notifyDataSetChanged();
//            }
//        });
//        dialog.show();
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    public ShopCartImp getShopCartImp() {
        return shopCartImp;
    }

    public void setShopCartImp(ShopCartImp shopCartImp) {
        this.shopCartImp = shopCartImp;
    }

    private class MenuViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout right_menu_layout;
        private TextView right_menu_title;

        public MenuViewHolder(View itemView) {
            super(itemView);
            right_menu_layout = (LinearLayout) itemView.findViewById(R.id.right_menu_item);
            right_menu_title = (TextView) itemView.findViewById(R.id.right_menu_tv);
        }
    }

    public class DishViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_goods_pic)
        ImageView ivGoodsPic;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_kc)
        TextView tvGoodsKc;
        @BindView(R.id.tv_goods_ys)
        TextView tvGoodsYs;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.layout_top)
        LinearLayout layoutTop;
        @BindView(R.id.tv_goods_down)
        TextView tvGoodsDown;
        @BindView(R.id.tv_goods_edit)
        TextView tvGoodsEdit;
        @BindView(R.id.tv_goods_delete)
        TextView tvGoodsDelete;
        @BindView(R.id.right_dish_item)
        LinearLayout rightDishItem;


        public DishViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

    }
}
