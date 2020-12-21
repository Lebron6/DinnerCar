package com.resttcar.dh.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.callback.BrandAddImpl;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.BrandData;
import com.resttcar.dh.entity.UpdataBrand;
import com.resttcar.dh.tools.PreferenceUtils;
import com.resttcar.dh.tools.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandAdapter extends RecyclerView.Adapter {


    private Context context;
    private BrandData data;

    public BrandAdapter(Context context,BrandAddImpl brandImpl) {
        this.context = context;
        this.brandAdd = brandImpl;
    }

    public void setDatas(BrandData data) {
        this
                .data = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_brand, null);
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

        final BrandData.GoodsBean bean = data.getGoods().get(position);
        Glide.with(context).load(BaseUrl.getInstence().ipAddress + bean.getImage()).into(viewHolder.ivGoodsPic);
        viewHolder.tvGoodsName.setText(bean.getTitle());
        viewHolder.tvPrice.setText("推荐价格：" + bean.getPrice()+"元");
        viewHolder.tvGoodsType.setText("所属分类：" + bean.getClass_name());
        if (bean.getSub_id() == 0) {//未添加
            viewHolder.tvAdd.setVisibility(View.VISIBLE);
            viewHolder.tvAlreadyAdd.setVisibility(View.GONE);
        } else {
            viewHolder.tvAdd.setVisibility(View.GONE);
            viewHolder.tvAlreadyAdd.setVisibility(View.VISIBLE);
        }
        viewHolder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdataBrand brand=new UpdataBrand();
                brand.setToken(PreferenceUtils.getInstance().getUserToken());
                ArrayList<Integer> integers = new ArrayList<>();
                integers.add(bean.getGoods_id());
                brand.setGoods_ids(integers);
                HttpUtil.createRequest(BaseUrl.getInstence().goodBrand()).brand(brand).enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.body()!=null){
                            if (response.body().getCode()==0){
                                ToastUtil.showToast("添加成功");
                                brandAdd.add(position);
                            }else{
                                ToastUtil.showToast(response.body().getMsg());
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        ToastUtil.showToast("网络异常:添加失败");
                    }
                });
            }
        });
    }
    BrandAddImpl brandAdd;
    @Override
    public int getItemCount() {
        return data.getGoods().size();
    }

    private OnItemClickLitener mOnItemClickLitener;


    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_goods_pic)
        ImageView ivGoodsPic;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_goods_type)
        TextView tvGoodsType;
        @BindView(R.id.tv_add)
        TextView tvAdd;
        @BindView(R.id.tv_already_Add)
        TextView tvAlreadyAdd;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}