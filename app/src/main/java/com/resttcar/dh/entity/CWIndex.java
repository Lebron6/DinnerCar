package com.resttcar.dh.entity;

import java.util.List;

/**
 * Created by James on 2020/5/29.
 */
public class CWIndex {

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
             * caiwu_id : 16
             * title : 购买原料
             * money : -0.01
             * beizhu : 支出
             * createtime : 1586416476
             */

            private int caiwu_id;
            private String title;
            private String money;
            private String beizhu;
            private int createtime;

            public int getCaiwu_id() {
                return caiwu_id;
            }

            public void setCaiwu_id(int caiwu_id) {
                this.caiwu_id = caiwu_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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

            public int getCreatetime() {
                return createtime;
            }

            public void setCreatetime(int createtime) {
                this.createtime = createtime;
            }
        }

}
