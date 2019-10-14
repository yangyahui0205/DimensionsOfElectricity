package com.baway.dimensionsofelectricity.di.presenter;

import com.baway.dimensionsofelectricity.di.contract.ICircleContract;
import com.baway.dimensionsofelectricity.di.model.CircleModel;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.17
 *@Time: 23:30:10
 *@Description:
 * */public class CirclePresenter<V extends ICircleContract.ICircleView> extends BasePresenter<ICircleContract.ICircleView> {

    private final CircleModel model;

    public CirclePresenter() {
        model = new CircleModel();
    }

    public void requestCircleData(int userId, String sessionId, int page) {
        model.containCircleData(userId,sessionId,page,new ICircleContract.ICircleModel.CallBack() {
            @Override
            public void getCircleResult(String message) {
                getView().showCircleData(message);
            }
        });
    }
}
