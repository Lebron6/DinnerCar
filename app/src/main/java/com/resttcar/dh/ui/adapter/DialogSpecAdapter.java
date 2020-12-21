package com.resttcar.dh.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.resttcar.dh.R;
import com.resttcar.dh.callback.SpecSelectImpl;
import com.resttcar.dh.entity.Dish;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogSpecAdapter extends RecyclerView.Adapter {


    private Context context;
    List<Dish.SpecValuesBean> valuesBeans;
    OrderGoodsAdapter adapter;
    SpecSelectImpl specSelect;

    public DialogSpecAdapter(Context context, SpecSelectImpl specSelect) {
        this.context = context;
        this.specSelect = specSelect;
    }

    public void setDatas(List<Dish.SpecValuesBean> valuesBeans) {
        this.valuesBeans = valuesBeans;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_spec, null);
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
        viewHolder.tvSpecType.setText(valuesBeans.get(position).getValueName());
       final List<String> list=new ArrayList<>();
        for (int i = 0; i <valuesBeans.get(position).getValueBean().getTags().size() ; i++) {
            list.add(valuesBeans.get(position).getValueBean().getTags().get(i).getVa());
        }
        viewHolder.tagSpec.setAdapter(new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(context).inflate(R.layout.tv_label,
                        viewHolder.tagSpec, false);
                tv.setText(s);
                return tv;
            }
        });

        viewHolder.tagSpec.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                if(selectPosSet.toString().length()==3){//取消选中的长度为[],2个长度位
                    String s = selectPosSet.toString().substring(1, 2);
//                    for (int i = 0; i < valuesBeans.get(position).getValueBean().getTags().size(); i++) {
//                        if (Integer.valueOf(s)==i){
//                            valuesBeans.get(position).getValueBean().getTags().get(Integer.valueOf(s)).setTAG(1);
//                        }else{
//                            valuesBeans.get(position).getValueBean().getTags().get(Integer.valueOf(s)).setTAG(0);
//                        }
//                    }
                    valuesBeans.get(position).setSelectValue(valuesBeans.get(position).getValueBean().getTags().get(Integer.valueOf(s)).getVa());
//                    Log.e("选择")
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return valuesBeans.size();
    }

    private OnItemClickLitener mOnItemClickLitener;


    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_spec_type)
        TextView tvSpecType;
        @BindView(R.id.tag_spec)
        TagFlowLayout tagSpec;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}