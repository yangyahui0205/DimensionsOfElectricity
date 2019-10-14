package com.baway.dimensionsofelectricity.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.OrderStatusBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.19
 *@Time: 09:37:14
 *@Description:
 * */public class OrderListToEvaluateAdapter extends BaseQuickAdapter<OrderStatusBean.OrderListBean, BaseViewHolder> {
    public OrderListToEvaluateAdapter(int layoutResId, @Nullable List<OrderStatusBean.OrderListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderStatusBean.OrderListBean item) {
        helper.setText(R.id.tv_order_list_to_evaluate_number, item.getOrderId());
        helper.setText(R.id.tv_order_list_to_evaluate_time, "2019-19-07 11:09");
        RecyclerView child_view = helper.getView(R.id.rv_order_detail_to_evaluate_list);
        List<OrderStatusBean.OrderListBean.DetailListBean> detailList = item.getDetailList();
        //创建适配器
        OrderDetailListToEvaluateAdapter allChildAdapter = new OrderDetailListToEvaluateAdapter(R.layout.layout_item_order_detaillist_to_evaluate, detailList);
        child_view.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        child_view.setAdapter(allChildAdapter);
        allChildAdapter.setOnClickToEvaluateListener(new OrderDetailListToEvaluateAdapter.OnClickToEvaluateListener() {
            @Override
            public void status(int commodityId, String commodityName, String commodityPic, int commodityPrice) {
                onClickEvaluateListener.status(commodityId,
                        item.getOrderId(),
                        commodityName,
                        commodityPic,
                        commodityPrice);
            }


        });
    }

    public interface OnClickEvaluateListener {
        public void status(int commodityId, String orderId, String commodityName, String commodityPic, int commodityPrice);
    }

    OnClickEvaluateListener onClickEvaluateListener;

    public void setOnClickEvaluateListener(OnClickEvaluateListener onClickEvaluateListener) {
        this.onClickEvaluateListener = onClickEvaluateListener;
    }
}
