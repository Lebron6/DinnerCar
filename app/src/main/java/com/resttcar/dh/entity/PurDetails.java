package com.resttcar.dh.entity;

import java.util.List;

/**
 * Created by James on 2020/5/29.
 */
public class PurDetails {


        private OrderBean order;
        private List<MatrialsBean> matrials;

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public List<MatrialsBean> getMatrials() {
            return matrials;
        }

        public void setMatrials(List<MatrialsBean> matrials) {
            this.matrials = matrials;
        }

        public static class OrderBean {
            /**
             * order_id : 8
             * order_no : 20200408162435396200001
             * price : 0.04
             * pay_type : 1
             * order_status : 1
             * createtime : 1586334276
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

        public static class MatrialsBean {
            /**
             * order_id : 8
             * material_id : 2
             * price : 0.02
             * amount : 2
             * name : 11188888
             * image : http://dh.tigerisa.com/uploads/20200325/666ba4a18217528515d8953bb027db20.jpg
             * unit : 瓶
             */

            private int order_id;
            private int material_id;
            private String price;
            private int amount;
            private String name;
            private String image;
            private String unit;

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public int getMaterial_id() {
                return material_id;
            }

            public void setMaterial_id(int material_id) {
                this.material_id = material_id;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getUnit() {
                return unit;
            }

            public void setUnit(String unit) {
                this.unit = unit;
            }
        }

}
