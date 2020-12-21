package com.resttcar.dh.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.resttcar.dh.R;
import com.resttcar.dh.api.BaseUrl;
import com.resttcar.dh.api.HttpUtil;
import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.ConfirmClassData;
import com.resttcar.dh.entity.GoodsType;
import com.resttcar.dh.tools.PreferenceUtils;
import com.resttcar.dh.tools.ToastUtil;
import com.resttcar.dh.ui.activity.CashWithDrawalActivity;
import com.resttcar.dh.ui.activity.DrawCashCommitActivity;
import com.resttcar.dh.ui.activity.ManageTypeActivity;
import com.resttcar.dh.ui.activity.NewTypeActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerTypeAdapter extends RecyclerView.Adapter implements ItemTouchHelperAdapter {


    private Context context;
    GoodsType data;

    public ManagerTypeAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(GoodsType data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_manager_type, null);
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
        final GoodsType.ClassesBean bean = data.getClasses().get(position);
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtil.createRequest(getClass().getSimpleName(), BaseUrl.getInstence().deleteClass()).deleteClass(PreferenceUtils.getInstance().getUserToken(), bean.getClass_id()).enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.body().getCode() == 1) {
                            ToastUtil.showToast("删除成功");
                            data.getClasses().remove(position);
                            notifyDataSetChanged();
                        } else {
                            ToastUtil.showToast(response.body().getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        ToastUtil.showToast("网络异常:删除失败");
                    }
                });
            }
        });
        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewTypeActivity.actionStart(context, bean.getClass_id());
            }
        });
        viewHolder.tvGoodsNum.setText(bean.getNum() + "个商品");
        viewHolder.tvType.setText(bean.getClass_name());
    }

    @Override
    public int getItemCount() {
        return data.getClasses().size();
    }

    private OnItemClickLitener mOnItemClickLitener;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_goods_num)
        TextView tvGoodsNum;
        @BindView(R.id.btn_delete)
        Button btnDelete;
        @BindView(R.id.btn_edit)
        Button btnEdit;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onItemMove(RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        int fromPosition = source.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        if (fromPosition < data.getClasses().size() && toPosition < data.getClasses().size()) {
            //交换数据位置
            Collections.swap(data.getClasses(), fromPosition, toPosition);
            //刷新位置交换
            notifyItemMoved(fromPosition, toPosition);
        }
        //移动过程中移除view的放大效果
        onItemClear(source);
        ConfirmClassData classData = new ConfirmClassData();
        List<ConfirmClassData.Class> classes = new ArrayList<>();
        for (int i = 0; i < data.getClasses().size(); i++) {
            ConfirmClassData.Class c = new ConfirmClassData.Class();
            c.setClass_id(data.getClasses().get(i).getClass_id());
            c.setSort(i + 1);
            classes.add(c);
        }
        classData.setToken(PreferenceUtils.getInstance().getUserToken());
        classData.setClasses(classes);
        Log.e("交换后的位置",new Gson().toJson(classData));
        HttpUtil.createRequest(BaseUrl.getInstence().classSort()).confirmClass(classData).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body().getCode() == 1) {
                    ToastUtil.showToast("分类已重新排序");
                } else {
                    ToastUtil.showToast(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                ToastUtil.showToast("网络异常:排序失败");
            }
        });
    }

    @Override
    public void onItemDissmiss(RecyclerView.ViewHolder source) {
        int position = source.getAdapterPosition();
        data.getClasses().remove(position);
        notifyItemChanged(position);
    }

    @Override
    public void onItemSelect(RecyclerView.ViewHolder source) {
//当拖拽选中时放大选中的view
        source.itemView.setScaleX(1.2f);
        source.itemView.setScaleY(1.2f);
    }

    @Override
    public void onItemClear(RecyclerView.ViewHolder source) {
        //拖拽结束后恢复view的状态
        source.itemView.setScaleX(1.0f);
        source.itemView.setScaleY(1.0f);
    }

}