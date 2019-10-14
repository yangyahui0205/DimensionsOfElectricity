package com.baway.dimensionsofelectricity.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.OrderStatusBean;
import com.baway.dimensionsofelectricity.ui.widget.CustomSubTractor;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.19
 *@Time: 09:37:48
 *@Description:
 * */public class OrderDetailListWaitForReceivingAdapter extends BaseQuickAdapter<OrderStatusBean.OrderListBean.DetailListBean, BaseViewHolder> {
    public OrderDetailListWaitForReceivingAdapter(int layoutResId, @Nullable List<OrderStatusBean.OrderListBean.DetailListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderStatusBean.OrderListBean.DetailListBean item) {
        final ImageView img = helper.getView(R.id.iv_order_detail_wait_for_receiving_pic);
        helper.setText(R.id.tv_order_detail_wait_for_receiving_name, item.getCommodityName());
        helper.setText(R.id.tv_order_detail_wait_for_receiving_price, item.getCommodityPrice() + "");
        String commodityPic = item.getCommodityPic();
        String[] split = commodityPic.split(",");
        ArrayList<String> list = new ArrayList<>(Arrays.asList(split));
        for (int i = 0; i < list.size(); i++) {
            Glide.with(mContext).load(list.get(i)).into(img);
        }
    }
}
