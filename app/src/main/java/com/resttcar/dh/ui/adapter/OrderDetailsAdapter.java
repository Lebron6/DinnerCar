package com.resttcar.dh.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.entity.OrderDetails;
import com.resttcar.dh.entity.PurDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailsAdapter extends RecyclerView.Adapter {



    private Context context;
    List<OrderDetails.GoodsBean> matrials;
    public OrderDetailsAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<OrderDetails.GoodsBean> matrials) {
        this.matrials=matrials;
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

        Glide.with(context).load(BaseUrl.getInstence().ipAddress + matrials.get(position).getGoods_image()).into(viewHolder.ivPic);
        viewHolder.tvGoodsName.setText(matrials.get(position).getGoods_title());
        viewHolder.tvNum.setText("×" + matrials.get(position).getAmount());
        viewHolder.tvPrice.setText("¥ " + matrials.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return matrials.size();
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