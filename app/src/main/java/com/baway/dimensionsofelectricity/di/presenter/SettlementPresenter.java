package com.baway.dimensionsofelectricity.di.presenter;

import com.baway.dimensionsofelectricity.di.contract.ISettlementContract;
import com.baway.dimensionsofelectricity.di.contract.IShoppingContract;
import com.baway.dimensionsofelectricity.di.model.SettlementModel;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.18
 *@Time: 08:49:11
 *@Description:
 * */public class SettlementPresenter<V extends ISettlementContract.ISettlementView> extends BasePresenter<V> {

    private final SettlementModel model;

    public SettlementPresenter() {
        model = new SettlementModel();
    }

    public void requestCreateOrderData(int userId, String orderId, String orderInfo, double totalPrice, int addressId) {
        model.containCreateOrderData(userId, orderId, orderInfo, totalPrice, addressId, new ISettlementContract.ISettlementModel.CallBack() {
            @Override
            public void getCreateOrderResult(String message) {
                getView().showCreateOrderData(message);
            }
        });
    }

    public void requestShoppingData(int userId, String sessionId) {
        model.containShoppingData(userId, sessionId, new ISettlementContract.ISettlementModel.ShoppingCartCallBack() {
            @Override
            public void getShoppingCartResult(String message) {
                getView().showShoppingData(message);
            }
        });
    }

    public void requestAddressList(int userId, String sessionId) {
        model.containAddressList(userId, sessionId, new ISettlementContract.ISettlementModel.AddressListCallBack() {
            @Override
            public void getAddressListResult(String message) {
                getView().showAddressListData(message);
            }
        });
    }
}
