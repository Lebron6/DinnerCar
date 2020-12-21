package com.resttcar.dh.entity;

import java.util.List;

/**
 * Created by James on 2020/5/25.
 */
public class GetHang {



        private OrderBean order;

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public static class OrderBean {
            /**
             * order_id : 91
             * order_no : 20200409151753938300001
             * price : 744.00
             * pay_price : 744.00
             * list : [{"goods_id":11,"spec":"一人份","price":"444.00","amount":4,"title":"牛山手数","image":"/uploads/20200317/a7a6d97d917b3a5cf5c5ed30986c0e36.jpg"},{"goods_id":13,"spec":"一人份","price":"300.00","amount":3,"title":"牛山手数888","image":"http://dh.tigerisa.com/uploads/20200318/305f08120ddbe5bfa01a1f00902bc4a7.jpg"}]
             */

            private int order_id;
            private String order_no;
            private String price;
            private String pay_price;
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

            public String getPay_price() {
                return pay_price;
            }

            public void setPay_price(String pay_price) {
                this.pay_price = pay_price;
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
                 * price : 444.00
                 * amount : 4
                 * title : 牛山手数
                 * image : /uploads/20200317/a7a6d97d917b3a5cf5c5ed30986c0e36.jpg
                 */

                private int goods_id;
                private String spec;
                private String price;
                private int amount;
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
}
