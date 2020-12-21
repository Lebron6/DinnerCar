package com.resttcar.dh.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.resttcar.dh.R;
import com.resttcar.dh.entity.MaterialOrders;
import com.resttcar.dh.entity.OrderList;
import com.resttcar.dh.tools.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListAdapter extends RecyclerView.Adapter {



    private Context context;
    List<OrderList.ListBean> datas;

    public OrderListAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<OrderList.ListBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_list, null);
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
        viewHolder.tvOrderNumber.setText(datas.get(position).getOrder_no());
        viewHolder.tvOrderPrice.setText(datas.get(position).getPrice() + "元");
        switch (datas.get(position).getOrder_status()) {
            case 1:
                viewHolder.tvOrderStatus.setText("待接单");
                break;
            case 2:
                viewHolder.tvOrderStatus.setText("已接单");
                break;
            case 3:
                viewHolder.tvOrderStatus.setText("挂单中");
                break;
            case 4:
                viewHolder.tvOrderStatus.setText("已完成");
                break;
            case 5:
                viewHolder.tvOrderStatus.setText("退款中");
                break;
            case 6:
                viewHolder.tvOrderStatus.setText("已退款");
                break;
            case -1:
                viewHolder.tvOrderStatus.setText("已取消");
                break;
        }

        viewHolder.tvOrderTime.setText(Utils.getDataYMD(datas.get(position).getCreatetime() + ""));
        viewHolder.tvOrderType.setText(datas.get(position).getOrder_type()==1?"外卖订单":"现场订单");
        viewHolder.tvNickName.setText(datas.get(position).getNickname());
    }

    @Override
    public int getItemCount() {
        if (datas == null || datas.size() == 0) {
            return 0;
        } else {
            return datas.size();
        }

    }

    private OnItemClickLitener mOnItemClickLitener;


    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_order_number)
        TextView tvOrderNumber;
        @BindView(R.id.tv_order_type)
        TextView tvOrderType;
        @BindView(R.id.tv_order_time)
        TextView tvOrderTime;
        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;
        @BindView(R.id.tv_nick_name)
        TextView tvNickName;
        @BindView(R.id.tv_order_price)
        TextView tvOrderPrice;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}