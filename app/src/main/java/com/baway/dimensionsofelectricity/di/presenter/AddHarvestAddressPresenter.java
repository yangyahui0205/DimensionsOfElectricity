package com.baway.dimensionsofelectricity.di.presenter;

import com.baway.dimensionsofelectricity.di.contract.IAddHarvestAddressContract;
import com.baway.dimensionsofelectricity.di.model.AddHarvestAddressModel;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.20
 *@Time: 10:08:24
 *@Description:
 * */public class AddHarvestAddressPresenter<V extends IAddHarvestAddressContract.IAddHarvestAddressView> extends BasePresenter<V> {

    private final AddHarvestAddressModel model;

    public AddHarvestAddressPresenter() {
        model = new AddHarvestAddressModel();
    }

    public void requestAddHarvestAddressData(int userId, String sessionId, String realName, String phone, String address, String zipCode) {
        model.containAddHarvestAddressData(userId, sessionId, realName, phone, address, zipCode, new IAddHarvestAddressContract.IAddHarvestAddressModel.CallBack() {
            @Override
            public void getAddHarvestAddressResult(String message) {
                getView().showMyHarvestAddressData(message);
            }
        });
    }
}
