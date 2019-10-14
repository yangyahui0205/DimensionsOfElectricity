package com.baway.dimensionsofelectricity.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.baway.dimensionsofelectricity.data.Constant;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.11
 *@Time: 09:16:29
 *@Description:
 * */public class HttpUtils {
    private static final HttpUtils ourInstance = new HttpUtils();

    public static HttpUtils getInstance() {
        return ourInstance;
    }

    private HttpUtils() {
    }

    public ApiService getApiService() {

        Retrofit retrofit = new Retrofit
                .Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        return apiService;
    }

    public boolean getConnectedState(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo managerActiveNetworkInfo = manager.getActiveNetworkInfo();
            if (managerActiveNetworkInfo != null) {
                return managerActiveNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
