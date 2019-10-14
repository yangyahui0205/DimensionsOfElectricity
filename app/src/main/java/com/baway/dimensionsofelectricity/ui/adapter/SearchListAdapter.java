package com.baway.dimensionsofelectricity.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.SearchListBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/*
 *@Auther:杨雅慧
 *@Date: 19.6.27
 *@Time: 19:53:51
 *@Description:
 * */public class SearchListAdapter extends BaseQuickAdapter<SearchListBean.ResultBean, BaseViewHolder> {


    public SearchListAdapter(int layoutResId, @Nullable List<SearchListBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchListBean.ResultBean item) {
        helper.setText(R.id.tv_search_name, item.getCommodityName());
        helper.setText(R.id.tv_search_price, item.getPrice() + "");
        helper.setText(R.id.tv_search_sell, "已售" + item.getSaleNum() + "件");
        ImageView iv_search_pic = helper.getView(R.id.iv_search_pic);
        Glide.with(mContext).load(item.getMasterPic()).into(iv_search_pic);

    }
}
