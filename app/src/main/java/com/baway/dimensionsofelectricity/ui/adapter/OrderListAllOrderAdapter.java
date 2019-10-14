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
 * */public class OrderListAllOrderAdapter extends BaseQuickAdapter<OrderStatusBean.OrderListBean, BaseViewHolder> {
    public OrderListAllOrderAdapter(int layoutResId, @Nullable List<OrderStatusBean.OrderListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderStatusBean.OrderListBean item) {
        helper.setText(R.id.tv_order_list_all_orders_number, item.getOrderId());
        helper.setText(R.id.tv_order_list_all_orders_time, "2019-19-07");
        RecyclerView child_view = helper.getView(R.id.rv_order_detail_all_orders_list);
        List<OrderStatusBean.OrderListBean.DetailListBean> detailList = item.getDetailList();
        //创建适配器
        OrderDetailListAllOrdersAdapter allChildAdapter = new OrderDetailListAllOrdersAdapter(R.layout.layout_item_order_detaillist_all_orders, detailList);
        child_view.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        child_view.setAdapter(allChildAdapter);
    }
}
