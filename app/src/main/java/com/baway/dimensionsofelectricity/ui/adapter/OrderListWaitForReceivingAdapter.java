package com.baway.dimensionsofelectricity.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

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
 * */public class OrderListWaitForReceivingAdapter extends BaseQuickAdapter<OrderStatusBean.OrderListBean, BaseViewHolder> {
    public OrderListWaitForReceivingAdapter(int layoutResId, @Nullable List<OrderStatusBean.OrderListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderStatusBean.OrderListBean item) {
        helper.setText(R.id.tv_order_list_wait_for_receiving_number, item.getOrderId());
        helper.setText(R.id.tv_order_list_wait_for_receiving_time, "2019-19-07");
        helper.setText(R.id.tv_order_list_wait_for_receiving_express, item.getExpressCompName());
        helper.setText(R.id.tv_order_list_wait_for_receiving_odd, item.getExpressSn());
        Button btn_order_wait_for_receiving = helper.getView(R.id.btn_order_list_wait_for_receiving);
        btn_order_wait_for_receiving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderId = item.getOrderId();
                onClickConfirmReceiptListener.status(orderId);
            }
        });
        RecyclerView child_view = helper.getView(R.id.rv_order_detail_wait_for_receiving_list);
        List<OrderStatusBean.OrderListBean.DetailListBean> detailList = item.getDetailList();
        //创建适配器
        OrderDetailListWaitForReceivingAdapter allChildAdapter = new OrderDetailListWaitForReceivingAdapter(R.layout.layout_item_order_detaillist_wait_for_receiving, detailList);
        child_view.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        child_view.setAdapter(allChildAdapter);
    }

    public interface OnClickConfirmReceiptListener {
        public void status(String orderId);
    }

    OnClickConfirmReceiptListener onClickConfirmReceiptListener;

    public void setOnClickConfirmReceiptListener(OnClickConfirmReceiptListener onClickConfirmReceiptListener) {
        this.onClickConfirmReceiptListener = onClickConfirmReceiptListener;
    }
}
