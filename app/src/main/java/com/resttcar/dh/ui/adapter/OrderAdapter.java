package com.resttcar.dh.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.OrderConfirmData;
import com.resttcar.dh.entity.OrderInfo;
import com.resttcar.dh.tools.PreferenceUtils;
import com.resttcar.dh.tools.RecyclerViewHelper;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.tools.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends RecyclerView.Adapter {


    private Context context;
    OrderGoodsAdapter adapter;
    OrderInfo data;

    public OrderAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(OrderInfo data) {
        this.data=data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, null);
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
        final OrderInfo.OrdersBean ordersBean = data.getOrders().get(position);
        viewHolder.tvBuyerName.setText(ordersBean.getNickname());
        if (ordersBean.getGender()==0){
            viewHolder.tvBuyerSex.setText("(保密)");
        }else if(ordersBean.getGender()==1){
            viewHolder.tvBuyerSex.setText("(男士)");
        }else{
            viewHolder.tvBuyerSex.setText("(女士)");
        }
        viewHolder.tvBuyerPhone.setText(ordersBean.getMobile());
        viewHolder.tvCode.setText(ordersBean.getOrder_code());
        viewHolder.tvRemarks.setText(ordersBean.getBeizhu());
        viewHolder.tvAllPrice.setText("¥"+ordersBean.getPrice());
        viewHolder.tvSevicePrice.setText("¥"+ordersBean.getFee());
        viewHolder.tvIncome.setText("¥"+ordersBean.getIncome());
        viewHolder.tvOrderTime.setText(Utils.timeToTimeStamp(ordersBean.getCreatetime()+"")+" 下单");
        viewHolder.tvOrderNum.setText("订单编号："+ordersBean.getOrder_no());
        adapter = new OrderGoodsAdapter(context);
        adapter.setDatas(ordersBean.getList());
        RecyclerViewHelper.initRecyclerViewV(context, viewHolder.rvGoodsInfo, false, adapter);

        switch (ordersBean.getOrder_status()){
            case -1://已取消

                break;
            case 1://待结单
                viewHolder.layoutCode.setVisibility(View.GONE);
                viewHolder.btnOut.setVisibility(View.GONE);
                viewHolder.btnRefuse.setVisibility(View.VISIBLE);
                viewHolder.btnAccept.setVisibility(View.VISIBLE);
                break;
            case 2://已接单
                viewHolder.layoutCode.setVisibility(View.VISIBLE);
                viewHolder.btnOut.setVisibility(View.VISIBLE);
                viewHolder.btnRefuse.setVisibility(View.GONE);
                viewHolder.btnAccept.setVisibility(View.GONE);
                break;
            case 3://挂单中

                break;
            case 4://已完成

                break;
            case 5://退款中

                break;
            case 6://已退款

                break;

        }
        viewHolder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil.createRequest(getClass().getSimpleName(), BaseUrl.getInstence().orderConfirm()).orderConfirm(PreferenceUtils.getInstance().getUserToken(),ordersBean.getOrder_id()).enqueue(new Callback<ApiResponse<OrderConfirmData>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<OrderConfirmData>> call, Response<ApiResponse<OrderConfirmData>> response) {
                        if (response.body()!=null){
                            if (response.body().getCode()==1){
                                ordersBean.setOrder_status(2);
                                ordersBean.setOrder_code(response.body().getData().getOrder_code());
                                notifyItemChanged(position);
                            }else{
                                ToastUtil.showToast(response.body().getMsg());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<OrderConfirmData>> call, Throwable t) {
                        ToastUtil.showToast("网络异常:接单失败");
                    }
                });
            }
        });
        viewHolder.btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil.createRequest(getClass().getSimpleName(), BaseUrl.getInstence().orderConfirm2()).orderConfirm2(PreferenceUtils.getInstance().getUserToken(),ordersBean.getOrder_id()).enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.body()!=null){
                            if (response.body().getCode()==1){
                                ToastUtil.showToast("已出单");//执行出单的adapter操作
                                data.getOrders().remove(position);
                                notifyDataSetChanged();
                            }else{
                                ToastUtil.showToast(response.body().getMsg());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        ToastUtil.showToast("网络异常:出单失败");
                    }
                });
            }
        });
        viewHolder.btnRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil.createRequest(getClass().getSimpleName(), BaseUrl.getInstence().orderReject()).rejectOrder(PreferenceUtils.getInstance().getUserToken(),ordersBean.getOrder_id()).enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.body()!=null){
                            if (response.body().getCode()==1){
                                ToastUtil.showToast("已拒单");
                            data.getOrders().remove(position);
                            notifyDataSetChanged();
                            }else{
                                ToastUtil.showToast(response.body().getMsg());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        ToastUtil.showToast("网络异常:拒单失败");
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.getOrders().size();
    }

    private OnItemClickLitener mOnItemClickLitener;


    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_buyer_name)
        TextView tvBuyerName;
        @BindView(R.id.tv_buyer_sex)
        TextView tvBuyerSex;
        @BindView(R.id.tv_buyer_phone)
        TextView tvBuyerPhone;
        @BindView(R.id.layout_order_buyer_info)
        LinearLayout layoutOrderBuyerInfo;
        @BindView(R.id.btn_refuse)
        Button btnRefuse;
        @BindView(R.id.btn_accept)
        Button btnAccept;
        @BindView(R.id.btn_out)
        Button btnOut;
        @BindView(R.id.tv_code)
        TextView tvCode;
        @BindView(R.id.layout_code)
        LinearLayout layoutCode;
        @BindView(R.id.rv_goods_info)
        RecyclerView rvGoodsInfo;
        @BindView(R.id.tv_remarks)
        TextView tvRemarks;
        @BindView(R.id.tv_all_price)
        TextView tvAllPrice;
        @BindView(R.id.tv_sevice_price)
        TextView tvSevicePrice;
        @BindView(R.id.tv_income)
        TextView tvIncome;
        @BindView(R.id.tv_order_time)
        TextView tvOrderTime;
        @BindView(R.id.tv_order_num)
        TextView tvOrderNum;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}