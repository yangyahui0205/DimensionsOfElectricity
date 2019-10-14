package com.baway.dimensionsofelectricity.ui.adapter;

import android.support.annotation.Nullable;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.WalletBean;
import com.baway.dimensionsofelectricity.data.net.TimeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.21
 *@Time: 15:45:05
 *@Description:
 * */public class MyWalletAdapter extends BaseQuickAdapter<WalletBean.ResultBean.DetailListBean, BaseViewHolder> {
    public MyWalletAdapter(int layoutResId, @Nullable List<WalletBean.ResultBean.DetailListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WalletBean.ResultBean.DetailListBean item) {
        helper.setText(R.id.tv_wallet_expense_money, item.getAmount()+"");
        long createTime = item.getConsumerTime();
        String dateTime = TimeUtil.getInstance().timeStamp2Date(String.valueOf(createTime), "yyyy-MM-dd HH:mm:ss");
        helper.setText(R.id.tv_wallet_expense_time, dateTime);
    }
}
