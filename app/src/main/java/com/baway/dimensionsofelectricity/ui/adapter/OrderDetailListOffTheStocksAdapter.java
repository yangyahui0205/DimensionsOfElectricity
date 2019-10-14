package com.baway.dimensionsofelectricity.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.OrderStatusBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.21
 *@Time: 14:03:08
 *@Description:
 * */public class OrderDetailListOffTheStocksAdapter extends BaseQuickAdapter<OrderStatusBean.OrderListBean.DetailListBean, BaseViewHolder> {

    public OrderDetailListOffTheStocksAdapter(int layoutResId, @Nullable List<OrderStatusBean.OrderListBean.DetailListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderStatusBean.OrderListBean.DetailListBean item) {
        final ImageView img = helper.getView(R.id.iv_order_detail_off_the_stocks_pic);
        helper.setText(R.id.tv_order_detail_off_the_stocks_name, item.getCommodityName());
        helper.setText(R.id.tv_order_detail_off_the_stocks_price, item.getCommodityPrice() + "");
        String commodityPic = item.getCommodityPic();
        String[] split = commodityPic.split(",");
        ArrayList<String> list = new ArrayList<>(Arrays.asList(split));
        for (int i = 0; i < list.size(); i++) {
            Glide.with(mContext).load(list.get(i)).into(img);
        }
    }
}
