package com.baway.dimensionsofelectricity.di.contract;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.8
 *@Time: 19:24:21
 *@Description:
 * */public interface ISettlementContract {
    public interface ISettlementView {
        public void showCreateOrderData(String message);

        void showShoppingData(String message);

        void showAddressListData(String message);
    }

    public interface ISettlementModel {
        public void containCreateOrderData(int userId, String sessionId, String orderInfo, double totalPrice, int addressId, CallBack back);

        public interface CallBack {
            public void getCreateOrderResult(String message);
        }

        public void containShoppingData(int userId, String sessionId, ShoppingCartCallBack back);

        public interface ShoppingCartCallBack {
            public void getShoppingCartResult(String message);
        }

        public void containAddressList(int userId, String sessionId,AddressListCallBack back);

        public interface AddressListCallBack {
            public void getAddressListResult(String message);
        }
    }
}
