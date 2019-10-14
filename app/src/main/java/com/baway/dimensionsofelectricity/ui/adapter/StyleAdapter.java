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
 *@Time: 23:06:41
 *@Description:
 * */public class StyleAdapter extends BaseQuickAdapter<HomePageBean.ResultBean.MlssBean.CommodityListBeanXX, BaseViewHolder> {
    public StyleAdapter(int layoutResId, @Nullable List<HomePageBean.ResultBean.MlssBean.CommodityListBeanXX> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomePageBean.ResultBean.MlssBean.CommodityListBeanXX item) {
        helper.setText(R.id.tv_style_name,item.getCommodityName());
        helper.setText(R.id.tv_style_price,item.getPrice()+"");
        ImageView imageView = helper.getView(R.id.iv_style_pic);
        Glide.with(mContext).load(item.getMasterPic()).into(imageView);
    }
}
