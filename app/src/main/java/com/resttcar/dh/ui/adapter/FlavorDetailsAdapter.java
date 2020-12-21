package com.resttcar.dh.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.resttcar.dh.R;
import com.resttcar.dh.entity.GoodSpec;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlavorDetailsAdapter extends RecyclerView.Adapter {



    private Context context;
    private List<String> values;
    private GoodSpec spec;

    public FlavorDetailsAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<String> values, GoodSpec spec) {
        this.values=values;
        this.spec=spec;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_flavor_details, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
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
        viewHolder.tvFlavorName.setText(values.get(position));
        viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               values.remove(position);
                spec.setValue("");
                for (int i = 0; i <values.size() ; i++) {
                    spec.setValue(spec.getValue()+","+values.get(i));
                }
                notifyDataSetChanged();
            }
        });
    }
    @Override
    public int getItemCount() {
        if (values!=null&&values.size()>0){
            return values.size();
        }else{
            return 0;
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
        @BindView(R.id.tv_flavor_name)
        TextView tvFlavorName;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}