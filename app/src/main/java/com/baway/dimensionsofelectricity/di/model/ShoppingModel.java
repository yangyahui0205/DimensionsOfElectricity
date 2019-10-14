package com.baway.dimensionsofelectricity.di.model;

import com.baway.dimensionsofelectricity.data.net.ApiService;
import com.baway.dimensionsofelectricity.data.net.HttpUtils;
import com.baway.dimensionsofelectricity.di.contract.IShoppingContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.12
 *@Time: 08:57:05
 *@Description:
 * */public class ShoppingModel implements IShoppingContract.IShoppingModel {
    @Override
    public void containShoppingData(int userId, String sessionId, final CallBack back) {

        ApiService apiService = HttpUtils.getInstance().getApiService();
        apiService
                .findShoppingCart(userId, sessionId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String responseBodyString = responseBody.string();
                        back.getShoppingResult(responseBodyString);
                    }
                });
    }

    ;

}
