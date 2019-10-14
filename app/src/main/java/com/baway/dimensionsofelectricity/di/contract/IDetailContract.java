package com.baway.dimensionsofelectricity.di.contract;


import okhttp3.FormBody;

/*
 *@Auther:杨雅慧
 *@Date: 19.6.21
 *@Time: 17:19:14
 *@Description:
 * */public interface IDetailContract {
    /**
     * V层
     */
    public interface IDetailView {
        public void showDetailData(String message);

        public void showSyncShoppingCartResult(String message);
    }

    /**
     * M层
     */
    public interface IDetailModel {
        public void containDetailData(int userId, String sessionId, int commodityId, CallBack back);

        public interface CallBack {
            public void getDetailResult(String s);
        }

        public void containSyncShoppingCartData(int userId, String sessionId, FormBody formBody, SyncShoppingCartCallBack back);

        public interface SyncShoppingCartCallBack {
            public void getSyncShoppingCartResult(String s);
        }
    }
}
