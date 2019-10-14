package com.baway.dimensionsofelectricity.di.model;

import com.baway.dimensionsofelectricity.data.net.HttpUtils;
import com.baway.dimensionsofelectricity.di.contract.IDetailContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.ResponseBody;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.17
 *@Time: 21:51:49
 *@Description:
 * */public class DetailModel implements IDetailContract.IDetailModel {
    @Override
    public void containDetailData(int userId, String sessionId, int commodityId, final CallBack back) {
        HttpUtils
                .getInstance()
                .getApiService()
                .findCommodityDetailsById(userId, sessionId, commodityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        back.getDetailResult(responseBody.string().toString());
                    }
                });
    }

    @Override
    public void containSyncShoppingCartData(int userId, String sessionId, FormBody formBody, final SyncShoppingCartCallBack back) {
        HttpUtils
                .getInstance()
                .getApiService()
                .syncShoppingCart(userId, sessionId, formBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        back.getSyncShoppingCartResult(responseBody.string().toString());
                    }
                });
    }
}
