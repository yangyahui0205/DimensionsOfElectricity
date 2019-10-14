package com.baway.dimensionsofelectricity.di.contract;


/*
 *@Auther:杨雅慧
 *@Date: 19.6.21
 *@Time: 17:19:14
 *@Description:
 * */public interface ICircleContract {
    /**
     * V层
     */
    public interface ICircleView {
        public void showCircleData(String message);
    }

    /**
     * M层
     */
    public interface ICircleModel {
        public void containCircleData(int userId, String sessionId, int page, CallBack back);

        public interface CallBack {
            public void getCircleResult(String message);
        }
    }
}
