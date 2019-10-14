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
 * */public class OrderDetailListToEvaluateAdapter extends BaseQuickAdapter<OrderStatusBean.OrderListBean.DetailListBean, BaseViewHolder> {

    public OrderDetailListToEvaluateAdapter(int layoutResId, @Nullable List<OrderStatusBean.OrderListBean.DetailListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderStatusBean.OrderListBean.DetailListBean item) {
        final ImageView img = helper.getView(R.id.iv_order_detail_to_evaluate_pic);
        helper.setText(R.id.tv_order_detail_to_evaluate_name, item.getCommodityName());
        helper.setText(R.id.tv_order_detail_to_evaluate_price, item.getCommodityPrice() + "");
        String commodityPic = item.getCommodityPic();
        String[] split = commodityPic.split(",");
        ArrayList<String> list = new ArrayList<>(Arrays.asList(split));
        for (int i = 0; i < list.size(); i++) {
            Glide.with(mContext).load(list.get(i)).into(img);
        }
        Button btn_to_evaluate = helper.getView(R.id.btn_order_detail_to_evaluate);
        btn_to_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "去评价", Toast.LENGTH_SHORT).show();
                onClickToEvaluateListener.status(item.getCommodityId(),
                        item.getCommodityName(),
                        item.getCommodityPic(),
                        item.getCommodityPrice());
            }
        });
    }

    public interface OnClickToEvaluateListener {
        public void status(int commodityId, String commodityName, String commodityPic, int commodityPrice);
    }

    OnClickToEvaluateListener onClickToEvaluateListener;

    public void setOnClickToEvaluateListener(OnClickToEvaluateListener onClickToEvaluateListener) {
        this.onClickToEvaluateListener = onClickToEvaluateListener;
    }
}
