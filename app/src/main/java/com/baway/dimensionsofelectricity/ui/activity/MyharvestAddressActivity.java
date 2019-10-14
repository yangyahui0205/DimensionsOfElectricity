package com.baway.dimensionsofelectricity.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.AddressListBean;
import com.baway.dimensionsofelectricity.di.contract.IMyharvestAddressContract;
import com.baway.dimensionsofelectricity.di.presenter.MyHarvestAddressPresenter;
import com.baway.dimensionsofelectricity.ui.adapter.MyHarvestAddressAdapter;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyharvestAddressActivity extends BaseActivity<IMyharvestAddressContract.IMyharvestAddressView, MyHarvestAddressPresenter<IMyharvestAddressContract.IMyharvestAddressView>> implements IMyharvestAddressContract.IMyharvestAddressView {


    @BindView(R.id.tv_myHarvest_address_complete)
    TextView tvMyHarvestAddressComplete;
    @BindView(R.id.rv_myHarvest_address)
    RecyclerView rvMyHarvestAddress;
    @BindView(R.id.btn_add_myHarvest_address)
    Button btnAddMyHarvestAddress;
    private int userId;
    private String sessionId;
    private MyHarvestAddressAdapter addressAdapter;

    @Override
    public void showMyHarvestAddressData(String message) {
        Gson gson = new Gson();
        final AddressListBean addressBean = gson.fromJson(message, AddressListBean.class);
        final List<AddressListBean.ResultBean> addressBeanResult = addressBean.getResult();
        rvMyHarvestAddress.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        addressAdapter = new MyHarvestAddressAdapter(R.layout.layout_item_myharvest_address, addressBeanResult);
        rvMyHarvestAddress.setAdapter(addressAdapter);
        addressAdapter.setOnClickAddressStatusListener(new MyHarvestAddressAdapter.OnClickAddressStatusListener() {
            @Override
            public void status(boolean checked, int layoutPosition) {
                if (checked) {
                    for (int i = 0; i < addressBeanResult.size(); i++) {
                        addressBeanResult.get(i).setWhetherDefault(2);
                    }
                    addressBeanResult.get(layoutPosition).setWhetherDefault(1);
                    //设置默认地址
                    //http://172.17.8.100/small/user/verify/v1/setDefaultReceiveAddress
                    presenter.requestDefaultReceiveAddressData(userId, sessionId, addressBeanResult.get(layoutPosition).getId());
                }
                addressAdapter.notifyDataSetChanged();
            }

            @Override
            public void update(int id, String realName, String phone, String address, String zipCode, int layoutPosition) {
                Intent intent = new Intent(context, UpdateHarvestAddressActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("realName", realName);
                intent.putExtra("phone", phone);
                intent.putExtra("address", address);
                intent.putExtra("zipCode", zipCode);
                intent.putExtra("position", layoutPosition);
                startActivityForResult(intent, 100);
            }

            @Override
            public void remove(int layoutPosition) {
                addressAdapter.remove(layoutPosition);
                addressAdapter.notifyDataSetChanged();

            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 100 && resultCode == 101) {
                int position = data.getIntExtra("position", 0);
                Log.d("MyharvestAddressActivit", "position:" + position);
                presenter.requestMyHarvestAddressData(userId, sessionId);
            }
            if (requestCode == 111 && resultCode == 112) {
                presenter.requestMyHarvestAddressData(userId, sessionId);
            }
        }
    }

    @Override
    public void showDefaultReceiveAddressData(String message) {
        Gson gson = new Gson();
        DefaultReceiveAddress defaultReceiveAddress = gson.fromJson(message, DefaultReceiveAddress.class);
        Toast.makeText(this, defaultReceiveAddress.getMessage().toString(), Toast.LENGTH_SHORT).show();
    }

    class DefaultReceiveAddress {

        /**
         * message : 设置成功
         * status : 0000
         */

        private String message;
        private String status;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    @Override
    protected MyHarvestAddressPresenter<IMyharvestAddressContract.IMyharvestAddressView> createPresenter() {
        return new MyHarvestAddressPresenter<>();
    }

    @Override
    protected void initData() {
        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        userId = user.getInt("userId", 4870);
        sessionId = user.getString("sessionId", "");
        presenter.requestMyHarvestAddressData(userId, sessionId);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_myharvest_address;
    }


    @OnClick({R.id.tv_myHarvest_address_complete, R.id.btn_add_myHarvest_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_myHarvest_address_complete:
                finish();
                break;
            case R.id.btn_add_myHarvest_address:
                startActivityForResult(new Intent(context, AddHarvestAddressActivity.class), 111);
                break;
        }
    }
}
