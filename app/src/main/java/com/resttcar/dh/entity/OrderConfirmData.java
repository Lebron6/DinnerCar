package com.resttcar.dh.entity;

/**
 * Created by James on 2020/5/25.
 */
public class OrderConfirmData {

        private String order_code;
        private boolean res;

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public boolean isRes() {
            return res;
        }

        public void setRes(boolean res) {
            this.res = res;
        }

}
