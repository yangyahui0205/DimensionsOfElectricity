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

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.OrderStatusBean;
import com.baway.dimensionsofelectricity.data.net.HttpUtils;
import com.baway.dimensionsofelectricity.ui.activity.EvaluateActivity;
import com.baway.dimensionsofelectricity.ui.adapter.OrderListPaymentAdapter;
import com.baway.dimensionsofelectricity.ui.adapter.OrderListToEvaluateAdapter;
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
 * */public class ListToEvaluateFragment extends Fragment {


    @BindView(R.id.rv_list_to_evaluate)
    RecyclerView rvListToEvaluate;
    private Unbinder unbinder;
    private int userId;
    private String sessionId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_to_evaluate, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HttpUtils
                .getInstance()
                .getApiService()
                .findOrderListByStatus(userId, sessionId, 3, 1, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        toEvaluate(responseBody);
                    }
                });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //3
        SharedPreferences user = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        userId = user.getInt("userId", 4870);
        sessionId = user.getString("sessionId", "");
        HttpUtils
                .getInstance()
                .getApiService()
                .findOrderListByStatus(userId, sessionId, 3, 1, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        toEvaluate(responseBody);
                    }
                });
    }

    private void toEvaluate(ResponseBody responseBody) throws IOException {
        String responseBodyString = responseBody.string().toString();
        Gson gson = new Gson();
        OrderStatusBean orderStatusBean = gson.fromJson(responseBodyString, OrderStatusBean.class);
        List<OrderStatusBean.OrderListBean> orderList = orderStatusBean.getOrderList();
        rvListToEvaluate.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        OrderListToEvaluateAdapter orderListAdapter = new OrderListToEvaluateAdapter(R.layout.layout_item_orderlist_to_evaluate, orderList);
        rvListToEvaluate.setAdapter(orderListAdapter);
        orderListAdapter.setOnClickEvaluateListener(new OrderListToEvaluateAdapter.OnClickEvaluateListener() {
            @Override
            public void status(int commodityId, String orderId, String commodityName, String commodityPic, int commodityPrice) {
                Intent intent = new Intent(getContext(), EvaluateActivity.class);
                intent.putExtra("commodityName", commodityName);
                intent.putExtra("commodityPic", commodityPic);
                intent.putExtra("commodityPrice", commodityPrice);
                intent.putExtra("commodityId", commodityId);
                intent.putExtra("orderId", orderId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
