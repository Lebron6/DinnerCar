package com.resttcar.dh.entity;

import java.util.List;

/**
 * Created by James on 2020/5/25.
 *
 */
public class OrderInfo {


        private List<OrdersBean> orders;
        private List<CancelOrdersBean> cancel_orders;

        public List<OrdersBean> getOrders() {
            return orders;
        }

        public void setOrders(List<OrdersBean> orders) {
            this.orders = orders;
        }

        public List<CancelOrdersBean> getCancel_orders() {
            return cancel_orders;
        }

        public void setCancel_orders(List<CancelOrdersBean> cancel_orders) {
            this.cancel_orders = cancel_orders;
        }

        public static class OrdersBean {
            /**
             * order_id : 91
             * order_no : 20200409151753938300001
             * price : 744.00
             * createtime : 1586506821
             * successtime : 1586506821
             * pay_price : 744.00
             * order_status : 1
             * beizhu :
             * order_code :
             * nickname : skywalker
             * gender : 0
             * avatar : /uploads/20200113/b47109edf204a155aff1292b1dfbcff5.jpg
             * mobile : 1862615161
             * buyer_name :
             * buyer_mobile :
             * buyer_address :
             * list : [{"goods_id":11,"spec":"一人份","amount":4,"price":"444.00","title":"牛山手数","image":"/uploads/20200317/a7a6d97d917b3a5cf5c5ed30986c0e36.jpg"},{"goods_id":13,"spec":"一人份","amount":3,"price":"300.00","title":"牛山手数888","image":"http://dh.tigerisa.com/uploads/20200318/305f08120ddbe5bfa01a1f00902bc4a7.jpg"}]
             * fee : 44.64
             * income : 699.36
             */

            private int order_id;
            private String order_no;
            private String price;
            private int createtime;
            private int successtime;
            private String pay_price;
            private int order_status;
            private String beizhu;
            private String order_code;
            private String nickname;
            private int gender;
            private String avatar;
            private String mobile;
            private String buyer_name;
            private String buyer_mobile;
            private String buyer_address;
            private double fee;
            private double income;
            private List<ListBean> list;

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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getCreatetime() {
                return createtime;
            }

            public void setCreatetime(int createtime) {
                this.createtime = createtime;
            }

            public int getSuccesstime() {
                return successtime;
            }

            public void setSuccesstime(int successtime) {
                this.successtime = successtime;
            }

            public String getPay_price() {
                return pay_price;
            }

            public void setPay_price(String pay_price) {
                this.pay_price = pay_price;
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

            public String getOrder_code() {
                return order_code;
            }

            public void setOrder_code(String order_code) {
                this.order_code = order_code;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
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

            public double getFee() {
                return fee;
            }

            public void setFee(double fee) {
                this.fee = fee;
            }

            public double getIncome() {
                return income;
            }

            public void setIncome(double income) {
                this.income = income;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * goods_id : 11
                 * spec : 一人份
                 * amount : 4
                 * price : 444.00
                 * title : 牛山手数
                 * image : /uploads/20200317/a7a6d97d917b3a5cf5c5ed30986c0e36.jpg
                 */

                private int goods_id;
                private String spec;
                private int amount;
                private String price;
                private String title;
                private String image;

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

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }
            }
        }

        public static class CancelOrdersBean {
            /**
             * order_id : 91
             */

            private int order_id;

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }
        }

}
