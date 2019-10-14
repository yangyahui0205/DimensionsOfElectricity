package com.baway.dimensionsofelectricity.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.CartBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.14
 *@Time: 19:50:54
 *@Description:
 * */public class ShoppingCartShopsAdapter extends BaseQuickAdapter<CartBean.ResultBean, BaseViewHolder> {
    public ShoppingCartShopsAdapter(int layoutResId, @Nullable List<CartBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final CartBean.ResultBean item) {
        //设置商店名称
        helper.setText(R.id.tv_parent_name, item.getCategoryName());
        //设置商店状态
        helper.setChecked(R.id.cb_parent, item.getShopsSelect());
        //获取checkbox
        final CheckBox cb_parent = helper.getView(R.id.cb_parent);
        //获取商品
        final RecyclerView rv_goods = helper.getView(R.id.rv_goods);
        rv_goods.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        final ShoppingCartGoodsAdapter goodsAdapter = new ShoppingCartGoodsAdapter(R.layout.layout_item_goods, item.getShoppingCartList());
        rv_goods.setAdapter(goodsAdapter);
        //点击商店 设置商品状态为相当应状态
        cb_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setShopsSelect(cb_parent.isChecked());
                for (int i = 0; i < item.getShoppingCartList().size(); i++) {
                    item.getShoppingCartList().get(i).setGoodsSelect(cb_parent.isChecked());
                }
                goodsAdapter.notifyDataSetChanged();
                //通知商家改变状态
                onClickShopsListener.shopsChange();
            }
        });
        //商家点击事件
        goodsAdapter.setOnClickGoodsListener(new ShoppingCartGoodsAdapter.OnClickGoodsListener() {
            @Override
            public void goodsState() {
                boolean state = true;
                for (int i = 0; i < item.getShoppingCartList().size(); i++) {
                    //将当前商家下的商品更改状态
                    boolean goodsSelect = item.getShoppingCartList().get(i).getGoodsSelect();
                    state = state & goodsSelect;
                }
                item.setShopsSelect(state);
                cb_parent.setChecked(state);
                //通知商家改变状态
                onClickShopsListener.shopsChange();
            }

           /* @Override
            public void getCurrentTotalPrice() {
                //通知商品点击
                onClickShopsListener.totalPrice();
            }*/
        });
    }

    public interface OnClickShopsListener {
        public void shopsChange();

        //public void totalPrice();
    }

    OnClickShopsListener onClickShopsListener;

    public void setOnClickShopsListener(OnClickShopsListener onClickShopsListener) {
        this.onClickShopsListener = onClickShopsListener;
    }
}
