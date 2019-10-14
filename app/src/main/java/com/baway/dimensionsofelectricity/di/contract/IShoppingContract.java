package com.baway.dimensionsofelectricity.di.contract;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.8
 *@Time: 19:24:21
 *@Description:
 * */public interface IShoppingContract {
    public interface IShoppingView {
        public void showShoppingData(String message);

    }

    public interface IShoppingModel {
        public void containShoppingData(int userId, String sessionId, CallBack back);

        public interface CallBack {
            public void getShoppingResult(String message);
        }
    }
}
