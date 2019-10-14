package com.baway.dimensionsofelectricity.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.HomePageBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.16
 *@Time: 22:37:00
 *@Description:
 * */public class HotAdapter extends BaseQuickAdapter<HomePageBean.ResultBean.RxxpBean.CommodityListBean, BaseViewHolder> {
    public HotAdapter(int layoutResId, @Nullable List<HomePageBean.ResultBean.RxxpBean.CommodityListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomePageBean.ResultBean.RxxpBean.CommodityListBean item) {
        helper.setText(R.id.tv_hot_name,item.getCommodityName());
        helper.setText(R.id.tv_hot_price,item.getPrice()+"");
        ImageView imageView = helper.getView(R.id.iv_hot_pic);
        Glide.with(mContext).load(item.getMasterPic()).into(imageView);
    }
}
