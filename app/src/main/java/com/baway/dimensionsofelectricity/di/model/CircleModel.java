package com.baway.dimensionsofelectricity.di.model;

import com.baway.dimensionsofelectricity.data.net.HttpUtils;
import com.baway.dimensionsofelectricity.di.contract.ICircleContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.17
 *@Time: 23:28:40
 *@Description:
 * */public class CircleModel implements ICircleContract.ICircleModel {
    @Override
    public void containCircleData(int userId, String sessionId, int page, final CallBack back) {
        HttpUtils
                .getInstance()
                .getApiService()
                .findCircleList(userId, sessionId, page, 5)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        back.getCircleResult(responseBody.string().toString());
                    }
                });
    }
}
