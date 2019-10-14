package com.baway.dimensionsofelectricity.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baway.dimensionsofelectricity.R;
import com.baway.dimensionsofelectricity.data.beans.AddressListBean;
import com.baway.dimensionsofelectricity.data.beans.CartBean;
import com.baway.dimensionsofelectricity.di.contract.ISettlementContract;
import com.baway.dimensionsofelectricity.di.presenter.SettlementPresenter;
import com.baway.dimensionsofelectricity.ui.adapter.AddressListPopAdapter;
import com.baway.dimensionsofelectricity.ui.adapter.ShoppingCartShopsAdapter;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SettlementActivity extends BaseActivity<ISettlementContract.ISettlementView, SettlementPresenter<ISettlementContract.ISettlementView>> implements ISettlementContract.ISettlementView {


    @BindView(R.id.btn_settlement_add_address)
    TextView btnSettlementAddAddress;
    @BindView(R.id.tv_settlement_address_name)
    TextView tvSettlementAddressName;
    @BindView(R.id.tv_settlement_address_phone)
    TextView tvSettlementAddressPhone;
    @BindView(R.id.tv_settlement_address_detail)
    TextView tvSettlementAddressDetail;
    @BindView(R.id.tv_settlement_address_down)
    ImageView tvSettlementAddressDown;
    @BindView(R.id.ll_settlement_address_bar)
    LinearLayout llSettlementAddressBar;
    @BindView(R.id.rv_settlement)
    RecyclerView rvSettlement;
    @BindView(R.id.tv_settlement_totalCount)
    TextView tvSettlementTotalCount;
    @BindView(R.id.tv_settlement_totalPrice)
    TextView tvSettlementTotalPrice;
    @BindView(R.id.btn_submit_order)
    Button btnSubmitOrder;
    private int userId;
    private String sessionId;
    private List<CartBean.ResultBean> resultBean;
    private ShoppingCartShopsAdapter shopsAdapter;
    private PopupWindow mPopupWindow;
    private boolean popCheck = false;
    int addressId = 0;

    @Override
    public void showCreateOrderData(String message) {
        Gson gson = new Gson();
        CreateOrderBean createOrderBean = gson.fromJson(message, CreateOrderBean.class);
        Toast.makeText(this, "createOrderBean:" + createOrderBean.getMessage(), Toast.LENGTH_SHORT).show();
        ;
        finish();
    }

    @Override
    public void showShoppingData(String message) {
        Gson gson = new Gson();
        CartBean cartBean = gson.fromJson(message, CartBean.class);
        rvSettlement.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        resultBean = cartBean.getResult();
        shopsAdapter = new ShoppingCartShopsAdapter(R.layout.layout_item_shops, resultBean);
        rvSettlement.setAdapter(shopsAdapter);
        //商家状态
        //遍历商品全为选中
        for (int i = 0; i < resultBean.size(); i++) {
            resultBean.get(i).setShopsSelect(true);
            List<CartBean.ResultBean.ShoppingCartListBean> shoppingCartList = resultBean.get(i).getShoppingCartList();
            for (int j = 0; j < shoppingCartList.size(); j++) {
                shoppingCartList.get(j).setGoodsSelect(true);
            }
        }
        refreshTheBottomBarOfTheCart();
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
                //刷新适配器
                shopsAdapter.notifyDataSetChanged();
                //刷新购物车底部栏
                refreshTheBottomBarOfTheCart();
            }
        });
    }

    @Override
    public void showAddressListData(String message) {
        Gson gson = new Gson();
        AddressListBean addressBean = gson.fromJson(message, AddressListBean.class);
        List<AddressListBean.ResultBean> addressBeanResult = addressBean.getResult();
        showPopupWindow(addressBeanResult);
    }

    private void showPopupWindow(List<AddressListBean.ResultBean> addressBeanResult) {
        //获取布局
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_address_pop, null);
        //设置弹出框的位置
        mPopupWindow = new PopupWindow(contentView, RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT, true);
        //给弹出框设置布局
        // 设置PopupWindow是否能响应外部点击事件
        mPopupWindow.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        mPopupWindow.setTouchable(true);
        mPopupWindow.setContentView(contentView);
        //设置各个控件的点击事件
        RecyclerView rv_address_pop = contentView.findViewById(R.id.rv_address_pop);
        rv_address_pop.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        AddressListPopAdapter addressListPopAdapter = new AddressListPopAdapter(R.layout.layout_item_address_pop, addressBeanResult);
        rv_address_pop.setAdapter(addressListPopAdapter);
        //在按钮下面显示r
        if (popCheck) {
            mPopupWindow.showAsDropDown(llSettlementAddressBar, 0, -24);

        } else {
            mPopupWindow.showAsDropDown(btnSettlementAddAddress, 0, -24);
        }


        addressListPopAdapter.setOnClickAddressListChooseListener(new AddressListPopAdapter.OnClickAddressListChooseListener() {
            @Override
            public void addressListDetail(int id, String address, String phone, String realName, String zipCode) {
                addressId = id;
                btnSettlementAddAddress.setVisibility(View.GONE);
                tvSettlementAddressName.setText(realName);
                tvSettlementAddressPhone.setText(phone);
                tvSettlementAddressDetail.setText(address);
                llSettlementAddressBar.setVisibility(View.VISIBLE);
                mPopupWindow.dismiss();
            }
        });
    }

    private void refreshTheBottomBarOfTheCart() {
        double totalPrice = 0;
        int totalCount = 0;
        //计算总价钱
        for (int i = 0; i < resultBean.size(); i++) {
            List<CartBean.ResultBean.ShoppingCartListBean> shoppingCartList = resultBean.get(i).getShoppingCartList();
            for (int j = 0; j < shoppingCartList.size(); j++) {
                if (shoppingCartList.get(j).getGoodsSelect()) {
                    int count = shoppingCartList.get(j).getCount();
                    totalPrice += count * shoppingCartList.get(j).getPrice();
                    totalCount += count;
                }
            }
        }
        tvSettlementTotalPrice.setText(totalPrice + "");
        tvSettlementTotalCount.setText(totalCount + "");
    }


    @Override
    protected SettlementPresenter<ISettlementContract.ISettlementView> createPresenter() {
        return new SettlementPresenter<>();
    }

    @Override
    protected void initData() {
        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        userId = user.getInt("userId", 4870);
        sessionId = user.getString("sessionId", "");
        Intent intent = getIntent();
        boolean check = intent.getBooleanExtra("check", false);
        if (check) {
            presenter.requestShoppingData(userId, sessionId);
        } else {
            Toast.makeText(this, "赶快购买商品吧", Toast.LENGTH_SHORT).show();
        }

    }
