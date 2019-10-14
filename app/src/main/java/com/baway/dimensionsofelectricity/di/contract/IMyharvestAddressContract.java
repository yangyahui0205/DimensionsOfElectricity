package com.baway.dimensionsofelectricity.di.contract;


/*
 *@Auther:杨雅慧
 *@Date: 19.6.21
 *@Time: 17:19:14
 *@Description:
 * */public interface IMyharvestAddressContract {
    /**
     * V层
     */
    public interface IMyharvestAddressView {
        public void showMyHarvestAddressData(String message);

        public void showDefaultReceiveAddressData(String message);
    }

    /**
     * M层
     */
    public interface IMyharvestAddressModel {
        public void containMyharvestAddressData(int userId, String sessionId, CallBack back);

        public interface CallBack {
            public void getIMyharvestAddressResult(String message);
        }

        public void containDefaultReceiveAddressData(int userId, String sessionId, int harvestAddressId, DefaultReceiveAddressCallBack back);

        public interface DefaultReceiveAddressCallBack {
            public void getDefaultReceiveAddressResult(String message);
        }
    }
}
