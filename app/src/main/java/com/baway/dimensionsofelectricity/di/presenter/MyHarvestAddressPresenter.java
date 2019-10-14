package com.baway.dimensionsofelectricity.di.presenter;

import com.baway.dimensionsofelectricity.di.contract.IMyharvestAddressContract;
import com.baway.dimensionsofelectricity.di.model.MyHarvestAddressModel;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.20
 *@Time: 08:37:06
 *@Description:
 * */public class MyHarvestAddressPresenter<V extends IMyharvestAddressContract.IMyharvestAddressView> extends BasePresenter<V> {

    private final MyHarvestAddressModel model;

    public MyHarvestAddressPresenter() {
        model = new MyHarvestAddressModel();
    }

    public void requestMyHarvestAddressData(int userId, String sessionId) {
        model.containMyharvestAddressData(userId, sessionId, new IMyharvestAddressContract.IMyharvestAddressModel.CallBack() {
            @Override
            public void getIMyharvestAddressResult(String message) {
                getView().showMyHarvestAddressData(message);
            }
        });
    }

    public void requestDefaultReceiveAddressData(int userId, String sessionId, int id) {
        model.containDefaultReceiveAddressData(userId, sessionId, id, new IMyharvestAddressContract.IMyharvestAddressModel.DefaultReceiveAddressCallBack() {
            @Override
            public void getDefaultReceiveAddressResult(String message) {
                getView().showDefaultReceiveAddressData(message);
            }
        });
    }
}
