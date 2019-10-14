package com.baway.dimensionsofelectricity.di.model;

import com.baway.dimensionsofelectricity.data.net.HttpUtils;
import com.baway.dimensionsofelectricity.di.contract.IMineContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.18
 *@Time: 15:32:37
 *@Description:
 * */public class MineModel implements IMineContract.IMineModel {
    @Override
    public void containMineData(int userId, String sessionId, final CallBack back) {
        HttpUtils
                .getInstance()
                .getApiService()
                .getUserById(userId,sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String responseBodyString = responseBody.string();
                        back.getMineResult(responseBodyString);
                    }
                });
    }
}
