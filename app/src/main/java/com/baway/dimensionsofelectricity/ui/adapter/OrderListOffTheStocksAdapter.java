package com.baway.dimensionsofelectricity.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

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
 * */public class OrderListOffTheStocksAdapter extends BaseQuickAdapter<OrderStatusBean.OrderListBean, BaseViewHolder> {
    public OrderListOffTheStocksAdapter(int layoutResId, @Nullable List<OrderStatusBean.OrderListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, OrderStatusBean.OrderListBean item) {
        ImageView iv_more = helper.getView(R.id.iv_order_list_off_the_stocks_more);
        iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickMoreListener.status(helper.getLayoutPosition());
            }
        });
        helper.setText(R.id.tv_order_list_off_the_stocks_number, item.getOrderId());
        helper.setText(R.id.tv_order_list_off_the_stocks_time, "2019-19-07 11:09");
        RecyclerView child_view = helper.getView(R.id.rv_order_detail_off_the_stocks_list);
        List<OrderStatusBean.OrderListBean.DetailListBean> detailList = item.getDetailList();
        //创建适配器
        OrderDetailListOffTheStocksAdapter allChildAdapter = new OrderDetailListOffTheStocksAdapter(R.layout.layout_item_order_detaillist_off_the_stocks, detailList);
        child_view.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        child_view.setAdapter(allChildAdapter);
    }

    public interface OnClickMoreListener {
        public void status(int layoutPosition);
    }

    OnClickMoreListener onClickMoreListener;

    public void setOnClickMoreListener(OnClickMoreListener onClickMoreListener) {
        this.onClickMoreListener = onClickMoreListener;
    }
}
