package com.baway.dimensionsofelectricity.di.presenter;

import com.baway.dimensionsofelectricity.di.contract.IMineContract;
import com.baway.dimensionsofelectricity.di.model.MineModel;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.18
 *@Time: 15:32:58
 *@Description:
 * */public class MinePresenter<V extends IMineContract.IMineView> extends BasePresenter<V> {

    private final MineModel model;

    public MinePresenter() {
        model = new MineModel();
    }

    public void requestMinePresenter(int userId, String sessionId) {
        model.containMineData(userId,sessionId,new IMineContract.IMineModel.CallBack() {
            @Override
            public void getMineResult(String message) {
                getView().showMineData(message);
            }
        });
    }
}
