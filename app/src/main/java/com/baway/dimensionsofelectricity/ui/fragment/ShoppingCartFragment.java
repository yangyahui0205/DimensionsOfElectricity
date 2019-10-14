package com.baway.dimensionsofelectricity.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.CartBean;
import com.baway.dimensionsofelectricity.data.beans.MessageEvent;
import com.baway.dimensionsofelectricity.di.contract.IShoppingContract;
import com.baway.dimensionsofelectricity.di.presenter.ShoppingPresenter;
import com.baway.dimensionsofelectricity.ui.activity.SettlementActivity;
import com.baway.dimensionsofelectricity.ui.adapter.ShoppingCartShopsAdapter;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.16
 *@Time: 14:21:36
 *@Description:
 * */public class ShoppingCartFragment extends BaseFragment<IShoppingContract.IShoppingView, ShoppingPresenter<IShoppingContract.IShoppingView>> implements IShoppingContract.IShoppingView {


    @BindView(R.id.cb_checkAll)
    CheckBox cbCheckAll;
    @BindView(R.id.tv_totalPrice)
    TextView tvTotalPrice;
    @BindView(R.id.btn_settlement)
    Button btnSettlement;
    @BindView(R.id.rv_shops)
    RecyclerView rvShops;
    private List<CartBean.ResultBean> resultBean;
    private ShoppingCartShopsAdapter shopsAdapter;
    private int userId;
    private String sessionId;

    @Override
    public void onResume() {
        super.onResume();
        presenter.requestShoppingData(userId, sessionId);
    }

    @Override
    public void showShoppingData(String message) {
        Gson gson = new Gson();
        CartBean cartBean = gson.fromJson(message, CartBean.class);
        rvShops.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        resultBean = cartBean.getResult();
        shopsAdapter = new ShoppingCartShopsAdapter(R.layout.layout_item_shops, resultBean);
        rvShops.setAdapter(shopsAdapter);
        //商家状态
        shopsAdapter.setOnClickShopsListener(new ShoppingCartShopsAdapter.OnClickShopsListener() {
            @Override
            public void shopsChange() {
                boolean state = true;
                for (int i = 0; i < resultBean.size(); i++) {
                    boolean shopsSelect = resultBean.get(i).getShopsSelect();
                    for (int j = 0; j < resultBean.get(i).getShoppingCartList().size(); j++) {
                        boolean goodsSelect = resultBean.get(i).getShoppingCartList().get(j).getGoodsSelect();
                        state = state & shopsSelect & goodsSelect;
                    }
                }
                cbCheckAll.setChecked(state);
                //刷新适配器
                shopsAdapter.notifyDataSetChanged();
                //刷新购物车底部栏
                refreshTheBottomBarOfTheCart();
            }
        });
    }

    private void refreshTheBottomBarOfTheCart() {
        double totalPrice = 0;
        //计算总价钱
        for (int i = 0; i < resultBean.size(); i++) {
            List<CartBean.ResultBean.ShoppingCartListBean> shoppingCartList = resultBean.get(i).getShoppingCartList();
            for (int j = 0; j < shoppingCartList.size(); j++) {
                if (shoppingCartList.get(j).getGoodsSelect()) {
                    int count = shoppingCartList.get(j).getCount();
                    totalPrice += count * shoppingCartList.get(j).getPrice();
                }
            }
        }
        tvTotalPrice.setText(totalPrice + "");
    }


    @Override
    protected ShoppingPresenter<IShoppingContract.IShoppingView> createPresenter() {
        return new ShoppingPresenter<>();
    }

    @Override
    protected void initData() {
        SharedPreferences user = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        userId = user.getInt("userId", 4870);
        sessionId = user.getString("sessionId", "");
        presenter.requestShoppingData(userId, sessionId);
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_shoppingcart;
    }


    @OnClick({R.id.cb_checkAll, R.id.btn_settlement})
    public void onViewClicked(View view) {
        boolean checkAll = cbCheckAll.isChecked();
        switch (view.getId()) {
            case R.id.cb_checkAll:
                //点击全选设置商家商品状态都为true
                for (int i = 0; i < resultBean.size(); i++) {
                    resultBean.get(i).setShopsSelect(checkAll);
                    for (int j = 0; j < resultBean.get(i).getShoppingCartList().size(); j++) {
                        resultBean.get(i).getShoppingCartList().get(j).setGoodsSelect(checkAll);
                    }
                }
                ;
                shopsAdapter.notifyDataSetChanged();
                refreshTheBottomBarOfTheCart();
                break;
            case R.id.btn_settlement:
               /* Gson gson = new Gson();
                String orderInfo = "";
                int totalCount = 0;
                double totalPrice = 0;
                //获取选中的商品 id count 以及总价钱
                for (int i = 0; i < resultBean.size(); i++) {
                    resultBean.get(i).setShopsSelect(checkAll);
                    for (int j = 0; j < resultBean.get(i).getShoppingCartList().size(); j++) {
                        if (resultBean.get(i).getShoppingCartList().get(j).getGoodsSelect()) {
                            int commodityId = resultBean.get(i).getShoppingCartList().get(j).getCommodityId();
                            int count = resultBean.get(i).getShoppingCartList().get(j).getCount();
                            totalPrice += count * resultBean.get(i).getShoppingCartList().get(j).getPrice();
                            totalCount += count;
                            // //定义Json格式的结构
                            HashMap<String, Integer> paramsMap = new HashMap<>();
                            paramsMap.put("commodityId", commodityId);
                            paramsMap.put("amount", count);
                            String json = gson.toJson(paramsMap);
                            orderInfo += json;
                        }

                    }
                }*/
                ;
//                Log.d("ShoppingCartFragment", "s:[" + orderInfo + "]");
               /* // int addressId = 0;
                presenter.createOrder(userId, sessionId, orderInfo, totalPrice, addressId);*/

//                MessageEvent event = new MessageEvent();
//                event.setOrderInfo(orderInfo);
//                event.setTotalPrice(totalPrice);
//                event.setTotalCount(totalCount);
//                EventBus.getDefault().postSticky(event);
                Intent intent = new Intent(getContext(), SettlementActivity.class);
                intent.putExtra("check", cbCheckAll.isChecked());
                startActivity(intent);
                break;
        }
    }
}
