package com.resttcar.dh.entity;

import java.util.List;

/**
 * Created by James on 2020/5/29.
 */
public class TiXian {

        /**
         * current_page : 1
         * per_page : 10
         * total : 11
         * has_more : true
         * list : [{"tixian_id":11,"money":"100.00","beizhu":"账户问题","status":-1,"tixian_percent":"6.00","pay_money":"94.00","createtime":1585729788,"account":"admin","adminname":"admin"},{"tixian_id":8,"money":"100.00","beizhu":"","status":1,"tixian_percent":"0.00","pay_money":"0.00","createtime":1583722734,"account":"admin","adminname":null},{"tixian_id":7,"money":"100.00","beizhu":"","status":1,"tixian_percent":"0.00","pay_money":"0.00","createtime":1583722725,"account":"admin","adminname":null},{"tixian_id":6,"money":"100.00","beizhu":"","status":1,"tixian_percent":"0.00","pay_money":"0.00","createtime":1583722716,"account":"admin","adminname":null},{"tixian_id":5,"money":"100.00","beizhu":"","status":1,"tixian_percent":"0.00","pay_money":"0.00","createtime":1583401293,"account":"admin","adminname":null},{"tixian_id":4,"money":"100.00","beizhu":"","status":1,"tixian_percent":"0.00","pay_money":"0.00","createtime":1583401284,"account":"admin","adminname":null},{"tixian_id":1,"money":"100.00","beizhu":"提现","status":1,"tixian_percent":"0.00","pay_money":"0.00","createtime":1579068790,"account":"admin","adminname":"admin"},{"tixian_id":12,"money":"100.00","beizhu":"","status":2,"tixian_percent":"6.00","pay_money":"94.00","createtime":1585733522,"account":"admin","adminname":"admin"},{"tixian_id":10,"money":"100.00","beizhu":"","status":2,"tixian_percent":"6.00","pay_money":"94.00","createtime":1585727764,"account":"admin","adminname":"admin"},{"tixian_id":9,"money":"100.00","beizhu":"","status":2,"tixian_percent":"6.00","pay_money":"94.00","createtime":1585725318,"account":"admin","adminname":"admin"}]
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
             * tixian_id : 11
             * money : 100.00
             * beizhu : 账户问题
             * status : -1
             * tixian_percent : 6.00
             * pay_money : 94.00
             * createtime : 1585729788
             * account : admin
             * adminname : admin
             */

            private int tixian_id;
            private String money;
            private String beizhu;
            private int status;
            private String tixian_percent;
            private String pay_money;
            private int createtime;
            private String account;
            private String adminname;

            public int getTixian_id() {
                return tixian_id;
            }

            public void setTixian_id(int tixian_id) {
                this.tixian_id = tixian_id;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getBeizhu() {
                return beizhu;
            }

            public void setBeizhu(String beizhu) {
                this.beizhu = beizhu;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTixian_percent() {
                return tixian_percent;
            }

            public void setTixian_percent(String tixian_percent) {
                this.tixian_percent = tixian_percent;
            }

            public String getPay_money() {
                return pay_money;
            }

            public void setPay_money(String pay_money) {
                this.pay_money = pay_money;
            }

            public int getCreatetime() {
                return createtime;
            }

            public void setCreatetime(int createtime) {
                this.createtime = createtime;
            }

            public String getAccount() {
                return account;
            }

            public void setAccount(String account) {
                this.account = account;
            }

            public String getAdminname() {
                return adminname;
            }

            public void setAdminname(String adminname) {
                this.adminname = adminname;
            }
        }

}
