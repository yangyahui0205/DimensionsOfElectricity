package com.baway.dimensionsofelectricity.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.AddressListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.17
 *@Time: 09:17:32
 *@Description:
 * */public class MyHarvestAddressAdapter extends BaseQuickAdapter<AddressListBean.ResultBean, BaseViewHolder> {
    public MyHarvestAddressAdapter(int layoutResId, @Nullable List<AddressListBean.ResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AddressListBean.ResultBean item) {
        helper.setText(R.id.tv_address_name, item.getRealName());
        helper.setText(R.id.tv_address_phone, item.getPhone());
        helper.setText(R.id.tv_address_address, item.getAddress());
        if (item.getWhetherDefault() == 1) {
            helper.setText(R.id.tv_address_status, "当前地址");
            helper.setChecked(R.id.cb_address_status, true);
        } else if (item.getWhetherDefault() == 2) {
            helper.setText(R.id.tv_address_status, "设为默认地址");
            helper.setChecked(R.id.cb_address_status, false);
        }
        final CheckBox cb_address_status = helper.getView(R.id.cb_address_status);
        cb_address_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddressStatusListener.status(cb_address_status.isChecked(), helper.getLayoutPosition());
            }
        });

        Button address_update = helper.getView(R.id.btn_address_update);
        Button address_remove = helper.getView(R.id.btn_address_remove);
        address_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddressStatusListener.update(item.getId(), item.getRealName(), item.getPhone(), item.getAddress(), item.getZipCode(),helper.getLayoutPosition());
            }
        });
        address_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddressStatusListener.remove(helper.getLayoutPosition());
            }
        });
    }


    public interface OnClickAddressStatusListener {
        public void status(boolean checked, int layoutPosition);

        public void update(int id, String realName, String phone, String address, String zipCode, int layoutPosition);

        public void remove(int layoutPosition);

    }

    OnClickAddressStatusListener onClickAddressStatusListener;

    public void setOnClickAddressStatusListener(OnClickAddressStatusListener onClickAddressStatusListener) {
        this.onClickAddressStatusListener = onClickAddressStatusListener;
    }
}
