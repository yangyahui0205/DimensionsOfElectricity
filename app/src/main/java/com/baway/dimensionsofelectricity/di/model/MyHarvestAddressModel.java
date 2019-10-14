package com.baway.dimensionsofelectricity.di.model;

import com.baway.dimensionsofelectricity.data.net.HttpUtils;
import com.baway.dimensionsofelectricity.di.contract.IMyharvestAddressContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.20
 *@Time: 08:36:03
 *@Description:
 * */public class MyHarvestAddressModel implements IMyharvestAddressContract.IMyharvestAddressModel {
    @Override
    public void containMyharvestAddressData(int userId, String sessionId, final CallBack back) {

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
                        back.getIMyharvestAddressResult(responseBodyString);
                    }
                });
    }

    @Override
    public void containDefaultReceiveAddressData(int userId, String sessionId, int harvestAddressId, final DefaultReceiveAddressCallBack back) {
        HttpUtils
                .getInstance()
                .getApiService()
                .setDefaultReceiveAddress(userId, sessionId,harvestAddressId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String responseBodyString = responseBody.string();
                        back.getDefaultReceiveAddressResult(responseBodyString);
                    }
                });
    }
}
