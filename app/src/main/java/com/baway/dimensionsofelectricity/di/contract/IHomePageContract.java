package com.baway.dimensionsofelectricity.di.contract;


/*
 *@Auther:杨雅慧
 *@Date: 19.6.21
 *@Time: 17:19:14
 *@Description:
 * */public interface IHomePageContract {
    /**
     * V层
     */
    public interface IHomePageView {
        public void showHomePageXBannerData(String message);

        public void showHomePageData(String message);
    }

    /**
     * M层
     */
    public interface IHomePageModel {
        public void containHomePageData(CallBack back);

        public interface CallBack {
            public void getHomePageResult(String message);
        }

        public void containHomePageXBannerData(XBannerCallBack back);

        public interface XBannerCallBack {
            public void getHomePageXBannerResult(String message);
        }
    }
}
