package com.baway.dimensionsofelectricity.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.ui.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.16
 *@Time: 14:19:26
 *@Description:
 * */public class ListFragment extends Fragment {

    @BindView(R.id.rb_all_orders)
    RadioButton rbAllOrders;
    @BindView(R.id.rb_payment)
    RadioButton rbPayment;
    @BindView(R.id.rb_wait_for_receiving)
    RadioButton rbWaitForReceiving;
    @BindView(R.id.rb_to_evaluate)
    RadioButton rbToEvaluate;
    @BindView(R.id.rb_off_the_stocks)
    RadioButton rbOffTheStocks;
    @BindView(R.id.viewPager_list)
    ViewPager viewPagerList;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fragments.add(new ListAllOrdersFragment());
        fragments.add(new ListPaymentFragment());
        fragments.add(new ListWaitForReceivingFragment());
        fragments.add(new ListToEvaluateFragment());
        fragments.add(new ListOffTheStocksFragment());


        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getFragmentManager(), fragments);

        viewPagerList.setAdapter(pagerAdapter);
        viewPagerList.setCurrentItem(0);
    }


    @OnClick({R.id.rb_all_orders, R.id.rb_payment, R.id.rb_wait_for_receiving, R.id.rb_to_evaluate, R.id.rb_off_the_stocks})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //全部订单 0
            case R.id.rb_all_orders:
                //status = 0;
                viewPagerList.setCurrentItem(0);
                // presenter.requestListData(userId, sessionId, status, page, count);
                break;
            //待付款 1
            case R.id.rb_payment:
                //status = 1;
                viewPagerList.setCurrentItem(1);
                // presenter.requestListData(userId, sessionId, status, page, count);
                break;
            //待收货 2
            case R.id.rb_wait_for_receiving:
                //status = 2;
                viewPagerList.setCurrentItem(2);
                ///presenter.requestListData(userId, sessionId, status, page, count);
                break;
            //待评价 3
            case R.id.rb_to_evaluate:
                // status = 3;
                viewPagerList.setCurrentItem(3);
                // presenter.requestListData(userId, sessionId, status, page, count);
                break;
            //已完成 9
            case R.id.rb_off_the_stocks:
                //status = 9;
                viewPagerList.setCurrentItem(4);
                // presenter.requestListData(userId, sessionId, status, page, count);
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
