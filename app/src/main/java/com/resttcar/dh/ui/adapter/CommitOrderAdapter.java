package com.resttcar.dh.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.entity.ShopCart;
import com.resttcar.dh.tools.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommitOrderAdapter extends RecyclerView.Adapter {



    private Context context;
    private ShopCart shopCart;

    public CommitOrderAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(ShopCart shopCart) {
        this.shopCart=shopCart;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_commit_order, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
        }

        Glide.with(context).load(BaseUrl.getInstence().ipAddress + shopCart.getAllDishs().get(position).getDishPic()).into(viewHolder.ivPic);
        viewHolder.tvGoodsName.setText(shopCart.getAllDishs().get(position).getDishName());
        viewHolder.tvNum.setText("×" + shopCart.getAllDishs().get(position).getNum());
        viewHolder.tvPrice.setText("¥ " + shopCart.getAllDishs().get(position).getDishPrice());
        if (shopCart.getAllDishs().get(position).getSelectSpec().length()>0){
            String[] arr = shopCart.getAllDishs().get(position).getSelectSpec().substring(1, shopCart.getAllDishs().get(position).getSelectSpec().length() - 1).split(",");
            viewHolder.layoutLable.setVisibility(View.VISIBLE);
            if (arr.length>0){
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        Utils.dip2px(context,15));
                for (int i = 0; i <arr.length; i++) {//String数组，不过arr[0]为空
                    if (!TextUtils.isEmpty(arr[i])){
                        TextView textView = new TextView(context);
                        layoutParams.setMargins(10, 0, 0, 0);
                        textView.setTextSize(10);
                        textView.setPadding(10, 0, 10, 0);
                        textView.setTextColor(context.getResources().getColor(R.color.colorTextGray));
                        textView.setBackgroundResource(R.drawable.bg_tv_gray); //设置背景
                        textView.setText(arr[i]);
                        textView.setGravity(Gravity.CENTER);
                        textView.setLayoutParams(layoutParams);
                        viewHolder.layoutLable.addView(textView);
                    }

                }
            }
        }else{
            viewHolder.layoutLable.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return shopCart.getAllDishs().size();
    }

    private OnItemClickLitener mOnItemClickLitener;


    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_pic)
        ImageView ivPic;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.layout_goods_attr)
        LinearLayout layoutGoodsAttr;
        @BindView(R.id.layout_lable)
        LinearLayout layoutLable;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}