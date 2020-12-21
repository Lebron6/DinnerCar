package com.resttcar.dh.entity;

import java.util.List;

/**
 * Created by James on 2020/5/18.
 */
public class ConfirmOrderData {

    /**
     * orders : [{"goods_id":"1","amount":"2","spec":"11+柔柔弱弱"},{"goods_id":"11","amount":"3","spec":""}]
     * hang : true
     */

    private String hang;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    private String beizhu;
    private List<OrdersBean> orders;

    public String getHang() {
        return hang;
    }

    public void setHang(String hang) {
        this.hang = hang;
    }

    public List<OrdersBean> getOrders() {
        return orders;
    }

    public void setOrders(List<OrdersBean> orders) {
        this.orders = orders;
    }

    public static class OrdersBean {
        /**
         * goods_id : 1
         * amount : 2
         * spec : 11+柔柔弱弱
         */

        private String goods_id;
        private String amount;
        private String spec;

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }
    }
}
