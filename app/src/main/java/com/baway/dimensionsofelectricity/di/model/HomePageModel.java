package com.baway.dimensionsofelectricity.di.model;

import com.baway.dimensionsofelectricity.data.net.HttpUtils;
import com.baway.dimensionsofelectricity.di.contract.IHomePageContract;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.16
 *@Time: 20:05:37
 *@Description:
 * */public class HomePageModel implements IHomePageContract.IHomePageModel {

    @Override
    public void containHomePageXBannerData(final XBannerCallBack back) {
        HttpUtils
                .getInstance()
                .getApiService()
                .getHomePageData("bannerShow")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String message = responseBody.string().toString();
                            back.getHomePageXBannerResult(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String message = e.getMessage();
                        back.getHomePageXBannerResult(message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void containHomePageData(final CallBack back) {
        HttpUtils
                .getInstance()
                .getApiService()
                .getHomePageData("commodityList")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String message = responseBody.string().toString();
                            back.getHomePageResult(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String message = e.getMessage();
                        back.getHomePageResult(message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
