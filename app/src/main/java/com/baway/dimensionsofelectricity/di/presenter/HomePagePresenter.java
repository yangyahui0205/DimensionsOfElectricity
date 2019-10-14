package com.baway.dimensionsofelectricity.di.presenter;

import com.baway.dimensionsofelectricity.di.contract.IHomePageContract;
import com.baway.dimensionsofelectricity.di.model.HomePageModel;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.16
 *@Time: 20:06:24
 *@Description:
 * */public class HomePagePresenter<V extends IHomePageContract.IHomePageView> extends BasePresenter<V> {

    private final HomePageModel model;

    public HomePagePresenter() {
        model = new HomePageModel();
    }

    public void requestHomePageXBannerData() {
        model.containHomePageXBannerData(new IHomePageContract.IHomePageModel.XBannerCallBack() {
            @Override
            public void getHomePageXBannerResult(String message) {
                getView().showHomePageXBannerData(message);
            }
        });
    }

    public void requestHomePageData() {
        model.containHomePageData(new IHomePageContract.IHomePageModel.CallBack() {
            @Override
            public void getHomePageResult(String message) {
                getView().showHomePageData(message);
            }
        });
    }
}
