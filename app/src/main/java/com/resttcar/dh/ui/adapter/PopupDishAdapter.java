package com.resttcar.dh.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.callback.ShopCartImp;
import com.resttcar.dh.entity.Dish;
import com.resttcar.dh.entity.ShopCart;

import java.util.ArrayList;

/**
 * Created by cheng on 16-12-23.
 */
public class PopupDishAdapter extends RecyclerView.Adapter{

    private static String TAG = "PopupDishAdapter1";
    private ShopCart shopCart;
    private Context context;
    private int itemCount;
    private ArrayList<Dish> dishList;
    private ShopCartImp shopCartImp;

    public PopupDishAdapter(Context context, ShopCart shopCart){
        this.shopCart = shopCart;
        this.context = context;
        this.itemCount = shopCart.getDishAccount();
        this.dishList = new ArrayList<>();
        dishList.addAll(shopCart.getAllDishs());
        Log.e(TAG, "PopupDishAdapter1: "+this.itemCount );
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cashier_pop, parent, false);
        DishViewHolder viewHolder = new DishViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        DishViewHolder dishholder = (DishViewHolder)holder;
        final Dish dish = getDishByPosition(position);
        if(dish!=null&&dish.getNum()>0) {
            dishholder.right_dish_name_tv.setText(dish.getDishName());
            dishholder.right_dish_price_tv.setText("¥"+dish.getDishPrice() );
            int num = dish.getNum();
            if (dish.getSelectSpec().length()>0){
                dishholder.tv_spec.setVisibility(View.VISIBLE);
                dishholder.tv_spec.setText(dish.getSelectSpec().substring(1,dish.getSelectSpec().length()-1));
            }else{
                dishholder.tv_spec.setVisibility(View.GONE);
            }
            dishholder.right_dish_account_tv.setText(num+"");

            Glide.with(context).load(BaseUrl.getInstence().ipAddress + dish.getDishPic()).into(dishholder.iv_pic);

            dishholder.right_dish_add_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        if(shopCart.addShoppingSingle(dish)) {
                            notifyItemChanged(position);
                            if(shopCartImp!=null)
                                shopCartImp.add(view,position);
                        }
                }
            });

            dishholder.right_dish_remove_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("减的条目",dish.toString());
                    if(shopCart.subShoppingSingle(dish)){
                        dishList.clear();
                        dishList.addAll(shopCart.getAllDishs());
                        itemCount = shopCart.getDishAccount();
                        notifyDataSetChanged();
                        if(shopCartImp!=null)
                            shopCartImp.remove(view,position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.itemCount;
    }

    public Dish getDishByPosition(int position){
       return dishList.get(position);
    }

    public ShopCartImp getShopCartImp() {
        return shopCartImp;
    }

    public void setShopCartImp(ShopCartImp shopCartImp) {
        this.shopCartImp = shopCartImp;
    }

    private class DishViewHolder extends RecyclerView.ViewHolder{
        private TextView right_dish_name_tv;
        private TextView right_dish_price_tv;
        private LinearLayout right_dish_layout;
        private ImageView right_dish_remove_iv;
        private ImageView right_dish_add_iv;
        private TextView right_dish_account_tv;
        private TextView tv_spec;

        private ImageView iv_pic;

        public DishViewHolder(View itemView) {
            super(itemView);
            right_dish_name_tv = (TextView)itemView.findViewById(R.id.right_dish_name);
            right_dish_price_tv = (TextView)itemView.findViewById(R.id.right_dish_price);
            right_dish_layout = (LinearLayout)itemView.findViewById(R.id.right_dish_item);
            right_dish_remove_iv = (ImageView)itemView.findViewById(R.id.right_dish_remove);
            right_dish_add_iv = (ImageView)itemView.findViewById(R.id.right_dish_add);
            right_dish_account_tv = (TextView) itemView.findViewById(R.id.right_dish_account);
            tv_spec = (TextView) itemView.findViewById(R.id.tv_spec);
            iv_pic = (ImageView) itemView.findViewById(R.id.iv_pic);
        }

    }
}
