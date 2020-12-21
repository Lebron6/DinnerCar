package com.resttcar.dh.api;


import com.resttcar.dh.entity.ApiResponse;
import com.resttcar.dh.entity.BrandData;
import com.resttcar.dh.entity.CWIndex;
import com.resttcar.dh.entity.CWInfo;
import com.resttcar.dh.entity.CarInfo;
import com.resttcar.dh.entity.CheckOrderResult;
import com.resttcar.dh.entity.ConfirmClassData;
import com.resttcar.dh.entity.ConfirmMaterialData;
import com.resttcar.dh.entity.ConfirmOrderData;
import com.resttcar.dh.entity.DeskIndex;
import com.resttcar.dh.entity.EditCarInfo;
import com.resttcar.dh.entity.GetHang;
import com.resttcar.dh.entity.GetPanelConfig;
import com.resttcar.dh.entity.GoodIndex;
import com.resttcar.dh.entity.GoodsType;
import com.resttcar.dh.entity.HangData;
import com.resttcar.dh.entity.MaterialIndex;
import com.resttcar.dh.entity.MaterialOrders;
import com.resttcar.dh.entity.Notice;
import com.resttcar.dh.entity.OrderConfirmData;
import com.resttcar.dh.entity.OrderDetails;
import com.resttcar.dh.entity.OrderInfo;
import com.resttcar.dh.entity.OrderList;
import com.resttcar.dh.entity.PurDetails;
import com.resttcar.dh.entity.TiXian;
import com.resttcar.dh.entity.UpLoadPic;
import com.resttcar.dh.entity.UpdataBrand;
import com.resttcar.dh.entity.User;
import com.resttcar.dh.entity.UserCenter;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by James on 2018/1/4.
 */

public interface CarApi {

    String Content_Type="translate?doctype=json&jsonversion=&type=&keyfrom=&model=&mid=&imei=&vendor=&screen=&ssid=&network=&abtest=";

    /**
     * 登录
     * @param phone
     * @param password
     * @return
     */
    @POST(Content_Type)
    @FormUrlEncoded
    Call<ApiResponse<User>> userLogin(@Field("account") String phone, @Field("password") String password);

    /**
     * 获取外卖营业状态数据
     * @return
     */
    @GET(Content_Type)
    Call<ApiResponse<GetPanelConfig>> getPanelConfig(@Query("token") String token);

    /**
     * 提交外卖营业状态
     */
    @POST(Content_Type)
    @FormUrlEncoded
    Call<ApiResponse> cimmitPanelConfig(@Field("token") String token,@Field("send_status") int send_status, @Field("send_type_id") int send_type_id, @Field("send_range") String send_range, @Field("start_money") String start_money);

    /**
     * 提交营业状态
     */
    @POST(Content_Type)
    @FormUrlEncoded
    Call<ApiResponse> cimmitPanelConfig(@Field("token") String token,@Field("status") int status);

    /**
     * 收银台--数据
     */
    @GET(Content_Type)
    Call<ApiResponse<DeskIndex>> getDeskIndex(@Query("token") String token);

    /**
     * 收银台--挂单数据
     */
    @GET(Content_Type)
    Call<ApiResponse<HangData>> getHang(@Query("token") String token);

    /**
     * 取消挂单
     */
    @POST(Content_Type)
    @FormUrlEncoded
    Call<ApiResponse> deleteHang(@Field("token") String token,@Field("order_id") int order_id);

    /**
     * 提交挂单数据
     */
    @POST(Content_Type)
    Call<ApiResponse> confirmHang(@Body ConfirmOrderData vo);

    /**
     * 取单
     */
    @GET(Content_Type)
    Call<ApiResponse<GetHang>> hangGet(@Query("token") String token, @Query("order_id") String order_id);

    /**
     * 收银台--确定订单--检测订单
     */
    @GET(Content_Type)
    Call<ApiResponse<CheckOrderResult>> checkOrder(@Query("token") String token, @Query("order_id") String order_id);

    /**
     * 首页订单数据
     */
    @GET(Content_Type)
    Call<ApiResponse<OrderInfo>> panelOrders(@Query("token") String token);

    /**
     * 接单
     */
    @POST(Content_Type)
    @FormUrlEncoded
    Call<ApiResponse<OrderConfirmData>> orderConfirm(@Field("token") String token, @Field("order_id") int order_id);

    /**
     * 拒绝接单
     */
    @POST(Content_Type)
    @FormUrlEncoded
    Call<ApiResponse> rejectOrder(@Field("token") String token, @Field("order_id") int order_id);

    /**
     * 出单
     */
    @POST(Content_Type)
    @FormUrlEncoded
    Call<ApiResponse> orderConfirm2(@Field("token") String token, @Field("order_id") int order_id);

    /**
     * 菜单管理--品牌菜单
     */
    @GET(Content_Type)
    Call<ApiResponse<BrandData>> brand(@Query("token") String token, @Query("title") String title);

    /**
     * 菜单管理--品牌菜单--待测试
     */
    @POST(Content_Type)
    Call<ApiResponse> brand(@Body UpdataBrand ub);

    /**
     * 菜单管理--品牌菜单
     */
    @GET(Content_Type)
    Call<ApiResponse<GoodIndex>> goodIndex(@Query("token") String token);

    /**
     * 菜单管理--餐车菜单--商品上下架
     */
    @POST(Content_Type)
    @FormUrlEncoded
    Call<ApiResponse> goodsOnOff(@Field("token") String token, @Field("goods_id") int goods_id, @Field("status") int status);

