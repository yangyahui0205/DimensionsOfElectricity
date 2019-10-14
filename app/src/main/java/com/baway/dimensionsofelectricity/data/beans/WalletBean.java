package com.baway.dimensionsofelectricity.data.beans;

import java.util.List;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.21
 *@Time: 15:45:53
 *@Description:
 * */public class WalletBean {

    /**
     * result : {"balance":9.999981E7,"detailList":[{"amount":189,"consumerTime":1563520644000,"orderId":"20190719151722133480","userId":480}]}
     * message : 查询成功
     * status : 0000
     */

    private ResultBean result;
    private String message;
    private String status;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class ResultBean {
        /**
         * balance : 9.999981E7
         * detailList : [{"amount":189,"consumerTime":1563520644000,"orderId":"20190719151722133480","userId":480}]
         */

        private double balance;
        private List<DetailListBean> detailList;

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public List<DetailListBean> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<DetailListBean> detailList) {
            this.detailList = detailList;
        }

        public static class DetailListBean {
            /**
             * amount : 189
             * consumerTime : 1563520644000
             * orderId : 20190719151722133480
             * userId : 480
             */

            private int amount;
            private long consumerTime;
            private String orderId;
            private int userId;

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public long getConsumerTime() {
                return consumerTime;
            }

            public void setConsumerTime(long consumerTime) {
                this.consumerTime = consumerTime;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
