package com.resttcar.dh.api;

/**
 * Created by Administrator on 2018/1/4.
 */

public class BaseUrl {

    private static BaseUrl baseUrl;

    public static BaseUrl getInstence() {
        if (baseUrl == null) {
            return new BaseUrl();
        }
        return baseUrl;
    }

//    public String ipAddress  = "https://dh.resttcar.com/";   //线上服务器
    public String ipAddress  = "http://test.resttcar.com/";   //测试服务器

    /**
     * 用户登录
     */
    public String getUserLoginUrl() {
        return ipAddress + "car/api/index/login/";
    }

    /**
     * 外卖营业状态设置--获取数据
     */
    public String getPanelCinfig() {
        return ipAddress + "car/api/panel/config/";
    }

    /**
     * 外卖营业状态设置--提交数据
     */
    public String panelCinfig() {
        return ipAddress + "car/api/panel/config/";
    }

    /**
     * 我的--个人中心
     */
    public String userCenter() {
        return ipAddress + "car/api/panel/index/";
    }

    /**
     * 用户退出登录
     */
    public String loginOut() {
        return ipAddress + "car/api/index/logout/";
    }

    /**
     * 收银台--数据
     */
    public String getdeskIndexData() {
        return ipAddress + "car/api/desk/index/";
    }

    /**
     * 收银台--挂单数据
     */
    public String getHang() {
        return ipAddress + "car/api/desk/hang/";
    }

    /**
     * 收银台--取消挂单数据
     */
    public String getDeleteHang() {
        return ipAddress + "car/api/desk/hang_del/";
    }

    /**
     * 收银台--提交挂单数据
     */
    public String getDeskConfirm() {
        return ipAddress + "car/api/desk/confirm/";
    }

    /**
     * 收银台--取单
     */
    public String getHang_get() {
        return ipAddress + "car/api/desk/hang_get/";
    }

    /**
     * 收银台--确定订单--检测订单
     */
    public String checkOrder() {
        return ipAddress + "car/api/desk/check_order/";
    }

    /**
     * 订单--获取数据
     */
    public String panelOrders() {
        return ipAddress + "car/api/panel/orders/";
    }

    /**
     * 订单--接单
     */
    public String orderConfirm() {
        return ipAddress + "car/api/panel/order_confirm/";
    }

    /**
     * 订单--拒绝接单
     */
    public String orderReject() {
        return ipAddress + "car/api/panel/order_reject/";
    }

    /**
     * 订单--出单
     */
    public String orderConfirm2() {
        return ipAddress + "car/api/panel/order_confirm2/";
    }

    /**
     * 菜单管理--品牌菜单/提交数据
     */
    public String goodBrand() {
        return ipAddress + "car/api/goods/brand/";
    }

    /**
     * 菜单管理--餐车菜单
     */
    public String goodsIndex() {
        return ipAddress + "car/api/goods/index/";
    }

    /**
     * 菜单管理--餐车菜单--商品上下架
     */
    public String goodsOnOff() {
        return ipAddress + "car/api/goods/goods_onoff/";
    }

    /**
     * 菜单管理--餐车菜单--商品删除
     */
    public String goodsDelete() {
        return ipAddress + "car/api/goods/goods_delete/";
    }

    /**
     * 菜单管理--餐车菜单--商品分类列表
     */
    public String classList() {
        return ipAddress + "car/api/goods/class_list/";
    }

    /**
     * 菜单管理--餐车菜单--商品分类删除
     */
    public String deleteClass() {
        return ipAddress + "car/api/goods/goods_class_delete/";
    }

    /**
     * 菜单管理--餐车菜单--商品分类添加
     */
    public String addClass() {
        return ipAddress + "car/api/goods/class_add/";
    }

    /**
     * 菜单管理--餐车菜单--商品分类排序
     */
    public String classSort() {
        return ipAddress + "car/api/goods/class_sort/";
    }

    /**
     * 上传图片
     */
    public String upLoadPic() {
        return ipAddress + "car/api/ajax/upload/";
    }

    /**
     * 添加商品
     */
    public String addGoods() {
        return ipAddress + "car/api/goods/add/";
    }

    /**
     * 原料采购--原料列表
     */
    public String materialIndex() {
        return ipAddress + "car/api/material/index/";
    }

    /**
     * 原料采购--确定订单
     */
    public String materialConfirm() {
        return ipAddress + "car/api/material/confirm/";
    }

    /**
     * 原料采购--订单列表
     */
    public String materialOrders() {
        return ipAddress + "car/api/material/orders/";
    }

    /**
     * 原料采购--订单列表--订单详细
     */
    public String materialDetail() {
        return ipAddress + "car/api/material/detail/";
    }

    /**
     * 财务统计--财务信息
     */
    public String cwInfo() {
        return ipAddress + "car/api/caiwu/info/";
    }

    /**
     * 财务统计--财务列表
     */
    public String cwIndex() {
        return ipAddress + "car/api/caiwu/index/";
    }

    /**
     * 财务统计--提交提现
     */
    public String cwTiXian() {
        return ipAddress + "car/api/caiwu/tixian/";
    }

    /**
     * 通知中心--消息列表
     */
    public String messageIndex() {
        return ipAddress + "car/api/message/index/";
    }

    /**
     * 设置--餐车信息
     */
    public String carIndex() {
        return ipAddress + "car/api/car/index/";
    }

    /**
     * 设置--编辑信息--提交数据
     */
    public String editCarInfo() {
        return ipAddress + "car/api/car/add/";
    }

    /**
     * 订单--订单管理--订单列表
     */
    public String orderIndex() {
        return ipAddress + "car/api/order/index/";
    }

    /**
     * 订单--订单管理--订单列表--订单详细
     */
    public String orderDetails() {
        return ipAddress + "car/api/order/detail/";
    }

}
