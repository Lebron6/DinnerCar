package com.resttcar.dh.entity;

import java.util.List;

/**
 * Created by James on 2020/6/1.
 */
public class Notice {

        /**
         * current_page : 1
         * per_page : 10
         * total : 5
         * has_more : false
         * list : [{"message_id":5,"title":"餐车提现","desc":"已到账(提现金额：100.00，平台抽成：6.00%，实际到账：94.00)","url":"/car/caiwu/tixian.html","createtime":1585733546,"isread":1},{"message_id":4,"title":"餐车提现驳回","desc":"账户问题","url":"","createtime":0,"isread":0},{"message_id":3,"title":"餐车提现","desc":"已到账(提现金额：100.00，平台抽成：6.00%，实际到账：94.00)","url":"/car/caiwu/tixian.html","createtime":1585728511,"isread":0},{"message_id":2,"title":"餐车提现","desc":"已到账(提现金额：100.00，平台抽成：5)","url":"/car/caiwu/tixian.html","createtime":1585725811,"isread":1},{"message_id":1,"title":"系统提示","desc":"欢迎来到餐车联盟","url":"","createtime":1579068790,"isread":1}]
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
             * message_id : 5
             * title : 餐车提现
             * desc : 已到账(提现金额：100.00，平台抽成：6.00%，实际到账：94.00)
             * url : /car/caiwu/tixian.html
             * createtime : 1585733546
             * isread : 1
             */

            private int message_id;
            private String title;
            private String desc;
            private String url;
            private int createtime;
            private int isread;

            public int getMessage_id() {
                return message_id;
            }

            public void setMessage_id(int message_id) {
                this.message_id = message_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getCreatetime() {
                return createtime;
            }

            public void setCreatetime(int createtime) {
                this.createtime = createtime;
            }

            public int getIsread() {
                return isread;
            }

            public void setIsread(int isread) {
                this.isread = isread;
            }
        }

}
