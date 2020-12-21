package com.resttcar.dh.entity;

import java.util.List;

/**
 * Created by James on 2020/6/18.
 */
public class OrderDetails {

        private OrderBean order;
        private List<GoodsBean> goods;

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class OrderBean {
            /**
             * order_id : 108
             * order_no : 20200424093936396000001
             * order_type : 2
             * price : 444.00
             * pay_price : 444.00
             * createtime : 1587692377
             * order_status : 4
             * beizhu :
             * nickname : skywalker
             * buyer_name :
             * buyer_mobile :
             * buyer_address :
             */

            private int order_id;
            private String order_no;
            private int order_type;
            private String price;
            private String pay_price;
            private int createtime;
            private int order_status;
            private String beizhu;
            private String nickname;
            private String buyer_name;
            private String buyer_mobile;
            private String buyer_address;

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public String getOrder_no() {
                return order_no;
            }

            public void setOrder_no(String order_no) {
                this.order_no = order_no;
            }

            public int getOrder_type() {
                return order_type;
            }

            public void setOrder_type(int order_type) {
                this.order_type = order_type;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getPay_price() {
                return pay_price;
            }

            public void setPay_price(String pay_price) {
                this.pay_price = pay_price;
            }

            public int getCreatetime() {
                return createtime;
            }

            public void setCreatetime(int createtime) {
                this.createtime = createtime;
            }

            public int getOrder_status() {
                return order_status;
            }

            public void setOrder_status(int order_status) {
                this.order_status = order_status;
            }

            public String getBeizhu() {
                return beizhu;
            }

            public void setBeizhu(String beizhu) {
                this.beizhu = beizhu;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getBuyer_name() {
                return buyer_name;
            }

            public void setBuyer_name(String buyer_name) {
                this.buyer_name = buyer_name;
            }

            public String getBuyer_mobile() {
                return buyer_mobile;
            }

            public void setBuyer_mobile(String buyer_mobile) {
                this.buyer_mobile = buyer_mobile;
            }

            public String getBuyer_address() {
                return buyer_address;
            }

            public void setBuyer_address(String buyer_address) {
                this.buyer_address = buyer_address;
            }
        }

        public static class GoodsBean {
            /**
             * order_id : 108
             * goods_id : 11
             * spec :
             * amount : 4
             * price : 444.00
             * goods_title : 牛山手数
             * goods_image : /uploads/20200317/a7a6d97d917b3a5cf5c5ed30986c0e36.jpg
             */

            private int order_id;
            private int goods_id;
            private String spec;
            private int amount;
            private String price;
            private String goods_title;
            private String goods_image;

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getSpec() {
                return spec;
            }

            public void setSpec(String spec) {
                this.spec = spec;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getGoods_title() {
                return goods_title;
            }

            public void setGoods_title(String goods_title) {
                this.goods_title = goods_title;
            }

            public String getGoods_image() {
                return goods_image;
            }

            public void setGoods_image(String goods_image) {
                this.goods_image = goods_image;
            }
        }
}