/*
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }*/

   /* @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(MessageEvent event) {
        String orderInfo = event.getOrderInfo();
        int totalCount = event.getTotalCount();
        double totalPrice = event.getTotalPrice();
        tvSettlementTotalCount.setText(totalCount + "");
        tvSettlementTotalPrice.setText(totalPrice + "");
        int addressId = 27665;
        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        userId = user.getInt("userId", 4870);
        sessionId = user.getString("sessionId", "");
        Log.d("SettlementActivity", orderInfo + "...");
        presenter.requestCreateOrderData(userId, sessionId, "[" + orderInfo + "]", totalPrice, addressId);
    }*/

    @Override
    protected int bindLayout() {
        return R.layout.activity_settlement;
    }


    @OnClick({R.id.btn_settlement_add_address, R.id.tv_settlement_address_down, R.id.btn_submit_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_settlement_address_down:
                popCheck = true;
                presenter.requestAddressList(userId, sessionId);
                break;
            case R.id.btn_settlement_add_address:
                popCheck = false;
                presenter.requestAddressList(userId, sessionId);
                break;
            case R.id.btn_submit_order:
                //提交订单
                Gson gson = new Gson();
                String orderInfo = "";
                double totalPrice = 0;
                //获取选中的商品 id count 以及总价钱
                for (int i = 0; i < resultBean.size(); i++) {
                    for (int j = 0; j < resultBean.get(i).getShoppingCartList().size(); j++) {
                        if (resultBean.get(i).getShoppingCartList().get(j).getGoodsSelect()) {
                            int commodityId = resultBean.get(i).getShoppingCartList().get(j).getCommodityId();
                            int count = resultBean.get(i).getShoppingCartList().get(j).getCount();
                            totalPrice += count * resultBean.get(i).getShoppingCartList().get(j).getPrice();
                            // //定义Json格式的结构
                            HashMap<String, Integer> paramsMap = new HashMap<>();
                            paramsMap.put("commodityId", commodityId);
                            paramsMap.put("amount", count);
                            String json = gson.toJson(paramsMap);
                            orderInfo += json;
                        }

                    }
                }
                ;
                Log.d("SettlementActivity", "s:[" + orderInfo + "]");
                Log.d("SettlementActivity", "totalPrice:" + totalPrice);


                presenter.requestCreateOrderData(userId, sessionId, "[" + orderInfo + "]", totalPrice, addressId);

                break;
        }
    }


    class CreateOrderBean {

        /**
         * orderId : 201907180905438204870
         * message : 创建订单成功
         * status : 0000
         */

        private String orderId;
        private String message;
        private String status;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

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
