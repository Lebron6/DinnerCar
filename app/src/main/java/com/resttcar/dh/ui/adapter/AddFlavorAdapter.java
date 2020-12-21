package com.resttcar.dh.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.resttcar.dh.R;
import com.resttcar.dh.entity.GoodSpec;
import com.resttcar.dh.tools.RecyclerViewHelper;
import com.resttcar.dh.tools.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddFlavorAdapter extends RecyclerView.Adapter {



    private Context context;
    FlavorDetailsAdapter adapter;List<GoodSpec> specs;
    public AddFlavorAdapter(Context context) {
        this.context = context;

    }

    public void setDatas(List<GoodSpec> specs) {
        this.specs = specs;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_add_flavor, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });
        }
        int num=position+1;
        final GoodSpec spec = specs.get(position);
        viewHolder.tvOv.setText("口味"+num);
        viewHolder.tvFlavorName.setText(spec.getName());
        adapter=new FlavorDetailsAdapter(context);
        if (!TextUtils.isEmpty(spec.getValue())){
            String[] values= spec.getValue().split(",");
            final List<String> strings=new ArrayList<>();
            for (int i = 0; i < values.length; i++) {
                strings.add(values[i]);
            }
            adapter.setDatas(strings,spec);
        }


        RecyclerViewHelper.initRecyclerViewH(context,viewHolder.rvFlavor,false,adapter);
        viewHolder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(viewHolder.etFlavorType.getText().toString())){
                    ToastUtil.showToast("请输入您要添加的口味");
                    return;
                }else{
                    spec.setName(viewHolder.tvFlavorName.getText().toString());
                    if (TextUtils.isEmpty(spec.getValue())){
                        spec.setValue(viewHolder.etFlavorType.getText().toString());
                    }else{
                        spec.setValue(spec.getValue()+","+viewHolder.etFlavorType.getText().toString());
                    }
                    viewHolder.etFlavorType.getText().clear();
                    notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return specs.size();
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
        EditText tvFlavorName;
        @BindView(R.id.tv_ov)
        TextView tvOv;
        @BindView(R.id.txt_flavor_type)
        TextView txtFlavorType;
        @BindView(R.id.et_flavor_type)
        EditText etFlavorType;
        @BindView(R.id.iv_add)
        ImageView ivAdd;
        @BindView(R.id.rv_flavor)
        RecyclerView rvFlavor;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}