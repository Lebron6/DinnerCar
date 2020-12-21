package com.resttcar.dh.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.resttcar.dh.R;
import com.resttcar.dh.entity.CWIndex;
import com.resttcar.dh.tools.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FinaStatAdapter extends RecyclerView.Adapter {


    private Context context;
    OrderGoodsAdapter adapter;
    List<CWIndex.ListBean> listBeans;
    public FinaStatAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<CWIndex.ListBean> listBeans) {
        this.listBeans=listBeans;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fina_stat, null);
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
        viewHolder.tvMoney.setText(listBeans.get(position).getMoney()+"元");
        viewHolder.tvMoneyType.setText(listBeans.get(position).getTitle());
        viewHolder.tvTime.setText(Utils.getHMS(listBeans.get(position).getCreatetime()+""));
        viewHolder.tv_data.setText(Utils.getDataYMD(listBeans.get(position).getCreatetime()+""));
    }

    @Override
    public int getItemCount() {
        return listBeans.size()>0?listBeans.size():0;
    }

    private OnItemClickLitener mOnItemClickLitener;


    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_data)
        TextView tv_data;
        @BindView(R.id.tv_money_type)
        TextView tvMoneyType;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}