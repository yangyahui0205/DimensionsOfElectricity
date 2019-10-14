package com.baway.dimensionsofelectricity.di.model;

import com.baway.dimensionsofelectricity.data.net.HttpUtils;
import com.baway.dimensionsofelectricity.di.contract.IAddHarvestAddressContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.20
 *@Time: 10:08:02
 *@Description:
 * */public class AddHarvestAddressModel implements IAddHarvestAddressContract.IAddHarvestAddressModel {
    @Override
    public void containAddHarvestAddressData(int userId, String sessionId, String realName, String phone, String address, String zipCode, final CallBack back) {
        HttpUtils
                .getInstance()
                .getApiService()
                .addReceiveAddress(userId, sessionId, realName, phone, address, zipCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String responseBodyString = responseBody.string();
                        back.getAddHarvestAddressResult(responseBodyString);
                    }
                });

    }
}
