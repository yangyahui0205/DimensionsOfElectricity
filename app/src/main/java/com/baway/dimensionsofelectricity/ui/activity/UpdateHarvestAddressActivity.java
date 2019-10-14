package com.baway.dimensionsofelectricity.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.net.HttpUtils;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class UpdateHarvestAddressActivity extends AppCompatActivity {

    @BindView(R.id.et_update_address_realName)
    EditText etUpdateAddressRealName;
    @BindView(R.id.et_update_address_phone)
    EditText etUpdateAddressPhone;
    @BindView(R.id.et_update_address_location)
    EditText etUpdateAddressLocation;
    @BindView(R.id.et_update_address_address)
    EditText etUpdateAddressAddress;
    @BindView(R.id.et_update_address_zipCode)
    EditText etUpdateAddressZipCode;
    @BindView(R.id.btn_update_address_saveAndUse)
    Button btnUpdateAddressSaveAndUse;
    private int id;
    private int userId;
    private String sessionId;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_harvest_address);
        ButterKnife.bind(this);
        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        userId = user.getInt("userId", 4870);
        sessionId = user.getString("sessionId", "");
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        position = intent.getIntExtra("position", 0);
        String realName = intent.getStringExtra("realName");
        String phone = intent.getStringExtra("phone");
        String address = intent.getStringExtra("address");
        String zipCode = intent.getStringExtra("zipCode");
        etUpdateAddressAddress.setText(address);
        etUpdateAddressLocation.setText(address);
        etUpdateAddressPhone.setText(phone);
        etUpdateAddressRealName.setText(realName);
        etUpdateAddressZipCode.setText(zipCode);
    }

    @OnClick(R.id.btn_update_address_saveAndUse)
    public void onViewClicked() {
        String address = etUpdateAddressAddress.getText().toString();
        String phone = etUpdateAddressPhone.getText().toString();
        String realName = etUpdateAddressRealName.getText().toString();
        String zipCode = etUpdateAddressZipCode.getText().toString();
        HttpUtils
                .getInstance()
                .getApiService()
                .changeReceiveAddress(userId, sessionId, id, realName, phone, address, zipCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String responseBodyString = responseBody.string().toString();
                        Gson gson = new Gson();
                        UpdateHarvestAddressBean updateHarvestAddressBean = gson.fromJson(responseBodyString, UpdateHarvestAddressBean.class);
                        Toast.makeText(UpdateHarvestAddressActivity.this, updateHarvestAddressBean.getMessage(), Toast.LENGTH_SHORT).show();
                        if (updateHarvestAddressBean.getStatus().equals("0000")) {
                            Intent intent = new Intent();
                            intent.putExtra("position",position);
                            setResult(101, intent);
                            finish();
                        }
                    }
                });
    }

    class UpdateHarvestAddressBean {

        /**
         * message : 修改成功
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
