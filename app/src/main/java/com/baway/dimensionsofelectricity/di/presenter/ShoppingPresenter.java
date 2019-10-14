package com.baway.dimensionsofelectricity.di.presenter;

import com.baway.dimensionsofelectricity.di.contract.IShoppingContract;
import com.baway.dimensionsofelectricity.di.model.ShoppingModel;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.12
 *@Time: 08:57:23
 *@Description:
 * */public class ShoppingPresenter<V extends IShoppingContract.IShoppingView> extends BasePresenter<V> {

    private final ShoppingModel model;

    public ShoppingPresenter() {
        model = new ShoppingModel();
    }

    public void requestShoppingData(int userId, String sessionId) {
        model.containShoppingData(userId, sessionId, new IShoppingContract.IShoppingModel.CallBack() {
            @Override
            public void getShoppingResult(String message) {
                getView().showShoppingData(message);
            }
        });
    }
}
