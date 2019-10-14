package com.baway.dimensionsofelectricity.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.AddressListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.19
 *@Time: 21:16:21
 *@Description:
 * */public class AddressListPopAdapter extends BaseQuickAdapter<AddressListBean.ResultBean, BaseViewHolder> {
    public AddressListPopAdapter(int layoutResId, @Nullable List<AddressListBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final AddressListBean.ResultBean item) {
        helper.setText(R.id.tv_pop_address_name, item.getRealName());
        helper.setText(R.id.tv_pop_address_phone, item.getPhone());
        helper.setText(R.id.tv_pop_address_detail, item.getAddress());
        TextView choose = helper.getView(R.id.tv_pop_address_choose);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddressListChooseListener.addressListDetail(item.getId(),item.getAddress(), item.getPhone(), item.getRealName(), item.getZipCode());
            }
        });
    }

    public interface OnClickAddressListChooseListener {
        public void addressListDetail(int id, String address, String phone, String realName, String zipCode);
    }

    OnClickAddressListChooseListener onClickAddressListChooseListener;

    public void setOnClickAddressListChooseListener(OnClickAddressListChooseListener onClickAddressListChooseListener) {
        this.onClickAddressListChooseListener = onClickAddressListChooseListener;
    }
}
