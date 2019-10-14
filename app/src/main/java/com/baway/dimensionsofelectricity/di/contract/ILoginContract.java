package com.baway.dimensionsofelectricity.di.contract;


/*
 *@Auther:杨雅慧
 *@Date: 19.6.21
 *@Time: 17:19:14
 *@Description:
 * */public interface ILoginContract {
    /**
     * V层
     */
    public interface ILoginView {
        public void showLoginData(String message);
    }

    /**
     * M层
     */
    public interface ILoginModel {
        public void containLoginData(String phone, String pwd, CallBack back);

        public interface CallBack {
            public void getLoginResult(String message);
        }
    }
}
