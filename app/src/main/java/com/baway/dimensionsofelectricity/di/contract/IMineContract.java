package com.baway.dimensionsofelectricity.di.contract;


/*
 *@Auther:杨雅慧
 *@Date: 19.6.21
 *@Time: 17:19:14
 *@Description:
 * */public interface IMineContract {
    /**
     * V层
     */
    public interface IMineView {
        public void showMineData(String message);
    }

    /**
     * M层
     */
    public interface IMineModel {
        public void containMineData(int userId, String sessionId, CallBack back);

        public interface CallBack {
            public void getMineResult(String message);
        }
    }
}
