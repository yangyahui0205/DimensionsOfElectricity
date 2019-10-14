package com.baway.dimensionsofelectricity.di.presenter;

import com.baway.dimensionsofelectricity.di.contract.ILoginContract;
import com.baway.dimensionsofelectricity.di.model.LoginModel;

/*
 *@Auther:杨雅慧
 *@Date: 19.6.21
 *@Time: 17:27:31
 *@Description:
 * */public class LoginPresenter<V extends ILoginContract.ILoginView> extends BasePresenter<V> {

    private final LoginModel model;

    public LoginPresenter() {
        //获取M层对象
        model = new LoginModel();
    }

    public void requestLoginData(String phone, String pwd) {
        //请求数据
       model.containLoginData(phone, pwd, new ILoginContract.ILoginModel.CallBack() {
           @Override
           public void getLoginResult(String message) {
               getView().showLoginData(message);
           }
       });
    }
}
