package com.baway.dimensionsofelectricity.di.model;

import com.baway.dimensionsofelectricity.data.net.ApiService;
import com.baway.dimensionsofelectricity.data.net.HttpUtils;
import com.baway.dimensionsofelectricity.di.contract.ILoginContract;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/*
 *@Auther:杨雅慧
 *@Date: 19.6.21
 *@Time: 17:23:58
 *@Description:
 * */public class LoginModel implements ILoginContract.ILoginModel {

    @Override
    public void containLoginData(String phone, String pwd, final CallBack back) {
        ApiService apiService = HttpUtils.getInstance().getApiService();
        apiService.userLogin(phone, pwd)
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
                            back.getLoginResult(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String message = e.getMessage();
                        back.getLoginResult(message);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
