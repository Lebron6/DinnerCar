package com.resttcar.dh.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.resttcar.dh.R;
import com.resttcar.dh.entity.OrderInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderGoodsAdapter extends RecyclerView.Adapter {


    private Context context;
    List<OrderInfo.OrdersBean.ListBean> list;

    public OrderGoodsAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<OrderInfo.OrdersBean.ListBean> list) {
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_goods_info, null);
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
        OrderInfo.OrdersBean.ListBean bean = list.get(position);
        viewHolder.tvGoodsName.setText(bean.getTitle());
        viewHolder.tvGoodsNum.setText("×"+bean.getAmount());
        viewHolder.tvGoodsPrice.setText("¥"+bean.getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private OnItemClickLitener mOnItemClickLitener;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_num)
        TextView tvGoodsNum;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}