package com.baway.dimensionsofelectricity.di.contract;


/*
 *@Auther:杨雅慧
 *@Date: 19.6.21
 *@Time: 17:19:14
 *@Description:
 * */public interface IAddHarvestAddressContract {
    /**
     * V层
     */
    public interface IAddHarvestAddressView {
        public void showMyHarvestAddressData(String message);
    }

    /**
     * M层
     */
    public interface IAddHarvestAddressModel {
        public void containAddHarvestAddressData(int userId, String sessionId, String realName, String phone, String address, String zipCode, CallBack back);

        public interface CallBack {
            public void getAddHarvestAddressResult(String message);
        }
    }
}
