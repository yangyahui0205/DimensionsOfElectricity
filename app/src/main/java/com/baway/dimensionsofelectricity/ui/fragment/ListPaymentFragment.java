package com.baway.dimensionsofelectricity.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.OrderStatusBean;
import com.baway.dimensionsofelectricity.data.net.HttpUtils;
import com.baway.dimensionsofelectricity.ui.activity.PayActivity;
import com.baway.dimensionsofelectricity.ui.adapter.OrderListPaymentAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static android.content.Context.MODE_PRIVATE;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.20
 *@Time: 11:52:50
 *@Description:
 * */public class ListPaymentFragment extends Fragment {


    @BindView(R.id.tv_list_payment_totalCount)
    TextView tvListPaymentTotalCount;
    @BindView(R.id.tv_settlement_totalPrice)
    TextView tvSettlementTotalPrice;
    @BindView(R.id.rv_list_payment)
    RecyclerView rvListPayment;
    @BindView(R.id.btn_list_payment_cancel_the_order)
    Button btnListPaymentCancelTheOrder;
    @BindView(R.id.btn_list_payment_to_pay_for)
    Button btnListPaymentToPayFor;
    private Unbinder unbinder;
    private List<OrderStatusBean.OrderListBean> orderList;
    private OrderListPaymentAdapter orderListAdapter;
    private int userId;
    private String sessionId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_payment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HttpUtils
                .getInstance()
                .getApiService()
                .findOrderListByStatus(userId, sessionId, 1, 1, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        getPaymentData(responseBody);
                    }
                });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences user = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        userId = user.getInt("userId", 4870);
        sessionId = user.getString("sessionId", "");
        HttpUtils
                .getInstance()
                .getApiService()
                .findOrderListByStatus(userId, sessionId, 1, 1, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        getPaymentData(responseBody);
                    }
                });
    }

    private void getPaymentData(ResponseBody responseBody) {


        try {

            String responseBodyString
                    = responseBody.string().toString();

            Gson gson = new Gson();
            OrderStatusBean orderStatusBean = gson.fromJson(responseBodyString, OrderStatusBean.class);
            orderList = orderStatusBean.getOrderList();
            rvListPayment.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            orderListAdapter = new OrderListPaymentAdapter(R.layout.layout_item_orderlist_payment, orderList);
            rvListPayment.setAdapter(orderListAdapter);
            refreshTheBottomBar();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void refreshTheBottomBar() {
        double totalPrice = 0;
        int totalCount = 0;
        for (int i = 0; i < orderList.size(); i++) {
            for (int j = 0; j < orderList.get(i).getDetailList().size(); j++) {
                int commodityPrice = orderList.get(i).getDetailList().get(j).getCommodityPrice();
                int commodityCount = orderList.get(i).getDetailList().get(j).getCommodityCount();
                totalPrice += commodityPrice * commodityCount;
                totalCount += commodityCount;
            }
        }
        tvSettlementTotalPrice.setText(totalPrice + "");
        tvListPaymentTotalCount.setText(totalCount + "");
        orderListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_list_payment_cancel_the_order, R.id.btn_list_payment_to_pay_for})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_list_payment_cancel_the_order:
                for (int i = 0; i < orderList.size(); i++) {
                    String orderId = orderList.get(i).getOrderId();
                    HttpUtils
                            .getInstance()
                            .getApiService()
                            .deleteOrder(userId, sessionId, orderId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<ResponseBody>() {
                                @Override
                                public void accept(ResponseBody responseBody) throws Exception {
                                    String responseBodyString = responseBody.string().toString();
                                    Toast.makeText(getContext(), responseBodyString, Toast.LENGTH_SHORT).show();
                                    onResume();
                                }
                            });
                }

                break;
            case R.id.btn_list_payment_to_pay_for:
                //去支付
                String orderId = orderList.get(0).getOrderId();
                Intent intent = new Intent(getContext(), PayActivity.class);
                String totalPrice = tvSettlementTotalPrice.getText().toString();
                intent.putExtra("totalPrice", totalPrice);
                intent.putExtra("orderId", orderId);
                startActivityForResult(intent, 123);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == 321) {
            onResume();
        }
    }
}
