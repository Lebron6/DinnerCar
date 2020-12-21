package com.resttcar.dh.entity;

import java.util.List;

/**
 * Created by James on 2020/6/18.
 */
public class OrderList {

        private int current_page;
        private int per_page;
        private int total;
        private boolean has_more;
        private List<ListBean> list;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public boolean isHas_more() {
            return has_more;
        }

        public void setHas_more(boolean has_more) {
            this.has_more = has_more;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * order_id : 91
             * order_no : 20200409151753938300001
             * order_type : 2
             * price : 744.00
             * pay_price : 744.00
             * createtime : 1586506821
             * order_status : 6
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

}
