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
import com.baway.dimensionsofelectricity.ui.adapter.OrderListAllOrderAdapter;
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
 * */public class ListAllOrdersFragment extends Fragment {

    @BindView(R.id.rv_list_all_orders)
    RecyclerView rvListAllOrders;
    /* @BindView(R.id.tv_list_all_orders_totalCount)
     TextView tvListAllOrdersTotalCount;
     @BindView(R.id.tv_all_orders_totalPrice)
     TextView tvAllOrdersTotalPrice;
     @BindView(R.id.btn_all_orders_cancel_the_order)
     Button btnAllOrdersCancelTheOrder;
     @BindView(R.id.btn_all_orders_to_pay_for)
     Button btnAllOrdersToPayFor;*/
    private Unbinder unbinder;
    private List<OrderStatusBean.OrderListBean> orderList;
    private OrderListAllOrderAdapter orderListAdapter;
    private int userId;
    private String sessionId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_all_orders, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HttpUtils
                .getInstance()
                .getApiService()
                .findOrderListByStatus(userId, sessionId, 0, 1, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        allOrders(responseBody);
                        // refreshTheBottomBar();
                    }
                });
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //2
        SharedPreferences user = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        userId = user.getInt("userId", 4870);
        sessionId = user.getString("sessionId", "");
        HttpUtils
                .getInstance()
                .getApiService()
                .findOrderListByStatus(userId, sessionId, 0, 1, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        allOrders(responseBody);
                        // refreshTheBottomBar();
                    }
                });
    }

    private void allOrders(ResponseBody responseBody) {

        try {
            String responseBodyString
                    = responseBody.string().toString();
            Gson gson = new Gson();
            OrderStatusBean orderStatusBean = gson.fromJson(responseBodyString, OrderStatusBean.class);
            orderList = orderStatusBean.getOrderList();
            rvListAllOrders.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            orderListAdapter = new OrderListAllOrderAdapter(R.layout.layout_item_orderlist_all_orders, orderList);
            rvListAllOrders.setAdapter(orderListAdapter);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

  /*  private void refreshTheBottomBar() {
        double totalPrice = 0;
        int totalCount = 0;
        if (orderList.size() > 0) {
            for (int i = 0; i < orderList.size(); i++) {
                for (int j = 0; j < orderList.get(i).getDetailList().size(); j++) {
                    int commodityPrice = orderList.get(i).getDetailList().get(j).getCommodityPrice();
                    int commodityCount = orderList.get(i).getDetailList().get(j).getCommodityCount();
                    totalPrice += commodityPrice * commodityCount;
                    totalCount += commodityCount;
                }
            }
        }
        tvAllOrdersTotalPrice.setText(totalPrice + "");
        tvListAllOrdersTotalCount.setText(totalCount + "");
        orderListAdapter.notifyDataSetChanged();
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

   /* @OnClick({R.id.btn_all_orders_cancel_the_order, R.id.btn_all_orders_to_pay_for})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_all_orders_cancel_the_order:
                String cancel_orderId = orderList.get(0).getOrderId();
                HttpUtils
                        .getInstance()
                        .getApiService()
                        .deleteOrder(userId, sessionId, cancel_orderId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResponseBody>() {
                            @Override
                            public void accept(ResponseBody responseBody) throws Exception {
                                String responseBodyString = responseBody.string().toString();
                                Toast.makeText(getContext(), responseBodyString, Toast.LENGTH_SHORT).show();
                            }
                        });
                orderListAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_all_orders_to_pay_for:
                //去支付
                String orderId = orderList.get(0).getOrderId();
                Intent intent = new Intent(getContext(), PayActivity.class);
                String totalPrice = tvAllOrdersTotalPrice.getText().toString();
                intent.putExtra("totalPrice", totalPrice);
                intent.putExtra("orderId", orderId);
                startActivityForResult(intent, 123);

                break;
        }
    }*/

}
