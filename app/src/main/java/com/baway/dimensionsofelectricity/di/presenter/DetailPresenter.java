package com.baway.dimensionsofelectricity.di.presenter;

import com.baway.dimensionsofelectricity.di.contract.IDetailContract;
import com.baway.dimensionsofelectricity.di.model.DetailModel;

import okhttp3.FormBody;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.17
 *@Time: 21:52:12
 *@Description:
 * */public class DetailPresenter<V extends IDetailContract.IDetailView> extends BasePresenter<IDetailContract.IDetailView> {

    private final DetailModel model;

    public DetailPresenter() {
        model = new DetailModel();
    }

    public void requestDetailData(int userId, String sessionId, int commodityId) {
        model.containDetailData(userId, sessionId, commodityId, new IDetailContract.IDetailModel.CallBack() {
            @Override
            public void getDetailResult(String s) {
                getView().showDetailData(s);
            }
        });
    }

    public void syncShoppingCart(int userId, String sessionId, FormBody formBody) {
        model.containSyncShoppingCartData(userId, sessionId, formBody, new IDetailContract.IDetailModel.SyncShoppingCartCallBack() {
            @Override
            public void getSyncShoppingCartResult(String message) {
                getView().showSyncShoppingCartResult(message);
            }
        });
    }

    ;
}
