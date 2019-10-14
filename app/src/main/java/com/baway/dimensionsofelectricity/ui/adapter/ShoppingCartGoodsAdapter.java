package com.baway.dimensionsofelectricity.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.CartBean;
import com.baway.dimensionsofelectricity.ui.widget.CustomSubTractor;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.14
 *@Time: 19:54:56
 *@Description:
 * */public class ShoppingCartGoodsAdapter extends BaseQuickAdapter<CartBean.ResultBean.ShoppingCartListBean, BaseViewHolder> {
    public ShoppingCartGoodsAdapter(int layoutResId, @Nullable List<CartBean.ResultBean.ShoppingCartListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CartBean.ResultBean.ShoppingCartListBean item) {
        ImageView iv_child_pic = helper.getView(R.id.iv_child_pic);
        Glide.with(mContext).load(item.getPic()).into(iv_child_pic);
        helper.setChecked(R.id.cb_child, item.getGoodsSelect());
        helper.setText(R.id.tv_child_name, item.getCommodityName());
        helper.setText(R.id.tv_child_price, item.getPrice() + "");
        final CheckBox cb_child = helper.getView(R.id.cb_child);
        final CustomSubTractor subTractor = helper.getView(R.id.subTractor);
        subTractor.setCount(item.getCount());
        //点击加减器 更改count值
        subTractor.setOnClickSubTractorListener(new CustomSubTractor.OnClickSubTractorListener() {
            @Override
            public void subTractor(int count) {
                //设置当前count值
                subTractor.setCount(count);
                //将当前count值存入到item中
                item.setCount(count);
                //通知更改底部栏
                onClickGoodsListener.goodsState();
            }
        });
        //点击商品回传商品状态
        cb_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击商品 将商品状态存入到item中
                    item.setGoodsSelect(cb_child.isChecked());
                //通知商家 商品点击
                onClickGoodsListener.goodsState();
            }
        });
    }

    public interface OnClickGoodsListener {
        public void goodsState();
    }

    OnClickGoodsListener onClickGoodsListener;

    public void setOnClickGoodsListener(OnClickGoodsListener onClickGoodsListener) {
        this.onClickGoodsListener = onClickGoodsListener;
    }
}
