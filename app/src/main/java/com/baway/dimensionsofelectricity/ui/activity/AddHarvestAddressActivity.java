package com.baway.dimensionsofelectricity.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.di.contract.IAddHarvestAddressContract;
import com.baway.dimensionsofelectricity.di.presenter.AddHarvestAddressPresenter;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddHarvestAddressActivity extends BaseActivity<IAddHarvestAddressContract.IAddHarvestAddressView, AddHarvestAddressPresenter<IAddHarvestAddressContract.IAddHarvestAddressView>> implements IAddHarvestAddressContract.IAddHarvestAddressView {


    @BindView(R.id.et_add_address_realName)
    EditText etAddAddressRealName;
    @BindView(R.id.et_add_address_phone)
    EditText etAddAddressPhone;
    @BindView(R.id.et_add_address_location)
    EditText etAddAddressLocation;
    @BindView(R.id.et_add_address_address)
    EditText etAddAddressAddress;
    @BindView(R.id.et_add_address_zipCode)
    EditText etAddAddressZipCode;
    @BindView(R.id.btn_add_address_saveAndUse)
    Button btnAddAddressSaveAndUse;
    private int userId;
    private String sessionId;

    @Override
    public void showMyHarvestAddressData(String message) {
        Gson gson = new Gson();
        AddHarvestAddressBean addHarvestAddressBean = gson.fromJson(message, AddHarvestAddressBean.class);
        Toast.makeText(this, addHarvestAddressBean.getMessage(), Toast.LENGTH_SHORT).show();
        if (addHarvestAddressBean.getStatus().equals("0000")) {
            setResult(112,new Intent());
            finish();
        }
    }

    @Override
    protected AddHarvestAddressPresenter<IAddHarvestAddressContract.IAddHarvestAddressView> createPresenter() {
        return new AddHarvestAddressPresenter<>();
    }

    @Override
    protected void initData() {
        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        userId = user.getInt("userId", 4870);
        sessionId = user.getString("sessionId", "");
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_add_harvest_address;
    }

    @OnClick(R.id.btn_add_address_saveAndUse)
    public void onViewClicked() {
        String address = etAddAddressAddress.getText().toString().trim();
        String location = etAddAddressLocation.getText().toString().trim();
        String phone = etAddAddressPhone.getText().toString().trim();
        String name = etAddAddressRealName.getText().toString().trim();
        String zipCode = etAddAddressZipCode.getText().toString().trim();
        Log.d("AddHarvestAddressActivi", zipCode);
        Log.d("AddHarvestAddressActivi", "111111:" + 111111);
        presenter.requestAddHarvestAddressData(userId, sessionId, name, phone, location + " " + address, zipCode);

    }

    class AddHarvestAddressBean {

        /**
         * message : 添加成功
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
}
