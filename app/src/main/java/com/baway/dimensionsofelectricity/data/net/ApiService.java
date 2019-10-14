package com.baway.dimensionsofelectricity.data.net;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*
 *@Auther:杨雅慧
 *@Date: 19.7.11
 *@Time: 08:44:46
 *@Description:
 * */public interface ApiService {
    //http://172.17.8.100/ small/user/v1/register
    @FormUrlEncoded
    @POST("small/user/v1/register")
    public Observable<ResponseBody> userRegister(@Field("phone") String phone, @Field("pwd") String pwd);

    //http://172.17.8.100/ small/user/v1/login
    @FormUrlEncoded
    @POST("small/user/v1/login")
    public Observable<ResponseBody> userLogin(@Field("phone") String phone, @Field("pwd") String pwd);

    //"http://172.17.8.100/ bannerShow";
    //"http://172.17.8.100/ small/commodity/v1/commodityList";
    @GET("small/commodity/v1/{path}")
    public Observable<ResponseBody> getHomePageData(@Path("path") String path);

    //http://172.17.8.100/
    //small/commodity/v1/findCommodityByKeyword
    // ?keyword=%E6%9D%BF%E9%9E%8B
    // &count=5
    // &page=1
    @GET("small/commodity/v1/findCommodityByKeyword")
    public Observable<ResponseBody> findCommodityByKeyword(@Query("keyword") String keyword,
                                                           @Query("count") int count,
                                                           @Query("page") int page);

    //http://172.17.8.100/ small/commodity/v1/findCommodityDetailsById
    @GET("small/commodity/v1/findCommodityDetailsById")
    public Observable<ResponseBody> findCommodityDetailsById(@Header("userId") int userId,
                                                             @Header("sessionId") String sessionId,
                                                             @Query("commodityId") int commodityId);

    //圈子列表
    //http://172.17.8.100/small/circle/v1/findCircleList
    @GET("small/circle/v1/findCircleList")
    public Observable<ResponseBody> findCircleList(@Header("userId") int userId,
                                                   @Header("sessionId") String sessionId,
                                                   @Query("page") int page,
                                                   @Query("count") int count);

    //查询购物车
    //http://172.17.8.100/small/order/verify/v1/findShoppingCart
    @GET("small/order/verify/v1/findShoppingCart")
    public Observable<ResponseBody> findShoppingCart(@Header("userId") int userId,
                                                     @Header("sessionId") String sessionId);

    //http://172.17.8.100/ small/order/verify/v1/createOrder
    @FormUrlEncoded
    @POST("small/order/verify/v1/createOrder")
    public Observable<ResponseBody> createOrder(@Header("userId") int userId,
                                                @Header("sessionId") String sessionId,
                                                @Field("orderInfo") String orderInfo,
                                                @Field("totalPrice") double totalPrice,
                                                @Field("addressId") int addressId);

    //根据订单状态查询数据
    //http://172.17.8.100/ small/order/verify/v1/findOrderListByStatus
    @GET("small/order/verify/v1/findOrderListByStatus")
    Observable<ResponseBody> findOrderListByStatus(@Header("userId") int userId,
                                                   @Header("sessionId") String sessionId,
                                                   @Query("status") int status,
                                                   @Query("page") int page,
                                                   @Query("count") int count);

    //同步购物车数据
    //http://172.17.8.100/small/order/verify/v1/syncShoppingCart
    @HTTP(method = "PUT", path = "small/order/verify/v1/syncShoppingCart", hasBody = true)
    public Observable<ResponseBody> syncShoppingCart(@Header("userId") int userId,
                                                     @Header("sessionId") String sessionId,
                                                     @Body RequestBody requestBody);

    //查询订单明细数据
    //http://172.17.8.100/ small/order/verify/v1/findOrderInfo
    @GET("small/order/verify/v1/findOrderInfo")
    public Observable<ResponseBody> findOrderInfo(@Header("userId") int userId,
                                                  @Query("orderId") String orderId);

    //收获地址列表
    //http://172.17.8.100/ small/user/verify/v1/receiveAddressList
    @GET("small/user/verify/v1/receiveAddressList")
    public Observable<ResponseBody> receiveAddressList(@Header("userId") int userId,
                                                       @Header("sessionId") String sessionId);

    //设置默认收获地址
    //http://172.17.8.100/ small/user/verify/v1/setDefaultReceiveAddress
    @FormUrlEncoded
    @POST("small/user/verify/v1/setDefaultReceiveAddress")
    public Observable<ResponseBody> setDefaultReceiveAddress(@Header("userId") int userId,
                                                             @Header("sessionId") String sessionId,
                                                             @Field("id") int id);

    //新增收获地址
    //http://172.17.8.100/ small/user/verify/v1/addReceiveAddress\
    @FormUrlEncoded
    @POST("small/user/verify/v1/addReceiveAddress")
    public Observable<ResponseBody> addReceiveAddress(@Header("userId") int userId,
                                                      @Header("sessionId") String sessionId,
                                                      @Field("realName") String realName,
                                                      @Field("phone") String phone,
                                                      @Field("address") String address,
                                                      @Field("zipCode") String zipCode);

    //修改收获地址
    //http://172.17.8.100/ small/user/verify/v1/changeReceiveAddress
    @FormUrlEncoded
    @PUT("small/user/verify/v1/changeReceiveAddress")
    public Observable<ResponseBody> changeReceiveAddress(@Header("userId") int userId,
                                                         @Header("sessionId") String sessionId,
                                                         @Field("id") int id,
                                                         @Field("realName") String realName,
                                                         @Field("phone") String phone,
                                                         @Field("address") String address,
                                                         @Field("zipCode") String zipCode);

    //我的钱包
    //    http://172.17.8.100/ small/user/verify/v1/findUserWallet
    @GET("small/user/verify/v1/findUserWallet")
    public Observable<ResponseBody> findUserWallet(@Header("userId") int userId,
                                                   @Header("sessionId") String sessionId,
                                                   @Query("page") int page,
                                                   @Query("count") int count);

    //我的圈子
    //http://172.17.8.100/ small/circle/verify/v1/findMyCircleById
    @GET("small/circle/verify/v1/findMyCircleById")
    public Observable<ResponseBody> findMyCircleById(@Header("userId") int userId,
                                                     @Header("sessionId") String sessionId,
                                                     @Query("page") int page,
                                                     @Query("count") int count);

    //支付
    //http://172.17.8.100/ small/order/verify/v1/pay
    @FormUrlEncoded
    @POST("small/order/verify/v1/pay")
    public Observable<ResponseBody> pay(@Header("userId") int userId,
                                        @Header("sessionId") String sessionId,
                                        @Field("orderId") String orderId,
                                        @Field("payType") int payType);

    //删除订单
    //http://172.17.8.100/ small/order/verify/v1/deleteOrder
    @DELETE("small/order/verify/v1/deleteOrder")
    public Observable<ResponseBody> deleteOrder(@Header("userId") int userId,
                                                @Header("sessionId") String sessionId,
                                                @Query("orderId") String orderId);

    //收货
    //http://172.17.8.100/ small/order/verify/v1/confirmReceipt
    @FormUrlEncoded
    @PUT("small/order/verify/v1/confirmReceipt")
    public Observable<ResponseBody> confirmReceipt(@Header("userId") int userId,
                                                   @Header("sessionId") String sessionId,
                                                   @Field("orderId") String orderId);

    //商品评论
    //http://172.17.8.100/ small/commodity/verify/v1/addCommodityComment
    @Multipart
    @POST("small/commodity/verify/v1/addCommodityComment")
    public Observable<ResponseBody> addCommodityComment(@Header("userId") int userId,
                                                        @Header("sessionId") String sessionId,
                                                        @Part List<MultipartBody.Part> parts);

    //发布圈子
    // http://172.17.8.100/small/circle/verify/v1/releaseCircle
    @Multipart
    @POST("small/circle/verify/v1/releaseCircle")
    public Observable<ResponseBody> releaseCircle(@Header("userId") int userId,
                                                  @Header("sessionId") String sessionId,
                                                  @Part List<MultipartBody.Part> parts);

    //我的足迹
    //http://172.17.8.100/ small/commodity/verify/v1/browseList
    @GET("small/commodity/verify/v1/browseList")
    public Observable<ResponseBody> browseList(@Header("userId") int userId,
                                               @Header("sessionId") String sessionId,
                                               @Query("page") int page,
                                               @Query("count") int count);

    //.根据用户ID查询用户信息
    //http://172.17.8.100/ small/user/verify/v1/getUserById
    @GET("small/user/verify/v1/getUserById")
    public Observable<ResponseBody> getUserById(@Header("userId") int userId,
                                                @Header("sessionId") String sessionId);

    //修改昵称
    //http://172.17.8.100/ small/user/verify/v1/modifyUserNick
    @PUT("small/user/verify/v1/modifyUserNick")
    public Observable<ResponseBody> modifyUserNick(@Header("userId") int userId,
                                                   @Header("sessionId") String sessionId,
                                                   @Query("nickName") String nickName);

    //修改用户密码
    //http://172.17.8.100/ small/user/verify/v1/modifyUserPwd
    @PUT("small/user/verify/v1/modifyUserPwd")
    public Observable<ResponseBody> modifyUserPwd(@Header("userId") int userId,
                                                  @Header("sessionId") String sessionId,
                                                  @Query("oldPwd") String oldPwd,
                                                  @Query("newPwd") String newPwd);

    //用户上传头像
    //http://172.17.8.100/small/user/verify/v1/modifyHeadPic
    @Multipart
    @POST("small/user/verify/v1/modifyHeadPic")
    public Observable<ResponseBody> modifyHeadPic(@Header("userId") int userId,
                                                  @Header("sessionId") String sessionId,
                                                  @Part List<MultipartBody.Part> parts);
    //删除我发表过的圈子

    //http://172.17.8.100/ small/circle/verify/v1/deleteCircle
    @DELETE("small/circle/verify/v1/deleteCircle")
    public Observable<ResponseBody> deleteCircle(@Header("userId") int userId,
                                                 @Header("sessionId") String sessionId,
                                                 @Query("circleId") int circleId);

    //圈子点赞
    //http://172.17.8.100/ small/circle/verify/v1/addCircleGreat
    @POST("small/circle/verify/v1/addCircleGreat")
    public Observable<ResponseBody> addCircleGreat(@Header("userId") int userId,
                                                   @Header("sessionId") String sessionId,
                                                   @Query("circleId") int circleId);


    //取消点赞
    //http://172.17.8.100/ small/circle/verify/v1/cancelCircleGreat
    @DELETE("small/circle/verify/v1/cancelCircleGreat")
    public Observable<ResponseBody> cancelCircleGreat(@Header("userId") int userId,
                                                      @Header("sessionId") String sessionId,
                                                      @Query("circleId") int circleId);

    //查询订单明细数据
    //http://172.17.8.100/small/order/verify/v1/findOrderInfo
    @GET("small/order/verify/v1/findOrderInfo")
    public Observable<ResponseBody> findOrderInfo(@Header("userId") int userId,
                                                  @Header("sessionId") String sessionId,
                                                  @Field("orderId") String orderId);

}
