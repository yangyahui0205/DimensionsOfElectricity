package com.baway.dimensionsofelectricity.di.model;


import android.util.Log;

import com.baway.dimensionsofelectricity.data.net.HttpUtils;
import com.baway.dimensionsofelectricity.di.contract.ISearchListContract;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/*
 *@Auther:杨雅慧
 *@Date: 19.6.27
 *@Time: 19:39:43
 *@Description:
 * */public class SearchListModel implements ISearchListContract.ISearchListModel {

    @Override
    public void containSearchListData(String content, int page, final CallBack back) {
        HttpUtils
                .getInstance()
                .getApiService()
                .findCommodityByKeyword(content, 5, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String message = responseBody.string();
                            back.getSearchListResult(message);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String message = e.getMessage();
                        back.getSearchListResult(message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
