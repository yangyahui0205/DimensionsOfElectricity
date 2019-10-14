package com.baway.dimensionsofelectricity.di.presenter;

import com.baway.dimensionsofelectricity.di.contract.IRegisterContract;
import com.baway.dimensionsofelectricity.di.model.RegisterModel;

/*
 *@Auther:杨雅慧
 *@Date: 19.6.21
 *@Time: 19:47:14
 *@Description:
 * */public class RegisterPresenter<V extends IRegisterContract.IRegisterView> extends BasePresenter<V> {

    private final RegisterModel model;

    public RegisterPresenter() {
        //获取M层对象
        model = new RegisterModel();
    }

    public void requestRegisterData(String phone, String pwd) {
        model.containRegisterData(phone, pwd, new IRegisterContract.IRegisterModel.CallBack() {
            @Override
            public void getRegisterResult(String message) {
                getView().showRegisterData(message);
            }
        });
    }
}
