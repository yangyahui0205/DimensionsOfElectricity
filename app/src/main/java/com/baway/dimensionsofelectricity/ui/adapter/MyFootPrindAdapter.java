package com.baway.dimensionsofelectricity.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.MyFootPrintBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.22
 *@Time: 21:21:03
 *@Description:
 * */public class MyFootPrindAdapter extends BaseQuickAdapter<MyFootPrintBean.ResultBean, BaseViewHolder> {
    public MyFootPrindAdapter(int layoutResId, @Nullable List<MyFootPrintBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyFootPrintBean.ResultBean item) {
        ImageView iv_my_footprint = helper.getView(R.id.iv_my_footprint_pic);
        Glide.with(mContext).load(item.getMasterPic()).into(iv_my_footprint);
        helper.setText(R.id.tv_my_footprint_name, item.getCommodityName());
        helper.setText(R.id.tv_my_footprint_price, item.getPrice() + "");
        helper.setText(R.id.tv_my_footprint_browse, "已浏览" + item.getBrowseNum() + "次");
        helper.setText(R.id.tv_my_footprint_time, item.getBrowseTime() + "");

    }
}
