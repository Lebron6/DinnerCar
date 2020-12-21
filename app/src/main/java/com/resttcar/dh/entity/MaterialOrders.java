package com.resttcar.dh.entity;

import java.util.List;

/**
 * Created by James on 2020/5/29.
 */
public class MaterialOrders {

        /**
         * current_page : 1
         * per_page : 10
         * total : 13
         * has_more : true
         * list : [{"order_id":25,"order_no":"20200409151436400000001","price":"0.01","pay_type":1,"order_status":1,"createtime":1586416476,"completetime":0,"beizhu":""},{"order_id":24,"order_no":"20200409151346667300001","price":"0.01","pay_type":1,"order_status":1,"createtime":1586416426,"completetime":0,"beizhu":""},{"order_id":22,"order_no":"20200408170847628500001","price":"0.05","pay_type":1,"order_status":1,"createtime":1586336927,"completetime":0,"beizhu":""},{"order_id":21,"order_no":"20200408170327818900001","price":"0.05","pay_type":1,"order_status":1,"createtime":1586336607,"completetime":0,"beizhu":""},{"order_id":20,"order_no":"20200408165928521400001","price":"0.06","pay_type":1,"order_status":1,"createtime":1586336368,"completetime":0,"beizhu":""},{"order_id":19,"order_no":"20200408165903847500001","price":"0.05","pay_type":1,"order_status":1,"createtime":1586336343,"completetime":0,"beizhu":""},{"order_id":18,"order_no":"20200408165740877000001","price":"0.04","pay_type":1,"order_status":1,"createtime":1586336260,"completetime":0,"beizhu":""},{"order_id":17,"order_no":"20200408165601998600001","price":"0.04","pay_type":1,"order_status":1,"createtime":1586336161,"completetime":0,"beizhu":""},{"order_id":16,"order_no":"20200408165431112500001","price":"0.04","pay_type":1,"order_status":1,"createtime":1586336071,"completetime":0,"beizhu":""},{"order_id":15,"order_no":"20200408165038429000001","price":"0.10","pay_type":1,"order_status":1,"createtime":1586335838,"completetime":0,"beizhu":""}]
         */

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
             * order_id : 25
             * order_no : 20200409151436400000001
             * price : 0.01
             * pay_type : 1
             * order_status : 1
             * createtime : 1586416476
             * completetime : 0
             * beizhu :
             */

            private int order_id;
            private String order_no;
            private String price;
            private int pay_type;
            private int order_status;
            private int createtime;
            private int completetime;
            private String beizhu;

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

            public int getPay_type() {
                return pay_type;
            }

            public void setPay_type(int pay_type) {
                this.pay_type = pay_type;
            }

            public int getOrder_status() {
                return order_status;
            }

            public void setOrder_status(int order_status) {
                this.order_status = order_status;
            }

            public int getCreatetime() {
                return createtime;
            }

            public void setCreatetime(int createtime) {
                this.createtime = createtime;
            }

            public int getCompletetime() {
                return completetime;
            }

            public void setCompletetime(int completetime) {
                this.completetime = completetime;
            }

            public String getBeizhu() {
                return beizhu;
            }

            public void setBeizhu(String beizhu) {
                this.beizhu = beizhu;
            }
        }

}
