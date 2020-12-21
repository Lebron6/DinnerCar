package com.resttcar.dh.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.HangData;
import com.resttcar.dh.entity.MessageWrap;
import com.resttcar.dh.tools.PreferenceUtils;
import com.resttcar.dh.tools.RecyclerViewHelper;
import com.resttcar.dh.tools.ToastUtil;


import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.resttcar.dh.api.Constant.DELETE;
import static com.resttcar.dh.api.Constant.GETHANG;

public class OrderCollectionAdapter extends RecyclerView.Adapter {

    CollectionGoodsAdapter adapter;
    private Activity context;
    private HangData datas ;

    public OrderCollectionAdapter(Activity context) {
        this.context = context;
    }

    public void setDatas(HangData datas) {
        this.datas=datas;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_collection, null);
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
        adapter = new CollectionGoodsAdapter(context);
        adapter.setOnItemClickLitener(new CollectionGoodsAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
        viewHolder.btnCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageWrap msg = MessageWrap.getInstance(GETHANG+datas.orders.get(position).getOrder_id());//外卖状态改变通知个人中心
                EventBus.getDefault().post(msg);
                context.finish();
            }
        });
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil.createRequest(getClass().getSimpleName(), BaseUrl.getInstence().getDeleteHang()).deleteHang(PreferenceUtils.getInstance().getUserToken(),datas.orders.get(position).getOrder_id()).enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.body().getCode()==1){
                            ToastUtil.showToast("取消挂单数据成功");
                            datas.orders.remove(position);
                            notifyDataSetChanged();
                            MessageWrap msg = MessageWrap.getInstance(DELETE);//外卖状态改变通知个人中心
                            EventBus.getDefault().post(msg);
                        }else{
                            ToastUtil.showToast(response.body().getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        ToastUtil.showToast("网络异常:删除挂单失败");
                    }
                });
            }
        });
        viewHolder.tvPrice.setText("订单价格:¥ "+datas.getOrders().get(position).getPay_price());
        adapter.setDatas(datas.orders.get(position));
        RecyclerViewHelper.initRecyclerViewV(context, viewHolder.rvCollList, false, adapter);
    }

    @Override
    public int getItemCount() {
        return datas.getOrders().size();
    }

    private OnItemClickLitener mOnItemClickLitener;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.btn_delete)
        Button btnDelete;
        @BindView(R.id.btn_collection)
        Button btnCollection;
        @BindView(R.id.rv_coll_list)
        RecyclerView rvCollList;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}