    /**
     * 菜单管理--餐车菜单--商品删除
     */
    @POST(Content_Type)
    @FormUrlEncoded
    Call<ApiResponse> goodsDelete(@Field("token") String token, @Field("goods_id") int goods_id);

    /**
     * 菜单管理--品牌菜单
     */
    @GET(Content_Type)
    Call<ApiResponse<GoodsType>> goodsTypes(@Query("token") String token);

    /**
     * 上传图片
     */
    @POST(Content_Type)
    @Multipart
    Call<ApiResponse<UpLoadPic>> upLoadPic( @Part MultipartBody.Part part);

    /**
     * 菜单管理--餐车菜单--添加商品
     */
    @POST(Content_Type)
    @FormUrlEncoded
    Call<ApiResponse> addGoods(@Field("token") String token, @Field("goods_id") String goods_id, @Field("class_id") int class_id, @Field("title") String title,
                               @Field("content") String content,@Field("image") String image,@Field("price") String price,@Field("last_amount") String last_amount,
                               @Field("spec") String spec);

    /**
     * 原料采购--获取数据
     */
    @GET(Content_Type)
    Call<ApiResponse<MaterialIndex>> materialIndex(@Query("token") String token);

    /**
     * 原料采购--确定订单
     */
    @POST(Content_Type)
    Call<ApiResponse> materialConfirm(@Body ConfirmMaterialData vo);

    /**
     * 原料采购--订单列表
     */
    @GET(Content_Type)
    Call<ApiResponse<MaterialOrders>> materialOrders(@Query("token") String token, @Query("page") int page, @Query("pagesize") int pagesize, @Query("order_no") String order_no);

    /**
     * 原料采购--订单列表--订单详细
     */
    @GET(Content_Type)
    Call<ApiResponse<PurDetails>> getPurDetails(@Query("token") String token,@Query("order_id") int order_id);

    /**
     * 财务统计--财务信息
     */
    @GET(Content_Type)
    Call<ApiResponse<CWIndex>> cwInfo(@Query("token") String token, @Query("order_id") int order_id);

    /**
     * 财务统计--信息
     */
    @GET(Content_Type)
    Call<ApiResponse<CWInfo>> cwInfo(@Query("token") String token);

    /**
     * 财务统计--财务列表
     */
    @GET(Content_Type)
    Call<ApiResponse<CWIndex>> cwIndex(@Query("token") String token, @Query("page") int page, @Query("pagesize") int pagesize);

    /**
     * 财务统计--提现列表
     */
    @GET(Content_Type)
    Call<ApiResponse<TiXian>> cwTiXian(@Query("token") String token, @Query("order_id") int order_id);

    /**
     * 财务统计--提交提现
     */
    @POST(Content_Type)
    @FormUrlEncoded
    Call<ApiResponse> cwTiXian(@Field("token") String token, @Field("tixian") String tixian);

    /**
     * 菜单管理--餐车菜单--商品分类删除
     */
    @POST(Content_Type)
    @FormUrlEncoded
    Call<ApiResponse> deleteClass(@Field("token") String token, @Field("class_id") int class_id);

    /**
     * 菜单管理--餐车菜单--商品分类添加
     */
    @POST(Content_Type)
    @FormUrlEncoded
    Call<ApiResponse> addClass(@Field("token") String token,@Field("class_name") String class_name, @Field("class_id") String class_id);

    /**
     * 提交分类
     */
    @POST(Content_Type)
    Call<ApiResponse> confirmClass(@Body ConfirmClassData vo);

    /**
     * 退出登录
     */
    @POST(Content_Type)
    @FormUrlEncoded
    Call<ApiResponse> loginOut(@Field("token") String token);

    /**
     * 通知中心--消息列表
     */
    @GET(Content_Type)
    Call<ApiResponse<Notice>> messageIndex(@Query("token") String token, @Query("page") int page, @Query("pagesize") int pagesize);

    /**
     * 设置--餐车信息
     */
    @GET(Content_Type)
    Call<ApiResponse<CarInfo>> carIndex(@Query("token") String token);

    /**
     * 设置--编辑信息
     */
    @GET(Content_Type)
    Call<ApiResponse<EditCarInfo>> carEditIndex(@Query("token") String token);

    /**
     * 设置--编辑信息--提交数据
     */
    @POST(Content_Type)
    @FormUrlEncoded
    Call<ApiResponse> editCarInfo(@Field("token") String token,@Field("car_name") String car_name,@Field("car_avatar") String car_avatar,
                               @Field("car_owner") String car_owner, @Field("car_no") String car_no,@Field("address") String address,
                               @Field("bank") String bank, @Field("bank_account") String bank_account, @Field("jointime") String jointime,
                                  @Field("province") String province, @Field("city") String city, @Field("district") String district
            , @Field("brand_id") int brand_id, @Field("run_type_id") int run_type_id);

    /**
     * 我的--个人中心
     */
    @GET(Content_Type)
    Call<ApiResponse<UserCenter>> userCenter(@Query("token") String token);

    /**
     * 订单--订单管理--订单列表
     */
    @GET(Content_Type)
    Call<ApiResponse<OrderList>> orderList(@Query("token") String token, @Query("page") int page, @Query("pagesize") int pagesize, @Query("order_no") String order_no);

    /**
     * 订单--订单管理--订单列表--订单详细
     */
    @GET(Content_Type)
    Call<ApiResponse<OrderDetails>> getOrderDetails(@Query("token") String token, @Query("order_id") int order_id);

}