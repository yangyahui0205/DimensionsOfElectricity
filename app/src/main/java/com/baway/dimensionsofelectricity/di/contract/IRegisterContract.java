package com.baway.dimensionsofelectricity.di.contract;


/*
 *@Auther:杨雅慧
 *@Date: 19.6.21
 *@Time: 17:19:14
 *@Description:
 * */public interface IRegisterContract {
    /**
     * V层
     */
    public interface IRegisterView {
        public void showRegisterData(String message);
    }


    /**
     * M层
     */
    public interface IRegisterModel {
        public void containRegisterData(String phone, String pwd, CallBack back);

        public interface CallBack {
            public void getRegisterResult(String message);
        }
    }
}
