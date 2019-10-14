package com.baway.dimensionsofelectricity.data.beans;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.17
 *@Time: 16:46:31
 *@Description:
 * */public class MessageEvent {
    private String content;
    private int CommodityId;

    private String orderInfo;
    private double totalPrice;
    private int totalCount;

    public int getCommodityId() {
        return CommodityId;
    }

    public void setCommodityId(int commodityId) {
        CommodityId = commodityId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
