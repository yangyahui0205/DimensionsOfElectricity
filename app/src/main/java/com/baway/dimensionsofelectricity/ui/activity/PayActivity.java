package com.baway.dimensionsofelectricity.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.net.HttpUtils;
import com.baway.dimensionsofelectricity.ui.adapter.AddressListPopAdapter;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class PayActivity extends AppCompatActivity {


    @BindView(R.id.cb_balance_paid)
    CheckBox cbBalancePaid;
    @BindView(R.id.cb_WeChat_pay)
    CheckBox cbWeChatPay;
    @BindView(R.id.cb_Alipay_payment)
    CheckBox cbAlipayPayment;
    @BindView(R.id.btn_the_balance_payment)
    Button btnTheBalancePayment;
    private int payType = 1;
    private int userId;
    private String sessionId;
    private String orderId;
    private String totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        userId = user.getInt("userId", 4870);
        sessionId = user.getString("sessionId", "");
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        totalPrice = intent.getStringExtra("totalPrice");
        btnTheBalancePayment.setText("余额支付" + totalPrice + "元");
    }

    @OnClick({R.id.cb_balance_paid, R.id.cb_WeChat_pay, R.id.cb_Alipay_payment, R.id.btn_the_balance_payment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cb_balance_paid:
                if (cbBalancePaid.isChecked()) {
                    payType = 1;
                }
                break;
            case R.id.cb_WeChat_pay:
                if (cbWeChatPay.isChecked()) {
                    payType = 2;
                }
                break;
            case R.id.cb_Alipay_payment:
                if (cbAlipayPayment.isChecked()) {
                    payType = 3;
                }
                break;
            case R.id.btn_the_balance_payment:
                HttpUtils
                        .getInstance()
                        .getApiService()
                        .pay(userId, sessionId, orderId, payType)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResponseBody>() {
                            @Override
                            public void accept(ResponseBody responseBody) throws Exception {
                                String responseBodyString = responseBody.string().toString();
                                Log.d("PayActivity", responseBodyString);
                                Gson gson = new Gson();
                                PayBean payBean = gson.fromJson(responseBodyString, PayBean.class);
                                Toast.makeText(PayActivity.this, payBean.getMessage(), Toast.LENGTH_SHORT).show();

                                if (payBean.getStatus().equals("1001")) {
                                    paymentFailedPop();

                                } else if (payBean.getStatus().equals("0000")) {
                                    paymentSuccessPop();
                                }

                            }
                        });

                //finish();
                break;
        }
    }

    private void paymentFailedPop() {
        //获取布局
        View contentView = LayoutInflater.from(PayActivity.this).inflate(R.layout.layout_item_payment_failed, null);
        //设置弹出框的位置
        final PopupWindow mPopupWindow = new PopupWindow(contentView, RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT, true);
        //给弹出框设置布局
        // 设置PopupWindow是否能响应外部点击事件
        mPopupWindow.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        mPopupWindow.setTouchable(true);
        mPopupWindow.setContentView(contentView);
        //设置各个控件的点击事件
        Button btn_payment_failed_continue_to_pay = contentView.findViewById(R.id.btn_payment_failed_continue_to_pay);
        btn_payment_failed_continue_to_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
    }

    private void paymentSuccessPop() {
        //获取布局
        View contentView = LayoutInflater.from(PayActivity.this).inflate(R.layout.layout_item_payment_success, null);
        //设置弹出框的位置
        final PopupWindow mPopupWindow = new PopupWindow(contentView, RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT, true);
        //给弹出框设置布局
        // 设置PopupWindow是否能响应外部点击事件
        mPopupWindow.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        mPopupWindow.setTouchable(true);
        mPopupWindow.setContentView(contentView);
        //设置各个控件的点击事件
        Button btn_payment_success_home_back = contentView.findViewById(R.id.btn_payment_success_home_back);
        Button btn_payment_success_check_order = contentView.findViewById(R.id.btn_payment_success_check_order);
        mPopupWindow.showAtLocation(btnTheBalancePayment, Gravity.CENTER, 0, 0);
        btn_payment_success_check_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(321, new Intent());
                finish();
                mPopupWindow.dismiss();
            }
        });
        btn_payment_success_home_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(321, new Intent());
                finish();
                mPopupWindow.dismiss();
            }
        });
    }
}

class PayBean {

    /**
     * message : 支付成功
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