package com.baway.dimensionsofelectricity.di.model;

import com.baway.dimensionsofelectricity.data.net.HttpUtils;
import com.baway.dimensionsofelectricity.di.contract.ISettlementContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.18
 *@Time: 08:48:42
 *@Description:
 * */public class SettlementModel implements ISettlementContract.ISettlementModel {
    @Override
    public void containCreateOrderData(int userId, String sessionId, String orderInfo, double totalPrice, int addressId, final CallBack back) {
        HttpUtils
                .getInstance()
                .getApiService()
                .createOrder(userId, sessionId, orderInfo, totalPrice, addressId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String responseBodyString = responseBody.string();
                        back.getCreateOrderResult(responseBodyString);
                    }
                });
    }

    @Override
    public void containShoppingData(int userId, String sessionId, final ShoppingCartCallBack back) {
        HttpUtils
                .getInstance()
                .getApiService()
                .findShoppingCart(userId, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String responseBodyString = responseBody.string();
                        back.getShoppingCartResult(responseBodyString);
                    }
                });
    }

    @Override
    public void containAddressList(int userId, String sessionId, final AddressListCallBack back) {
        HttpUtils
                .getInstance()
                .getApiService()
                .receiveAddressList(userId, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String responseBodyString = responseBody.string();
                        back.getAddressListResult(responseBodyString);
                    }
                });
    }
}
