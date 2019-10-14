package com.baway.dimensionsofelectricity.ui.fragment;

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
import android.widget.Toast;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.OrderStatusBean;
import com.baway.dimensionsofelectricity.data.net.HttpUtils;
import com.baway.dimensionsofelectricity.ui.adapter.OrderListOffTheStocksAdapter;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
 * */public class ListOffTheStocksFragment extends Fragment {
    @BindView(R.id.rv_list_off_the_stocks)
    RecyclerView rvListOffTheStocks;
    private Unbinder unbinder;
    private int userId;
    private String sessionId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_off_the_stocks, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HttpUtils
                .getInstance()
                .getApiService()
                .findOrderListByStatus(userId, sessionId, 9, 1, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        offTheStocks(responseBody, userId, sessionId);
                    }
                });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //2
        SharedPreferences user = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        userId = user.getInt("userId", 4870);
        sessionId = user.getString("sessionId", "");
        HttpUtils
                .getInstance()
                .getApiService()
                .findOrderListByStatus(userId, sessionId, 9, 1, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        offTheStocks(responseBody, userId, sessionId);
                    }
                });
    }

    private void offTheStocks(ResponseBody responseBody, final int userId, final String sessionId) {
        try {
            String responseBodyString = responseBody.string().toString();
            Gson gson = new Gson();
            OrderStatusBean orderStatusBean = gson.fromJson(responseBodyString, OrderStatusBean.class);
            final List<OrderStatusBean.OrderListBean> orderList = orderStatusBean.getOrderList();
            rvListOffTheStocks.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            OrderListOffTheStocksAdapter orderListAdapter = new OrderListOffTheStocksAdapter(R.layout.layout_item_orderlist_off_the_stocks, orderList);
            rvListOffTheStocks.setAdapter(orderListAdapter);
            orderListAdapter.setOnClickMoreListener(new OrderListOffTheStocksAdapter.OnClickMoreListener() {
                @Override
                public void status(int layoutPosition) {
                    String orderId = orderList.get(layoutPosition).getOrderId();
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
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
