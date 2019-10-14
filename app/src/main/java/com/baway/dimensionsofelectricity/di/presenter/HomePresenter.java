package com.baway.dimensionsofelectricity.di.presenter;

import com.baway.dimensionsofelectricity.di.contract.IHomeContract;
import com.baway.dimensionsofelectricity.di.model.HomeModel;

/*
 *@Auther:杨雅慧
 *@Date: 19.6.21
 *@Time: 19:58:32
 *@Description:
 * */public class HomePresenter<V extends IHomeContract.IHomeView> extends BasePresenter<V> {

    private final HomeModel model;

    public HomePresenter() {
        model = new HomeModel();
    }

    public void requestHomeData() {
        model.containHomeData(new IHomeContract.IHomeModel.CallBack() {
            @Override
            public void getHomeResult() {

            }
        });
    }
}
