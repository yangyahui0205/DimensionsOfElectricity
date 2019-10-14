package com.baway.dimensionsofelectricity.di.contract;


/*
 *@Auther:杨雅慧
 *@Date: 19.6.25
 *@Time: 16:47:09
 *@Description:
 * */public interface ISearchListContract {
    /**
     * V层
     */
    public interface ISearchListView {
        public void showSearchListData(String message);
    }


    /**
     * M层
     */
    public interface ISearchListModel {
        public void containSearchListData(String content, int page, CallBack back);

        public interface CallBack {
            public void getSearchListResult(String message);
        }
    }
}
