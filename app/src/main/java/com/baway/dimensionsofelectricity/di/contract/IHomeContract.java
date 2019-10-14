package com.baway.dimensionsofelectricity.di.contract;


/*
 *@Auther:杨雅慧
 *@Date: 19.6.21
 *@Time: 17:19:14
 *@Description:
 * */public interface IHomeContract {
    /**
     * V层
     */
    public interface IHomeView {
        public void showHomeData();
    }

    /**
     * M层
     */
    public interface IHomeModel {
        public void containHomeData(CallBack back);

        public interface CallBack {
            public void getHomeResult();
        }
    }
}
